/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.muses.common.orm.mybatis.easylist.list.convert;

import java.util.ArrayList;
import java.util.List;

import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * 在原基础上增加对Iterable的支持
 *
 * @author zhangxu
 * @author Jervis
 */
@SuppressWarnings("unchecked")
class InConverter extends AbstractSearchDataConverter {

    @Override
    public List<Object> convert(Object source) {
        List<Object> result = new ArrayList<Object>();

        if (null != source) {
            if (source instanceof Iterable<?>) {
                for (Object obj : (Iterable<?>) source) {
                    result.add(obj);
                }
            } else {
                String data = source.toString();
                for (String subValue : data.split(",")) {
                    result.add(subValue);
                }
            }
        }

        return result;
    }

    @Override
    protected WhereCriteria.Criterion buildCriteria(WhereCriteria.Criteria targetCriteria, SearchData data) {
        List<Object> result = data.getData();

        if (isEmpty(result)) {
            return null;
        }

        return targetCriteria.addInCriterion(data.getColumn(), result);
    }
}
