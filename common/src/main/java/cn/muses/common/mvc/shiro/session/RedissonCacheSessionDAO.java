package cn.muses.common.mvc.shiro.session;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatch;
import org.redisson.api.RMap;
import org.redisson.api.RMapAsync;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jervis
 */
@SuppressWarnings("unchecked")
public class RedissonCacheSessionDAO extends AbstractMultiLevelCacheSessionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonCacheSessionDAO.class);

    private final RedissonClient redissonClient;

    private final String prefixString;

    public RedissonCacheSessionDAO(RedissonClient redissonClient, String prefix) {
        super(prefix);
        this.prefixString = new String(getPrefix());
        this.redissonClient = redissonClient;
    }

    @Override
    protected void doUpdate(Session session) {
        LOGGER.trace("尝试从Redis中更新/保存Session: {}", session.getId());
        String stringId = getStringSessionId(session);
        RBatch batch = redissonClient.createBatch(BatchOptions.defaults());
        RMapAsync<Object, Object> map = batch.getMap(prefixString + stringId);
        map.fastPutAsync(stringId, session);
        if (session.getTimeout() >= 0) {
            map.expireAsync(session.getTimeout(), TimeUnit.MILLISECONDS);
        }
        batch.execute();
    }

    @Override
    protected void doDelete(Session session) {
        LOGGER.trace("尝试从Redis中删除Session: {}", session.getId());
        String stringId = getStringSessionId(session);
        RMap<Object, Object> map = redissonClient.getMap(prefixString + stringId);
        map.fastRemove(stringId);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        LOGGER.trace("尝试从Redis中读取Session: {}", sessionId);
        Session session = null;
        String stringId = getString(sessionId);
        RMap<Object, Object> map = redissonClient.getMap(prefixString + stringId);
        Object o = map.get(stringId);
        if (null != o) {
            session = (Session) o;
            proxySessionAttributes(session);
            cache(session, sessionId);
        }
        LOGGER.trace("读取结束，是否取到？ {}", null != session);
        return session;
    }

    @Override
    ExternalCacheMap buildCacheMap(byte[] key) {
        return new RedissonCacheMap(this.redissonClient, key);
    }

    private String getStringSessionId(Session session) {
        if (null == session) {
            return "";
        }
        return getString(session.getId());

    }

    private String getString(Serializable sessionId) {
        if (null == sessionId) {
            return "";
        }
        if (sessionId instanceof String) {
            return (String) sessionId;
        }
        return new String(SerialUtils.serialize(sessionId));
    }

}
