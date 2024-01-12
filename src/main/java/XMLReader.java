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

public class XMLReader implements APIHyperTextReader {
    private int stringCounter = 0;
    private int mathExpressionsCounter = 0;
    Document document;
    Element root;

    // Constructor taking the XML file name
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
            throw new IllegalArgumentException("File content does not match XML document");
        } catch (IOException e) {
            throw new FileNotFoundException("Unable to load or read file");
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

    // Read a MathExpression from the XML file
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
}

class XMLNonAPIReader implements NonAPIHyperTextReader {
    private _BufferedFileReader reader;

    // Constructor initializes the reader with the specified filename
    public XMLNonAPIReader(String filename) throws FileNotFoundException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException("Unable to load file");
        }
        reader = new _BufferedFileReader(new _FileReader(file));
    }

    // Close the reader
    public void CloseReader() throws IOException {
        reader.CloseFile();
    }

    // Read a string from the XML content
    public String ReadString() throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.ReadString()) != null) {
            content.append(line);
        }

        // Use regex to extract the content between <Data> tags
        Pattern stringPattern = Pattern.compile("<Data>(.*?)</Data>");
        Matcher stringMatcher = stringPattern.matcher(content);
        if (stringMatcher.find()) {
            return stringMatcher.group(1);
        } else {
            throw new IllegalArgumentException("Unable to read String");
        }
    }

    // Read an integer from the XML content
    public int ReadInteger() throws IllegalArgumentException {
        try {
            // Parse the string content as an integer
            return Integer.parseInt(ReadString());
        } catch (NumberFormatException | IOException exception) {
            throw new IllegalArgumentException("Unable to read Integer");
        }
    }

    // Read a MathExpression from the XML content
    public MathExpression ReadMathExpression() throws IllegalArgumentException, IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.ReadString()) != null) {
            content.append(line);
        }

        MathExpression mathExpression = new MathExpression();

        // Use regex to extract expression content between <expression> tags
        Pattern expressionPattern = Pattern.compile("<expression>(.*?)</expression>");
        Matcher expressionMatcher = expressionPattern.matcher(content);
        if (expressionMatcher.find()) {
            mathExpression.setExpression(expressionMatcher.group(1));
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        // Use regex to extract variables content between <variables> tags
        Pattern variablesPattern = Pattern.compile("<variables>(.*?)</variables>");
        Matcher variablesMatcher = variablesPattern.matcher(content);
        if (variablesMatcher.find()) {
            String variablesContent = variablesMatcher.group(1);
            if (!variablesContent.equals("\"\"")) {
                // Extract individual characters from the variables content
                ArrayList<Character> variables = new ArrayList<>();
                for (int i = 1; i < variablesContent.length(); i += 3) {
                    variables.add(variablesContent.charAt(i));
                }
                mathExpression.setVariables(variables);
            }
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        // Use regex to extract types content between <types> tags
        Pattern typesPattern = Pattern.compile("<types>(.*?)</types>");
        Matcher typesMatcher = typesPattern.matcher(content);
        if (typesMatcher.find()) {
            String typesContent = typesMatcher.group(1);
            if (!typesContent.equals("\"\"")) {
                // Extract individual characters from the types content
                ArrayList<Character> types = new ArrayList<>();
                for (int i = 1; i < typesContent.length(); i += 3) {
                    types.add(typesContent.charAt(i));
                }
                mathExpression.setTypes(types);
            }
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        // Use regex to extract integers content between <integers> tags
        Pattern integersPattern = Pattern.compile("<integers>(.*?)</integers>");
        Matcher integersMatcher = integersPattern.matcher(content);
        if (integersMatcher.find()) {
            String integersContent = integersMatcher.group(1);
            if (!integersContent.equals("\"\"")) {
                // Extract pairs of integers from the integers content
                ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
                String[] pairs = integersContent.split("\"\"");
                for (String s : pairs) {
                    integers.add(new ImmutablePair<>(
                            Integer.parseInt(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                            Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
                }
                mathExpression.setIntegers(integers);
            }
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        // Use regex to extract doubles content between <doubles> tags
        Pattern doublesPattern = Pattern.compile("<doubles>(.*?)</doubles>");
        Matcher doublesMatcher = doublesPattern.matcher(content);
        if (doublesMatcher.find()) {
            String doublesContent = doublesMatcher.group(1);
            if (!doublesContent.equals("\"\"")) {
                // Extract pairs of doubles from the doubles content
                ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
                String[] pairs = doublesContent.split("\"\"");
                for (String s : pairs) {
                    doubles.add(new ImmutablePair<>(
                            Double.parseDouble(s.substring(s.indexOf('(') + 1, s.indexOf(','))),
                            Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf(')')))));
                }
                mathExpression.setDoubles(doubles);
            }
        } else {
            throw new IllegalArgumentException("Unable to read math expression");
        }

        return mathExpression;
    }

    // Read a list of MathExpressions from the XML content
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        StringBuilder content = new StringBuilder();
        String line = reader.ReadString();
        if (!line.equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>")) {
            throw new IOException("The .xml file is damaged or does not match the format");
        }
        while ((line = reader.ReadString()) != null) {
            content.append(line);
        }

        // Use regex patterns and matchers for each component of MathExpression
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
        while (true) {
            MathExpression mathExpression = new MathExpression();

            // Use regex patterns and matchers for each component of MathExpression
            if (expressionMatcher.find()) {
                mathExpression.setExpression(expressionMatcher.group(1));
            } else {
                break;
            }

            if (variablesMatcher.find()) {
                String variablesContent = variablesMatcher.group(1);
                if (!variablesContent.equals("\"\"")) {
                    // Extract individual characters from the variables content
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
                    // Extract individual characters from the types content
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
                    // Extract pairs of integers from the integers content
                    ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
                    String[] pairs = integersContent.split("\"\"");
                    for (String s : pairs) {
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
                    // Extract pairs of doubles from the doubles content
                    ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
                    String[] pairs = doublesContent.split("\"\"");
                    for (String s : pairs) {
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