/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muses.common.validator.EasyFieldError;
import com.muses.common.validator.EasyValidatorUtilities;
import com.muses.common.validator.ValidContext;
import com.muses.common.validator.data.Action;

/**
 * Action校验执行器
 *
 * @author Jervis
 * @date 2018/11/29.
 */
public interface ActionValidator<T extends Action> {
	Logger	logger	= LoggerFactory.getLogger(ActionValidator.class);

	boolean supports(Action action);

	/**
	 * 校验执行
	 * @param utilities
	 * @param validContext
	 * @param action
	 * @param chain
	 * @return
	 */
	List<EasyFieldError> validator(EasyValidatorUtilities utilities, ValidContext validContext, T action,
			ActionValidatorChain chain);
}
