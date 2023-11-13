package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        _FileWriter fw = new _FileWriter("./input.txt");
        fw.WriteString("sadasd");
        fw.WriteInteger(64);
        fw.CloseFile();
        _FileReader fr = new _FileReader("./input.txt");
        String str = fr.ReadString();
        int i = fr.ReadInteger();
        System.out.println(str);
        System.out.println(i);
    }
}