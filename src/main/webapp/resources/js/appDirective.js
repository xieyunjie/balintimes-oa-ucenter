/**
 *
 */
'use strict';
define(['angular', 'balintimesConstant'], function (angular, balintimesConstant) {

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

    appDirective.directive("userMenu", function () {
        return {
            restrict: 'EA',
            //scope: {
            //    userMenus:"="
            //},
            link: function (scope, element, attrs) {

                var menus = scope.$eval(attrs["userMenus"])

                console.info(menus);

                //angular.element(element).append('<li class="treeview" litype="module"><a href="#">sssss</a><ul class="treeview-menu"></ul></li>');

                for (var i = 0; i < menus.length; i++) {
                    angular.element(element).append('<li class="treeview" litype="module"><i class="fa fa-share"></i> <span>' + menus[i].name + '</span><i class="fa fa-angle-left pull-right"></i><ul class="treeview-menu"></ul></li>');
                }

                //var genTree = function (menu, elem) {
                //
                //    if(menu.children.length > 0){
                //
                //        angular.element(elem).append('<li class="treeview" litype="module"><a href="#">sssss</a><ul class="treeview-menu"></ul></li>');
                //
                //    }
                //    else{
                //
                //    }
                //
                //}
            }
        }

    });


    return appDirective;

});