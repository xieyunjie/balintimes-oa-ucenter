"use strict"
define(
		[ 'angularAMD', 'balintimesConstant', 'ui-bootstrap',
				'angular-messages' ],
		function(angularAMD, balintimesConstant) {

			var app = angular.module("applicationTypeModule", [ 'ui.router',
					'ui.bootstrap', 'ngMessages' ]);

			var mainState = {
				name : 'application/type',
				url : '/application/type',
				templateUrl : balintimesConstant.rootpath
						+ '/views/application/type/list.html',
				controller : 'ApplicationTypeListController'
			};

			var editState = {
				name : 'application/type/edit',
				url : '/application/type/edit/:uid',
				templateUrl : balintimesConstant.rootpath
						+ '/views/application/type/edit.html',
				controller : 'ApplicationTypeEditController',
				resolve : {
					applicationTypeData : function(AjaxRequest, $stateParams) {
						var uid = "0";
						if ($stateParams.uid != undefined
								&& $stateParams.uid != "")
							uid = $stateParams.uid;
						return AjaxRequest.Get("/applicationType/getType/"
								+ uid);
					}
				}
			};

			app.config([ '$stateProvider', '$urlRouterProvider',
					function($stateProvider, $urlRouterProvider) {
						$stateProvider.state(mainState).state(editState);
					} ]);

			app.controller('ApplicationTypeListController', function($scope,
					$state, AjaxRequest, DlgMsg, AlertMsg) {
				$scope.types = {};
				$scope.name = "";

				$scope.loadData = function() {
					var url = "/applicationType/list";
					AjaxRequest.Get(url, {
						name : $scope.name
					}).then(function(rs) {
						$scope.types = rs.data;
					});
				};

				$scope.createFn = function() {
					$state.go("application/type/edit");
				};

				$scope.updateFn = function(uid) {
					$state.go("application/type/edit", {
						uid : uid
					});
				};

				$scope.deleteFn = function(uid) {
					DlgMsg.confirm('系统提示', '是否删除当前项目？').result.then(function(
							btn) {
						AjaxRequest.Post("/applicationType/delete", {
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

			app.controller(
							'ApplicationTypeEditController',
							function($scope, $state, applicationTypeData,
									AjaxRequest, DlgMsg, AlertMsg) {
								$scope.applicationType = applicationTypeData.data;
								var original = angular
										.copy(applicationTypeData.data);

								$scope.saveType = function() {
									var url = "/applicationType/update"
									if (angular
											.isUndefined($scope.applicationType.uid) == true
											|| $scope.applicationType.uid == "0") {
										url = "/applicationType/create"
									}

									AjaxRequest
											.Post(url, $scope.applicationType)
											.then(
													function(rsBody) {
														if (rsBody.success == 'true') {
															$state
																	.go('application/type');
														}
													})
								};

								$scope.revert = function() {
									$scope.applicationType = angular
											.copy(original);
									$scope.editForm.$setPristine();
								};

							});

			return {
				mainState : mainState,
				module : app
			};

		});
