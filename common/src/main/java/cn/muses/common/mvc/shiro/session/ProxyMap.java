package cn.muses.common.mvc.shiro.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author mengfanjun
 */
public class ProxyMap<K, V> implements Map<K, V>, Serializable {
    private static final long serialVersionUID = 2605257258112239245L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyMap.class);

    private transient ExternalCacheMap cacheMap;
    private Map attributes;

    public ProxyMap(Map attributes) {
        this.attributes = attributes;
        Assert.notNull(attributes, "attributes cannot be null.");
    }

    @Override
    public void clear() {
        attributes.clear();
        cacheMap.clear();
    }

    @Override
    public boolean containsKey(final Object key) {
        if (isInternalAttributeKey(key)) {
            return attributes.containsKey(key);
        }
        return cacheMap.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return attributes.containsValue(value) || cacheMap.containsValue(value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        LOGGER.trace("取 entrySet");
        final Map<K, V> result = new HashMap<K, V>();
        for (Entry entry : (Set<Entry>) attributes.entrySet()) {
            result.put((K) entry.getKey(), (V) entry.getValue());
        }
        for (Entry entry : (Set<Entry>) cacheMap.entrySet()) {
            result.put((K) entry.getKey(), (V) entry.getValue());
        }
        return result.entrySet();
    }

    @Override
    public V get(final Object key) {
        LOGGER.trace("尝试读取: {}", key);
        if (isInternalAttributeKey(key)) {
            LOGGER.trace("内部属性");
            return (V) attributes.get(key);
        }
        LOGGER.trace("外部属性");
        return (V) cacheMap.get(key);
    }

    @Override
    public boolean isEmpty() {
        // redis中 第一个实体永远是Session 本身，不可能为空。
        // 此处之所以不以计数减一计算有两个原因：
        // 1. 计数减一计算，如果要保证准确，每次请求（内建属性未设置情况）至少额外多请求4次Redis。
        // 2.无法和keySet 保持一致（ketSet中永远有值），如果去掉keySet 默认的SessionId,需要再处理，目前没必要。
        return false;
    }

    @Override
    public Set<K> keySet() {
        LOGGER.trace("取 KeySet");
        final Set<K> result = new HashSet<K>();
        for (Object key : attributes.keySet()) {
            result.add((K) key);
        }
        for (Object key : cacheMap.keySet()) {
            result.add((K) key);
        }
        return result;
    }

    @Override
    public V put(final K key, final V value) {
        LOGGER.trace("尝试更新/保存: {}-{}", key, value);
        if (isInternalAttributeKey(key)) {
            return (V) attributes.put(key, value);
        }
        return (V) cacheMap.put(key, value);
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        if (m == null || m.isEmpty()) {
            return;
        }
        Map<K, V> toRedis = new HashMap<K, V>();
        for (K k : m.keySet()) {
            if (isInternalAttributeKey(k)) {
                attributes.put(k, m.get(k));
            } else {
                toRedis.put(k, m.get(k));
            }
        }
        cacheMap.putAll(toRedis);
    }

    @Override
    public V remove(final Object key) {
        LOGGER.trace("尝试移除: {}", key);
        if (isInternalAttributeKey(key)) {
            return (V) attributes.remove(key);
        }
        return (V) cacheMap.remove(key);
    }

    @Override
    public int size() {
        int size = attributes.size() + cacheMap.size();
        LOGGER.trace("获取长度: {}", size);
        return size;
    }

    @Override
    public Collection<V> values() {
        LOGGER.trace("取 values");
        List<V> result = new ArrayList<V>(attributes.size());
        for (Object val : attributes.values()) {
            result.add((V) val);
        }
        Collection<byte[]> values = cacheMap.values();
        for (byte[] val : values) {
            result.add((V) SerialUtils.deserialize(val));
        }
        return result;
    }

    /**
     * 判断是否为内部属性
     * @param key key
     * @return true（internal） or false
     */
    private boolean isInternalAttributeKey(Object key) {
        if (key instanceof String) {
            return SessionInternalAttrConfig.isInternal((String) key);
        }
        return false;
    }

    public void setCacheMap(ExternalCacheMap cacheMap) {
        this.cacheMap = cacheMap;
    }
}
