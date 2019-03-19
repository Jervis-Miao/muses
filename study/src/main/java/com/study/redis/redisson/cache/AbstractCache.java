/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.redis.redisson.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.study.redis.redisson.RedissonUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import com.muses.common.orm.mybatis.easylist.list.utils.ObjectUtils;
import com.study.redis.redisson.cache.constant.RedisCacheConstant;
import com.study.redis.redisson.cache.dto.CacheParam;

/**
 * redis 缓存处理器
 *
 * @param <K> 参数
 * @param <V> 返回结果
 *
 * @author miaoqiang
 * @date 2018/10/17.
 */
public abstract class AbstractCache<K extends CacheParam, V> {
	private Logger								log				= LoggerFactory.getLogger(this.getClass());

	protected static final ThreadPoolExecutor	setDataToRedisCachePool;

	protected RedissonClient					redissonClient	= RedissonUtils.getInstance().getRedissonClient();

	static {
		setDataToRedisCachePool = new ThreadPoolExecutor(10, 10, 10L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
				new NamedThreadFactory("SetDataToRedisCachePool"));
		setDataToRedisCachePool.allowCoreThreadTimeOut(Boolean.TRUE);
	}

	/**
	 * 获取缓存对象
	 *
	 * @param cacheParam
	 * @return
	 */
	private V getData(K cacheParam) {
		try {
			return this.getFromDB(cacheParam);
		} catch (Exception e) {
			log.info("AbstractCache.getData: " + cacheParam.getPrefixKey() + cacheParam.getKey() + ", Exception", e);
		}
		return null;
	}

	/**
	 * 从数据库获取实体
	 *
	 * @param cacheParam
	 * @return
	 */
	protected abstract V getFromDB(K cacheParam);

	/**
	 * 获取设置缓存锁键
	 * 
	 * @param cacheParam
	 * @return
	 */
	private String getLockKey(K cacheParam) {
		return RedisCacheConstant.LOCK_PREFIX_KEY + cacheParam.getPrefixKey() + cacheParam.getKey();
	}

	/**
	 * 获取自定义设置缓存锁键
	 * 
	 * @param cacheParam
	 * @param value
	 * @return
	 */
	protected String getCustomLockKey(K cacheParam, V value) {
		log.error("Can not getCustomLockKey by class: " + this.getClass().getName());
		throw new RuntimeException("Can not getCustomLockKey by class: " + this.getClass().getName());
	}

	/**
	 * 自定义获取缓存，不局限于某种形式，可以是组合数据/批量操作等等
	 *
	 * @param cacheParam
	 * @return
	 */
	public V customGet(K cacheParam) {
		return null;
	}

	/**
	 * 自定义缓存设置任务
	 *
	 * @param cacheParam
	 * @param value
	 */
	protected void customSetCache(K cacheParam, V value) {
		return;
	}

	/**
	 * 先从数据库获取数据并返回，同时将数据设置到redis缓存中
	 *
	 * @param cacheParam
	 * @return
	 */
	public V customGetAndSet(K cacheParam) {
		if (cacheParam.getKey() == null) {
			return null;
		}

		// 获取数据
		V value = this.getData(cacheParam);

		// 设置缓存
		this.customSetAsync(cacheParam, value);

		return value;
	}

	/**
	 * 自定义设置缓存，不局限于某种形式，可以是组合数据/批量操作等等
	 *
	 * @param cacheParam
	 * @return
	 */
	public void customSetAsync(K cacheParam) {
		this.customSetAsync(cacheParam, null);
	}

	/**
	 * 异步自定义设置缓存
	 *
	 * @param cacheParam
	 * @param value
	 */
	public void customSetAsync(K cacheParam, V value) {
		if (ObjectUtils.isNotEmpty(cacheParam.getKey())) {
			setDataToRedisCachePool.submit(new CustomSetCacheTask(cacheParam, value, this));
		}
	}

	/**
	 * 获取缓存信息RBucket形式
	 *
	 * @param cacheParam
	 * @return
	 */
	public V get(K cacheParam) {
		try {
			RBucket<V> bucket;

			if (ObjectUtils.isNotEmpty(cacheParam.getCodec())) {
				bucket = redissonClient.getBucket(cacheParam.getPrefixKey() + cacheParam.getKey(),
						cacheParam.getCodec());
			} else {
				bucket = redissonClient.getBucket(cacheParam.getPrefixKey() + cacheParam.getKey());
			}

			V v = bucket.get();

			if (v == null) {
				v = this.getAndSet(cacheParam);
			}

			// 因为redisson内部重新定义了返回结果泛型类型，因此这里需要做泛型判断
			if (ObjectUtils.isNotEmpty(v)) {
				this.checkResultType(cacheParam, v);
			}

			return v;
		} catch (Exception e) {
			this.log.info("AbstractCache.getFromRedisByBucket Key = " + cacheParam.getPrefixKey() + cacheParam.getKey()
					+ ", 从redis中获取信息异常: " + e.getMessage(), e);
			return this.getAndSet(cacheParam);
		}
	}

	/**
	 * 缓存设置任务，RBucket
	 *
	 * @param cacheParam
	 * @param value
	 */
	protected void setCache(K cacheParam, V value) {
		RBucket<V> bucket;
		if (ObjectUtils.isNotEmpty(value)) {
			if (ObjectUtils.isNotEmpty(cacheParam.getCodec())) {
				bucket = redissonClient.getBucket(cacheParam.getPrefixKey() + cacheParam.getKey(),
						cacheParam.getCodec());
			} else {
				bucket = redissonClient.getBucket(cacheParam.getPrefixKey() + cacheParam.getKey());
			}
			bucket.delete();
			// 设置失效时间
			if (ObjectUtils.isNotEmpty(cacheParam.getTimeToLive()) && ObjectUtils.isNotEmpty(cacheParam.getTimeUnit())) {
				bucket.set(value, cacheParam.getTimeToLive(), cacheParam.getTimeUnit());
			} else {
				bucket.set(value);
			}
		}
	}

	/**
	 * 设置缓存信息RBucket形式，并返回信息
	 * 
	 * @param cacheParam
	 * @return
	 */
	public V getAndSet(K cacheParam) {
		if (cacheParam.getKey() == null) {
			return null;
		}

		// 获取数据
		V value = (V) this.getData(cacheParam);

		// 设置缓存
		this.setAsync(cacheParam, value);

		return value;
	}

	/**
	 * 设置缓存信息，RBucket
	 * 
	 * @param cacheParam
	 * @param value
	 */
	public void setAsync(K cacheParam) {
		this.setAsync(cacheParam, null);
	}

	/**
	 * 设置缓存信息，RBucket
	 * 
	 * @param cacheParam
	 * @param value
	 */
	public void setAsync(K cacheParam, V value) {
		if (ObjectUtils.isNotEmpty(cacheParam.getKey())) {
			setDataToRedisCachePool.submit(new SetCacheTask(cacheParam, value, this));
		}
	}

	public class CustomSetCacheTask implements Runnable {
		private K					cacheParam;
		private V					value;
		private AbstractCache<K, V>	cache;

		CustomSetCacheTask(K cacheParam, V value, AbstractCache cache) {
			this.cacheParam = cacheParam;
			this.value = value;
			this.cache = cache;
		}

		@Override
		public void run() {
			if (ObjectUtils.isEmpty(this.value)) {
				this.value = this.cache.getData(this.cacheParam);
			}
			if (ObjectUtils.isNotEmpty(this.value)) {
				// 自定义对象，使用复杂键，需value配合获取唯一性锁
				String lockKey = this.cache.getCustomLockKey(this.cacheParam, this.value);
				RLock lock = redissonClient.getLock(lockKey);
				try {
					boolean tryLock = lock.tryLock(3, TimeUnit.MINUTES);
					if (tryLock) {
						try {
							this.cache.customSetCache(this.cacheParam, this.value);
						} finally {
							lock.unlock();
						}
					}
				} catch (InterruptedException e) {
					log.info("CustomSetCacheTask exception", e);
					if (lock.isLocked()) {
						lock.unlock();
					}
				}
			}
		}
	}

	public class SetCacheTask implements Runnable {
		private K					cacheParam;
		private V					value;
		private AbstractCache<K, V>	cache;

		SetCacheTask(K cacheParam, V value, AbstractCache cache) {
			this.cacheParam = cacheParam;
			this.value = value;
			this.cache = cache;
		}

		@Override
		public void run() {
			// 简单对象使用单键，可以直接获取唯一锁
			String lockKey = this.cache.getLockKey(this.cacheParam);
			RLock lock = redissonClient.getLock(lockKey);
			try {
				boolean tryLock = lock.tryLock(1, TimeUnit.MINUTES);
				if (tryLock) {
					try {
						if (ObjectUtils.isEmpty(this.value)) {
							this.value = this.cache.getData(this.cacheParam);
						}
						if (ObjectUtils.isNotEmpty(this.value)) {
							this.cache.setCache(this.cacheParam, this.value);
						}
					} finally {
						lock.unlock();
					}
				}
			} catch (InterruptedException e) {
				log.info("SetCacheTask exception", e);
				if (lock.isLocked()) {
					lock.unlock();
				}
			}
		}
	}

	/**
	 * 检查返回结果
	 *
	 * @param v
	 */
	protected void checkResultType(K cacheParam, V value) {
		Long key = this.getKeyFromValue(value);
		if (ObjectUtils.isEmpty(key)) {
			// 获取类定义上的泛型类型
			Type genericSuperclass = this.getClass().getGenericSuperclass();
			Type[] genericType = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
			String className = genericType[1].getTypeName();
			if (ObjectUtils.isNotEmpty(value) && !className.equals(value.getClass().getName())) {
				throw new ClassCastException(value.getClass().getName() + " cannot be cast to " + className);
			}
		} else {
			// 返回结果合法检查
			if (cacheParam.getKey().compareTo(key) != 0) {
				throw new RuntimeException("The value with key " + key + " is error!");
			}
		}
	}

	/**
	 * 获取返回对象键
	 * 
	 * @param value
	 * @return
	 */
	protected Long getKeyFromValue(V value) {
		return null;
	}
}
