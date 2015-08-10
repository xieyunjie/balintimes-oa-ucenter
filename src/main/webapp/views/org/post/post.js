"use strict"
define([ 'angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages', 'angular-treetable' ], function(angularAMD, balintimesConstant) {

	var app = angular.module("postModule", [ 'ui.router', 'ui.bootstrap', 'ngMessages', 'ngTreetable' ]);

	var mainState = {
		name : 'org/post',
		url : '/org/post',
		templateUrl : balintimesConstant.rootpath + '/views/org/post/list.html',
		controller : 'postListController'
	};

	var editState = {
		name : 'org/post/edit',
		url : '/org/post/edit/:uid/:parentuid/:parentname',
		templateUrl : balintimesConstant.rootpath + '/views/org/post/edit.html',
		controller : 'postEditController',
		resolve : {
			postData : function(AjaxRequest, $stateParams) {
				if ($stateParams.uid == "0") {
					return {
						data : {
							uid : "0",
							parentuid : $stateParams.parentuid,
							parentname : $stateParams.parentname
						}
					}
				} else {
					return AjaxRequest.Get("/post/getone/" + $stateParams.uid);
				}

			},
			orgData : function(AjaxRequest) {
				return AjaxRequest.Get("/organization/list");
			}
		}
	};

	app.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
		$stateProvider.state(mainState).state(editState);
	} ]);

	app.controller("postListController", function($scope, $state, AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams) {
		var treeData = [];
		$scope.search_postname = "";

		// var vm = $scope.vm = {};
		// vm.choices = [
		// {
		// code: 'post',
		// label: '职位'
		// },
		// {
		// code: 'org',
		// label: '机构'
		// }
		// ];

		 
		$scope.initpostTree = function(name) {
			var param = {};
			if (name != "" && name != null) {
				param = {
					postname : name
				}
			}
			return AjaxRequest.Get("/post/tree", param).then(function(rs) {
				treeData = rs.data;
				if (treeData.length > 0) {
					$scope.expanded_params.refresh().then(function() {
						$scope.expanded_params.expendNode("0");
					});
				} else {
					DlgMsg.alert("系统提示", "没有查找的职位信息！");
				}

			});
		};
		$scope.Deletepost = function(uid) {			
			 DlgMsg.confirm("系统提示", "注意！！是否确认删除此职位？此职位删除后，其下属职位也会一拼删除。").result.then(function(btn) {
				if (btn == "ok") {
					AjaxRequest.Post("/post/delete", {
						uid : uid
					}).then(function() {
						$scope.initpostTree();
					})
				}
			})

		}

		$scope.expanded_params = new ngTreetableParams({
			getNodes : function(parent) {
				return parent ? parent.children : treeData;
			},
			getTemplate : function(node) {
				return 'tree_node';
			}
		});

		$scope.initpostTree();		

	}).controller("postEditController", function($scope, $state, AjaxRequest, TreeSelectModal, DlgMsg, postData, orgData) {
		$scope.postStatus = [ {
			text : '启用',
			value : 1
		}, {
			text : '禁用',
			value : 0
		} ];
		$scope.orgData = orgData.data;					
		var original = angular.copy($scope.post);
		$scope.post = postData.data;
		$scope.postDropDown = false;
		$scope.treeData = [];
		$scope.organization=[];

		AjaxRequest.Get("/post/tree").then(function(res) {
			$scope.treeData = res.data;
		})
		
		AjaxRequest.Get("/organization/tree").then(function(res) {
			$scope.organization = res.data;
		})
		
		$scope.Savepost = function() {
			if ($scope.post.uid == "0") {
				AjaxRequest.Post("/post/create", $scope.post).then(function(res) {
					$state.go("org/post");
				})
			} else {
				AjaxRequest.Post("/post/update", $scope.post).then(function(res) {
					$state.go("org/post");
				})
			}
		};
		$scope.Revert = function() {
			$scope.post = angular.copy(original);
			$scope.editForm.$setPristine();
		};

		$scope.SelectTreepost = function(node) {
			if ($scope.post.uid != "0") {
				if ($scope.post.uid == node.uid) {
					DlgMsg.alert("系统提示", "父职位不能与当前职位一致，请重新选择。")
					return;
				}
			}
			$scope.post.parentuid = node.uid;
			$scope.post.parentname = node.name;
			$scope.postDropDown = false;
		}
		
		$scope.SelectTreeOrg = function (node) {				
			$scope.post.organizationuid = node.uid;
			$scope.post.organizationname = node.name;
            $scope.orgDropDown = false;
        };
	})

	return {
		mainState : mainState,
		module : app
	};
})