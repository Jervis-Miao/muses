/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 一般情况下, 一个验证请求可看做是一个Group.
 * 但当配置@ValidEasy注解value为多个值时, 一个验证请求对应多个Group
 * </pre>
 * 
 * @author Jervis
 * @date 2018/11/29.
 */
public class Group extends ToStringObject {
	// 名称
	private final String		name;
	private final List<Action>	actions	= new ArrayList<Action>();

	public Group(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}

	public void addActions(List<Action> actions) {
		this.actions.addAll(actions);
	}
}
