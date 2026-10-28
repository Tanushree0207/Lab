import javax.crypto.Cipher; 
import javax.crypto.SecretKey; 
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;  
import javax.crypto.spec.IvParameterSpec; 
import java.nio.charset.StandardCharsets;  
import java.security.SecureRandom; 
import java.util.Base64; 
import java.util.Scanner; 
public class DESDemo { 
 // Generate DES key from password 
 public static SecretKey keyFromPassword(String password) 
 throws Exception { 
 byte[] rawKey = new byte[8]; 
 byte[] src = password.getBytes(StandardCharsets.UTF_8); //Convert password to bytes 
 // Use first 8 bytes of password 
 for (int i = 0; i < 8; i++) { 
 if (i < src.length) { 
 rawKey[i] = src[i];
 } else { 
 rawKey[i] = 0; 
 } 
 } 
 DESKeySpec desKeySpec = new DESKeySpec(rawKey); 
 SecretKeyFactory keyFactory = 
 SecretKeyFactory.getInstance("DES"); 
 return keyFactory.generateSecret(desKeySpec);  } 
 // DES Encryption 
 public static String[] encrypt(String plaintext,  SecretKey secretKey) 
 throws Exception { 
 Cipher cipher = 
 Cipher.getInstance("DES/CBC/PKCS5Padding"); 
 // Generate random 8-byte IV 
 byte[] ivBytes = new byte[8]; 
 SecureRandom random = new SecureRandom();  random.nextBytes(ivBytes); 
 IvParameterSpec iv = 
 new IvParameterSpec(ivBytes);
 cipher.init(Cipher.ENCRYPT_MODE, 
 secretKey, iv); 
 byte[] ciphertextBytes = 
 cipher.doFinal( 
 plaintext.getBytes(StandardCharsets.UTF_8)  ); 
 String ciphertext = 
 Base64.getEncoder() 
 .encodeToString(ciphertextBytes); 
 String ivBase64 = 
 Base64.getEncoder() 
 .encodeToString(ivBytes); 
 return new String[]{ciphertext, ivBase64};  } 
 // DES Decryption 
 public static String decrypt(String ciphertext,  String ivBase64, 
 SecretKey secretKey) 
 throws Exception { 
 Cipher cipher = 
 Cipher.getInstance("DES/CBC/PKCS5Padding");
 byte[] ivBytes = 
 Base64.getDecoder() 
 .decode(ivBase64); 
 IvParameterSpec iv = 
 new IvParameterSpec(ivBytes); 
 cipher.init(Cipher.DECRYPT_MODE,  secretKey, iv); 
 byte[] ciphertextBytes = 
 Base64.getDecoder() 
 .decode(ciphertext); 
 byte[] plaintextBytes = 
 cipher.doFinal(ciphertextBytes); 
 return new String( 
 plaintextBytes, 
 StandardCharsets.UTF_8  ); 
 } 
 // Main method 
 public static void main(String[] args) {  Scanner sc = new Scanner(System.in);
 try { 
 System.out.print("Enter plaintext: ");  
String plaintext = sc.nextLine(); 
 System.out.print( 
 "Enter password (minimum 8 characters): "  ); 
 String password = sc.nextLine(); 
 // Generate DES key 
 SecretKey secretKey = 
 keyFromPassword(password); 
 // Encryption 
 String[] result = 
 encrypt(plaintext, secretKey); 
 String ciphertext = result[0]; 
 String iv = result[1]; 
 System.out.println( 
 "\n--- ENCRYPTION OUTPUTS ---"  ); 
 System.out.println( 
 "Ciphertext (Base64): "
 + ciphertext 
 ); 
 System.out.println( 
 "IV (Base64): " + iv 
 ); 
 // Decryption 
 String recovered = 
 decrypt(ciphertext, 
 iv, 
 secretKey); 
 System.out.println( 
 "\n--- DECRYPTION OUTPUT ---"  ); 
 System.out.println( 
 "Recovered Plaintext: " 
 + recovered 
 ); 
 } catch (Exception e) { 
 System.out.println( 
 "Error: " + e.getMessage()  ); 
 }
 sc.close(); 
 } 
} 
