package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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