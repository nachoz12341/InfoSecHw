import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


class main {

    public static void main(String args[])
    {
        String messageString = "hello world!";
        String key = "1011010110010010";
        byte[] cipherText;
        byte[] finalMessage;



        try
        {
            byte[] message = messageString.getBytes("UTF-8");
            System.out.println("Starting Message");
            System.out.println(messageString);

            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            byte[] iv2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            IvParameterSpec ivspec2 = new IvParameterSpec(iv2);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,skeySpec,ivspec);
            cipherText = cipher.doFinal(message);
            System.out.println("Encrypted Message");
            System.out.println(String s = String(cipherText));

            Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher2.init(Cipher.DECRYPT_MODE,skeySpec,ivspec);
            finalMessage=cipher2.doFinal(cipherText);
            System.out.println("Decrypted Message");
            System.out.println(finalMessage.toString());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
