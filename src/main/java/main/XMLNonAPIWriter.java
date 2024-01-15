package main;

import com.udojava.evalex.Expression;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XMLNonAPIWriter extends NonAPIHyperTextWriter {
    private BufferedWriter writer;

    // Constructor to set the filename
    public XMLNonAPIWriter(String filename_) throws IOException {
        super(filename_);
    }

    // Close the XML writer by writing the closing tag and closing the file
    public void CloseWriter() throws IOException {
        writer.write("</Content>\n");
        writer.close();
    }

    // Open the XML writer by creating the file and writing the opening tags
    public void OpenWriter() throws IOException {
        File File_ = new File(filename);
        writer = new BufferedWriter(new FileWriter(File_));
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Content>\n");
    }

    // Write a string to the XML file
    public void WriteString(String str) throws IOException {
        OpenWriter();
        writer.write("  <Data>" + str + "</Data>\n");
        CloseWriter();
    }

    // Write an integer to the XML file
    public void WriteInteger(int i) throws IOException {
        OpenWriter();
        writer.write("  <Data>" + i + "</Data>\n");
        CloseWriter();
    }

    // Write a main.MathExpression to the XML file
    public void WriteMathExpression(MathExpression expression) throws IOException {
        OpenWriter();
        StringBuilder content = new StringBuilder();
        content.append("  <main.MathExpression>\n")
                .append("    <expression>").append(expression.getExpression()).append("</expression>\n")
                .append("    <variables>");

        // Format and write variables
        String str = String.join("\"\"", expression.getVariables().toString().split(", "));
        str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
        content.append(str).append("</variables>\n").append("    <types>");

        // Format and write types
        str = String.join("\"\"", expression.getTypes().toString().split(", "));
        str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
        content.append(str).append("</types>\n").append("    <integers>");

        // Format and write integers
        str = String.join("\"\"", expression.getIntegers().toString().split(", "));
        str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
        content.append(str).append("</integers>\n").append("    <doubles>");

        // Format and write doubles
        str = String.join("\"\"", expression.getDoubles().toString().split(", "));
        str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
        content.append(str).append("</doubles>\n")
                .append("  </main.MathExpression>\n");
        writer.write(content.toString());
        CloseWriter();
    }

    // Write a list of MathExpressions to the XML file
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        OpenWriter();
        for (MathExpression expression : expressions) {
            StringBuilder content = new StringBuilder();
            content.append("  <main.MathExpression>\n")
                    .append("    <expression>").append(expression.getExpression()).append("</expression>\n")
                    .append("    <variables>");

            // Format and write variables
            String str = String.join("\"\"", expression.getVariables().toString().split(", "));
            str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
            content.append(str).append("</variables>\n").append("    <types>");

            // Format and write types
            str = String.join("\"\"", expression.getTypes().toString().split(", "));
            str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
            content.append(str).append("</types>\n").append("    <integers>");

            // Format and write integers
            str = String.join("\"\"", expression.getIntegers().toString().split(", "));
            str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
            content.append(str).append("</integers>\n").append("    <doubles>");

            // Format and write doubles
            str = String.join("\"\"", expression.getDoubles().toString().split(", "));
            str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
            content.append(str).append("</doubles>\n")
                    .append("  </main.MathExpression>\n");
            writer.write(content.toString());
        }
        CloseWriter();
    }

    // Write a main.Result to the XML file
    public void WriteResult(Result result) throws IOException {
        OpenWriter();
        StringBuilder content = new StringBuilder();
        content.append("  <main.Result>\n")
                .append("    <result>").append(result.getResult()).append("</result>\n")
                .append("  </main.Result>\n");
        writer.write(content.toString());
        CloseWriter();
    }

    // Write a list of Results of MathExpressions to the XML file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        ArrayList<Result> results = new ArrayList<>();
        for (MathExpression expression : expressions) {
            try {
                results.add(new Result(expression.Result(type)));
            } catch (IOException | Expression.ExpressionException | IllegalArgumentException exception) {
                results.add(new Result('e'));
            }
        }
        OpenWriter();
        for (Result result : results) {
            StringBuilder content = new StringBuilder();
            content.append("  <main.Result>\n")
                    .append("    <result>").append(result.getResult()).append("</result>\n")
                    .append("  </main.Result>\n");
            writer.write(content.toString());
        }
        CloseWriter();
    }
}
