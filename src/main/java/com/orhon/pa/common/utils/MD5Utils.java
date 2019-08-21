/**
 * 
 */
package com.orhon.pa.common.utils;

import java.security.MessageDigest;

/**
 * @author Shawn
 *
 */
public class MD5Utils {
	private final static char[] hexDigits = "0123456789ABCDEF".toCharArray();
	private final static String MD5 = "MD5";

	public static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance(MD5);
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(MD5Utils.MD5("20121221"));
		System.out.println(MD5Utils.MD5("加密"));
	}

}
