package main;

import com.udojava.evalex.Expression;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.*;
import java.util.ArrayList;

public class _FileWriter implements TextWriter {

    // FileWriter to write text to the file
    private FileWriter File_;

    // Counter to keep track of the task number
    private int count = 1;

    // Constructor to create a FileWriter with the specified filename
    public _FileWriter(String filename) throws IOException {
        File_ = new FileWriter(filename);
    }

    // Constructor to create a FileWriter with the specified File
    _FileWriter(File EnterFile) throws IOException {
        File_ = new FileWriter(EnterFile);
    }

    // Method to close the file
    public void CloseFile() throws IOException {
        File_.close();
    }

    // Method to write a string to the file
    public void WriteString(String str) throws IOException {
        File_.write(str + '\n');
    }

    // Method to write an integer to the file
    public void WriteInteger(int i) throws IOException {
        File_.write(Integer.valueOf(i).toString() + ' ');
    }

    // Method to write a mathematical expression to the file
    public void WriteMathExpression(MathExpression expression) throws IOException {
        // Writing task information
        WriteString("Task " + count++ + ":");
        WriteString(expression.getExpression());

        // Retrieving expression details
        ArrayList<Character> variables = expression.getVariables();
        ArrayList<Character> types = expression.getTypes();
        ArrayList<ImmutablePair<Integer, Integer>> integers = expression.getIntegers();
        ArrayList<ImmutablePair<Double, Integer>> doubles = expression.getDoubles();

        // Writing variable assignments
        for (int i = 0; i < variables.size(); ++i) {
            StringBuilder var = new StringBuilder(variables.get(i) + " = ");
            if (types.get(i) == 'i') {
                for (ImmutablePair<Integer, Integer> j : integers) {
                    if (j.getRight() == i) {
                        var.append(j.getLeft());
                    }
                }
            } else {
                for (ImmutablePair<Double, Integer> j : doubles) {
                    if (j.getRight() == i) {
                        var.append(j.getLeft());
                    }
                }
            }
            WriteString(var.toString());
        }
    }

    // Method to write a list of mathematical expressions to the file
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        for (MathExpression expression : expressions) {
            WriteMathExpression(expression);
        }
    }

    // Method to write a result to the file
    public void WriteResult(Result result) throws IOException {
        // Writing task information
        WriteString("Task " + count++ + ":");
        WriteString(result.getResult());
    }

    // Method to write a list of results of mathematical expressions to the file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        for (MathExpression expression : expressions) {
            try {
                // Writing task information
                WriteString("Task " + count++ + ":");
                // Writing the result of the expression
                WriteString(expression.Result(type));
            } catch (IOException | Expression.ExpressionException | IllegalArgumentException exception) {
                // Handling exceptions during computation
                WriteString("Error while computing expression!");
                WriteString("Original expression:");
                --count;
                WriteMathExpression(expression);
            }
        }
    }
}
