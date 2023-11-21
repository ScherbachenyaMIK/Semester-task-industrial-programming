package org.example;

import java.io.*;

public class _FileReader {
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
        if(str.charAt(str.length() - 1) == '\r')
        {
            str = str.replaceFirst("\r", "");
        }
        return str;
    }
    public int ReadInteger() throws IOException {
        String str = ReadString();
        if(str.endsWith(" "))
        {
            str = str.replaceFirst("(.*) $", "$1");
        }
        return Integer.parseInt(str);
    }

    public FileReader getFile_() {
        return File_;
    }
}

