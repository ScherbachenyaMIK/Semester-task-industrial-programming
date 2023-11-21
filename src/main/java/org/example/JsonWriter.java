package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonWriter {
    private File File_;
    private ObjectMapper objectMapper;
    JsonWriter(String filename)
    {
        objectMapper = new ObjectMapper();
        File_ = new File(filename);
    }
    JsonWriter(File EnterFile)
    {
        objectMapper = new ObjectMapper();
        File_ = EnterFile;
    }
    public void WriteString(String str) throws IOException {
        objectMapper.writeValue(File_, str);
    }
    public void WriteInteger(int i) throws IOException {
        objectMapper.writeValue(File_, i);;
    }
}
