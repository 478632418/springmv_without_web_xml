package com.dx.test.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisSessionDao extends AbstractSessionDAO {
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
    private RedisTemplate redisTemplate;
    private String keyPrefix = "shiro_redis_session:";
    /**
     * 单位minutes
     */
    private Long sessionTimeout = 30L;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public void setSessionTimeout(Long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    private String getKey(String key) {
        return getKeyPrefix() + key;
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session != null && session.getId() != null) {
            String key = this.getKey(session.getId().toString());
            session.setTimeout(this.sessionTimeout * 60 * 1000);
            redisTemplate.opsForValue().set(key, session, this.sessionTimeout*60*1000, TimeUnit.SECONDS);
        } else {
            logger.error("session or session id is null");
        }
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        logger.debug("更新seesion,id=[{}]", session.getId() != null ? session.getId().toString() : "null");
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        logger.debug("删除seesion,id=[{}]", session.getId().toString());
        try {
            String key = getKey(session.getId().toString());
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.info("获取存活的session");

        Set<Session> sessions = new HashSet<>();
        Set<String> keys = redisTemplate.keys(getKey("*"));
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Session s = (Session) redisTemplate.opsForValue().get(key);
                sessions.add(s);
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        logger.debug("创建seesion,id=[{}]", session.getId() != null ? session.getId().toString() : "null");
        // 当有游客进入或者remeberme都会调用
        this.saveSession(session);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取seesion,id=[{}]", sessionId.toString());
        Session readSession = null;
        try {
            readSession = (Session) redisTemplate.opsForValue().get(getKey(sessionId.toString()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return readSession;
    }
}