package com.muses.common.orm.mybatis.easylist.paginator.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.muses.common.orm.mybatis.easylist.paginator.domain.PageBounds;

/**
 * Dialect for HSQLDB
 * @author badqiu
 * @author miemiedev
 */
public class HSQLDialect extends Dialect {

	public HSQLDialect(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
		super(mappedStatement, parameterObject, pageBounds);
	}

	@Override
	protected String getLimitString(String sql, String offsetName, int offset, String limitName, int limit) {
		boolean hasOffset = offset > 0;
		return new StringBuffer(sql.length() + 10)
				.append(sql)
				.insert(sql.toLowerCase().indexOf("select") + 6,
						hasOffset ? " limit " + String.valueOf(offset) + " " + String.valueOf(limit) : " top "
								+ String.valueOf(limit)).toString();
	}

}
