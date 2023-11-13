package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReader {
    private File File_;
    private ObjectMapper objectMapper;
    JsonReader(String filename)
    {
        objectMapper = new ObjectMapper();
        File_ = new File(filename);
    }
    public String ReadString() throws IOException {
        String str = new String();
        return objectMapper.readValue(File_, str.getClass());
    }
    public int ReadInteger() throws IOException {
        return objectMapper.readValue(File_, Integer.class);
    }
}
