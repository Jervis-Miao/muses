package cn.muses.common.orm.mybatis.easylist.list.criteria.modifier;

import java.util.List;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * SearchCriteriaModifier <br/>
 * 搜索组合条件修正接口
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月8日 下午3:42:15
 * @author Jervis
 */
public interface SearchCriteriaModifier {

	/**
	 * 在组装好SearchData之后做点什么
	 *
	 * @param searchDatas
	 */
	void afterSearchDatasAssembled(List<SearchData> searchDatas);

	/**
	 * 在每个SearchCriteria组装完成后，对其中的criterion做点什么
	 *
	 * @param criteria
	 * @param criterion
	 */
	void afterPerSearchCriteria(WhereCriteria criteria, WhereCriteria.Criterion criterion);

	/**
	 * 在组装好searchCriteria之后做点什么
	 *
	 * @param searchCriteria
	 */
	void afterSearchCriteriaAssembled(WhereCriteria searchCriteria);

}
