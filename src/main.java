import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class main {

    public static void main(String args[])
    {
        partFour();
    }

    private static void partOne()
    {
        String message = "";
        String encrMessage="";
        String dncrMessage="";
        Scanner sc = new Scanner(System.in);
        Crypto crypto = new Crypto("AES");
        FileOutputStream fileOut;
        FileInputStream fileIn;
        byte[] cipherText = null;
        byte[] cipherTextRead = null;
        byte[] finalText;


        System.out.println("Enter the message for Alice to encrypt: ");
        message = sc.nextLine();

        try
        {
            //Alice
            crypto.init();
            cipherText = crypto.encrypt(message.getBytes("UTF-8"));

            fileOut = new FileOutputStream("ctext.txt");
            fileOut.write(cipherText);
            System.out.println(cipherText);
            fileOut.close();

            encrMessage = new String(cipherText);
            System.out.println("Encrypted message");
            System.out.println(encrMessage);
            System.out.println("Ready for Bob to decrypt!");


            //Bob
            fileIn = new FileInputStream("ctext.txt");
            cipherTextRead = new byte[fileIn.available()];
            fileIn.read(cipherTextRead);
            fileIn.close();

            System.out.println("Read ciphertext from file.");
            finalText = crypto.decrypt(cipherTextRead);

            dncrMessage = new String(finalText);
            System.out.println("Decrypted message");
            System.out.println(dncrMessage);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void partTwo()
    {
        String message = "";
        String encrMessage="";
        Scanner sc = new Scanner(System.in);
        Crypto crypto = new Crypto("RSA");
        FileOutputStream fileOut;
        String dncrMessage="";
        FileInputStream fileIn;
        byte[] cipherText = null;
        byte[] cipherTextRead = null;
        byte[] finalText;


        System.out.println("Enter the message for Alice to encrypt: ");
        message = sc.nextLine();

        try
        {
            //Alice
            crypto.init();
            cipherText = crypto.encrypt(message.getBytes("UTF-8"));

            fileOut = new FileOutputStream("ctext.txt");
            fileOut.write(cipherText);
            System.out.println(cipherText);
            fileOut.close();

            encrMessage = new String(cipherText);
            System.out.println("Encrypted message");
            System.out.println(encrMessage);
            System.out.println("Ready for Bob to decrypt!");


            //Bob
            fileIn = new FileInputStream("ctext.txt");
            cipherTextRead = new byte[fileIn.available()];
            fileIn.read(cipherTextRead);
            fileIn.close();

            System.out.println("Read ciphertext from file.");
            finalText = crypto.decrypt(cipherTextRead);

            dncrMessage = new String(finalText);
            System.out.println("Decrypted message");
            System.out.println(dncrMessage);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void partThree()
    {
        Crypto aesCrypto = new Crypto("AES");
        Crypto rsaCrypto = new Crypto("RSA");
        aesCrypto.init();
        rsaCrypto.init();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter message to be benchmarked: ");
        String input = sc.nextLine();

        byte[] aesCipherText = null;
        byte[] rsaCipherText = null;

        long aesEncrypt =  0;
        long aesDecrypt =  0;
        long rsaEncrypt =  0;
        long rsaDecrypt =  0;


        try
        {
            //AES encrypt
            aesEncrypt=System.nanoTime();
            for(int i=0; i<100;i++)
            {
                aesCipherText=aesCrypto.encrypt(input.getBytes("UTF-8"));
            }
            aesEncrypt=(System.nanoTime()-aesEncrypt)/100;
            System.out.println("Average AES Encrypt(ns): "+aesEncrypt);

            //AES decrypt
            aesDecrypt=System.nanoTime();
            for(int i=0; i<100;i++)
            {
                aesCrypto.decrypt(aesCipherText);
            }
            aesDecrypt=(System.nanoTime()-aesDecrypt)/100;
            System.out.println("Average AES Decrypt(ns): "+aesDecrypt);

            //RSA encrypt
            rsaEncrypt=System.nanoTime();
            for(int i=0; i<100;i++)
            {
                rsaCipherText=rsaCrypto.encrypt(input.getBytes("UTF-8"));
            }
            rsaEncrypt=(System.nanoTime()-rsaEncrypt)/100;
            System.out.println("Average RSA Encrypt(ns): "+rsaEncrypt);

            //RSA decrypt
            rsaDecrypt=System.nanoTime();
            for(int i=0; i<100;i++)
            {
                rsaCrypto.decrypt(rsaCipherText);
            }
            rsaDecrypt=(System.nanoTime()-rsaDecrypt)/100;
            System.out.println("Average RSA Decrypt(ns): "+rsaDecrypt);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void partFour()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input message: ");
        String input = sc.nextLine();


    }
}
