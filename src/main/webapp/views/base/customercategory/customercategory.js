"use strict"
define(
		[ 'angularAMD', 'balintimesConstant', 'ui-bootstrap',
				'angular-messages' ],
		function(angularAMD, balintimesConstant) {
			var app = angular.module("customerCategoryModule", [ 'ui.router',
					'ui.bootstrap', 'ngMessages' ]);

			var mainState = {
				name : 'base/customercategory',
				url : '/base/customercategory',
				templateUrl : balintimesConstant.rootpath
						+ '/views/base/customercategory/list.html',
				controller : 'CustomerCategoryListController'
			};

			var editState = {
				name : 'base/customercategory/edit',
				url : '/base/customercategory/edit/:uid',
				templateUrl : balintimesConstant.rootpath
						+ '/views/base/customercategory/edit.html',
				controller : 'CustomerCategoryEditController',
				resolve : {
					customerCategoryData : function(AjaxRequest, $stateParams) {
						var uid = "0";
						if ($stateParams.uid != undefined
								&& $stateParams.uid != "")
							uid = $stateParams.uid;
						return AjaxRequest.Get("/customercategory/getcategory",
								({
									uid : uid
								}));
					}
				}
			};

			app.config([ '$stateProvider', '$urlRouterProvider',
					function($stateProvider, $urlRouterProvider) {
						$stateProvider.state(mainState).state(editState);
					} ]);

			app.controller('CustomerCategoryListController', function($scope,
					$state, AjaxRequest, DlgMsg, AlertMsg) {
				$scope.customercategoryList = {};
				$scope.name = "";

				$scope.loadData = function() {
					var url = "/customercategory/list";
					AjaxRequest.Get(url, {
						name : $scope.name
					}).then(function(rs) {
						$scope.customercategoryList = rs.data;
					});
				};

				$scope.deleteFn = function(uid) {
					DlgMsg.confirm('系统提示', '是否删除当前项目？').result.then(function(
							btn) {
						AjaxRequest.Post("/customercategory/delete", {
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
							'CustomerCategoryEditController',
							function($scope, $state, AjaxRequest, DlgMsg,
									AlertMsg, customerCategoryData) {
								$scope.customerCategory = customerCategoryData.data;
								var original = angular
										.copy(customerCategoryData.data);

								$scope.saveType = function() {
									var url = "/customercategory/update"
									if (angular
											.isUndefined($scope.customerCategory.uid) == true
											|| $scope.customerCategory.uid == "0") {
										url = "/customercategory/create"
									}

									AjaxRequest
											.Post(url, $scope.customerCategory)
											.then(
													function(rsBody) {
														if (rsBody.success == 'true') {
															$state
																	.go('base/customercategory');
														}
													});
								};

								$scope.revert = function() {
									$scope.customercategory = angular
											.copy(original);
									$scope.editForm.$setPristine();
								};
							});

			return {
				mainState : mainState,
				module : app
			};

		})