import java.io.*;
import java.util.ArrayList;

public class _BufferedFileReader implements TextReader {

    // BufferedReader to read text from the file
    private BufferedReader File_;

    // Constructor that takes a _FileReader as a parameter
    _BufferedFileReader(_FileReader fileReader) throws FileNotFoundException {
        // Creating a BufferedReader using the _FileReader's FileReader
        File_ = new BufferedReader(fileReader.getFile_());
    }

    // Constructor that takes an InputStream and a charsetName as parameters
    _BufferedFileReader(InputStream fileReader, String charsetName) throws UnsupportedEncodingException {
        // Creating a BufferedReader using an InputStreamReader with the specified charsetName
        File_ = new BufferedReader(new InputStreamReader(fileReader, charsetName));
    }

    // Method to close the file
    public void CloseFile() throws IOException {
        File_.close();
    }

    // Method to read a single line from the file
    public String ReadString() throws IOException {
        String str;
        // Reading a line from the file
        str = File_.readLine();
        return str;
    }

    // Method to read an integer from the file
    public int ReadInteger() throws IOException {
        String str;
        // Reading a line from the file
        str = File_.readLine();
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
        File_.mark(1024);
        // Reading variables until a line starts with "Task" or the end of the file is reached
        while ((variable = ReadString()) != null && !variable.startsWith("Task")) {
            // Marking the current position for possible reset
            File_.mark(1024);
            variables.add(variable);
        }

        // If the last read line is not null, reset the BufferedReader to the marked position
        if (variable != null) {
            File_.reset();
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
