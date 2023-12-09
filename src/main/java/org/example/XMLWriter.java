package org.example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class XMLWriter {
    private File file;

    public XMLWriter(String filename) {
        this.file = new File(filename);
    }

    public void WriteString(String data) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("Data");
        root.appendChild(document.createTextNode(data));
        document.appendChild(root);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);

        transformer.transform(domSource, streamResult);
    }

    public void WriteInteger(int data) throws ParserConfigurationException, TransformerException {
        WriteString(String.valueOf(data));
    }

    public void WriteExpression(MathExpression data) throws ParserConfigurationException, TransformerException, InvocationTargetException, IllegalAccessException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("MathExpression");
        document.appendChild(root);

        Element expressionElement = document.createElement("expression");
        expressionElement.appendChild(document.createTextNode(data.getExpession()));
        root.appendChild(expressionElement);

        Element variablesElement = document.createElement("variables");
        for (char var : data.getVariables()) {
            variablesElement.appendChild(document.createTextNode("\"" + String.valueOf(var) + "\""));
        }
        root.appendChild(variablesElement);

        Element typesElement = document.createElement("types");
        for (char var : data.getTypes()) {
            typesElement.appendChild(document.createTextNode("\"" + String.valueOf(var) + "\""));
        }
        root.appendChild(typesElement);

        Element integersElement = document.createElement("integers");
        for (ImmutablePair<Integer, Integer> var : data.getIntegers()) {
            integersElement.appendChild(document.createTextNode("\"" + String.valueOf(var) + "\""));
        }
        root.appendChild(integersElement);

        Element doublesElement = document.createElement("doubles");
        for (ImmutablePair<Double, Integer> var : data.getDoubles()) {
            doublesElement.appendChild(document.createTextNode("\"" + String.valueOf(var) + "\""));
        }
        root.appendChild(doublesElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }
}
