package org.example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public MathExpression ReadMathExpression() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList expressionNodeList = document.getElementsByTagName("expression");
        NodeList variablesNodeList = document.getElementsByTagName("variables");
        NodeList typesNodeList = document.getElementsByTagName("types");
        NodeList integersNodeList = document.getElementsByTagName("integers");
        NodeList doublesNodeList = document.getElementsByTagName("doubles");

        MathExpression mathExpression = new MathExpression();
        if (expressionNodeList.getLength() > 0) {
            mathExpression.setExpression(expressionNodeList.item(0).getTextContent());
        }
        else
        {
            throw new IOException();
        }

        if (variablesNodeList.getLength() > 0) {
            ArrayList<Character> variables = new ArrayList<>();
            String variablesContent = variablesNodeList.item(0).getTextContent();
            for(int i = 1; i < variablesContent.length(); i += 3) {
                variables.add(variablesContent.charAt(i));
            }
            mathExpression.setVariables(variables);
        }
        else
        {
            throw new IOException();
        }

        if (typesNodeList.getLength() > 0) {
            ArrayList<Character> types = new ArrayList<>();
            String typesContent = typesNodeList.item(0).getTextContent();
            for(int i = 1; i < typesContent.length(); i += 3) {
                types.add(typesContent.charAt(i));
            }
            mathExpression.setTypes(types);
        }
        else
        {
            throw new IOException();
        }

        if (integersNodeList.getLength() > 0) {
            ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
            String integersContent = integersNodeList.item(0).getTextContent();
            String[] Pairs = integersContent.split("\"\"");
            for(String s : Pairs) {
                integers.add(new ImmutablePair<>(
                        Integer.parseInt(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                        Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
            }
            mathExpression.setIntegers(integers);
        }
        else
        {
            throw new IOException();
        }

        if (doublesNodeList.getLength() > 0) {
            ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
            String doublesContent = doublesNodeList.item(0).getTextContent();
            String[] Pairs = doublesContent.split("\"\"");
            for(String s : Pairs) {
                doubles.add(new ImmutablePair<>(
                        Double.parseDouble(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                        Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
            }
            mathExpression.setDoubles(doubles);
        }
        else
        {
            throw new IOException();
        }
        return mathExpression;
    }
}

class XMLNonAPIReader {
    private _BufferedFileReader reader;

    public XMLNonAPIReader(String filename) throws FileNotFoundException {
        File File_ = new File(filename);
        reader = new _BufferedFileReader(new _FileReader(File_));
    }
    public void CloseXMLNonAPIReader() throws IOException {
        reader.CloseFile();
    }
    public MathExpression ReadMathExpression() throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.ReadString()) != null) {
            content.append(line);
        }

        MathExpression mathExpression = new MathExpression();

        Pattern expressionPattern = Pattern.compile("<expression>(.*?)</expression>");
        Matcher expressionMatcher = expressionPattern.matcher(content);
        if (expressionMatcher.find()) {
            mathExpression.setExpression(expressionMatcher.group(1));
        } else {
            throw new IOException("Expression not found in XML");
        }

        Pattern variablesPattern = Pattern.compile("<variables>(.*?)</variables>");
        Matcher variablesMatcher = variablesPattern.matcher(content);
        if (variablesMatcher.find()) {
            String variablesContent = variablesMatcher.group(1);
            ArrayList<Character> variables = new ArrayList<>();
            for (int i = 1; i < variablesContent.length(); i += 3) {
                variables.add(variablesContent.charAt(i));
            }
            mathExpression.setVariables(variables);
        } else {
            throw new IOException("Variables not found in XML");
        }

        Pattern typesPattern = Pattern.compile("<types>(.*?)</types>");
        Matcher typesMatcher = typesPattern.matcher(content);
        if (typesMatcher.find()) {
            String typesContent = typesMatcher.group(1);
            ArrayList<Character> types = new ArrayList<>();
            for (int i = 1; i < typesContent.length(); i += 3) {
                types.add(typesContent.charAt(i));
            }
            mathExpression.setTypes(types);
        } else {
            throw new IOException("Types not found in XML");
        }

        Pattern integersPattern = Pattern.compile("<integers>(.*?)</integers>");
        Matcher integersMatcher = integersPattern.matcher(content);
        if (integersMatcher.find()) {
            String integersContent = integersMatcher.group(1);
            ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
            String[] Pairs = integersContent.split("\"\"");
            for(String s : Pairs) {
                integers.add(new ImmutablePair<>(
                        Integer.parseInt(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                        Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
            }
            mathExpression.setIntegers(integers);
        } else {
            throw new IOException("Integers not found in XML");
        }

        Pattern doublesPattern = Pattern.compile("<doubles>(.*?)</doubles>");
        Matcher doublesMatcher = doublesPattern.matcher(content);
        if (doublesMatcher.find()) {
            String doublesContent = doublesMatcher.group(1);
            ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
            String[] Pairs = doublesContent.split("\"\"");
            for(String s : Pairs) {
                doubles.add(new ImmutablePair<>(
                        Double.parseDouble(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                        Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
            }
            mathExpression.setDoubles(doubles);
        } else {
            throw new IOException("Doubles not found in XML");
        }

        return mathExpression;
    }
}