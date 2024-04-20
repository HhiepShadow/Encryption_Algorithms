import java.math.BigInteger;

public class RSAPublicKey {
    private final BigInteger exponent;
    private final BigInteger modulus;

    public RSAPublicKey(BigInteger exponent, BigInteger modulus) {
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
