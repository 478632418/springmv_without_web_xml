<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin page</title>
</head>
<body>
	<!-- 原文链接：http://shiro.apache.org/web.html#Web-JSP%2FGSPTagLibrary -->
	<h3>The guest tag</h3>
	<shiro:guest>
    Hi there!  Please <a href="login">Login</a> or <a href="signup">Signup</a> today!<br>
	</shiro:guest>
	
	<h3>The user tag</h3>
	<shiro:user>
    Welcome back John!  Not John? Click <a href="login">here<a>
				to login.<br>
	</shiro:user>
	
	<h3>The authenticated tag</h3>
	<shiro:authenticated>
		<a href="updateAccount">Update your contact information</a>.<br>
	</shiro:authenticated>
	
	<h3>The notAuthenticated tag</h3>
	<shiro:notAuthenticated>
    Please <a href="login.jsp">login</a> in order to update your credit card information.<br>
	</shiro:notAuthenticated>
	
	<h3>The principal tag</h3>
	Hello,<shiro:principal />, how are you today?<br> 
	This is (mostly) equivalent to the following:<br> 
	Hello,<%=org.apache.shiro.SecurityUtils.getSubject().getPrincipal().toString()%>,how are you today?<br>
	
	<h4>Typed principal</h4>
	User Name:<!-- shiro:principal type="java.lang.String" --><br> 
	This is (mostly) equivalent to the following:<br> 
	User Name:<!-- %= org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType(String.class).toString() %--><br>
	
	<h4>Principal property</h4>
	Hello,<shiro:principal property="username" />, how are you today?<br> 
	This is (mostly) equivalent to the following:<br> 
	Hello,<%=((com.dx.test.model.SysUser) org.apache.shiro.SecurityUtils.getSubject().getPrincipal()).getUsername()%>,how are you today?<br> 
	Or, combined with the type attribute:<br>
	Hello,<shiro:principal type="com.dx.test.model.SysUser" property="username" />, how are you today?<br> 
	this is largely equivalent to the following:<br> 
	Hello,<%=org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType(com.dx.test.model.SysUser.class).getUsername().toString()%>,how are you today?<br>
	
	<h3>The hasRole tag</h3>
	<shiro:hasRole name="admin">
		<button class="btn btn-primary" id="btn_add_emp">新增</button>
		<br>
		<button class="btn btn-danger btn_all_del">删除</button>
		<br>
	</shiro:hasRole>
	
	<h3>The lacksRole tag</h3>
	<shiro:lacksRole name="test">
    Sorry, you are not allowed to administer the system.<br>
	</shiro:lacksRole>
	
	<h3>The hasAnyRole tag</h3>
	<shiro:hasAnyRoles name="developer, project manager, admin">
    You are either a developer, project manager, or admin.<br>
	</shiro:hasAnyRoles>
	
	<h3>The hasPermission tag</h3>
	<shiro:hasPermission name="article:delete">
		<a href="deleteArticle.jsp">Delete a new article</a>
		<br>
	</shiro:hasPermission>
	
	<h3>The lacksPermission tag</h3>
	<shiro:lacksPermission name="artilce:view">
    Sorry, you are not allowed to view article.<br>
	</shiro:lacksPermission>

	<a href="deleteArticle">测试注解1</a>
	<a href="queryArticle">测试注解2</a>
</body>
</html>