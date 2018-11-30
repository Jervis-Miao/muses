package com.muses.common.mvc.shiro.session;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Jervis
 */
@SuppressWarnings("unchecked")
public class SpringRedisCacheSessionDAO extends AbstractMultiLevelCacheSessionDAO {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(SpringRedisCacheSessionDAO.class);

	private final RedisTemplate	redisTemplate;

	public SpringRedisCacheSessionDAO(RedisTemplate redisTemplate, String prefix) {
		super(prefix);
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected void doUpdate(Session session) {
		LOGGER.trace("尝试从Redis中更新/保存Session: {}", session.getId());
		final byte[] k = SerialUtils.computeKey(session.getId(), getPrefix());
		final byte[] f = SerialUtils.serialize(session.getId());
		final byte[] v = SerialUtils.serialize(session);
		final int expiration = (int) (session.getTimeout() / 1000);
		redisTemplate.execute((RedisCallback<Object>) connection -> {
			connection.hSet(k, f, v);
			if (expiration >= 0) {
				connection.expire(k, expiration);
			}
			return null;
		}, redisTemplate.isExposeConnection(), true);
	}

	@Override
	protected void doDelete(Session session) {
		LOGGER.trace("尝试从Redis中删除Session: {}", session.getId());
		final byte[] k = SerialUtils.computeKey(session.getId(), getPrefix());
		redisTemplate.execute((RedisCallback) connection -> connection.del(k));
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		LOGGER.trace("尝试从Redis中读取Session: {}", sessionId);
		Session session = null;
		final byte[] k = SerialUtils.computeKey(sessionId, getPrefix());
		final byte[] f = SerialUtils.serialize(sessionId);
		byte[] bytes = (byte[]) redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.hGet(k, f));
		if (null != bytes) {
			session = (Session) SerialUtils.deserialize(bytes);
			proxySessionAttributes(session);
			cache(session, sessionId);
		}
		LOGGER.trace("读取结束，是否取到？ {}", null != session);
		return session;
	}

	@Override
	ExternalCacheMap buildCacheMap(byte[] key) {
		return new SpringRedisMap(this.redisTemplate, key);
	}

}
