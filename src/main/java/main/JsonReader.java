package main;

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

public class JsonReader extends APIHyperTextReader {
    private File File_;
    private ObjectMapper objectMapper;

    // Constructor taking filename as input
    public JsonReader(String filename) {
        super(filename);
        // Initialize ObjectMapper and register main.MathExpressionDeserializer
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MathExpression.class, new MathExpressionDeserializer());
        objectMapper.registerModule(module);
        // Set the file for reading
        File_ = new File(filename);
    }

    // Constructor taking File as input
    JsonReader(File EnterFile) {
        super(EnterFile.getPath());
        // Initialize ObjectMapper
        objectMapper = new ObjectMapper();
        // Set the file for reading
        File_ = EnterFile;
    }

    // Method to read a String from JSON
    public String ReadString() throws IllegalArgumentException, FileNotFoundException {
        try {
            return objectMapper.readValue(File_, String.class);
        } catch (FileNotFoundException exception) {
            throw new FileNotFoundException(exception.getMessage());
        } catch (IOException | NullPointerException exception) {
            throw new IllegalArgumentException("Cannot deserialize String");
        }
    }

    // Method to read an Integer from JSON
    public int ReadInteger() throws IllegalArgumentException, FileNotFoundException {
        try {
            return objectMapper.readValue(File_, Integer.class);
        } catch (FileNotFoundException exception) {
            throw new FileNotFoundException(exception.getMessage());
        } catch (IOException | NullPointerException exception) {
            throw new IllegalArgumentException("Cannot deserialize Integer");
        }
    }

    // Method to read a main.MathExpression from JSON
    public MathExpression ReadMathExpression() throws IllegalArgumentException, FileNotFoundException {
        try {
            return objectMapper.readValue(File_, MathExpression.class);
        } catch (FileNotFoundException exception) {
            throw new FileNotFoundException(exception.getMessage());
        } catch (IOException | NullPointerException exception) {
            throw new IllegalArgumentException("Cannot deserialize math expression");
        }
    }

    // Method to read a list of MathExpressions from JSON
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IllegalArgumentException, FileNotFoundException {
        try {
            return objectMapper.readValue(File_, new TypeReference<ArrayList<MathExpression>>() {
            });
        } catch (FileNotFoundException exception) {
            throw new FileNotFoundException(exception.getMessage());
        } catch (IOException | NullPointerException exception) {
            throw new IllegalArgumentException("Cannot deserialize list of math expressions");
        }
    }

    @Override
    protected void setFilename(String filename_) {
        filename = filename_;
        // Creating a FileReader using the filename
        File_ = new File(filename);
    }
}

// Custom deserializer for main.MathExpression class
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
        // Read the JSON node
        JsonNode node = jp.getCodec().readTree(jp);

        // Create main.MathExpression object
        MathExpression mathExpression = new MathExpression();
        mathExpression.setExpression(node.get("expression").asText());

        // Read variables from JSON node
        JsonNode variablesNode = node.get("variables");
        ArrayList<Character> variables = new ArrayList<>();
        for (JsonNode varNode : variablesNode) {
            variables.add(varNode.asText().charAt(0));
        }
        mathExpression.setVariables(variables);

        // Read types from JSON node
        JsonNode typesNode = node.get("types");
        ArrayList<Character> types = new ArrayList<>();
        for (JsonNode varNode : typesNode) {
            types.add(varNode.asText().charAt(0));
        }
        mathExpression.setTypes(types);

        // Read integers from JSON node
        JsonNode integersNode = node.get("integers");
        ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
        for (JsonNode varNode : integersNode) {
            String left = varNode.fieldNames().next();
            integers.add(new ImmutablePair<>(Integer.parseInt(left), varNode.get(left).asInt()));
        }
        mathExpression.setIntegers(integers);

        // Read doubles from JSON node
        JsonNode doublesNode = node.get("doubles");
        ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
        for (JsonNode varNode : doublesNode) {
            String left = varNode.fieldNames().next();
            doubles.add(new ImmutablePair<>(Double.parseDouble(left), varNode.get(left).asInt()));
        }
        mathExpression.setDoubles(doubles);

        // Return the main.MathExpression object
        return mathExpression;
    }
}

