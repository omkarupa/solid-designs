package com.op_solutions.solid_designs.services;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class Aes256CbcService {
	
	// Encrypt JSON body to Base64 using AES-256-CBC with key = ciphertext+api_key & IV = ciphertext
    public String encrypt(String jsonBody, String apiKey, String ciphertext) throws Exception {
        String appSecret = ciphertext + apiKey;

        byte[] keyBytes = appSecret.getBytes(StandardCharsets.UTF_8);
        byte[] key = new byte[32];
        System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, 32));

        byte[] ivBytes = new byte[16];
        byte[] ciphertextBytes = ciphertext.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(ciphertextBytes, 0, ivBytes, 0, Math.min(ciphertextBytes.length, 16));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        byte[] encryptedBytes = cipher.doFinal(jsonBody.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt Base64 encrypted data using AES-256-CBC with key = ciphertext+api_key & IV = ciphertext
    public String decrypt(String base64EncryptedData, String apiKey, String ciphertext) throws Exception {
        String appSecret = ciphertext + apiKey;

        byte[] keyBytes = appSecret.getBytes(StandardCharsets.UTF_8);
        byte[] key = new byte[32];
        System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, 32));

        byte[] ivBytes = new byte[16];
        byte[] ciphertextBytes = ciphertext.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(ciphertextBytes, 0, ivBytes, 0, Math.min(ciphertextBytes.length, 16));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] decodedEncryptedData = Base64.getDecoder().decode(base64EncryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedEncryptedData);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
