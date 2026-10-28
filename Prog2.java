import java.util.*; 
public class main { 
static char[][] matrix = new char[5][5]; 
// Generate Playfair Matrix 
static void generateMatrix(String key) { 
key = key.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I'); 
LinkedHashSet<Character> set = new LinkedHashSet<>(); 
for (char ch : key.toCharArray()) { 
set.add(ch); 
} 
for (char ch = 'A'; ch <= 'Z'; ch++) { 
if (ch != 'J') 
set.add(ch); 
} 
Iterator<Character> it = set.iterator(); 
for (int i = 0; i < 5; i++) { 
for (int j = 0; j < 5; j++) { 
matrix[i][j] = it.next(); 
} 
} 
} 
// Find Position 
static int[] findPosition(char ch) { 
if (ch == 'J') 
ch = 'I'; 
for (int i = 0; i < 5; i++) {
for (int j = 0; j < 5; j++) { 
if (matrix[i][j] == ch) { 
return new int[]{i, j}; 
} 
} 
} 
return null; 
} 
// Prepare Plaintext 
static String prepareText(String text) { 
text = text.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I'); 
StringBuilder prepared = new StringBuilder(); 
int i = 0; 
while (i < text.length()) { 
char first = text.charAt(i); 
char second; 
if (i + 1 < text.length()) { 
second = text.charAt(i + 1); 
if (first == second) { 
second = 'X'; 
i++; 
} else { 
i += 2; 
} 
} else { 
second = 'X'; 
i++; 
} 
prepared.append(first).append(second); 
} 
return prepared.toString(); 
} 
// Encrypt 
static String encrypt(String plaintext) { 
String text = prepareText(plaintext); 
StringBuilder cipher = new StringBuilder();
for (int i = 0; i < text.length(); i += 2) { 
char a = text.charAt(i); 
char b = text.charAt(i + 1); 
int[] posA = findPosition(a); 
int[] posB = findPosition(b); 
// Same Row 
if (posA[0] == posB[0]) { 
cipher.append(matrix[posA[0]][(posA[1] + 1) % 5]); cipher.append(matrix[posB[0]][(posB[1] + 1) % 5]); } 
// Same Column 
else if (posA[1] == posB[1]) { 
cipher.append(matrix[(posA[0] + 1) % 5][posA[1]]); cipher.append(matrix[(posB[0] + 1) % 5][posB[1]]); } 
// Rectangle Rule 
else { 
cipher.append(matrix[posA[0]][posB[1]]); 
cipher.append(matrix[posB[0]][posA[1]]); 
} 
} 
return cipher.toString(); 
} 
public static void main(String[] args) { 
Scanner sc = new Scanner(System.in); 
System.out.print("Enter keyword: "); 
String key = sc.nextLine(); 
generateMatrix(key); 
System.out.println("Playfair Matrix:"); 
for (int i = 0; i < 5; i++) { 
for (int j = 0; j < 5; j++) { 
System.out.print(matrix[i][j] + " "); 
}
System.out.println(); 
} 
System.out.print("Enter plaintext: "); 
String plaintext = sc.nextLine(); 
String cipher = encrypt(plaintext); 
System.out.println("Encrypted Text: " + cipher); 
sc.close(); 
} 
} 
