/*
Copyright 2018 All rights reserved.
 */

package cn.muses.api.dto;

import java.io.Serializable;
import java.util.List;

import cn.muses.common.orm.mybatis.easylist.list.base.SearchType;
import cn.muses.common.orm.mybatis.easylist.list.base.annotation.SearchItem;
import cn.muses.api.dto.base.PageQueryDTO;

/**
 * @author Jervis
 * @date 2018/11/26.
 */
public class StudentQueryDTO extends PageQueryDTO implements Serializable {
	private static final long	serialVersionUID	= 6349229984734537719L;

	@SearchItem(value = "STUDENT.STUDENT_ID", searchType = SearchType.IN)
	private List<Long>			studentIds;

	/**
	 * 排序字段
	 */
	private List<String>		sortFieldList;

	/**
	 * 排序方式
	 */
	private List<String>		sortTypeList;

	public List<Long> getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(List<Long> studentIds) {
		this.studentIds = studentIds;
	}

	public List<String> getSortFieldList() {
		return sortFieldList;
	}

	public void setSortFieldList(List<String> sortFieldList) {
		this.sortFieldList = sortFieldList;
	}

	public List<String> getSortTypeList() {
		return sortTypeList;
	}

	public void setSortTypeList(List<String> sortTypeList) {
		this.sortTypeList = sortTypeList;
	}
}
