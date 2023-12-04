package org.example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class XMLReader {
    private File file;

    public XMLReader(String filename) {
        this.file = new File(filename);
    }

    public String ReadString() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList nodeList = document.getElementsByTagName("Data");
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    public int ReadInteger() throws ParserConfigurationException, IOException, SAXException {
        String data = ReadString();
        if (data != null) {
            return Integer.parseInt(data);
        }
        return 0;
    }
}
