package repository;

import domain.BaseEntity;
import domain.ClothingItem;
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

public class itemXmlRepo extends inMemoryRepository<Long, ClothingItem> {

    private String filePath;

    public itemXmlRepo(Validator<ClothingItem> v, String file){
        super(v);
        this.filePath = file;
    }

    @Override
    public Optional<ClothingItem> save(ClothingItem entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        validator.validate(entity);

        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);
            Element root = doc.getDocumentElement();

            Element itemElement = doc.createElement("item");
            itemElement.setAttribute("id", String.valueOf(entity.getId()));
            root.appendChild(itemElement);

            appendChildWithText(doc, itemElement, "name", entity.getName());
            appendChildWithText(doc, itemElement, "designer", entity.getDesigner());
            appendChildWithText(doc, itemElement, "price", String.valueOf(entity.getPrice()));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(root), new StreamResult(new FileOutputStream(filePath)));

        }
        catch(Exception ex){
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

    public Optional<ClothingItem> delete(Long id){
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
        Optional<ClothingItem> opt = Optional.ofNullable(entities.remove(id));

        return opt;
    }

    @Override
    public Optional<ClothingItem> update(ClothingItem entity) throws ValidatorException
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

                        for (int j = 0; i < list.getLength(); i++) {

                            Node node = list.item(i);
                            if ("name".equals(node.getNodeName())) {
                                node.setTextContent(entity.getName());
                            } else if ("designer".equals(node.getNodeName())) {
                                node.setTextContent(entity.getDesigner());
                            } else if ("price".equals(node.getNodeName())) {
                                node.setTextContent(String.valueOf(entity.getPrice()));
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

        Optional<ClothingItem> opt = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
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
            Node itemNode = nodes.item(i);
            if(itemNode instanceof Element){
                ClothingItem item = createItem((Element)itemNode);
                entities.put(item.getId(), item);
            }
        }
    }

    private ClothingItem createItem(Element itemNode){
        String idS = itemNode.getAttribute("id");
        java.lang.Long id = java.lang.Long.parseLong(idS);

        ClothingItem item = new ClothingItem(getTextFromTagName(itemNode, "name"), getTextFromTagName(itemNode, "designer"), Integer.parseInt(getTextFromTagName(itemNode, "price")));
        item.setId(id);

        return item;
    }

    private static String getTextFromTagName(Element element, String tagName){
      NodeList elements = element.getElementsByTagName(tagName);
      Node node = elements.item(0);
      return node.getTextContent();
    }
}

