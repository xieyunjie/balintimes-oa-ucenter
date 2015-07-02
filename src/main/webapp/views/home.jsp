<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<%@include file="/views/include/resources_header.jsp"%>
<title>百灵时代OA系统 -- 2015</title>
</head>
<body>
	<div inform class="inform-fixed"></div>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#example-navbar-collapse">
			<span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
		<a href="#" class="navbar-brand">Balintimes OA 2015</a>
	</div>

	<div class="collapse navbar-collapse" id="example-navbar-collapse">


		<ul class="nav navbar-nav">
			<!-- <li ng-class="{active: $state.includes('{{router.state}}')}" ng-repeat=" router in routers"> -->
			<li ng-class="{active: $window.location.hash.indexOf(module.state) > -1}"
				ng-repeat=" module in modules"><a href="#" ng-click="initMenu(module.uid)">{{module.name}}</a></li>
		</ul>

		<ul class="nav navbar-nav navbar-right">
			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
				role="button" aria-haspopup="true" aria-expanded="false"> <span
					class="glyphicon glyphicon-user" aria-hidden="true" /> {{WebUser.username}} <span
					class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="#"><span class="glyphicon glyphicon-cog" aria-hidden="true" /> 个人设置 </a></li>
					<li role="separator" class="divider"></li>
					<li><a href="{{root}}/logout"><span class="glyphicon glyphicon-off" aria-hidden="true" />
							登出</a></li>
				</ul></li>


		</ul>
	</div>
	</nav>


	<div block-ui="main">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-2 col-sm-3 sidebar">
					<ul class="nav nav-pills nav-stacked ">

						<li ng-class="{active: $state.includes('{{currentModule.state + '/' + menu.state}}')>-1}"
							ng-repeat="menu in menus"><a href="#"
							ui-sref="{{currentModule.state + '/' + menu.state}}">{{menu.name}}</a></li>
					</ul>
				</div>
				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<div class="content" ui-view></div>
				</div>
			</div>
		</div>
	</div>
	<footer class="footer">
	<div style="margin-top: 3px; text-align: center;">balintimes oa ucenter 2015</div>
	</footer>
</body>
</html>