package main;

import java.io.*;

public class ArchiverRar {

    // Filename for the RAR archive
    String filename;

    // Constructor to initialize the filename
    public ArchiverRar(String filename_) {
        filename = filename_;
    }

    // Method to archive a directory using the RAR executable
    public void Archive(String directoryname) throws IOException, InterruptedException {
        // Creating a process builder for the RAR executable with the appropriate command-line arguments
        ProcessBuilder processBuilder = new ProcessBuilder("./Rar.exe", "a", "-r", filename, directoryname);

        // Starting the process
        Process process = processBuilder.start();

        // Waiting for the process to complete and retrieving the exit code
        int exitCode = process.waitFor();

        // Checking the exit code for errors
        if (exitCode != 0) {
            throw new IOException("Error while working with .rar archive");
        }
    }
}
