package com.op_solutions.sunsmart;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class SignatureGenerator {
	
	public static String generateSignature(String apiKey, String packageName, String refId, String securityKey, String requestTime) throws Exception {
        // Concatenate fields in the specified order
        String toHash = apiKey + securityKey + packageName + refId + securityKey + requestTime;

        // Create SHA-256 MessageDigest instance
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Compute the hash bytes
        byte[] hashBytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

        // Encode the hash bytes to Base64
        return Base64.getEncoder().encodeToString(hashBytes);
    }

}
