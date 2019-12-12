<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 屏蔽tomcat 自带的 EL表达式 -->
<%@ page isELIgnored="false" %>
<html>
<body>
	<h2>Post包含上传文件提交：</h2>
	<form method="POST" name="article" action="${pageContext.request.contextPath }/update_with_post_file" enctype="multipart/form-data">
		<!-- <input type="hidden" name="_method" value="PUT" />  -->
		Id:<input name="id" id="id" value="${article.id}" /><br /> 
		Title:<input name="title" id="title" value="${article.title}" /><br /> 
		Content:<input name="content" id="content" value="${article.content}" /><br />
		yourfile: 
		<input type="file" name="files" /><br /> 
		<input type="file" name="files" /><br /> 
		<input type="file" name="files" /><br />
		yourfile2: 
		<input type="file" name="execelFile" /><br /> 
		<input type="submit" value="Submit" />
	</form>
	<h2>Put包含上传文件提交：</h2>
	<form method="POST" name="article" action="${pageContext.request.contextPath }/update_with_put_file/" enctype="multipart/form-data">
		<input type="hidden" name="_method" value="PUT" />
		Id:<input name="id" id="id" value="${article.id}" /><br /> 
		Title:<input name="title" id="title" value="${article.title}" /><br /> 
		Content:<input name="content" id="content" value="${article.content}" /><br />
		yourfile: 
		<input type="file" name="files" /><br /> 
		<input type="file" name="files" /><br /> 
		<input type="file" name="files" /><br />
		yourfile2: 
		<input type="file" name="execelFile" /><br /> 
		<input type="submit" value="Submit" />
	</form>
</body>
</html>