<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="itg" uri="http://www.tedu.cn/tag/itag" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/login.css"/>
		<title>EasyMall欢迎您登陆</title>
	</head>
	<body>
		<h1>欢迎登陆EasyMall</h1>
		<form action="${pageContext.request.contextPath }/LoginServlet" method="POST">
			<table>
				<tr>
					<td class="tdx" colspan="2" style="color:red; text-align:center" >
						${requestScope.msg}
					</td>
				</tr>
				<tr>
					<td class="tdx">用户名:</td>
					<td><input type="text" name="username" value='<itg:urldec name="username"/>'/></td>
				</tr>
				<tr>
					<td class="tdx">密码:</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="checkbox" name="remname" value="true" 
						<c:if test="${cookie.username!=null}">checked="checked"</c:if>
						/>记住用户名
						<input type="checkbox" name="autologin" value="true"/>30天内自动登陆
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="登陆"/>
					</td>
				</tr>
			</table>
		</form>		
	</body>
</html>
