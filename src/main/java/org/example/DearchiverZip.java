package org.example;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DearchiverZip {
    private FileInputStream File_;
    static ZipArchiveInputStream ZipStream;
    DearchiverZip(String filename) throws IOException {
        File_ = new FileInputStream(filename);
        ZipStream = new ZipArchiveInputStream(File_);
    }
    public void CloseArchiverZip(String filename) throws IOException {
        File_.close();
        ZipStream.close();
    }

    public static HashMap<String, String> Dearchive() throws IOException {
        HashMap<String, String> files;
        ArchiveEntry entry;
        while ((entry = ZipStream.getNextEntry()) != null) {
            files.put(entry.getName(), ReadContent(CheckFormat(ZipStream));
        }
    }
}
