/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec.encrypter;

import com.utils.codec.EncryptException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;

/**
 * 三重DES,针对Byte[]加密/解密
 * 
 * @author miaoqiang
 */
public class TriDESByteEncrypter implements Encrypter {
	private final StandardPBEByteEncryptor	encryptor;
	private static final Log				logger	= LogFactory.getLog(TriDESByteEncrypter.class);

	public TriDESByteEncrypter() {
		encryptor = new StandardPBEByteEncryptor();
		encryptor.setAlgorithm(Encrypter.TYPE.PBEWithMD5AndTripleDES.name());
		encryptor.setPassword(Encrypter.PASSWORD.INSKYE.name());
	}

	@Override
	public byte[] decrypt(byte[] encryptedMessage) throws EncryptException {
		try {
			return encryptor.decrypt(encryptedMessage);
		} catch (Exception ex) {
			logger.error("解密异常", ex);
			throw new EncryptException(ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage());
		}
	}

	@Override
	public byte[] encrypt(byte[] message) throws EncryptException {
		try {
			return encryptor.encrypt(message);
		} catch (Exception ex) {
			logger.error("加密异常", ex);
			throw new EncryptException(ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage());
		}

	}

	@Override
	public String encrypt(String message) throws EncryptException {
		throw new EncryptException("this is Byte[] Digester, not support String");
	}

	@Override
	public String decrypt(String encryptedMessage) throws EncryptException {
		throw new EncryptException("this is Byte[] Digester, not support String");
	}

}
