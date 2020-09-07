/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.validator.action;

import java.util.List;

import cn.muses.common.validator.EasyFieldError;
import cn.muses.common.validator.EasyValidatorUtilities;
import cn.muses.common.validator.ValidContext;
import cn.muses.common.validator.data.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
