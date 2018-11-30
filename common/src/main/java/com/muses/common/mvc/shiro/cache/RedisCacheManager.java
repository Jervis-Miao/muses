package com.muses.common.mvc.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.muses.common.external.springside.modules.nosql.redis.JedisTemplate;

/**
 * com.muses.common.mvc.shiro.cache 使用Redis实现Cache的CacheManager<br/>
 * 为防止公用的Redis连接池被此处错误销毁，连接池应自行管理。
 *
 * @author Jervis
 * @version 1.0.0 Created by Jervis on 2014/11/4 14:43.
 */
public class RedisCacheManager extends AbstractCacheManager {

	public static final String	DEFAULT_PREFIX		= "RC:";

	public static final int		DEFAULT_EXPIRE_TIME	= 30 * 60;

	private JedisTemplate		jedisTemplate;

	private String				prefix				= DEFAULT_PREFIX;

	/**
	 * 过期时间，单位 秒；默认30分钟。
	 */
	private int					expireTime			= DEFAULT_EXPIRE_TIME;

	/**
	 * 是否开启过期，默认true；如果为false，则 {@link #expireTime } 无效
	 */
	private boolean				expireEnable		= true;

	public RedisCacheManager() {
		super();
	}

	@Override
	protected Cache createCache(String name) throws CacheException {
		int expire = expireTime;
		if (!expireEnable) {
			expire = 0;
		}
		return new RedisCache(jedisTemplate, name, prefix, expire);
	}

	public void setJedisTemplate(JedisTemplate jedisTemplate) {
		this.jedisTemplate = jedisTemplate;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	public void setExpireEnable(boolean expireEnable) {
		this.expireEnable = expireEnable;
	}
}
