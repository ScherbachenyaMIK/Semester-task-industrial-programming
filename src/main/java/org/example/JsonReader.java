package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonReader {
    private File File_;
    private ObjectMapper objectMapper;
    JsonReader(String filename)
    {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MathExpression.class, new MathExpressionDeserializer());
        objectMapper.registerModule(module);
        File_ = new File(filename);
    }
    JsonReader(File EnterFile)
    {
        objectMapper = new ObjectMapper();
        File_ = EnterFile;
    }
    public String ReadString() throws IOException {
        String str = new String();
        return objectMapper.readValue(File_, str.getClass());
    }
    public int ReadInteger() throws IOException {
        return objectMapper.readValue(File_, Integer.class);
    }
    public MathExpression ReadMathExpression() throws IOException {
        return objectMapper.readValue(File_, MathExpression.class);
    }
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        return objectMapper.readValue(File_, new TypeReference<ArrayList<MathExpression>>() {});
    }
}

class MathExpressionDeserializer extends StdDeserializer<MathExpression> {

    public MathExpressionDeserializer() {
        this(null);
    }

    public MathExpressionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MathExpression deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        MathExpression mathExpression = new MathExpression();
        mathExpression.setExpression(node.get("expression").asText());

        JsonNode variablesNode = node.get("variables");
        ArrayList<Character> variables = new ArrayList<>();
        for (JsonNode varNode : variablesNode) {
            variables.add(varNode.asText().charAt(0));
        }
        mathExpression.setVariables(variables);

        JsonNode typesNode = node.get("types");
        ArrayList<Character> types = new ArrayList<>();
        for (JsonNode varNode : typesNode) {
            types.add(varNode.asText().charAt(0));
        }
        mathExpression.setTypes(types);

        JsonNode integersNode = node.get("integers");
        ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
        for (JsonNode varNode : integersNode) {
            String left = varNode.fieldNames().next();
            integers.add(new ImmutablePair<>(Integer.parseInt(left), varNode.get(left).asInt()));
        }
        mathExpression.setIntegers(integers);

        JsonNode doublesNode = node.get("doubles");
        ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
        for (JsonNode varNode : doublesNode) {
            String left = varNode.fieldNames().next();
            doubles.add(new ImmutablePair<>(Double.parseDouble(left), varNode.get(left).asInt()));
        }
        mathExpression.setDoubles(doubles);
        return mathExpression;
    }
}

class JsonNonAPIReader {
    private _BufferedFileReader reader;
    JsonNonAPIReader(String filename) throws FileNotFoundException {
        File File_ = new File(filename);
        reader = new _BufferedFileReader(new _FileReader(File_));
    }
    public void CloseJsonNonAPIReader() throws IOException {
        reader.CloseFile();
    }
    public String ReadString() throws IOException {
        return reader.ReadString();
    }
    public int ReadInteger() throws IOException {
        return Integer.parseInt(ReadString());
    }
    public MathExpression ReadMathExpression() throws IOException {
        StringBuilder data = new StringBuilder();
        String line;
        while ((line = ReadString()) != null)
        {
            data.append(line);
            if (line.charAt(line.length() - 1) == ',')
            {
                data.append("\n");
            }
        }
        data.insert(data.length() - 1, '\n');
        String tab = "  ";
        Pattern expression_pattern = Pattern.compile(tab + "\"(.*?)\" : (.*?)(,\n|\n})");
        Matcher matcher = expression_pattern.matcher(data);
        HashMap<String, String> nodes = new HashMap<>();
        while (matcher.find())
        {
            nodes.put(matcher.group(1), matcher.group(2));
        }
        String expression = nodes.get("expression");
        ArrayList<Character> variables = new ArrayList<>();
        String variablesContent = nodes.get("variables");
        String[] variablesArray = variablesContent.substring(2, variablesContent.length() - 2)
                .replaceAll("\"", "").split(", ");
        for(String s : variablesArray)
        {
            variables.add(s.charAt(0));
        }
        ArrayList<Character> types = new ArrayList<>();
        String typesContent = nodes.get("types");
        String[] typesArray = typesContent.substring(2, typesContent.length() - 2)
                .replaceAll("\"", "").split(", ");
        for(String s : typesArray)
        {
            types.add(s.charAt(0));
        }
        ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
        String integersContent = nodes.get("integers");
        String[] integersArray = integersContent.substring(2, integersContent.length() - 2)
                .replaceAll("\"", "").replaceAll(" ", "")
                .replaceAll("\\{", "").replaceAll("}", "")
                .split(",");
        for(String s : integersArray)
        {
            String[] pairContent = s.split(":");
            integers.add(new ImmutablePair<>(Integer.parseInt(pairContent[0]), Integer.parseInt(pairContent[1])));
        }
        ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
        String doublesContent = nodes.get("doubles");
        String[] doublesArray = doublesContent.substring(2, doublesContent.length() - 2)
                .replaceAll("\"", "").replaceAll(" ", "")
                .replaceAll("\\{", "").replaceAll("}", "")
                .split(",");
        for(String s : doublesArray)
        {
            String[] pairContent = s.split(":");
            doubles.add(new ImmutablePair<>(Double.parseDouble(pairContent[0]), Integer.parseInt(pairContent[1])));
        }

        MathExpression mathExpression = new MathExpression();
        mathExpression.setExpression(expression.substring(1, expression.length() - 1));
        mathExpression.setVariables(variables);
        mathExpression.setTypes(types);
        mathExpression.setIntegers(integers);
        mathExpression.setDoubles(doubles);
        return mathExpression;
    }
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        ArrayList<MathExpression> expressions = new ArrayList<>();
        StringBuilder data = new StringBuilder();
        String line;
        while ((line = ReadString()) != null)
        {
            if (line.equals("}, {"))
            {
                data.append("\n\n");
            }
            data.append(line);
            if (line.charAt(line.length() - 1) == ',')
            {
                data.append("\n");
            }
        }
        data.insert(data.length() - 3, "\n");
        String[] mathExpressionsContent = data.substring(3, data.length() - 3).split("\n}, \\{");
        String tab = "  ";
        Pattern expression_pattern = Pattern.compile(tab + "\"(.*?)\" : (.*?)(,\n|\n)");
        for (String expressionContent : mathExpressionsContent)
        {
            Matcher matcher = expression_pattern.matcher(expressionContent);
            HashMap<String, String> nodes = new HashMap<>();
            while (matcher.find()) {
                nodes.put(matcher.group(1), matcher.group(2));
            }
            String expression = nodes.get("expression");
            ArrayList<Character> variables = new ArrayList<>();
            String variablesContent = nodes.get("variables");
            if (!Objects.equals(variablesContent, "[ ]"))
            {
                String[] variablesArray = variablesContent.substring(2, variablesContent.length() - 2)
                        .replaceAll("\"", "").split(", ");
                for (String s : variablesArray) {
                    variables.add(s.charAt(0));
                }
            }
            ArrayList<Character> types = new ArrayList<>();
            String typesContent = nodes.get("types");
            if (!Objects.equals(typesContent, "[ ]")) {
                String[] typesArray = typesContent.substring(2, typesContent.length() - 2)
                        .replaceAll("\"", "").split(", ");
                for (String s : typesArray) {
                    types.add(s.charAt(0));
                }
            }
            ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
            String integersContent = nodes.get("integers");
            if (!Objects.equals(integersContent, "[ ]")) {
                String[] integersArray = integersContent.substring(2, integersContent.length() - 2)
                        .replaceAll("\"", "").replaceAll(" ", "")
                        .replaceAll("\\{", "").replaceAll("}", "")
                        .split(",");
                for (String s : integersArray) {
                    String[] pairContent = s.split(":");
                    integers.add(new ImmutablePair<>(Integer.parseInt(pairContent[0]), Integer.parseInt(pairContent[1])));
                }
            }
            ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
            String doublesContent = nodes.get("doubles");
            if (!Objects.equals(doublesContent, "[ ]")) {
                String[] doublesArray = doublesContent.substring(2, doublesContent.length() - 2)
                        .replaceAll("\"", "").replaceAll(" ", "")
                        .replaceAll("\\{", "").replaceAll("}", "")
                        .split(",");
                for (String s : doublesArray) {
                    String[] pairContent = s.split(":");
                    doubles.add(new ImmutablePair<>(Double.parseDouble(pairContent[0]), Integer.parseInt(pairContent[1])));
                }
            }

            MathExpression mathExpression = new MathExpression();
            mathExpression.setExpression(expression.substring(1, expression.length() - 1));
            mathExpression.setVariables(variables);
            mathExpression.setTypes(types);
            mathExpression.setIntegers(integers);
            mathExpression.setDoubles(doubles);
            expressions.add(mathExpression);
        }
        return expressions;
    }
}