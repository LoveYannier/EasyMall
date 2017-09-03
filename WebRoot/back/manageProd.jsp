<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<style type="text/css">
body {
	background: #F5F5F5;
	text-align: center;
}

table {
	text-align: center;
	margin: 0px auto;
}

th {
	background-color: silver;
}
</style>
<script type="text/JavaScript" src="${app}/js/ajax.js"></script>
<script type="text/javascript">
	function changePnum(id,obj,oldPnum){
		var newNum = obj.value;
		if(isNaN(newNum)){
			alert("您输入数量不合法，请输入数字！");
			obj.value = oldPnum;
		}else if(newNum<0){
			if(window.confirm("亲，您输入了小于零的数字，将执行删除操作，您确定删除吗？")){
				window.location.href = "${app}/servlet/BackProdDeleteServlet?id="+id;
			}else{
				obj.value = oldPnum;
			}
		}else if(oldPnum!=newNum){
			//1.第一步, 创建XMLHTTPRequest对象
			var xmlHttp = ajaxFunction();
			//2.第二步, 打开与服务器的连接
			xmlHttp.open("post", "${app}/servlet/AjaxChangePnumServlet", true);
			//3.第三步, 发送请求
			xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");//指定向服务器发送的是请求参数
			xmlHttp.send("id="+id+"&newNum="+newNum);
			//4.第四步, 注册监听
			xmlHttp.onreadystatechange = function(){
				if(xmlHttp.readyState == 4){
					if(xmlHttp.status == 200 || xmlHttp.status == 304){
						var data = xmlHttp.responseText;
						if(data == "true"){
							alert("修改成功！");
						}else{
							alert("修改失败！");
						}
					}
				}
			};
		}
	}
</script>
</head>
<body>
	<h1>商品管理</h1>
	<a href="${pageContext.request.contextPath}/back/manageAddProd.jsp">添加商品</a>
	<hr>
	<table bordercolor="black" border="1" width="95%" cellspacing="0px" cellpadding="5px">
		<tr>
			<th>商品图片</th>
			<!-- <th>商品id</th> -->
			<th>商品名称</th>
			<th>商品种类</th>
			<th>商品单价</th>
			<th>库存数量</th>
			<th>描述信息</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${list }" var="prod">
			<tr>
				<td><img width="120px" height="120px" src="${pageContext.request.contextPath}/servlet/ProdImgServlet?imgurl=${prod.imgurl}"/></td>
				<%-- <td>${prod.id }</td> --%>
				<td>${prod.name}</td>
				<td>${prod.category}</td>
				<td>${prod.price }</td>
				<td><input type="text" value="${prod.pnum }" style="width: 50px" onblur="changePnum('${prod.id}',this,${prod.pnum })"/></td>
				<td>${prod.description }</td>
				<td><a href="${app }/servlet/BackProdDeleteServlet?id=${prod.id}">删除</a>
				<a href="${app }/servlet/BackProdDeleteServlet?id=${prod.id}">修改</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
