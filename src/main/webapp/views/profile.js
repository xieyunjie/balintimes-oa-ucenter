/**
 * Created by AlexXie on 2015/7/9.
 */
"use strict"
define(['angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages'], function (angularAMD, balintimesConstant) {
    var app = angular.module("userModule", ['ui.router', 'ui.bootstrap', 'ngMessages']);

    var mainState = {
        name: 'profile',
        url: '/profile',
        templateUrl: balintimesConstant.rootpath + '/views/profile.html',
        controller: 'ProfileController',
    };
    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state(mainState);
    }]);

    app.controller("ProfileController", function ($rootScope, $scope, $state, AjaxRequest) {
        $scope.activeTabIndex = 1;
        $scope.activeTab = function (index) {
            $scope.activeTabIndex = index;
        };

        /*个人资料*/
        $scope.email = "";
        $scope.SaveUserInfo = function () {
            console.info($scope.userInfoForm);
            //AjaxRequest.Post("/user/updatepassword", {oldpassword: $scope.oldpassword, newpassword: $scope.newpassword});
        }

        /*个人密码修改功能*/
        $scope.userpassword = {
            oldpassword: "",
            newpassword: ""
        };
        $scope.confirmpassword = "";
        $scope.SavePassword = function () {
            $scope.passwordForm.$setPristine();
            return;
            AjaxRequest.Post("/user/updatepassword", $scope.userpassword).then(function (res) {
                $rootScope.init();
                $scope.confirmpassword = "";
                $scope.userpassword = {
                    oldpassword: "",
                    newpassword: ""
                };


            })
        };

    })

    return {
        mainState: mainState,
        module: app
    };
})