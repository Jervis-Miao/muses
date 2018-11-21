package com.muses.common.orm.mybatis.easylist.list;

import java.util.List;

import com.muses.common.orm.mybatis.easylist.list.base.ISortItem;
import com.muses.common.orm.mybatis.easylist.list.criteria.SearchCriteriaAssembler;
import com.muses.common.orm.mybatis.easylist.list.criteria.SearchCriteriaSupport;
import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import com.muses.common.orm.mybatis.easylist.list.criteria.SearchDataAssembler;
import com.muses.common.orm.mybatis.easylist.list.criteria.SortCriteriaAssembler;
import com.muses.common.orm.mybatis.easylist.list.criteria.SortCriteriaSupport;
import com.muses.common.orm.mybatis.easylist.list.criteria.SortDataAssembler;
import com.muses.common.orm.mybatis.easylist.list.criteria.modifier.SearchCriteriaModifier;
import com.muses.common.orm.mybatis.easylist.list.criteria.modifier.SortCriteriaModifier;
import com.muses.common.orm.mybatis.easylist.paginator.domain.PageBounds;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * EasyListHelper <br/>
 * 简单列表帮助类
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月8日 下午3:36:19
 * @author Jervis
 */
public final class EasyListHelper {

    private static SearchDataAssembler searchDataAssembler;

    private static SearchCriteriaAssembler searchCriteriaAssembler;

    private static SortDataAssembler sortDataAssembler;

    private static SortCriteriaAssembler sortCriteriaAssemble;

    static {
        // 在可预见的时间段内，大概也不会有别的实现
        // 为了方便，先全部static 初始化吧
        SearchCriteriaSupport searchCriteriaSupport = new SearchCriteriaSupport();
        SortCriteriaSupport sortCriteriaSupport = new SortCriteriaSupport();

        searchDataAssembler = searchCriteriaSupport;
        searchCriteriaAssembler = searchCriteriaSupport;
        sortDataAssembler = sortCriteriaSupport;
        sortCriteriaAssemble = sortCriteriaSupport;
    }

    /**
     * 根据vo，获得一个已装配查询条件和排序条件的PageBounds。 <br/>
     * 默认无分页，且不查询总数。<br/>
     * 如果需要分页且查询总数，建议用 {@link #getPageBounds(Object, int, int)}<br/>
     * 控制分页的所有行为使用 {@link #getPageBounds(Object, int, int, boolean)}
     *
     * @param vo
     * @return
     * @see #getPageBounds(Object, int, int, boolean)
     */
    public static PageBounds getPageBounds(Object vo) {
        return getPageBounds(vo, PageBounds.NO_ROW_LIMIT, PageBounds.NO_PAGE, false);
    }

    /**
     * 根据vo，获得一个已装配查询条件和排序条件的PageBounds。 <br/>
     * 使用分页参数控制分页，默认查询总数。<br/>
     * 如果不需要分页和查询总数可以用 {@link #getPageBounds(Object)}<br/>
     * 控制分页的所有行为使用 {@link #getPageBounds(Object, int, int, boolean)}
     *
     * @param vo 含有条件参数的值对象
     * @param limit 每页大小
     * @param page 起始页码（从1开始）
     * @return
     * @see #getPageBounds(Object, int, int, boolean)
     */
    public static PageBounds getPageBounds(Object vo, int limit, int page) {
        return getPageBounds(vo, limit, page, true);
    }

    /**
     * 根据vo，获得一个已装配查询条件和排序条件的PageBounds。 <br/>
     * 该PageBounds使用
     * {@link #com.muses.common.mybatis.easylist.paginator.domain.PageBounds.PageBounds(int limit, int page, boolean containsTotalCount)}
     * 初始化。<br/>
     * 默认无分页，但查询总数。<b>请自行设置limit和pageSize</b>
     *
     * @param vo 含有条件参数的值对象
     * @param limit 每页大小
     * @param page 起始页码（从1开始）
     * @param containsTotalCount 是否求总数
     * @return
     */
    public static PageBounds getPageBounds(Object vo, int limit, int page, boolean containsTotalCount) {
        PageBounds pageBounds = new PageBounds(limit, page, containsTotalCount);

        WhereCriteria searchCriteria = getSearchCriteria(vo);
        pageBounds.setWhereClause(searchCriteria);

        String sortCriteria = getSortCriteria(vo);
        pageBounds.setOrderByClause(sortCriteria);

        return pageBounds;
    }

    /**
     * 根据vo，获得其组合查询的条件
     *
     * @param vo
     * @return
     */
    public static WhereCriteria getSearchCriteria(Object vo) {
        return getSearchCriteria(vo, null);
    }

    /**
     * 根据vo，获得其组合查询的条件，期间，利用criteriaModifier做一些修正
     *
     * @param vo
     * @param criteriaModifier
     * @return
     */
    public static WhereCriteria getSearchCriteria(Object vo, SearchCriteriaModifier criteriaModifier) {
        List<SearchData> searchDatas = searchDataAssembler.assembleSearchData(vo);
        if (null != criteriaModifier) {
            // do something with criteriaModifier
            criteriaModifier.afterSearchDatasAssembled(searchDatas);
        }
        WhereCriteria searchCriteria = searchCriteriaAssembler.assembleSearchCriteria(searchDatas, criteriaModifier);
        if (null != criteriaModifier) {
            // do something with criteriaModifier
            criteriaModifier.afterSearchCriteriaAssembled(searchCriteria);
        }
        return searchCriteria;
    }

    /**
     * 根据VO，获得其组合的排序条件
     *
     * @param vo
     * @return
     */
    public static String getSortCriteria(Object vo) {
        return getSortCriteria(vo, null);
    }

    /**
     * 根据VO，获得其组合的排序条件，期间，利用criteriaModifier做一些修正
     *
     * @param vo
     * @param criteriaModifier
     * @return
     */
    public static String getSortCriteria(Object vo, SortCriteriaModifier criteriaModifier) {
        List<ISortItem> sortDatas = sortDataAssembler.assembleSortData(vo);
        if (null != criteriaModifier) {
            // do something with criteriaModifier
            criteriaModifier.afterSortDataAssembled(sortDatas);
        }
        String sortCriteria = sortCriteriaAssemble.assembleSortCriteria(sortDatas);
        if (null != criteriaModifier) {
            // do something with criteriaModifier
            sortCriteria = criteriaModifier.afterSorCriteriaAssembled(sortCriteria);
        }
        return sortCriteria;
    }

}
