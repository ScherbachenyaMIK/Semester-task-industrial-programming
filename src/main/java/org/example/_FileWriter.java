package org.example;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.*;
import java.util.ArrayList;

public class _FileWriter {
    private FileWriter File_;
    private int count = 1;
    _FileWriter(String filename) throws IOException {
        File_ = new FileWriter(filename);
    }
    _FileWriter(File EnterFile) throws IOException {
        File_ = new FileWriter(EnterFile);
    }
    public void CloseFile() throws IOException {
        File_.close();
    }
    public void WriteString(String str) throws IOException {
        File_.write(str + '\n');
    }
    public void WriteInteger(int i) throws IOException {
        File_.write(Integer.valueOf(i).toString() + ' ');
    }
    public void WriteMathExpression(MathExpression expression) throws IOException {
        WriteString("Task " + count++ + ": ");
        WriteString(expression.getExpression());
        ArrayList<Character> variables = expression.getVariables();
        ArrayList<Character> types = expression.getTypes();
        ArrayList<ImmutablePair<Integer, Integer>> integers = expression.getIntegers();
        ArrayList<ImmutablePair<Double, Integer>> doubles = expression.getDoubles();
        for (int i = 0; i < variables.size(); ++i) {
            StringBuilder var = new StringBuilder(variables.get(i) + " = ");
            if (types.get(i) == 'i') {
                for (ImmutablePair<Integer, Integer> j : integers) {
                    if (j.getRight() == i) {
                        var.append(j.getLeft());
                    }
                }
            }
            else {
                for (ImmutablePair<Double, Integer> j : doubles) {
                    if (j.getRight() == i) {
                        var.append(j.getLeft());
                    }
                }
            }
            WriteString(var.toString());
        }
    }
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        for(MathExpression expression : expressions)
        {
            WriteMathExpression(expression);
        }
    }
}
