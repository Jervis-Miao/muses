package com.muses.common.mvc.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.redisson.api.RedissonClient;

/**
 * com.muses.common.mvc.shiro.cache 使用 redisson 实现Cache的CacheManager<br/>
 *
 * @author Jervis
 */
public class RedissonShiroCacheManager extends AbstractCacheManager {

    public static final String DEFAULT_PREFIX = "RC:";

    public static final int DEFAULT_EXPIRE_TIME = 30 * 60;

    private RedissonClient redissonClient;

    private String prefix = DEFAULT_PREFIX;

    /**
     * 过期时间，单位 秒；默认30分钟。
     */
    private int expireTime = DEFAULT_EXPIRE_TIME;

    /**
     * 是否开启过期，默认true；如果为false，则 {@link #expireTime } 无效
     */
    private boolean expireEnable = true;

    public RedissonShiroCacheManager() {
        super();
    }

    @Override
    protected Cache createCache(String name) throws CacheException {
        int expire = expireTime;
        if (!expireEnable) {
            expire = 0;
        }
        return new RedissonShiroCache(redissonClient, name, prefix, expire);
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
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
