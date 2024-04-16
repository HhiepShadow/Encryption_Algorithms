import java.util.HashMap;
import java.util.Map;

public class SingleCharacterTable {
    private Map<Character, Character> encryptionTable;

    public SingleCharacterTable(String key) {
        encryptionTable = new HashMap<>();
        for (int i = 0; i < key.length(); i++) {
            char cUpperCase = (char) (i + 65);
            char cLowerCase = (char) (i + 97);
            encryptionTable.put(cLowerCase, key.charAt(i));
            encryptionTable.put(cUpperCase, key.charAt(i));
        }
    }

    public String encrypt(String plainText) {
        String cipherText = "";
        for (int i = 0; i < plainText.length(); i++) {
            if (Character.isLetter(plainText.charAt(i))) {
                cipherText += encryptionTable.get(plainText.charAt(i));
            }
        }
        return cipherText;
    }

    public Map<Character, Character> getEncryptionTable() {
        return encryptionTable;
    }
}