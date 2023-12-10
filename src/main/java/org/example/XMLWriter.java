package org.example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class XMLWriter {
    private File File_;
    private Document document;
    private Element root;

    public XMLWriter(String filename) throws ParserConfigurationException {
        File_ = new File(filename);
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
        root = document.createElement("Content");
        document.appendChild(root);
    }
    public void CloseXMLWriter() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(File_);
        transformer.transform(domSource, streamResult);
    }
    public void WriteString(String data) throws ParserConfigurationException, TransformerException {
        Element main = document.createElement("Data");
        main.appendChild(document.createTextNode(data));
        root.appendChild(main);
    }

    public void WriteInteger(int data) throws ParserConfigurationException, TransformerException {
        WriteString(String.valueOf(data));
    }

    public void WriteMathExpression(MathExpression data) throws ParserConfigurationException, TransformerException, InvocationTargetException, IllegalAccessException {
        Element main = document.createElement("MathExpression");
        root.appendChild(main);

        Element expressionElement = document.createElement("expression");
        expressionElement.appendChild(document.createTextNode(data.getExpression()));
        main.appendChild(expressionElement);

        Element variablesElement = document.createElement("variables");
        if (data.getVariables().isEmpty()) {
            variablesElement.appendChild(document.createTextNode("\"\""));
        }
        else {
            for (char var : data.getVariables()) {
                variablesElement.appendChild(document.createTextNode("\"" + String.valueOf(var) + "\""));
            }
        }
        main.appendChild(variablesElement);

        Element typesElement = document.createElement("types");
        if (data.getTypes().isEmpty()) {
            typesElement.appendChild(document.createTextNode("\"\""));
        }
        else {
            for (char var : data.getTypes()) {
                typesElement.appendChild(document.createTextNode("\"" + String.valueOf(var) + "\""));
            }
        }
        main.appendChild(typesElement);

        Element integersElement = document.createElement("integers");
        if (data.getIntegers().isEmpty()) {
            integersElement.appendChild(document.createTextNode("\"\""));
        }
        else {
            for (ImmutablePair<Integer, Integer> var : data.getIntegers()) {
                integersElement.appendChild(document.createTextNode("\"" + String.valueOf(var) + "\""));
            }
        }
        main.appendChild(integersElement);

        Element doublesElement = document.createElement("doubles");
        if (data.getDoubles().isEmpty()) {
            doublesElement.appendChild(document.createTextNode("\"\""));
        }
        else {
            for (ImmutablePair<Double, Integer> var : data.getDoubles()) {
                doublesElement.appendChild(document.createTextNode("\"" + String.valueOf(var) + "\""));
            }
        }
        main.appendChild(doublesElement);
    }
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws ParserConfigurationException, TransformerException, InvocationTargetException, IllegalAccessException {
        for(MathExpression me : expressions)
        {
            WriteMathExpression(me);
        }
    }
}

//TODO сброс потока при каждой новой записи
class XMLNonAPIWriter {
    private BufferedWriter writer;

    XMLNonAPIWriter(String filename) throws IOException {
        File File_ = new File(filename);
        writer = new BufferedWriter(new FileWriter(File_));
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Content>\n");
    }
    void CloseXMLNonAPIWriter() throws IOException {
        writer.write("</Content>\n");
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
        content.append("  <MathExpression>\n")
                .append("    <expression>").append(expression.getExpression()).append("</expression>\n")
                .append("    <variables>");
        String str = String.join("\"\"", expression.getVariables().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</variables>\n").append("    <types>");
        str = String.join("\"\"", expression.getTypes().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</types>\n").append("    <integers>");
        str = String.join("\"\"", expression.getIntegers().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</integers>\n").append("    <doubles>");
        str = String.join("\"\"", expression.getDoubles().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</doubles>\n")
                .append("  </MathExpression>\n");
        writer.write(content.toString());
    }
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        for(MathExpression me : expressions)
        {
            WriteMathExpression(me);
        }
    }
}
