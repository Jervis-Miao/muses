/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.common.orm.mybatis.easylist.list.base;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchLogic;

/**
 * 检索项
 *
 * @author zhangxu
 */
public interface ISearchItem extends ISqlItem {
    SearchType getType();

    SearchLogic getLogic();

    Object getData();

    void setData(Object data);
}
