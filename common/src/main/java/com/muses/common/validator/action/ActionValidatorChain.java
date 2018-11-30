/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator.action;

import java.util.List;

import com.muses.common.validator.EasyFieldError;
import com.muses.common.validator.EasyValidatorUtilities;
import com.muses.common.validator.ValidContext;
import com.muses.common.validator.data.Action;

/**
 * 校验执行器链
 * @author Jervis
 * @date 2018/11/29.
 */
public class ActionValidatorChain {
	public List<EasyFieldError> validator(EasyValidatorUtilities utilities, ValidContext validContext, Action action) {
		validContext.setAction(action);
		for (ActionValidator actionValidator : utilities.getContext().getActionValidators()) {
			if (actionValidator.supports(validContext.getAction())) {
				return actionValidator.validator(utilities, validContext, action, this);
			}
		}
		return null;
	}
}
