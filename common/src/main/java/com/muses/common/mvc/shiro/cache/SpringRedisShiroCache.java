package com.muses.common.mvc.shiro.cache;

import static com.muses.common.mvc.shiro.session.SerialUtils.computeKey;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.muses.common.mvc.shiro.session.SerialUtils;

/**
 * 用 Spring data redis 实现 shiro cache
 * @author Jervis
 */
@SuppressWarnings("unchecked")
public class SpringRedisShiroCache<K, V> implements Cache<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringRedisShiroCache.class);
    public static final String SEPARATOR = ":";

    private String name;

    private byte[] prefix;

    private RedisTemplate redisTemplate;

    private int expire;

    SpringRedisShiroCache(RedisTemplate redisTemplate, String name, String prefix, int expire) {
        if ((redisTemplate == null) || (StringUtils.isEmpty(name)) || (prefix == null)) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.redisTemplate = redisTemplate;
        this.expire = expire;
        this.name = name;
        // 根据name和prefix生成新的prefix
        this.prefix = (prefix + SEPARATOR + name + SEPARATOR).getBytes(Charset.forName("UTF-8"));

    }

    @Override
    public V get(final K key) throws CacheException {
        LOGGER.trace("Getting object from cache [" + name + "] for key [" + key + "]");
        return (V) this.redisTemplate.execute((RedisCallback) connection -> {
            byte[] o = connection.get(computeKey(key, getPrefix()));
            if (o == null) {
                LOGGER.trace("Element for [" + key + "] is null.");
            }
            return SerialUtils.deserialize(o);
        });

    }

    /**
     * Puts an object into the cache.
     *
     * @param key the key.
     * @param value the value.
     */
    @Override
    public V put(final K key, final V value) throws CacheException {
        LOGGER.trace("Putting object in cache [" + name + "] for key [" + key + "]");
        byte[] k = computeKey(key, getPrefix());
        return (V) this.redisTemplate.execute((RedisCallback) connection -> {
            byte[] o = connection.get(k);
            if (expire > 0) {
                connection.setEx(k, expire, SerialUtils.serialize(value));
            } else {
                connection.set(k, SerialUtils.serialize(value));
            }
            return SerialUtils.deserialize(o);
        });
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
        LOGGER.trace("Removing object from cache [" + name + "] for key [" + key + "]");
        byte[] k = computeKey(key, getPrefix());
        return (V) this.redisTemplate.execute((RedisCallback) connection -> {
            byte[] o = connection.get(k);
            connection.del(k);
            return SerialUtils.deserialize(o);
        });
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
