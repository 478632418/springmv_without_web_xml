package com.dx.test.listener;

import com.dx.test.shiro.ShiroService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 将系统中的的permission信息追加到 shiroFilter.filterChainDefinitions 下。
 * 注意：<br>
 * 在SpringMvc项目中，这个类的onApplicationEvent方法会被执行两次，因为项目中有两个ApplicationContext：<br>
 * 1）parent ApplicationContext:ContextLoaderListener初始化的；<br>
 * 2）child ApplicationContext:DispatcherServlet初始化的。<br>
 */
@Component("StartupListener")
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShiroService shiroService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 这里只想在 parent ApplicationContext 初始化完整时，执行相应业务，因为 applicationContext-shiro.xml 是在 ContextLoaderListner 下加载的。
        if (event.getApplicationContext().getParent() == null) {
            // 获取到上下文唯一 shiroFilter bean对象
            ShiroFilterFactoryBean shiroFilterFactoryBean = event.getApplicationContext().getBean(ShiroFilterFactoryBean.class);
            // 获取到 shiroFilter bean中配置的 filterChainDefinitions 信息，然后与 sys_permission 中的配置信息一起 merge。
            Map<String, String> filterChainDefinitionMap = shiroService.mergeFilterChainDefinitions(shiroFilterFactoryBean.getFilterChainDefinitionMap());
            // 重新设置 shiroFilter.filterChainDefinitions 属性。
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        }
    }
}
