package cn.muses.common.mvc.shiro.session;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisOperations;

/**
 * 使用 spring-data-redis 实现的 redisMap <br/>
 * <b>注意： 涉及大量传输数据的内容，需要注意, 未做优化。如 containsValue， values 等</b>
 * @author Jervis
 */
public class SpringRedisMap<K, V> implements ExternalCacheMap<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringRedisMap.class);

    private final BoundHashOperations<byte[], K, V> hashOperations;

    @SuppressWarnings("unchecked")
    SpringRedisMap(RedisOperations operations, byte[] key) {
        this.hashOperations = operations.<K, V>boundHashOps(key);
    }

    @Override
    public int size() {
        return Math.toIntExact(hashOperations.size());
    }

    @Override
    public boolean isEmpty() {
        return hashOperations.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return hashOperations.hasKey(key);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean containsValue(Object value) {
        LOGGER.warn("SpringRedisMap 实现中调用 containsValue 方法，可能涉及到大量数据传输！");
        List<V> values = hashOperations.values();
        LOGGER.warn("SpringRedisMap 实现中调用 containsValue 方法，集合结果数为：{}！", values.size());
        return values.contains(value);
    }

    @Override
    public V get(Object key) {
        return hashOperations.get(key);
    }

    @Override
    public V put(K key, V value) {
        V rs = hashOperations.get(key);
        hashOperations.put(key, value);
        return rs;
    }

    @Override
    public V remove(Object key) {
        V rs = hashOperations.get(key);
        hashOperations.delete(key);
        return rs;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        hashOperations.putAll(m);
    }

    @Override
    public void clear() {
        LOGGER.warn("SpringRedisMap 实现中调用 clear 方法，可能涉及到大量数据传输！");
        Set<K> keys = hashOperations.keys();
        LOGGER.warn("SpringRedisMap 实现中调用 clear 方法，集合结果数为：{}！", keys.size());
        hashOperations.delete(keys);
    }

    @Override
    public Set<K> keySet() {
        LOGGER.warn("SpringRedisMap 实现中调用 keySet 方法，可能涉及到大量数据传输！");
        Set<K> keys = hashOperations.keys();
        LOGGER.warn("SpringRedisMap 实现中调用 keySet 方法，集合结果数为：{}！", keys.size());
        return keys;
    }

    @Override
    public Collection<V> values() {
        LOGGER.warn("SpringRedisMap 实现中调用 values 方法，可能涉及到大量数据传输！");
        List<V> values = hashOperations.values();
        LOGGER.warn("SpringRedisMap 实现中调用 values 方法，集合结果数为：{}！", values.size());
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        LOGGER.warn("SpringRedisMap 实现中调用 entrySet 方法，可能涉及到大量数据传输！");
        Map<K, V> entries = hashOperations.entries();
        LOGGER.warn("SpringRedisMap 实现中调用 entrySet 方法，集合结果数为：{}！", entries.size());
        return entries.entrySet();
    }


}
