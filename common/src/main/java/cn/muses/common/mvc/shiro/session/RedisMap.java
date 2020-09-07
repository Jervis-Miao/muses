package cn.muses.common.mvc.shiro.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.muses.common.external.springside.modules.nosql.redis.JedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @author mengfanjun
 */
@SuppressWarnings("unchecked")
public class RedisMap<K, V> implements ExternalCacheMap<K, V> {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(RedisMap.class);
	private final JedisTemplate	jedisTemplate;
	// session cache key(对应redis hash key)
	private final byte[]		k;

	RedisMap(JedisTemplate jedisTemplate, byte[] k) {
		this.jedisTemplate = jedisTemplate;
		this.k = k;
	}

	@Override
	public void clear() {
		LOGGER.trace("尝试清理Redis");
		try {
			jedisTemplate.execute(new JedisTemplate.JedisActionNoResult() {
				@Override
				public void action(Jedis jedis) {
					Set<byte[]> keys = jedis.hkeys(k);
					byte[][] fields = keys.toArray(new byte[keys.size()][]);
					jedis.hdel(k, fields);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！未成功清理", e);
		}
	}

	@Override
	public boolean containsKey(final Object key) {
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<Boolean>() {
				@Override
				public Boolean action(Jedis jedis) {
					return jedis.hexists(k, SerialUtils.serialize(key));
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！伪造结果 false", e);
			return false;
		}
	}

	@Override
	public boolean containsValue(final Object value) {
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<Boolean>() {
				@Override
				public Boolean action(Jedis jedis) {
					byte[] v = SerialUtils.serialize(value);
					List<byte[]> vals = jedis.hvals(k);
					return vals != null && vals.contains(v);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！伪造结果 false", e);
			return false;
		}
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<Set<Entry<K, V>>>() {
				@Override
				public Set<Map.Entry<K, V>> action(Jedis jedis) {
					Map<byte[], byte[]> bytes = jedis.hgetAll(k);
					Map<K, V> result = new HashMap<K, V>(bytes.size());
					for (byte[] key : bytes.keySet()) {
						result.put((K) SerialUtils.deserialize(key), (V) SerialUtils.deserialize(bytes.get(key)));
					}
					return result.entrySet();
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！伪造结果 空 HashSet", e);
			return new HashSet<Map.Entry<K, V>>();
		}
	}

	@Override
	public V get(final Object key) {
		LOGGER.trace("尝试从Redis中读取: {}", key);
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<V>() {
				@Override
				public V action(Jedis jedis) {
					byte[] val = jedis.hget(k, SerialUtils.serialize(key));
					return (V) SerialUtils.deserialize(val);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！伪造结果 Null", e);
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		LOGGER.trace("判断Redis是否为空");
		Long size = null;
		try {
			size = jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
				@Override
				public Long action(Jedis jedis) {
					return jedis.hlen(k);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！伪造结果 0", e);
			size = 0L;
		}
		return size == 0;
	}

	@Override
	public Set<K> keySet() {
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<Set<K>>() {
				@Override
				public Set<K> action(Jedis jedis) {
					Set<byte[]> keys = jedis.hkeys(k);
					Set<K> result = new HashSet<K>(keys.size());
					for (byte[] key : keys) {
						result.add((K) SerialUtils.deserialize(key));
					}
					return result;
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！伪造结果 空 HashSet", e);
			return new HashSet<K>();
		}
	}

	@Override
	public V put(final K key, final V value) {
		LOGGER.trace("尝试在Redis设置: {}-{}", key, value);
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<V>() {
				@Override
				public V action(Jedis jedis) {
					byte[] f = SerialUtils.serialize(key);
					byte[] v = SerialUtils.serialize(value);
					byte[] val = jedis.hget(k, f);
					jedis.hset(k, f, v);
					return (V) SerialUtils.deserialize(val);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！数据未保存，伪造结果 null", e);
			return null;
		}
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		try {
			jedisTemplate.execute(new JedisTemplate.JedisActionNoResult() {
				@Override
				public void action(Jedis jedis) {
					Map<byte[], byte[]> vals = new HashMap<byte[], byte[]>(m.size());
					for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
						byte[] f = SerialUtils.serialize(entry.getKey());
						byte[] v = SerialUtils.serialize(entry.getValue());
						vals.put(f, v);
					}
					jedis.hmset(k, vals);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！数据未保存", e);
		}
	}

	@Override
	public V remove(final Object key) {
		LOGGER.trace("尝试从Redis移除: {}", key);
		try {
			return jedisTemplate.execute(new JedisTemplate.JedisAction<V>() {
				@Override
				public V action(Jedis jedis) {
					byte[] bytes = jedis.hget(k, SerialUtils.serialize(key));
					jedis.hdel(k, SerialUtils.serialize(key));
					return (V) SerialUtils.deserialize(bytes);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！数据未删除，伪造结果 null", e);
			return null;
		}
	}

	@Override
	public int size() {
		Long size = null;
		try {
			size = jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
				@Override
				public Long action(Jedis jedis) {
					return jedis.hlen(k);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！伪造结果 0", e);
			size = 0L;
		}
		LOGGER.trace("尝试从Redis获取大小: {}", size);
		return size.intValue();
	}

	@Override
	public Collection<V> values() {
		try {
			Collection<byte[]> values = jedisTemplate.execute(new JedisTemplate.JedisAction<Collection<byte[]>>() {
				@Override
				public Collection<byte[]> action(Jedis jedis) {
					return jedis.hvals(k);
				}
			});
			List<V> list = new ArrayList<V>(values.size());
			for (byte[] v : values) {
				list.add((V) SerialUtils.deserialize(v));
			}
			return list;
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！伪造结果 空 ArrayList", e);
			return new ArrayList<V>();
		}
	}
}
