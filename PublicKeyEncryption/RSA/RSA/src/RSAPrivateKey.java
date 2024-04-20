import java.math.BigInteger;

public class RSAPrivateKey {
    private final BigInteger exponent;
    private final BigInteger modulus;

    public RSAPrivateKey(BigInteger exponent, BigInteger modulus) {
        this.exponent = exponent;
        this.modulus = modulus;
    }

    public BigInteger getExponent() {
        return this.exponent;
    }

    public BigInteger getModulus() {
        return this.modulus;
    }
}
