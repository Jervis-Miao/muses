/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.muses.common.orm.mybatis.easylist.list.base;

/**
 * DefaultSortItem.java
 *
 * @author zhangxu
 */
class DefaultSqlItem implements ISqlItem {
    private String column;

    public DefaultSqlItem(String column) {
        this.column = column;
    }

    @Override
    public String getColumn() {
        return column;
    }

}
