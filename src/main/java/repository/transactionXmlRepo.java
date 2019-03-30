package repository;

import domain.BaseEntity;
import domain.Client;
import domain.ClothingItem;
import domain.Transaction;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.*;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.xml.sax.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class transactionXmlRepo extends inMemoryRepository<Long, Transaction> {

    private String filePath;

    public transactionXmlRepo(Validator<Transaction> v, String file){
        super(v);
        this.filePath = file;
    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        validator.validate(entity);

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);
            Element root = doc.getDocumentElement();

            Element itemElement = doc.createElement("transaction");
            itemElement.setAttribute("id", String.valueOf(entity.getId()));
            root.appendChild(itemElement);

            appendChildWithText(doc, itemElement, "client", entity.getClient().getId() + "," + entity.getClient().getName() + "," + String.valueOf(entity.getClient().getAge()) + "," + entity.getClient().getMembershipType());
            appendChildWithText(doc, itemElement, "date", entity.getDate());
            String items = "";
            for(int i=0; i<entity.getItems().size(); i++){
                items = items + String.valueOf(entity.getItems().get(i).getId()) + "," + entity.getItems().get(i).getName() + "," + entity.getItems().get(i).getDesigner() + "," + String.valueOf(entity.getItems().get(i).getPrice()) + ";";
            }
            appendChildWithText(doc, itemElement, "items", items);
            appendChildWithText(doc, itemElement, "total", String.valueOf(entity.getTotalPrice()));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(root), new StreamResult(new FileOutputStream(filePath)));
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        
    }

    private static void appendChildWithText(Document doc, Node parent, String tagName, String textContent){
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    @Override
    public Optional<Transaction> delete(Long id){
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);

            Element root = doc.getDocumentElement();

            NodeList nodes = root.getChildNodes();

            int len = nodes.getLength();
            for(int i =0; i<len; i++) {
                Node itemNode = nodes.item(i);
                if (itemNode instanceof Element) {
                    if (((Element) itemNode).getAttribute("id").equals(String.valueOf(id))) {
                        root.removeChild(itemNode);
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        Optional<Transaction> opt = Optional.ofNullable(entities.remove(id));

        return opt;
    }

    @Override
    public Optional<Transaction> update(Transaction entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);

            Element root = doc.getDocumentElement();

            NodeList nodes = root.getChildNodes();
            int len = nodes.getLength();
            for(int i =0; i<len; i++){
                Node itemNode = nodes.item(i);
                if(itemNode instanceof Element){
                    if(((Element) itemNode).getAttribute("id").equals(String.valueOf(entity.getId()))){
                        NodeList list = itemNode.getChildNodes();

                        for (int j = 0; j < list.getLength(); j++) {

                            Node node = list.item(j);
                            if ("client".equals(node.getNodeName())) {
                                node.setTextContent((entity.getClient().getId() + "," + entity.getClient().getName() + "," + String.valueOf(entity.getClient().getAge()) + "," + entity.getClient().getMembershipType()));
                            } else if ("date".equals(node.getNodeName())) {
                                node.setTextContent(entity.getDate());
                            } else if ("items".equals(node.getNodeName())) {
                                String items = "";
                                for(int k=0; k<entity.getItems().size(); k++){
                                    items = items + String.valueOf(entity.getItems().get(k).getId()) + "," + entity.getItems().get(k).getName() + "," + entity.getItems().get(k).getDesigner() + "," + String.valueOf(entity.getItems().get(k).getPrice()) + ";";
                                }
                                node.setTextContent(items);
                            }
                            else if("total".equals(node.getNodeName())){
                                node.setTextContent(String.valueOf(entity.getTotalPrice()));
                            }
                        }
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        Optional<Transaction> opt = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
        return opt;
    }

    public void readFromFile() throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();

        Document doc = documentBuilder.parse(filePath);
        Element root = doc.getDocumentElement();

        NodeList nodes = root.getChildNodes();
        int len = nodes.getLength();

        for(int i = 0; i<len; i++){
            Node transactionNode = nodes.item(i);
            if(transactionNode instanceof Element){
                Transaction transaction = createItem((Element)transactionNode);
                entities.put(transaction.getId(), transaction);
            }
        }
    }

    private Transaction createItem(Element itemNode){
        String idS = itemNode.getAttribute("id");
        java.lang.Long id = java.lang.Long.parseLong(idS);

        String clientS = getTextFromTagName(itemNode, "client");
        String[] attributes = clientS.split(",");
        Client client = new Client(attributes[1], Integer.parseInt(attributes[2]), attributes[3]);
        client.setId(java.lang.Long.parseLong(attributes[0]));
        Transaction transaction = new Transaction(client, getTextFromTagName(itemNode, "date"));
        transaction.setId(id);

        String itemS = getTextFromTagName(itemNode, "items");
        String[] itemListS = itemS.split(";");
        for(int i=0; i<itemListS.length; i++){
            String[] itemAttributes = itemListS[i].split(",");
            ClothingItem item = new ClothingItem(itemAttributes[1], itemAttributes[2], Integer.parseInt(itemAttributes[3]));
            item.setId(java.lang.Long.parseLong(itemAttributes[0]));
            transaction.addItemToCart(item);
        }

        return transaction;
    }

    private static String getTextFromTagName(Element element, String tagName){
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }
}
