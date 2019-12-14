package com.dx.test.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

//public class WebAppInit implements WebApplicationInitializer {
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//
//		// 设置Spring的父类bean factory
//		AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext();
//		parent.register(BeanFactoryConfig.class);
//		parent.refresh();
//
//		// 注册mvc配置文件
//		ctx.register(WebMvcConfig.class);
//		ctx.setParent(parent);
//		ctx.setServletContext(servletContext);
//		ctx.refresh();
//
//		DispatcherServlet dispatcherServlet = new DispatcherServlet();
//		ServletRegistration.Dynamic servlet = servletContext.addServlet("mvc", dispatcherServlet);
//		servlet.setLoadOnStartup(1);
//		servlet.addMapping("/");
//		//servlet.setInitParameter("contextConfigLocation","");
///**
// * 
//严重: StandardWrapper.Throwable
//org.springframework.beans.factory.BeanDefinitionStoreException: IOException parsing XML document from ServletContext resource [/WEB-INF/mvc-servlet.xml]; nested exception is java.io.FileNotFoundException: Could not open ServletContext resource [/WEB-INF/mvc-servlet.xml]
//	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:345)
//	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:305)
//	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:188)
//	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:224)
//	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:195)
//	at org.springframework.web.context.support.XmlWebApplicationContext.loadBeanDefinitions(XmlWebApplicationContext.java:125)
//	at org.springframework.web.context.support.XmlWebApplicationContext.loadBeanDefinitions(XmlWebApplicationContext.java:94)
//	at org.springframework.context.support.AbstractRefreshableApplicationContext.refreshBeanFactory(AbstractRefreshableApplicationContext.java:133)
//	at org.springframework.context.support.AbstractApplicationContext.obtainFreshBeanFactory(AbstractApplicationContext.java:637)
//	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:522)
//	at org.springframework.web.servlet.FrameworkServlet.configureAndRefreshWebApplicationContext(FrameworkServlet.java:702)
//	at org.springframework.web.servlet.FrameworkServlet.createWebApplicationContext(FrameworkServlet.java:668)
//	at org.springframework.web.servlet.FrameworkServlet.createWebApplicationContext(FrameworkServlet.java:716)
//	at org.springframework.web.servlet.FrameworkServlet.initWebApplicationContext(FrameworkServlet.java:591)
//	at org.springframework.web.servlet.FrameworkServlet.initServletBean(FrameworkServlet.java:530)
//	at org.springframework.web.servlet.HttpServletBean.init(HttpServletBean.java:170)
//	at javax.servlet.GenericServlet.init(GenericServlet.java:158)
//	at org.apache.catalina.core.StandardWrapper.initServlet(StandardWrapper.java:1144)
//	at org.apache.catalina.core.StandardWrapper.load(StandardWrapper.java:988)
//	at org.apache.catalina.core.StandardContext.loadOnStartup(StandardContext.java:4885)
//	at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:5199)
//	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
//	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1412)
//	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1402)
//	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
//	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
//	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
//	at java.lang.Thread.run(Thread.java:748)
//Caused by: java.io.FileNotFoundException: Could not open ServletContext resource [/WEB-INF/mvc-servlet.xml]
//	at org.springframework.web.context.support.ServletContextResource.getInputStream(ServletContextResource.java:159)
//	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:331)
//	... 27 more
// * */
//		CharacterEncodingFilter encode = new CharacterEncodingFilter();
//		encode.setEncoding("utf-8");
//		FilterRegistration.Dynamic filter = servletContext.addFilter("encode", encode);
//		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST), false, "/*");
//
//	}
//}

public class WebAppInit extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { BeanFactoryConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

//	// 给DispatcherServlet添加filter
//	@Override
//	protected Filter[] getServletFilters() {
//		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
//		hiddenHttpMethodFilter.setMethodParam("_method");
//
//		MultipartFilter multipartFilter = new MultipartFilter();
//		multipartFilter.setMultipartResolverBeanName("multipartResolver");
//
//		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
//		encodingFilter.setEncoding("utf-8");
//		encodingFilter.setForceEncoding(true);
//
//		return new Filter[] { hiddenHttpMethodFilter, multipartFilter, encodingFilter };
//	}

	/**
	 * 设置Multipart具体细节（必须）  <br>
	 * 指定文件存放的临时路径 <br>
	 * 上传文件最大容量  <br>
	 * 整个请求的最大容量  <br>
	 * 0表示将所有上传的文件写入到磁盘中 <br>
	 */
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement("/Users/dz/temp", 20971520, 41943040, 0));
	}
	
	/**
	  * 配置其他的 servlet 和 filter
	  *
	  * @param servletContext
	  * @throws ServletException
	  */
	 @Override
	 public void onStartup(ServletContext servletContext) throws ServletException {
		  FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		  encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
		  encodingFilter.setInitParameter("forceEncoding", "true");
		  encodingFilter.addMappingForUrlPatterns(null, false, "/*");
		  
		  FilterRegistration.Dynamic httpMethodFilter = servletContext.addFilter("hiddenHttpMethodFilter", HiddenHttpMethodFilter.class);
		  httpMethodFilter.setInitParameter("method", "_method");
		  httpMethodFilter.addMappingForUrlPatterns(null, false, "/*");
		
		  FilterRegistration.Dynamic multipartFilter = servletContext.addFilter("multipartFilter", MultipartFilter.class);
		  multipartFilter.setInitParameter("multipartResolverBeanName", "multipartResolver");
		  multipartFilter.addMappingForUrlPatterns(null, false, "/*");

		  super.onStartup(servletContext);
	 }
}