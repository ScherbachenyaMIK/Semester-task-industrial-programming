package org.example;

import java.io.*;

public class _FileWriter {
    private FileWriter File_;
    _FileWriter(String filename) throws IOException {
        File_ = new FileWriter(filename);
    }
    _FileWriter(File EnterFile) throws IOException {
        File_ = new FileWriter(EnterFile);
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
