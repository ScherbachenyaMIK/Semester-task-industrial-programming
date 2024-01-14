package main;

import lombok.Getter;

// Class representing the result of a calculation
public class Result {
    @Getter
    private String result;

    // Default constructor
    public Result() {
        result = "";
    }

    // Constructor with an error character
    public Result(char e) {
        result = "error!";
    }

    // Constructor with a valid result string
    public Result(String result_) {
        result = result_;
    }
}
