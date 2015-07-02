/**
 * 
 */
'use strict';
define([ 'angular', 'balintimesConstant', 'blockUI'], function(angular, balintimesConstant) {

	var appConfig = angular.module('appConfig', [ 'blockUI' ]);

	appConfig.config([ '$httpProvider', function($httpProvider) {
		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
		$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
		$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

		$httpProvider.defaults.transformRequest = [ function(data) {
			if (data) {
				return $.param(data);
			}
			return data;
		} ];

		$httpProvider.interceptors.push('securityInterceptor');
	} ]);

	appConfig.config(function(blockUIConfig) {
		blockUIConfig.message = '正在努力加载中 ......';
		blockUIConfig.autoInjectBodyBlock = false;
		blockUIConfig.blockBrowserNavigation = true;
		blockUIConfig.requestFilter = function(config) {

			var message;

			switch (config.method) {
			case 'GET':
				message = '正在获取数据 ......';
				break;

			case 'POST':
				message = '正在提交数据 ......';
				break;

			case 'DELETE':
				message = '正在提交数据 ......';
				break;

			case 'PUT':
				message = '正在提交数据 ......';
				break;
			}
			;

			return message;

		};
	});

	appConfig.config(function(paginationConfig) {
		paginationConfig.itemsPerPage = balintimesConstant.pageSize;
		paginationConfig.firstText = "«";
		paginationConfig.previousText = '‹';
		paginationConfig.nextText = '›';
		paginationConfig.lastText = '»';
	});

	return appConfig;
})