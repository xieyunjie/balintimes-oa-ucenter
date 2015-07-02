/**
 * 
 */
"use strict"
define([ 'angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages' ], function(angularAMD, balintimesConstant) {
	var app = angular.module("userModule", [ 'ui.router', 'ui.bootstrap', 'ngMessages' ]);

	var mainState = {
		name : 'auth/user',
		url : '/auth/user',
		templateUrl : balintimesConstant.rootpath + '/views/auth/user/list.html',
		controller : 'UserListController',
		resolve : {
			userlist : function(AjaxRequest) {
				return AjaxRequest.Get("/user/list");
			}
		}
	};

	var editState = {
		name : 'auth/user/edit',
		url : '/auth/user/edit/:uid',
		templateUrl : balintimesConstant.rootpath + '/views/auth/user/edit.html',
		controller : 'UserEditController',
		resolve : {
			userData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/user/getuser/" + $stateParams.uid);
			}
		}
	};

	app.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

		$stateProvider.state(mainState).state(editState);
	} ]);

	app.controller("UserListController", function($scope, $state, userlist, AjaxRequest, balintimesConfirm, NgUtil) {
		$scope.users = userlist.data;
		$scope.pageParams = NgUtil.initPageParams();
		$scope.totalItems = 1;

		$scope.loadPage = function() {
			AjaxRequest.Post("/user/listbypage", $scope.pageParams).then(function(rsBody) {
				$scope.users = rsBody.data;
				$scope.pageParams.total = rsBody.total;
			})
		};

		$scope.reflashUserw = function() {
			$scope.pageParams.username = 'admin';
			$scope.pageParams.deptname = '11111';
			$scope.pageParams.page = 1;
			AjaxRequest.Post("/user/listbypage", $scope.pageParams).then(function(rsBody) {
				$scope.users = rsBody.data;
				$scope.pageParams.total = rsBody.total;
			})
		};
		$scope.reflashUser = function() {
			AjaxRequest.Get("/user/list").then(function(rsBody) {
				$scope.users = rsBody.data;
			})
		};

		$scope.deleteUser = function(UID) {
			balintimesConfirm.show('系统提示', '是否删除当前项目？').result.then(function(btn) {
				AjaxRequest.Get("/user/delete").then(function(rsBody) {
					if (rsBody.success == 'true') {
						$scope.reflashUser();
					}
				})
			});
		}

	}).controller("UserEditController", function($scope, $state, userData, AjaxRequest) {
		$scope.user = userData.data;
		$scope.rootpath = balintimesConstant.rootpath;
		$scope.resetPassword = function() {
			$scope.user.password = "123";
		};
		$scope.saveUser = function() {
			var url = "/user/update"
			if (angular.isUndefined($scope.user.uid) == true) {
				url = "/user/create"
			}

			AjaxRequest.Post(url, $scope.user).then(function(rsBody) {
				if (rsBody.success == 'true') {
					$state.go('auth/user');
				}
			})
		}
	})

	return {
		mainState : mainState,
		module : app
	};
})