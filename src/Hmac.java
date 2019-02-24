import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hmac
{
    Mac mac;
    SecretKeySpec skeySpec;
    String hmacKey="This is definitely a key";
    byte[] hashText=null;

    public Hmac()
    {

    }

    public void init() throws Exception
    {
        mac = Mac.getInstance("HmacSHA256");
        skeySpec = new SecretKeySpec(hmacKey.getBytes("UTF-8"), "HmacSHA256");
    }

    public byte[] hash(byte[] message) throws Exception
    {
        mac.init(skeySpec);
        hashText = mac.doFinal(message);
        return hashText;
    }
}
