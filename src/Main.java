import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sound.sampled.Clip;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

/**
 * Created By XuanRan on 2021/12/6
 */
public class Main {

    public static HashMap<Integer,String> hashMap = new HashMap<>();
    public static void main(String[] args) throws Exception {
        getKeyPair();
        //加密字符串
        String message = "XuanRan + Token = 111111";
        System.out.println("随机生成的公钥为:" + hashMap.get(0));
        System.out.println("随机生成的私钥为:" + hashMap.get(1));
        String messageEn = encodedFromRSA(message,hashMap.get(0));
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn,hashMap.get(1));
        System.out.println("还原后的字符串为:" + messageDe);
    }

    private static void getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048,new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKey = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
        hashMap.put(0,publicKey);
        hashMap.put(1,privateKey);
    }

    private static String encodedFromRSA(String info,String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey publicKey1 = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey1);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(info.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥验签
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}
