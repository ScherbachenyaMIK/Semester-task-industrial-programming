package main;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class DearchiverRar extends BaseDecoratorReader {
    // Output file path
    private String OutputFile;

    // Constructor to initialize the input RAR archive file name
    public DearchiverRar(readerSource reader_) {
        super(reader_);
    }

    // Method to close the RAR dearchiver and delete the extracted file
    private void closeDearchiverRar() throws IOException {
        File file = new File(OutputFile);
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            FileUtils.deleteDirectory(new File(OutputFile));
        }
    }

    // Method to open the RAR dearchiver
    private void openDearchiverRar() throws IOException {
        if (filename.lastIndexOf('.') != -1) {
            OutputFile = "dearchived_" + filename.substring(0, filename.lastIndexOf('.'));
        }
        else {
            OutputFile = "dearchived_" + filename;
        }
    }

    // Method to dearchive the RAR file
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        // Opening of dearchiver
        openDearchiverRar();

        // Creating a ProcessBuilder for running the UnRAR command
        ProcessBuilder processBuilder = new ProcessBuilder("./UnRAR.exe", "x", filename);

        // Starting the process
        Process process = processBuilder.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean isNameChanged = false;
        // If the process takes a long time, check for errors in process
        if (process.isAlive()) {
            // Getting of output stream to write answers
            OutputStream outputStream = process.getOutputStream();

            // Writer which send command to second process
            PrintWriter writer = new PrintWriter(outputStream, true);

            // Variable to store type of extracted file
            String line;
            // Reading of error stream of other process
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                if (line.startsWith("Would you like to replace the existing file ")) {
                    // Mark that we always change output filename
                    isNameChanged = true;
                    // Waiting for writing necessary information by other process
                    for (int i = 0; i < 3; ++i) {
                        errorReader.readLine();
                    }
                    // Send command to other process
                    writer.println("R");
                    break;
                }
            }

            // Reading of output stream of other process
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = inputReader.readLine()) != null) {
                if (line.startsWith("Extracting from ")) {
                    // Waiting for writing necessary information by other process
                    inputReader.readLine();
                    // Send command to other process
                    writer.println(OutputFile);
                    break;
                }
            }
        }

        // Waiting for the process to complete and capturing the exit code
        int exitCode;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException(e);
        }

        // Checking the exit code, throwing IOException if not successful
        if (exitCode != 0) {
            throw new IOException("Error while dearchiving RAR file");
        }

        if (!isNameChanged) {
            String line;
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = inputReader.readLine()) != null) {
                if (line.startsWith("Extracting  ")) {
                    // Extracting filename from process output
                    line = line.substring(12, line.lastIndexOf("O")).replaceAll(" ", "")
                            .replaceAll("\\d\\d?%", "").replaceAll("\b", "");
                    File file = new File(line);
                    // if this file represents rar archive add the extension
                    file.renameTo(new File(OutputFile));
                    break;
                }
            }
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
            closeDearchiverRar();
            throw new IOException(exception);
        }
        catch (IllegalArgumentException exception) {
            closeDearchiverRar();
            throw new IllegalArgumentException(exception);
        }

        // Closing DearchiverRar
        closeDearchiverRar();

        return result;
    }
}
