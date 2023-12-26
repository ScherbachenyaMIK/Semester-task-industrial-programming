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

    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        ArrayList<Result> results = new ArrayList<>();
        for (MathExpression expression : expressions) {
            try {
                results.add(new Result(expression.Result(type)));
            } catch (IOException | NumberFormatException | Expression.ExpressionException exception) {
                results.add(new Result('e'));
            }
        }
        objectMapper.writeValue(File_, results);
    }
}

//TODO сброс потока при каждой новой записи
class JsonNonAPIWriter {
    private BufferedWriter writer;
    JsonNonAPIWriter(String filename) throws IOException {
        File File_ = new File(filename);
        writer = new BufferedWriter(new FileWriter(File_));
    }
    void CloseJsonNonAPIWriter() throws IOException {
        writer.close();
    }
    public void WriteString(String str) throws IOException {
        writer.write("\"" + str + "\"");
    }

    public void WriteInteger(int i) throws IOException {
        writer.write(String.valueOf(i));
    }

    public void WriteMathExpression(MathExpression expression) throws IOException {
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
    }
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        writer.write("[ ");
        for(MathExpression me : expressions)
        {
            WriteMathExpression(me);
        }
        writer.write(" ]");
    }

    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
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
            } catch (IOException | NumberFormatException | Expression.ExpressionException exception) {
                results.add(new Result('e'));
            }
        }
        for (Result result : results) {
            content.append("{\n").append(tab).append("\"result\" : \"").append(result.getResult()).append("\"\n}, ");
        }
        content.setLength(content.length() - 2);
        content.append(" ]");
        writer.write(content.toString());
    }
}