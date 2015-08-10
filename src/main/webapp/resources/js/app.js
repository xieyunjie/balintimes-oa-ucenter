"use strict"

define(['angularAMD', 'balintimesConstant', 'angular-ui-router', 'ui-router-extras', 'blockUI', 'inform', 'ui-bootstrap', 'angular-messages', 'appConfig', 'appFactory',
    'appDirective'], function (angularAMD, balintimesConstant) {

    var app =
        angular.module("mainModule", ['ct.ui.router.extras.future', 'ct.ui.router.extras.statevis', 'blockUI', 'inform', 'ui.bootstrap', 'ngMessages', 'appConfig',
            'appFactory', 'appDirective']);

    app.config(['$futureStateProvider', '$controllerProvider', function ($futureStateProvider, $controllerProvider) {

        var loadAndRegisterFutureStates = function ($http) {
            return $http.get(balintimesConstant.rootpath + '/home/usermenus').then(function (resp) {
                angular.forEach(balintimesConstant.views, function (view) {
                        $futureStateProvider.futureState({
                            stateName: view.stateName,
                            urlPrefix: view.urlPrefix,
                            type: 'ngload',
                            src: balintimesConstant.rootpath + view.url,
                            auth: true
                        });

                    }
                );

                //angular.forEach(resp.data, function (module) {
                angular.forEach(resp.data, function (m) {
                    $futureStateProvider.futureState({
                        stateName: m.state,
                        urlPrefix: '/' + m.state,
                        type: 'ngload',
                        src: balintimesConstant.rootpath + m.url
                    });
                });
                //});
            })
        };

        $futureStateProvider.stateFactory('ngload', ngloadStateFactory);
        $futureStateProvider.stateFactory('iframe', iframeStateFactory);
        $futureStateProvider.stateFactory('requireCtrl', requireCtrlStateFactory);
        $futureStateProvider.addResolve(loadAndRegisterFutureStates);

    }]);

    app.run(function ($rootScope, $state, $window, $timeout, $modal, $http, $q) {

        $rootScope.rootpath = balintimesConstant.rootpath;
        $rootScope.$state = $state;
        $rootScope.$window = $window;
        $rootScope.MenuTree = {};
        $rootScope.WebUser = {};
        $rootScope.permissions = {};

        $rootScope.$on("$stateChangeSuccess", function () {
            $timeout(function () {
                console.info("Local:" + $window.location.pathname + $window.location.hash);

            });
        });

        $rootScope.$on("$stateChangeStart", function () {
            $timeout(function () {
                console.info("To:" + $window.location.pathname + $window.location.hash);
            });
        });

        $rootScope.init = function () {
            var menuPromise = $http.get(balintimesConstant.rootpath + '/home/usermenutree'),
                userPromise = $http.get(balintimesConstant.rootpath + "/home/inituser"),
                permissionPromise = $http.get(balintimesConstant.rootpath + "/home/userpermissions");

            $q.all({menu: menuPromise, user: userPromise, permission: permissionPromise}).then(function (results) {
                $rootScope.rootpath = balintimesConstant.rootpath;
                $rootScope.MenuTree = results.menu.data;
                $rootScope.WebUser = results.user.data;
                $rootScope.permissions = results.permission.data;

                var uiUrl = $window.location.hash.replace("#", "").replace("/", "");
                require(["appadmin"], function ngloadCallback() {

                    if (uiUrl == "") {

                        $state.go("index");
                    }
                    else {
                        angular.element("li a[ui-sref='" + uiUrl + "']").parents("li[litype='module']").children('a').trigger('click')
                    }

                });
            });

        };
        $rootScope.init();
    });

    angularAMD.bootstrap(app);

    return app;

    function requireCtrlStateFactory($q, futureState) {
        var d = $q.defer();

        require(['lazyController'], function (lazyController) {
            var fullstate = {
                controller: lazyController,
                name: futureState.stateName,
                url: futureState.urlPrefix,
                templateUrl: futureState.templateUrl
            };
            d.resolve(fullstate);
        });
        return d.promise;
    }

    function iframeStateFactory($q, futureState) {
        var state = {
            name: futureState.stateName,
            template: "<iframe src='" + futureState.src + "'></iframe>",
            url: futureState.urlPrefix
        };
        return $q.when(state);
    }

    function ngloadStateFactory($q, futureState, AjaxRequest, $location) {
        var ngloadDeferred = $q.defer();

        /*
         此处可以做一个权限管理。但存在一些异常情况
         1、如果在地址栏直接转入无权限菜单，能直接跳转至401页面。
         2、如果在界面直接打开菜单(实质上是不可能的)，第一次点击时不会跳转，但第二次点击时会直接跳转错误，由于第一次点击时发现错误并注册了state(地址为"")，所以不会跳转，第二次检测到state，但地址是错的，所以会报错了。

         两者的打开方式不同，1是根据url匹配，2是根据name进行匹配的。

         AjaxRequest.Post("/user/getresourcepermission", {state: futureState.stateName}, false).then(function (res) {

         console.info(res);

         if (res.permission == false) {
         $location.path("error/401");

         ngloadDeferred.reject("unpermission");
         } else {
         require(["ngload!" + futureState.src, 'ngload', 'angularAMD'], function ngloadCallback(result, ngload, angularAMD) {
         angularAMD.processQueue();
         ngloadDeferred.resolve("permission");
         });
         }
         });*/
        require(["ngload!" + futureState.src, 'ngload', 'angularAMD'], function ngloadCallback(result, ngload, angularAMD) {
            angularAMD.processQueue();
            ngloadDeferred.resolve(undefined);
        });

        return ngloadDeferred.promise;
    }

});