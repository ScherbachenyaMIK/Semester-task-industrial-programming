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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        expressionElement.appendChild(document.createTextNode(data.getExpression()));
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

class XMLNonAPIWriter {
    private BufferedWriter writer;

    XMLNonAPIWriter(String filename) throws IOException {
        File File_ = new File(filename);
        writer = new BufferedWriter(new FileWriter(File_));
    }
    void CloseXMLNonAPIWriter() throws IOException {
        writer.close();
    }
    public void WriteString(String str) throws IOException {
        writer.write("<Data>" + str + "</Data>");
    }
    public void WriteInteger(int i) throws IOException {
             writer.write("<Data>" + i + "</Data>");
    }
    public void WriteMathExpression(MathExpression expression) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
                .append("<MathExpression>\n")
                .append("  <expression>").append(expression.getExpression()).append("</expression>\n")
                .append("  <variables>");
        String str = String.join("\"\"", expression.getVariables().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</variables>\n").append("  <types>");
        str = String.join("\"\"", expression.getTypes().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</types>\n").append("  <integers>");
        str = String.join("\"\"", expression.getIntegers().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</integers>\n").append("  <doubles>");
        str = String.join("\"\"", expression.getDoubles().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</doubles>\n")
                .append("</MathExpression>");
        writer.write(content.toString());
    }
}
