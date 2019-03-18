/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
public class DockingResDTO implements Serializable {
	private static final long	serialVersionUID	= 8414609733869696282L;
	/**
	 * 响应结果
	 */
	private Map<String, String>	results;

	/**
	 * 附件文件ID, 文件服务
	 * @see com.focustech.ins.metis.mount.file.FileProvider
	 */
	private Long				fileId;

	/**
	 * 任务处理成功标记
	 */
	private Boolean				isSuccess;

	/**
	 * 错误信息码
	 */
	private Integer				errCode;

	/**
	 * 错误信息
	 */
	private String				errMessage;

	public Map<String, String> getResults() {
		return results;
	}

	public void setResults(Map<String, String> results) {
		this.results = results;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Boolean getSuccess() {
		return isSuccess;
	}

	public void setSuccess(Boolean success) {
		isSuccess = success;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
}
