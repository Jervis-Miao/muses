package cn.muses.common.mvc.shiro.cache;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatch;
import org.redisson.api.RBucketAsync;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.muses.common.mvc.shiro.session.SerialUtils;

/**
 * 用 redisson 实现 shiro cache
 * @author Jervis
 */
@SuppressWarnings("unchecked")
public class RedissonShiroCache<K, V> implements Cache<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonShiroCache.class);
    public static final String SEPARATOR = ":";

    private String name;

    private String prefix;

    private RedissonClient redissonClient;

    private int expire;

    RedissonShiroCache(RedissonClient redissonClient, String name, String prefix, int expire) {
        if ((redissonClient == null) || (StringUtils.isEmpty(name)) || (prefix == null)) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.redissonClient = redissonClient;
        this.expire = expire;
        this.name = name;
        // 根据name和prefix生成新的prefix
        this.prefix = prefix + SEPARATOR + name + SEPARATOR;
    }

    @Override
    public V get(final K key) throws CacheException {
        LOGGER.trace("Getting object from cache [" + name + "] for key [" + key + "]");
        return (V) redissonClient.getBucket(getSerializeKey(key)).get();
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
        V rs;
        if (expire > 0) {
            RBatch batch = redissonClient.createBatch(BatchOptions.defaults());
            RBucketAsync<Object> bucket = batch.getBucket(getSerializeKey(key));
            RFuture<Object> rsAsync = bucket.getAndSetAsync(value);
            bucket.expireAsync(expire, TimeUnit.SECONDS);
            batch.execute();
            rs = (V) rsAsync.getNow();
        } else {
            rs = (V) redissonClient.getBucket(getSerializeKey(key)).getAndSet(value);
        }
        return rs;
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
        return (V) redissonClient.getBucket(getSerializeKey(key)).getAndDelete();
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

    public String getPrefix() {
        return prefix;
    }

    private String getSerializeKey(K key) {
        String seKey = "empty";
        if (null != key) {
            if (key instanceof String) {
                seKey = (String) key;
            } else {
                seKey = new String(SerialUtils.serialize(key));
            }
        }
        return getPrefix() + seKey;
    }
}
