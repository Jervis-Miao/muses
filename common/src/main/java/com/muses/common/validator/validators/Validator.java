/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator.validators;

import com.muses.common.validator.ValidContext;

import java.util.Map;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public interface Validator {
	/**
	 * 获取名称
	 *
	 * @return 校验器名称
	 */
	String getName();

	void setName(String name);

	void attrs(Map<String, String> attrs);

	/**
	 * 验证
	 *
	 * @param object 待验证值
	 * @param validContext 校验上下文
	 * @return if isValid ok is true, other return false
	 */
	boolean isValid(Object object, ValidContext validContext);
}
