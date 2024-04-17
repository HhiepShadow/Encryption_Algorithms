import java.util.*;

public class HillCipher {
    private String key;
    private static final int MOD = 26;

    public HillCipher(String key) {
        this.key = key;
    }

    // public String encrypt(String plainText) {
    //     List<Integer> plainTextVector = convertToVector(plainText);

    // }

    private List<Integer> convertToVector(String msg) {
        msg = msg.replaceAll("\\s+", "").toUpperCase();
        List<Integer> vector = new ArrayList<>();
        for (int i = 0; i < msg.length(); i++) {
            vector.add(charToValue(msg.charAt(i)));
        }
        return vector;
    }

    private String convertToString(List<Integer> vector) {
        String str = "";
        for (int i = 0; i < vector.size(); i++) {
            str += valueToChar(vector.get(i));
        }
        return str;
    }

    private List<Integer> multiplyMatrix(List<Integer> vector, List<List<Integer>> matrix) {
        List<Integer> result = new ArrayList<>(matrix.size());
        for (int i = 0; i < matrix.size(); i++) {
            result.add(0);
        }

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < vector.size(); j++) {
                int value = result.get(i) + vector.get(j) * matrix.get(i).get(j);
                result.set(i, value);
            }
            result.set(i, result.get(i) % MOD);

        }

        return result;
    }
    
    private List<List<Integer>> calculateInverseMatrix(List<List<Integer>> matrix) {
        int det = matrixDeterminant(matrix);
        int detInverse = multiplicativeInverse(det, 26);
        List<List<Integer>> adjugateMatrix = calculateAdjugateMatrix(matrix);
        List<List<Integer>> inverseMatrix = scalarMultiplyMatrix(adjugateMatrix, detInverse);

        return inverseMatrix;
    }

    private int matrixDeterminant(List<List<Integer>> matrix) {
        int n = matrix.size();

        /*
         * [a b   =  a*d - b*c
         *  c d]
         */
        if (n == 2) {
            return matrix.get(0).get(0) * matrix.get(1).get(1) - matrix.get(0).get(1) * matrix.get(1).get(0);
        }

        int determinant = 0;
        for (int i = 0; i < n; i++) {
            List<List<Integer>> subMatrix = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                subMatrix.add(new ArrayList<>());
            }

            for (int j = 1; j < n; j++) {
                for (int k = 0, l = 0; k < n; k++) {
                    if (k == i) {
                        continue;
                    }
                    subMatrix.get(j - 1).set(l, matrix.get(l).get(k));
                    l++;
                }
            }

            determinant += matrix.get(0).get(i) * matrixDeterminant(subMatrix) * (int) Math.pow(-1, i);
        }
        return determinant;
    }

    private int multiplicativeInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }

    private List<List<Integer>> calculateAdjugateMatrix(List<List<Integer>> matrix) {
        int n = matrix.size();
        List<List<Integer>> adjugateMatrix = new ArrayList<>();
        
    }

    public List<List<Integer>> getKeyMatrix() {
        int keyMatrixLength = (int) Math.sqrt(key.length());
        List<List<Integer>> keyMatrix = new ArrayList<>();
        for (int i = 0; i < keyMatrixLength; i++) {
            keyMatrix.add(new ArrayList<>());
        }
        int k = 0;

        for (int i = 0; i < keyMatrixLength; i++) {
            for (int j = 0; j < keyMatrixLength; j++) {
                keyMatrix.get(i).add(charToValue(key.charAt(k)));
                k++;
            }
        }

        return keyMatrix;
    }
    
    private int charToValue(char c) {
        return c - 'A';
    }

    private char valueToChar(int value) {
        return (char) (value + 'A');
    }

    public static void main(String[] args) {
        HillCipher hillCipher = new HillCipher("GYBNQKURP");
        List<List<Integer>> keyMatrix = hillCipher.getKeyMatrix();
        for (List<Integer> list : keyMatrix) {
            for (int value : list) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}