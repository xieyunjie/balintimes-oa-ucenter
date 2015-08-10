"use strict"
define([ 'angularAMD', 'balintimesConstant', 'ui-bootstrap',
		'angular-messages', 'angular-treetable' ], function(angularAMD,
		balintimesConstant) {

	var app = angular.module("roleModule", [ 'ui.router', 'ui.bootstrap',
			'ngMessages', 'ngTreetable' ]);

	var mainState = {
		name : 'auth/role',
		url : '/auth/role',
		templateUrl : balintimesConstant.rootpath
				+ '/views/auth/role/list.html',
		controller : 'RoleListController'
	};

	var editState = {
		name : 'auth/role/edit',
		url : '/auth/role/edit/:uid/:parentuid',
		templateUrl : balintimesConstant.rootpath
				+ '/views/auth/role/edit.html',
		controller : 'RoleEditController',
		resolve : {
			roleData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/role/getRole", {
					uid : $stateParams.uid
				});
			},
			parentRoleData:function(AjaxRequest, $stateParams){
				if($stateParams.parentuid=="0")
					return null;
				return AjaxRequest.Get("/role/getRole", {
					uid : $stateParams.parentuid
				});
			}
		}
	};
	
	var roleSettingState={
		name : 'auth/role/setting',
		url : '/auth/role/setting/:uid',
		templateUrl : balintimesConstant.rootpath
				+ '/views/auth/role/setting.html',
		controller : 'RoleSettingController',
		resolve : {
			roleData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/role/getRole", {
					uid : $stateParams.uid
				});
			},
			
			applicationListData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/application/listByNotForbidden");
			}
		}
	};
	
	var appSettingState={
			name : 'auth/role/appsetting',
			url : '/auth/role/appsetting/:uid',
			templateUrl : balintimesConstant.rootpath
					+ '/views/auth/role/appsetting.html',
			controller : 'AppSettingController',
			resolve : {
				roleData : function(AjaxRequest, $stateParams) {
					return AjaxRequest.Get("/role/getRole", {
						uid : $stateParams.uid
					});
				},
				
				applicationListData : function(AjaxRequest, $stateParams) {
					return AjaxRequest.Get("/application/listByRole",{roleuid:$stateParams.uid});
				}
			}	
	};

	app.config([ '$stateProvider', '$urlRouterProvider',
			function($stateProvider, $urlRouterProvider) {
				$stateProvider.state(mainState).state(editState).state(roleSettingState).state(appSettingState);
			} ]);

	app.controller('RoleListController', function($scope, $state, $stateParams,
			AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams) {
		
		var treeData=[];
		$scope.expanded_params = new ngTreetableParams({
			getNodes : function(parent) {
				return parent ? parent.children : treeData;
			},
			getTemplate : function(node) {
				return 'tree_node';
			}
		});
		
		$scope.loadData=function(){
			return AjaxRequest.Get("/role/list").then(function(rs) {
				treeData = rs.data;
				if (treeData.length>0) {
					$scope.expanded_params.refresh().then(function() {
						$scope.expanded_params.expendNode("0");
					});
				} else {
					//DlgMsg.alert("系统提示", "没有查找的权限信息！");
				}

			});
		};
		
		$scope.deleteRole=function(uid){
			DlgMsg.confirm("系统提示", "注意！！是否确认删除此信息？此信息删除后，其下属信息也会一拼删除。").result.then(function (btn) {
                if (btn == "ok") {
                    AjaxRequest.Post("/role/delete", {uid: uid}).then(function () {
                        $scope.loadData();
                    })
                }
            });
		};
		
		$scope.loadData();
	});

	app.controller('RoleEditController', function($scope, $state, $stateParams,
			roleData, parentRoleData,AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams) {
		$scope.role=roleData.data;
		$scope.parentname="";
		var original = angular.copy($scope.role);
		$scope.treeData = [];
		$scope.orgDropDown = false;
		
		if(parentRoleData!=null){
			$scope.parentname=parentRoleData.data!=null?parentRoleData.data.name:"";
		}
		
		if($stateParams.uid=="0" && $stateParams.parentuid!="0"){
			$scope.role={
				uid:"0",
				name:"",
				forbidden:false,
				parentUid:$stateParams.parentuid,
				comment:""
			};
		}
		
		$scope.SelectTreeRole=function(node){
			if ($scope.role.uid != "0") {
                if ($scope.role.uid == node.uid) {
                    DlgMsg.alert("系统提示", "上级角色不能与当前角色一致，请重新选择。")
                    return;
                }
            }
            $scope.role.parentUid = node.uid;
            $scope.parentname = node.name;
            $scope.orgDropDown = false;
		};
		
		$scope.saveRole=function(){
			var url = "/role/update"
				if (angular.isUndefined($scope.role.uid) == true
						|| $scope.role.uid == "0") {
					url = "/role/create"
				}

				AjaxRequest.Post(url, $scope.role).then(
						function(rsBody) {
							if (rsBody.success == 'true') {
								$state.go('auth/role');
							}
						});
		};
		
		$scope.revert=function(){
			$scope.role = angular.copy(original);
			$scope.editForm.$setPristine();
		};
		
        AjaxRequest.Get("/role/list").then(function (res) {
            $scope.treeData = res.data;
        });
		
	});
	
	app.controller('RoleSettingController',function($scope, $state, $stateParams,
			roleData, applicationListData,AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams){
		
		$scope.role=roleData.data;
		$scope.applicationList=applicationListData.data;
		$scope.appUid="0";
		
		var treeData=[];
		$scope.expanded_params = new ngTreetableParams({
			getNodes : function(parent) {
				return parent ? parent.children : treeData;
			},
			getTemplate : function(node) {
				return 'tree_node';
			}
		});
		
		$scope.loadData = function() {

			var param = {
				roleuid:$scope.role.uid,
				appuid : $scope.appUid
			};
			
			return AjaxRequest.Get("/resource/listByRole",param).then(function(rs){
				treeData = rs.data;
				if (treeData.length>0) {
					$scope.expanded_params.refresh().then(function() {
						$scope.expanded_params.expendNode("0");
					});
				} else {
					DlgMsg.alert("系统提示", "没有查找的功能模块信息！");
				}
			});
		};
		
		$scope.saveCheckedValue=function(appuid,resourceuid,checked){
			var url = "/role/saveRoleResource"
			var params={
				roleUid:$scope.role.uid,
				appUid:appuid,
				resourceUid:resourceuid,
				checked:checked
			};
			AjaxRequest.Post(url, params).then(
				function(rsBody) {
					if (rsBody.success == 'true') {
							$scope.loadData();
						}
					});
		};
		
		$scope.cleanSetting=function(){
			DlgMsg.confirm("清空设置", "是否确认清空设置？", '').result.then(function (btn) {
				var url = "/role/cleanSetting"
					var params={
						roleUid:$scope.role.uid
					};
					AjaxRequest.Post(url, params).then(
						function(rsBody) {
							if (rsBody.success == 'true') {
									$scope.loadData();
								}
							});
            });
		}
		
		$scope.loadData();
	});
	
	app.controller('AppSettingController',function($scope, $state, $stateParams,
			roleData, applicationListData,AjaxRequest, DlgMsg, AlertMsg){
		
		$scope.role=roleData.data;
		$scope.applicationList=applicationListData.data;
		
		$scope.loadData=function(){
			var url = "/application/listByRole";
			AjaxRequest.Get(url,{roleuid:$scope.role.uid} ).then(
					function(rs) {
						$scope.applicationList = rs.data;
					});
		};
		
		$scope.saveCheckedValue=function(appuid,checked){
			var url = "/role/saveRoleApplication"
				var params={
					roleuid:$scope.role.uid,
					appuid:appuid,
					checked:checked
				};
				AjaxRequest.Post(url, params).then(
					function(rsBody) {
						if (rsBody.success == 'true') {
								$scope.loadData();
							}
						});
		};
	});

	return {
		mainState : mainState,
		module : app
	};
});