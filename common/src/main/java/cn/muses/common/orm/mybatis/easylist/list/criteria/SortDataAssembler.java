package cn.muses.common.orm.mybatis.easylist.list.criteria;

import java.util.List;

import cn.muses.common.orm.mybatis.easylist.list.base.ISortItem;

/**
 * SortDataAssembler <br/>
 * 排序元数据组装器
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月9日 下午5:44:30
 * @author Jervis
 */
public interface SortDataAssembler {

    /**
     * 从给定的数据源中，装配出列表查询字段信息
     *
     * @param source
     * @return
     */
    public abstract List<ISortItem> assembleSortData(Object source);

}
