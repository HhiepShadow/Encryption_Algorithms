public class App {
    public static void main(String[] args) throws Exception {
        String key = "deceptive";
        VigenereRepeatKey vigenereRepeatKey = new VigenereRepeatKey(key);
        VigenereAutoKey vigenereAutoKey = new VigenereAutoKey(key);

        System.out.println(vigenereAutoKey.encrypt("wearediscoveredsaveyourself"));
        System.out.println(vigenereRepeatKey.encrypt("wearediscoveredsaveyourself"));
    }
}
