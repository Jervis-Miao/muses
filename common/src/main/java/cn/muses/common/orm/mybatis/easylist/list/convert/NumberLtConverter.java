/**
 * Author Jervis
 * XYZ Reserved
 * Created on 2016年4月13日 下午5:20:58
 */
package cn.muses.common.orm.mybatis.easylist.list.convert;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criteria;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criterion;

/**
 * @author Jervis
 *
 */
public class NumberLtConverter extends AbstractNumberConverter {

    @Override
    protected Criterion buildCriteria(Criteria targetCriteria, SearchData data) {
        return targetCriteria.addLessThanCriterion(data.getColumn(), data.getData());
    }

}
