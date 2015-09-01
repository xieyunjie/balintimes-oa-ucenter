/**
 *
 */
'use strict';
define(['angular', 'balintimesConstant'], function (angular,balintimesConstant) {

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
    appDirective.directive("tdMouseoverToggle", function () {
        return {
            restrict: 'A',
            scope: {},
            link: function (scope, element, attrs) {
                var tr = angular.element(element.parents('tr')[0]);
                var el = element;
                angular.element(el).hide();
                tr.bind('mouseover', function () {
                    angular.element(el).show();
                });
                tr.bind('mouseout', function () {
                    angular.element(el).hide();
                });
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
    
    appDirective.directive('nodeTree', function () {
        return {
            template: '<node ng-repeat="node in tree"></node>',
            replace: true,
            restrict: 'E',
            scope: {
                tree: '=children'
            }
        };
    });

    appDirective.directive('node', function ($compile) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: balintimesConstant.rootpath + '/views/tpls/directive/checktreenode.html',
            link: function (scope, element) {
                /*
                 * Here we are checking that if current node has children then compiling/rendering children.
                 * */
				 
            	var hasCheckChildren=element.attr("checkchildren");;
                if(hasCheckChildren==undefined || hasCheckChildren=="")
                	hasCheckChildren=false;
                
                var hcc=eval(hasCheckChildren);
                
                 var t= element.attr("text");
                 if(t==undefined || t=="")
                    t="name";

                 scope.bindText=eval("scope.node."+t);

                if (scope.node && scope.node.children && scope.node.children.length > 0) {
					var children=scope.node.children;
					scope.node.checkType=-1;
                   
                    var e=element.attr("expanded");
                    if(e==undefined || e=="")
                        e=false;

                    scope.node.childrenVisibility =eval(e);//true;
                    
                    var html='<ul class="tree" ng-if="node.childrenVisibility"><node-tree children="node.children" text="'+element.attr("text")+'" expanded="'+e+'" checkchildren="'+hasCheckChildren+'"></node-tree></ul>';

                    var childNode = $compile(html)(scope);
                    element.append(childNode);
                } else {
					if(scope.node.checked){
						scope.node.checkType=1;
					}else{
						scope.node.checkType=-1;
					}
                    scope.node.childrenVisibility = true;
                    
                    scope.checkParent(scope,scope.node);
                }
            },
            controller: ["$scope","$element", function ($scope,$element) {
                // This function is for just toggle the visibility of children
            	
            	var hasCheckChildren=$element.attr("checkchildren");;
                if(hasCheckChildren==undefined || hasCheckChildren=="")
                	hasCheckChildren=false;
                
                var hcc=eval(hasCheckChildren);
            	
                $scope.toggleVisibility = function (node) {
                    if (node.children) {
                        node.childrenVisibility = !node.childrenVisibility;
                    }
                };
                // Here We are marking check/un-check all the nodes.
                $scope.checkNode = function (sender,node) {
                    node.checked = !node.checked;
					
					$scope.setCheckState(sender,node);
                };

                $scope.checkParent= function(s,c){
						var p=s.$parent.$parent.$parent;
						var n=p.node;
						
						if(n!=undefined){
							if(n.children!=undefined){
								var cAry=new Array();
								var nAry=new Array();
								var uAry=new Array();
								angular.forEach(n.children,function(ch){
									if(ch.checkType==-1)
										nAry.push(ch);
									else if(ch.checkType==0)
										uAry.push(ch);
									else
										cAry.push(ch);
								});
								
								if(uAry.length>0){
									n.checkType=0;
								}else{
									if(cAry.length>0 && nAry.length>0){
										n.checkType=0;
									}else if(cAry.length==n.children.length){
										n.checkType=1;
									}else if(nAry.length==n.children.length){
										n.checkType=-1;
									}else{
										n.checkType=0;
									}
								}
						
							}
							
							$scope.checkParent(p,n);
						}
					}

                $scope.setCheckState=function(sender,node){
                    if(node.checked){
						node.checkType=1;
					}else{
						node.checkType=-1;
					}
					
					
					
                    function checkChildren(c) {
                    	if(hcc==true)
                    		return;
                        angular.forEach(c.children, function (c) {
                            c.checked = node.checked;
							
							if(node.checked){
								c.checkType=1;
							}else{
								c.checkType=-1;
							}
							
                            checkChildren(c);
                        });
                    }
					
					

                    checkChildren(node);
					$scope.checkParent(sender,node);
                };
            }]
        };
    });

    return appDirective;

});