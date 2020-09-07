package cn.muses.common.orm.mybatis.easylist.list.convert;

import java.util.Date;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criteria;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criterion;

public class DateGeConverter extends AbstractDateConverter {

    @Override
    public Date convert(Object source) {
        if (null == source || !(source instanceof Date)) {
            return null;
        }
        return (Date) source;
    }

    @Override
    protected Criterion buildCriteria(Criteria targetCriteria, SearchData data) {
        Date result = data.getData();

        if (null == result) {
            return null;
        }
        return targetCriteria.addGreaterThanOrEqualToCriterion(data.getColumn(), result);
    }

}
