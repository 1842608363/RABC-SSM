<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keys" content="">
<meta name="author" content="">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/login.css">
<style>
</style>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" href="index.html" style="font-size: 32px;">尚筹网-创意产品众筹平台</a>
				</div>
			</div>
		</div>
	</nav>

	<div class="container">
		<h1 style="color: red;">${param.errorMsg}</h1>
		<form id="LoginForm" action="doLogin" method="post"
			class="form-signin" role="form">
			<h2 class="form-signin-heading">
				<i class="glyphicon glyphicon-user"></i> 用户登录
			</h2>
			<div class="form-group has-success has-feedback">
				<input type="text" class="form-control" name="loginacct"
					id="loginacct" placeholder="请输入登录账号" autofocus> <span
					class="glyphicon glyphicon-user form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="text" class="form-control" name="password"
					id="password" placeholder="请输入登录密码" style="margin-top: 10px;">
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<select class="form-control">
					<option value="member">会员</option>
					<option value="user">管理</option>
				</select>
			</div>
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me">
					记住我
				</label> <br> <label> 忘记密码 </label> <label style="float: right">
					<a href="reg.html">我要注册</a>
				</label>
			</div>
			<a class="btn btn-lg btn-success btn-block" onclick="dologin()">
				登录</a>
		</form>
	</div>
	<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script>
		function dologin() {
			//非空校验
			var username = $("#loginacct").val();
			//表单元素的value取值不会为null，取值为空字符串
			if (username == "") {
				//alert("用户名不可为空，请输入用户名");
				layer.msg("用户名不可为空，请输入用户名", {
					time : 3000,
					icon : 5,
					shift : 6
				}, function() {

				});
				return;
			}

			var password = $("#password").val();
			if (password == "") {
				//alert("密码不可为空，请输入密码");
				layer.msg("密码不可为空，请输入密码", {
					time : 3000,
					icon : 5,
					shift : 6
				}, function() {

				});
				return;
			}
			//提交表单
			//$("#LoginForm").submit();
			//使用AJAX提交表烦数据
			var loadingIndex = null;
			$.ajax({
				type : "POST",
				url : "doAJAXLogin",
				data : {
					"loginacct" : username,
					"password" : password
				},
				beforeSend : function() {
					loadingIndex = layer.msg('登录中', {
						icon : 16
					});
				},
				success : function(result) {
					layer.close(loadingIndex);
					if (result.success) {
						window.location.href = "main";
					} else {
						layer.msg("登录失败，用户或密码错误，请重新输入", {
							time : 3000,
							icon : 5,
							shift : 6
						}, function() {

						});
					}
				}
			});
		}
	</script>
</body>
</html>