/**
 * 
 */

/**
 * 
 */
"use strict"
define([ 'angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages' ], function(angularAMD, balintimesConstant) {
	var app = angular.module("userModule", [ 'ui.router', 'ui.bootstrap', 'ngMessages' ]);

	var mainState = {
		name : 'org/dept',
		url : '/org/dept',
		templateUrl : balintimesConstant.rootpath + '/views/org/dept/list.html',
		controller : 'DeptListController',
		resolve : {
			userlist : function(AjaxRequest) {
				return AjaxRequest.Get("/user/list");
			}
		}
	};

	var editState = {
		name : 'org/dept/edit',
		url : 'org/dept/edit/:uid',
		templateUrl : balintimesConstant.rootpath + '/views/org/dept/edit.html',
		controller : 'DeptEditController',
		resolve : {
			userData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/user/getuser/" + $stateParams.uid);
			}
		}
	};

	app.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

		$stateProvider.state(mainState).state(editState);
	} ]);

	app.controller("DeptListController", function($scope, $state, userlist, AjaxRequest, DlgMsg) {

	}).controller("DeptEditController", function($scope, $state, userData, AjaxRequest) {

	})

	return {
		mainState : mainState,
		module : app
	};
})