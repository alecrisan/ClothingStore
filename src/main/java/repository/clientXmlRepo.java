package repository;

import domain.BaseEntity;
import domain.Client;
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

public class clientXmlRepo extends inMemoryRepository<Long, Client> {

    private String filePath;

    public clientXmlRepo(Validator<Client> v, String file){
        super(v);
        this.filePath = file;
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        validator.validate(entity);

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);
            Element root = doc.getDocumentElement();

            Element itemElement = doc.createElement("client");
            itemElement.setAttribute("id", String.valueOf(entity.getId()));
            root.appendChild(itemElement);

            appendChildWithText(doc, itemElement, "name", entity.getName());
            appendChildWithText(doc, itemElement, "age", String.valueOf(entity.getAge()));
            appendChildWithText(doc, itemElement, "membership", entity.getMembershipType());

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
    public Optional<Client> delete(Long id){
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
        Optional<Client> opt = Optional.ofNullable(entities.remove(id));

        return opt;
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException
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
                            if ("name".equals(node.getNodeName())) {
                                node.setTextContent(entity.getName());
                            } else if ("membership".equals(node.getNodeName())) {
                                node.setTextContent(entity.getMembershipType());
                            } else if ("age".equals(node.getNodeName())) {
                                node.setTextContent(String.valueOf(entity.getAge()));
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

        Optional<Client> opt = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
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
            Node clientNode = nodes.item(i);
            if(clientNode instanceof Element){
                Client client = createItem((Element)clientNode);
                entities.put(client.getId(), client);
            }
        }
    }

    private Client createItem(Element itemNode){
        String idS = itemNode.getAttribute("id");
        java.lang.Long id = java.lang.Long.parseLong(idS);

        Client client = new domain.Client(getTextFromTagName(itemNode, "name"), Integer.parseInt(getTextFromTagName(itemNode, "age")), getTextFromTagName(itemNode, "membership"));
        client.setId(id);

        return client;
    }

    private static String getTextFromTagName(Element element, String tagName){
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }
}
