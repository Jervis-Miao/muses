/*
Copyright 2018 All rights reserved.
 */

package cn.muses.provider;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

/**
 * @author miaoqiang
 * @date 2018/12/4.
 */
public abstract class AbstractCache<T> {
	public T getFromRedisByBucket(RedissonClient redissonClient, String key) {
		RBucket<T> bucket = redissonClient.getBucket(key);
		T v = bucket.get();
		return v;
	}
}
