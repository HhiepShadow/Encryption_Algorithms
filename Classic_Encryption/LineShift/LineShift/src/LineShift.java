public class LineShift {
    public String encrypt(String plaintext, int rows, int columns) {
        StringBuilder ciphertext = new StringBuilder();
        int length = plaintext.length();
        int padding = rows * columns - length;
        plaintext += " ".repeat(padding);

        char[][] grid = new char[rows][columns];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = plaintext.charAt(index++);
            }
        }

        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                ciphertext.append(grid[i][j]);
            }
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, int rows, int columns) {
        StringBuilder plaintext = new StringBuilder();
        int length = ciphertext.length();

        char[][] grid = new char[rows][columns];
        int index = 0;

        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                grid[i][j] = ciphertext.charAt(index++);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                plaintext.append(grid[i][j]);
            }
        }

        return plaintext.toString().trim();
    }
}