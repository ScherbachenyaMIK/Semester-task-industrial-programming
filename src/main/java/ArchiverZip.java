import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchiverZip {

    // FileOutputStream to write to the ZIP archive file
    private FileOutputStream File_;

    // ZipArchiveOutputStream to handle ZIP archive entries
    private ZipArchiveOutputStream ZipStream;

    // Constructor to initialize the FileOutputStream and ZipArchiveOutputStream
    ArchiverZip(String filename) throws IOException {
        File_ = new FileOutputStream(filename);
        ZipStream = new ZipArchiveOutputStream(File_);
    }

    // Method to close the ZIP archive
    public void CloseArchiverZip() throws IOException {
        ZipStream.close();
        File_.close();
    }

    // Static method to create a list of files to be archived
    public static List<String> makeListOfFilesToArchive(String directoryPath) throws FileNotFoundException {
        // List to store file paths
        List<String> result = new ArrayList<>();

        // Creating a File object for the specified directory path
        File folder = new File(directoryPath);

        // Checking if the specified path is a file, and adding it to the result list if true
        if (folder.isFile()) {
            result.add(folder.getPath());
        } else {
            // If the path is not a file, it should be a directory
            if (!folder.exists() || !folder.isDirectory()) {
                throw new FileNotFoundException();
            }
            // Adding the directory path to the result list
            result.add(directoryPath);
            // Calling the recursive helper method to list files within the directory
            listFiles(folder, result);
        }
        return result;
    }

    // Helper method to recursively list files within a directory
    private static void listFiles(File folder, List<String> result) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    // Adding the file path to the result list
                    result.add(file.getPath().replaceAll("\\\\", "/"));
                } else if (file.isDirectory()) {
                    // Adding the directory path to the result list
                    result.add(file.getPath().replaceAll("\\\\", "/").concat("/"));
                    // Recursive call to list files within the subdirectory
                    listFiles(file, result);
                }
            }
        }
    }

    // Method to archive a list of files
    public void Archive(List<String> filesToArchive) throws IOException {
        for (String file : filesToArchive) {
            String filePath = file;
            File file_check = new File(file);
            // Checking if the file exists
            if (!file_check.exists()) {
                throw new FileNotFoundException("Error while working with .zip archive, file not exists");
            }
            // Creating a ZipArchiveEntry for the file
            ArchiveEntry entry = new ZipArchiveEntry(file);

            // Putting the entry into the ZipStream
            ZipStream.putArchiveEntry(entry);

            // Writing the content of the file to the ZipStream
            if (!file.endsWith("/")) {
                BufferedInputStream InputStream = new BufferedInputStream(new FileInputStream(filePath));
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = InputStream.read(buffer)) != -1) {
                    ZipStream.write(buffer, 0, bytesRead);
                }
                InputStream.close();
            }

            // Closing the ZipStream entry
            ZipStream.closeArchiveEntry();
        }
        // Finishing the ZipStream to complete the archive
        ZipStream.finish();
    }
}
