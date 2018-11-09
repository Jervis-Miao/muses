/*
Copyright All rights reserved.
 */

package utils.area.dto;

import utils.excel.ExcelUtil;

/**
 * @author MiaoQiang
 * @date 2018/6/24.
 */
public class AreaDto {
	private String	name;
	private String	code;
	private String	parentCode;
	private String	link;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
