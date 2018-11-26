/*
Copyright 2018 All rights reserved.
 */

package com.muses.api.dto.base;

import java.io.Serializable;
import java.util.List;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
public class PageListDTO<T> implements Serializable {
	private static final long	serialVersionUID	= -2587922320831677673L;

	private int					totalCount;
	private int					totalPage;
	private int					currentPage;

	private List<T>				dataList;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
