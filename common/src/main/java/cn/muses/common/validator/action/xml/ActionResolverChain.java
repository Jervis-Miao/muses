/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.validator.action.xml;

import java.util.List;

import cn.muses.common.validator.data.Action;
import cn.muses.common.validator.data.Entity;
import cn.muses.common.validator.config.XmlElement;

/**
 * Action校验解析器链
 *
 * @author Jervis
 * @date 2018/11/29.
 */
public class ActionResolverChain {
	private List<ActionResolver>	resolvers;

	public ActionResolverChain(List<ActionResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public List<Action> resolve(XmlElement element, Entity entity, Action action) {
		for (ActionResolver actionResolver : resolvers) {
			if (actionResolver.supports(element)) {
				return actionResolver.resolve(element, entity, action, this);
			}
		}
		return null;
	}
}
