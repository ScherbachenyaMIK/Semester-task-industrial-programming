package main;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;

public class ArchiverZip extends BaseDecoratorWriter {

    // FileOutputStream to write to the ZIP archive file
    private FileOutputStream File_;

    // ZipArchiveOutputStream to handle ZIP archive entries
    private ZipArchiveOutputStream ZipStream;

    // Constructor to initialize the FileOutputStream and ZipArchiveOutputStream
    public ArchiverZip(writerSource writer_) throws IOException {
        super(writer_);
    }

    // Method to close the ZIP archiver
    private void closeArchiverZip() throws IOException {
        ZipStream.close();
        File_.close();
    }

    // Method to open the ZIP archiver
    private void openArchiverZip() throws IOException {
        if (filename.lastIndexOf('.') != -1) {
            filename = filename.substring(0, filename.lastIndexOf('.')) + ".zip";
        }
        else {
            filename = filename + ".zip";
        }
        File_ = new FileOutputStream(filename);
        ZipStream = new ZipArchiveOutputStream(File_);
    }

    // Method to archive a file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        writer.setFilename("temp_" + (temps_counter + 1));
        writer.setTemps_counter(temps_counter + 1);
        writer.WriteListOfResultsOfMathExpressions(expressions, type);
        openArchiverZip();
        // Creating a ZipArchiveEntry for the file
        ArchiveEntry entry;
        if(writer.getFilename().endsWith(".zip")) {
            entry = new ZipArchiveEntry(
                    source_filename.substring(0, source_filename.lastIndexOf('.')) + ".zip");
        }
        else if (writer.getFilename().endsWith(".rar")) {
            entry = new ZipArchiveEntry(
                    source_filename.substring(0, source_filename.lastIndexOf('.')) + ".rar");
        } else {
            entry = new ZipArchiveEntry(source_filename);
        }

        // Putting the entry into the ZipStream
        ZipStream.putArchiveEntry(entry);

        // Writing the content of the file to the ZipStream
        byte[] fileContent = FileUtils.readFileToByteArray(new File(writer.getFilename()));
        ZipStream.write(fileContent);

        // Closing the ZipStream entry
        ZipStream.closeArchiveEntry();

        // Finishing the ZipStream to complete the archive
        ZipStream.finish();
        closeArchiverZip();

        // Deletion of temporary file
        File file = new File(writer.getFilename());
        if (file.exists())
        {
            file.delete();
        }
    }
}
