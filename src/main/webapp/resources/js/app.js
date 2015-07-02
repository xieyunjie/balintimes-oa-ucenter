"use strict"

define([ 'angularAMD', 'balintimesConstant', 'angular-ui-router', 'ui-router-extras', 'blockUI', 'inform', 'ui-bootstrap', 'angular-messages', 'appConfig', 'appFactory',
		'appDirective' ], function(angularAMD, balintimesConstant) {

	var app =
			angular.module("mainModule", [ 'ct.ui.router.extras.future', 'ct.ui.router.extras.statevis', 'blockUI', 'inform', 'ui.bootstrap', 'ngMessages', 'appConfig',
					'appFactory', 'appDirective' ]);

	app.config([ '$futureStateProvider', '$controllerProvider', function($futureStateProvider, $controllerProvider) {

		var loadAndRegisterFutureStates = function($http) {
			return $http.get(balintimesConstant.rootpath + '/home/usermodule').then(function(resp) {
				angular.forEach(resp.data, function(module) {

					angular.forEach(module.menus, function(m) {
						$futureStateProvider.futureState({
							stateName : module.state + '/' + m.state,
							urlPrefix : '/' + module.state + '/' + m.state,
							type : 'ngload',
							src : balintimesConstant.rootpath + m.url
						});
					});
				});
			})
		};

		$futureStateProvider.stateFactory('ngload', ngloadStateFactory);
		$futureStateProvider.stateFactory('iframe', iframeStateFactory);
		$futureStateProvider.stateFactory('requireCtrl', requireCtrlStateFactory);
		$futureStateProvider.addResolve(loadAndRegisterFutureStates);

	} ]);

	app.run(function($rootScope, $state, $window, $timeout, $modal, $http, blockUI) {

		$rootScope.root = balintimesConstant.rootpath;
		$rootScope.$state = $state;
		$rootScope.currentModule = {}
		$rootScope.$window = $window;
		$rootScope.TestMsg = "this is Test Message!!!";
		$rootScope.modules = {};
		$rootScope.menus = {};
		$rootScope.WebUser = {};

		$rootScope.$on("$stateChangeSuccess", function() {
			$timeout(function() {
				console.info("Local:" + $window.location.pathname + $window.location.hash);

			});
		});

		$rootScope.$on("$stateChangeStart", function() {
			$timeout(function() {
				console.info("To:" + $window.location.pathname + $window.location.hash);
			});
		});

		$rootScope.init = function() {
			$http.get(balintimesConstant.rootpath + "/home/inituser").then(function(resp) {
				var webuser = resp.data;
				$rootScope.modules = webuser.modules;
				$rootScope.WebUser = webuser;

				if (angular.isDefined($window.location.hash)) {
					var hash = $window.location.hash;
					var uid = "#";
					angular.forEach($rootScope.modules, function(r) {
						if (hash.indexOf(r.state) > -1) {
							uid = r.uid;
							return false;
						}
					})
					$rootScope.initMenu(uid);
				}
			});

			// $rootScope.routers = routers;
			// console.info("init");

		}
		$rootScope.initMenu = function(uid) {
			for (var i = 0; i < $rootScope.modules.length; i++) {
				if ($rootScope.modules[i].uid == uid) {
					$rootScope.currentModule = $rootScope.modules[i];
					$rootScope.menus = $rootScope.modules[i].menus;
					break;
				}
			}
		}
		$rootScope.init();
	});

	angularAMD.bootstrap(app);

	return app;

	function requireCtrlStateFactory($q, futureState) {
		var d = $q.defer();

		require([ 'lazyController' ], function(lazyController) {
			var fullstate = {
				controller : lazyController,
				name : futureState.stateName,
				url : futureState.urlPrefix,
				templateUrl : futureState.templateUrl
			};
			d.resolve(fullstate);
		});
		return d.promise;
	}

	function iframeStateFactory($q, futureState) {
		var state = {
			name : futureState.stateName,
			template : "<iframe src='" + futureState.src + "'></iframe>",
			url : futureState.urlPrefix
		};
		return $q.when(state);
	}

	function ngloadStateFactory($q, futureState) {
		var ngloadDeferred = $q.defer();
		require([ "ngload!" + futureState.src, 'ngload', 'angularAMD' ], function ngloadCallback(result, ngload, angularAMD) {
			angularAMD.processQueue();
			ngloadDeferred.resolve(undefined);
		});
		return ngloadDeferred.promise;
	}

});