package org.example;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.*;
import java.util.ArrayList;

public class DearchiverZip {
    private FileInputStream File_;
    private ZipArchiveInputStream ZipStream;
    DearchiverZip(String filename) throws IOException {
        File_ = new FileInputStream(filename);
        ZipStream = new ZipArchiveInputStream(File_);
    }
    public void CloseDearchiverZip() throws IOException {
        File_.close();
        ZipStream.close();
    }

    public ArrayList<String> Dearchive() throws IOException {
        ArrayList<String> files = new ArrayList<String>();
        ArchiveEntry entry;
        _BufferedFileReader fr = new _BufferedFileReader(ZipStream, "UTF8");
        while ((entry = ZipStream.getNextEntry()) != null) {
            String name = entry.getName();
            files.add(name);
            if (name.charAt(name.length() - 1) == '/'){
                new File(entry.getName()).mkdirs();
            }
            else {
                _FileWriter fw = new _FileWriter(files.get(files.size() - 1));
                String str;
                while ((str = fr.ReadString()) != null) {
                    fw.WriteString(str);
                }
                fw.CloseFile();
            }
        }
        return files;
    }
}
