<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>欢迎注册EasyMall</title>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/regist.css"/>
	<script type="text/JavaScript" src="${pageContext.request.contextPath }/js/ajax.js"></script>
	<script type="text/javascript">
		//点击图片换一张
		function changeImage(obj){
			obj.src = "${pageContext.request.contextPath }/ValiImageServlet?time="+ new Date().getTime();
		}
		
		
		//js的表单校验
		function checkForm(){
			var flag = true;
			
			flag = checkNull("username", "用户名不能为空") && flag;
			flag = checkNull("password", "密码不能为空") && flag;
			flag = checkNull("password2", "确认密码不能为空") && flag;
			flag = checkNull("nickname", "昵称不能为空") && flag;
			flag = checkNull("email", "邮箱不能为空") && flag;
			flag = checkNull("valistr", "验证码不能为空") && flag;
			flag = checkPassword("password", "两次密码不一致") && flag;
			flag = checkEmail("email", "邮箱格式不正确") && flag;
			
			
			return flag;
		}
		
		//邮箱格式的校验
		function checkEmail(name, msg){
			var email = document.getElementsByName(name)[0].value;
			setMsg(name, "");
			if(email == ""){
				setMsg(name, "邮箱不能为空");
				return false;
			}
			var reg = /^\w+@\w+(\.\w+)+$/;
			if(!reg.test(email)){
				setMsg(name, msg);
				return false;
			}
			return true;
		}
		
		//校验两次密码是否一致
		function checkPassword(name, msg){
			var psw1 = document.getElementsByName(name)[0].value;
			var psw2 = document.getElementsByName(name+"2")[0].value;
			
			setMsg(name+"2", "");
			if(psw2 == ""){
				setMsg(name+"2", "确认密码不能为空");
				return false;
			}
			
			if(psw1 != "" && psw2 != "" & psw1 != psw2){
				setMsg(name+"2", msg);
				return false;
			}
			return true;
		}
		
		//通用的方法: 判断输入框是否为空
		function checkNull(name, msg){
			var value = document.getElementsByName(name)[0].value;
			setMsg(name, "");
			if(value == ""){
				//alert("用户名不能为空");
				setMsg(name, msg);
				return false;
			}
			return true;
		}
			
		//通用的方法: 设置提示消息
		function setMsg(name,msg){
			document.getElementById(name+"_msg").innerHTML = msg;
		}
		
	</script>
</head>
<body>
	<h1>欢迎注册EasyMall</h1>
	<form action="${pageContext.request.contextPath }/RegistServlet" method="POST" onsubmit="return checkForm()">
		<table>
			<tr>
				<td class="tds" colspan="2" style="text-align:center; color:red">
					${requestScope.msg }
				</td>
			</tr>
			<tr>
				<td class="tds">用户名：</td>
				<td>
					<input type="text" name="username" onblur="checkUsername()" value="${param.username}">
					<span id="username_msg" style="font-size:13px; color:red"></span>	
				</td>
			
			</tr>
			<tr>
				<td class="tds">密码：</td>
				<td><input type="password" name="password" onblur="checkNull('password', '密码不能为空')" >
					<span id="password_msg" style="font-size:13px; color:red"></span>	
				</td>
			</tr>
			<tr>
				<td class="tds">确认密码：</td>
				<td><input type="password" name="password2"   onblur="checkPassword('password', '两次密码不一致')" >
					<span id="password2_msg" style="font-size:13px; color:red"></span>	
				</td>
			</tr>
			<tr>
				<td class="tds">昵称：</td>
				<td><input type="text" name="nickname" onblur="checkNull('nickname', '昵称不能为空')"   value="${param.nickname }">
					<span id="nickname_msg" style="font-size:13px; color:red"></span>	
				</td>
			</tr>
			<tr>
				<td class="tds">邮箱：</td>
				<td><input type="text" name="email"  onblur="checkEmail('email', '邮箱格式不正确')"  value="${param.email}">
					<span id="email_msg" style="font-size:13px; color:red"></span>	
				</td>
			</tr>
			<tr>
				<td class="tds">验证码：</td>
				<td>
					<input type="text" name="valistr" onblur="checkNull('valistr', '验证码不能为空')"  >
					<img id="yzm_img" src="${pageContext.request.contextPath }/ValiImageServlet" style="cursor: pointer" onclick="changeImage(this)"/>
					<span id="valistr_msg" style="font-size:13px; color:red"></span>	
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="注册用户"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
