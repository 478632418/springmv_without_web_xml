package com.dx.test.shiro;

import com.dx.test.dao.SysPermissionMapper;
import com.dx.test.model.SysPermission;
import com.dx.test.model.SysUser;
import com.dx.test.model.vo.UserOnlineVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.*;

@Service
public class ShiroService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private RedisSessionDao redisSessionDao;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private RedisCacheManager redisCacheManager;

    /**
     * 将applicationContext-shiro.xml中shiroFilter.filterChainDefinitions配置信息与sys_permission合并。
     */
    public Map<String, String> mergeFilterChainDefinitions(Map<String, String> oldFilterChainDefinitions) {
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/error/**", "anon");
        filterChainDefinitionMap.put("/kickout", "anon");
        /*filterChainDefinitionMap.put("/logout", "logout");*/
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/libs/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/verificationCode", "anon");
        List<SysPermission> permissionList = sysPermissionMapper.getAll();
        for (SysPermission permission : permissionList) {
            if (StringUtils.isNotBlank(permission.getPermissionUrl()) && StringUtils.isNotBlank(permission.getPermissionValue())) {
                String perm = "perms[" + permission.getPermissionValue() + "]";
                filterChainDefinitionMap.put(permission.getPermissionUrl(), perm + ",kickout");
            }
        }
        filterChainDefinitionMap.put("/**", "user,kickout");

        for (Map.Entry<String, String> entry : oldFilterChainDefinitions.entrySet()) {
            if (false == filterChainDefinitionMap.containsKey(entry.getKey())) {
                filterChainDefinitionMap.put(entry.getKey(), entry.getValue());
            }
        }

        return filterChainDefinitionMap;
    }

    /**
     * 从redis中获取到在线用户
     */
    public List<UserOnlineVo> getOnlineUserList() {
        Collection<Session> sessions = redisSessionDao.getActiveSessions();
        Iterator<Session> it = sessions.iterator();
        List<UserOnlineVo> userOnlineVoList = new ArrayList<UserOnlineVo>();
        // 遍历session
        while (it.hasNext()) {
            // 这是shiro已经存入session的
            // 现在直接取就是了
            Session session = it.next();
            //标记为已提出的不加入在线列表
            if (session.getAttribute("kickout") != null) {
                continue;
            }
            UserOnlineVo userOnlineVo = getOnlineUserinfoFromSession(session);
            if (userOnlineVo != null) {
                userOnlineVoList.add(userOnlineVo);
            }
        }

        return userOnlineVoList;
    }

    /**
     * 剔出在线用户
     */
    public void kickout(Serializable sessionId, String username) {
        getSessionBysessionId(sessionId).setAttribute("kickout", true);
        //读取缓存,找到并从队列中移除
        Cache<String, Deque<Serializable>> cache = redisCacheManager.getCache(redisCacheManager.getKeyPrefix() + username);
        Deque<Serializable> deques = cache.get(username);
        for (Serializable deque : deques) {
            if (sessionId.equals(deque)) {
                deques.remove(deque);
                break;
            }
        }
        cache.put(username, deques);
    }

    private Session getSessionBysessionId(Serializable sessionId) {
        Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(sessionId));
        return kickoutSession;
    }

    private UserOnlineVo getOnlineUserinfoFromSession(Session session) {
        //获取session登录信息。
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (null == obj) {
            return null;
        }
        //确保是 SimplePrincipalCollection对象。
        if (obj instanceof SimplePrincipalCollection) {
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            obj = spc.getPrimaryPrincipal();
            if (null != obj && obj instanceof SysUser) {
                SysUser user = (SysUser) obj;
                //存储session + user 综合信息
                UserOnlineVo userOnlineVo = new UserOnlineVo();
                //最后一次和系统交互的时间
                userOnlineVo.setLastAccess(session.getLastAccessTime());
                //主机的ip地址
                userOnlineVo.setHost(session.getHost());
                //session ID
                userOnlineVo.setSessionId(session.getId().toString());
                //最后登录时间
                userOnlineVo.setLastLoginTime(session.getStartTimestamp());
                //回话到期 ttl(ms)
                userOnlineVo.setTimeout(session.getTimeout());
                //session创建时间
                userOnlineVo.setStartTime(session.getStartTimestamp());
                //是否踢出
                userOnlineVo.setSessionStatus(false);
                /*用户名*/
                userOnlineVo.setUsername(user.getUsername());
                return userOnlineVo;
            }
        }
        return null;
    }

    /**
     * 重置 filterChainDefinitions
     */
    public void reloadPermission(ServletContext servletContext) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(ShiroFilterFactoryBean.class);
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                        .getObject();
            } catch (Exception e) {
                throw new RuntimeException(
                        "get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            Map<String, String> oldFilterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            Map<String, String> newFilterChainDefinitionMap = mergeFilterChainDefinitions(oldFilterChainDefinitionMap);

            // 清空老的权限控制
            defaultFilterChainManager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

            shiroFilterFactoryBean.setFilterChainDefinitionMap(newFilterChainDefinitionMap);
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                defaultFilterChainManager.createChain(url, chainDefinition);
            }
        }
    }
}
