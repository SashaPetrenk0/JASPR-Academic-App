package org.jaspr.hr.demo;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Utility class for password security operations such as salt generation and password hashing
 */
public class PasswordUtility {

    // Length of the generated salt in bytes.
    private static final int SALT_LENGTH = 16; // 16 bytes
    // Number of hashing iterations.
    private static final int HASH_ITERATIONS = 65536;
    // Length of the generated key in bits
    private static final int KEY_LENGTH = 256;

    /**
     * Generates a cryptographically secure random salt encoded in Base64.
     * @return a Base64-encoded salt string
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a password using PBKDF2 with HMAC-SHA256, the given salt, and defined iterations.
     * @param password the plain-text password to hash
     * @param salt a Base64-encoded salt string
     * @return the Base64-encoded hashed password
     * @throws RuntimeException if hashing fails due to unsupported algorithm or key spec issues
     *
     */
    public static String hashPassword(String password, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    Base64.getDecoder().decode(salt),
                    HASH_ITERATIONS,
                    KEY_LENGTH
            );
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing password: " + e.getMessage(), e);
        }
    }


    //
}