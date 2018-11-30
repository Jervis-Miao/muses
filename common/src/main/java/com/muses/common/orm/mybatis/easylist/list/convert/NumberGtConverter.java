/**
 * Author Jervis
 * XYZ Reserved
 * Created on 2016年4月13日 下午5:20:03
 */
package com.muses.common.orm.mybatis.easylist.list.convert;

import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criteria;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criterion;

/**
 * @author Jervis
 *
 */
public class NumberGtConverter extends AbstractNumberConverter {

    @Override
    protected Criterion buildCriteria(Criteria targetCriteria, SearchData data) {
        return targetCriteria.addGreaterThanCriterion(data.getColumn(), data.getData());
    }

}
