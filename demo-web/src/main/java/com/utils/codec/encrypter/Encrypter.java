/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec.encrypter;


import com.utils.codec.EncryptException;

/**
 * 加密/解密器，采用固定的基于口令的密码
 * 
 * @author miaoqiang
 * @since 2010-09-17
 * @version 1.0
 */
public interface Encrypter {

	enum PASSWORD {
		INSKYE,COOKIEKEY
	}

	enum TYPE {
		PBEWithMD5AndDES,
		PBEWithMD5AndTripleDES
	}

	public byte[] encrypt(byte[] message) throws EncryptException;;

	public byte[] decrypt(byte[] encryptedMessage) throws EncryptException;;

	public String encrypt(String message) throws EncryptException;

	public String decrypt(String encryptedMessage) throws EncryptException;;
}
