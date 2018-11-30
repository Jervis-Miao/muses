/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator.spring;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.muses.common.validator.EasyFieldError;
import com.muses.common.validator.EasyValidatorUtilities;
import com.muses.common.validator.ValidEasy;
import com.muses.common.validator.utils.StringUtils;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public class EasyValidator implements Validator {
	public static final ThreadLocal<List<EasyValidatorHolder>>	Holder						= new ThreadLocal<>();
	private static EasyValidatorUtilities						utilities					= new EasyValidatorUtilities();
	// 默认设置
	private static final String									DEFAULT_ERROR_CODE_PREFIX	= "valid.easy.";

	/**
	 * 是否支持对Entity的校验
	 * @param clazz
	 * @return
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return utilities.supports(clazz);
	}

	/**
	 * 由 DataBinder.validate()方法触发
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Set<String> hints = new LinkedHashSet<>();
		// easyvList存放的是每个校验参数对象的信息
		List<EasyValidatorHolder> easyvList = Holder.get();
		for (EasyValidatorHolder easyv : easyvList) {
			if (easyv.getParameterType().equals(target.getClass())) {
				ValidEasy validator = easyv.getValidEasy();
				// 单@ValidEasy注解value未配置时,使用方法名作为group名称
				if ((validator.value() == null) || (validator.value().length == 0)) {
					hints.add(easyv.getMethod().getName());
				} else {
					for (String value : validator.value()) {
						hints.add(value);
					}
				}
				// 防止多个相同DTO同时校验
				// easyvList.remove(easyv);
				break;
			}
		}
		List<EasyFieldError> errorList = utilities.validator(target, hints.toArray(new String[hints.size()]));
		for (EasyFieldError err : errorList) {
			errors.rejectValue(err.getField(), DEFAULT_ERROR_CODE_PREFIX + target.getClass().getCanonicalName() + "."
					+ err.getField(), null, StringUtils.replaceProperty(err.getMsg(), err.getArgs()));
		}
	}

	public static EasyValidatorUtilities getUtilities() {
		return EasyValidator.utilities;
	}

	public void setUtilities(EasyValidatorUtilities utilities) {
		EasyValidator.utilities = utilities;
	}
}
