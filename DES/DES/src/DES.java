import java.util.*;

/**
 * DES - Data Encryption Standard
 */
public class DES{
  private List<String> key_fromPC2_binary;
  private List<String> key_fromPC2_hexa;

  /**
   *  Initial Permutation Table 
  */
  private static final int[]
  IP = {
    58, 50, 42, 34, 26, 18, 10, 2,
    60, 52, 44, 36, 28, 20, 12, 4,
    62, 54, 46, 38, 30, 22, 14, 6,
    64, 56, 48, 40, 32, 24, 16, 8,
    57, 49, 41, 33, 25, 17, 9, 1,
    59, 51, 43, 35, 27, 19, 11, 3,
    61, 53, 45, 37, 29, 21, 13, 5,
    63, 55, 47, 39, 31, 23, 15, 7
  };

  /**
   * Final Permutation Table
   */
  private static final int[]
  FP = {
    40, 8, 48, 16, 56, 24, 64, 32,
    39, 7, 47, 15, 55, 23, 63, 31,
    38, 6, 46, 14, 54, 22, 62, 30,
    37, 5, 45, 13, 53, 21, 61, 29,
    36, 4, 44, 12, 52, 20, 60, 28,
    35, 3, 43, 11, 51, 19, 59, 27,
    34, 2, 42, 10, 50, 18, 58, 26,
    33, 1, 41,  9, 49, 17, 57, 25
  };

  /**
   * Expansion Permutation Table(E):
   */
  private static final int[]
  E = {
    32, 1,  2,  3,  4,  5,
    4,  5,  6,  7,  8,  9,
    8,  9, 10, 11, 12, 13,
    12, 13, 14, 15, 16, 17,
    16, 17, 18, 19, 20, 21,
    20, 21, 22, 23, 24, 25,
    24, 25, 26, 27, 28, 29,
    28, 29, 30, 31, 32,  1
  };

  /**
   * 8 S-Boxes:
   */
  private static final int[][][]
  S = new int[][][] {
    { // S1
      {14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7},
      {0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8},
      {4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0},
      {15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13}
    },

    { // S2
      {15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10},
      {3, 13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5},
      {0, 14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15},
      {13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9}
    },

    { // S3
      {10,  0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8},
      {13,  7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1},
      {13,  6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7},
      {1, 10, 13,  0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2,  12}
    },

    { // S4
      {7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15},
      {13,  8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9},
      {10,  6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4},
      {3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14}
    },

    { // S5
      {2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9},
      {14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6},
      {4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14},
      {11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3}
    },

    { // S6
      {12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11},
      {10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8},
      {9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6},
      {4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13}
    },

    { // S7
      {4, 11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1},
      {13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6},
      {1,  4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2},
      {6, 11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12}
    },

    { // S8
      {13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7},
      {1, 15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2},
      {7, 11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8},
      {2,  1, 14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11}
    }
  };

  /**
   * Permutation Table(P):
   */
  private static final int[] P = {
      16, 7,  20, 21, 29, 12, 28, 17,
      1,  15, 23, 26, 5,  18, 31, 10,
      2,  8,  24, 14, 32, 27,  3,  9,
      19, 13, 30,  6, 22, 11,  4, 25
  };

  /**
  * Permuted choice 1(PC1)
  */
  private static final int[]
  PC1 = {
    57, 49, 41, 33, 25, 17,  9,
    1, 58, 50, 42, 34, 26, 18,
    10,  2, 59, 51, 43, 35, 27,
    19, 11,  3, 60, 52, 44, 36,
    63, 55, 47, 39, 31, 23, 15,
    7, 62, 54, 46, 38, 30, 22,
    14,  6, 61, 53, 45, 37, 29,
    21, 13,  5, 28, 20, 12,  4
  };

  /**
   * Permuted Choice 2(PC2):
   */
  private static final int[]
  PC2 = {
    14, 17, 11, 24,  1,  5, 3, 28,
    15,  6, 21, 10, 23, 19, 12, 4,
    26,  8, 16, 7, 27, 20, 13,  2,
    41, 52, 31, 37, 47, 55, 30, 40,
    51, 45, 33, 48, 44, 49, 39, 56,
    34, 53, 46, 42, 50, 36, 29, 32
  };

  /**
   * Shift Bits Table for Keys:
   */
  private static final int[] shift_table = {
      1, 1, 2, 2,
      2, 2, 2, 2,
      1, 2, 2, 2,
      2, 2, 2, 1
  };

  /**
   * Constructor
   */
  public DES() {
    key_fromPC2_binary = new ArrayList<>();
    key_fromPC2_hexa = new ArrayList<>();
  }

  /**
   * XOR function
   * @param a and b should be in binary string
   */
  private String xor(String a, String b) {
    String res = "";
    for (int i = 0; i < a.length(); i++) {
      if (a.charAt(i) == b.charAt(i)) {
        res += "0";
      } else {
        res += "1";
      }
    }

    return res;
  }

  /**
   * Hexadecimal to binary conversion
   * @param msg(message): String
   * @return binary: String
   */
  public String hexa_to_binary(String msg) {
    Map<Character, String> map = new HashMap<>();
    String binary = "";

    map.put('0', "0000");
    map.put('1', "0001");
    map.put('2', "0010");
    map.put('3', "0011");
    map.put('4', "0100");
    map.put('5', "0101");
    map.put('6', "0110");
    map.put('7', "0111");
    map.put('8', "1000");
    map.put('9', "1001");
    map.put('A', "1010");
    map.put('B', "1011");
    map.put('C', "1100");
    map.put('D', "1101");
    map.put('E', "1110");
    map.put('F', "1111");

    for (int i = 0; i < msg.length(); i++) {
      binary += map.get(msg.charAt(i));
    }

    return binary;
  }

  /**
   * Binary to Hexadecimal conversion
   * @param msg(message): String
   * @return hexa: String
   */
  public String binary_to_hexa(String msg) {
    Map<String, Character> map = new HashMap<>();
    String hexa = "";

    map.put("0000", '0');
    map.put("0001", '1');
    map.put("0010", '2');
    map.put("0011", '3');
    map.put("0100", '4');
    map.put("0101", '5');
    map.put("0110", '6');
    map.put("0111", '7');
    map.put("1000", '8');
    map.put("1001", '9');
    map.put("1010", 'A');
    map.put("1011", 'B');
    map.put("1100", 'C');
    map.put("1101", 'D');
    map.put("1110", 'E');
    map.put("1111", 'F');

    for (int i = 0; i < msg.length(); i += 4) {
      String ch = "";
      ch += msg.charAt(i);
      ch += msg.charAt(i + 1);
      ch += msg.charAt(i + 2);
      ch += msg.charAt(i + 3);
      hexa += map.get(ch);
    }

    return hexa;
  }

  /**
   * Binary to Decimal conversion
   * @param msg(message): String
   * @return decimal: long
   */
  public long binary_to_decimal(String msg) {
    int decimal = 0;
    int power = 0;
    for (int i = msg.length() - 1; i >= 0; i--) {
      int digit = Character.getNumericValue(msg.charAt(i));
      decimal += digit * Math.pow(2, power);
      power++;
    }
    return decimal;
  }

  /**
   * Decimal to Binary conversion
   * @param decimal: int
   * @return binary: String
   */
  public String decimal_to_binary(int decimal) {
    StringBuilder binary = new StringBuilder();
    for (int i = 3; i >= 0; i--) {
      int bit = (decimal >> i) & 1;
      binary.append(bit);
    }
    return binary.toString();
  }

  /** 
   * Shifting the bits of the key towards left by nth shifts
   * @param 
   */
  public String shiftLeft(String key, int nth_shift) {
    String newKey = "";
    for (int i = 0; i < nth_shift; i++) {
      for (int j = 1; j < key.length(); j++) {
        newKey += key.charAt(j);
      }
      newKey += key.charAt(0);
      key = newKey;
      newKey = "";
    }

    return key;
  }

  /**
   * Step 1: IP - Initial Permutation
   */
  public String initialPermutation(String plainText, int[] initialPerm, int numberBits) {
    String permutation = "";
    for (int i = 0; i < numberBits; i++) {
      permutation += plainText.charAt(initialPerm[i] - 1);
    }
    return permutation;
  }

  public String encrypt(String msg, String key) {
    String cipherText = "";
    
    msg = hexa_to_binary(msg);

    msg = initialPermutation(msg, IP, 64);

    key = hexa_to_binary(key);

    key = initialPermutation(key, PC1, 56);

    String l = key.substring(0, 28);
    String r = key.substring(28, 56);

    String left = msg.substring(0, 32);
    String right = msg.substring(32, 64);

    for (int k = 0; k < 16; k++) {
      l = shiftLeft(l, shift_table[k]);
      r = shiftLeft(r, shift_table[k]);
      String newKey = l + r;

      String roundKey = initialPermutation(newKey, PC2, 48);
      key_fromPC2_binary.add(roundKey);
      key_fromPC2_hexa.add(binary_to_hexa(roundKey));

      //System.out.println("Round " + (k + 1) + ": " + roundKey);
    }

    for (int i = 0; i < 16; i++) {
      String rightExpand = initialPermutation(right, E, 48);
      String A = xor(rightExpand, key_fromPC2_binary.get(i));
      String sBoxString = "";
      for (int j = 0; j < 8; j++) {
        long row = binary_to_decimal(A.charAt(j * 6) + "" + A.charAt(j * 6 + 5));
        long column = binary_to_decimal(
            A.charAt(j * 6 + 1) + "" +
                A.charAt(j * 6 + 2) +
                A.charAt(j * 6 + 3) +
                A.charAt(j * 6 + 4));
        int valueSBox = S[j][(int) row][(int) column];
        sBoxString = sBoxString + decimal_to_binary(valueSBox);
      }

      sBoxString = initialPermutation(sBoxString, P, 32);

      String result = xor(left, sBoxString);
      left = result;

      if (i != 15) {
        String tmp = left;
        left = right;
        right = tmp;
      }
    }
    
    String combine = left + right;
    cipherText = initialPermutation(combine, FP, 64);
    
    return cipherText;
  }

  public static void main(String[] args) {
    String key = "F7918DFD6815020C";
    String msg = "D8B8217DA16D5B5F";
    DES des = new DES();
    System.out.println(des.binary_to_hexa(des.encrypt(msg, key)));
    //System.out.println(des.decimal_to_binary(2));
  }
}