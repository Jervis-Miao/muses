package com.muses.common.mvc.shiro.session;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muses.common.external.springside.modules.nosql.redis.JedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

/**
 * com.muses.common.mvc.shiro.session 使用Redis为数据源的SessionDAO<br/>
 * 继承了CachingSessionDAO，可使用缓存来保存从Redis读取的Session。<br/>
 * 使用Redis的hash结构存储session的attribute，其中field=sessionId存放SimpleSession的基本信息
 *
 * @author Jervis
 * @version 1.0.0 Created by Jervis on 2014/11/3 18:38.
 */
public class RedisCacheSessionDAO extends AbstractMultiLevelCacheSessionDAO {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(RedisCacheSessionDAO.class);

	private JedisTemplate		jedisTemplate;

	public RedisCacheSessionDAO(JedisTemplate jedisTemplate, String prefix) {
		super(prefix);
		this.jedisTemplate = jedisTemplate;
	}

	@Override
	protected void doUpdate(final Session session) {
		LOGGER.trace("尝试从Redis中更新/保存Session: {}", session.getId());
		final byte[] k = SerialUtils.computeKey(session.getId(), getPrefix());
		final byte[] f = SerialUtils.serialize(session.getId());
		final byte[] v = SerialUtils.serialize(session);
		final int expiration = (int) (session.getTimeout() / 1000);
		try {
			jedisTemplate.execute(new JedisTemplate.JedisActionNoResult() {

				@Override
				public void action(Jedis jedis) {
					Pipeline p = jedis.pipelined();
					p.hset(k, f, v);
					if (expiration >= 0) {
						p.expire(k, expiration);
					}
					p.sync();
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！Session未写入", e);
		}
	}

	@Override
	protected void doDelete(Session session) {
		LOGGER.trace("尝试从Redis中删除Session: {}", session.getId());
		final byte[] k = SerialUtils.computeKey(session.getId(), getPrefix());
		try {
			jedisTemplate.execute(new JedisTemplate.JedisActionNoResult() {

				@Override
				public void action(Jedis jedis) {
					jedis.del(k);
				}
			});
		} catch (JedisException e) {
			LOGGER.error("Redis连接异常！未删除", e);
		}
	}

	@Override
	protected Session doReadSession(final Serializable sessionId) {
		LOGGER.trace("尝试从Redis中读取Session: {}", sessionId);
		Session session = null;
		try {
			session = jedisTemplate.execute(new JedisTemplate.JedisAction<Session>() {

				@Override
				public Session action(Jedis jedis) {
					byte[] bs = jedis.hget(SerialUtils.computeKey(sessionId, getPrefix()),
							SerialUtils.serialize(sessionId));
					Object value = SerialUtils.deserialize(bs);
					return null == value ? null : (Session) value;
				}
			});
			proxySessionAttributes(session);
		} catch (JedisException e) {
			// Redis如果挂了……
			LOGGER.error("Redis连接异常！伪造Session", e);
			return null;
		}
		LOGGER.trace("读取结束，是否取到？ {}", null != session);
		// 缓存Session
		cache(session, sessionId);
		return session;
	}

	@Override
	ExternalCacheMap buildCacheMap(byte[] key) {
		return new RedisMap(this.jedisTemplate, key);
	}
}
