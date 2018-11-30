/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator.action.xml;

import java.util.List;

import com.muses.common.validator.config.XmlElement;
import com.muses.common.validator.data.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muses.common.validator.data.Action;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public interface ActionResolver<T extends Action> {
	Logger	logger	= LoggerFactory.getLogger(ActionResolver.class);

	/**
	 * 依据标签来判断是否支持
	 * @param xmlElement
	 * @return
	 */
	boolean supports(XmlElement xmlElement);

	/**
	 * 解析XML转换为Action对象集合
	 * @param element
	 * @param entity
	 * @param action
	 * @param chain
	 * @return
	 */
	List<T> resolve(XmlElement element, Entity entity, Action action, ActionResolverChain chain);
}
