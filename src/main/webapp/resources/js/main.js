/**
 * 
 */
require.config({
	baseUrl : "/oaucenter/resources/js/",

	paths : {
		'app' : 'app',
		'appConfig' : 'appConfig',
		'appFactory' : 'appFactory',
		'appDirective':'appDirective',

		'angular' : 'angularjs/angular',
		'angular-ui-router' : 'angularjs/angular-ui-router',
		'blockUI' : 'angularjs/angular-block-ui',
		'angular-animate' : 'angularjs/angular-animate',
		'angular-messages' : 'angularjs/angular-messages',

		'inform' : 'angularjs/angular-inform',
		'ui-bootstrap' : 'angularjs/ui-bootstrap-tpls',

		'angularAMD' : 'angularjs/angularAMD.min',
		'ngload' : 'angularjs/ngload.min',
		'ui-router-extras' : 'angularjs/ct-ui-router-extras'

	},
	shim : {
		"angular" : {
			exports : "angular"
		},
		'angular-messages' : [ 'angular' ],
		"angularAMD" : [ "angular" ],
		"ngload" : [ "angularAMD" ],
		"angular-ui-router" : [ "angular" ],
		'blockUI' : [ 'angular' ],
		'inform' : [ 'angular' ],
		'angular-animate' : [ 'angular' ],
		'ui-router-extras' : [ 'angular' ],
		'ui-bootstrap' : [ 'angular' ]

	},

	deps : [ 'app' ]
});
