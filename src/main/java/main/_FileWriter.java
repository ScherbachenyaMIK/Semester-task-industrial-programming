package main;

import com.udojava.evalex.Expression;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.*;
import java.util.ArrayList;

public class _FileWriter extends TextWriter {

    // FileWriter to write text to the file
    private FileWriter File_;

    // Counter to keep track of the task number
    private int count = 1;

    // Constructor to create a FileWriter with the specified filename
    public _FileWriter(String filename) throws IOException {
        super(filename);
    }

    // Constructor to create a FileWriter with the specified File
    _FileWriter(File EnterFile) throws IOException {
        super(EnterFile.getPath());
    }

    // Method to close the file
    public void CloseFile() throws IOException {
        File_.close();
    }

    // Method to open the file
    public void OpenFile() throws IOException {
        File_ = new FileWriter(filename);
    }

    // Method to write a string to the file
    public void WriteString(String str) throws IOException {
        OpenFile();
        File_.write(str + '\n');
        CloseFile();
    }

    // Method to write an integer to the file
    public void WriteInteger(int i) throws IOException {
        OpenFile();
        File_.write(Integer.valueOf(i).toString() + ' ');
        CloseFile();
    }

    // Method to write a mathematical expression to the file
    public void WriteMathExpression(MathExpression expression) throws IOException {
        OpenFile();
        // Writing task information
        File_.write("Task " + count++ + ":\n");
        File_.write(expression.getExpression() + '\n');

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
            File_.write(var.append('\n').toString());
        }
        CloseFile();
    }

    // Method to write a list of mathematical expressions to the file
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        OpenFile();
        for (MathExpression expression : expressions) {
            // Writing task information
            File_.write("Task " + count++ + ":\n");
            File_.write(expression.getExpression() + '\n');

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
                File_.write(var.append('\n').toString());
            }
        }
        CloseFile();
    }

    // Method to write a result to the file
    public void WriteResult(Result result) throws IOException {
        OpenFile();
        // Writing task information
        File_.write("Task " + count++ + ":\n");
        File_.write(result.getResult() + '\n');
        CloseFile();
    }

    // Method to write a list of results of mathematical expressions to the file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        OpenFile();
        for (MathExpression expression : expressions) {
            try {
                // Writing task information
                File_.write("Task " + count++ + ":\n");
                // Writing the result of the expression
                File_.write(expression.Result(type) + '\n');
            } catch (IOException | Expression.ExpressionException | IllegalArgumentException exception) {
                // Handling exceptions during computation
                File_.write("Error while computing expression!\n");
                File_.write("Original expression:\n");

                File_.write(expression.getExpression() + '\n');

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
                    File_.write(var.append('\n').toString());
                }
            }
        }
        CloseFile();
    }
}
