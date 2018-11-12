/*
Copyright 2018 All rights reserved.
 */

package com.utils.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * * NoShortJavaTypeResolver <br/>
 * 修改默认规则，即使Numeric类型的长度小于5也使用Integer
 * 
 * @author miaoqiang
 * @date 2018/11/12.
 */
public class NoShortJavaTypeResolver extends JavaTypeResolverDefaultImpl {
	/**
	 * 修改父类对长度小于5类型的处理
	 *
	 * @see org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl#calculateJavaType(org.mybatis.generator.api.IntrospectedColumn)
	 */
	@Override
	public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
		FullyQualifiedJavaType answer = super.calculateJavaType(introspectedColumn);
		if (null != answer && (answer.getShortName().contains("Short") || answer.getShortName().contains("Byte"))) {
			answer = new FullyQualifiedJavaType(Integer.class.getName());
		}
		return answer;
	}
}
