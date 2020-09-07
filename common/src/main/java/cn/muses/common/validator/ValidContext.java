/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.validator;

import java.util.List;

import cn.muses.common.validator.data.Action;
import cn.muses.common.validator.data.Entity;
import cn.muses.common.validator.data.Valid;

/**
 * 校验上下文. 一个校验规则(Valid)对应一个校验上下文
 *
 * @author Jervis
 * @date 2018/11/29.
 */
public class ValidContext {
	/**
	 * 原始对象
	 */
	private final Object				target;
	private final Entity				entity;
	private final List<EasyFieldError>	errors;
	private Action						action;
	private Valid						valid;

	public ValidContext(Object target, Entity entity, List<EasyFieldError> errors) {
		super();
		this.target = target;
		this.entity = entity;
		this.errors = errors;
	}

	public Object getTarget() {
		return target;
	}

	public Entity getEntity() {
		return entity;
	}

	public List<EasyFieldError> getErrors() {
		return errors;
	}

	public Valid getValid() {
		return valid;
	}

	public void setValid(Valid valid) {
		this.valid = valid;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
}
