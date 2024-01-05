import lombok.Getter;

import java.io.*;
import java.util.ArrayList;

public class _FileReader {
    @Getter
    private FileReader File_;
    private StringBuilder remainder = new StringBuilder();
    private static final int BUFFER_SIZE = 1024;
    _FileReader(String filename) throws FileNotFoundException {
        File_ = new FileReader(filename);
    }
    _FileReader(File EnterFile) throws FileNotFoundException {
        File_ = new FileReader(EnterFile);
    }
    public void CloseFile() throws IOException {
        File_.close();
    }
    public String ReadString() throws IOException {
        char[] buffer = new char[BUFFER_SIZE];
        int charsRead;

        charsRead = File_.read(buffer);
        if (charsRead == -1) {
            if (remainder.length() == 0) {
                return null;
            }
            String lastLine = remainder.substring(0, remainder.indexOf("\n"));
            remainder = remainder.replace(0, remainder.indexOf("\n") + 1, "");
            return lastLine;
        }
        remainder.append(new String(buffer).substring(0, charsRead) + "\n");
        int i;
        while ((i = remainder.indexOf("\n\n")) != -1) {
            remainder.replace(i, i + 2, "\n");
        }
        String lastLine = remainder.substring(0, remainder.indexOf("\n"));
        remainder = remainder.replace(0, remainder.indexOf("\n") + 1, "");
        return lastLine;
    }
    public int ReadInteger() throws IOException {
        String str = ReadString();
        if (str.endsWith(" ")) {
            str = str.replaceFirst("(.*) $", "$1");
        }
        return Integer.parseInt(str);
    }
    public MathExpression ReadMathExpression() throws IOException {
        String task = ReadString();
        if (task == null) {
            return null;
        }
        if (!task.startsWith("Task")) {
            throw new IllegalArgumentException("The text file is damaged or does not match the format");
        }
        String expression = ReadString();
        if (expression == null)
        {
            throw new IllegalArgumentException("The text file is damaged or does not match the format");
        }
        ArrayList<String> variables = new ArrayList<>();
        String variable;
        while((variable = ReadString()) != null && !variable.startsWith("Task"))
        {
            variables.add(variable);
        }
        if (variable != null) {
            remainder.insert(0, variable + "\n");
        }
        return new MathExpression(expression, variables);
    }
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        ArrayList<MathExpression> expressions = new ArrayList<>();
        MathExpression expression;
        while ((expression = ReadMathExpression()) != null)
        {
            expressions.add(expression);
        }
        return expressions;
    }
}

