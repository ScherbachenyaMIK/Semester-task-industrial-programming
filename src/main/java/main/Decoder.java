package main;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Decoder extends BaseDecoratorReader {
    // Key to encode file
    private String key = "ssdkF$HUy2A#D%kd";

    // Input stream to read the encoded file
    private InputStream inputStream;

    // Output stream to write the decoded content
    private OutputStream outputStream;

    // Path to the output file
    private String OutputPath;

    // Constructor to initialize input and output streams, and the output file path
    public Decoder(readerSource reader_) throws FileNotFoundException {
        super(reader_);
    }

    // Method to close the decoder, input, and output streams, and delete the output file
    private void closeDecoder() throws IOException {
        inputStream.close();
        outputStream.close();
        File file = new File(OutputPath);
        if (file.exists()) {
            file.delete();
        }
    }

    // Method to open the decoder, input and output streams
    private void openDecoder() throws IOException {
        inputStream = new FileInputStream(filename);
        OutputPath = "decoded_" + filename;
        outputStream = new FileOutputStream(OutputPath);
    }

    // Method to decrypt the file using AES/CBC/PKCS5PADDING algorithm with the provided key
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        try {
            // Opening of decoder
            openDecoder();
            // Creating a SecretKeySpec using the key and the AES algorithm
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            // Creating a Cipher instance for AES/CBC/PKCS5PADDING
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            // Initializing the cipher with the key and an IV (Initialization Vector)
            byte[] iv = new byte[16];
            if (inputStream.read(iv) != iv.length) {
                throw new IllegalStateException("Incorrect file format: IV is absent");
            }
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            // Creating a buffered input stream for efficient reading
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // Buffer for reading and decrypting data
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Reading and decrypting data in chunks
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(cipher.update(buffer, 0, bytesRead));
            }

            // Writing the final decrypted bytes
            byte[] decryptedBytes = cipher.doFinal();
            if (decryptedBytes != null) {
                outputStream.write(decryptedBytes);
            }

            // Closing the buffered input stream
            bufferedInputStream.close();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException |
                 InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new IOException("Incorrect decoder format!");
        }

        // Checking the output file format
        if (FileChecker.CheckFormat(new FileInputStream(OutputPath)).equals("RAR")) {
            outputStream.close();
            File file = new File(OutputPath);
            // if this file represents rar archive add the extension
            OutputPath += ".rar";
            file.renameTo(new File(OutputPath));
        }

        // Set new filename for reader
        reader.setFilename(OutputPath);
        ArrayList<MathExpression> result;
        try {
            // Starting the reading in another object
            result = reader.ReadListOfMathExpressions();
        }
        catch (IOException | IllegalArgumentException exception) {
            closeDecoder();
            throw new IOException(exception);
        }

        // Closing decoder
        closeDecoder();

        return result;
    }
}
