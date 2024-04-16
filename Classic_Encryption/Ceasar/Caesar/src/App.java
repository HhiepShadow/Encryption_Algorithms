import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String plainText = scanner.nextLine();
        int shift = scanner.nextInt();

        Caesar caesar = new Caesar(shift);
        String cipherText = caesar.encrypt(plainText);
        System.out.println(cipherText);

        String decryptedText = caesar.decrypt(cipherText);
        System.out.println(decryptedText);
        scanner.close();
    }
}
