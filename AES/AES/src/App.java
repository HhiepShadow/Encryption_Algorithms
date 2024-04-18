public class App {
    public static void main(String[] args) throws Exception {
        String key = "021D3D04A490B5A4C91A4F85112A5B55";
        String m = "7BB88955B6E87E91095C2A880F983F46";
        AES_128 aes = new AES_128(key);
        int[][] C = aes.encrypt(m);
        for (int[] s : C) {
            for (int c : s) {
                if (Integer.toHexString(c).length() == 1)
                    System.out.print("0" + Integer.toHexString(c));
                else
                    System.out.print(Integer.toHexString(c));
            }
        }
    }
}
