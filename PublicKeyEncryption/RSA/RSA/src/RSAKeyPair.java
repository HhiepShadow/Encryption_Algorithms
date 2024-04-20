public class RSAKeyPair {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public RSAKeyPair(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return this.publicKey;
    }
}
