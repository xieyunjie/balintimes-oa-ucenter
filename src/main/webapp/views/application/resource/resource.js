"use strict"
define([ 'angularAMD', 'balintimesConstant', 'ui-bootstrap',
		'angular-messages', 'angular-treetable' ], function(angularAMD,
		balintimesConstant) {

	var app = angular.module("resourceModule", [ 'ui.router', 'ui.bootstrap',
			'ngMessages', 'ngTreetable' ]);

	var mainState = {
		name : 'application/resource',
		url : '/application/resource/:appuid',
		templateUrl : balintimesConstant.rootpath
				+ '/views/application/resource/list.html',
		controller : 'ResourceListController',
		resolve : {
			applicationListData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/application/getAllList");
			}
		}
	};

	var editState = {
		name : 'application/resource/edit',
		url : '/application/resource/edit/:uid/:parentuid/:parentname/:treetype',
		templateUrl : balintimesConstant.rootpath
				+ '/views/application/resource/edit.html',
		controller : 'ResourceEditController',
		resolve : {
			applicationListData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/application/getAllList");
			},

			resourceData : function(AjaxRequest, $stateParams) {
				var uid = "0";
				if ($stateParams.uid != undefined && $stateParams.uid != "")
					uid = $stateParams.uid;
				
				if(uid=="0"){
					if($stateParams.treetype==-1){
						var rd={
							uid:"0",
							appUid:$stateParams.parentuid,
							parentUid:"00000000-0000-0000-0000-000000000000",
							name:"",
							priority:0,
							resourceType:1,
							showInMenu:true,
							forbidden:false
						};
						
						return rd;
						
					}else{
						return AjaxRequest.Get("/resource/getResource" ,{uid:$stateParams.parentuid});
					}
				}else{
					return AjaxRequest.Get("/resource/getResource" , {uid:$stateParams.uid});
				}
			},
			
			parentResourceData:function(AjaxRequest, $stateParams){
				return AjaxRequest.Get("/resource/getResource",{uid:$stateParams.parentuid});
			}
		}
	};

	app.config([ '$stateProvider', '$urlRouterProvider',
			function($stateProvider, $urlRouterProvider) {
				$stateProvider.state(mainState).state(editState);
			} ]);

	app.controller('ResourceListController', function($scope, $state,$stateParams,
			applicationListData, AjaxRequest, DlgMsg, AlertMsg,
			ngTreetableParams) {

		$scope.applicationList = applicationListData.data;
		$scope.appUid = null;
		var treeData = [];

		$scope.expanded_params = new ngTreetableParams({
			getNodes : function(parent) {
				return parent ? parent.children : treeData;
			},
			getTemplate : function(node) {
				return 'tree_node';
			},
            options: {
                initialState: 'expanded'
            }
		});

		$scope.loadData = function() {

			if ($scope.appUid == null || $scope.appUid == "") {
				DlgMsg.alert("警告", "请选择应用程序！");
				return;
			}

			var param = {
				appUid : $scope.appUid
			};

			return AjaxRequest.Get("/resource/list", param).then(function(rs) {
				treeData = rs.data;
				if (treeData.length>0) {
					$scope.expanded_params.refresh();
				} else {
					DlgMsg.alert("系统提示", "没有查找的功能模块信息！");
				}

			});
		};
		
		$scope.deleteResource=function(uid){
			DlgMsg.confirm("系统提示", "注意！！是否确认删除此信息？此信息删除后，其下属信息也会一拼删除。").result.then(function (btn) {
                if (btn == "ok") {
                    AjaxRequest.Post("/resource/delete", {uid: uid}).then(function () {
                        $scope.loadData();
                    })
                }
            });
		};
		
		if($stateParams.appuid!="0" && $stateParams.appuid!= ""){
			$scope.appUid=$stateParams.appuid;
			$scope.loadData();
		}
		
	});

	app.controller('ResourceEditController', function($scope, $state,
			applicationListData, resourceData,parentResourceData, AjaxRequest, $stateParams,DlgMsg, AlertMsg,
			TreeSelectModal) {
		$scope.resourceTypeList=[
		                         {
		                        	 id:1,
		                        	 name:'模块'
		                         },{
		                        	 id:2,
		                        	 name:'功能'
		                         }
		                         ];
		
		$scope.resource=null;
		$scope.parentname="";
		
		if($stateParams.treetype==-1){
			$scope.resource=resourceData;
			$scope.resource.resourceType=1;
		}
		else{
			if($stateParams.uid!="0"){
				$scope.resource=resourceData.data;
				$scope.parentname=parentResourceData.data!=null? parentResourceData.data.name:"";
			}else{
				var rd=resourceData.data;
				$scope.resource={
					uid:"0",
					appUid:rd.appUid,
					parentUid:rd.uid,
					appName:rd.appName,
					priority:0,
					name:"",
					showInMenu:true,
					forbidden:false
				};
				$scope.parentname=$stateParams.parentname;
			}
			
			if($stateParams.treetype==1){
				$scope.resource.resourceType=1;
			}else if($stateParams.treetype==2){
				$scope.resource.resourceType=2;
			}
		}
		
		
		$scope.applicationList=applicationListData.data;
		var original = angular.copy($scope.resource);
		var appUid=$scope.resource.appUid;
		$scope.treeData = [];
		$scope.orgDropDown = false;
		
		
		$scope.IsShow=function(){
			if($scope.resource.resourceType==1)
				return true;
			return false;
		};
		
		$scope.SelectTreeResource=function(node){
			if ($scope.resource.uid != "0") {
                if ($scope.resource.uid == node.uid) {
                    DlgMsg.alert("系统提示", "上级菜单不能与当前菜单一致，请重新选择。")
                    return;
                }
            }
            $scope.resource.parentUid = node.uid;
            $scope.parentname = node.name;
            $scope.orgDropDown = false;
		};
		
		$scope.saveResource=function(){
			var url = "/resource/update"
				if (angular.isUndefined($scope.resource.uid) == true
						|| $scope.resource.uid == "0") {
					url = "/resource/create"
				}

				AjaxRequest.Post(url, $scope.resource).then(
						function(rsBody) {
							if (rsBody.success == 'true') {
								$state.go('application/resource',{appuid:$scope.resource.appUid});
							}
						});
		};
		
		$scope.revert=function(){
			$scope.resource = angular.copy(original);
			$scope.editForm.$setPristine();
		};
		
        AjaxRequest.Get("/resource/listBytreebox",{appUid:appUid,istree:1}).then(function (res) {
            $scope.treeData = res.data;
        });
        
	});

	return {
		mainState : mainState,
		module : app
	};

});