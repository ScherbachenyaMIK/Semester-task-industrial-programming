package org.example;

import java.io.*;

public class _BufferedFileReader {
    private BufferedReader File_;

    _BufferedFileReader(_FileReader fileReader) throws FileNotFoundException {
        File_ = new BufferedReader(fileReader.getFile_());
    }

    _BufferedFileReader(InputStream fileReader, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {  //not working
        File_ = new BufferedReader(new InputStreamReader(fileReader, charsetName));
    }

    public void CloseFile() throws IOException {
        File_.close();
    }

    public String ReadString() throws IOException {
        String str;
        str = File_.readLine();
        return str;
    }

    public int ReadInteger() throws IOException {
        String str;
        str = File_.readLine();
        return Integer.parseInt(str);
    }
}
