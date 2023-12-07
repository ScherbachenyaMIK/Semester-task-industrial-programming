package org.example;

import java.io.*;

public class ArchiverRar {
    String directoryname;
    ArchiverRar(String directoryname_) throws IOException {
        directoryname = directoryname_;
    }

    public void Archive()
            throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("./Rar.exe", "a", "-r",
                directoryname.substring(0, directoryname.length() - 1) + ".rar", directoryname);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if(exitCode != 0)
        {
            throw new IOException();
        }
    }
}
