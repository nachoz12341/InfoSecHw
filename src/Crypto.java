import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;


public class Crypto
{
    String encryptionType;
    String aesKey = "1011010110010010";
    SecretKeySpec skeySpec;
    Cipher cipher;

    KeyPair keyPair;
    PublicKey pubKey;
    PrivateKey privateKey;

    byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    IvParameterSpec ivspec = new IvParameterSpec(iv);

    public Crypto(String encrType)
    {
        encryptionType=encrType;
    }

    public void init()
    {
        try
        {
            setEncryption(encryptionType);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public byte[] encrypt(byte[] message) throws Exception
    {
        if(encryptionType.equals("AES"))
            cipher.init(Cipher.ENCRYPT_MODE,skeySpec,ivspec);
        else
            if(encryptionType.equals("RSA"))
                cipher.init(Cipher.ENCRYPT_MODE,pubKey);

        return cipher.doFinal(message);
    }

    public byte[] decrypt(byte[] cipherText) throws Exception
    {
        if(encryptionType.equals("AES"))
            cipher.init(Cipher.DECRYPT_MODE,skeySpec,ivspec);
        else
            if(encryptionType.equals("RSA"))
                cipher.init(Cipher.DECRYPT_MODE,privateKey);

        return cipher.doFinal(cipherText);
    }

    private void setEncryption(String encryptionType) throws Exception
    {
        if(encryptionType.equals("AES"))
        {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            skeySpec = new SecretKeySpec(aesKey.getBytes("UTF-8"), "AES");
        }
        else
            if(encryptionType.equals("RSA"))
            {
                cipher = Cipher.getInstance("RSA");
                keyPair = buildKeyPair();
                pubKey=keyPair.getPublic();
                privateKey=keyPair.getPrivate();
            }
    }

    private KeyPair buildKeyPair() throws Exception
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.genKeyPair();
    }
}
