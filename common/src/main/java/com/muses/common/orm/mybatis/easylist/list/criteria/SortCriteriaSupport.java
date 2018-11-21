package com.muses.common.orm.mybatis.easylist.list.criteria;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.muses.common.orm.mybatis.easylist.list.base.DefaultSortItem;
import com.muses.common.orm.mybatis.easylist.list.base.ISortItem;
import com.muses.common.orm.mybatis.easylist.list.base.SortType;
import com.muses.common.orm.mybatis.easylist.list.base.annotation.SortItem;
import com.muses.common.orm.mybatis.easylist.list.utils.ObjectUtils;
import com.muses.common.utils.CollectionUtils;
import com.muses.common.utils.StringUtils;

/**
 * SearchDataAssembler <br/>
 * 排序条件支持类，包含元数据抽取和最终条件组装<br/>
 * 实现Copy自 {@link com.focustech.tm.components.easylist.ListDataAssembler ListDataAssembler} 只做了少许改动
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月8日 下午3:58:22
 * @author Jervis
 * @see com.focustech.tm.components.easylist.ListDataAssembler
 */
public class SortCriteriaSupport implements SortDataAssembler, SortCriteriaAssembler {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ISortItem> assembleSortData(Object source) {
		List<ISortItem> result = new ArrayList<ISortItem>();

		if (null != source) {
			Class<?> clazz = source.getClass();

			SortItem item;
			Object value;
			String column;

			SortType sort;

			for (Field field : clazz.getDeclaredFields()) {
				item = field.getAnnotation(SortItem.class);

				if (null != item) {
					try {
						field.setAccessible(true);

						value = field.get(source);
						if (ObjectUtils.isNotEmpty(value)) {
							sort = SortType.getType(value.toString());

							column = item.value();

							if ((null != item.ascColumn() && item.ascColumn().trim().length() > 0)
									&& (null != item.descColumn() && item.descColumn().trim().length() > 0)) {
								column = sort == SortType.ASC ? item.ascColumn() : item.descColumn();
							}

							result.add(new DefaultSortItem(column, sort));
							appendStaticSort(result, item);
							appendWithSort(result, item, sort);
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
			Field sortFieldListField = ReflectionUtils.findField(clazz, "sortFieldList");
			Field sortTypeListField = ReflectionUtils.findField(clazz, "sortTypeList");

			if (ObjectUtils.isNotEmpty(sortFieldListField) && ObjectUtils.isNotEmpty(sortTypeListField)) {
				sortFieldListField.setAccessible(true);
				sortTypeListField.setAccessible(true);
				try {
					Object sortFieldListValue = sortFieldListField.get(source);
					Object sortTypeListValue = sortTypeListField.get(source);
					if (sortFieldListValue instanceof List && sortFieldListValue instanceof List
							&& CollectionUtils.isNotEmpty((List) sortFieldListValue)
							&& CollectionUtils.isNotEmpty((List) sortTypeListValue)
							&& ((List) sortFieldListValue).size() == ((List) sortTypeListValue).size()) {
						for (int i = 0; i < ((List) sortFieldListValue).size(); i++) {
							column = ((List<String>) sortFieldListValue).get(i);
							sort = SortType.getType(((List<String>) sortTypeListValue).get(i).toLowerCase());
							result.add(new DefaultSortItem(column, sort));
						}
					} else {
						log.warn("sortFieldList和sortTypeList字段已找到, 但不符合使用规范");
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		}

		return result;
	}

	@Override
	public String assembleSortCriteria(List<ISortItem> sortDatas) {
		if (null == sortDatas || sortDatas.size() == 0) {
			return StringUtils.EMPTY;
		}

		StringBuilder sb = new StringBuilder("");

		for (ISortItem item : sortDatas) {
			sb.append(item.toSortSql());
			sb.append(",");
		}

		return sb.toString().replaceAll(",$", "");
	}

	private void appendWithSort(List<ISortItem> result, SortItem item, SortType sort) {
		boolean[] withReverse = item.withReverse();
		int index = 0;

		for (String c : item.with()) {
			result.add(new DefaultSortItem(c, withReverse[index++] ? sort.reverse() : sort));
		}
	}

	private void appendStaticSort(List<ISortItem> result, SortItem item) {
		SortType[] staticType = item.staticType();
		int index = 0;

		for (String c : item.staticColumn()) {
			result.add(new DefaultSortItem(c, staticType[index++]));
		}
	}

}
