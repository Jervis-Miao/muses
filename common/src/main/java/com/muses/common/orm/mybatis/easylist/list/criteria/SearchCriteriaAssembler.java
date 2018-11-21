package com.muses.common.orm.mybatis.easylist.list.criteria;

import java.util.List;

import com.muses.common.orm.mybatis.easylist.list.criteria.modifier.SearchCriteriaModifier;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * SearchCriteriaAssembler <br/>
 * 搜索条件组装器
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月9日 下午4:49:07
 * @author Jervis
 */
public interface SearchCriteriaAssembler {

    /**
     * 组装搜索条件
     *
     * @param searches 搜索元数据
     * @return
     */
    public abstract WhereCriteria assembleSearchCriteria(List<SearchData> searches);

    /**
     * 组装搜索条件
     *
     * @param searches 搜索元数据
     * @param criteriaModifier
     * @return
     */
    public abstract WhereCriteria assembleSearchCriteria(List<SearchData> searches,
                                                         SearchCriteriaModifier criteriaModifier);

}
