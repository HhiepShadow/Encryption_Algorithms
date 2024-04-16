public class Caesar {
    private int shift;

    public Caesar(int shift) {
        this.shift = shift;
    }

    public String encrypt(String plainText) {
        String cipherText = "";

        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            if (Character.isLetter(c)) {
                char encryptedChar = (char) (c + shift);
                if (Character.isUpperCase(c)) {
                    encryptedChar = (char) ((encryptedChar - 'A') % 26 + 'A');
                } else {
                    encryptedChar = (char) ((encryptedChar - 'a') % 26 + 'a');
                }
                cipherText += encryptedChar;
            } else {
                cipherText += c;
            }
        }

        return cipherText;
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);

            if (Character.isLetter(c)) {
                char decryptedChar = (char) (c - shift);

                if (Character.isUpperCase(c)) {
                    decryptedChar = (char) ((decryptedChar - 'A' + 26) % 26 + 'A');
                } else {
                    decryptedChar = (char) ((decryptedChar - 'a' + 26) % 26 + 'a');
                }

                plaintext.append(decryptedChar);
            } else {
                plaintext.append(c);
            }
        }

        return plaintext.toString();
    }
}