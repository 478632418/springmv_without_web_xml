<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
Admin
<!-- 
原文链接：https://blog.csdn.net/pyfysf/article/details/81952889
 -->
<shiro:hasRole name="admin">
    <button class="btn btn-primary" id="btn_add_emp">新增</button>
    <button class="btn btn-danger btn_all_del">删除</button>
</shiro:hasRole>
<a href="deleteArticle">测试注解</a>
<a href="queryArticle">测试注解</a>
</body>
</html>