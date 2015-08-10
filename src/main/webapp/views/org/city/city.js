/**
 *
 */
"use strict"
define([ 'angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages' ], function(angularAMD, balintimesConstant) {
	var app = angular.module("CityModule", [ 'ui.router', 'ui.bootstrap', 'ngMessages' ]);

	var mainState = {
		name : 'org/city',
		url : '/org/city',
		templateUrl : balintimesConstant.rootpath + '/views/org/city/list.html',
		controller : 'CityListController'
	};
	var editState = {
		name : 'org/city/edit',
		url : '/org/city/edit/:uid',
		templateUrl : balintimesConstant.rootpath + '/views/org/city/edit.html',
		controller : 'CityEditController',
		resolve : {
			CityData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/city/getCity/" + $stateParams.uid);
			}
		}
	};
	var addState = {
		name : 'org/city/add',
		url : '/org/city/add/:returnUrl',
		templateUrl : balintimesConstant.rootpath + '/views/org/city/edit.html',
		controller : 'CityEditController',
		resolve : {
			CityData : function($stateParams) {
				return {
					data : {
						uid : 0,
						name : ''
					},
					returnUrl : $stateParams.returnUrl
				}
			}
		}
	};

	app.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

		$stateProvider.state(mainState).state(editState).state(addState);
	} ]);

	app.factory("CityServices", function(AjaxRequest) {
		return {
			listCityByPage : function(params) {
				return AjaxRequest.Post("/city/list");
			},
			deleteCity : function(UID) {
				return AjaxRequest.Post("/city/delete", {
					UID : UID
				});
			},
			searchCityByPage : function(searchParams) {
				return AjaxRequest.Post("/city/listByPage", searchParams);
			}
		}
	});

	app.controller("CityListController", function($scope, $state, AjaxRequest, DlgMsg, NgUtil, CityServices) {
		$scope.Citys = {};
		$scope.CityTypes = [];
		$scope.searchParams = {};
		//$scope.totalItems = 1;
		$scope.rootpath = balintimesConstant.rootpath;

		$scope.resetForm = function() {
			$scope.searchParams = NgUtil.initPageParams();
			CityServices.listCityByPage($scope.searchParams).then(function(rsBody) {
				$scope.Citys = rsBody.data;
				$scope.searchParams.total = rsBody.total;
			});
		};
		$scope.init = function() {
			$scope.resetForm();
		};
		$scope.init();

		$scope.loadPage = function() {
			CityServices.listCityByPage($scope.searchParams).then(function(rsBody) {
				$scope.Citys = rsBody.data;
				$scope.searchParams.total = rsBody.total;
			})
		};

		$scope.searchCity = function() {
			CityServices.searchCityByPage($scope.searchParams).then(function(rsBody) {
				$scope.Citys = rsBody.data;
				$scope.searchParams.total = rsBody.total;
			})
		};

		$scope.deleteCity = function(UID) {
			DlgMsg.confirm('系统提示', '是否删除当前城市？').result.then(function(btn) {
				CityServices.deleteCity(UID).then(function(rsBody) {
					if (rsBody.success == 'true') {
						$scope.resetForm();
					}
				})
			});
		}

	}).controller("CityEditController", function($scope, $state, $location, CityData, AjaxRequest) {
		$scope.City = CityData.data;
		$scope.returnUrl = angular.isUndefined(CityData.returnUrl) ? 'org/city' : decodeURIComponent(CityData.returnUrl);
		var original = angular.copy(CityData.data);

		$scope.saveCity = function() {
			var url = "/city/update";
			if (angular.isUndefined($scope.City.uid) == true || $scope.City.uid == "0" || $scope.City.uid == null) {
				url = "/city/create"
			}

			AjaxRequest.Post(url, $scope.City).then(function(rsBody) {
				if (rsBody.success == 'true') {
					$location.path($scope.returnUrl);
				}
			})
		};
		$scope.revert = function() {
			$scope.City = angular.copy(original);
			$scope.editForm.$setPristine();
		};

		$scope.returnView = function() {
			$location.path($scope.returnUrl);
		};
	});

	return {
		mainState : mainState,
		module : app
	};
})