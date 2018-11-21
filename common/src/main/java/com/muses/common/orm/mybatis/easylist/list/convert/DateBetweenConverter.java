package com.muses.common.orm.mybatis.easylist.list.convert;

import java.util.Date;

import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

@SuppressWarnings("unchecked")
class DateBetweenConverter extends AbstractDateConverter {

    @Override
    public Date[] convert(Object source) {
        Date[] result = new Date[2];

        if (null != source) {
            Date[] data = (Date[]) source;
            result[0] = data[0];
            result[1] = data[1];
        }

        return result;
    }

    @Override
    protected WhereCriteria.Criterion buildCriteria(WhereCriteria.Criteria targetCriteria, SearchData data) {
        Date[] result = data.getData();

        if (isEmpty(result) || ((null == result[0]) && (null == result[1]))) {
            return null;
        }

        return targetCriteria.addBetweenCriterion(data.getColumn(), result);
    }
}
