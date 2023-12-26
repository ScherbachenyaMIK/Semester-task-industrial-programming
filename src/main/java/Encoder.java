import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.io.*;
import java.security.spec.InvalidParameterSpecException;

public class Encoder {
    private InputStream inputStream;
    private OutputStream outputStream;
    Encoder(String inputFilePath, String outputFilePath) throws FileNotFoundException {
        inputStream = new FileInputStream(inputFilePath);
        outputStream = new FileOutputStream(outputFilePath);
    }
    public void CloseEncoder() throws IOException {
        inputStream.close();
        outputStream.close();
    }
    public void encryptFile(String key) throws IOException, InvalidKeyException, InvalidParameterSpecException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            outputStream.write(cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV());
            BufferedInputStream InputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = InputStream.read(buffer)) != -1) {
                outputStream.write(cipher.update(buffer, 0, bytesRead));
            }
            byte[] encryptedBytes = cipher.doFinal();
            if (encryptedBytes != null) {
                outputStream.write(encryptedBytes);
            }
            InputStream.close();
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e)
        {
            throw new IOException("Incorrect decoder format!");
        }
    }
}
