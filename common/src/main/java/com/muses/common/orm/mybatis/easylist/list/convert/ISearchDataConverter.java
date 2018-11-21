/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.muses.common.orm.mybatis.easylist.list.convert;

import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * 列表检索数据转换器。
 * <ul>
 * <li>负责把数据来源转换成指定需要的格式。例如把字符串信息转换成list数据，以适应列表查询的数据</li>
 * <li>把检索数据装配到Page对象的检索条件中去。</li>
 * </ul>
 *
 * @author zhangxu
 */
public interface ISearchDataConverter {
    /**
     * 数据转换
     *
     * @param <T> 目标数据类型
     * @param source 源数据
     * @return
     */
    <T> T convert(Object source);

    /**
     * 检索数据装配到 {@link WhereCriteria#oredCriteria}中去
     *
     * @param data
     * @param page
     */
    WhereCriteria.Criterion assembleCriteria(SearchData data, WhereCriteria page);
}
