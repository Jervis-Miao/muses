package cn.muses.common.mvc.shiro.session;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用 redisson 实现的 redisMap <br/>
 * <b>注意： 涉及大量传输数据的内容，需要注意, 未做优化。如 containsValue， values 等</b>
 * @author Jervis
 */
public class RedissonCacheMap<K, V> implements ExternalCacheMap<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonCacheMap.class);

    private final RMap<K, V> rMap;

    RedissonCacheMap(RedissonClient redissonClient, byte[] key) {
        rMap = redissonClient.getMap(new String(key));
    }

    @Override
    public int size() {
        return rMap.size();
    }

    @Override
    public boolean isEmpty() {
        return rMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return rMap.containsKey(key);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean containsValue(Object value) {
        return rMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return rMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return rMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return rMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        rMap.putAll(m);
    }

    @Override
    public void clear() {
        rMap.clear();
    }

    @Override
    public Set<K> keySet() {
        LOGGER.warn("RedissonCacheMap 实现中调用 keySet 方法，底层使用异步实现，可能涉及到大量数据传输！");
        return rMap.keySet();
    }

    @Override
    public Collection<V> values() {
        LOGGER.warn("RedissonCacheMap 实现中调用 values 方法，底层使用异步实现，具体取值可能涉及到大量数据传输！");
        return rMap.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return rMap.entrySet();
    }

}
