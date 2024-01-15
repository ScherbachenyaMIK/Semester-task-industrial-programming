package main;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;

// Common interface for all readers
public abstract class Reader extends readerSource {
    Reader(String filename_) {
        super(filename_);
    }

    // Method to read a string
    abstract public String ReadString() throws IOException;

    // Method to read an integer
    abstract public int ReadInteger() throws IOException;

    // Method to read a mathematical expression
    abstract public MathExpression ReadMathExpression() throws IOException;

    // Method to read a list of mathematical expressions
    abstract public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException;
}

// Interface for readers of text data
abstract class TextReader extends Reader {
    TextReader(String filename_) {
        super(filename_);
    }

    // Method to free file after reading
    abstract void CloseFile() throws IOException;
}

// Interface for readers of hyper-text data
abstract class HyperTextReader extends Reader {
    HyperTextReader(String filename_) {
        super(filename_);
    }
}

// Interface for readers of hyper-text data through an API
abstract class APIHyperTextReader extends HyperTextReader {
    APIHyperTextReader(String filename_) {
        super(filename_);
    }
}

// Interface for readers of hyper-text data not using an API
abstract class NonAPIHyperTextReader extends HyperTextReader {
    NonAPIHyperTextReader(String filename_) {
        super(filename_);
    }

    // Method to free file after reading
    abstract void CloseReader() throws IOException;
}

/*
Whole inheritance diagram:
                                    _______________
                                    | main.Reader |
                                    ---------------
                                        |
                   |----------------------------------------------------|
                   |                                                    |
                  \/                                                   \/
             ___________________                                   ________________________
             | main.TextReader |                                   | main.HyperTextReader |
             -------------------                                   ------------------------
                   |                                                        |
         |---------------------|                                     |-------------------------------|
        \/                    \/                                    \/                              \/
   ____________________  ____________________________     ___________________________  ______________________________
   | main._FileReader |  | main._BufferedFileReader |     | main.APIHyperTextReader |  | main.NonAPIHyperTextReader |
   --------------------  ----------------------------     ---------------------------  ------------------------------
                                                          |                                          |
                                         |--------------------|                              |---------------------------|
                                        \/                   \/                             \/                          \/
                                   __________________       ___________________   ________________________   _________________________
                                   | main.XMLReader |       | main.JsonReader |   | main.XMLNonAPIReader |   | main.JsonNonAPIReader |
                                   ------------------       -------------------   ------------------------   -------------------------
*/