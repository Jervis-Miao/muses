package com.muses.common.orm.mybatis.easylist.list.criteria;

import java.util.List;

/**
 * SearchDataAssembler <br/>
 * 搜索元数据组装器
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月9日 下午5:53:19
 * @author Jervis
 */
public interface SearchDataAssembler {

    /**
     * 从给定的数据源中，装配出列表查询字段信息
     *
     * @param source
     */
    public abstract List<SearchData> assembleSearchData(Object source);

}
