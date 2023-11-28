package org.example;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidParameterSpecException {
        Decoder de = new Decoder("EncTasks1-2.zip", "DecTasks1-2.zip");
        de.decryptFile("ssdkF$HUy2A#D%kd");
        de.closeDecoder();
        DearchiverZip dz = new DearchiverZip("DecTasks1-2.zip");
        ArrayList<String> LA = dz.Dearchive();
        for(String i : LA)
        {
            System.out.println(i);
        }
        ArchiverZip az = new ArchiverZip("Tasks1-2_result.zip");
        az.Archive(LA, "./");
        dz.CloseDearchiverZip();
        az.CloseArchiverZip();
        Encoder en = new Encoder("Tasks1-2_result.zip", "EncTasks1-2_result.zip");
        en.encryptFile("ssdkF$HUy2A#D%kd");
        en.CloseEncoder();
    }
}