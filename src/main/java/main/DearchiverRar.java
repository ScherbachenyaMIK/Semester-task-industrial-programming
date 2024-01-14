package main;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;

public class DearchiverRar {

    // Input RAR archive file name
    private String filename;

    // Output directory path for extracted files
    private String OutputFiles;

    // Constructor to initialize the input RAR archive file name
    public DearchiverRar(String filename_) {
        filename = filename_;
    }

    // Method to close the RAR dearchiver and delete the extracted files
    public void CloseDearchiverRar() throws IOException {
        // Deleting the output directory using Apache Commons FileUtils
        FileUtils.deleteDirectory(new File(OutputFiles));
    }

    // Method to dearchive the RAR file and return a list of extracted files
    public ArrayList<String> Dearchive() throws IOException, InterruptedException {
        // List to store extracted file paths
        ArrayList<String> files = new ArrayList<>();

        // Generating the output directory path based on the input RAR file name
        OutputFiles = filename.substring(0, filename.length() - 4) + "/";

        // Creating a ProcessBuilder for running the UnRAR command
        ProcessBuilder processBuilder = new ProcessBuilder("./UnRAR.exe", "x", "-o+", filename, OutputFiles);

        // Starting the process
        Process process = processBuilder.start();

        // Creating a BufferedReader to read the process's input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Waiting for the process to complete and capturing the exit code
        int exitCode = process.waitFor();

        // Checking the exit code, throwing IOException if not successful
        if (exitCode != 0) {
            throw new IOException("Error while dearchiving RAR file");
        }

        // Variables to store lines read from the process's input stream
        String line;
        String file;

        // Reading lines from the process's input stream
        while ((line = reader.readLine()) != null) {
            // Processing lines starting with "Creating"
            if (line.startsWith("Creating")) {
                // Cleaning up and formatting the line to extract the file path
                line = line.replaceAll(" ", "");
                file = line.substring(8, line.lastIndexOf("O"));
                file = file.replaceAll("\\\\", "/");
                // Adding the file path to the list of extracted files
                files.add(file + "/");
            }
            // Processing lines starting with "Extracting" (excluding "Extracting from")
            else if (line.startsWith("Extracting") && !line.startsWith("Extracting from")) {
                // Cleaning up and formatting the line to extract the file path
                line = line.replaceAll(" ", "");
                line = line.replaceAll("\b", "");
                line = line.replaceAll("\\d\\d?%", "");
                file = line.substring(10, line.lastIndexOf("O"));
                file = file.replaceAll("\\\\", "/");
                // Adding the file path to the list of extracted files
                files.add(file);
            }
        }

        // Returning the list of extracted files
        return files;
    }
}
