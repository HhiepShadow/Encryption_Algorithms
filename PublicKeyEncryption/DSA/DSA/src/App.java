public class App {
    public static void main(String[] args) throws Exception {
        DSA dsa = new DSA(7, 67, 11, 9, 2, 3);
        System.out.println(dsa.yA());

        System.out.println(dsa.M()[0] + " " + dsa.M()[1]);

        System.out.println(dsa.w());

        System.out.println(dsa.u1());

        System.out.println(dsa.u2());

        System.out.println(dsa.v());
    }
}
