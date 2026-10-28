
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class AESDemo {

    
    public static SecretKey deriveKey(String password, byte[] salt)
            throws Exception {

        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                65536,
                128
        );

        byte[] keyBytes = factory.generateSecret(spec).getEncoded();

        return new SecretKeySpec(keyBytes, "AES");
    }

    // Encryption
    public static String[] encrypt(String plaintext, String password)
            throws Exception {

        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];
        random.nextBytes(salt);

        SecretKey secretKey = deriveKey(password, salt);

        byte[] ivBytes = new byte[16];
        random.nextBytes(ivBytes);

        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        Cipher cipher =
                Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] plaintextBytes =
                plaintext.getBytes(StandardCharsets.UTF_8);

        byte[] ciphertextBytes = cipher.doFinal(plaintextBytes);

        String b64Cipher = Base64.getEncoder()
                .encodeToString(ciphertextBytes);

        String b64Iv = Base64.getEncoder()
                .encodeToString(ivBytes);

        String b64Salt = Base64.getEncoder()
                .encodeToString(salt);

        return new String[]{
                b64Cipher,
                b64Iv,
                b64Salt
        };
    }

    // Decryption
    public static String decrypt(
            String b64Cipher,
            String b64Iv,
            String b64Salt,
            String password) throws Exception {

        byte[] salt = Base64.getDecoder()
                .decode(b64Salt);

        SecretKey secretKey = deriveKey(password, salt);

        byte[] ivBytes = Base64.getDecoder()
                .decode(b64Iv);

        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        Cipher cipher =
                Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] ciphertextBytes = Base64.getDecoder()
                .decode(b64Cipher);

        byte[] plainBytes = cipher.doFinal(ciphertextBytes);

        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    // Main method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter plaintext: ");
            String plaintext = sc.nextLine();

            System.out.print("Enter password: ");
            String password = sc.nextLine();

            String[] encrypted = encrypt(plaintext, password);

            String ciphertext = encrypted[0];
            String iv = encrypted[1];
            String salt = encrypted[2];

            System.out.println("\n--- ENCRYPTION OUTPUTS ---");

            System.out.println("Ciphertext (Base64): " + ciphertext);
            System.out.println("IV (Base64): " + iv);
            System.out.println("Salt (Base64): " + salt);

            String recovered = decrypt(
                    ciphertext,
                    iv,
                    salt,
                    password
            );

            System.out.println("\n--- DECRYPTION OUTPUT ---");
            System.out.println("Recovered Plaintext: " + recovered);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
