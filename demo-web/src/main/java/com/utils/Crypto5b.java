package com.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Crypto5b encoding.
 */
public final class Crypto5b {
	private static final String ALPHABET = "abcghijklmnopqrstuvwxyz123456789";

	private Crypto5b() {
	}

	static public String encode(final byte[] bytes) {
		final StringBuilder r = new StringBuilder(bytes.length * 8 / 5 + 1);
		int buffer = 0;
		int bufferSize = 0;
		for (final byte b : bytes) {
			buffer <<= 8;
			buffer |= b & 0xFF;
			bufferSize += 8;
			while (bufferSize >= 5) {
				bufferSize -= 5;
				r.append(at((buffer >>> bufferSize) & 0x1F));
			}
		}
		if (0 != bufferSize) {
			buffer <<= 5 - bufferSize;
			r.append(at(buffer & 0x1F));
		}
		return r.toString();
	}

	static private char at(final int v) {
		return ALPHABET.charAt(v);
	}

	static public byte[] decode(final String chars) throws InvalidCrypto5b {
		final int end = chars.length();
		final byte[] r = new byte[end * 5 / 8];
		int buffer = 0;
		int bufferSize = 0;
		int j = 0;
		for (int i = 0; i != end; ++i) {
			final char c = chars.charAt(i);
			buffer <<= 5;
			buffer |= locate(c);
			bufferSize += 5;
			if (bufferSize >= 8) {
				bufferSize -= 8;
				r[j++] = (byte) (buffer >>> bufferSize);
			}
		}
		if (0 != (buffer & ((1 << bufferSize) - 1))) {
			invalid();
		}
		return r;
	}

	static private int locate(final char c) throws InvalidCrypto5b {
		int loc = ALPHABET.indexOf(c);
		return loc == -1 ? invalid() : loc;
	}

	static private int invalid() throws InvalidCrypto5b {
		throw new InvalidCrypto5b();
	}

	public static void main(String[] args) throws InvalidCrypto5b, DecoderException {
		long i = 1;
		for (int j = 1; j < 11; j++) {
			i = Double.valueOf(Math.pow(10, j)).longValue();
			String i_str = String.valueOf(Long.toHexString(i));
			String str = StringUtils.leftPad(i_str, i_str.length() + 2, DigestUtils.md5Hex(i_str).substring(0, 2));
			// System.out.println(i + " - " + str);
			String chars = encode(str.getBytes());
			System.out.println(i + " - " + i_str + " - " + str + " - " + chars + "  | " + new String(decode(chars)));
		}
		// original: JavaEye论坛频道
		// encode: mmt1p2nisiv6iz8pzsgooqra
		// decode: JavaEye论坛频道
		String str2 = "JavaEye论坛频道";
		System.out.println(encode(str2.getBytes()));
		System.out.println(new String(decode(encode(str2.getBytes()))));
		for (int j = 999000; j < 1000000; j++) {
			String i_str = String.valueOf(Long.toHexString(j));
			String str = StringUtils.leftPad(i_str, i_str.length() + 2, DigestUtils.md5Hex(i_str).substring(0, 2));
			String chars = encode(str.getBytes());
			System.out.println(j + " - " + str + " - " + chars + "  | " + new String(decode(chars)));
		}

		// System.out.println(Long.valueOf(new String(decode("jbwgj3mzp1")), 16));
		// System.out.println(Long.valueOf("ff", 16));
		System.out.println(DigestUtils.md5Hex("123"));
	}
}

class InvalidCrypto5b extends Exception {
	private static final long serialVersionUID = 1L;
}
