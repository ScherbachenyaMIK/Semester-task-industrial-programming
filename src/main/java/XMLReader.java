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
    public XMLReader(String filename) throws IllegalArgumentException, FileNotFoundException {
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
            throw new IllegalArgumentException("File content does not match xml document");
        } catch (IOException e) {
            throw new FileNotFoundException("Unable to load or read file");
        }
        root = document.getDocumentElement();
    }

    public String ReadString() throws IllegalArgumentException {
        NodeList nodeList = document.getElementsByTagName("Data");
        if (nodeList.getLength() > stringCounter) {
            return nodeList.item(stringCounter++).getTextContent();
        }
        else {
            throw new IllegalArgumentException("Unable to read String");
        }
    }
    public int ReadInteger() throws IllegalArgumentException {
        String data = ReadString();
        if (data != null) {
            try {
                return Integer.parseInt(data);
            }
            catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Unable to read Integer");
            }
        }
        else {
            throw new IllegalArgumentException("Unable to read Integer");
        }
    }
    public MathExpression ReadMathExpression() throws IllegalArgumentException {
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
            throw new IllegalArgumentException("Unable to read math expression");
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
            throw new IllegalArgumentException("Unable to read math expression");
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
            throw new IllegalArgumentException("Unable to read math expression");
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
            throw new IllegalArgumentException("Unable to read math expression");
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
            throw new IllegalArgumentException("Unable to read math expression");
        }
        ++mathExpressionsCounter;
        return mathExpression;
    }
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        ArrayList<MathExpression> expressions = new ArrayList<>();
        MathExpression mathExpression;
        try {
            while(true)
            {
                mathExpression = ReadMathExpression();
                expressions.add(mathExpression);
            }
        }
        catch (IllegalArgumentException exception) {

        }
        if (expressions.isEmpty()) {
            throw new IllegalArgumentException("Unable to read math expression");
        }
        return expressions;
    }
}

class XMLNonAPIReader {
    private _BufferedFileReader reader;

    public XMLNonAPIReader(String filename) throws FileNotFoundException {
        File File_ = new File(filename);
        if (!File_.exists()) {
            throw new FileNotFoundException("Unable to load file");
        }
        reader = new _BufferedFileReader(new _FileReader(File_));
    }
    public void CloseXMLNonAPIReader() throws IOException {
        reader.CloseFile();
    }
    public String ReadString() throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.ReadString()) != null) {
            content.append(line);
        }

        Pattern StringPattern = Pattern.compile("<Data>(.*?)</Data>");
        Matcher StringMatcher = StringPattern.matcher(content);
        if (StringMatcher.find()) {
            return StringMatcher.group(1);
        }
        else {
            throw new IllegalArgumentException("Unable to read String");
        }
    }
    public int ReadInteger() throws IllegalArgumentException {
        try {
            return Integer.parseInt(ReadString());
        }
        catch (NumberFormatException | IOException exception) {
            throw new IllegalArgumentException("Unable to read Integer");
        }
    }
    public MathExpression ReadMathExpression() throws IllegalArgumentException, IOException {
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
            throw new IllegalArgumentException("Unable to read math expression");
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
            throw new IllegalArgumentException("Unable to read math expression");
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
            throw new IllegalArgumentException("Unable to read math expression");
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
            throw new IllegalArgumentException("Unable to read math expression");
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
            throw new IllegalArgumentException("Unable to read math expression");
        }

        return mathExpression;
    }
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        StringBuilder content = new StringBuilder();
        String line = reader.ReadString();
        if (!line.equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>")) {
            throw new IOException("The .xml file is damaged or does not match the format");
        }
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
                if (!variablesContent.equals("\"\"")) {
                    ArrayList<Character> variables = new ArrayList<>();
                    for (int i = 1; i < variablesContent.length(); i += 3) {
                        variables.add(variablesContent.charAt(i));
                    }
                    mathExpression.setVariables(variables);
                }
            } else {
                break;
            }

            if (typesMatcher.find()) {
                String typesContent = typesMatcher.group(1);
                if (!typesContent.equals("\"\"")) {
                    ArrayList<Character> types = new ArrayList<>();
                    for (int i = 1; i < typesContent.length(); i += 3) {
                        types.add(typesContent.charAt(i));
                    }
                    mathExpression.setTypes(types);
                }
            } else {
                break;
            }

            if (integersMatcher.find()) {
                String integersContent = integersMatcher.group(1);
                if (!integersContent.equals("\"\"")) {
                    ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
                    String[] Pairs = integersContent.split("\"\"");
                    for (String s : Pairs) {
                        integers.add(new ImmutablePair<>(
                                Integer.parseInt(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                                Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
                    }
                    mathExpression.setIntegers(integers);
                }
            } else {
                break;
            }

            if (doublesMatcher.find()) {
                String doublesContent = doublesMatcher.group(1);
                if (!doublesContent.equals("\"\"")) {
                    ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
                    String[] Pairs = doublesContent.split("\"\"");
                    for (String s : Pairs) {
                        doubles.add(new ImmutablePair<>(
                                Double.parseDouble(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                                Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
                    }
                    mathExpression.setDoubles(doubles);
                }
            } else {
                break;
            }
            expressions.add(mathExpression);
        }
        if (expressions.isEmpty()) {
            throw new IllegalArgumentException("Unable to read math expression");
        }
        return expressions;
    }
}