/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.mvc.web.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public class BaseResponseDTO<T> {
	private Integer				ret;
	private final List<String>	msg;
	private T					data;

	public BaseResponseDTO() {
		this.ret = Integer.valueOf(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.SUCCESS.value());
		this.msg = new ArrayList();
	}

	public BaseResponseDTO(T dto) {
		this.ret = Integer.valueOf(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.SUCCESS.value());
		this.msg = new ArrayList();
		this.data = dto;
	}

	public Integer getRet() {
		return this.ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public void addError(String error) {
		this.msg.add(error);
	}

	public void addErrors(String[] errors) {
		this.addErrors(Arrays.asList(errors));
	}

	public void addErrors(List<String> errorList) {
		this.msg.addAll(errorList);
	}

	public void removeError(String error) {
		this.msg.remove(error);
	}

	public List<String> getMsg() {
		return this.msg;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static enum DEFAULT_RESPONSE_RESULT {
        /**
         * 返回状态
         */
		SUCCESS(0, "[]"),
		AUTHEN_FAIL(-1, "认证失败"),
		AUTHOR_FAIL(-2, "权限不足"),
		PARAM_CHECK_FAIL(-3, ""),
		RESOURCE_NOT_EXIST(-4, "请求资源不存在"),
		SYSTEM_ERROR(-5, "系统错误"),
		DATA_MALFORMAT(-6, "请求参数数据格式不正确"),
		REQMETHOD_ERROR(-7, "请求方法不正确"),
		TYPE_MISMATCH(-8, "请求参数类型不匹配"),
		MISS_REQUEST_PARAM(-9, "请求参数缺失");

		private final Integer	value;
		private final String	desc;

		private DEFAULT_RESPONSE_RESULT(int value, String desc) {
			this.value = Integer.valueOf(value);
			this.desc = desc;
		}

		public int value() {
			return this.value.intValue();
		}

		public String desc() {
			return this.desc;
		}
	}
}
