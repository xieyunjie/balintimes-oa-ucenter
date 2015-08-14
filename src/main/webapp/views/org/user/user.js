/**
 * kang.wu 2015-8
 */
"use strict"
define(['angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages', 'angular-treetable'], function (angularAMD, balintimesConstant) {

    var app = angular.module("userModule", ['ui.router', 'ui.bootstrap', 'ngMessages', 'ngTreetable']);
   
    var mainState = {
        name: 'org/user',
        url: '/org/user',
        templateUrl: balintimesConstant.rootpath + '/views/org/user/list.html',
        controller: 'userListController'
    };

    var editState = {
        name: 'org/user/edit',
        url: '/org/user/edit/:uid/:parentuid/:parentname',
        templateUrl: balintimesConstant.rootpath + '/views/org/user/edit.html',
        controller: 'userEditController',
        resolve: {
            userData: function (AjaxRequest, $stateParams) {

                if ($stateParams.uid == "0") {
                    return {
                        data: {uid: 0, parentuid: $stateParams.parentuid, parentname: $stateParams.parentname}
                    }
                }
                else {
                    return AjaxRequest.Get("/user/getuser/" + $stateParams.uid);
                }

            },
            postData: function (AjaxRequest) {
                //return AjaxRequest.Get("/post/list");
            }
        }
    };

    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state(mainState).state(editState);
    }]);

    app.controller("userListController", function ($scope, $state, $location, AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams) {

        var treeData = [];
        $scope.search_username = "";

        $scope.inituserTree = function (name) {
            var param = {};
            if (name != "" && name != null) {
                param = {
                    username: name

                }
            }
            return AjaxRequest.Get("/user/tree", param).then(function (rs) {
                treeData = rs.data;
                if (treeData.length > 0) {
                    $scope.expanded_params.refresh();                    
                } else {
                    DlgMsg.alert("系统提示", "没有查找的员工信息！");
                }

            });
        };
        $scope.Deleteuser = function (uid) {
            DlgMsg.confirm("系统提示", "注意！！是否确认删除此员工？此员工删除后，其下属员工也会一拼删除。").result.then(function (btn) {
                if (btn == "ok") {
                    AjaxRequest.Post("/user/delete", {uid: uid}).then(function () {
                        $scope.inituserTree();
                    })
                }
            })
        }
        $scope.updateTreeData = function () {
            //$scope.expanded_params.expendNode("0");
            AlertMsg.info("这只是一个提示一个提示提示。。。")
        }
        $scope.expanded_params = new ngTreetableParams({
            getNodes: function (parent) {
                return parent ? parent.children : treeData;
            },
            getTemplate: function (node) {
                return 'tree_node';
            },
            options: {
                initialState: 'expanded'
            }
        });
        $scope.inituserTree();

    }).controller("userEditController", function ($scope, $state, $location, AjaxRequest, TreeSelectModal, DlgMsg, userData, postData) {

        $scope.userStatus = [{text: '启用', value: 1}, {text: '禁用', value: 0}];
//        $scope.cityData = CityData.data;
        var original = angular.copy($scope.user);
        $scope.user = userData.data;
        $scope.userDropDown = false;
        $scope.treeData = [];

        AjaxRequest.Get("/user/tree").then(function (res) {
            $scope.treeData = res.data;
        })

        $scope.Saveuser = function () {
            AjaxRequest.Post("/user/save", $scope.user).then(function (res) {
                $state.go("org/user");
            })
        };
        $scope.Revert = function () {
            $scope.user = angular.copy(original);
            $scope.editForm.$setPristine();
        };
//        $scope.SelectParentuser = function () {
//
//            var promise = AjaxRequest.Get("/user/tree");
//            TreeSelectModal.show("员工选择", promise).result.then(function (node) {
//
//                if ($scope.user.uid != "0") {
//                    if ($scope.user.uid == node.uid) {
//                        DlgMsg.alert("系统提示", "上级员工不能与当前员工一致，请重新选择。")
//                        return;
//                    }
//                }
//                $scope.user.parentuid = node.uid;
//                $scope.user.parentname = node.employeename;
//
//            })
//
//        };
        $scope.SelectTreeuser = function (node) {
            if ($scope.user.uid != "0") {
                if ($scope.user.uid == node.uid) {
                    DlgMsg.alert("系统提示", "上级员工不能与当前员工一致，请重新选择。")
                    return;
                }
            }
            $scope.user.parentuid = node.uid;
            $scope.user.parentname = node.employeename;
            $scope.userDropDown = false;
        };

//        $scope.AddCity = function () {
//            $location.path("org/post/add/" + encodeURIComponent($location.path().substr(1, $location.path().length - 1)));
//        };
    })

    return {
        mainState: mainState,
        module: app
    };
})