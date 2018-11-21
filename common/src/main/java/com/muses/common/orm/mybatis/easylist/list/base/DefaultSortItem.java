/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.muses.common.orm.mybatis.easylist.list.base;

import org.apache.commons.lang3.StringUtils;

/**
 * DefaultSortItem.java
 *
 * @author zhangxu
 */
public class DefaultSortItem extends DefaultSqlItem implements ISortItem {
	private final SortType	type;

	public DefaultSortItem(String column) {
		this(column, SortType.ASC);
	}

	public DefaultSortItem(String column, SortType type) {
		super(column);
		this.type = type;
	}

	@Override
	public SortType getType() {
		return type;
	}

	@Override
	public String toSortSql() {
		// 添加对排序占位符的支持，
		// in oracle like:"nlssort(column ? ,'NLS_SORT=SCHINESE_PINYIN_M')"
		if (StringUtils.isNoneBlank(getColumn()) && getColumn().contains("?") && getColumn().contains("NLS_SORT")) {
			return getColumn().replaceAll("\\?", type.toString());
		}
		return getColumn() + " " + type;
	}
}
