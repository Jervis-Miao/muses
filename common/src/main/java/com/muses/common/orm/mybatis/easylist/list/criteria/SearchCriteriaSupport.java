package com.muses.common.orm.mybatis.easylist.list.criteria;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.muses.common.orm.mybatis.easylist.list.base.DefaultSearchItem;
import com.muses.common.orm.mybatis.easylist.list.base.ISearchItem;
import com.muses.common.orm.mybatis.easylist.list.base.annotation.SearchItem;
import com.muses.common.orm.mybatis.easylist.list.convert.Dialect;
import com.muses.common.orm.mybatis.easylist.list.convert.ISearchDataConverter;
import com.muses.common.orm.mybatis.easylist.list.criteria.modifier.SearchCriteriaModifier;
import com.muses.common.orm.mybatis.easylist.list.utils.ObjectUtils;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;
import com.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria.Criterion;

/**
 * SearchCriteriaSupport <br/>
 * 搜索条件支持类，包含元数据抽取和最终条件组装<br/>
 * 实现Copy自 {@link com.focustech.tm.components.easylist.ListDataAssembler ListDataAssembler} 只做了少许改动
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月9日 下午5:54:09
 * @author Jervis
 * @see com.focustech.tm.components.easylist.ListDataAssembler
 */
public class SearchCriteriaSupport implements SearchCriteriaAssembler, SearchDataAssembler {

    @Override
    public List<SearchData> assembleSearchData(Object source) {
        List<SearchData> result = null;

        if (null != source) {
            Class<?> clazz = source.getClass();

            SearchItem item;
            Object value;

            for (Field field : clazz.getDeclaredFields()) {
                item = field.getAnnotation(SearchItem.class);

                if (null != item) {
                    try {
                        field.setAccessible(true);
                        value = field.get(source);

                        if (ObjectUtils.isNotEmpty(value)) {
                            if (result == null) {
                                result = new ArrayList<SearchData>();
                            }
                            result.add(assembleDataBySearchItemAnntation(item, value));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return null == result ? new ArrayList<SearchData>() : result;
    }

    @Override
    public WhereCriteria assembleSearchCriteria(List<SearchData> searches) {
        return assembleSearchCriteria(searches, null);
    }

    @Override
    public WhereCriteria assembleSearchCriteria(List<SearchData> searches, SearchCriteriaModifier criteriaModifier) {
        WhereCriteria criteria = new WhereCriteria();
        for (SearchData data : searches) {
            ISearchDataConverter converter = data.getItem().getType().getSearchDataConverter();
            Criterion criterion = converter.assembleCriteria(data, criteria);
            if (null != criteriaModifier) {
                // 对内部的语句再做点啥
                criteriaModifier.afterPerSearchCriteria(criteria, criterion);
            }
        }
        return criteria;
    }

    private SearchData assembleDataBySearchItemAnntation(SearchItem searchItem, Object srcData) {
        return assembleDataBySearchType(searchItem, srcData, new DefaultSearchItem(searchItem), searchItem.index());
    }

    private SearchData assembleDataBySearchType(SearchItem searchItem, Object srcData, ISearchItem item, int index) {
        ISearchDataConverter converter = searchItem.searchType().getSearchDataConverter();
        Dialect dialect = searchItem.dialect();

        SearchData searchData = new SearchData(item);
        searchData.setDialect(dialect);

        searchData.setData(converter.convert(srcData));
        searchData.setIndex(index);

        return searchData;
    }

}
