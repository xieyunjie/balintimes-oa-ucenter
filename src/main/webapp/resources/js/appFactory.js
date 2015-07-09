/**
 *
 */
'use strict';
define(['angular', 'balintimesConstant', 'ui-bootstrap', 'angular-tree-control'], function (angular, balintimesConstant) {

    var appFactory = angular.module('appFactory', ['ui.bootstrap', 'treeControl']);

    appFactory.factory('securityInterceptor', ['$q', '$rootScope', '$location', 'AlertMsg', function ($q, $rootScope, $location, AlertMsg) {
        /*错误有两种 1、filter出错。shiro会抛401异常，所以程序重写方法，以区分普通请求还是ajax请求，普通请求会跳转页面，而ajax则会返回“正常”的json数据，不会报401错误，由界面进行处理。
         2、permission role 出错。错误由UnPermissionExceptionHandler进行捕捉，然后报401错误，但不会提示跳转页面，只会直接返回错误数据。*/
        var responseInterceptor = {
            response: function (response) {
                if (response.data.httpStatus == 401) {
                    AlertMsg.exception(response.data.responseMsg);
                    return $q.reject(response);
                }
                return response;
            },
            'responseError': function (response) {
                if (response.status == 403) {
                    window.location = balintimesConstant.rootpath + "/login";

                }
                else if (response.status == 401) {
                    /*此处可以跳转或者提示*/
                    //window.location = balintimesConstant.rootpath + "/login";
                    AlertMsg.exception(response.data.responseMsg);
                    //$location.path("error/401")
                }
                ;
                return $q.reject(response);
            }
        };
        return responseInterceptor;
    }]);

    appFactory.factory('DlgMsg', ['$modal', function ($modal) {

        return {
            confirm: function (title, content, size) {
                var s = '';
                if (size)
                    s = size;
                return $modal.open({
                    animation: true,
                    size: s,
                    templateUrl: balintimesConstant.rootpath + '/views/tpls/modal/comfirm.tpl.html',
                    controller: function ($scope, $modalInstance, viewContent) {
                        $scope.viewContent = viewContent;
                        $scope.btnClick = function (btn) {
                            $modalInstance.close(btn)
                        };
                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        }
                    },
                    resolve: {
                        viewContent: function () {
                            return {
                                title: title,
                                content: content
                            };
                        }
                    }
                });
            },
            alert: function (title, content, size) {
                var s = '';
                if (size)
                    s = size;
                return $modal.open({
                    animation: true,
                    size: s,
                    templateUrl: balintimesConstant.rootpath + '/views/tpls/modal/alert.tpl.html',
                    controller: function ($scope, $modalInstance, viewContent) {
                        $scope.viewContent = viewContent;
                        $scope.btnClick = function (btn) {
                            $modalInstance.close(btn)
                        };
                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        }
                    },
                    resolve: {
                        viewContent: function () {
                            return {
                                title: title,
                                content: content
                            };
                        }
                    }
                });
            }
        }
    }]);

    appFactory.factory('AlertMsg', function (inform) {

        var alertInfo = function (msg) {
            inform.add(msg == null ? '信息提示.....' : "<img src='" + balintimesConstant.rootpath + "/resources/image/inform.png' class='inforMessageIco'/>" + msg, {
                type: 'info',
                ttl: 3000,
                html: true
            });
        };
        var alertSuccess = function (msg) {
            inform.add(msg == null ? '成功提示.....' : "<img src='" + balintimesConstant.rootpath + "/resources/image/success.png' class='inforMessageIco'/>" + msg, {
                type: 'success',
                ttl: 3000,
                html: true
            });
        };
        var alertWarning = function (msg) {
            inform.add(msg == null ? '警告提示.....' : "<img src='" + balintimesConstant.rootpath + "/resources/image/warning.png' class='inforMessageIco'/>" + msg, {
                type: 'warning',
                ttl: 5000,
                html: true
            });
        };
        var alertException = function (msg) {
            inform.add(msg == null ? '异常提示.....' : "<img src='" + balintimesConstant.rootpath + "/resources/image/exception.png' class='inforMessageIco'/>" + msg, {
                type: 'danger',
                ttl: 5000,
                html: true
            });
        };
        var alertClear = function () {
            inform.Clear();
        };

        return {
            info: alertInfo,
            success: alertSuccess,
            warning: alertWarning,
            exception: alertException,
            clear: alertClear
        }

    });

    appFactory.factory("AjaxRequest", function ($http, AlertMsg) {

        return {
            Post: function (url, params, alertmsg) {
                url = balintimesConstant.rootpath + url;
                return $http.post(url, params).then(function (response) {
                    if (response.data.responseMsg != "") {
                        if (response.data.success == "true") {
                            if (!(alertmsg == false)) {
                                AlertMsg.success(response.data.responseMsg);
                            }
                        }
                        else {
                            AlertMsg.warning(response.data.responseMsg);
                        }
                    }
                    return response.data;
                });
            },
            Put: function (url, params) {
                url = balintimesConstant.rootpath + url;
                return $http.put(url, params).then(function (response) {
                    if (response.data.responseMsg != "") {
                        if (response.data.success == "true") {
                            AlertMsg.success(response.data.responseMsg);
                        }
                        else {
                            AlertMsg.warning(response.data.responseMsg);
                        }
                    }
                    return response.data;
                });
            },
            Get: function (url, params) {
                url = balintimesConstant.rootpath + url;

                var strParams = "";
                if (params) {
                    var values = [];
                    for (var i in params) {
                        values.push(params[i]);
                    }
                    strParams = values.join("/");

                    url = url + "/" + strParams;
                }
                return $http.get(url).then(function (response) {

                    return response.data;
                });
            },
            TestMsg: function (msg) {
                alert(balintimesConstant.rootpath);
            }
        }
    });

    appFactory.factory("NgUtil", function () {
        return {
            initPageParams: function (params) {
                var r = {};
                if (params) {
                    r = angular.copy(params);
                }
                r.pageSize = balintimesConstant.pageSize;
                r.page = 1;
                r.total = 0;
                return r;
            }
        }
    });

    appFactory.factory("TreeSelectModal", ['$modal', function ($modal) {
        return {
            show: function (title, dataPromise) {

                return $modal.open({
                    animation: true,
                    templateUrl: balintimesConstant.rootpath + '/views/tpls/modal/treeselectmodal.tpl.html',
                    controller: function ($scope, $modalInstance, treeData) {
                        $scope.title = title;
                        $scope.treedata = treeData.data;
                        $scope.selectedNode;
                        $scope.showSelected = function (sel) {
                            $scope.selectedNode = sel;
                        };
                        $scope.btnClick = function (btn) {
                            if ($scope.selectedNode) {
                                $modalInstance.close($scope.selectedNode);
                            }

                        };
                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        }
                    },
                    resolve: {
                        treeData: function () {
                            return dataPromise;
                        }
                    }
                });
            }
        };

        /*        return ;*/
    }]);

    appFactory.factory('LocalStorage', ['$window', function ($window) {

        var Get = function (key) {
            return $window.localStorage.getItem(key);
        };
        var Set = function (key, value) {
            $window.localStorage.setItem(key, value);
        };
        var Remove = function (key) {
            $window.removeItem(key);
        };

        return {
            Get: Get,
            Set: Set,
            Remove: Remove
        }

    }]);

    return appFactory;
})