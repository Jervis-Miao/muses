/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.orm.page;

import java.io.Serializable;

/**
 * @author Jervis
 * @date 2018/11/21.
 */
public class PageReq implements Serializable {
    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    private static final long serialVersionUID = -6553693305474182318L;

    private int pageIndex;
    private int pageSize;

    public PageReq() {
        this(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
    }

    public PageReq(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
