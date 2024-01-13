import lombok.Getter;

import java.io.*;
import java.util.ArrayList;

public class _FileReader implements TextReader {

    // FileReader to read characters from a file
    @Getter
    private FileReader File_;

    // StringBuilder to store remaining characters after reading from the file
    private StringBuilder remainder = new StringBuilder();

    // Size of the buffer used for reading characters
    private static final int BUFFER_SIZE = 1024;

    // Constructor that takes a filename as a parameter
    _FileReader(String filename) throws FileNotFoundException {
        // Creating a FileReader for the specified filename
        File_ = new FileReader(filename);
    }

    // Constructor that takes a File as a parameter
    _FileReader(File EnterFile) throws FileNotFoundException {
        // Creating a FileReader for the specified File
        File_ = new FileReader(EnterFile);
    }

    // Method to close the file
    public void CloseFile() throws IOException {
        File_.close();
    }

    // Method to read a single line from the file
    public String ReadString() throws IOException {
        char[] buffer = new char[BUFFER_SIZE];
        int charsRead;

        // Reading characters into the buffer
        charsRead = File_.read(buffer);

        // Handling end-of-file condition
        if (charsRead == -1) {
            if (remainder.length() == 0) {
                return null;
            }

            // Extracting the last line from the remainder
            String lastLine = remainder.substring(0, remainder.indexOf("\n"));

            // Removing the extracted line from the remainder
            remainder = remainder.replace(0, remainder.indexOf("\n") + 1, "");
            return lastLine.replaceAll("\\r", "");
        }

        // Appending the newly read characters to the remainder
        remainder.append(new String(buffer).substring(0, charsRead) + "\n");

        // Removing double newline occurrences in the remainder
        int i;
        while ((i = remainder.indexOf("\n\n")) != -1) {
            remainder.replace(i, i + 2, "\n");
        }

        // Extracting the first line from the remainder
        String lastLine = remainder.substring(0, remainder.indexOf("\n"));

        // Removing the extracted line from the remainder
        remainder = remainder.replace(0, remainder.indexOf("\n") + 1, "");

        return lastLine.replaceAll("\\r", "");
    }

    // Method to read an integer from the file
    public int ReadInteger() throws IOException {
        String str = ReadString();

        // Removing trailing spaces and parsing the string into an integer
        if (str.endsWith(" ")) {
            str = str.replaceFirst("(.*) $", "$1");
        }
        return Integer.parseInt(str);
    }

    // Method to read a mathematical expression from the file
    public MathExpression ReadMathExpression() throws IOException {
        // Reading the task line, which should start with "Task"
        String task = ReadString();
        if (task == null) {
            return null;
        }
        // Checking if the task line starts with "Task"
        if (!task.startsWith("Task")) {
            throw new IllegalArgumentException("The text file is damaged or does not match the format");
        }

        // Reading the expression line
        String expression = ReadString();
        if (expression == null) {
            throw new IllegalArgumentException("The text file is damaged or does not match the format");
        }

        // ArrayList to store variables
        ArrayList<String> variables = new ArrayList<>();
        String variable;

        // Reading variables until a line starts with "Task" or the end of the file is reached
        while ((variable = ReadString()) != null && !variable.startsWith("Task")) {
            // Marking the current position for possible reset
            variables.add(variable);
        }

        // If the last read line is not null, insert it at the beginning of the remainder
        if (variable != null) {
            remainder.insert(0, variable + "\n");
        }

        // Creating a new MathExpression object with the read expression and variables
        return new MathExpression(expression, variables);
    }

    // Method to read a list of mathematical expressions from the file
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        // ArrayList to store read expressions
        ArrayList<MathExpression> expressions = new ArrayList<>();
        MathExpression expression;

        // Reading expressions until the end of the file is reached
        while ((expression = ReadMathExpression()) != null) {
            expressions.add(expression);
        }

        return expressions;
    }
}
