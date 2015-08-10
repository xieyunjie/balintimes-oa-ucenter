/**
 *
 */
'use strict';
define(['angular', 'balintimesConstant'], function (angular) {

    var appDirective = angular.module('appDirective', []);

    appDirective.directive("trMouseoverToggle", function () {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {

                angular.element(element.find("td")[attrs["trMouseoverToggle"]]).children().hide();

                element.on("mouseover", function () {
                    angular.element(element.find("td")[attrs["trMouseoverToggle"]]).children().show();
                });
                element.on("mouseout", function () {
                    angular.element(element.find("td")[attrs["trMouseoverToggle"]]).children().hide();
                })
            }
        }
    });
    appDirective.directive("matchValidator", function () {
        return {
            require: "ngModel",
            link: function (scope, element, attrs, ngModel) {
                ngModel.$parsers.push(function (value) {
                    ngModel.$setValidity("match", value == scope.$eval(attrs.matchValidator));
                    return value;
                })

            }
        }
    });

    appDirective.directive("userMenu", ["$compile", function ($compile) {
        return {
            restrict: 'E',
            scope: {
                userMenus: "="
            },
            link: function (scope, element) {

                scope.$watch("userMenus", function (menus) {
                    if (!menus.length) {
                        return;
                    }
                    var menu = {};
                    var html_menu = "";
                    for (var i = 0; i < menus.length; i++) {
                        menu = menus[i];
                        html_menu += '<li class="treeview"><a href="#"><i class="fa ' + menu.iconclass + '"></i> ' +
                            '<span>' + menu.name + '</span><i class="fa fa-angle-left pull-right"></i></a><ul class="treeview-menu">';
                        html_menu += gentree(menu);
                        html_menu += '</ul></li>';
                    }

                    angular.element(element).after($compile(angular.element(html_menu))(scope));
                });

                var gentree = function (menu) {

                    var child = {},
                        html = '';

                    for (var i = 0; i < menu.children.length; i++) {
                        child = menu.children[i];

                        if (child.children.length == 0) {
                            html += '<li><a href="#" ui-sref="' + child.state + '"><i class="fa ' + child.iconclass + '"></i>' + child.name + '</a></li>';
                        }
                        else {
                            html += '<li><a href="#"><i class="fa ' + child.iconclass + '"></i>' + child.name + '<i class="fa fa-angle-left pull-right"></i></a>' +
                                '<ul class="treeview-menu">';
                            html += gentree(child);
                            html += '</ul></li>';
                        }
                    }

                    return html;
                };
                //}
            }
        }

    }]);


    return appDirective;

});