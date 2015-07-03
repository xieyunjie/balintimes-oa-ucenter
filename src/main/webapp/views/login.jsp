<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
<script src="${ctx}/resources/js/jquery.min.js"></script>
<script src="${ctx}/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/resources/css/login.css">
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />

<script type="text/javascript">
	/* 	$("#btnSubmit").on('click', function(e) {
	 var $btn = $(this).button('loading')
	 }); */
</script>
<title>登录</title>
</head>
<body>
	<div class="container">

		<div class="container-fluid">
			<div class="collapse navbar-collapse" id="example-navbar-collapse">
				<nav class="navbar navbar-inverse" role="navigation"> <a href="#" class="navbar-brand">Balintimes
					OA 2015</a>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><fmt:formatDate value="${now}" type="both" dateStyle="long"
								pattern="yyyy年MM月dd日" /></a></li>
				</ul>

				</nav>
			</div>
		</div>

		<div style="text-align: center;">
			<img alt="blank" src="${ctx}/resources/image/programmer.jpg" />
		</div>

		<c:if test="${fn:length(errormessage) > 0}">
			<div class="alert alert-danger col-sm-8 col-sm-offset-2 " role="alert">${errormessage}</div>
			<br />
			<br />
			<br />
		</c:if>
		<form class="form-horizontal" action="login/submit" method="post">
			<h3 class="form-signin-heading col-sm-offset-5">
				<s:message code="PleaseSignIn" />
			</h3>
			<div class="form-group">
				<label for="username" class="col-sm-2 col-sm-offset-2 control-label"><s:message
						code="UserName" /></label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="username">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 col-sm-offset-2 control-label"><s:message
						code="Password" /></label>
				<div class="col-sm-4">
					<input type="password" class="form-control" name="password">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-5">
					<input type="checkbox" value="true" name="rememberme"> 记住我？
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-4">
					<input id="btnSubmit" class="btn btn-lg btn-primary" style="width: 90%" type="submit"
						value="登录">
				</div>
			</div>
		</form>
</body>
</html>