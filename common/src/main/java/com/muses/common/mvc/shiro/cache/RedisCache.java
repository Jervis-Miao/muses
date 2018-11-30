package com.muses.common.mvc.shiro.cache;

import static com.muses.common.mvc.shiro.session.SerialUtils.computeKey;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;

import com.muses.common.external.springside.modules.nosql.redis.JedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class RedisCache<K, V> implements Cache<K, V> {

	private static final Logger	log			= LoggerFactory.getLogger(RedisCache.class);
	public static final String	separator	= ":";

	private String				name;

	private byte[]				prefix;

	private JedisTemplate		jedisTemplate;

	private int					expire;

	public RedisCache(JedisTemplate jedisTemplate, String name, String prefix, int expire) {
		if ((jedisTemplate == null) || (StringUtils.isEmpty(name)) || (prefix == null)) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.jedisTemplate = jedisTemplate;
		this.expire = expire;
		this.name = name;
		// 根据name和prefix生成新的prefix
		this.prefix = new StringBuilder(prefix).append(separator).append(name).append(separator).toString()
				.getBytes(Charset.forName("UTF-8"));

	}

	@Override
	public V get(final K key) throws CacheException {
		log.trace("Getting object from cache [" + name + "] for key [" + key + "]");
		try {
			if (key == null) {
				return null;
			} else {
				return jedisTemplate.execute(new JedisTemplate.JedisAction<V>() {

					@Override
					public V action(Jedis jedis) {
						byte[] element = jedis.get(computeKey(key, getPrefix()));
						if (element == null) {
							log.trace("Element for [" + key + "] is null.");
							return null;
						} else {
							// noinspection unchecked
							return (V) SerializationUtils.deserialize(element);
						}
					}
				});
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	/**
	 * Puts an object into the cache.
	 *
	 * @param key the key.
	 * @param value the value.
	 */
	@Override
	public V put(final K key, final V value) throws CacheException {
		log.trace("Putting object in cache [" + name + "] for key [" + key + "]");
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<V>() {

				@Override
				public V action(Jedis jedis) {
					Pipeline pipelined = jedis.pipelined();
					byte[] k = computeKey(key, getPrefix());
					pipelined.set(k, SerializationUtils.serialize(value));
					if (expire > 0) {
						pipelined.expire(k, expire);
					}
					pipelined.sync();
					return null;
				}
			});
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	/**
	 * Removes the element which matches the key.
	 *
	 * <p>
	 * If no element matches, nothing is removed and no Exception is thrown.
	 * </p>
	 *
	 * @param key the key of the element to remove
	 */
	@Override
	public V remove(final K key) throws CacheException {
		log.trace("Removing object from cache [" + name + "] for key [" + key + "]");
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<V>() {
				@Override
				public V action(Jedis jedis) {
					jedis.del(computeKey(key, getPrefix()));
					return null;
				}
			});
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	/**
	 * Removes all elements in the cache, but leaves the cache in a useable state.
	 */
	@Override
	public void clear() throws CacheException {
		throw new UnsupportedOperationException("RedisCache 因性能原因未实现该方法，不应使用");
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException("RedisCache 因性能原因未实现该方法，不应使用");
	}

	@Override
	public Set<K> keys() {
		throw new UnsupportedOperationException("RedisCache 因性能原因未实现该方法，不应使用");
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException("RedisCache 因性能原因未实现该方法，不应使用");
	}

	@Override
	public String toString() {
		return "RedisCache [" + name + "]";
	}

	public byte[] getPrefix() {
		return prefix;
	}
}
