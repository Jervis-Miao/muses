/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.validator.action;

import java.util.List;

import cn.muses.common.validator.EasyFieldError;
import cn.muses.common.validator.EasyValidatorUtilities;
import cn.muses.common.validator.ValidContext;
import cn.muses.common.validator.data.Action;

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
