/**
 *
 */
"use strict"
define(['angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages','angular-treetable'], function (angularAMD, balintimesConstant) {
    var app = angular.module("userModule", ['ui.router', 'ui.bootstrap', 'ngMessages','ngTreetable']);

    var mainState = {
        name: 'auth/user',
        url: '/auth/user',
        templateUrl: balintimesConstant.rootpath + '/views/auth/user/list.html',
        controller: 'UserListController'
    };
    var editState = {
        name: 'auth/user/edit',
        url: '/auth/user/edit/:uid',
        templateUrl: balintimesConstant.rootpath + '/views/auth/user/edit.html',
        controller: 'UserEditController',
        resolve: {
            userData: function (AjaxRequest, $stateParams) {
                return AjaxRequest.Get("/user/getuser", {uid: $stateParams.uid});
            }
        }
    };
    
    var settingState={
    		name: 'auth/user/setting',
            url: '/auth/user/setting/:uid',
            templateUrl: balintimesConstant.rootpath + '/views/auth/user/setting.html',
            controller: 'UserSettingController',
            resolve: {
                userData: function (AjaxRequest, $stateParams) {
                    return AjaxRequest.Get("/user/getuser", {uid: $stateParams.uid});
                }
            }	
    }

    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state(mainState).state(editState).state(settingState);
    }]);

    app.factory("userTypeDataPromise", function (AjaxRequest) {
        return AjaxRequest.Get("/usertype/list");
    });

    app.factory("userServices", function (AjaxRequest) {
        return {
            listUserByPage: function (params) {
                return AjaxRequest.Post("/user/listbypage", params);
            },
            deleteUser: function (UID) {
                return AjaxRequest.Post("/user/delete", {UID: UID});
            }
        }

    });

    app.controller("UserListController", function ($scope, $state, AjaxRequest, DlgMsg, NgUtil, userTypeDataPromise, userServices) {
        $scope.users = {};
        $scope.userTypes = [];
        $scope.searchParams = {};
        $scope.totalItems = 1;

        $scope.resetForm = function () {
            $scope.searchParams = NgUtil.initPageParams();
            userServices.listUserByPage($scope.searchParams).then(function (rsBody) {
                $scope.users = rsBody.data;
                $scope.searchParams.total = rsBody.total;
            });
        };
        $scope.init = function () {
            $scope.resetForm();
            userTypeDataPromise.then(function (res) {
                $scope.userTypes = res.data;
            })
        };
        $scope.init();
        $scope.loadPage = function () {
            userServices.listUserByPage($scope.searchParams).then(function (rsBody) {
                $scope.users = rsBody.data;
                $scope.searchParams.total = rsBody.total;
            })
        };

        $scope.searchUser = function () {
            $scope.searchParams.page = 1;
            userServices.listUserByPage($scope.searchParams).then(function (rsBody) {
                $scope.users = rsBody.data;
                $scope.searchParams.total = rsBody.total;
            })
        };
        $scope.resetPassword = function (uid) {
            DlgMsg.confirm("密码重置", "是否确认重置密码？重置当前用户密码后为[1]。", '').result.then(function (btn) {
                AjaxRequest.Post("/user/resetpassword", {
                    UID: uid
                }).then(function (rsBody) {
                    $scope.reflashUser();
                })
            });
        };

        $scope.deleteUser = function (UID) {
            DlgMsg.confirm('系统提示', '是否删除当前项目？').result.then(function (btn) {
                userServices.deleteUser(UID).then(function (rsBody) {
                    if (rsBody.success == 'true') {
                        $scope.reflashUser();
                    }
                })
            });
        };


    }).controller("UserEditController", function ($scope, $state, userData, userTypeDataPromise, AjaxRequest, TreeSelectModal) {
        $scope.user = userData.data;
        var original = angular.copy(userData.data);
        $scope.userTypes = {};
        $scope.password2 = "";
        userTypeDataPromise.then(function (res) {
            $scope.userTypes = res.data;
        });
        $scope.saveUser = function () {
            var url = "/user/update"
            if (angular.isUndefined($scope.user.uid) == true || $scope.user.uid == "0") {
                url = "/user/create"
            }

            AjaxRequest.Post(url, $scope.user).then(function (rsBody) {
                if (rsBody.success == 'true') {
                    $state.go('auth/user');
                }
            })
        };
        $scope.revert = function () {
            $scope.user = angular.copy(original);
            $scope.editForm.$setPristine();
        };
        $scope.ShowTreeModal = function () {
            TreeSelectModal.show().result.then(function (node) {
                console.info(node);
            });

        }
    });

    app.controller('UserSettingController',function($scope, $state, $stateParams,
    		userData,AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams){
    	
    	$scope.user=userData.data;
    	var treeData=[];
    	
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
		
		$scope.loadData=function(){
			return AjaxRequest.Get("/role/listByUser",{useruid:$scope.user.uid}).then(function(rs) {
				treeData = rs.data;
				if (treeData.length>0) {
					$scope.expanded_params.refresh();
				} else {
					//DlgMsg.alert("系统提示", "没有查找的权限信息！");
				}

			});
		};
		
		$scope.saveCheckedValue=function(uid,checked){
			var url = "/role/saveUserRole"
				var params={
					userUid:$scope.user.uid,
					roleUid:uid,
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
				var url = "/role/cleanUserSetting"
					var params={
						userUid:$scope.user.uid
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
    
    return {
        mainState: mainState,
        module: app
    };
})