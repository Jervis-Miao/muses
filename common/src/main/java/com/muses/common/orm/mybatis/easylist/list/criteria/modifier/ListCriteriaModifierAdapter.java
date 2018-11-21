package com.muses.common.orm.mybatis.easylist.list.criteria.modifier;

import java.util.List;

import com.muses.common.orm.mybatis.easylist.list.base.ISortItem;
import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criterion;

/**
 * ListCriteriaModifierAdapter <br/>
 * 列表条件修正适配器
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月9日 下午5:09:05
 * @author Jervis
 */
public abstract class ListCriteriaModifierAdapter implements SearchCriteriaModifier, SortCriteriaModifier {

    @Override
    public void afterSortDataAssembled(List<ISortItem> sortDatas) {
        // do nothing
    }

    @Override
    public String afterSorCriteriaAssembled(String sortCriteria) {
        // do nothing
        return sortCriteria;
    }

    @Override
    public void afterSearchDatasAssembled(List<SearchData> searchDatas) {
        // do nothing
    }

    @Override
    public void afterSearchCriteriaAssembled(WhereCriteria searchCriteria) {
        // do nothing
    }

    @Override
    public void afterPerSearchCriteria(WhereCriteria criteria, Criterion criterion) {
        // do nothing
    }

}
