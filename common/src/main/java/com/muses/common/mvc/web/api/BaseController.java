/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.mvc.web.api;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.ObjectError;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public abstract class BaseController extends BaseLogComponent {
	public BaseController() {
	}

	protected <T> BaseResponseDTO<T> returnWithSuccess(T t) {
		return new BaseResponseDTO(t);
	}

	protected BaseResponseDTO returnWithFail(Integer errorCode, List<String> errorMsg) {
		BaseResponseDTO dto = new BaseResponseDTO();
		dto.setRet(errorCode);
		dto.getMsg().addAll(errorMsg);
		return dto;
	}

	protected BaseResponseDTO returnWithCheckFail(List<? extends ObjectError> errorList) {
		BaseResponseDTO dto = new BaseResponseDTO();
		if (CollectionUtils.isNotEmpty(errorList)) {
			dto.setRet(Integer.valueOf(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL.value()));
			Iterator var3 = errorList.iterator();

			while (var3.hasNext()) {
				ObjectError error = (ObjectError) var3.next();
				dto.getMsg().add(error.getDefaultMessage());
			}
		}

		return dto;
	}

	protected BaseResponseDTO returnWithCheckFail(ObjectError error) {
		BaseResponseDTO dto = new BaseResponseDTO();
		dto.setRet(Integer.valueOf(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL.value()));
		dto.getMsg().add(error.getDefaultMessage());
		return dto;
	}
}
