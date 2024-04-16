public class App {
    public static void main(String[] args) throws Exception {
        SingleCharacterTable singleCharacterTable = new SingleCharacterTable("DKVQFIBJWPESCXHTMYAUOLRGZN");

        String plainText = "ifwewishtoreplaceletters";
        String cipherText = singleCharacterTable.encrypt(plainText);
        System.out.println(cipherText);
    }
}
