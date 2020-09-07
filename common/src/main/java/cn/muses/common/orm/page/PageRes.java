/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.orm.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jervis
 * @date 2018/11/21.
 */
public class PageRes<T> implements Serializable {
	public static final int		DEFAULT_PAGE_INDEX	= 1;
	public static final int		DEFAULT_PAGE_SIZE	= 20;
	private static final long	serialVersionUID	= -6984215680110073355L;

	private int					pageIndex;
	private int					pageSize;
	private int					pageCount;
	private int					totalCount;
	private List<T>				data;

	public PageRes() {
		this(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
	}

	public PageRes(PageReq pageReq) {
		this(pageReq.getPageIndex(), pageReq.getPageSize());
	}

	/**
	 * Specify page index (starts from 1) and default page size (20).
	 *
	 * @param pageIndex Starts from 1.
	 */
	public PageRes(int pageIndex) {
		this(pageIndex, DEFAULT_PAGE_SIZE);
	}

	/**
	 * Specify page index (starts from 1) and page size.
	 *
	 * @param pageIndex Starts from 1.
	 * @param pageSize How many items a page can display.
	 */
	public PageRes(int pageIndex, int pageSize) {
		// check:
		if ((pageIndex < 1) || (pageSize < 1) || (pageSize > 100)) {
			throw new IllegalArgumentException();
		}
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	/**
	 * Get page index.
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * Get page size.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Get how many pages.
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * Get how many items in total.
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * Get index of the first item in this page.
	 */
	public int getFirstResult() {
		return (pageIndex - 1) * pageSize;
	}

	/**
	 * Get index of the last item in this page.
	 */
	public int getLastResult() {
		if (totalCount == 0) {
			return 0;
		}
		int last = ((pageIndex) * pageSize) - 1;
		return last >= totalCount ? totalCount - 1 : last;
	}

	/**
	 * Has previous page?
	 */
	public boolean getHasPrevious() {
		return pageIndex > 1;
	}

	/**
	 * Has next page?
	 */
	public boolean getHasNext() {
		return pageIndex < pageCount;
	}

	/**
	 * Set a total count of items.
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		pageCount = (totalCount / pageSize) + ((totalCount % pageSize) == 0 ? 0 : 1);
		// adjust pageIndex:
		if (totalCount == 0) {
			if (pageIndex != 1) {
				throw new IndexOutOfBoundsException("Page index out of range.");
			}
		} else {
			if (pageIndex > pageCount) {
				throw new IndexOutOfBoundsException("Page index out of range.");
			}
		}
	}

	/**
	 * Is there any items? It is equal of getTotalCount()==0.
	 */
	public boolean isEmpty() {
		return totalCount == 0;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
