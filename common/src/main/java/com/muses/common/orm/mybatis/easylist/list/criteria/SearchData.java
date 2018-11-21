/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.muses.common.orm.mybatis.easylist.list.criteria;

import com.muses.common.orm.mybatis.easylist.list.base.ISearchItem;
import com.muses.common.orm.mybatis.easylist.list.base.ISqlItem;
import com.muses.common.orm.mybatis.easylist.list.convert.Dialect;

/**
 * SearchData.java
 *
 * @author zhangxu
 */
public final class SearchData implements ISqlItem {
	private final ISearchItem	item;
	private Object				data;
	private int					index;
	private Dialect				dialect;

	public SearchData(ISearchItem item) {
		this.item = item;
	}

	@SuppressWarnings("unchecked")
	public <T> T getData() {
		return (T) data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ISearchItem getItem() {
		return item;
	}

	@Override
	public String getColumn() {
		return item.getColumn();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
}
