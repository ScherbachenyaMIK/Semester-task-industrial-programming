package main;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonNonAPIReader implements NonAPIHyperTextReader {

    // Private member to handle file reading
    private _BufferedFileReader reader;

    // Constructor that takes a filename and initializes the reader
    public JsonNonAPIReader(String filename) throws FileNotFoundException {
        // Check if the file exists
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found!");
        }
        // Initialize the reader with the file
        reader = new _BufferedFileReader(new _FileReader(file));
    }

    // Close the file reader
    public void CloseReader() throws IOException {
        reader.CloseFile();
    }

    // Read a JSON string and remove enclosing quotes
    public String ReadString() throws IOException {
        // Read the string
        String result = reader.ReadString();
        // Check for null and remove quotes
        if (result == null) {
            throw new IllegalArgumentException("Cannot deserialize String");
        }
        result = result.replaceFirst("^\"", "").replaceFirst("\"$", "");
        return result;
    }

    // Read a JSON text string without removing quotes
    public String ReadTextString() throws IOException {
        // Read the text string
        String result = reader.ReadString();
        // Check for null and return without removing quotes
        if (result != null) {
            result = result.replaceFirst("^\"", "").replaceFirst("\"$", "");
        }
        return result;
    }

    // Read an integer from the JSON
    public int ReadInteger() throws IllegalArgumentException {
        try {
            // Parse the text string as an integer
            return Integer.parseInt(ReadTextString());
        } catch (IOException | NullPointerException exception) {
            throw new IllegalArgumentException("Cannot deserialize Integer");
        }
    }

    // Read a main.MathExpression object from the JSON
    public MathExpression ReadMathExpression() throws IllegalArgumentException {
        try {
            // Read the JSON content into a StringBuilder
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = ReadTextString()) != null) {
                data.append(line);
                if (line.charAt(line.length() - 1) == ',') {
                    data.append("\n");
                }
            }
            data.insert(data.length() - 1, '\n');

            // Define a pattern to extract key-value pairs from the JSON
            String tab = "  ";
            Pattern expression_pattern = Pattern.compile(tab + "\"(.*?)\" : (.*?)(,\n|\n})");
            Matcher matcher = expression_pattern.matcher(data);

            // Map to store key-value pairs
            HashMap<String, String> nodes = new HashMap<>();
            while (matcher.find()) {
                nodes.put(matcher.group(1), matcher.group(2));
            }

            // Extract values from the map and create a main.MathExpression object
            String expression = nodes.get("expression");
            ArrayList<Character> variables = parseCharacterArray(nodes.get("variables"));
            ArrayList<Character> types = parseCharacterArray(nodes.get("types"));
            ArrayList<ImmutablePair<Integer, Integer>> integers = parseIntPairArray(nodes.get("integers"));
            ArrayList<ImmutablePair<Double, Integer>> doubles = parseDoublePairArray(nodes.get("doubles"));

            MathExpression mathExpression = new MathExpression();
            mathExpression.setExpression(expression.substring(1, expression.length() - 1));
            mathExpression.setVariables(variables);
            mathExpression.setTypes(types);
            mathExpression.setIntegers(integers);
            mathExpression.setDoubles(doubles);

            return mathExpression;
        } catch (IOException | NullPointerException exception) {
            throw new IllegalArgumentException("Cannot deserialize math expression");
        }
    }

    // Read a list of main.MathExpression objects from the JSON
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IllegalArgumentException {
        try {
            // Initialize a list to store main.MathExpression objects
            ArrayList<MathExpression> expressions = new ArrayList<>();
            // Initialize a StringBuilder to store JSON content
            StringBuilder data = new StringBuilder();
            String line;
            // Read JSON content line by line
            while ((line = ReadTextString()) != null) {
                if (line.equals("}, {")) {
                    data.append("\n\n");
                }
                data.append(line);
                if (line.charAt(line.length() - 1) == ',') {
                    data.append("\n");
                }
            }
            data.insert(data.length() - 3, "\n");

            // Split JSON content into individual main.MathExpression strings
            String[] mathExpressionsContent = data.substring(3, data.length() - 3).split("\n}, \\{");
            String tab = "  ";
            Pattern expression_pattern = Pattern.compile(tab + "\"(.*?)\" : (.*?)(,\n|\n)");

            // Process each main.MathExpression string
            for (String expressionContent : mathExpressionsContent) {
                Matcher matcher = expression_pattern.matcher(expressionContent);
                HashMap<String, String> nodes = new HashMap<>();
                // Extract key-value pairs for each main.MathExpression
                while (matcher.find()) {
                    nodes.put(matcher.group(1), matcher.group(2));
                }

                // Extract values from the map and create a main.MathExpression object
                String expression = nodes.get("expression");
                ArrayList<Character> variables = parseCharacterArray(nodes.get("variables"));
                ArrayList<Character> types = parseCharacterArray(nodes.get("types"));
                ArrayList<ImmutablePair<Integer, Integer>> integers = parseIntPairArray(nodes.get("integers"));
                ArrayList<ImmutablePair<Double, Integer>> doubles = parseDoublePairArray(nodes.get("doubles"));

                MathExpression mathExpression = new MathExpression();
                mathExpression.setExpression(expression.substring(1, expression.length() - 1));
                mathExpression.setVariables(variables);
                mathExpression.setTypes(types);
                mathExpression.setIntegers(integers);
                mathExpression.setDoubles(doubles);

                expressions.add(mathExpression);
            }

            // Return the list of main.MathExpression objects
            return expressions;
        } catch (IOException | NullPointerException exception) {
            throw new IllegalArgumentException("Cannot deserialize math expression");
        }
    }

    // Helper method to parse a JSON array of characters
    private ArrayList<Character> parseCharacterArray(String content) {
        ArrayList<Character> characters = new ArrayList<>();
        if (!Objects.equals(content, "[ ]")) {
            String[] array = content.substring(2, content.length() - 2)
                    .replaceAll("\"", "").split(", ");
            for (String s : array) {
                characters.add(s.charAt(0));
            }
        }
        return characters;
    }

    // Helper method to parse a JSON array of integer pairs
    private ArrayList<ImmutablePair<Integer, Integer>> parseIntPairArray(String content) {
        ArrayList<ImmutablePair<Integer, Integer>> pairs = new ArrayList<>();
        if (!Objects.equals(content, "[ ]")) {
            String[] array = content.substring(2, content.length() - 2)
                    .replaceAll("\"", "").replaceAll(" ", "")
                    .replaceAll("\\{", "").replaceAll("}", "")
                    .split(",");
            for (String s : array) {
                String[] pairContent = s.split(":");
                pairs.add(new ImmutablePair<>(Integer.parseInt(pairContent[0]), Integer.parseInt(pairContent[1])));
            }
        }
        return pairs;
    }

    // Helper method to parse a JSON array of double pairs
    private ArrayList<ImmutablePair<Double, Integer>> parseDoublePairArray(String content) {
        ArrayList<ImmutablePair<Double, Integer>> pairs = new ArrayList<>();
        if (!Objects.equals(content, "[ ]")) {
            String[] array = content.substring(2, content.length() - 2)
                    .replaceAll("\"", "").replaceAll(" ", "")
                    .replaceAll("\\{", "").replaceAll("}", "")
                    .split(",");
            for (String s : array) {
                String[] pairContent = s.split(":");
                pairs.add(new ImmutablePair<>(Double.parseDouble(pairContent[0]), Integer.parseInt(pairContent[1])));
            }
        }
        return pairs;
    }
}
