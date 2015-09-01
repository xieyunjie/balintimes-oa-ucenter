"use strict"
define(
		[ 'angularAMD', 'balintimesConstant', 'ui-bootstrap',
				'angular-messages' ], function(angularAMD, balintimesConstant) {
			var app = angular.module("businessTypeModule", [ 'ui.router',
					'ui.bootstrap', 'ngMessages' ]);

			var mainState = {
				name : 'base/businesstype',
				url : '/base/businesstype',
				templateUrl : balintimesConstant.rootpath
						+ '/views/base/businesstype/list.html',
				controller : 'BusinessTypeListController'
			};

			var editState = {
				name : 'base/businesstype/edit',
				url : '/base/businesstype/edit/:uid',
				templateUrl : balintimesConstant.rootpath
						+ '/views/base/businesstype/edit.html',
				controller : 'BusinessTypeEditController',
				resolve : {
					businessTypeData : function(AjaxRequest, $stateParams) {
						var uid = "0";
						if ($stateParams.uid != undefined
								&& $stateParams.uid != "")
							uid = $stateParams.uid;
						return AjaxRequest.Get("/businesstype/gettype/" + uid);
					}
				}
			};

			app.config([ '$stateProvider', '$urlRouterProvider',
					function($stateProvider, $urlRouterProvider) {
						$stateProvider.state(mainState).state(editState);
					} ]);

			app.controller('BusinessTypeListController', function($scope,
					$state, AjaxRequest, DlgMsg, AlertMsg) {
				$scope.typeList = {};
				$scope.name = "";

				$scope.loadData = function() {
					var url = "/businesstype/list";
					AjaxRequest.Get(url, {
						name : $scope.name
					}).then(function(rs) {
						$scope.typeList = rs.data;
					});
				};

				$scope.deleteFn = function(uid) {
					DlgMsg.confirm('系统提示', '是否删除当前项目？').result.then(function(
							btn) {
						AjaxRequest.Post("/businesstype/delete", {
							uid : uid
						}).then(function(rsBody) {
							if (rsBody.success == 'true') {
								$scope.loadData();
							}
						})
					});
				};
				
				
				$scope.loadData();
			});

			app.controller('BusinessTypeEditController', function($scope,
					$state, AjaxRequest, DlgMsg, AlertMsg, businessTypeData) {
				$scope.businessType = businessTypeData.data;
				var original = angular.copy(businessTypeData.data);

				$scope.saveType = function() {
					var url = "/businesstype/update"
					if (angular.isUndefined($scope.businessType.uid) == true
							|| $scope.businessType.uid == "0") {
						url = "/businesstype/create"
					}

					AjaxRequest.Post(url, $scope.businessType).then(
							function(rsBody) {
								if (rsBody.success == 'true') {
									$state.go('base/businesstype');
								}
							});
				};

				$scope.revert = function() {
					$scope.businessType = angular.copy(original);
					$scope.editForm.$setPristine();
				};

			});

			return {
				mainState : mainState,
				module : app
			};
		});