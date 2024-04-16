/**
 * VigenereAutoKey
 */
public class VigenereAutoKey {
    private String key;

    public VigenereAutoKey(String key) {
        this.key = key;
    }
    
    public String encrypt(String plainText) {
        String cipherText = "";
        String expandedKey = expandKey(plainText);

        for (int i = 0; i < plainText.length(); i++) {
            char plainCharacter = plainText.charAt(i);
            char keyCharacter = expandedKey.charAt(i);
            if (Character.isLetter(plainCharacter)) {
                char encryptedCharacter = encryptCharacter(plainCharacter, keyCharacter);
                cipherText += encryptedCharacter;
            } else {
                cipherText += plainCharacter;
            }
        }
        
        return cipherText;

    }
    
    public String decrypt(String cipherText) {
        String plainText = "";
        String expandedKey = expandKey(key);

        for (int i = 0; i < cipherText.length(); i++) {
            char cipherCharacter = cipherText.charAt(i);
            char keyCharacter = expandedKey.charAt(i);

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
    
    private String expandKey(String plainText) {
        String expandedKey = key;

        for (int i = 0; i < plainText.length() - key.length(); i++) {
            expandedKey += plainText.charAt(i % plainText.length());
        }

        return expandedKey;
    }
}