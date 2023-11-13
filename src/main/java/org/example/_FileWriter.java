package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class _FileWriter {
    private FileWriter File_;
    _FileWriter(String filename) throws IOException {
        File_ = new FileWriter(filename);
    }
    public void CloseFile() throws IOException {
        File_.close();
    }
    public void WriteString(String str) throws IOException {
        File_.write(str + '\n');
    }
    public void WriteInteger(int i) throws IOException {
        File_.write(Integer.valueOf(i).toString() + ' ');
    }
}
