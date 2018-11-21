package com.muses.common.orm.mybatis.easylist.list.criteria.modifier;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.muses.common.orm.mybatis.easylist.list.criteria.SearchData;

/**
 * SearchDataSortModifier <br/>
 * 搜索条件排序
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月9日 下午6:51:34
 * @author Jervis
 */
public class SearchDataSortModifier extends ListCriteriaModifierAdapter {

    @Override
    public void afterSearchDatasAssembled(List<SearchData> searchDatas) {
        // 查询条件排序
        Collections.sort(searchDatas, new Comparator<SearchData>() {
            @Override
            public int compare(SearchData o1, SearchData o2) {
                return o1.getIndex() - o2.getIndex();
            }
        });
    }

}
