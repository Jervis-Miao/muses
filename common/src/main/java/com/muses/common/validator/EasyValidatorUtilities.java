/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muses.common.validator.action.ActionValidatorChain;
import com.muses.common.validator.data.Action;
import com.muses.common.validator.data.Entity;
import com.muses.common.validator.data.Group;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public class EasyValidatorUtilities {
	private final Logger				logger					= LoggerFactory.getLogger(getClass());
	private final ActionValidatorChain	actionValidatorChain	= new ActionValidatorChain();
	private final EasyValidatorContext	context;

	public EasyValidatorUtilities() {
		this(new EasyValidatorContext());
	}

	public EasyValidatorUtilities(EasyValidatorContext context) {
		this.context = context;
	}

	public boolean supports(Class<?> clazz) {
		return context.getEntity(clazz.getCanonicalName()) != null;
	}

	/**
	 * <pre>
	 * 执行对象校验
	 * 一个校验对象对应多个字段. 每个字段又存在多个校验规则
	 * </pre>
	 *
	 * @param target
	 * @param groups
	 * @return
	 */
	public List<EasyFieldError> validator(Object target, String[] groups) {
		List<EasyFieldError> errors = new ArrayList<>();
		if (target == null) {
			return errors;
		}

		List<Action> actions = new ArrayList<>();
		Entity entity = context.getEntity(target.getClass().getCanonicalName());
		for (String key : groups) {
			Group group = entity.getGroup(key);
			if (group == null) {
				IllegalArgumentException cause = new IllegalArgumentException(String.format("%s校验分组不存在", key));
				logger.error(cause.getMessage(), cause);
				throw cause;
			}
			for (Action action : group.getActions()) {
				if (!actions.contains(action)) {
					actions.add(action);
				}
			}
		}
		for (Action action : actions) {
			validator(target, action, errors);
		}
		return errors;
	}

	public List<EasyFieldError> validator(Object target, Action action) {
		return validator(target, action, new ArrayList<>());
	}

	private List<EasyFieldError> validator(Object target, Action action, List<EasyFieldError> errors) {
		Entity entity = context.getEntity(target.getClass().getCanonicalName());
		ValidContext validContext = new ValidContext(target, entity, errors);
		return actionValidatorChain.validator(this, validContext, action);
	}

	public EasyValidatorContext getContext() {
		return context;
	}
}
