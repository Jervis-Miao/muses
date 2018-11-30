/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.mvc.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public class BaseLogComponent {
	protected Logger	log	= LoggerFactory.getLogger(this.getClass());

	public BaseLogComponent() {
	}
}
