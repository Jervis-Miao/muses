package com.muses.common.orm.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.ConversionService;

/**
 *
 * @author Jervis
 * @date 14/12/18 018
 */
public class OrmContext {
	private ConversionService	conversionService;

	private Map<String, Object>	sessions	= new HashMap<>();

	public Map<String, Object> getSessions() {
		return sessions;
	}

	public void setSessions(Map<String, Object> sessions) {
		this.sessions = sessions;
	}

	public ConversionService getConversionService() {
		return conversionService;
	}

	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

}
