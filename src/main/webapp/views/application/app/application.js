"use strict"
define(
		[ 'angularAMD', 'balintimesConstant', 'ui-bootstrap',
				'angular-messages' ], function(angularAMD, balintimesConstant) {
			var app = angular.module("applicationModule", [ 'ui.router',
					'ui.bootstrap', 'ngMessages' ]);

			var mainState = {
				name : 'application/app',
				url : '/application/app',
				templateUrl : balintimesConstant.rootpath
						+ '/views/application/app/list.html',
				controller : 'ApplicationListController',
				resolve : {
					applicationTypeListData : function(AjaxRequest,
							$stateParams) {
						return AjaxRequest.Get("/applicationType/list");
					}
				}
			};

			var editState = {
				name : 'application/app/edit',
				url : '/application/app/edit/:uid',
				templateUrl : balintimesConstant.rootpath
						+ '/views/application/app/edit.html',
				controller : 'ApplicationEditController',
				resolve : {
					applicationTypeListData : function(AjaxRequest,
							$stateParams) {
						return AjaxRequest.Get("/applicationType/list");
					},

					applicationData : function(AjaxRequest, $stateParams) {
						var uid = "0";
						if ($stateParams.uid != undefined
								&& $stateParams.uid != "")
							uid = $stateParams.uid;
						return AjaxRequest.Get("/application/getApplication/"
								+ uid);
					}
				}
			};

			app.config([ '$stateProvider', '$urlRouterProvider',
					function($stateProvider, $urlRouterProvider) {
						$stateProvider.state(mainState).state(editState);
					} ]);

			app.controller('ApplicationListController', function($scope,
					$state, applicationTypeListData, AjaxRequest, DlgMsg,
					AlertMsg) {
				$scope.applications = {};
				$scope.types = applicationTypeListData.data;
				$scope.searchParams = null;

				$scope.loadData = function() {
					var url = "/application/list";
					if ($scope.searchParams == null) {
						$scope.searchParams = {
							name : "",
							typeUid : null,
							showInMenu : -1,
							forbidden : -1
						};
					}
					AjaxRequest.Post(url, $scope.searchParams).then(
							function(rs) {
								$scope.applications = rs.data;
							});
				};

				$scope.deleteFn = function(uid) {
					DlgMsg.confirm('系统提示', '是否删除当前项目？').result.then(function(
							btn) {
						AjaxRequest.Post("/application/delete", {
							uid : uid
						}).then(function(rsBody) {
							if (rsBody.success == 'true') {
								$scope.loadData();
							}
						})
					});
				};

				$scope.resetForm = function() {
					$scope.searchParams = {
						name : "",
						typeUid : null,
						showInMenu : -1,
						forbidden : -1
					};
					$scope.loadData();
				}

				$scope.loadData();

			});

			app.controller('ApplicationEditController', function($scope,
					$state, applicationTypeListData, applicationData,
					AjaxRequest, DlgMsg, AlertMsg) {

				$scope.application = applicationData.data;
				$scope.applicationTypeList = applicationTypeListData.data;
				var original = angular.copy(applicationData.data);

				$scope.saveApp = function() {
					var url = "/application/update"
					if (angular.isUndefined($scope.application.uid) == true
							|| $scope.application.uid == "0") {
						url = "/application/create"
					}

					AjaxRequest.Post(url, $scope.application).then(
							function(rsBody) {
								if (rsBody.success == 'true') {
									$state.go('application/app');
								}
							})
				};

				$scope.revert = function() {
					$scope.application = angular.copy(original);
					$scope.editForm.$setPristine();
				};

			});

			return {
				mainState : mainState,
				module : app
			};
		});