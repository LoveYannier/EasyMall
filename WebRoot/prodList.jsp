<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>商品列表</title>
    <link href="${app }/css/prodList.css" rel="stylesheet" type="text/css">
<style type='text/css'>
#fy_div{
	text-align: center;
}

#fy_div input{
	width:20px;
	border:solid 1px #CCCCCC;
}

#fy_div a{
	text-decoration: none;
	border: solid 1px #CCCCCC;
	padding: 5px;
	margin: 3px;
	color:#333
}

#fy_div a:hover{
	color:white;
	background-color: red;
}
</style>
<script type="text/javascript">
	function changePage(obj){
		var newTp = obj.value;
		var reg = /^[1-9][0-9]*$/;
		if(!reg.test(newTp)){
			alert("请输入正确的页面！");
			obj.value="${page.thispage}";
			return;
		}
		//数据正确
		//将隐藏域中的thispage的值改为newTp
		document.getElementById("thispage").value=newTp;
		//将searchForm表单提交
		document.getElementById("searchForm").submit();
	}
	function changePageA(tp){
		//将隐藏域中的thispage的值改为tp
		document.getElementById("thispage").value=tp;
		//将searchForm表单提交
		document.getElementById("searchForm").submit();
		
	}
	function changePageB(){
		//将隐藏域中的thispage的值改为tp
		document.getElementById("thispage").value=1;
		//将searchForm表单提交
		document.getElementById("searchForm").submit();
		
	}
</script>
  </head>
  <body>
 <%@include file="/_head.jsp" %>
 <div id="content">
		<div id="search_div">
			<form id="searchForm" method="post" action="${app }/servlet/ProdPageServlet">
			    <input type="hidden" id="thispage" name="thispage" value="${page.thispage }"/>
			    <input type="hidden" name="rowperpage" value="${page.rowperpage }"/>
				<span class="input_span">商品名：<input type="text" name="name" value="${name }"/></span>
				<span class="input_span">商品种类：<input type="text" name="category" value="${category }"/></span>
				<span class="input_span">商品价格区间：
				<input type="text" name="minprice" value="${minprice}"/> - 
				<input type="text" name="maxprice" value="${maxprice}"/></span>
				<input type="button" value="查询" onclick="changePageB()">
			</form>
		</div>
		<div id="prod_content">
		<c:forEach items="${page.list}" var="prod">
			<div id="prod_div">
				<img src="${app}/servlet/ProdImgServlet?imgurl=${prod.imgurl}"></img>
				<div id="prod_name_div">
					${prod.name}
				</div>
				<div id="prod_price_div">
					￥${prod.price }元
				</div>
				<div>
					<div id="gotocart_div">
						<a href="${app }/servlet/AddCartServlet?pid=${prod.id}&buyNum=1">加入购物车</a>
					</div>					
					<div id="say_div">
						库存：${prod.pnum}
					</div>					
				</div>
			</div>
		</c:forEach>
		</div>
		<div style="clear: both"></div>
	</div>  
		<div id="fy_div">
		共${page.countrow }条记录 共${page.countpage }页
		<a href="javascript:void(0)" onclick="changePageA(1)">首页</a>
		<a href="javascript:void(0)" onclick="changePageA(${page.prepage})">上一页</a>
		<%-- 分页逻辑开始 --%>
		<c:set var="begin" scope="page" value="0"/>
		<c:set var="end" scope="page" value="0"/>
		<c:if test="${page.countpage<=5 }">
			<c:set var="begin" scope="page" value="1"/>
		    <c:set var="end" scope="page" value="${page.countpage}"/>
		</c:if>
		<c:if test="${page.countpage>5}">
		   <c:choose>
		   	<c:when test="${page.thispage<=3}">
		   	  <c:set var="begin" scope="page" value="1"/>
		      <c:set var="end" scope="page" value="5"/>
		   	</c:when>
		    <c:when test="${page.thispage>=page.countpage-2}">
		     <c:set var="begin" scope="page" value="${page.countpage-4}"/>
		     <c:set var="end" scope="page" value="${page.countpage}"/>
		    </c:when>
		    <c:otherwise>
		      <c:set var="begin" scope="page" value="${page.thispage-2}"/>
		      <c:set var="end" scope="page" value="${page.thispage+2}"/>
		    </c:otherwise>
		   </c:choose>
		</c:if>
		<c:forEach begin="${begin }" end="${end }" step="1" var="i">
		    <c:if test="${page.thispage==i }">${i }</c:if>
		 	<c:if test="${page.thispage!=i }"><a href="javascript:void(0)" onclick="changePageA(${i})">${i }</a></c:if>
		 	&nbsp;
		</c:forEach>
		<%-- 分页逻辑结束 --%>
		<a href="javascript:void(0)" onclick="changePageA(${page.nextpage})">下一页 </a>
		<a href="javascript:void(0)" onclick="changePageA(${page.countpage})">尾页</a>
		跳转到<input type="text" value="${page.thispage}" onblur="changePage(this)"/>页
		</div>
 <%@include file="/_foot.jsp" %>
  </body>
</html>
