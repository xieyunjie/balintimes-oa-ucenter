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
			},
			userTypesData : function(AjaxRequest) {
				return AjaxRequest.Get("/usertype/list");
			}
		}
	};

	app.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

		$stateProvider.state(mainState).state(editState);
	} ]);

	app.controller("UserListController", function($scope, $state, userlist, AjaxRequest, $dialog, NgUtil) {
		$scope.users = userlist.data;
		$scope.pageParams = NgUtil.initPageParams();
		$scope.totalItems = 1;
		$scope.rootpath = balintimesConstant.rootpath;

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

		$scope.resetPassword = function(uid) {
			$dialog.confirm("密码重置", "是否确认重置密码？重置当前用户密码后为[1]。", '').result.then(function(btn) {
				AjaxRequest.Post("/user/resetpassword", {
					UID : uid
				}).then(function(rsBody) {
					if (rsBody.success == 'true') {
						$scope.reflashUser();
					}
				})
			});
		};

		$scope.deleteUser = function(UID) {
			$dialog.confirm('系统提示', '是否删除当前项目？').result.then(function(btn) {
				AjaxRequest.Post("/user/delete", {
					UID : UID
				}).then(function(rsBody) {
					if (rsBody.success == 'true') {
						$scope.reflashUser();
					}
				})
			});
		}

	}).controller("UserEditController", function($scope, $state, userData, userTypesData, AjaxRequest, AlertMsg) {
		$scope.user = userData.data;
		$scope.userTypes = userTypesData.data;
		$scope.password2 = "";
		$scope.rootpath = balintimesConstant.rootpath;
		$scope.resetPassword = function() {
			AlertMsg.exception("这是一个提示")
		};
		$scope.saveUser = function() {
			var url = "/user/update"
			if (angular.isUndefined($scope.user.uid) == true || $scope.user.uid == "0") {
				url = "/user/create"
			}

			AjaxRequest.Post(url, $scope.user).then(function(rsBody) {
				if (rsBody.success == 'true') {
					$state.go('auth/user');
				}
			})
		};
		$scope.GetNullUser = function() {
			AjaxRequest.Get("/user/getnulluser").then(function(rsBody) {
				console.info(rsBody);
			});
		};
		$scope.GetAdminUser = function() {
			AjaxRequest.Get("/admin/getadminuser").then(function(rsBody) {
				console.info(rsBody);
			});
		};
	})

	return {
		mainState : mainState,
		module : app
	};
})