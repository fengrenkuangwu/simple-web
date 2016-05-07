package com.leaf.base.utils;

/**
 * 加密解密
 */
import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESPlus {

    private static String strDefaultKey = "www.si-tech.com.cn";

    private Cipher encryptCipher = null;

    private Cipher decryptCipher = null;
    
    private static BASE64Decoder base64De = new BASE64Decoder();
    
    private static BASE64Encoder base64en = new BASE64Encoder();

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] hexStr2ByteArr(String
     * strIn) 互为可逆的转换过程
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */

    public static String byteArr2HexStr(byte[] arrB) throws Exception {

        int iLen = arrB.length;

        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍

        StringBuffer sb = new StringBuffer(iLen * 2);

        for (int i = 0; i < iLen; i++) {

            int intTmp = arrB[i];

            // 把负数转换为正数

            while (intTmp < 0) {

                intTmp = intTmp + 256;

            }

            // 小于0F的数需要在前面补0

            if (intTmp < 16) {

                sb.append("0");

            }

            sb.append(Integer.toString(intTmp, 16));

        }

        return sb.toString();

    }
    
	/**
	 * 将byte数组 转换为 使用 Base64 加密后的字符串
	 * 
	 * @param arrB
	 *            需要转换的byte[] 数组
	 * @return
	 * @throws Exception
	 */
	public static String byteArr2Base64(byte[] arrB) throws Exception {

		String strMi = base64en.encode(arrB);
		return strMi;
	}

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {

        byte[] arrB = strIn.getBytes();

        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2

        byte[] arrOut = new byte[iLen / 2];

        for (int i = 0; i < iLen; i = i + 2) {

            String strTmp = new String(arrB, i, 2);

            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);

        }

        return arrOut;

    }
    
	/**
	 * 将base64字符串转换为2进制byte数组
	 * 
	 * @param arrB
	 *            需要转换的base64字符串
	 * @return
	 * @throws Exception
	 */
	public static byte[] base642ByteArr(String strIn) throws Exception {

		byte[] byteMi = base64De.decodeBuffer(strIn);
		return byteMi;
	}

    /**
     * 默认构造方法，使用默认密钥
     * @throws Exception
     */

    public DESPlus() throws Exception {

        this(strDefaultKey);

    }

    /**
     * 指定密钥构造方法
     * @param strKey 指定的密钥
     * @throws Exception
     */

    public DESPlus(String strKey) throws Exception {

       // Security.addProvider(new com.sun.crypto.provider.SunJCE());

        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");

        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");

        decryptCipher.init(Cipher.DECRYPT_MODE, key);

    }

    /**
     * 加密字节数组
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */

    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);

    }

    /**
     * 加密字符串 (返回十六进制字符串)
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */

    public String encrypt(String strIn) throws Exception {

        return byteArr2HexStr(encrypt(strIn.getBytes()));

    }
    
    /**
     * Des加密字符串 (返回Base64字符串)
     * @param strIn 需要加密的字符串
     * @return  Base64字符串
     * @throws Exception
     */
    public String encrypt2Base64(String strIn) throws Exception {
    	return byteArr2Base64(encrypt(strIn.getBytes()));
    }
    

    /**
     * 解密字节数组
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */

    public byte[] decrypt(byte[] arrB) throws Exception {

        return decryptCipher.doFinal(arrB);

    }
    

    /**
     * 从 十六进制 解密字符串
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */

    public String decrypt(String strIn) throws Exception {

        return new String(decrypt(hexStr2ByteArr(strIn)));

    }
    
    /**
     * 从Base64 解密字符串
     * @param strIn Base64字符串
     * @return
     * @throws Exception
     */
    public String decryptFromBase64(String strIn) throws Exception {
    	return new String(decrypt(base642ByteArr(strIn)));
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */

    private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥

        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        
//      KeySpec spec = new DESKeySpec(arrBTmp);
//      SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
//		SecretKey secretKey = factory.generateSecret(spec);
		
        return key;

    }

    /*
     * public static void main(String[] args) { // TODO Auto-generated method stub try { String test
     * = "dbportal"; //DESPlus des = new DESPlus();//默认密钥 DESPlus des = new
     * DESPlus("leemenz");//自定义密钥 System.out.println("加密前的字符："+test);
     * System.out.println("加密后的字符："+des.encrypt(test));
     * System.out.println("解密后的字符："+des.decrypt(des.encrypt(test))); } catch (Exception e) { //
     * TODO: handle exception e.printStackTrace(); } }
     */

    public static void main(String[] args) throws Exception {
        String strOriginal = "1111";
        String strOp = "-de";
        String strKey = "";
        if (args.length == 3) {
            strOp = args[0];
            strOriginal = args[1];
            strKey = args[2];
        } else {
            // System.out.println("Wrong operater , try use \"java DESPlus -de|-en  key'the string you want to be Encrypted'\"");
            // System.out.println("Now do Encrypt with \"1111\"");
        }
        try {
            if (strOp.equals("-de")) {
                DESPlus des = new DESPlus(strKey);
                des.encrypt(strOriginal);
            } else if (strOp.equals("-en")) {
                DESPlus des = new DESPlus(strKey);
                des.encrypt(strOriginal);
            } else {
                // System.out.println("Wrong operater , try use \"java DES -de|-en  'the string you want to be Encrypted'\"");
                // System.out.println("Now do Encrypt with \"1111\"");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
