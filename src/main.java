import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class main {

    public static void main(String args[])
    {
        boolean stop = false;
        Scanner sc = new Scanner(System.in);
        String command = "";

        while(!stop)
        {
            System.out.println("Enter which part to run (1,..,6) or exit: ");
            command=sc.nextLine();

            if(command.equals("exit"))
                stop=true;
            else
            {
                int partNumber = Integer.parseInt(command);

                switch(partNumber)
                {
                    case 1:
                        partOne();
                        break;
                    case 2:
                        partTwo();
                        break;

                    case 3:
                        partThree();
                        break;

                    case 4:
                        partFour();
                        break;

                    case 5:
                        partFive();
                        break;

                    case 6:
                        partSix();
                        break;
                }
            }

            System.out.println("");
        }
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
        FileOutputStream fileOut;
        FileInputStream fileIn;

        Hmac hmac = new Hmac();
        byte[] hash = null;
        byte[] fileText = null;



        try
        {
            //Alice hashes message
            hmac.init();
            hash = hmac.hash(input.getBytes());
            System.out.println("Message hash: ");

            String hashString = DatatypeConverter.printHexBinary(hash);
            System.out.println(hashString);


            fileOut = new FileOutputStream("mactext.txt");
            fileOut.write((input+","+hashString).getBytes());
            System.out.println("Wrote message and hash to file");
            fileOut.close();

            //Bob
            fileIn = new FileInputStream("mactext.txt");
            fileText = new byte[fileIn.available()];
            fileIn.read(fileText);
            System.out.println("Read message and hash from file");

            int split=0;
            for(int i=0;i<fileText.length;i++)
                if(fileText[i]==',')
                    split=i;

            byte[] readMessage = Arrays.copyOfRange(fileText,0,split);
            byte[] readHash = Arrays.copyOfRange(fileText,split+1,fileText.length);
            byte[] newHash = hmac.hash(readMessage);

            System.out.println("Message matches saved hash: ");
            System.out.println(DatatypeConverter.printHexBinary(newHash).equals(new String(readHash)));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void partFive()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input message: ");
        String input = sc.nextLine();
        FileOutputStream fileOut;
        FileInputStream fileIn;

        Crypto crypto = new Crypto("RSA");
        byte[] cipherText = null;
        byte[] fileText = null;



        try
        {
            //Alice signs message
            crypto.init();
            cipherText = crypto.sign(input.getBytes());
            System.out.println("Message signature: ");

            String sigString = new String(cipherText);
            System.out.println(sigString);


            fileOut = new FileOutputStream("sigtext.txt");
            fileOut.write((input+","+sigString).getBytes());
            System.out.println("Wrote message and signature to file");
            fileOut.close();

            //Bob
            fileIn = new FileInputStream("sigtext.txt");
            fileText = new byte[fileIn.available()];
            fileIn.read(fileText);
            System.out.println("Read message and signature from file");

            int split=0;
            for(int i=0;i<fileText.length;i++)
            {
                if (fileText[i] == ',')
                {
                    split = i;
                    break;
                }
            }

            byte[] readMessage = Arrays.copyOfRange(fileText,0,split);
            byte[] readSig = Arrays.copyOfRange(fileText,split+1,fileText.length);
            byte[] newSig = crypto.sign(readMessage);//readMessage);

            System.out.println("Message matches saved hash: ");
            System.out.println(new String(newSig).equals(new String(readSig)));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void partSix()
    {
        Hmac hmac = new Hmac();
        Crypto rsa = new Crypto("RSA");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter message to be benchmarked: ");
        String input = sc.nextLine();

        byte[] hmacHashText = null;
        byte[] rsaSigText = null;
        byte[] rsaSigVerify = null;

        long hmacHash =  0;
        long rsaSign =  0;
        long rsaVerify = 0;


        try
        {
            hmac.init();
            rsa.init();

            //Hmac Hash generation
            hmacHash=System.nanoTime();
            for(int i=0; i<100;i++)
            {
                hmacHashText=hmac.hash(input.getBytes("UTF-8"));
            }
            hmacHash=(System.nanoTime()-hmacHash)/100;
            System.out.println("Average Hmac hash(ns): "+hmacHash);

            //RSA signature generation
            rsaSign=System.nanoTime();
            for(int i=0; i<100;i++)
            {
                rsaSigText=rsa.sign(input.getBytes("UTF-8"));
            }
            rsaSign=(System.nanoTime()-rsaSign)/100;
            System.out.println("Average RSA sign(ns): "+rsaSign);

            //RSA signature verification
            rsaVerify=System.nanoTime();
            for(int i=0; i<100;i++)
            {
                rsaSigVerify=rsa.sign(input.getBytes("UTF-8"));
                new String(rsaSigText).equals(new String(rsaSigVerify));
            }
            rsaVerify=(System.nanoTime()-rsaVerify)/100;
            System.out.println("Average RSA verification(ns): "+rsaVerify);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
