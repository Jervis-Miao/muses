package cn.muses.common.orm.mybatis.easylist.list.criteria;

import java.util.List;

import cn.muses.common.orm.mybatis.easylist.list.base.ISortItem;

/**
 * SortCriteriaAssemble <br/>
 * 排序条件组装器接口
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月9日 下午4:50:42
 * @author Jervis
 */
public interface SortCriteriaAssembler {

    /**
     * 根据排序项组装排序条件
     *
     * @param sortDatas 排序条件
     * @return
     */
    public abstract String assembleSortCriteria(List<ISortItem> sortDatas);

}
