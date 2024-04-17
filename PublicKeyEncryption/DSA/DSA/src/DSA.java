import java.math.*;
/**
 * Digital Signature Algorithm
 */
public class DSA {
    private long H_M;
    private long p;
    private long q;
    private long h;
    private long xA;
    private long k;

    public DSA(long H_M, long p, long q, long h, long xA, long k) {
        this.H_M = H_M;
        this.p = p;
        this.q = q;
        this.h = h;
        this.xA = xA;
        this.k = k;
    }

    private long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        base = base % modulus;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent /= 2;
        }

        return result;
    }
    
    private long inversionExtendedEuclid(long b, long m) throws Exception{
        long A1 = 0;
        long A2 = m;
        long B1 = 1;
        long B2 = b;

        while (B2 != 0 || B2 != 1) {
            if (B2 == 0) {
                throw new Exception("No inverse");
            }
            if (B2 == 1) {
                if (B1 < 0) {
                    B1 += m;
                }
                return B1;
            }

            long Q = (long) A2 / B2;
            long T1 = A1 - Q * B1;
            long T2 = A2 - Q * B2;

            A1 = B1;
            A2 = B2;

            B1 = T1;
            B2 = T2;
        }
        if (B1 < 0)
            B1 += m;

        return B1;
    }

    private long g() {
        long g = modularExponentiation(h, (p - 1) / q, p);

        return g;
    }

    public long yA() {
        long g = g();
        long yA = modularExponentiation(g, xA, p);

        return yA;
    }

    public long[] M() {
        long[] rs = new long[2];
        long g = g();
        long r = modularExponentiation(
                modularExponentiation(g, k, p),
                1,
                q);

        long s = modularExponentiation(
                (H_M + xA * r) / k,
                1,
                q);

        rs[0] = r;
        rs[1] = s;

        return rs;
    }
    
    public long w() throws Exception {
        long s = M()[1];
        long w = modularExponentiation(inversionExtendedEuclid(s, q), 1, q);
        return w;
    }
    
    public long u1() throws Exception {
        long w = w();
        long u1 = modularExponentiation(H_M * w, 1, q);
        return u1;
    }

    public long u2() throws Exception {
        long w = w();
        long r = M()[0];
        long u2 = modularExponentiation(r * w, 1, q);
        return u2;
    }

    public long v() throws Exception{
        long g = g();
        long u1 = u1();
        long gU1 = modularExponentiation(g, u1, p);
        long yA = yA();
        long u2 = u2();
        long yAU2 = modularExponentiation(yA, u2, p);

        long inside = modularExponentiation(gU1 * yAU2, 1, p);

        long v = modularExponentiation(inside, 1, q);

        return v;
    }
}