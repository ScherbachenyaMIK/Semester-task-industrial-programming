package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class JsonWriter {
    private File File_;
    private ObjectMapper objectMapper;
    JsonWriter(String filename)
    {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File_ = new File(filename);
    }
    JsonWriter(File EnterFile)
    {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File_ = EnterFile;
    }
    public void WriteString(String str) throws IOException {
        objectMapper.writeValue(File_, str);
    }
    public void WriteInteger(int i) throws IOException {
        objectMapper.writeValue(File_, i);;
    }
    public void WriteMathExpression(MathExpression expression) throws IOException {
        objectMapper.writeValue(File_, expression);
    }
}
