package main;

import lombok.Getter;

import java.io.*;
import java.util.ArrayList;

public class _BufferedFileReader extends TextReader {
    // BufferedReader to read text from the file
    private BufferedReader bufferedReader;

    // Constructor that takes a main._FileReader as a parameter
    public _BufferedFileReader(_FileReader fileReader) throws FileNotFoundException {
        super(fileReader.getFilename());
        // Creating a BufferedReader using the filename
        bufferedReader = new BufferedReader(new FileReader(filename));
    }

    // Method to close the file
    public void CloseFile() throws IOException {
        bufferedReader.close();
    }

    // Method to read a single line from the file
    public String ReadString() throws IOException {
        String str;
        // Reading a line from the file
        str = bufferedReader.readLine();
        return str;
    }

    // Method to read an integer from the file
    public int ReadInteger() throws IOException {
        String str;
        // Reading a line from the file
        str = bufferedReader.readLine();
        // Parsing the read string into an integer
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

        // Marking the current position in the BufferedReader
        bufferedReader.mark(1024);
        // Reading variables until a line starts with "Task" or the end of the file is reached
        while ((variable = ReadString()) != null && !variable.startsWith("Task")) {
            // Marking the current position for possible reset
            bufferedReader.mark(1024);
            variables.add(variable);
        }

        // If the last read line is not null, reset the BufferedReader to the marked position
        if (variable != null) {
            bufferedReader.reset();
        }

        // Creating a new main.MathExpression object with the read expression and variables
        return new MathExpression(expression, variables);
    }

    // Method to read a list of mathematical expressions from the file
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        // ArrayList to store read expressions
        ArrayList<MathExpression> expressions = new ArrayList<>();
        MathExpression expression;

        try {
            // Reading expressions until the end of the file is reached
            while ((expression = ReadMathExpression()) != null) {
                expressions.add(expression);
            }
        }
        catch (IOException exception) {
            CloseFile();
            throw new IOException(exception);
        }
        catch (IllegalArgumentException exception) {
            CloseFile();
            throw new IllegalArgumentException(exception);
        }
        CloseFile();
        return expressions;
    }

    @Override
    protected void setFilename(String filename_) {
        filename = filename_;
        // Creating a BufferedReader using the filename
        try {
            bufferedReader.close();
            bufferedReader = new BufferedReader(new FileReader(filename));
        } catch (IOException e) {

        }
    }
}
