package org.example;

import org.apache.commons.io.FileUtils;
import java.io.*;

public class DearchiverRar {
    private String filename;
    private String OutputFiles;
    public DearchiverRar(String filename_) {
        filename = filename_;
    }
    public void CloseDearchiverRar() throws IOException {
        FileUtils.deleteDirectory(new File(OutputFiles));
    }
    public void Dearchive() throws IOException, InterruptedException {
        OutputFiles = "./" + filename.substring(0, filename.length() - 4) + "/";
        ProcessBuilder processBuilder = new ProcessBuilder("./Rar.exe", "x", "-o+", filename, OutputFiles);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException();
        }
    }
}
