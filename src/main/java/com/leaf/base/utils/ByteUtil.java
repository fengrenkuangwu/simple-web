package com.leaf.base.utils;

public class ByteUtil {

	private static String hexStr = "0123456789ABCDEF";

	private static String[] binaryArray = { "0000", "0001", "0010", "0011",
			"0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };

	/**
	 * 将 十进制的 num 存放在 byteNum 个 byte 数组中 <b> 如果 num 为正数 并且 转为 二进制后 的位数 大于
	 * byteNum*8 的位数 ，则会抛弃高位 <b> <b>
	 * 如果移动的位数超过了该类型的最大位数，那么编译器会对移动的位数取模。如对int型移动33位，实际上只移动了33%32=1位 <b>
	 * 
	 * @param num
	 * @param byteNum
	 * @return
	 * @throws 如果给定的byte数组不够存放
	 *             int类型的数据, 则抛错
	 */
	public static byte[] intConvertByteArray(long num, int byteNum) { //

		String binStr = Long.toString(num, 2); // 十进制转为二进制字符串
		int sig = Long.signum(num); // 是否为 负数 ?
		if (sig == -1) { // 负数 去掉 符号位
			binStr = binStr.substring(1, binStr.length());
		}
		int strLength = byteNum * 8;
		if (strLength < binStr.length()) {
			throw new RuntimeException("byteNum 位数不够存放 num 的二进制值");
		}

		byte[] bytes = new byte[byteNum];
		int j = 0;
		for (int i = byteNum - 1; i >= 0; i--) {
			bytes[j] = (byte) (num >> (i * 8));
			j++;
		}

		return bytes;
	}

	/**
	 * 不带符号的bytes 数组 转为 long类型
	 * 
	 * @param bytes
	 * @return
	 */
	public static long unsigByteArrayConvertInt(byte[] bytes) { // bytes <= 4 ;
																// 如果大于4 ， 则要把
																// bytes[i] 强转为
																// long

		long[] num = new long[bytes.length];
		int j = bytes.length - 1;
		for (int i = 0; i < bytes.length; i++) {
			num[i] = (bytes[i] & 0xFF) << (j * 8); // 与 0x000000ff 做 & 操作 去掉 高位
													// 是1的情况 ， 【负数的话 高位为1
													// ，直接位移会造成结果错误】
			j--;
		}

		long retnum = 0;
		for (int i = 0; i < num.length; i++) {
			retnum = retnum | num[i];
			// System.out.println(retnum);
		}
		return retnum;
	}

	/**
	 * 带符号 的 bytes 数组 转为 long 类型
	 * 
	 * @param bytes
	 * @return
	 */
	public static long sigByteArrayConvertInt(byte[] bytes) { // bytes <= 4 ;
																// 如果大于4 ， 则要把
																// bytes[i] 强转为
																// long

		long[] num = new long[8]; // 初始化 int 类型 的数组
		int j = bytes.length - 1;
		int k = 0;
		int byteLocation = 8 - bytes.length;
		for (int i = byteLocation; i < 8; i++) {
			num[i] = (bytes[k] & 0xFF) << (j * 8); // 与 0x000000ff 做 & 操作 去掉 高位
													// 是1的情况 ， 【负数的话 高位为1
													// ，直接位移会造成结果错误】
			j--;
			k++;
		}

		if ((bytes[0] & 0x80) == 0x80) { // 判断 高位 是正数还是负数 ，如果是负数
			int f = 1;
			for (int i = 0; i < byteLocation; i++) {
				num[i] = (-1 & 0xFF) << ((8 - f) * 8);
				f++;
			}
		}

		long retnum = 0;
		for (int i = 0; i < num.length; i++) {
			retnum = retnum | num[i];
			// System.out.println(retnum);
		}
		return retnum;
	}

	/**
	 * 二进制 数组 转为 16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String BinaryToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		int k = 0;
		for (byte b : bytes) {
			// char c01 = getChar((b & 0xF0) >> 4);
			char c01 = hexStr.charAt((b & 0xF0) >> 4);
			sb.append(c01);
			// char c02 = getChar(b & 0x0F);
			char c02 = hexStr.charAt(b & 0x0F);
			sb.append(c02);
			if (k != (bytes.length - 1)) {
				sb.append(' ');
			}
			k++;
		}
		return sb.toString();
	}

	/**
	 * 十六进制字符串转 二进制
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] HexStringToBinary(String hexString) {

		String hexStringFormat = hexString.replaceAll("\\s*", "");

		// hexString的长度对2取整，作为bytes的长度
		int len = hexStringFormat.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// 字节高四位
		byte low = 0;// 字节低四位

		for (int i = 0; i < len; i++) {
			// 右移四位得到高位
			high = (byte) ((hexStr.indexOf(hexStringFormat.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexStringFormat.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);// 高地位做或运算
		}

		return bytes;
	}

	/**
	 * 二进制数组转为字符串打印
	 * 
	 * @param bArray
	 * @return
	 */
	public static String bytes2BinaryStr(byte[] bArray) {

		String outStr = "";
		int pos = 0;
		for (byte b : bArray) {
			// 高四位
			pos = (b & 0xF0) >> 4;
			outStr += binaryArray[pos];
			// 低四位
			pos = b & 0x0F;
			outStr += binaryArray[pos];
		}
		return outStr;

	}

	public static char getChar(int b) {
		if (b >= 0 && b <= 9) {
			return (char) ('0' + b);
		}
		if (b >= 10 && b <= 15) {
			return (char) ('A' - 10 + b);
		}
		throw new IllegalArgumentException("error byte:" + b);
	}

	public static void main(String[] args) {

		byte[] bytes = intConvertByteArray(128, 1);
		for (int i = 0; i < bytes.length; i++) {
			System.out.println(" ===============  " + bytes[i]);
		}

		long retnum = unsigByteArrayConvertInt(bytes);
		System.out.println(" ==============retnum============== " + retnum);

		// System.out.println(Integer.toBinaryString(65280));

		byte b = 111;
		int a = b << 32;
		long c = (long) b << 32;
		System.out.println(a);
		System.out.println(c);

		System.out.println((char) ('0' + 3));

	}

}
