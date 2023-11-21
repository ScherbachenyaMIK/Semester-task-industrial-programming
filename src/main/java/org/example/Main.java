package org.example;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        DearchiverZip dz = new DearchiverZip("Tasks.zip");
        ArrayList<String> LA = dz.Dearchive();
        for(String i : LA)
        {
            System.out.println(i);
        }
    }
}