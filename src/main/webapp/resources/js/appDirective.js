/**
 * 
 */
'use strict';
define([ 'angular', 'balintimesConstant' ], function(angular, balintimesConstant) {

	var appDirective = angular.module('appDirective', []);

	appDirective.directive('bltextinput', function() {
		return {
			scope : {
				label : "@",
				name : "@",
				value : "="
			},
			require : 'ngModel',
			restrict : 'AE',
			templateUrl : balintimesConstant.rootpath + "/views/tpls/directive/bl-textinput.html",
			replace : true,
			transclude : true,
			link : function(scope, elem, attrs, ngModel) {
				elem.find('input').attr('ng-maxlength', attrs.maxlength);
				elem.find('input').attr('ng-minlength', attrs.minlength);
				elem.find('input').attr('required', attrs.required);
				scope.$watch(attrs.ngModel, function(e) {
					console.info("link link link link");
					
					 ngModel.$setValidity("minlength",false);
				});
				scope.$parent.$watch(attrs.ngModel, function(e) {
					console.info("$parent link link link");
					ngModel.$validators.minlength = false;
				});
			}
		};
	});

	return appDirective;

});