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

public class Decoder {

    // Input stream to read the encoded file
    private InputStream inputStream;

    // Output stream to write the decoded content
    private OutputStream outputStream;

    // Path to the output file
    private String OutputPath;

    // Constructor to initialize input and output streams, and the output file path
    Decoder(String inputFilePath, String outputFilePath) throws FileNotFoundException {
        inputStream = new FileInputStream(inputFilePath);
        outputStream = new FileOutputStream(outputFilePath);
        OutputPath = outputFilePath;
    }

    // Method to close the decoder, input, and output streams, and delete the output file
    public void closeDecoder() throws IOException {
        inputStream.close();
        outputStream.close();
        File file = new File(OutputPath);
        if (file.exists()) {
            file.delete();
        }
    }

    // Method to decrypt the file using AES/CBC/PKCS5PADDING algorithm with the provided key
    public void decryptFile(String key) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException {
        try {
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
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IOException("Incorrect decoder format!");
        }
    }
}
