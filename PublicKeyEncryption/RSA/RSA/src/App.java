import java.math.BigInteger;

public class App {
    public static void main(String[] args) throws Exception {
        int bitLength = 1024;
        RSA rsa = new RSA();
        RSAKeyPair keyPair = rsa.generateKeyPair(bitLength);

        System.out.println("Public Key: " + keyPair.getPublicKey());
        System.out.println("Private Key:" + keyPair.getPrivateKey());

        String message = "Hello, World";
        BigInteger encryptedMsg = rsa.encrypt(message, keyPair.getPublicKey());
        String decryptedMsg = rsa.decrypt(encryptedMsg, keyPair.getPrivateKey());

        System.out.println("Encrypted Message: " + encryptedMsg);
        System.out.println("Decrypted Message: " + decryptedMsg);
    }
}
