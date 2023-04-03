import attack.Frequencyattack;
import java.util.Scanner;

public class App {


    public static char[][] gettable() {
        char[][] table = new char[26][26];

        for(int row=0; row<26; row++) {
            for(int col=0; col<26; col++){

                table[row][col] = (char) ((row + col) % 26 + 'A');

            };
        };


        return table;
    };

    public static String vigenere(String message, String keyword, boolean encrypt, char[][] vigenereAlphabetTable) {
        message = message.toUpperCase();
        keyword = keyword.toUpperCase();
        message =  message.replaceAll("[^A-Z]", "");
        keyword = keyword.replaceAll("[^A-Z]", "");
    
        // Initialize an empty string to store the encrypted message    
        String result = "";
    
        // Loop through the characters in the message
        for (int i = 0; i < message.length(); i++) {
            // Get the current character in the message
            char currentChar = message.charAt(i);
    
            // Get the corresponding character in the keyword
            char currentKey = keyword.charAt(i % keyword.length());
    
            // Calculate the shift value based on the current character in the keyword
            int shift = currentKey - 'A';
    
            // Use the Vigenère alphabet table to look up the encrypted/decrypted version of the current character in the message
            char encryptedChar;
            if (encrypt) {
                // Encrypt the current character
                encryptedChar = vigenereAlphabetTable[currentChar - 'A'][ shift];
            } else {
                // Decrypt the current character
                encryptedChar = vigenereAlphabetTable[currentChar - 'A'][(26 - shift) % 26];
            }
    
            // Append the encrypted/decrypted character to the result
            result += encryptedChar;
        }
    
        return result;
    }
    
    
    

    public static void main(String[] args) throws Exception {
        //char[][] vigenereAlphabetTable = gettable();

        // Encrypt a message
        //String encryptedMessage = vigenere("hello world", "keyword", true, vigenereAlphabetTable);
        //System.out.println(encryptedMessage);


        Scanner selection = new Scanner(System.in);
        System.out.println("enter c for encrypt, d for decrypt or f for frequency analysis");
        //System.out.println(vigenere("HGPLQ", "ABC", false, vigenereAlphabetTable);)

        String choice = selection.nextLine();  // Read user input
        System.out.println("You have choosen: " + choice);

        if (choice.contains("c")) { //solve encrypt
            System.out.println("Provide alphabet table? yes or no (case insensitive)");
            String alphabetbool = selection.nextLine(); //Read if user wishes to provide a n alphabet table or not
            System.out.println("You have choosen: " + alphabetbool + " Please, provide on separate lines, the message to be encrypted and the key");

            Scanner crypto = new Scanner(System.in);
            String originalmessage = crypto.nextLine();
            String key = crypto.nextLine();

            if (alphabetbool == "yes") {  //Encrypt with user-provided alphabet table in the form of a string
                System.out.println("Please provide the alphabet table");
                String stringtable = crypto.nextLine();
                char[] alphabettableoned = stringtable.toCharArray(); //turns string into char array
                char[][] vigenereAlphabetTable = new char[alphabettableoned.length][alphabettableoned.length]; //turns char array into the alphabet table
                for (int i = 0; i < alphabettableoned.length; i++) {
                    vigenereAlphabetTable[i][i] = alphabettableoned[i];
                }

                String encryptedMessage = vigenere(originalmessage, key, true, vigenereAlphabetTable);
                System.out.println("Your encrypted message is " + encryptedMessage);

            }
            else {
                char[][] vigenereAlphabetTable = gettable();
                String encryptedMessage = vigenere(originalmessage, key, true, vigenereAlphabetTable);
                System.out.println("Your encrypted message is " + encryptedMessage);
            }
        }

        else if ((choice.contains("d"))){ //solve decrypt
            System.out.println("Provide alphabet table? yes or no (case insensitive)");
            String alphabetbool = selection.nextLine(); //Read if user wishes to provide a n alphabet table or not
            System.out.println("You have choosen: " + alphabetbool + " Please, provide on separate lines, the message to be decrypted and the key");

            Scanner crypto = new Scanner(System.in);
            String encryptedmessage = crypto.nextLine();
            String key = crypto.nextLine();

            if (alphabetbool == "yes") {  //Encrypt with user-provided alphabet table in the form of a string
                System.out.println("Please provide the alphabet table");
                String stringtable = crypto.nextLine();
                char[] alphabettableoned = stringtable.toCharArray(); //turns string into char array
                char[][] vigenereAlphabetTable = new char[alphabettableoned.length][alphabettableoned.length]; //turns char array into the alphabet table
                for (int i = 0; i < alphabettableoned.length; i++) {
                    vigenereAlphabetTable[i][i] = alphabettableoned[i];
                }

                String originalmessage = vigenere(encryptedmessage, key, false, vigenereAlphabetTable);
                System.out.println("Your decrypted message is " + originalmessage);

            }
            else {
                char[][] vigenereAlphabetTable = gettable();
                String originalmessage = vigenere(encryptedmessage, key, false, vigenereAlphabetTable);
                System.out.println("Your decrypted message is " + originalmessage);
            }
        }
        else  {
            System.out.println("You have choosen analysis, please provided the cyphered text");
            Scanner crypto = new Scanner(System.in);
            String cypheredtext = crypto.nextLine();
            attack.Frequencyattack(cypheredtext);

        }
        
    }
}


