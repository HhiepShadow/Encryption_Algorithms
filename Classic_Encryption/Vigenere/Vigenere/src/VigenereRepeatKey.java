/**
 * Vigenere
 */
public class VigenereRepeatKey {
    private String key;

    public VigenereRepeatKey(String key){
        this.key = key;
    }

    public String encrypt(String plainText) {
        String cipherText = "";
        int keyLength = key.length();
        for (int i = 0; i < plainText.length(); i++) {
            char plainCharacter = plainText.charAt(i);
            char keyCharacter = key.charAt(i % keyLength);

            if (Character.isLetter(plainCharacter)) {
                char encryptedChar = encryptCharacter(plainCharacter, keyCharacter);
                cipherText += encryptedChar;
            }
        }

        return cipherText;
    }

    public String decrypt(String cipherText) {
        String plainText = "";
        int keyLength = key.length();

        for (int i = 0; i < cipherText.length(); i++) {
            char cipherCharacter = cipherText.charAt(i);
            char keyCharacter = key.charAt(i % keyLength);

            if (Character.isLetter(cipherCharacter)) {
                char decryptedCharacter = decryptCharacter(cipherCharacter, keyCharacter);
                plainText += decryptedCharacter;
            } else {
                plainText += cipherCharacter;
            }
        }
        return plainText;
    }
    
    private char encryptCharacter(char plainCharacter, char keyCharacter) {
        int plainValue = Character.toUpperCase(plainCharacter) - 'A';
        int keyValue = Character.toUpperCase(keyCharacter) - 'A';
        int encryptedValue = (plainValue + keyValue) % 26;

        return (char) (encryptedValue + 'A');
    }

    private char decryptCharacter(char cipherCharacter, char keyCharacter) {
        int cipherValue = Character.toUpperCase(cipherCharacter) - 'A';
        int keyValue = Character.toUpperCase(keyCharacter) - 'A';
        int plainValue = (cipherValue - keyValue + 26) % 26;
        return (char) (plainValue + 'A');
    }
}