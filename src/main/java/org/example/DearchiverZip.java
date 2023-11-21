package org.example;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DearchiverZip {
    private FileInputStream File_;
    private ZipArchiveInputStream ZipStream;
    DearchiverZip(String filename) throws IOException {
        File_ = new FileInputStream(filename);
        ZipStream = new ZipArchiveInputStream(File_);
    }
    public void CloseArchiverZip(String filename) throws IOException {
        File_.close();
        ZipStream.close();
    }

    public ArrayList<String> Dearchive() throws IOException {
        ArrayList<String> files = new ArrayList<String>();
        ArchiveEntry entry;
        entry = ZipStream.getNextEntry();
        _BufferedFileReader fr = new _BufferedFileReader(ZipStream, "UTF8");
        while ((entry = ZipStream.getNextEntry()) != null) {
            files.add(entry.getName());
            _FileWriter fw = new _FileWriter(files.get(files.size() - 1));
            String str;
            while ((str = fr.ReadString()) != null) {
                fw.WriteString(str);
            }
            fw.CloseFile();
        }
        return files;
    }
}
