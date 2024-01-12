import java.io.IOException;
import java.util.ArrayList;

// Common interface for all writers
public interface _Writer {
    // Method to write a string
    void WriteString(String data) throws IOException;

    // Method to write an integer
    void WriteInteger(int data) throws IOException;

    // Method to write a mathematical expression
    void WriteMathExpression(MathExpression expression) throws IOException;

    // Method to write a list of mathematical expressions
    void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException;

    // Method to write a result of mathematical expressions
    void WriteResult(Result result) throws IOException;

    // Method to write a list of results of mathematical expressions
    void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException;
}

// Interface for writers of text data
interface TextWriter extends _Writer {
    // Method to free resources after writing
    void CloseFile() throws IOException;
}

// Interface for writers of hyper-text data
interface HyperTextWriter extends _Writer {

}

// Interface for writers of hyper-text data through an API
interface APIHyperTextWriter extends HyperTextWriter {
}

// Interface for writers of hyper-text data not using an API
interface NonAPIHyperTextWriter extends HyperTextWriter {
    // Method to free resources after writing
    void CloseWriter() throws IOException;

    //Method to lock a file
    void OpenWriter() throws IOException;
}

/*
Whole inheritance diagram:
                                    ___________
                                    | _Writer |
                                    -----------
                                         |
                   |----------------------------------------------------|
                   |                                                    |
                  \/                                                   \/
             ______________                                   ___________________
             | TextWriter |                                   | HyperTextWriter |
             --------------                                   -------------------
                   |                                                   |
                   |                                       |------------------------|
                  \/                                      \/                       \/
           _______________                      ______________________  _________________________
           | _FileWriter |                      | APIHyperTextWriter |  | NonAPIHyperTextWriter |
           ---------------                      ----------------------  -------------------------
                                                          |                        |
                                         |--------------------|                  |---------------------|
                                        \/                   \/                 \/                    \/
                                   _____________       ______________   ___________________   ____________________
                                   | XMLWriter |       | JsonWriter |   | XMLNonAPIWriter |   | JsonNonAPIWriter |
                                   -------------       --------------   -------------------   --------------------
*/