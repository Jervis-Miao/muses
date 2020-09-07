package cn.muses.common.orm.mybatis.easylist.list.convert;

import java.util.Calendar;
import java.util.Date;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

class DateDayBetweenConverter extends DateBetweenConverter {

    @Override
    public Date[] convert(Object source) {
        Date[] result = super.convert(source);

        for (int i = 0; i < result.length; i++) {
            if (null != result[i]) {
                result[i] = truncate(result[i], Calendar.DATE);
            }
        }

        return result;
    }

    @Override
    protected WhereCriteria.Criterion buildCriteria(WhereCriteria.Criteria targetCriteria, SearchData data) {
        Date[] result = data.getData();

        if (isEmpty(result) || ((null == result[0]) && (null == result[1]))) {
            return null;
        }

        return targetCriteria.addBetweenCriterion(getByDialect(data.getColumn(), data.getDialect()), result);
    }

}
