package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.udojava.evalex.Expression;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Implementation of main.HyperTextWriter using Jackson for JSON writing
public class JsonWriter implements HyperTextWriter {
    private File File_;
    private ObjectMapper objectMapper;

    // Constructor with a filename
    public JsonWriter(String filename) {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File_ = new File(filename);
    }

    // Constructor with a File object
    JsonWriter(File EnterFile) {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File_ = EnterFile;
    }

    // Write a string to the JSON file
    public void WriteString(String str) throws IOException {
        objectMapper.writeValue(File_, str);
    }

    // Write an integer to the JSON file
    public void WriteInteger(int i) throws IOException {
        objectMapper.writeValue(File_, i);
    }

    // Write a main.MathExpression object to the JSON file
    public void WriteMathExpression(MathExpression expression) throws IOException {
        objectMapper.writeValue(File_, expression);
    }

    // Write a list of main.MathExpression objects to the JSON file
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        objectMapper.writeValue(File_, expressions);
    }

    // Write a main.Result object to the JSON file
    public void WriteResult(Result result) throws IOException {
        objectMapper.writeValue(File_, result);
    }

    // Write a list of Results for MathExpressions to the JSON file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        ArrayList<Result> results = new ArrayList<>();
        for (MathExpression expression : expressions) {
            try {
                results.add(new Result(expression.Result(type)));
            } catch (IOException | Expression.ExpressionException | IllegalArgumentException exception) {
                results.add(new Result('e'));
            }
        }
        objectMapper.writeValue(File_, results);
    }
}

