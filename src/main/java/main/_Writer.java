package main;

import java.io.IOException;
import java.util.ArrayList;

// Common interface for all writers
public abstract class  _Writer extends writerSource {
    _Writer(String filename_) {
        super(filename_);
    }

    // Method to write a string
    public abstract void WriteString(String data) throws IOException;

    // Method to write an integer
    public abstract void WriteInteger(int data) throws IOException;

    // Method to write a mathematical expression
    public abstract void WriteMathExpression(MathExpression expression) throws IOException;

    // Method to write a list of mathematical expressions
    public abstract void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException;

    // Method to write a result of mathematical expressions
    public abstract void WriteResult(Result result) throws IOException;

    // Method to write a list of results of mathematical expressions
    public abstract void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException;
}

// Interface for writers of text data
abstract class TextWriter extends _Writer {
    TextWriter(String filename_) {
        super(filename_);
    }

    // Method to free resources after writing
    abstract void CloseFile() throws IOException;
}

// Interface for writers of hyper-text data
abstract class HyperTextWriter extends _Writer {

    HyperTextWriter(String filename_) {
        super(filename_);
    }
}

// Interface for writers of hyper-text data through an API
abstract class APIHyperTextWriter extends HyperTextWriter {
    APIHyperTextWriter(String filename_) {
        super(filename_);
    }
}

// Interface for writers of hyper-text data not using an API
abstract class NonAPIHyperTextWriter extends HyperTextWriter {
    NonAPIHyperTextWriter(String filename_) {
        super(filename_);
    }

    // Method to free resources after writing
    abstract void CloseWriter() throws IOException;

    //Method to lock a file
    abstract void OpenWriter() throws IOException;
}

/*
Whole inheritance diagram:
                                    ________________
                                    | main._Writer |
                                    ----------------
                                         |
                   |----------------------------------------------------|
                   |                                                    |
                  \/                                                   \/
             ___________________                                   ________________________
             | main.TextWriter |                                   | main.HyperTextWriter |
             -------------------                                   ------------------------
                   |                                                   |
                   |                                       |------------------------|
                  \/                                      \/                       \/
           ____________________                      ___________________________  ______________________________
           | main._FileWriter |                      | main.APIHyperTextWriter |  | main.NonAPIHyperTextWriter |
           --------------------                      ---------------------------  ------------------------------
                                                          |                                              |
                                         |--------------------|                                |---------------------|
                                        \/                   \/                               \/                    \/
                                   __________________       ___________________   ________________________   _________________________
                                   | main.XMLWriter |       | main.JsonWriter |   | main.XMLNonAPIWriter |   | main.JsonNonAPIWriter |
                                   ------------------       -------------------   ------------------------   -------------------------
*/