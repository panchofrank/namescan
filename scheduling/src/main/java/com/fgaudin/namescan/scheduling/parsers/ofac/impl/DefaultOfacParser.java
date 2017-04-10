package com.fgaudin.namescan.scheduling.parsers.ofac.impl;


import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.scheduling.parsers.ofac.OfacParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultOfacParser implements OfacParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultOfacParser.class);

    private static final String TAG_ROOT = "sdnList";
    private static final String TAG_PUBLISH_INFORMATION = "publshInformation";
    private static final String TAG_RECORD_COUNT = "Record_Count";
    private static final String TAG_PUBLISH_DATE = "Publish_Date";
    private static final String TAG_ENTRY = "sdnEntry";
    private static final String TAG_ENTRY_TYPE = "sdnType";
    private static final String TAG_LAST_NAME = "lastName";
    private static final String TAG_FIRST_NAME = "firstName";
    private static final String TAG_UID = "uid";

    private static final String DATE_PATTERN = "MM/dd/yyyy";

    private static final String INDIVIDUAL_TYPE = "Individual";

    @Override
    public List<Person> parse(Resource resource) {
        LOGGER.info("Parsing resource...");
        PublicationInformation metaData = null;
        List<Person> persons = new ArrayList<>();
        Document document = documentFromFile(resource);
        Node rootNode = resolveRootNode(document);
        NodeList nodes = rootNode.getChildNodes();


        for( int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                LOGGER.debug("At node {}", nodeName);
                if (TAG_ENTRY.equals(nodeName)) {
                    Optional<Person> person = resolvePersonFromNode(node);
                    person.ifPresent(persons::add);
                } else if (TAG_PUBLISH_INFORMATION.equals(nodeName)) {
                    if (metaData != null) {
                        throw new RuntimeException("Found more than one metadata tag!");
                    }
                    metaData = resolveMetaData(node);
                }
            }
        }

            // TODO check date and count against parsed data
            if (metaData != null) {
                LOGGER.info("Publication date: {}, Count: {}", metaData.getPublicationDate(), metaData.getCount());
            }

            return persons;
    }

    private Node resolveRootNode(Document document) {
        LOGGER.info("Resolving root node...");
        NodeList nodeList = document.getChildNodes();
        if (nodeList.getLength() != 1) {
            LOGGER.warn("Found more than 1 root node!");
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE || !TAG_ROOT.equals(node.getNodeName())) {
                LOGGER.warn("Unexpected node: {}", node.getNodeName());
            } else {
                return node;
            }
        }
        throw new RuntimeException("Expected to find node " + TAG_ROOT + ", but it wasn't present!");
    }

    private PublicationInformation resolveMetaData(Node metaDataNode) {
        LOGGER.debug("resolveMetaData...");

        NodeList nodes = metaDataNode.getChildNodes();
        Integer count = null;
        LocalDate date = null;

        for( int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();

                if (TAG_RECORD_COUNT.equals(nodeName)) {
                    if (count != null) {
                        throw new RuntimeException("Found more than one count tag!");
                    }
                    count = resolveCountFromPublicationNode(node);

                } else if (TAG_PUBLISH_DATE.equals(nodeName)) {
                    if (date != null) {
                        throw new RuntimeException("Found more than one publish date tag!");
                    }
                date = resolveDateFromPublicationNode(node);
                }
            }
        }
        return createMetaData(count, date);


    }

    private PublicationInformation createMetaData(Integer count, LocalDate date) {
        PublicationInformation metaData = new PublicationInformation();
        metaData.setCount(count);

        metaData.setPublicationDate(date);
        return metaData;

    }
    private Integer resolveCountFromPublicationNode(Node node) {
        String countStr = node.getTextContent();
        try {
            return Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            LOGGER.warn("Invalid count value: {}", countStr);
        }
        return null;
    }

    private LocalDate resolveDateFromPublicationNode(Node node) {
        String dateStr = node.getTextContent();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            LOGGER.warn("Invalid publication date: {}", dateStr);
        }
        return null;
    }



    // TODO parse companies
    private Optional<Person> resolvePersonFromNode(Node personNode) {
        LOGGER.debug("resolvePersonFromElement...");
        NodeList nodes = personNode.getChildNodes();

        String uid = "";
        String firstName = "";
        String lastName = "";
        for( int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                if (TAG_ENTRY_TYPE.equals(nodeName) && !INDIVIDUAL_TYPE.equals(node.getTextContent())) {
                        return Optional.empty();

                } else if (TAG_UID.equals(nodeName)) {
                    uid = node.getTextContent();

                } else if (TAG_FIRST_NAME.equals(nodeName)) {
                    firstName = node.getTextContent();
                } else if (TAG_LAST_NAME.equals(nodeName)) {
                    lastName = node.getTextContent();
                }
            }

        }
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);


        // TODO String dobString = resolveChildElementText(element, TAG_DATE_OF_BIRTH);
        // TODO akas
        // TODO uid

        return Optional.of(person);
    }

    private String resolveChildElementText(Element element, String name) {
        LOGGER.debug("resolveChildElementText: {}", name);

        NodeList childElement = element.getElementsByTagName(name);
        verifyNodeListHasOnlyOneItem(childElement);
        String result = "";
        if (childElement != null) {
            result = resolveFirstItemTextContent(childElement);

            LOGGER.debug("Resolved value: {}", result);
        }
        return result;
    }

    private String resolveFirstItemTextContent(NodeList nodeList) {
        String result = "";
        Node node = nodeList.item(0);
        if (node != null) {
            result = node.getTextContent();
        }
        return result;
    }

    private void verifyNodeListHasOnlyOneItem(NodeList nodeList) {
        int length = nodeList.getLength();
        if (length != 1) {
            LOGGER.warn("Expected one item but found {}", length);

        }
    }
    private File fileFromResource(Resource resource) {
        LOGGER.debug("fileFromResource...");
        try {
            return resource.getFile();
        } catch (IOException e) {
            LOGGER.error("Unable to resolve file: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Document documentFromFile(Resource resource) {
        LOGGER.debug("documentFromFile...");
        File file = fileFromResource(resource);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            return document;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error("Unable to build Document from file: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
