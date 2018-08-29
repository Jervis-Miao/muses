/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec;

import com.utils.codec.digester.ByteDigesterFactory;
import com.utils.codec.digester.Digester;
import com.utils.codec.digester.StringDigesterFactory;
import com.utils.codec.encrypter.ByteEncrypterFactory;
import com.utils.codec.encrypter.Encrypter;
import com.utils.codec.encrypter.StringEncrypterFactory;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 加密/解密/摘要服务 实现类
 * 
 * @author miaoqiang
 * @since 2010-09-17
 * @version 1.0
 */

enum DEFAULT_DIGESTER {
	MD5
}

enum DEFAULT_ENCRYPTER {
	PBEWithMD5AndDES
}

@Service
public class DefaultEncryptService implements EncryptService {
	private static final Log	logger	= LogFactory.getLog(DefaultEncryptService.class);

	@Override
	public byte[] encrypt(byte[] plaintext, String algorithm) throws EncryptException {
		final Encrypter encrypt = getByteEncrypt(algorithm);
		return encrypt.encrypt(plaintext);
	}

	@Override
	public byte[] decrypt(byte[] cryptotext, String algorithm) throws EncryptException {
		final Encrypter encrypt = getByteEncrypt(algorithm);
		return encrypt.decrypt(cryptotext);
	}

	@Override
	public String decrypt(String cryptotext, String algorithm) throws EncryptException {
		try {
			String decrypted = new String(Hex.decodeHex(cryptotext.toCharArray()));
			final Encrypter encrypt = getStringEncrypt(algorithm);
			return encrypt.decrypt(decrypted);
		} catch (DecoderException e) {
			logger.error("解密异常", e);
			throw new EncryptException(e);
		}
	}

	@Override
	public String encrypt(String plaintext, String algorithm) throws EncryptException {
		final Encrypter encrypt = getStringEncrypt(algorithm);
		String encrypted = encrypt.encrypt(plaintext);
		return Hex.encodeHexString(encrypted.getBytes());
	}

	@Override
	public byte[] digest(byte[] digtotext, String algorithm) throws DigestedException {
		final Digester digester = getByteDigester(algorithm);
		return digester.digest(digtotext);
	}

	@Override
	public String digest(String digtotext, String algorithm) throws DigestedException {
		final Digester digester = getStringDigester(algorithm);
		return digester.digest(digtotext);
	}

	@Override
	public boolean matches(byte[] message, byte[] digest, String algorithm) throws DigestedException {
		final Digester digester = getByteDigester(algorithm);
		return digester.matches(message, digest);
	}

	@Override
	public boolean matches(String message, String digest, String algorithm) throws DigestedException {
		final Digester digester = getStringDigester(algorithm);
		return digester.matches(message, digest);
	}

	protected Encrypter getStringEncrypt(String algorithm) throws EncryptException {
		if (StringUtils.isBlank(algorithm)) {
			algorithm = DEFAULT_ENCRYPTER.PBEWithMD5AndDES.name();
		}
		StringEncrypterFactory factory = StringEncrypterFactory.getInstance();

		return factory.getEncrypter(algorithm);
	}

	protected Encrypter getByteEncrypt(String algorithm) throws EncryptException {
		if (StringUtils.isBlank(algorithm)) {
			algorithm = DEFAULT_ENCRYPTER.PBEWithMD5AndDES.name();
		}
		ByteEncrypterFactory factory = ByteEncrypterFactory.getInstance();

		return factory.getEncrypter(algorithm);
	}

	protected Digester getByteDigester(String algorithm) throws DigestedException {
		if (StringUtils.isBlank(algorithm)) {
			algorithm = DEFAULT_DIGESTER.MD5.name();
		}
		ByteDigesterFactory factory = ByteDigesterFactory.getInstance();
		return factory.getDigester(algorithm);

	}

	protected Digester getStringDigester(String algorithm) throws DigestedException {
		if (StringUtils.isBlank(algorithm)) {
			algorithm = DEFAULT_DIGESTER.MD5.name();
		}
		StringDigesterFactory factory = StringDigesterFactory.getInstance();
		return factory.getDigester(algorithm);

	}

	@Override
	public String encryptWithoutHexEncode(String plaintext, String algorithm) throws EncryptException {
		final Encrypter encrypt = getStringEncrypt(algorithm);
		return encrypt.encrypt(plaintext);
	}

	@Override
	public String decryptWithoutHexDecode(String cryptotext, String algorithm) throws EncryptException {
		final Encrypter encrypt = getStringEncrypt(algorithm);
		return encrypt.decrypt(cryptotext);

	}

}
