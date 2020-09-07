/*
 * Copyright 2012 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.common.orm.mybatis.easylist.list.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.muses.common.orm.mybatis.easylist.list.base.SearchType;
import cn.muses.common.orm.mybatis.easylist.list.convert.Dialect;
import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchLogic;

/**
 * SearchItem.java
 *
 * @author zhangxu
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchItem {
    String value();

    SearchType searchType();

    Dialect dialect() default Dialect.COMMON;

    SearchLogic searchLogic() default SearchLogic.AND;

    /**
     * 调整搜索条件在where语句中的位置，目的是为了适应索引
     *
     * @return
     */
    int index() default -1;
}
