package main;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;

public class XMLReader extends APIHyperTextReader {
    private int stringCounter = 0;
    private int mathExpressionsCounter = 0;
    Document document;
    Element root;

    // Constructor taking the XML file name
    public XMLReader(String filename) throws IllegalArgumentException, FileNotFoundException {
        super(filename);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            document = documentBuilder.parse(filename);
        } catch (SAXException e) {
            return;
        } catch (IOException e) {
            return;
        }
        root = document.getDocumentElement();
    }

    // Read a string from the XML file
    public String ReadString() throws IllegalArgumentException {
        NodeList nodeList = document.getElementsByTagName("Data");
        if (nodeList.getLength() > stringCounter) {
            return nodeList.item(stringCounter++).getTextContent();
        } else {
            throw new IllegalArgumentException("Unable to read String");
        }
    }

    // Read an integer from the XML file
    public int ReadInteger() throws IllegalArgumentException {
        String data = ReadString();
        if (data != null) {
            try {
                return Integer.parseInt(data);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Unable to read Integer");
            }
        } else {
            throw new IllegalArgumentException("Unable to read Integer");
        }
    }

    // Read a main.MathExpression from the XML file
    public MathExpression ReadMathExpression() throws IllegalArgumentException {
        NodeList expressionNodeList = root.getElementsByTagName("expression");
        NodeList variablesNodeList = root.getElementsByTagName("variables");
        NodeList typesNodeList = root.getElementsByTagName("types");
        NodeList integersNodeList = root.getElementsByTagName("integers");
        NodeList doublesNodeList = root.getElementsByTagName("doubles");

        MathExpression mathExpression = new MathExpression();
        if (expressionNodeList.getLength() > mathExpressionsCounter) {
            mathExpression.setExpression(expressionNodeList.item(mathExpressionsCounter).getTextContent());
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        if (variablesNodeList.getLength() > mathExpressionsCounter) {
            ArrayList<Character> variables = new ArrayList<>();
            String variablesContent = variablesNodeList.item(mathExpressionsCounter).getTextContent();
            for (int i = 1; i < variablesContent.length(); i += 3) {
                variables.add(variablesContent.charAt(i));
            }
            mathExpression.setVariables(variables);
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        if (typesNodeList.getLength() > mathExpressionsCounter) {
            ArrayList<Character> types = new ArrayList<>();
            String typesContent = typesNodeList.item(mathExpressionsCounter).getTextContent();
            for (int i = 1; i < typesContent.length(); i += 3) {
                types.add(typesContent.charAt(i));
            }
            mathExpression.setTypes(types);
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        if (integersNodeList.getLength() > mathExpressionsCounter) {
            ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
            String integersContent = integersNodeList.item(mathExpressionsCounter).getTextContent();
            String[] Pairs = integersContent.split("\"\"");
            for (String s : Pairs) {
                integers.add(new ImmutablePair<>(
                        Integer.parseInt(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                        Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
            }
            mathExpression.setIntegers(integers);
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        if (doublesNodeList.getLength() > mathExpressionsCounter) {
            ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
            String doublesContent = doublesNodeList.item(mathExpressionsCounter).getTextContent();
            String[] Pairs = doublesContent.split("\"\"");
            for (String s : Pairs) {
                doubles.add(new ImmutablePair<>(
                        Double.parseDouble(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                        Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
            }
            mathExpression.setDoubles(doubles);
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }
        ++mathExpressionsCounter;
        return mathExpression;
    }

    // Read a list of MathExpressions from the XML file
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        ArrayList<MathExpression> expressions = new ArrayList<>();
        MathExpression mathExpression;
        try {
            while (true) {
                mathExpression = ReadMathExpression();
                expressions.add(mathExpression);
            }
        } catch (IllegalArgumentException exception) {
            // Exception indicates the end of MathExpressions in the XML file
        }
        if (expressions.isEmpty()) {
            throw new IllegalArgumentException("Unable to read math expression");
        }
        return expressions;
    }

    @Override
    protected void setFilename(String filename_) {
        filename = filename_;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            document = documentBuilder.parse(filename);
        } catch (SAXException e) {

        } catch (IOException e) {

        }
        root = document.getDocumentElement();
    }
}

