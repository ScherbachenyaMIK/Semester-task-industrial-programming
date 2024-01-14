package main;

import java.io.IOException;
import java.util.ArrayList;

// Common interface for all readers
public interface Reader {
    // Method to read a string
    String ReadString() throws IOException;

    // Method to read an integer
    int ReadInteger() throws IOException;

    // Method to read a mathematical expression
    MathExpression ReadMathExpression() throws IOException;

    // Method to read a list of mathematical expressions
    ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException;
}

// Interface for readers of text data
interface TextReader extends Reader {
    // Method to free file after reading
    void CloseFile() throws IOException;
}

// Interface for readers of hyper-text data
interface HyperTextReader extends Reader {

}

// Interface for readers of hyper-text data through an API
interface APIHyperTextReader extends HyperTextReader {
}

// Interface for readers of hyper-text data not using an API
interface NonAPIHyperTextReader extends HyperTextReader {
    // Method to free file after reading
    void CloseReader() throws IOException;
}

/*
Whole inheritance diagram:
                                    __________
                                    | main.Reader |
                                    ----------
                                        |
                   |----------------------------------------------------|
                   |                                                    |
                  \/                                                   \/
             ______________                                   ___________________
             | main.TextReader |                                   | main.HyperTextReader |
             --------------                                   -------------------
                   |                                                   |
         |---------------------|                           |------------------------|
        \/                    \/                          \/                       \/
   _______________  _______________________     ______________________  _________________________
   | main._FileReader |  | main._BufferedFileReader |     | main.APIHyperTextReader |  | main.NonAPIHyperTextReader |
   ---------------  -----------------------     ----------------------  -------------------------
                                                          |                        |
                                         |--------------------|                  |---------------------|
                                        \/                   \/                 \/                    \/
                                   _____________       ______________   ___________________   ____________________
                                   | main.XMLReader |       | main.JsonReader |   | main.XMLNonAPIReader |   | main.JsonNonAPIReader |
                                   -------------       --------------   -------------------   --------------------
*/