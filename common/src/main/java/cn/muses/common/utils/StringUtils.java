/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.utils;

/**
 * @author Jervis
 * @date 2018/11/20.
 */
public class StringUtils {
	private StringUtils() {
	}

	public static final String	EMPTY	= "";

	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static boolean isNotEmpty(CharSequence cs) {
		return !isEmpty(cs);
	}

	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs != null && (strLen = cs.length()) != 0) {
			for (int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(cs.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public static boolean isNotBlank(CharSequence cs) {
		return !isBlank(cs);
	}

	public static String trimToEmpty(String str) {
		return str == null ? "" : str.trim();
	}
}
