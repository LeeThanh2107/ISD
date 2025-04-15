package com.example.CMS.Common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.security.SecureRandom;

public class IdEncryptor {

    // 256-bit key (32 bytes)
    private static final String SECRET_KEY = "d3b07384d113edec49eaa6238ad5ff00"; // You can keep this in application.properties or use env var
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    // Helper to convert hex to bytes
    private static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < hex.length(); i += 2){
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return bytes;
    }

    public static String encrypt(Long id) throws Exception {
        byte[] clean = String.valueOf(id).getBytes();

        // Generate a random 16-byte IV
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Prepare AES key
        SecretKeySpec keySpec = new SecretKeySpec(hexToBytes(SECRET_KEY), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encrypted = cipher.doFinal(clean);

        // Combine IV and encrypted data
        byte[] ivAndEncrypted = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, ivAndEncrypted, 0, iv.length);
        System.arraycopy(encrypted, 0, ivAndEncrypted, iv.length, encrypted.length);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(ivAndEncrypted);
    }

    public static Long decrypt(String encryptedId) throws Exception {
        byte[] ivAndEncrypted = Base64.getUrlDecoder().decode(encryptedId);
        System.out.println("a"+encryptedId);
        // Extract IV and encrypted data
        byte[] iv = new byte[16];
        byte[] encrypted = new byte[ivAndEncrypted.length - 16];
        System.out.println("a="+encryptedId);

        System.arraycopy(ivAndEncrypted, 0, iv, 0, 16);
        System.out.println("b="+encryptedId);

        System.arraycopy(ivAndEncrypted, 16, encrypted, 0, encrypted.length);
        System.out.println("c="+encryptedId);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        System.out.println("d="+encryptedId);

        SecretKeySpec keySpec = new SecretKeySpec(hexToBytes(SECRET_KEY), "AES");
        System.out.println("e="+encryptedId);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        System.out.println("f="+encryptedId);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        System.out.println("g="+encryptedId);

        byte[] decrypted = cipher.doFinal(encrypted);
        System.out.println("h="+encryptedId);

        return Long.valueOf(new String(decrypted));
    }
}
