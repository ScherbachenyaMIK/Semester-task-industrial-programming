package main;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

import java.io.*;
import java.util.ArrayList;

public class ArchiverRar extends BaseDecoratorWriter {

    // Constructor to initialize the filename
    public ArchiverRar(writerSource writer_) {
        super(writer_);
    }

    // Method to archive a file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        writer.setFilename("temp_" + (temps_counter + 1));
        writer.setTemps_counter(temps_counter + 1);
        writer.WriteListOfResultsOfMathExpressions(expressions, type);
        if (filename.lastIndexOf('.') != -1) {
            filename = filename.substring(0, filename.lastIndexOf('.')) + ".rar";
        }
        else {
            filename = filename + ".rar";
        }
        // Creating a process builder for the archiving RAR
        ProcessBuilder processBuilder = new ProcessBuilder("./Rar.exe", "a", "-r", filename, writer.getFilename());

        // Starting the process
        Process process = processBuilder.start();

        // Waiting for the process to complete and retrieving the exit code
        int exitCode = 0;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Error while working with .rar archive");
        }

        // Checking the exit code for errors
        if (exitCode != 0) {
            throw new IOException("Error while working with .rar archive");
        }

        String entryname;
        if(writer.getFilename().endsWith(".zip")) {
            entryname = source_filename.substring(0, source_filename.lastIndexOf('.')) + ".zip";
        }
        else if (writer.getFilename().endsWith(".rar")) {
            entryname = source_filename.substring(0, source_filename.lastIndexOf('.')) + ".rar";
        } else {
            entryname = source_filename;
        }

        // Creating a process builder for the rename file in RAR file
        processBuilder = new ProcessBuilder("./Rar.exe", "rn", filename, writer.getFilename(), entryname);

        // Starting the process
        process = processBuilder.start();

        // Waiting for the process to complete and retrieving the exit code
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Error while working with .rar archive");
        }

        // Checking the exit code for errors
        if (exitCode != 0) {
            throw new IOException("Error while working with .rar archive");
        }

        // Deletion of temporary file
        File file = new File(writer.getFilename());
        if (file.exists())
        {
            file.delete();
        }
    }
}
