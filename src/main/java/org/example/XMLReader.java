package org.example;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLReader {
    private int stringCounter = 0;
    private int mathExpressionsCounter = 0;
    Document document;
    Element root;
    public XMLReader(String filename) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(filename);
        root = document.getDocumentElement();
    }

    public String ReadString() {
        NodeList nodeList = document.getElementsByTagName("Data");
        if (nodeList.getLength() > stringCounter) {
            return nodeList.item(stringCounter++).getTextContent();
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
    public MathExpression ReadMathExpression() throws IOException {
        NodeList expressionNodeList = root.getElementsByTagName("expression");
        NodeList variablesNodeList = root.getElementsByTagName("variables");
        NodeList typesNodeList = root.getElementsByTagName("types");
        NodeList integersNodeList = root.getElementsByTagName("integers");
        NodeList doublesNodeList = root.getElementsByTagName("doubles");

        MathExpression mathExpression = new MathExpression();
        if (expressionNodeList.getLength() > mathExpressionsCounter) {
            mathExpression.setExpression(expressionNodeList.item(mathExpressionsCounter).getTextContent());
        }
        else
        {
            return null;
        }

        if (variablesNodeList.getLength() > mathExpressionsCounter) {
            ArrayList<Character> variables = new ArrayList<>();
            String variablesContent = variablesNodeList.item(mathExpressionsCounter).getTextContent();
            for(int i = 1; i < variablesContent.length(); i += 3) {
                variables.add(variablesContent.charAt(i));
            }
            mathExpression.setVariables(variables);
        }
        else
        {
            return null;
        }

        if (typesNodeList.getLength() > mathExpressionsCounter) {
            ArrayList<Character> types = new ArrayList<>();
            String typesContent = typesNodeList.item(mathExpressionsCounter).getTextContent();
            for(int i = 1; i < typesContent.length(); i += 3) {
                types.add(typesContent.charAt(i));
            }
            mathExpression.setTypes(types);
        }
        else
        {
            return null;
        }

        if (integersNodeList.getLength() > mathExpressionsCounter) {
            ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
            String integersContent = integersNodeList.item(mathExpressionsCounter).getTextContent();
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
            return null;
        }

        if (doublesNodeList.getLength() > mathExpressionsCounter) {
            ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
            String doublesContent = doublesNodeList.item(mathExpressionsCounter).getTextContent();
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
            return null;
        }
        ++mathExpressionsCounter;
        return mathExpression;
    }
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        ArrayList<MathExpression> expressions = new ArrayList<>();
        MathExpression mathExpression;
        while((mathExpression = ReadMathExpression()) != null)
        {
            expressions.add(mathExpression);
        }
        return expressions;
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
            return null;
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
            return null;
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
            return null;
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
            return null;
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
            return null;
        }

        return mathExpression;
    }
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.ReadString()) != null) {
            content.append(line);
        }

        Pattern expressionPattern = Pattern.compile("<expression>(.*?)</expression>");
        Matcher expressionMatcher = expressionPattern.matcher(content);
        Pattern variablesPattern = Pattern.compile("<variables>(.*?)</variables>");
        Matcher variablesMatcher = variablesPattern.matcher(content);
        Pattern typesPattern = Pattern.compile("<types>(.*?)</types>");
        Matcher typesMatcher = typesPattern.matcher(content);
        Pattern integersPattern = Pattern.compile("<integers>(.*?)</integers>");
        Matcher integersMatcher = integersPattern.matcher(content);
        Pattern doublesPattern = Pattern.compile("<doubles>(.*?)</doubles>");
        Matcher doublesMatcher = doublesPattern.matcher(content);
        ArrayList<MathExpression> expressions = new ArrayList<>();
        while(true)
        {
            MathExpression mathExpression = new MathExpression();
            if (expressionMatcher.find()) {
                mathExpression.setExpression(expressionMatcher.group(1));
            } else {
                break;
            }

            if (variablesMatcher.find()) {
                String variablesContent = variablesMatcher.group(1);
                ArrayList<Character> variables = new ArrayList<>();
                for (int i = 1; i < variablesContent.length(); i += 3) {
                    variables.add(variablesContent.charAt(i));
                }
                mathExpression.setVariables(variables);
            } else {
                break;
            }

            if (typesMatcher.find()) {
                String typesContent = typesMatcher.group(1);
                ArrayList<Character> types = new ArrayList<>();
                for (int i = 1; i < typesContent.length(); i += 3) {
                    types.add(typesContent.charAt(i));
                }
                mathExpression.setTypes(types);
            } else {
                break;
            }

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
                break;
            }

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
                break;
            }
            expressions.add(mathExpression);
        }
        return expressions;
    }
}