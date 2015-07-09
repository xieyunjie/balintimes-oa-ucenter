/**
 * Created by AlexXie on 2015/7/27.
 */
"use strict"
define(['angularAMD', 'balintimesConstant', 'ui-bootstrap', 'angular-messages'], function (angularAMD, balintimesConstant) {
    var app = angular.module("userModule", ['ui.router', 'ui.bootstrap', 'ngMessages']);

    var State401 = {
        name: 'error/401',
        url: '/error/401',
        templateUrl: balintimesConstant.rootpath + '/views/error/401.html',
        controller: 'ErrorController'
    };
    var State403 = {
        name: 'error/403',
        url: '/error/403',
        templateUrl: balintimesConstant.rootpath + '/views/error/403.html',
        controller: 'ErrorController'
    };
    var State404 = {
        name: 'error/404',
        url: '/error/404',
        templateUrl: balintimesConstant.rootpath + '/views/error/404.html',
        controller: 'ErrorController'
    };


    app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state(State401).state(State403).state(State404);
    }]);

    app.controller("ErrorController", function () {

    });


    return {
        mainState: State401,
        module: app
    };
})