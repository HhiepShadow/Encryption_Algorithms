public class App {
    public static void main(String[] args) throws Exception {
        String key = "021D3D04A490B5A4C91A4F85112A5B55";
        String m = "7BB88955B6E87E91095C2A880F983F46";
        AES aes = new AES(key.getBytes());
        byte[] cipher = aes.ECB_encrypt(m.getBytes());
        System.out.println(cipher);
    }
}
