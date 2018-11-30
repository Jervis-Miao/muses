package com.muses.common.mvc.shiro.session;

import java.util.Map;

/**
 * 被代理的外部缓存实现抽象为接口，为了同时兼容新老版本实现
 * @author Jervis
 */
public interface ExternalCacheMap<K,V> extends Map<K,V> {
}
