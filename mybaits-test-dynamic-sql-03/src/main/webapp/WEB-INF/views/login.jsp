<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="login" method="POST">
		<!-- 用户名 -->
		<label for="username">用户：</label> <input id="username"
			name="username" type="text" /> <br>
		<!-- 用户密码 -->
		<label for="password">密码：</label> <input id="password"
			name="password" type="password" /> <br>
		<!-- 登录 -->
		<input type="submit" title="登录"/>
	</form>
</body>
</html>