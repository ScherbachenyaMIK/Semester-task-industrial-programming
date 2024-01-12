import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchiverZip
{
    private FileOutputStream File_;
    private ZipArchiveOutputStream ZipStream;
    ArchiverZip(String filename) throws IOException {
        File_ = new FileOutputStream(filename);
        ZipStream = new ZipArchiveOutputStream(File_);
    }

    public void CloseArchiverZip() throws IOException {
        ZipStream.close();
        File_.close();
    }

    public static List<String> makeListOfFilesToArchive(String directoryPath) throws FileNotFoundException {
        List <String> result = new ArrayList<>();
        File folder = new File(directoryPath);
        if (folder.isFile()) {
            result.add(folder.getPath());
        } else {
            if (!folder.exists() || !folder.isDirectory()) {
                throw new FileNotFoundException();
            }
            result.add(directoryPath);
            listFiles(folder, result);
        }
        return result;
    }

    private static void listFiles(File folder, List<String> result) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    result.add(file.getPath().replaceAll("\\\\", "/"));
                } else if (file.isDirectory()) {
                    result.add(file.getPath().replaceAll("\\\\", "/").concat("/"));
                    listFiles(file, result);
                }
            }
        }
    }

    public void Archive(List<String> filesToArchive)
            throws IOException {
        for (String file : filesToArchive) {
            String filePath = file;
            File file_check = new File(file);
            if (!file_check.exists()) {
                throw new FileNotFoundException("Error while working with .zip archive, file not exists");
            }
            ArchiveEntry entry = new ZipArchiveEntry(file);

            ZipStream.putArchiveEntry(entry);
            if (!file.endsWith("/")) {
                BufferedInputStream InputStream = new BufferedInputStream(new FileInputStream(filePath));
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = InputStream.read(buffer)) != -1) {
                    ZipStream.write(buffer, 0, bytesRead);
                }
                InputStream.close();
            }
            ZipStream.closeArchiveEntry();
        }
        ZipStream.finish();
    }
}
