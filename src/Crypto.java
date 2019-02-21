import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto
{
    String encryptionType;
    String key;
    SecretKeySpec skeySpec;
    Cipher cipher;

    byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    IvParameterSpec ivspec = new IvParameterSpec(iv);

    public Crypto(String encrType, String inKey)
    {
        encryptionType=encrType;
        key=inKey;
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
        cipher.init(Cipher.ENCRYPT_MODE,skeySpec,ivspec);
        return cipher.doFinal(message);
    }

    public byte[] decrypt(byte[] cipherText) throws Exception
    {
        cipher.init(Cipher.DECRYPT_MODE,skeySpec,ivspec);
        return cipher.doFinal(cipherText);
    }

    private void setEncryption(String encryptionType) throws Exception
    {
        if(encryptionType.equals("AES"))
        {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        }
    }
}
