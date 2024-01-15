package main;

import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DearchiverZip extends BaseDecoratorReader {

    // Input stream for reading from the ZIP archive file
    private FileInputStream File_;

    // ZIP archive stream for reading ZIP entries
    private ZipArchiveInputStream ZipStream;

    // Output file path
    private String OutputFile;

    // Constructor to initialize the input ZIP archive file and set the output directory path
    public DearchiverZip(readerSource reader_) throws IOException {
        super(reader_);
    }

    // Method to open the ZIP dearchiver, input stream and declare output
    private void openDearchiverZip() throws IOException {
        File_ = new FileInputStream(filename);
        ZipStream = new ZipArchiveInputStream(File_);
        // Output filename
        if (filename.lastIndexOf('.') != -1) {
            OutputFile = "dearchived_" + filename.substring(0, filename.lastIndexOf('.'));
        }
        else {
            OutputFile = "dearchived_" + filename;
        }
    }

    // Method to close the ZIP dearchiver, input stream, and delete the extracted file
    private void closeDearchiverZip() throws IOException {
        File_.close();
        ZipStream.close();
        File file = new File(OutputFile);
        file.delete();
    }

    // Method to dearchive the ZIP file
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        // Firstly, open the input stream
        openDearchiverZip();

        // Creating a buffered file reader for reading entries from the ZIP archive
        BufferedReader fr = new BufferedReader(new InputStreamReader(ZipStream, StandardCharsets.UTF_8));

        // Take ZIP entry
        if (ZipStream.getNextEntry() != null) {

            // Create a FileOutputStream to write the file content
            FileOutputStream fos = new FileOutputStream(OutputFile);

            // Reading of the content of the file and writing to output file
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = ZipStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            // Closing the FileWriter
            fos.close();
        }
        else {
            closeDearchiverZip();
            fr.close();
            throw new IOException("Archive is empty");
        }

        // Closing the FileReader
        fr.close();

        if (ZipStream.getNextEntry() != null) {
            closeDearchiverZip();
            throw new IOException("Multiple files found in archive");
        }

        // Checking the output file format
        if (FileChecker.CheckFormat(new FileInputStream(OutputFile)).equals("RAR")) {
            File file = new File(OutputFile);
            // if this file represents rar archive add the extension
            OutputFile += ".rar";
            file.renameTo(new File(OutputFile));
        }

        // Set new filename for reader
        reader.setFilename(OutputFile);
        ArrayList<MathExpression> result;
        try {
            // Starting the reading in another object
            result = reader.ReadListOfMathExpressions();
        }
        catch (IOException exception) {
            closeDearchiverZip();
            throw new IOException(exception);
        }
        catch (IllegalArgumentException exception) {
            closeDearchiverZip();
            throw new IllegalArgumentException(exception);
        }

        // Closing DearchiverZip
        closeDearchiverZip();

        return result;
    }
}
