package org.example;

import lombok.Getter;

import java.io.*;
import java.util.ArrayList;

public class _FileReader {
    @Getter
    private FileReader File_;
    _FileReader(String filename) throws FileNotFoundException {
        File_ = new FileReader(filename);
    }
    _FileReader(File EnterFile) throws FileNotFoundException {
        File_ = new FileReader(EnterFile);
    }
    public void CloseFile() throws IOException{
        File_.close();
    }
    public String ReadString() throws IOException {
        String str = new String();
        int ch;
        while (((ch = File_.read()) != '\n') && (ch != -1))
        {
            str = str + (char)ch;
        }
        if(str.isEmpty())
        {
            return null;
        }
        if(str.charAt(str.length() - 1) == '\r')
        {
            str = str.replaceFirst("\r", "");
        }
        return str;
    }
    public int ReadInteger() throws IOException {
        String str = ReadString();
        if (str.endsWith(" ")) {
            str = str.replaceFirst("(.*) $", "$1");
        }
        return Integer.parseInt(str);
    }
    public MathExpression ReadMathExpression() throws IOException {
        String expression = ReadString();
        ArrayList<String> variables = new ArrayList<>();
        String variable;
        while((variable = ReadString()) != null)
        {
            variables.add(variable);
        }
        return new MathExpression(expression, variables);
    }
}

