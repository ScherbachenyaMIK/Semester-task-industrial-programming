package org.example;
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

import java.io.*;

public class Decoder {
    private InputStream inputStream;
    private OutputStream outputStream;

    Decoder(String inputFilePath, String outputFilePath) throws FileNotFoundException {
        inputStream = new FileInputStream(inputFilePath);
        outputStream = new FileOutputStream(outputFilePath);
    }

    public void closeDecoder() throws IOException {
        inputStream.close();
        outputStream.close();
    }

    public void decryptFile(String key) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        byte[] iv = new byte[16];
        if (inputStream.read(iv) != iv.length) {
            throw new IllegalStateException("Некорректный формат файла: отсутствует IV");
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            outputStream.write(cipher.update(buffer, 0, bytesRead));
        }
        byte[] decryptedBytes = cipher.doFinal();
        if (decryptedBytes != null) {
            outputStream.write(decryptedBytes);
        }
        bufferedInputStream.close();
    }
}