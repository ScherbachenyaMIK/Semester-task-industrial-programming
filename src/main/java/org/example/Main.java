package org.example;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        DearchiverZip dz = new DearchiverZip("Tasks1-2.zip");
        ArrayList<String> LA = dz.Dearchive();
        for(String i : LA)
        {
            System.out.println(i);
        }
        ArchiverZip az = new ArchiverZip(LA.get(0).substring(0, LA.get(0).indexOf('/')) + "Tasks1-2_result.zip");
        az.Archive(LA, "./");
        dz.CloseDearchiverZip();
        az.CloseArchiverZip();
    }
}