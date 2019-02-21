import java.util.Scanner;

class main {

    public static void main(String args[])
    {
        Crypto crypto = new Crypto("AES", "1011010110010010");
        String message = "Hello world!";
        byte[] cipherText = null;
        byte[] finalText = null;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a message: ");
        message = sc.nextLine();

        try
        {
            crypto.init();
            cipherText = crypto.encrypt(message.getBytes("UTF-8"));
            finalText = crypto.decrypt(cipherText);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String encrMessage = new String(cipherText);
        String dncrMessage = new String(finalText);
        System.out.println("Encrypted message");
        System.out.println(encrMessage);
        System.out.println("Decrypted message");
        System.out.println(dncrMessage);
    }
}
