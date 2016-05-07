package com.leaf.base.utils;

/**
 * Created with IntelliJ IDEA.
 * User: sunhao
 * Date: 14-9-3
 * Time: 上午9:53
 * 加密工具类，包括MD5|SHA|AES
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	// 默认字符编码
	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * md5加密
	 * 
	 * @param inputText
	 *            输入字符
	 * @return 加密后十六进制字符串
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static String encodeMd5(String inputText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return encrypt(inputText, "md5", CHARSET_NAME);
	}

	/**
	 * md5加密
	 * 
	 * @param inputText
	 *            输入字符
	 * @param charsetName
	 *            字符编码
	 * @return 加密后十六进制字符串
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeMd5(String inputText, String charsetName)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return encrypt(inputText, "md5", charsetName);
	}

	/**
	 * sha加密
	 * 
	 * @param inputText
	 *            输入字符
	 * @return 加密后十六进制字符串
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeSHA(String inputText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return encrypt(inputText, "sha-1", CHARSET_NAME);
	}

	/**
	 * sha加密
	 * 
	 * @param inputText
	 *            输入字符
	 * @param charsetName
	 *            字符编码
	 * @return 加密后十六进制字符串
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeSHA(String inputText, String charsetName)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return encrypt(inputText, "sha-1", charsetName);
	}

	/**
	 * AES加密
	 * 
	 * @param inputText
	 *            输入字符
	 * @param password
	 *            加密密码
	 * @return 加密后十六进制字符串
	 * @see AES
	 */
	public static String encodeAES(String inputText, String password) {
		byte[] encryptResult = AES.encrypt(inputText, password);
		return AES.parseByte2HexStr(encryptResult);
	}

	/**
	 * AES解密
	 * 
	 * @param inputText
	 *            输入密文字符
	 * @param password
	 *            加密密码
	 * @return 解密后十进制字符
	 * @see AES
	 */
	public static String decodeAES(String inputText, String password) {
		byte[] decryptFrom = AES.parseHexStr2Byte(inputText);
		byte[] decryptResult = AES.decrypt(decryptFrom, password);
		return new String(decryptResult);
	}

	/**
	 * md5或者sha-1加密
	 *
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @param charsetName
	 *            指定加密字符编码，默认为UTF-8
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private static String encrypt(String inputText, String algorithmName, String charsetName)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (StringUtil.isSpaceOrNull(inputText)) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (StringUtil.isSpaceOrNull(charsetName)) {
			charsetName = CHARSET_NAME;
		}
		MessageDigest m = MessageDigest.getInstance(algorithmName);
		m.update(inputText.getBytes(charsetName));
		byte s[] = m.digest();
		return bytes2hex(s);
	}

	/**
	 * 字节数组转换为16进制字符串
	 * 
	 * @param arr
	 * @return
	 */
	private static String bytes2hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 测试主函数
	 * 
	 * @param args
	 *            空
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String svcCont = "<SvcCont><custOrderInfo><extCustOrderId>140825000101</extCustOrderId><acceptTime>20140825105410</acceptTime><oldExtCustOrderId>140729000059</oldExtCustOrderId><remark>孙皓测试</remark></custOrderInfo></SvcCont>";

		// md5加密测试
		String md5_1 = encodeMd5(svcCont);
		System.out.println(md5_1);
		System.out.println("md5 length: " + md5_1.length());// 32
		// sha加密测试
		String sha_1 = encodeSHA(svcCont);
		System.out.println(sha_1);
		System.out.println("sha length: " + sha_1.length());// 40
		// AES加密测试
		String AES_1 = encodeAES(svcCont, "sunhao");
		System.out.println(AES_1);
		System.out.println("AES_1 length: " + AES_1.length());// 448
		// AES解密测试
		String decodeCont = "20EB8566B27C88A921B10E5F10AF917740BCF76C2EC650D5AF4494EDF1DA31868DD9C8A4644CEDCF95BFD23AD80F090942757082613B5B3C70CDDB45C976920CDC580B6B83C1BE23AC9CDBA070482BB88A1576A0420BC8725A68A13CD62306DD05D2BA97D057A957BD80503B5D875575794EE19E228BDECE0BCF07312911614A0157BE8488C10B594F1E743C4D110A10AB3265EF03F891454D2F423B7C16FB88720E7BA4B45787E0FD7B23FA11B8A8CEEAB65B9093ED95A31233F58CB6D79CEA75DAF59C6CA7993BDE0281D767EF35A8313FB61BE1332E22107A21AD5ABF167D";
		String AES_2 = decodeAES(decodeCont, "sunhao");
		System.out.println(AES_2);
	}
}
