/**
 * Author Jervis
 * XYZ Reserved
 * Created on 2016年4月13日 下午4:57:11
 */
package com.muses.common.orm.mybatis.easylist.list.convert;

import java.util.Calendar;
import java.util.Date;

import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criteria;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criterion;

/**
 * @author Jervis
 *
 */
public class DateDayGtConverter extends DateGtConverter {

    @Override
    public Date convert(Object source) {
        Date result = super.convert(source);
        if (null != result) {
            result = truncate(result, Calendar.DATE);
        }

        return result;
    }

    @Override
    protected Criterion buildCriteria(Criteria targetCriteria, SearchData data) {
        Date result = data.getData();

        if (null == result) {
            return null;
        }

        return targetCriteria.addGreaterThanCriterion(getByDialect(data.getColumn(), data.getDialect()), result);
    }

}
