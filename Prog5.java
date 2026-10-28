import java.security.KeyPair; 
import java.security.KeyPairGenerator; 
import java.security.SecureRandom; 
import java.security.interfaces.RSAPublicKey; 
import java.security.interfaces.RSAPrivateKey; 
import javax.crypto.Cipher; 
public class RSAEncryption { 
// Helper function to convert byte array to hexadecimal 
public static String bytesToHex(byte[] bytes) { 
StringBuilder hexString = new StringBuilder(); 
for (byte b : bytes) { 
hexString.append(String.format("%02X", b)); 
} 
return hexString.toString(); 
} 
public static void main(String[] args) { 
try {

KeyPairGenerator keyPairGenerator = 
KeyPairGenerator.getInstance("RSA"); 
SecureRandom secureRandom = new SecureRandom(); keyPairGenerator.initialize(2048, secureRandom); KeyPair keyPair = keyPairGenerator.generateKeyPair(); 
RSAPublicKey publicKey = 
(RSAPublicKey) keyPair.getPublic(); 
RSAPrivateKey privateKey = 
(RSAPrivateKey) keyPair.getPrivate(); 
System.out.println("========== RSA KEY PAIR =========="); 
System.out.println("\nPublic Key:"); 
System.out.println(publicKey); 
System.out.println("\nPrivate Key:"); 
System.out.println(privateKey);

String message = "Hello, RSA Encryption!"; 
System.out.println("\n========== ORIGINAL MESSAGE =========="); System.out.println("Original Message: " + message); 

Cipher encryptCipher = Cipher.getInstance("RSA"); 
encryptCipher.init( 
Cipher.ENCRYPT_MODE, 
publicKey 
); 
byte[] messageBytes = message.getBytes(); 
byte[] encryptedBytes = 
encryptCipher.doFinal(messageBytes); 
System.out.println("\n========== ENCRYPTION =========="); System.out.println("Encrypted Message (HEX):");
System.out.println(bytesToHex(encryptedBytes)); 

Cipher decryptCipher = Cipher.getInstance("RSA"); 
decryptCipher.init( 
Cipher.DECRYPT_MODE, 
privateKey 
); 
byte[] decryptedBytes = 
decryptCipher.doFinal(encryptedBytes); 
String decryptedMessage = 
new String(decryptedBytes); 
System.out.println("\n========== DECRYPTION =========="); 
System.out.println( 
"Decrypted Message: " + decryptedMessage 
); 
} catch (Exception e) {
System.out.println("Error: " + e.getMessage()); 
e.printStackTrace(); 
} 
} 
} 
