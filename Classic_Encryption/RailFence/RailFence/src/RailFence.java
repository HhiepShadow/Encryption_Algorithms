public class RailFence {
    private int rails;

    public RailFence(int rails) {
        this.rails = rails;
    }

    public String encrypt(String plainText) {
        String cipherText = "";
        int length = plainText.length();

        char[][] fence = new char[rails][length];

        int rail = 0;
        int direction = 1;

        for (int i = 0; i < length; i++) {
            fence[rail][i] = plainText.charAt(i);
            rail += direction;

            if (rail == 0 || rail == rails - 1) {
                direction *= -1;
            }
        }

        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < length; j++) {
                if (fence[i][j] != '\u0000') {
                    cipherText += fence[i][j];
                }
            }
        }

        return cipherText;
    }

    public String decrypt(String cipherText) {
        String plainText = "";
        int length = cipherText.length();
        char[][] fence = new char[rails][length];
        int rail = 0;
        int direction = 1;

        for (int i = 0; i < length; i++) {
            fence[rail][i] = '*';
            rail += direction;

            if (rail == 0 || rail == rails - 1) {
                direction = -direction;
            }
        }

        int index = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < length; j++) {
                if (fence[i][j] == '*' && index < length) {
                    fence[i][j] = cipherText.charAt(index++);
                }
            }
        }

        rail = 0;
        direction = 1;
        for (int i = 0; i < length; i++) {
            cipherText += fence[rail][i];
            rail += direction;

            if (rail == 0 || rail == rails - 1) {
                direction = -direction;
            }
        }

        return plainText;
    }
}