package com.op_solutions.solid_designs.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionService {

	public String encryptRequestBody(String jsonBody, String apiKey, String ciphertext) throws Exception {
		// Concatenate ciphertext + apiKey to form app secret
		String appSecret = ciphertext + apiKey;

		// Ensure the appSecret key is 32 bytes (256 bits) for AES-256
		byte[] keyBytes = appSecret.getBytes(StandardCharsets.UTF_8);
		byte[] key = new byte[32];
		System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, 32));

		// Use first 16 bytes of ciphertext as IV
		byte[] ivBytes = new byte[16];
		byte[] ciphertextBytes = ciphertext.getBytes(StandardCharsets.UTF_8);
		System.arraycopy(ciphertextBytes, 0, ivBytes, 0, Math.min(ciphertextBytes.length, 16));

		// Setup Cipher for AES-256-CBC with PKCS5 Padding
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

		// Encrypt the JSON body string
		byte[] encryptedBytes = cipher.doFinal(jsonBody.getBytes(StandardCharsets.UTF_8));

		// Base64 encode encrypted bytes and return
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}
}
