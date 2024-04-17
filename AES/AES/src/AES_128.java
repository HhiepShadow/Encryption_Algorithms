import java.nio.charset.StandardCharsets;
import java.util.*;

public class AES_128 {
    private String key;
    private static final int[] RCON = {
            0x01, 0x00, 0x00, 0x00,
            0x02, 0x00, 0x00, 0x00,
            0x04, 0x00, 0x00, 0x00,
            0x08, 0x00, 0x00, 0x00,
            0x10, 0x00, 0x00, 0x00,
            0x20, 0x00, 0x00, 0x00,
            0x40, 0x00, 0x00, 0x00,
            0x80, 0x00, 0x00, 0x00,
            0x1B, 0x00, 0x00, 0x00,
            0x36, 0x00, 0x00, 0x00
    };

    private static final int[] SBOX = {
            0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
            0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
            0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
            0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
            0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
            0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
            0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
            0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
            0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
            0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
            0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
            0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x48, 0xBD, 0x8B, 0x8A,
            0x70, 0x3E, 0x85, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0x89, 0x86, 0xC1, 0x1D, 0x9E,
            0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
            0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
    };

    private static final int[][] MIX_COLUMN_MATRIX = {
        {0x02, 0x03, 0x01, 0x01},
        {0x01, 0x02, 0x03, 0x01},
        {0x01, 0x01, 0x02, 0x03},
        {0x03, 0x01, 0x01, 0x02}
    };

    public AES_128(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public int[][] splitKeyIntoWords(String key) {
        int keyLength = key.length();
        int wordLength = keyLength / 4;

        int[][] words = new int[4][4];

        for (int i = 0; i < 4; i++) {
            int startIndex = i * wordLength;
            int endIndex = (i + 1) * wordLength;
            String word = key.substring(startIndex, endIndex);
            //System.out.println(word);
            int wordSplitedLength = word.length() / 4;
            for (int j = 0; j < 4; j++) {
                int start = j * wordSplitedLength;
                int end = (j + 1) * wordSplitedLength;
                String splitWord = word.substring(start, end);
                
                words[i][j] = Integer.parseInt(splitWord, 16);
            }
        }
        return words;
    }

    private int getRoundCount(int keySize) {
        switch (keySize) {
            case 16:
                return 10;
            case 24:
                return 12;
            case 32:
                return 14;
            default:
                throw new IllegalArgumentException("Invalid key size");
        }
    }

    public int[] rotWord(int[] word) {
        int[] result = new int[word.length];
        result[0] = word[1];
        result[1] = word[2];
        result[2] = word[3];
        result[3] = word[0];
        return result;
    }

    public int[] subWord(int[] word) {
        int[] result = new int[word.length];
        for (int i = 0; i < word.length; i++) {
            result[i] = subByte(word[i]);
        }
        return result;
    }

    public int subByte(int b) {
        int index = b & 0xFF;
        return SBOX[index];
    }

    public int[] xorRCON(int[] subword, int nth_RCON) {
        int[] xcsw = new int[subword.length];
        for (int i = 0; i < subword.length; i++) {
            xcsw[i] = subword[i] ^ RCON[nth_RCON++];
        }
        return xcsw;
    }

    public int[][] calculateNewKey(int[] xcsw, int[][] words) {
        int[][] newKey = new int[4][4];
        for (int i = 0; i < 4; i++) {
            newKey[0][i] = xcsw[i] ^ words[0][i];
        }
        for (int i = 0; i < 4; i++) {
            newKey[1][i] = newKey[0][i] ^ words[1][i];
        }
        for (int i = 0; i < 4; i++) {
            newKey[2][i] = newKey[1][i] ^ words[2][i];
        }
        for (int i = 0; i < 4; i++) {
            newKey[3][i] = newKey[2][i] ^ words[3][i];
        }
        return newKey;
    }

    public int[][] addRoundKey(int[][] state, int[][] key) {
        int[][] newState = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newState[i][j] = state[i][j] ^ key[i][j];
            }
        }
        return newState;
    }

    public int[][] subByte(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = subByte(state[i][j]);
            }
        }
        return state;
    }

    // public int[][] changeRowsToColumns(int[][] state) {
    //     int rows = state.length;
    //     int columns = state[0].length;
    //     int[][] result = new int[rows][columns];

    //     for (int row = 0; row < rows; row++) {
    //         for (int column = 0; column < columns; column++) {
    //             result[row][column] = state[column][row];
    //         }
    //     }
    //     return result;
    // }

    public int[][] changeRowsToColumns(int[][] state) {
        int rows = state.length;
        int columns = state[0].length;

        int[][] result = new int[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                result[row][column] = state[column][row];
            }
        }

        return result;
    }

    public int[][] shiftRows(int[][] state) {
        int rows = state.length;
        int columns = state[0].length;

        state = changeRowsToColumns(state);

        int[][] newState = new int[rows][columns];

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                newState[row][col] = state[row][(col + row) % 4];
            }
        }

        return newState;
    }

    public int[][] mixColumns(int[][] state) {
        int[][] newState = new int[4][4];
        for (int col = 0; col < 4; col++) {
            newState[0][col] = multiply(0x02, state[0][col]) ^ multiply(0x03, state[1][col]) ^ state[2][col]
                    ^ state[3][col];
            newState[1][col] = state[0][col] ^ multiply(0x02, state[1][col]) ^ multiply(0x03, state[2][col])
                    ^ state[3][col];
            newState[2][col] = state[0][col] ^ state[1][col] ^ multiply(0x02, state[2][col])
                    ^ multiply(0x03, state[3][col]);
            newState[3][col] = multiply(0x03, state[0][col]) ^ state[1][col] ^ state[2][col]
                    ^ multiply(0x02, state[3][col]);
        }

        return newState;
    }

    public int multiply(int a, int b) {
        byte byteA = (byte) a;
        byte byteB = (byte) b;

        byte resultByte = 0;
        byte carry = 0;
        for (int i = 0; i < 8; i++) {
            if ((byteB & 1) != 0) {
                resultByte ^= byteA;
            }
            boolean byteACarry = (byteA & 0x80) != 0;
            byteA <<= 1;
            if (byteACarry) {
                byteA ^= 0x1B;
            }
            byteB >>= 1;
        }

        int result = resultByte >= 0 ? resultByte : 256 + resultByte;
        return result;
    }

    public static void main(String[] args) {
        AES_128 aes_128 = new AES_128("021D3D04A490B5A4C91A4F85112A5B55");

        int[][] splitWords = aes_128.splitKeyIntoWords(aes_128.getKey());

        int[] rotword = aes_128.rotWord(splitWords[3]);
        // for (int r : rotword) {
        //     System.out.println(r);
        // }

        int[] subword = aes_128.subWord(rotword);
        // for (int s : subword) {
        //     System.out.println(s);
        // }

        int[] xor = aes_128.xorRCON(subword, 0);
        // for (int x : xor) {
        //     System.out.println(x);
        // }

        int[][] newKey = aes_128.calculateNewKey(xor, splitWords);
        for (int[] key : newKey) {
            for (int k : key) {
                System.out.print(Integer.toHexString(k));
            }
        }

        System.out.println();

        String M = "7BB88955B6E87E91095C2A880F983F46";
        int[][] state = aes_128.splitKeyIntoWords(M);
        state = aes_128.addRoundKey(state, aes_128.splitKeyIntoWords(aes_128.getKey()));
        // for (int[] st : state) {
        //     for (int s : st) {
        //         System.out.println(s);
        //     }
        // }

        state = aes_128.subByte(state);
        // for (int[] st : state) {
        //     for (int s : st) {
        //         System.out.print(Integer.toHexString(s) + " ");
        //     }
        //     System.out.println();
        // }

        state = aes_128.shiftRows(state);
        // for (int[] st : state) {
        //     for (int s : st) {
        //         System.out.print(Integer.toHexString(s) + " ");
        //     }
        //     System.out.println();
        // }

        state = aes_128.mixColumns(state);
        // for (int[] st : state) {
        //     for (int s : st) {
        //         System.out.print(Integer.toHexString(s) + " ");
        //     }
        //     System.out.println();
        // }

        state = aes_128.addRoundKey(state, newKey);
        for (int[] st : state) {
            for (int s : st) {
                System.out.print(Integer.toHexString(s) + " ");
            }
            System.out.println();
        }
    }
}
