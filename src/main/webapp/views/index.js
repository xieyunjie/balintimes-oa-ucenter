/**
 * Created by AlexXie on 2015/7/9.
 */
"use strict"
define(['angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages'], function (angularAMD, balintimesConstant) {
    var app = angular.module("userModule", ['ui.router', 'ui.bootstrap', 'ngMessages']);

    var mainState = {
        name: 'index',
        url: '/index',
        templateUrl: balintimesConstant.rootpath + '/views/index.html',
        controller: 'IndexController',
    };
    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state(mainState);
    }]);

    app.controller("IndexController", function ($scope, $state) {
        $scope.indexTitle = "标题XXXX";

    })

    return {
        mainState: mainState,
        module: app
    };
})