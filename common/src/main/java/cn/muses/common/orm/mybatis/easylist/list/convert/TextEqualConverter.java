/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.common.orm.mybatis.easylist.list.convert;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * @author zhangxu
 */
class TextEqualConverter extends AbstractSearchDataConverter {
    @Override
    @SuppressWarnings("unchecked")
    public String convert(Object source) {
        return null == source ? "" : source.toString();
    }

    @Override
    protected WhereCriteria.Criterion buildCriteria(WhereCriteria.Criteria targetCriteria, SearchData data) {
        if (isBlank((String) data.getData())) {
            return null;
        }

        return targetCriteria.addEqualToCriterion(data.getColumn(), data.getData());
    }
}
