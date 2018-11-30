package com.muses.common.mvc.shiro.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.shiro.cache.Cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * SingleValueThreadLocalCache <br/>
 * 单值的本地线程Cache，不论put的key为什么，都会替换掉原值
 *
 * @version 1.0.0 Created by Jervis on 2014/11/3 18:03.
 */
public class SingleValueThreadLocalCache<K, V> implements Cache<K, V> {

    private final ThreadLocal<Pair<K, V>> local = new ThreadLocal<Pair<K, V>>();

    private String name;

    public SingleValueThreadLocalCache(String name) {
        this.name = name;
    }

    @Override
    public V get(K key) {
        if (null == key) {
            return null;
        }
        Pair<K, V> pair = local.get();
        return (null != pair && key.equals(pair.getKey())) ? pair.getV() : null;
    }

    @Override
    public V put(K key, V value) {
        if (null == key) {
            return null;
        }
        Pair<K, V> pair = local.get();
        if (null == pair) {
            pair = new Pair<K, V>();
        }
        V v = key.equals(pair.getKey()) ? pair.getV() : null;
        pair.setKey(key);
        pair.setV(value);
        local.set(pair);
        return v;
    }

    @Override
    public V remove(K key) {
        V v = get(key);
        if (null != v) {
            local.remove();
        }
        return v;
    }

    @Override
    public void clear() {
        local.remove();
    }

    @Override
    public int size() {
        Pair<K, V> pair = local.get();
        return (null != pair && null != pair.getV()) ? 1 : 0;
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = null;
        Pair<K, V> pair = local.get();

        if (null != pair && null != pair.getKey()) {
            keys = Sets.newHashSet();
            keys.add(pair.getKey());
        } else {
            keys = Collections.emptySet();
        }

        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = null;
        Pair<K, V> pair = local.get();
        if (null != pair && null != pair.getV()) {
            values = Lists.newArrayList();
            values.add(pair.getV());
        } else {
            values = Collections.emptyList();
        }
        return values;
    }

    @Override
    public String toString() {
        return new StringBuilder("SingleValueThreadLocalCache '").append(name).append("' (").append(size())
                .append(" entries)").toString();
    }

    private final class Pair<KK, VV> {

        private KK key;

        private VV value;

        /**
         * @return the key
         */
        public KK getKey() {
            return key;
        }

        /**
         * @param key the key to set
         */
        public void setKey(KK key) {
            this.key = key;
        }

        /**
         * @return the v
         */
        public VV getV() {
            return value;
        }

        /**
         * @param v the v to set
         */
        public void setV(VV v) {
            this.value = v;
        }

    }

}
