package com.dx.test.shiro;

import com.alibaba.fastjson.JSON;
import com.dx.test.model.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 用来缓存已经通过认证的用户，使得用户与sessionId关联起。
 * */
public class KickoutSessionFilter extends AccessControlFilter {
    /**
     * 踢出之后跳转的url
     */
    private String kickoutUrl;
    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认5
     */
    private Integer maxSession = 5;
    /**
     * 设置sessionManager
     */
    private SessionManager sessionManager;
    /**
     * redis缓存cache对象别名
     */
    private String cacheName = "shiro_redis_kickout_cache";
    /**
     * 获取cacheManager下的Cache接口实现对象
     */
    private Cache<String, Deque<Serializable>> cache;

    /**
     * 构造函数
     *
     * @param sessionManager session管理器
     * @param cacheManager   cache管理器
     * @param cacheName      redis缓存cache对象别名
     * @param kickoutAfter   踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     * @param kickoutUrl     踢出之后跳转的url
     * @param maxSession     同一个帐号最大会话数 默认5
     */
    public KickoutSessionFilter(SessionManager sessionManager, String cacheName, CacheManager cacheManager, String kickoutUrl, Boolean kickoutAfter, Integer maxSession) {
        this.sessionManager = sessionManager;
        this.cacheName = cacheName;
        this.cache = cacheManager.getCache(this.cacheName);
        this.kickoutAfter = kickoutAfter;
        this.kickoutUrl = kickoutUrl;
        this.maxSession = maxSession;
    }

    /**
     * 是否有权限访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 没有权限访问时，才执行该方法。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        SysUser user = (SysUser) subject.getPrincipal();
        String username = user.getUsername();
        Serializable sessionId = session.getId();

        //读取缓存   没有就存入
        Deque<Serializable> deque = cache.get(username);

        //如果此用户没有session队列，也就是还没有登录过，缓存中没有
        //就new一个空队列，不然deque对象为空，会报空指针
        if (deque == null) {
            deque = new LinkedList<Serializable>();
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            //将sessionId存入队列
            deque.push(sessionId);
            //将用户的sessionId队列缓存
            cache.put(username, deque);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            //如果踢出后者
            if (kickoutAfter) {
                kickoutSessionId = deque.removeFirst();
                //踢出后再更新下缓存队列
                cache.put(username, deque);
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
                //踢出后再更新下缓存队列
                cache.put(username, deque);
            }

            try {
                //获取被踢出的sessionId的session对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if ((Boolean) session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout") == true) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);

            Map<String, String> resultMap = new HashMap<String, String>(2);
            //判断是不是Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                resultMap.put("state", "300");
                resultMap.put("message", "您已经在其他地方登录，请重新登录！");
                //输出json串
                out(response, resultMap);
            } else {
                //重定向
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;
    }

    private void out(ServletResponse hresponse, Map<String, String> resultMap)
            throws IOException {
        try {
            hresponse.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.println(JSON.toJSONString(resultMap));
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
        }
    }
}
