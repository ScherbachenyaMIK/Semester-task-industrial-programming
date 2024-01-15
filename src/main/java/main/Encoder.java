package main;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.io.*;
import java.util.ArrayList;

public class Encoder extends BaseDecoratorWriter {
    // Key to decode file
    private String key = "ssdkF$HUy2A#D%kd";

    // Input stream to read the original file
    private InputStream inputStream;

    // Output stream to write the encrypted content
    private OutputStream outputStream;

    // Constructor to initialize input and output streams
    public Encoder(writerSource writer_) throws FileNotFoundException {
        super(writer_);
    }

    // Method to close the encoder, input and output streams
    private void closeEncoder() throws IOException {
        inputStream.close();
        outputStream.close();
    }

    // Method to open the encoder, input and output streams
    private void openEncoder() throws IOException {
        inputStream = new FileInputStream(writer.getFilename());
        outputStream = new FileOutputStream(filename);
    }

    // Method to encrypt the file using AES/CBC/PKCS5PADDING algorithm with the provided key
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        writer.setFilename("temp_" + (temps_counter + 1));
        writer.setTemps_counter(temps_counter + 1);
        writer.WriteListOfResultsOfMathExpressions(expressions, type);
        openEncoder();
        try {
            // Creating a SecretKeySpec using the key and the AES algorithm
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            // Creating a Cipher instance for AES/CBC/PKCS5PADDING
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            // Initializing the cipher in ENCRYPT_MODE with the key
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            // Writing the Initialization Vector (IV) to the output stream
            outputStream.write(cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV());

            // Creating a buffered input stream for efficient reading
            BufferedInputStream InputStream = new BufferedInputStream(inputStream);

            // Buffer for reading and encrypting data
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Reading and encrypting data in chunks
            while ((bytesRead = InputStream.read(buffer)) != -1) {
                outputStream.write(cipher.update(buffer, 0, bytesRead));
            }

            // Writing the final encrypted bytes
            byte[] encryptedBytes = cipher.doFinal();
            if (encryptedBytes != null) {
                outputStream.write(encryptedBytes);
            }

            // Closing the buffered input stream
            closeEncoder();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException |
                 InvalidKeyException | InvalidParameterSpecException e) {
            closeEncoder();
            throw new IOException("Incorrect encoder format!");
        }
        // Deletion of temporary file
        File file = new File(writer.getFilename());
        if (file.exists())
        {
            file.delete();
        }
    }
}
