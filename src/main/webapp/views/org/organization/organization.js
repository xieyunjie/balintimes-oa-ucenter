/**
 * Created by AlexXie on 2015/7/13.
 */

"use strict"
define(['angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages', 'angular-treetable'], function (angularAMD, balintimesConstant) {

    var app = angular.module("organizationModule", ['ui.router', 'ui.bootstrap', 'ngMessages', 'ngTreetable']);

    var mainState = {
        name: 'org/organization',
        url: '/org/organization',
        templateUrl: balintimesConstant.rootpath + '/views/org/organization/list.html',
        controller: 'OrganizationListController'
    };

    var editState = {
        name: 'org/organization/edit',
        url: '/org/organization/edit/:uid/:parentuid/:parentname',
        templateUrl: balintimesConstant.rootpath + '/views/org/organization/edit.html',
        controller: 'OrganizationEditController',
        resolve: {
            OrgData: function (AjaxRequest, $stateParams) {

                if ($stateParams.uid == "0") {
                    return {
                        data: {uid: 0, parentuid: $stateParams.parentuid, parentname: $stateParams.parentname}
                    }
                }
                else {
                    return AjaxRequest.Get("/organization/getone/" + $stateParams.uid);
                }

            },
            CityData: function (AjaxRequest) {
                return AjaxRequest.Get("/city/list");
            }
        }
    };

    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state(mainState).state(editState);
    }]);

    app.controller("OrganizationListController", function ($scope, $state, $location, AjaxRequest, DlgMsg, AlertMsg, ngTreetableParams) {

        var treeData = [];
        $scope.search_orgname = "";

        $scope.initOrgTree = function (name) {
            var param = {};
            if (name != "" && name != null) {
                param = {
                    orgname: name

                }
            }
            return AjaxRequest.Get("/organization/tree", param).then(function (rs) {
                treeData = rs.data;
                if (treeData.length > 0) {
                    $scope.expanded_params.refresh();
                    //$scope.expanded_params.refresh().then(function () {
                    //    $scope.expanded_params.expendNode("0");
                    //    /* console.info("expendNode");*/
                    //});

                    //$scope.expanded_params.refresh({"key": "name", "value": name});
                } else {
                    DlgMsg.alert("系统提示", "没有查找的机构信息！");
                }

            });
        };
        $scope.DeleteOrg = function (uid) {
            DlgMsg.confirm("系统提示", "注意！！是否确认删除此机构？此机构删除后，其下属机构也会一拼删除。").result.then(function (btn) {
                if (btn == "ok") {
                    AjaxRequest.Post("/organization/delete", {uid: uid}).then(function () {
                        $scope.initOrgTree();
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
        $scope.initOrgTree();

    }).controller("OrganizationEditController", function ($scope, $state, $location, AjaxRequest, TreeSelectModal, DlgMsg, OrgData, CityData) {

        $scope.orgStatus = [{text: '启用', value: 1}, {text: '禁用', value: 0}];
        $scope.cityData = CityData.data;
        var original = angular.copy($scope.organization);
        $scope.organization = OrgData.data;
        $scope.orgDropDown = false;
        $scope.treeData = [];

        AjaxRequest.Get("/organization/tree").then(function (res) {
            $scope.treeData = res.data;
        })

        $scope.SaveOrg = function () {
            AjaxRequest.Post("/organization/save", $scope.organization).then(function (res) {
                $state.go("org/organization");
            })
        };
        $scope.Revert = function () {
            $scope.organization = angular.copy(original);
            $scope.editForm.$setPristine();
        };
        $scope.SelectParentOrg = function () {

            var promise = AjaxRequest.Get("/organization/tree");
            TreeSelectModal.show("机构选择", promise).result.then(function (node) {

                if ($scope.organization.uid != "0") {
                    if ($scope.organization.uid == node.uid) {
                        DlgMsg.alert("系统提示", "父机构不能与当前机构一致，请重新选择。")
                        return;
                    }
                }
                $scope.organization.parentuid = node.uid;
                $scope.organization.parentname = node.name;

            })

        };
        $scope.SelectTreeOrg = function (node) {
            if ($scope.organization.uid != "0") {
                if ($scope.organization.uid == node.uid) {
                    DlgMsg.alert("系统提示", "父机构不能与当前机构一致，请重新选择。")
                    return;
                }
            }
            $scope.organization.parentuid = node.uid;
            $scope.organization.parentname = node.name;
            $scope.orgDropDown = false;
        };

        $scope.AddCity = function () {
            $location.path("org/city/add/" + encodeURIComponent($location.path().substr(1, $location.path().length - 1)));
        };
    })

    return {
        mainState: mainState,
        module: app
    };
})
