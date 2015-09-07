/**
 * kang.wu 2015-8
 */
"use strict"
define([ 'angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages', 'angular-treetable' ], function(angularAMD, balintimesConstant) {

	var app = angular.module("userModule", [ 'ui.router', 'ui.bootstrap', 'ngMessages', 'ngTreetable' ]);

	var mainState = {
		name : 'org/user',
		url : '/org/user',
		templateUrl : balintimesConstant.rootpath + '/views/org/user/list.html',
		controller : 'userListController'
	};

	var editState = {
		name : 'org/user/edit',
		url : '/org/user/edit/:uid/:parentuid/:parentname/:json',
		templateUrl : balintimesConstant.rootpath + '/views/org/user/edit.html',
		controller : 'userEditController',
		resolve : {
			userData : function(AjaxRequest, $stateParams) {
				if ($stateParams.uid == "0") {
					return {
						data : {
							uid : 0,
							parentuid : $stateParams.parentuid,
							parentname : $stateParams.parentname
						}
					}
				} else {
					return AjaxRequest.Get("/user/getoneuser/" + $stateParams.uid);
				}

			},
			userParentData : function(AjaxRequest, $stateParams) {
				if ($stateParams.uid == "0") {
					return {
						data : {
							uid : 0,
							parentuid : $stateParams.parentuid,
							employeename : $stateParams.parentname
						}
					}
				} else {
					return AjaxRequest.Get("/user/getoneuserparent/" + $stateParams.parentuid);
				}
			}
		}
	};

	var editByPostState = {
		name : 'org/post/editbypost',
		url : '/org/post/editbypost/:postuid/:postname/:uid/:parentuid/:parentname/:json',
		templateUrl : balintimesConstant.rootpath + '/views/org/post/editbypost.html',
		controller : 'PostEditGroupController',
		resolve : {
			postGroupData : function(AjaxRequest, $stateParams) {

				var uid = "0";
				var name = "";
				if ($stateParams.uid != undefined && $stateParams.uid != "")
					uid = $stateParams.uid;
				if ($stateParams.name != undefined && $stateParams.name != "")
					name = $stateParams.name;
				return AjaxRequest.Post("/post/getpostgroup", {
					postuid : uid,
					postname : name
				});
			}
		}
	};

	var uploadByUserState = {
		name : 'org/user/uploadbyuser',
		url : '/org/user/uploadbyuser',
		templateUrl : balintimesConstant.rootpath + '/views/org/user/uploadbyuser.html',
		controller : 'userUploadController',
		resolve : {
			userData : function(AjaxRequest, $stateParams) {

			}
		}
	};

	app.config([ '$stateProvider', '$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
		$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
	    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
		$stateProvider.state(mainState).state(editState).state(editByPostState).state(uploadByUserState);
	} ]);

	app.factory("userTypeData", function(AjaxRequest) {
		return AjaxRequest.Get("/usertype/list");
	});

	app.factory("postData", function(AjaxRequest) {
		return AjaxRequest.Get("/post/tree");
	});

	app.factory("orgData", function(AjaxRequest) {
		return AjaxRequest.Get("/organization/tree");
	});

	app.controller("userListController", function($scope, $state, $location, AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams, userTypeData, postData, orgData) {

		var treeData = [];
		$scope.search_username = "";
		$scope.searchParams = {};

		var post2 = [];
		var org = [];
		postData.then(function(rs) {
			$scope.post2 = rs.data;
		});
		orgData.then(function(rs) {
			$scope.org = rs.data;
		});

		$scope.queryUserTree = function() {
			return AjaxRequest.Post("/user/querytree", $scope.searchParams).then(function(rs) {
				treeData = rs.data;
				$scope.expanded_params.refresh();
				// if (treeData !=null && treeData.length > 0) {
				// $scope.expanded_params.refresh();
				// }
			});
		}

		$scope.inituserTree = function(name) {
			userTypeData.then(function(res) {
				$scope.userTypes = res.data;
			})

			var param = {};
			if (name != "" && name != null) {
				param = {
					username : name

				}
			}
			return AjaxRequest.Get("/user/tree", param).then(function(rs) {
				treeData = rs.data;
				if (treeData.length > 0) {
					$scope.expanded_params.refresh();
				} else {
					DlgMsg.alert("系统提示", "没有查找的员工信息！");
				}

			});
		};
		$scope.Deleteuser = function(uid) {
			DlgMsg.confirm("系统提示", "注意！！是否确认删除此员工？此员工删除后，其下属员工也会一拼删除。").result.then(function(btn) {
				if (btn == "ok") {
					AjaxRequest.Post("/user/delete", {
						UID : uid
					}).then(function() {
						$scope.inituserTree();
					})
				}
			})
		}
		$scope.updateTreeData = function() {

		}
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

		$scope.SelectTreePost2 = function(node) {
			$scope.searchParams.postuid = node.uid;
			$scope.searchParams.postname = node.name;
			$scope.postDropDown = false;
		};

		$scope.SelectTreeOrg = function(node) {
			$scope.searchParams.orgnizationuid = node.uid;
			$scope.searchParams.orgnizationname = node.name;
			$scope.orgDropDown = false;
		}
		$scope.inituserTree();

	}).controller("userEditController", function($scope, $state, $location, AjaxRequest, TreeSelectModal, DlgMsg, userData, userParentData, userTypeData, $stateParams) {
		$scope.go = function(uid, name) {
			var userModel = {
				uid : $stateParams.uid,
				parentuid : $stateParams.parentuid,
				parentname : $stateParams.parentname,
				postuid : "",
				postname : "",
				employeename : $scope.user.employeename
			}
			if (userData.data != null && userData.data != undefined) {
				userModel = userData.data;
			}
			$state.go("org/post/editbypost", {
				postuid : uid,
				postname : name,
				uid : $stateParams.uid,
				parentuid : $stateParams.parentuid,
				parentname : $stateParams.parentname,
				json : JSON.stringify(userModel)
			});
		}

		$scope.userStatus = [ {
			text : '启用',
			value : 1
		}, {
			text : '禁用',
			value : 0
		} ];

		var original = angular.copy($scope.user);
		var originalPost = angular.copy($scope.post);
		var originalUserParent = angular.copy($scope.userParent);

		$scope.user = userData.data;
		if ($stateParams.json != undefined && $stateParams.json != null && $stateParams.json != "") {
			var t = JSON.parse($stateParams.json);
			$scope.user.postuid = t.postuid;
			$scope.user.postname = t.postname;
		}

		$scope.userDropDown = false;
		$scope.treeData = [];
		$scope.post = [];
		$scope.userParent = userParentData.data;

		AjaxRequest.Get("/user/tree").then(function(res) {
			$scope.treeData = res.data;
		})

		AjaxRequest.Get("/post/tree").then(function(res) {
			$scope.post = res.data;
		})

		userTypeData.then(function(res) {
			$scope.userTypes = res.data;
		})

		$scope.SaveUser = function() {
			if ($scope.user.uid == "0") {
				AjaxRequest.Post("/user/create", $scope.user).then(function(res) {
					if (res.data == null) {
						DlgMsg.alert("系统提示", res.responseMsg);
						$state.go("org/user");
					} else
						$state.go("org/user");
				})
			} else {
				AjaxRequest.Post("/user/update", $scope.user).then(function(res) {
					if (res.data == null) {
						DlgMsg.alert("系统提示", res.responseMsg);
						$state.go("org/user");
					} else
						$state.go("org/user");
				})
			}
		};
		$scope.Revert = function() {
			$scope.user = angular.copy(original);
			$scope.editForm.$setPristine();
		};

		$scope.SelectTreeuser = function(node) {
			if ($scope.userParent != null) {
				if ($scope.userParent.uid == node.uid) {
					DlgMsg.alert("系统提示", "上级员工不能与当前员工一致，请重新选择。")
					return;
				}
			}
			$scope.userParent.uid = node.uid;
			$scope.userParent.employeename = node.employeename;
			$scope.userDropDown = false;
		};

		$scope.SelectTreePost = function(node) {
			$scope.user.postuid = node.uid;
			$scope.user.postname = node.name;
			$scope.postDropDown = false;
		};
	})

	app.controller('PostEditGroupController', function($scope, $state, $stateParams, AjaxRequest, DlgMsg, AlertMsg, postGroupData) {
		$scope.postCheckTree = postGroupData.data;
		$scope.postTreeData = [];
		var postname = "";
		postname = $stateParams.postname;

		$scope.loadData = function() {
			var params = {};
			if (postname != null && postname != undefined) {
				params = {
					postname : postname,
					postuid : $scope.postCheckTree.uid
				};
			}

			var url = "/post/getpostgroup";
			AjaxRequest.Post(url, params).then(function(rs) {

				if (rs.success == 'true') {
					$scope.postTreeData = rs.data;
				}
			});
		};

		$scope.back = function() {
			var url = "";
			if ($stateParams.url == 0) {
				url = "org/user";
			}
			if (url != "") {
				$state.go(url);
			}
		}

		$scope.save = function() {
			var checkedChildren = new Array();
			for (var i = 0; i < $scope.postTreeData.length; i++) {
				var g = $scope.postTreeData[i];
				var d = {
					uid : "",
					name : ""
				};

				if (g.checked) {
					d.uid = g.uid;
					d.name = g.name;
					checkedChildren.push(d);
				}
				getCheckedChildren(g, checkedChildren);
			}

			var postname = "";
			var postuid = "";
			for (var j = 0; j < checkedChildren.length; j++) {
				postname = checkedChildren[j].name + "," + postname;
				postuid = checkedChildren[j].uid + "," + postuid;
			}

			var tempUser = JSON.parse($stateParams.json);
			if (tempUser == null || tempUser == undefined) {
				tempUser = {
					postname : "",
					postuid : "",
					uid : $stateParams.uid,
					parentuid : $stateParams.parentuid,
					parentname : $stateParams.parentname
				};
			}
			tempUser.postname = postname;
			tempUser.postuid = postuid;

			var userJson = JSON.stringify(tempUser);
			$state.go("org/user/edit", {
				uid : $stateParams.uid,
				parentuid : $stateParams.parentuid,
				parentname : $stateParams.parentname,
				json : userJson
			});

		};

		var getCheckedChildren = function(root, ary) {
			for (var k = 0; k < root.children.length; k++) {
				var c = root.children[k];
				var d = {
					uid : "",
					name : ""
				};
				if (c.checked) {
					d.uid = c.uid;
					d.name = c.name;
					ary.push(d);
				}
				getCheckedChildren(c, ary);
			}
		}

		$scope.loadData();
	});

	app.controller("userUploadController", function($scope, $state, $location, $stateParams, AjaxRequest, TreeSelectModal, DlgMsg, userData, $http, $q, $parse, $element) {

		var getModelAsFormData = function(data) {
			var dataAsFormData = new FormData();
			angular.forEach(data, function(value, key) {
				dataAsFormData.append(key, value);
			});
			return dataAsFormData;
		};

		$scope.saveTutorial = function(tutorial) {
			var data = tutorial;
			console.log(data);

			var deferred = $q.defer();
			$http({
				url :  balintimesConstant.rootpath +"/user/upload",
				method : "POST",
				data : data,				
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				}
			}).success(function(result) {
				deferred.resolve(result);
			}).error(function(result, status) {
				deferred.reject(status);
			});
			// return deferred.promise;

		};

	}).directive("akFileModel", [ "$parse", function($parse) {
		return {
			restrict : "A",
			link : function(scope, element, attrs) {
				var model = $parse(attrs.akFileModel);
				var modelSetter = model.assign;
				element.bind("change", function() {
					scope.$apply(function() {
						modelSetter(scope, element[0].files[0]);
					});
				});
			}
		};
	} ]);

	return {
		mainState : mainState,
		module : app
	};
})