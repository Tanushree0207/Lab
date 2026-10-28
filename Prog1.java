import java.util.*;

public class rsa{
  public static String encrypt(String text, int shift){
     String result = "";
     for(int i=0;i<text.length();i++){
         char ch = text.charAt(i);
         if(Character.isUpperCase(ch)){
                 ch = (char)((ch-'A'+shift+26)%26+'A');
}
         else if(Character.isLowerCase(ch)){
                ch = (char)((ch-'a'+shift+26)%26+'a');
}
         result+=ch;
}
  return result;
}
public static String decrypt(String text, int shift){
         String result = "";
     for(int i=0;i<text.length();i++){
         char ch = text.charAt(i);
         if(Character.isUpperCase(ch)){
                 ch = (char)((ch-'A'-shift+26)%26+'A');
}
         else if(Character.isLowerCase(ch)){
                ch = (char)((ch-'a'-shift+26)%26+'a');
}
         result+=ch;
}
  return result;
}

public static void main (String[] args){
     Scanner sc = new Scanner(System.in);
     System.out.println("ENTER TEXT : ");
     String text = sc.nextLine();
     System.out.println("Shift : ");
     int shift = sc.nextInt();

     String encryptedtext = encrypt(text, shift);
     System.out.println("Encrypted text : " + encryptedtext);
    String decryptedtext = decrypt(text,shift);
     System.out.println("Decrypted text : " + decryptedtext);

sc.close();
}
}
