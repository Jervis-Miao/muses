/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.muses.common.orm.mybatis.easylist.list.convert;

import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * @author zhangxu
 */
class NumberNotEqualConverter extends AbstractNumberConverter {
    @Override
    protected WhereCriteria.Criterion buildCriteria(WhereCriteria.Criteria targetCriteria, SearchData data) {
        return targetCriteria.addNotEqualToCriterion(data.getColumn(), data.getData());
    }
}
