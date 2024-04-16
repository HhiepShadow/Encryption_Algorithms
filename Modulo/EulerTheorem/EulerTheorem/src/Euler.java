public class Euler {
    public static long calculateTotient(int n) {
        int phi = n;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                while (n % i == 0) {
                    n /= i;
                }
                phi -= phi / i;
            }
        }
        if (n > 1) {
            phi -= phi / n;
        }
        return phi;
    }

    public static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        base = base % modulus;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent = exponent / 2;
        }
        return result;
    }

    public static void main(String[] args) {
        int base = 4;
        int modulus = 13;

        long totient = calculateTotient(modulus);
        System.out.println(totient);
        long result = modularExponentiation(base, totient, modulus);

        System.out.println("Result: " + result);
    }
}