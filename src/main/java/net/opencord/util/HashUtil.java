package net.opencord.util;

import org.bouncycastle.crypto.generators.SCrypt;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtil {

    // Recommended scrypt parameters
    private static final int N = 16384; // CPU/memory cost parameter (must be power of 2)
    private static final int r = 8;     // Block size (RAM usage)
    private static final int p = 1;     // Parallelization factor (threads)
    private static final int keyLength = 32; // Output length in bytes (32 bytes = 256 bits)

    // Generates a scrypt hash with salt
    public static String generateHash(String password, byte[] salt) {
        byte[] hash = SCrypt.generate(password.getBytes(), salt, N, r, p, keyLength);
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    // Creates a secure random salt
    public static byte[] generateSalt(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    // Verifies if the provided password matches the stored scrypt hash
    public static boolean verify(String password, String storedHash) {
        String[] parts = storedHash.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[1]);

        byte[] actualHash = SCrypt.generate(password.getBytes(), salt, N, r, p, keyLength);
        return java.util.Arrays.equals(expectedHash, actualHash);
    }

    /* EXAMPLE


    public static void main(String[] args) {
        String password = "MyStrongPassword123";
        byte[] salt = generateSalt(16);

        String hash = generateHash(password, salt);
        System.out.println("Generated hash: " + hash);

        boolean match = verify("MyStrongPassword123", hash);
        System.out.println("Password correct? " + match);

        boolean wrong = verify("WrongPassword", hash);
        System.out.println("Wrong password? " + wrong);
    }



     */
}
