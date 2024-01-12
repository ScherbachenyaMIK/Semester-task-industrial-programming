import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.udojava.evalex.Expression;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Implementation of HyperTextWriter using Jackson for JSON writing
public class JsonWriter implements HyperTextWriter {
    private File File_;
    private ObjectMapper objectMapper;

    // Constructor with a filename
    JsonWriter(String filename) {
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

    // Write a MathExpression object to the JSON file
    public void WriteMathExpression(MathExpression expression) throws IOException {
        objectMapper.writeValue(File_, expression);
    }

    // Write a list of MathExpression objects to the JSON file
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        objectMapper.writeValue(File_, expressions);
    }

    // Write a Result object to the JSON file
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

// Implementation of NonAPIHyperTextWriter for writing JSON
class JsonNonAPIWriter implements NonAPIHyperTextWriter {
    private String filename;
    private BufferedWriter writer;

    // Constructor with a filename
    JsonNonAPIWriter(String filename_) throws IOException {
        filename = filename_;
    }

    // Open the writer
    public void OpenWriter() throws IOException {
        File File_ = new File(filename);
        writer = new BufferedWriter(new FileWriter(File_));
    }

    // Close the writer
    public void CloseWriter() throws IOException {
        writer.close();
    }

    // Write a string to the JSON file
    public void WriteString(String str) throws IOException {
        OpenWriter();
        writer.write("\"" + str + "\"");
        CloseWriter();
    }

    // Write an integer to the JSON file
    public void WriteInteger(int i) throws IOException {
        OpenWriter();
        writer.write(String.valueOf(i));
        CloseWriter();
    }

    // Write a MathExpression object to the JSON file
    public void WriteMathExpression(MathExpression expression) throws IOException {
        OpenWriter();
        String tab = "  ";
        StringBuilder content = new StringBuilder("{\n");

        // Write the expression string
        content.append(tab).append("\"expression\" : \"").append(expression.getExpression()).append("\",\n");

        // Write the list of variables
        content.append(tab).append("\"variables\" : [");
        ArrayList<Character> variables = expression.getVariables();
        for (Character c : variables) {
            content.append(" \"").append(c).append("\",");
        }
        if (!variables.isEmpty()) {
            content.deleteCharAt(content.length() - 1);
        }
        content.append(" ],\n");

        // Write the list of types
        content.append(tab).append("\"types\" : [");
        ArrayList<Character> types = expression.getTypes();
        for (Character c : types) {
            content.append(" \"").append(c).append("\",");
        }
        if (!types.isEmpty()) {
            content.deleteCharAt(content.length() - 1);
        }
        content.append(" ],\n");

        // Write the list of integers
        content.append(tab).append("\"integers\" : [");
        ArrayList<ImmutablePair<Integer, Integer>> integers = expression.getIntegers();
        for (ImmutablePair<Integer, Integer> p : integers) {
            content.append(" {\n").append(tab).append(tab).append("\"").append(p.getLeft())
                    .append("\" : ").append(p.getRight()).append("\n").append(tab).append("},");
        }
        if (!integers.isEmpty()) {
            content.deleteCharAt(content.length() - 1);
        }
        content.append(" ],\n");

        // Write the list of doubles
        content.append(tab).append("\"doubles\" : [");
        ArrayList<ImmutablePair<Double, Integer>> doubles = expression.getDoubles();
        for (ImmutablePair<Double, Integer> p : doubles) {
            content.append(" {\n").append(tab).append(tab).append("\"").append(p.getLeft())
                    .append("\" : ").append(p.getRight()).append("\n").append(tab).append("},");
        }
        if (!doubles.isEmpty()) {
            content.deleteCharAt(content.length() - 1);
        }
        content.append(" ]\n}");

        // Write the content to the file
        writer.write(content.toString());
        CloseWriter();
    }

    // Write a list of MathExpression objects to the JSON file
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        OpenWriter();
        String tab = "  ";
        StringBuilder content = new StringBuilder();
        writer.write("[ ");
        for (MathExpression expression : expressions) {
            content.append("{\n");
            content.append(tab).append("\"expression\" : \"").append(expression.getExpression()).append("\",\n");
            content.append(tab).append("\"variables\" : [");
            ArrayList<Character> variables = expression.getVariables();
            for (Character c : variables) {
                content.append(" \"").append(c).append("\",");
            }
            if (!variables.isEmpty()) {
                content.deleteCharAt(content.length() - 1);
            }
            content.append(" ],\n");
            content.append(tab).append("\"types\" : [");
            ArrayList<Character> types = expression.getTypes();
            for (Character c : types) {
                content.append(" \"").append(c).append("\",");
            }
            if (!types.isEmpty()) {
                content.deleteCharAt(content.length() - 1);
            }
            content.append(" ],\n");
            content.append(tab).append("\"integers\" : [");
            ArrayList<ImmutablePair<Integer, Integer>> integers = expression.getIntegers();
            for (ImmutablePair<Integer, Integer> p : integers) {
                content.append(" {\n").append(tab).append(tab).append("\"").append(p.getLeft())
                        .append("\" : ").append(p.getRight()).append("\n").append(tab).append("},");
            }
            if (!integers.isEmpty()) {
                content.deleteCharAt(content.length() - 1);
            }
            content.append(" ],\n");
            content.append(tab).append("\"doubles\" : [");
            ArrayList<ImmutablePair<Double, Integer>> doubles = expression.getDoubles();
            for (ImmutablePair<Double, Integer> p : doubles) {
                content.append(" {\n").append(tab).append(tab).append("\"").append(p.getLeft())
                        .append("\" : ").append(p.getRight()).append("\n").append(tab).append("},");
            }
            if (!doubles.isEmpty()) {
                content.deleteCharAt(content.length() - 1);
            }
            content.append(" ]\n}, ");
        }
        writer.write(content.substring(0, content.length() - 2).toString());
        writer.write(" ]");
        CloseWriter();
    }

    // Write a Result object to the JSON file
    public void WriteResult(Result result) throws IOException {
        OpenWriter();
        String tab = "  ";
        StringBuilder content = new StringBuilder();

        // Write the result string
        content.append("{\n").append(tab).append("\"result\" : \"").append(result.getResult()).append("\"\n}");

        // Write the content to the file
        writer.write(content.toString());
        CloseWriter();
    }

    // Write a list of Results for MathExpressions to the JSON file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        OpenWriter();
        if (expressions.isEmpty()) {
            writer.write("[ ]");
            return;
        }
        String tab = "  ";
        StringBuilder content = new StringBuilder("[ ");
        ArrayList<Result> results = new ArrayList<>();
        for (MathExpression expression : expressions) {
            try {
                results.add(new Result(expression.Result(type)));
            } catch (IOException | Expression.ExpressionException | IllegalArgumentException exception) {
                results.add(new Result('e'));
            }
        }
        for (Result result : results) {
            content.append("{\n").append(tab).append("\"result\" : \"").append(result.getResult()).append("\"\n}, ");
        }
        content.setLength(content.length() - 2);
        content.append(" ]");
        writer.write(content.toString());
        CloseWriter();
    }
}
