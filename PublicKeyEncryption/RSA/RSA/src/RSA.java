import java.math.BigInteger;
import java.util.Random;

public class RSA {
    public RSAKeyPair generateKeyPair(int bitLength) {
        Random random = new Random();
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger q = BigInteger.probablePrime(bitLength, random);

        // n = p * q
        BigInteger n = p.multiply(q);

        // phi(n) = (p - 1) * (q - 1)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = new BigInteger("65537");

        // d = e^-1 mod phi(n)
        BigInteger d = e.modInverse(phi);

        return new RSAKeyPair(
                new RSAPrivateKey(e, n),
                new RSAPublicKey(d, n));
    }

    public BigInteger encrypt(String message, RSAPublicKey publicKey) {
        // Convert message into bytes
        byte[] bytes = message.getBytes();

        BigInteger plainText = new BigInteger(bytes);

        // C = M^e mod n
        return plainText.modPow(
                publicKey.getExponent(),
                publicKey.getModulus());
    }

    public String decrypt(BigInteger cipherText, RSAPrivateKey privateKey) {
        BigInteger plainText = cipherText.modPow(
                privateKey.getExponent(),
                privateKey.getModulus());
        
        return new String(plainText.toByteArray());
    }
}