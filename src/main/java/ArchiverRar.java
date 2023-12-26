import java.io.*;

public class ArchiverRar {
    String filename;
    ArchiverRar(String filename_) {
        filename = filename_;
    }

    public void Archive(String directoryname) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("./Rar.exe", "a", "-r",
                filename, directoryname);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if(exitCode != 0)
        {
            throw new IOException("Error while working with .rar archive");
        }
    }
}
