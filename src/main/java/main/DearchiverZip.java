package main;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;

public class DearchiverZip {

    // Input stream for reading from the ZIP archive file
    private FileInputStream File_;

    // ZIP archive stream for reading ZIP entries
    private ZipArchiveInputStream ZipStream;

    // Output directory path for extracted files
    private String OutputFiles;

    // Constructor to initialize the input ZIP archive file and set the output directory path
    public DearchiverZip(String filename) throws IOException {
        File_ = new FileInputStream(filename);
        ZipStream = new ZipArchiveInputStream(File_);
        OutputFiles = filename.substring(0, filename.length() - 4) + "/";
    }

    // Method to close the ZIP dearchiver, input stream, and delete the extracted files
    public void CloseDearchiverZip() throws IOException {
        File_.close();
        ZipStream.close();
        // Deleting the output directory using Apache Commons FileUtils
        FileUtils.deleteDirectory(new File(OutputFiles));
    }

    // Method to dearchive the ZIP file and return a list of extracted file paths
    public ArrayList<String> Dearchive() throws IOException {
        // List to store extracted file paths
        ArrayList<String> files = new ArrayList<String>();

        // Variable to store each ZIP entry during iteration
        ArchiveEntry entry;

        // Creating a buffered file reader for reading entries from the ZIP archive
        _BufferedFileReader fr = new _BufferedFileReader(ZipStream, "UTF8");

        // Adding the output directory path to the list of extracted files
        files.add(OutputFiles);

        // Creating the output directory and its parent directories
        new File(OutputFiles).mkdirs();

        // Iterating through ZIP entries
        while ((entry = ZipStream.getNextEntry()) != null) {
            // Constructing the full path for the extracted file
            String name = OutputFiles + entry.getName();
            // Adding the file path to the list of extracted files
            files.add(name);

            // Checking if the entry represents a directory (ends with '/')
            if (name.charAt(name.length() - 1) == '/') {
                // Creating the directory and its parent directories
                new File(name).mkdirs();
            } else {
                // If the entry represents a file, create a FileWriter to write the file content
                _FileWriter fw = new _FileWriter(name);
                String str;

                // Reading lines from the buffered file reader and writing to the FileWriter
                while ((str = fr.ReadString()) != null) {
                    fw.WriteString(str);
                }

                // Closing the FileWriter
                fw.CloseFile();
            }
        }

        // If the list contains only the output directory, throw an IOException
        if (files.size() == 1) {
            throw new IOException("Error while working with .zip archive");
        }

        // Returning the list of extracted files
        return files;
    }
}
