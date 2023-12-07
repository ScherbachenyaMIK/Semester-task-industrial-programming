package org.example;

import java.io.*;

public class DearchiverRar {
    private String filename;

    public DearchiverRar(String filename_) {
        filename = filename_;
    }

    public void Dearchive() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("./Rar.exe", "x", "-o+", filename, "./" +
                filename.substring(0, filename.length() - 4) + "/");
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException();
        }
    }
}
