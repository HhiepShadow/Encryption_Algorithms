import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {
    public static void main(String[] args) throws Exception {
        // Initialize p and g parameters
        BigInteger p = BigInteger.probablePrime(512, new SecureRandom());
        BigInteger g = new BigInteger("2");

        // A side chooses a random number 
        BigInteger a = generateRandomNumber(p.subtract(BigInteger.ONE));

        // A side chooses a random number
        BigInteger b = generateRandomNumber(p.subtract(BigInteger.ONE));

        // A = g^a mod p
        BigInteger A = g.modPow(a, p);

        // B = g^b mod p
        BigInteger B = g.modPow(b, p);

        // Share key from A: s = B^a mod p
        BigInteger sA = B.modPow(a, p);

        // Share key from B: s = A^b mod p
        BigInteger sB = A.modPow(a, p);

        if (sA.equals(sB)) {
            System.out.println("Shared Key: " + sA);
        } else {
            System.out.println("Wrong key");
        }

    }
    
    private static BigInteger generateRandomNumber(BigInteger max) {
        SecureRandom random = new SecureRandom();
        BigInteger result;

        do {
            result = new BigInteger(max.bitLength(), random);
        } while (result.compareTo(max) >= 0);

        return result;
    }
}
