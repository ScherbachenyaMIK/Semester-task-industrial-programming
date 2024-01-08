import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.udojava.evalex.Expression;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonWriter {
    private File File_;
    private ObjectMapper objectMapper;
    JsonWriter(String filename)
    {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File_ = new File(filename);
    }
    JsonWriter(File EnterFile)
    {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File_ = EnterFile;
    }
    public void WriteString(String str) throws IOException {
        objectMapper.writeValue(File_, str);
    }
    public void WriteInteger(int i) throws IOException {
        objectMapper.writeValue(File_, i);;
    }
    public void WriteMathExpression(MathExpression expression) throws IOException {
        objectMapper.writeValue(File_, expression);
    }
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        objectMapper.writeValue(File_, expressions);
    }
    public void WriteResult(Result result) throws IOException {
        objectMapper.writeValue(File_, result);
    }
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

class JsonNonAPIWriter {
    private String filename;
    private BufferedWriter writer;
    JsonNonAPIWriter(String filename_) throws IOException {
        filename = filename_;
    }
    private void OpenJsonNonAPIWriter() throws IOException {
        File File_ = new File(filename);
        writer = new BufferedWriter(new FileWriter(File_));
    }
    private void CloseJsonNonAPIWriter() throws IOException {
        writer.close();
    }
    public void WriteString(String str) throws IOException {
        OpenJsonNonAPIWriter();
        writer.write("\"" + str + "\"");
        CloseJsonNonAPIWriter();
    }

    public void WriteInteger(int i) throws IOException {
        OpenJsonNonAPIWriter();
        writer.write(String.valueOf(i));
        CloseJsonNonAPIWriter();
    }

    public void WriteMathExpression(MathExpression expression) throws IOException {
        OpenJsonNonAPIWriter();
        String tab = "  ";
        StringBuilder content = new StringBuilder("{\n");
        content.append(tab).append("\"expression\" : \"").append(expression.getExpression()).append("\",\n");
        content.append(tab).append("\"variables\" : [");
        ArrayList<Character> variables = expression.getVariables();
        for(Character c : variables)
        {
            content.append(" \"").append(c).append("\",");
        }
        if (!variables.isEmpty()) {
            content.deleteCharAt(content.length() - 1);
        }
        content.append(" ],\n");
        content.append(tab).append("\"types\" : [");
        ArrayList<Character> types = expression.getTypes();
        for(Character c : types)
        {
            content.append(" \"").append(c).append("\",");
        }
        if (!types.isEmpty()) {
            content.deleteCharAt(content.length() - 1);
        }
        content.append(" ],\n");
        content.append(tab).append("\"integers\" : [");
        ArrayList<ImmutablePair<Integer, Integer>> integers = expression.getIntegers();
        for(ImmutablePair<Integer, Integer> p : integers)
        {
            content.append(" {\n").append(tab).append(tab).append("\"").append(p.getLeft())
                    .append("\" : ").append(p.getRight()).append("\n").append(tab).append("},");
        }
        if (!integers.isEmpty()) {
            content.deleteCharAt(content.length() - 1);
        }
        content.append(" ],\n");
        content.append(tab).append("\"doubles\" : [");
        ArrayList<ImmutablePair<Double, Integer>> doubles = expression.getDoubles();
        for(ImmutablePair<Double, Integer> p : doubles)
        {
            content.append(" {\n").append(tab).append(tab).append("\"").append(p.getLeft())
                    .append("\" : ").append(p.getRight()).append("\n").append(tab).append("},");
        }
        if (!doubles.isEmpty()) {
            content.deleteCharAt(content.length() - 1);
        }
        content.append(" ]\n}");
        writer.write(content.toString());
        CloseJsonNonAPIWriter();
    }
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        OpenJsonNonAPIWriter();
        String tab = "  ";
        StringBuilder content = new StringBuilder();
        writer.write("[ ");
        for(MathExpression expression : expressions)
        {
            content.append("{\n");
            content.append(tab).append("\"expression\" : \"").append(expression.getExpression()).append("\",\n");
            content.append(tab).append("\"variables\" : [");
            ArrayList<Character> variables = expression.getVariables();
            for(Character c : variables)
            {
                content.append(" \"").append(c).append("\",");
            }
            if (!variables.isEmpty()) {
                content.deleteCharAt(content.length() - 1);
            }
            content.append(" ],\n");
            content.append(tab).append("\"types\" : [");
            ArrayList<Character> types = expression.getTypes();
            for(Character c : types)
            {
                content.append(" \"").append(c).append("\",");
            }
            if (!types.isEmpty()) {
                content.deleteCharAt(content.length() - 1);
            }
            content.append(" ],\n");
            content.append(tab).append("\"integers\" : [");
            ArrayList<ImmutablePair<Integer, Integer>> integers = expression.getIntegers();
            for(ImmutablePair<Integer, Integer> p : integers)
            {
                content.append(" {\n").append(tab).append(tab).append("\"").append(p.getLeft())
                        .append("\" : ").append(p.getRight()).append("\n").append(tab).append("},");
            }
            if (!integers.isEmpty()) {
                content.deleteCharAt(content.length() - 1);
            }
            content.append(" ],\n");
            content.append(tab).append("\"doubles\" : [");
            ArrayList<ImmutablePair<Double, Integer>> doubles = expression.getDoubles();
            for(ImmutablePair<Double, Integer> p : doubles)
            {
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
        CloseJsonNonAPIWriter();
    }
    public void WriteResult(Result result) throws IOException {
        OpenJsonNonAPIWriter();
        String tab = "  ";
        StringBuilder content = new StringBuilder();
        content.append("{\n").append(tab).append("\"result\" : \"").append(result.getResult()).append("\"\n}");
        writer.write(content.toString());
        CloseJsonNonAPIWriter();
    }
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        OpenJsonNonAPIWriter();
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
        CloseJsonNonAPIWriter();
    }
}