/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.muses.common.orm.mybatis.easylist.list.base;

import com.muses.common.orm.mybatis.easylist.list.base.annotation.SearchItem;
import com.muses.common.orm.mybatis.easylist.list.criteria.SearchLogic;

/**
 * DefaultSearchItem.java
 *
 * @author zhangxu
 */
public class DefaultSearchItem extends DefaultSqlItem implements ISearchItem {
    private final SearchLogic logic;
    private final SearchType searchType;
    private Object data;

    public DefaultSearchItem(String column, SearchType searchType) {
        this(column, searchType, SearchLogic.AND);
    }

    public DefaultSearchItem(SearchItem item) {
        this(item.value(), item.searchType(), item.searchLogic());
    }

    public DefaultSearchItem(String column, SearchType searchType, SearchLogic logic) {
        super(column);

        this.searchType = searchType;
        this.logic = logic;
    }

    @Override
    public SearchType getType() {
        return searchType;
    }

    @Override
    public SearchLogic getLogic() {
        return logic;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }
}
