/**
 * $Id: CryptoUtils.java
 */
package com.utils;

import com.utils.codec.DefaultEncryptService;
import com.utils.codec.EncryptService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;


/**
 * 数字加解密
 */
public class CryptoUtils {
	public static <T> String e1(T input) {
		// EncryptService encrypt = SpringContextUtils
		// .getBeanByClazz(DefaultEncryptService.class);
		EncryptService encrypt = new DefaultEncryptService();
		return encrypt.encrypt(StringUtils.trimToEmpty(input.toString()),
				EncryptService.ENCRYPT_ALGORITHM.PBEWithMD5AndDES.name());
	}

	public static <T> long d1(T input) {
		// EncryptService encrypt = SpringContextUtils
		// .getBeanByClazz(DefaultEncryptService.class);
		EncryptService encrypt = new DefaultEncryptService();
		return Long.valueOf(encrypt.decrypt(StringUtils.trimToEmpty(input.toString()),
				EncryptService.ENCRYPT_ALGORITHM.PBEWithMD5AndDES.name()));
	}

	public static <T> String e2(T input) {
		EncryptService encrypt = new DefaultEncryptService();
		return encrypt.encrypt(StringUtils.trimToEmpty(input.toString()),
				EncryptService.ENCRYPT_ALGORITHM.PBEWithMD5AndDES.name());
	}

	public static <T> String d2(T input) {
		EncryptService encrypt = new DefaultEncryptService();
		return encrypt.decrypt(StringUtils.trimToEmpty(input.toString()),
				EncryptService.ENCRYPT_ALGORITHM.PBEWithMD5AndDES.name());
	}

	/**
	 * 针对前台ID加密
	 */
	public static <T> String encrypt(T input) {
		String input0 = Long.toHexString(Long.valueOf(input.toString()));
		String input1 = StringUtils.leftPad(input0, input0.length() + 2, DigestUtils.md5Hex(input0).substring(0, 2));
		return Crypto5b.encode(input1.getBytes());
	}

	/**
	 */
	public static <T> long decrypt(T input) {
		try {
			String output = new String(Crypto5b.decode(input.toString()));
			long output0 = Long.valueOf(output.substring(2), 16);
			if (DigestUtils.md5Hex(output.substring(2)).substring(0, 2).equals(output.substring(0, 2))) {
				return output0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 用于cookie加密
	 *
	 * @param <T>
	 * @param input
	 * @return
	 */
	public static <T> String eStr(T input) {
		return CryptoUtils.e1(input);
	}

	/**
	 * 用于cookie解密
	 *
	 * @param <T>
	 * @param input
	 * @return
	 */
	public static <T> long dStr(T input) {
		return CryptoUtils.d1(input);
	}

	public static void main(String[] args) {
		//http://192.168.27.237:9080/membercenter/orders/insurance/180606922390/renew
		//http://192.168.27.237:9080/membercenter/orders/insurance/juvwh2mtpt2gh3oikh5a/renew
		//
		System.out.println(encrypt(180606922390L));
		System.out.println(encrypt(180606922388L));
	}
}
