/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package utils.area.dto;

/**
 * @author miaoqiang
 * @date 2018/6/25.
 */
public class CompareAreaDto extends AreaDto {
	private String	oldName;
	private String	oldCode;

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}
}
