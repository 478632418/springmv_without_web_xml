package com.dx.test.config;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import com.dx.test.aspect.LoggerAspect;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
@EnableWebMvc
@ComponentScans(value = { @ComponentScan("com.dx.test.controller"), @ComponentScan("com.dx.test.aspect") })
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebMvcConfig implements WebMvcConfigurer {
	// https://blog.csdn.net/tianjun2012/article/details/47809739
	@Bean
	public LoggerAspect loggerAspect() {
		return new LoggerAspect();
	}

	// 日期输出格式化
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder().indentOutput(true)
				.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).modulesToInstall(new ParameterNamesModule());

		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
		converters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()));
	}

	// 解决静态资源加载问题
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(3000);
	}

	// 接收格式控制
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 下面三个bean 配置 Thymeleaf 模板 
	 * <p><blockquote><pre>
	 * {@code<!-- 使用thymeleaf解析 -->  
	 * <bean id="templateResolver" class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">  
	 *   <property name="prefix" value="/WEB-INF/templates/" />  
	 *   <property name="suffix" value=".html" />  
	 *   <property name="templateMode" value="HTML" />  
	 *   <property name="characterEncoding" value="UTF-8"/>  
	 *   <property name="cacheable" value="false" />  
	 * </bean>  
	 *     
	 * <bean id="templateEngine" class="org.thymeleaf.spring5.SpringTemplateEngine">  
	 *   <property name="templateResolver" ref="templateResolver" />  
	 * </bean>  
	 * 
	 * <bean class="org.thymeleaf.spring5.view.ThymeleafViewResolver">  
	 *   <property name="templateEngine" ref="templateEngine" />  
	 *   <!--解决中文乱码-->  
	 *   <property name="characterEncoding" value="UTF-8"/>  
	 * </bean>
	 * }
	 * </pre></blockquote></p>
	 * @return
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		//templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public TemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

	//@Bean
	public ViewResolver viewResolver(TemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine((ISpringTemplateEngine) templateEngine);
		viewResolver.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
		viewResolver.setViewNames(new String[] {"*.html"});
		viewResolver.setOrder(1);
		return viewResolver;
	}
	
	// 配置jsp 视图解析器
	//@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");// 设置预加载路径/目录
		//resolver.setSuffix(".jsp"); // 设置允许后缀 //返回时也自动匹配到该后缀
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setViewNames("*.jsp");
		resolver.setOrder(2);
		return resolver;
	}
	
	// 参考：https://blog.csdn.net/qq_19408473/article/details/71214972
	@Bean
	public ContentNegotiatingViewResolver viewResolvers(){
		ContentNegotiatingViewResolver contentNegotiatingViewResolver=new ContentNegotiatingViewResolver();
		
		List<ViewResolver> viewResolverList=new ArrayList<ViewResolver>();
		// <!--used thymeleaf  -->
		viewResolverList.add(viewResolver(templateEngine(templateResolver())));
		// <!-- used jsp -->
		viewResolverList.add(viewResolver());
		
		contentNegotiatingViewResolver.setViewResolvers(viewResolverList);
		
		return contentNegotiatingViewResolver;
	}
}