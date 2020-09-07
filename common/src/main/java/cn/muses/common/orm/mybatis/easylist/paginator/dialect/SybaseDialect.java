package cn.muses.common.orm.mybatis.easylist.paginator.dialect;

import cn.muses.common.orm.mybatis.easylist.paginator.domain.PageBounds;
import org.apache.ibatis.mapping.MappedStatement;

public class SybaseDialect extends Dialect {

	public SybaseDialect(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
		super(mappedStatement, parameterObject, pageBounds);
	}

	@Override
	protected String getLimitString(String sql, String offsetName, int offset, String limitName, int limit) {
		throw new UnsupportedOperationException("paged queries not supported");
	}

}
