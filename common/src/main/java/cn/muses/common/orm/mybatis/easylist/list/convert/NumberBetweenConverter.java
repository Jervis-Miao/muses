/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.common.orm.mybatis.easylist.list.convert;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * @author zhangxu
 */
@SuppressWarnings("unchecked")
class NumberBetweenConverter extends AbstractSearchDataConverter {
    @Override
    public Double[] convert(Object source) {
        if (null == source) {
            return new Double[2];
        }

        Double[] value = new Double[2];

        if (String[].class == source.getClass()) {
            value = new Double[2];
            String sv;

            for (int i = 0; i < 2; i++) {
                sv = ((String[]) source)[i];
                value[i] = getDoubleValue(sv);
            }

        } else if (Double[].class == source.getClass()) {
            value = (Double[]) source;
        }

        return value;
    }

    @Override
    protected WhereCriteria.Criterion buildCriteria(WhereCriteria.Criteria targetCriteria, SearchData data) {
        Double[] result = data.getData();

        if (isEmpty(result) || (result[0] == null && result[1] == null)) {
            return null;
        }

        return targetCriteria.addBetweenCriterion(data.getColumn(), result);
    }
}
