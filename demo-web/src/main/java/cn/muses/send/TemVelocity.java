/*
Copyright 2018 All rights reserved.
 */

package cn.muses.send;

import java.util.Map;

/**
 * @author miaoqiang
 * @date 2018/11/30.
 */
public class TemVelocity {
	private String				temId;			// 模板ID
	private String				temName;		// 模板名称
	private String				temFileName;	// 模板文件名称
	private String				subject;		// 邮件主题
	private String				senderAddress;	// 发信邮箱
	private String				txtContent;	// 模板正文，文本内容
	private String				temFilePath;	// 模板路径
	private String				htmlContent;	// 预览html内容
	private String				temType;		// sms-短信模板；mail—邮件模板
	private String				triggerMode;	// 短信触发方式
	private String				senderDisplay;
	private String				temModule;		// 模板所属模块
	private Map<String, String>	paramtersMap;	// 模板中的变量名和值，目前值默认为0

	public String getTemType() {
		return temType;
	}

	public void setTemType(String temType) {
		this.temType = temType;
	}

	public String getTemId() {
		return temId;
	}

	public void setTemId(String temId) {
		this.temId = temId;
	}

	public String getTemName() {
		return temName;
	}

	public void setTemName(String temName) {
		this.temName = temName;
	}

	public String getTemFileName() {
		return temFileName;
	}

	public void setTemFileName(String temFileName) {
		this.temFileName = temFileName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getTemFilePath() {
		return temFilePath;
	}

	public void setTemFilePath(String temFilePath) {
		this.temFilePath = temFilePath;
	}

	public String getTxtContent() {
		return txtContent;
	}

	public void setTxtContent(String txtContent) {
		this.txtContent = txtContent;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public void setTriggerMode(String triggerMode) {
		this.triggerMode = triggerMode;
	}

	public String getTriggerMode() {
		return triggerMode;
	}

	public void setSenderDisplay(String senderDisplay) {
		this.senderDisplay = senderDisplay;
	}

	public String getSenderDisplay() {
		return senderDisplay;
	}

	public void setParamtersMap(Map<String, String> paramtersMap) {
		this.paramtersMap = paramtersMap;
	}

	public Map<String, String> getParamtersMap() {
		return paramtersMap;
	}

	public void setTemModule(String temModule) {
		this.temModule = temModule;
	}

	public String getTemModule() {
		return temModule;
	}
}
