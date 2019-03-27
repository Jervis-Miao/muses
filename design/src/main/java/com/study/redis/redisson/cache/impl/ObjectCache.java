/*
Copyright 2018 All rights reserved.
 */

package com.study.redis.redisson.cache.impl;

import com.study.redis.redisson.cache.AbstractCache;
import com.study.redis.redisson.cache.dto.CacheParam;

/**
 * @author miaoqiang
 * @date 2019/3/18.
 */
public class ObjectCache extends AbstractCache<CacheParam, Object> {

	@Override
	public Object getFromDB(CacheParam cacheParam) {
		return new Object();
	}

	@Override
	public Long getKeyFromValue(Object value) {
		return 0L;
	}
}
