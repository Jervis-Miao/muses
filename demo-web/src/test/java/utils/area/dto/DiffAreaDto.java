/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package utils.area.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaoqiang
 * @date 2018/6/25.
 */
public class DiffAreaDto {
	List<CompareAreaDto>	add		= new ArrayList<>();
	List<CompareAreaDto>	delete	= new ArrayList<>();
	List<CompareAreaDto>	modify	= new ArrayList<>();

	public List<CompareAreaDto> getAdd() {
		return add;
	}

	public void setAdd(List<CompareAreaDto> add) {
		this.add = add;
	}

	public List<CompareAreaDto> getDelete() {
		return delete;
	}

	public void setDelete(List<CompareAreaDto> delete) {
		this.delete = delete;
	}

	public List<CompareAreaDto> getModify() {
		return modify;
	}

	public void setModify(List<CompareAreaDto> modify) {
		this.modify = modify;
	}
}
