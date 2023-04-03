import java.util.ArrayList;
import java.util.List;

public class attack {

    // Frequency table for the English language
    public static final double[] ENGLISH_FREQUENCIES = {
        0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015,  // A-G
        0.06094, 0.06966, 0.00153, 0.00772, 0.04025, 0.02406, 0.06749,  // H-N
        0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758,  // O-U
        0.00978, 0.02360, 0.00150, 0.01974, 0.00074                     // V-Z
    };
    public static final double[] PORTUGUESE_FREQUENCIES ={
        0.14634, 0.01043, 0.03882, 0.04992, 0.012570, 0.01023, 0.01303, // A-G
        0.00781, 0.06186, 0.00397, 0.00015, 0.02779, 0.04738, 0.04446, // H-N
        0.09735, 0.02523, 0.01204, 0.06530, 0.06805, 0.04336, 0.03639,// O-U
        0.01575, 0.00037, 0.00253, 0.00006, 0.00470// V-Z
    };

    public static void Frequencyattack(String ciphertext) {
    //public static void main(String[] args) {
        //String ciphertext = "XYZXYZ";

        // Try to determine the key length via the Kasiski examination method.
        List<Integer> keyLength = getKeyLength(ciphertext);

        // Divide the ciphertext into separate blocks, with each block corresponding
        // to a different letter in the key

        for (int i = 0; i < keyLength.size(); i++) {
            String[] blocks = getBlocks(ciphertext, keyLength.get(i));

        // Try to determine the shift for each block by performing a single-letter
        // substitution cipher attack and comparing the resulting frequencies to
        // the known frequencies in the frequency table
            int[] shifts = new int[keyLength.get(i)];
            for (int j = 0; j < keyLength.get(i); j++) {
                int[] blockFrequencies = getFrequencies(blocks[j]);
                shifts[j] = getShift(blockFrequencies, ENGLISH_FREQUENCIES);
                System.out.println("Shift for block " + j + ": " + shifts[j]);
            }

        // Use the shifts to determine the key used to encrypt the ciphertext
            String key = getKey(shifts);
            System.out.println("Key: " + key);
        }
        //return key;
    }

    // Calculate the frequencies of each letter in the given text
    private static int[] getFrequencies(String text) {
        int[] frequencies = new int[26];
        for (char c : text.toCharArray()) {
            int index = c - 'A';
            if (index >= 0 && index < 26) {
                frequencies[index]++;
            }
        }
        return frequencies;
    }

    //Try to determine the lenght via the Kasiski method.
    private static List<Integer> getKeyLength(String ciphertext) {
        // Identify repeating sequences of characters in the ciphertext
        List<Integer> distances = new ArrayList<>();
        for (int i = 0; i < ciphertext.length() - 3; i++) {
            String sequence = ciphertext.substring(i, i + 3);
            int index = ciphertext.indexOf(sequence, i + 3);
            while (index != -1) {
                distances.add(index - i);
                index = ciphertext.indexOf(sequence, index + 1);
            }
        }

        // Calculate the GCD of the distances between the repeated sequences
        int gcd = distances.get(0);
        for (int i = 1; i < distances.size(); i++) {
            gcd = getGCD(gcd, distances.get(i));
        }

        // Divide the length of the ciphertext by the GCD to get a list of potential key lengths
        int ciphertextLength = ciphertext.length();
        List<Integer> keyLengths = new ArrayList<>();
        for (int i = 1; i <= gcd; i++) {
            if (ciphertextLength % i == 0) {
                keyLengths.add(i);
            }
        }

        System.out.println("Potential key lengths: " + keyLengths);
        return keyLengths;
    }

    // Divide the ciphertext into separate blocks, with each block corresponding
    // to a different letter in the key
    private static String[] getBlocks(String text, int keyLength) {
        String[] blocks = new String[keyLength];
        for (int i = 0; i < keyLength; i++) {
            blocks[i] = "";
        }
        for (int i = 0; i < text.length(); i++) {
            blocks[i % keyLength] += text.charAt(i);
        }
        return blocks;
    }
    // Try to determine the shift for each block by performing a single-letter
    // substitution cipher attack and comparing the resulting frequencies to
    // the known frequencies in the frequency table
    private static int getShift(int[] frequencies, double[] referenceFrequencies) {
        int shift = 0;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < frequencies.length; i++) {
            double distance = 0;
            for (int j = 0; j < frequencies.length; j++) {
                distance += Math.abs(frequencies[j] - referenceFrequencies[(j + i) % frequencies.length]);
            }
            if (distance < minDistance) {
                minDistance = distance;
                shift = i;
            }
        }
        return shift;
    }

    // Use the shifts to determine the key used to encrypt the ciphertext
    private static String getKey(int[] shifts) {
        StringBuilder key = new StringBuilder();
        for (int shift : shifts) {
            char c = (char)('A' + (shift % 26));
            if (shift > 26) {
                c = (char)(c - 26);
            }
            key.append(c);
        }
        return key.toString();
    }
    

    public static int getGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}

