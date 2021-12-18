import java.util.Map;

/**
 * Created By XuanRan on 2021/12/18
 */
public class Main {
    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
/*            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);*/
            publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPWX0aejCipBOxFMyqV/oA/ibkcibeMIzwbuDmQwBtfeSeYYon3FYWlGmfb7cR+J9MGPbf2uK+OCl2EZ5sz2Ch5z8i47BMl9ruun/Qt4ZRc0QGaYKG6APQ3K9Jbfx1u8EgFD3QLoTMzuXBGPyUIefeq9DfhaEYuQc4uPk3NrZ2+wIDAQAB";
            privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI9ZfRp6MKKkE7EUzKpX+gD+JuRyJt4wjPBu4OZDAG195J5hiifcVhaUaZ9vtxH4n0wY9t/a4r44KXYRnmzPYKHnPyLjsEyX2u66f9C3hlFzRAZpgoboA9Dcr0lt/HW7wSAUPdAuhMzO5cEY/JQh596r0N+FoRi5Bzi4+Tc2tnb7AgMBAAECgYAkSHZN2VeuEvGZBLa49wOkdK/LaslDu+4wfSAXqCXXkOHo5K9fX3m6xV3mqRQhTFilnjv+dRynIPf9loAO8n52m0XMv4BmyXiHV26srWPnlt8QPjyHK76BS92PBlNUaN8WEqDUOwH7imjeIC7XwIlgQcq0s1Jzhs8gH9trAbYDYQJBAMk0AybrcBYdZR5NETqZDb4ZLWr/zAFGBcsVALR/32O9qfi7/2oL6jHVZPFQOp2FOfjVzxDSRVswuDZjMdW2aCcCQQC2Y+SzvQxCC05421Wt7rsdc9PpgpW8HvE+b/QXttHJuwFJBSK/zuY/56u14ysLAQinsyumBVorHZI+IguYqosNAkBHCdEvFaNee4RvPWCe4tkiTDtn8uaxcGqtOD4mfJt/2MBBbQcSByDp9WENfnkjkp1USREx7DvGpZ30vfMjdKKzAkEAlvQMWBpyJ8M12l8mBYOdiB3SWOSESyOM5PFBo9LixMSqpqXCgd+av29dtXdjq4Lah/mJeanaW+7ol9YTpPIZjQJBALybi6jyuK2Cs7gIRJMCeuZEb8iBAxRFPk0oIHF20ExPtU8XzRLNfXLtF+hjAXjUsO2oRow/eayKMnZxyGde3ME=";
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        test();
        testSign();
    }

    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }
}
