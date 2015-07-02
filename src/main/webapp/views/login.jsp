<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<title>登录</title>
</head>
<body>
	<nav class="navbar navbar-inverse" role="navigation"> <a href="#" class="navbar-brand">Balintimes
		OA 2015</a> </nav>
	<div class="jumbotron">
		<c:if test="${fn:length(errormessage) > 0}">
			<div class="alert alert-success" role="alert">${errormessage}</div>
		</c:if>

		<form class="form-horizontal" action="login/submit" method="get">
			<div class="form-group">
				<label for="username" class="col-sm-2 control-label">用户名：</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="username">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">密码：</label>
				<div class="col-sm-5">
					<input type="password" class="form-control" name="password">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-5">
					<input class="btn btn-primary" type="submit" value="登录">
				</div>
			</div>
		</form>
	</div>

</body>
</html>