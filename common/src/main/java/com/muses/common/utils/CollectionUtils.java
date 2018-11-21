/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.utils;

import java.util.Collection;

/**
 * @author Jervis
 * @date 2018/11/21.
 */
public class CollectionUtils {
	public static boolean isEmpty(Collection coll) {
		return ((coll == null) || coll.isEmpty());
	}

	public static boolean isNotEmpty(Collection coll) {
		return !CollectionUtils.isEmpty(coll);
	}
}
