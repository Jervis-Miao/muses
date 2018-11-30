/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator.data;

/**
 * Action抽象的一个执行动作
 * 
 * @author Jervis
 * @date 2018/11/29.
 */
public interface Action {
	void addAction(Action action);
}
