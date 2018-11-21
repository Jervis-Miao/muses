package com.muses.common.orm.mybatis.easylist.paginator.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.muses.common.orm.mybatis.easylist.paginator.domain.PageBounds;

/**
 * A dialect compatible with the H2 database.
 *
 * @author Thomas Mueller
 * @author miemiedev
 */
public class H2Dialect extends Dialect {

	public H2Dialect(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
		super(mappedStatement, parameterObject, pageBounds);
	}

	@Override
	protected String getLimitString(String sql, String offsetName, int offset, String limitName, int limit) {
		return new StringBuffer(sql.length() + 40)
				.append(sql)
				.append((offset > 0) ? " limit " + String.valueOf(limit) + " offset " + String.valueOf(offset)
						: " limit " + String.valueOf(limit)).toString();
	}

}
