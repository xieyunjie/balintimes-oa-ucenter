/**
 * 
 */
'use strict';
define([ 'angular', 'balintimesConstant', 'ui-bootstrap' ], function(angular, balintimesConstant) {

	var appFactory = angular.module('appFactory', [ 'ui.bootstrap' ]);

	appFactory.factory('securityInterceptor', [ '$q', '$rootScope', function($q, $rootScope) {
		var responseInterceptor = {
			response : function(response) {
				return response;
			},
			'responseError' : function(response) {
				if (response.status == 403) {
					window.location = balintimesConstant.rootpath + "/login";

				} else if (response.status == 401) {

				};
				return $q.reject(response);
			}
		};
		return responseInterceptor;
	} ]);

	appFactory.factory('balintimesConfirm', [ '$modal', function($modal) {

		return {
			show : function(title, content) {

				return $modal.open({
					animation : true,
					size : 'sm',
					templateUrl : balintimesConstant.rootpath + '/views/tpls/modal/comfirm.tpl.html',
					controller : function($scope, $modalInstance, viewContent) {
						$scope.viewContent = viewContent;
						$scope.btnClick = function(btn) {
							$modalInstance.close(btn)
						};
						$scope.cancel = function() {
							$modalInstance.dismiss('cancel');
						}
					},
					resolve : {
						viewContent : function() {
							return {
								title : title,
								content : content
							};
						}
					}
				});
			}

		}
	} ]);

	appFactory.factory('AlertMsg', function(inform) {

		var alertInfo = function(msg) {
			inform.add(msg == null ? '信息提示.....' : msg, {
				type : 'info',
				ttl : 3000
			});
		};
		var alertSuccess = function(msg) {
			inform.add(msg == null ? '成功提示.....' : msg, {
				type : 'success',
				ttl : 3000
			});
		};
		var alertWarning = function(msg) {
			inform.add(msg == null ? '警告提示.....' : msg, {
				type : 'warning',
				ttl : 3000
			});
		};
		var alertException = function(msg) {
			inform.add(msg == null ? '异常提示.....' : msg, {
				type : 'danger',
				ttl : 5000
			});
		};
		var alertClear = function() {
			inform.Clear();
		};

		return {
			info : alertInfo,
			success : alertSuccess,
			warning : alertWarning,
			exception : alertException,
			clear : alertClear
		}

	});

	appFactory.factory("AjaxRequest", function($http, AlertMsg) {

		return {
			Post : function(url, params, successFn, failureFn) {
				url = balintimesConstant.rootpath + url;
				return $http.post(url, params).then(function(response) {
					if (response.data.responseMsg != "") {
						AlertMsg.success(response.data.responseMsg);
					}
					return response.data;
				});
			},
			Put : function(url, params, successFn, failureFn) {
				url = balintimesConstant.rootpath + url;
				return $http.put(url, params).then(function(response) {
					if (response.data.responseMsg != "") {
						AlertMsg.success(response.data.responseMsg);
					}
					return response.data;
				});
			},
			Get : function(url) {
				url = balintimesConstant.rootpath + url;
				return $http.get(url).then(function(response) {

					return response.data;
				});
			},
			TestMsg : function(msg) {
				alert(balintimesConstant.rootpath);
			}
		}
	});

	appFactory.factory("NgUtil", function() {
		return {
			initPageParams : function(params) {
				var r = {};
				if (params) {
					r = angular.copy(params);
				}
				r.pageSize = balintimesConstant.pageSize;
				r.total = 0;
				return r;
			}
		}
	});

	return appFactory;
})