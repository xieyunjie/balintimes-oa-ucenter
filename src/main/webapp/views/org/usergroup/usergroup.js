"use strict"

define([ 'angularAMD', 'balintimesConstant', 'ui-bootstrap',
		'angular-messages', 'angular-treetable' ], function(angularAMD,
		balintimesConstant) {

	var app = angular.module("userGroupModule", [ 'ui.router', 'ui.bootstrap',
			'ngMessages', 'ngTreetable' ]);

	var mainState = {
		name : 'org/usergroup',
		url : '/org/usergroup',
		templateUrl : balintimesConstant.rootpath
				+ '/views/org/usergroup/list.html',
		controller : 'UserGroupListController'
	};

	var editState = {
		name : 'org/usergroup/edit',
		url : '/org/usergroup/edit/:uid',
		templateUrl : balintimesConstant.rootpath
				+ '/views/org/usergroup/edit.html',
		controller : 'UserGroupEditController',
		resolve : {
			userGroupData : function(AjaxRequest, $stateParams) {
				var uid = "0";
				if ($stateParams.uid != undefined && $stateParams.uid != "")
					uid = $stateParams.uid;
				return AjaxRequest.Get("/usergroup/getusergroup", {
					uid : uid
				});
			}
		}
	};

	var editDetailState = {
		name : 'org/usergroup/editdetail',
		url : '/org/usergroup/editdetail/:uid',
		templateUrl : balintimesConstant.rootpath
				+ '/views/org/usergroup/editdetail.html',
		controller : 'UserGroupDetailEditController',
		resolve : {
			userGroupData : function(AjaxRequest, $stateParams) {
				var uid = "0";
				if ($stateParams.uid != undefined && $stateParams.uid != "")
					uid = $stateParams.uid;
				return AjaxRequest.Get("/usergroup/getusergroup", {
					uid : uid
				});
			},
			roleTypeListData : function(AjaxRequest, $stateParams) {
				return AjaxRequest.Get("/usergroup/roletypelist")
			}
		}
	};

	var editByUserState = {
		name : 'org/usergroup/editbyuser',
		url : '/org/usergroup/editbyuser/:uid/:url',
		templateUrl : balintimesConstant.rootpath
				+ '/views/org/usergroup/editbyuser.html',
		controller : 'UserGroupEditByUserController',
		resolve : {
			userData : function(AjaxRequest, $stateParams) {
				var uid = "0";
				if ($stateParams.uid != undefined && $stateParams.uid != "")
					uid = $stateParams.uid;
				return AjaxRequest.Get("/user/getuser", {
					uid : uid
				});
			}
		}
	};

	app.config([
			'$stateProvider',
			'$urlRouterProvider',
			function($stateProvider, $urlRouterProvider) {
				$urlRouterProvider.when("/org/usergroup/:uid",
						"/org/usergroup/:uid");

				$stateProvider.state(mainState).state(editState).state(
						editDetailState).state(editByUserState);

			} ]);

	app.controller('UserGroupListController', function($scope, $state,
			AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams, $stateParams) {
		// $scope.userGroupList = {};

//		if ($stateParams.useruid != undefined && $stateParams.useruid != "") {
//			$state.go("org/usergroup/editbyuser", {
//				uid : $stateParams.useruid,
//				url : $stateParams.url
//			});
//			return;
//		}

		$scope.name = "";

		var treeData = [];

		$scope.expanded_params = new ngTreetableParams({
			getNodes : function(parent) {
				return parent ? parent.children : treeData;
			},
			getTemplate : function(node) {
				return 'tree_node';
			},
			options : {
				initialState : 'expanded'
			}
		});

		$scope.loadData = function() {
			var url = "/usergroup/list";
			AjaxRequest.Get(url, {
				name : $scope.name
			}).then(function(rs) {
				// $scope.userGroupList = rs.data;
				treeData = rs.data;
				if (treeData.length > 0) {
					$scope.expanded_params.refresh();
				}
			});
		};

		$scope.deleteFn = function(uid) {
			DlgMsg.confirm('系统提示', '是否删除当前项目？').result.then(function(btn) {
				AjaxRequest.Post("/usergroup/delete", {
					uid : uid
				}).then(function(rsBody) {
					if (rsBody.success == 'true') {
						$scope.loadData();
					}
				})
			});
		};

		$scope.deleteFnByAllUser = function(uid) {
			DlgMsg.confirm('系统提示', '是否删除当前项目？').result.then(function(btn) {
				AjaxRequest.Post("/usergroup/deletebygroup", {
					groupUid : uid
				}).then(function(rsBody) {
					if (rsBody.success == 'true') {
						$scope.loadData();
					}
				})
			});
		};

		$scope.deleteFnByUser = function(uid) {

			DlgMsg.confirm('系统提示', '是否删除当前项目？').result.then(function(btn) {
				AjaxRequest.Post("/usergroup/deletebyusergroupdetail", {
					userGroupDetailUid : uid
				}).then(function(rsBody) {
					if (rsBody.success == 'true') {
						$scope.loadData();
					}
				})
			});
		};

		$scope.go = function(uid) {

			$state.go("org/usergroup/editbyuser", {
				uid : uid
			});
		}

		$scope.loadData();
	});

	app.controller('UserGroupEditController', function($scope, $state,
			AjaxRequest, DlgMsg, AlertMsg, userGroupData) {
		$scope.userGroup = userGroupData.data;
		var original = angular.copy(userGroupData.data);

		$scope.save = function() {
			var url = "/usergroup/update"
			if (angular.isUndefined($scope.userGroup.uid) == true
					|| $scope.userGroup.uid == "0") {
				url = "/usergroup/create"
			}

			AjaxRequest.Post(url, $scope.userGroup).then(function(rsBody) {
				if (rsBody.success == 'true') {
					$state.go('org/usergroup');
				}
			})
		};

		$scope.revert = function() {
			$scope.userGroup = angular.copy(original);
			$scope.editForm.$setPristine();
		};
	});

	app.controller('UserGroupDetailEditController', function($scope, $state,
			AjaxRequest, DlgMsg, AlertMsg, userGroupData, roleTypeListData) {
		$scope.userGroup = userGroupData.data;
		$scope.roleTypeList = roleTypeListData.data;
		$scope.orgDropDown = false;
		$scope.empName = "";
		$scope.emps = {
			names : "",
			uids : new Array()
		};
		$scope.roleType = 0;
		$scope.users = [];

		$scope.getUser = function() {
			if ($scope.empName.length >= 2) {
				var url = "/user/getuserbyempname";
				AjaxRequest.Post(url, {
					empname : $scope.empName
				}).then(function(rs) {
					$scope.users = rs.data;
				});
			} else {
				$scope.users = [];
			}
		};

		$scope.saveChange = function(user) {
			if (checkAry(user.uid) == true) {
				$scope.emps.uids.push(user.uid);
				if ($scope.emps.names == "")
					$scope.emps.names = user.employeename;
				else
					$scope.emps.names += "," + user.employeename;
			} else {
				for (var i = 0; i < $scope.emps.uids.length; i++) {
					var u = $scope.emps.uids[i];
					if (u == user.uid) {
						$scope.emps.uids.splice(i, 1);
						break;
					}
				}

				$scope.emps.names = $scope.emps.names.replace(","
						+ user.employeename, "");
				$scope.emps.names = $scope.emps.names.replace(
						user.employeename, "");
			}
		};

		$scope.cleanChange = function() {
			$scope.emps = {
				names : "",
				uids : new Array()
			};

			$scope.users = [];
		};

		function checkAry(uid) {
			if ($scope.emps.uids.length == 0)
				return true;

			var b = true;
			for (var i = 0; i < $scope.emps.uids.length; i++) {
				var u = $scope.emps.uids[i];
				if (u == uid) {
					b = false;
					break;
				}
			}

			return b;
		}

		$scope.revert = function() {
			$scope.cleanChange();
			$scope.roleType = 0;
			$scope.empName = "";
		};

		$scope.save = function() {
			if ($scope.emps.uids.length <= 0) {
				AlertMsg.alertException("请选择员工");
			}

			var uids = "";
			for (var i = 0; i < $scope.emps.uids.length; i++) {
				var item = $scope.emps.uids[i];
				if (i == 0) {
					uids = item;
				} else {
					uids += "," + item;
				}
			}

			var params = {
				userUids : uids,
				userGroupUid : $scope.userGroup.uid,
				roleType : $scope.roleType
			};
			var url = "/usergroup/createusergroupdetail";
			AjaxRequest.Post(url, params).then(function(rsBody) {
				if (rsBody.success == 'true') {
					$state.go('org/usergroup');
				}
			})
		};

	});

	app.controller('UserGroupEditByUserController', function($scope, $state,
			$stateParams, AjaxRequest, DlgMsg, AlertMsg, userData) {
		$scope.user = userData.data;
		$scope.treeData = [];
		$scope.name = "";

		$scope.loadData = function() {
			var params = {
				name : $scope.name,
				useruid : $scope.user.uid
			};
			var url = "/usergroup/getusergrouproletype";
			AjaxRequest.Post(url, params).then(function(rs) {
				if (rs.success == 'true') {
					$scope.treeData = rs.data;
				}
			});
		};

		$scope.back = function() {
			var url = "";
			if ($stateParams.url == 0) {
				url = "auth/user";
			}
			if (url != "") {
				$state.go(url);
			}
		}

		$scope.save = function() {
			var ary = new Array();

			for (var i = 0; i < $scope.treeData.length; i++) {
				var g = $scope.treeData[i];
				var d = {
					groupuid : g.uid,
					roletypes : new Array()
				};

				for (var k = 0; k < g.children.length; k++) {
					var c = g.children[k];
					if (c.checked) {
						d.roletypes.push(c.id);
					}
				}

				ary.push(d);
				// if (d.roletypes.length != 0) {
				//					
				// }
			}

			// if (ary.length == 0) {
			// AlertMsg.warning("没有需要保存的数据");
			// return;
			// }

			var url = "/usergroup/saveusergroupdetail";
			var params = {
				useruid : $scope.user.uid,
				json : JSON.stringify(ary)
			};
			AjaxRequest.Post(url, params).then(function(rs) {
				if (rs.success == 'true') {
					$scope.loadData();
				}
			});
		};

		$scope.loadData();
	});

	return {
		mainState : mainState,
		module : app
	};
});