/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec;

/**
 * 加密/解密/摘要服务
 * 
 * @author miaoqiang
 * @since 2010-09-17
 * @version 1.0
 */
public interface EncryptService {

	enum DIGEST_ALGORITHM {
		MD5,
		SHA256
	}

	enum ENCRYPT_ALGORITHM {
		PBEWithMD5AndDES,
		PBEWithMD5AndTripleDES,
		InsCookie
	}

	/**
	 * 采用encrypterName指定的加密器，对plaintext进行加密
	 * 
	 * @param plaintext 需要加密的内容
	 * @param algorithm 加密器名称
	 * @throws EncryptException
	 */
	byte[] encrypt(byte[] plaintext, String algorithm) throws EncryptException;

	/**
	 * 采用encryterName指定的解密器，对cryptotext进行解密
	 * 
	 * @param cryptotext 需解密的内容
	 * @param algorithm 解密器名称
	 * @throws EncryptException
	 */
	byte[] decrypt(byte[] cryptotext, String algorithm) throws EncryptException;

	/**
	 * 采用encrypterName指定的加密器，对plaintext进行加密
	 * 
	 * @param plaintext 需要加密的内容
	 * @param algorithm 加密器名称
	 * @throws EncryptException
	 */
	String encrypt(String plaintext, String algorithm) throws EncryptException;

	/**
	 * 采用encryterName指定的解密器，对cryptotext进行解密
	 * 
	 * @param cryptotext 需解密的内容
	 * @param algorithm 解密器名称
	 * @throws EncryptException
	 */
	String decrypt(String cryptotext, String algorithm) throws EncryptException;

	/**
	 * 只加密,不Hex编码密文
	 * 
	 * @param plaintext
	 * @param algorithm
	 * @return
	 * @throws EncryptException
	 */
	String encryptWithoutHexEncode(String plaintext, String algorithm) throws EncryptException;

	/**
	 * 只解密, 不Hex解码密文
	 * 
	 * @param cryptotext
	 * @param algorithm
	 * @return
	 * @throws EncryptException
	 */
	String decryptWithoutHexDecode(String cryptotext, String algorithm) throws EncryptException;

	/**
	 * 采用encryterName指定的消息摘要器，对message进行消息摘要处理
	 * 
	 * @param message 需摘要的内容
	 * @param digesterName 摘要器名称
	 * @throws EncryptException
	 */
	public byte[] digest(byte[] message, String algorithm) throws DigestedException;

	/**
	 * 判断摘要是否匹配
	 * 
	 * @param message 需摘要的内容
	 * @param digest 之前的摘要处理过的内容
	 * @param digesterName 摘要器名称
	 * @throws EncryptException
	 */
	public boolean matches(byte[] message, byte[] digest, String algorithm) throws DigestedException;;

	/**
	 * 采用encryterName指定的消息摘要器，对message进行消息摘要处理
	 * 
	 * @param message 需摘要的内容
	 * @param digesterName 摘要器名称
	 * @throws EncryptException
	 */
	public String digest(String message, String algorithm) throws DigestedException;

	/**
	 * 判断摘要是否匹配
	 * 
	 * @param message 需摘要的内容
	 * @param digest 之前的摘要处理过的内容
	 * @param digesterName 摘要器名称
	 * @throws EncryptException
	 */
	public boolean matches(String message, String digest, String algorithm) throws DigestedException;;

}
