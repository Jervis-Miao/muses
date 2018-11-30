/*
Copyright 2018 All rights reserved.
 */

package com.muses.api.dto.base;

import java.io.Serializable;

/**
 * @author Jervis
 * @date 2018/11/26.
 */
public class PageQueryDTO implements Serializable {
	private static final long	serialVersionUID	= -1090658666801168808L;

	/**
	 * 分页参数，第几页
	 */
	private int					pageIndex			= 1;

	/**
	 * 分页参数，每页多少条
	 */
	private int					limit				= 10;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
