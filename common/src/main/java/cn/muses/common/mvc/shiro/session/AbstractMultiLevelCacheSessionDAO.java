package cn.muses.common.mvc.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在原有基础上添加一层抽象，为了同时兼容新老版本实现
 * @author Jervis
 */
public abstract class AbstractMultiLevelCacheSessionDAO extends CachingSessionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMultiLevelCacheSessionDAO.class);

    protected byte[] prefix;

    private boolean updateOnCreate;

    public AbstractMultiLevelCacheSessionDAO(String prefix) {
        this.prefix = prefix.concat(":").getBytes();
    }

    /**
     * 只有在getCacheManager为Null 的情况下才允许设置
     *
     * @param cacheManager 唯一初始化用
     */
    @Override
    public void setCacheManager(CacheManager cacheManager) {
        if (null == getCacheManager()) {
            super.setCacheManager(cacheManager);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        proxySessionAttributes(session);
        LOGGER.trace("分配新Session的ID: {}", sessionId);
        if (isUpdateOnCreate() || getActiveSessionsCache() == null) {
            // 配置了创建时 update 或者无缓存，则立即执行一次更新
            doUpdate(session);
        }
        return sessionId;
    }

    /**
     * 根据 key 生成 CacheMap
     * @param key cache key
     * @return CacheMap，将被代理到 Session 中的 attributes
     */
    abstract ExternalCacheMap buildCacheMap(byte[] key);

    /**
     * 对原始Session 的属性进行代理，以支持按单个属性的粒度进行控制<br/>
     *
     * @param session 原始的Session
     */
    protected void proxySessionAttributes(Session session) {
        if (session instanceof SimpleSession) {
            SimpleSession simpleSession = ((SimpleSession) session);
            Map map = simpleSession.getAttributes();
            if (!(map instanceof ProxyMap)) {
                map = new ProxyMap(new HashMap());
                simpleSession.setAttributes(map);
            }
            ((ProxyMap) map).setCacheMap(buildCacheMap(SerialUtils.computeKey(session.getId(), getPrefix())));
        }
    }

    /**
     * 清除并同步本地缓存（更新）
     */
    public void clearAndSyncCache() {
        Cache<Serializable, Session> activeSessionsCache = getActiveSessionsCache();
        if (activeSessionsCache != null) {
            Collection<Session> values = activeSessionsCache.values();
            if (CollectionUtils.isNotEmpty(values)) {
                for (Session session : values) {
                    doUpdate(session);
                }
            }
            activeSessionsCache.clear();
        }
    }

    public byte[] getPrefix() {
        return prefix;
    }

    public boolean isUpdateOnCreate() {
        return updateOnCreate;
    }

    public void setUpdateOnCreate(boolean updateOnCreate) {
        this.updateOnCreate = updateOnCreate;
    }
}
