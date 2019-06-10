/*
Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;
import java.util.List;

/**
 * 投保问券
 * 
 * @author miaoqiang
 * @date 2019/3/28.
 */
public class AppQuestionDTO implements Serializable {
	private static final long		serialVersionUID	= 5624780805924912928L;

	/**
	 * 问题编码
	 */
	private String					questionCode;

	/**
	 * 答案
	 */
	private String					answerValue;

	/**
	 * 子问题
	 */
	private List<AppQuestionDTO>	subQuestions;

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public List<AppQuestionDTO> getSubQuestions() {
		return subQuestions;
	}

	public void setSubQuestions(List<AppQuestionDTO> subQuestions) {
		this.subQuestions = subQuestions;
	}
}
