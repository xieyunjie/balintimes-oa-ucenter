/**
 *
 */
'use strict'
define(function () {
    var p = {
        rootpath: "/oaucenter",
        pageSize: 3,

        views: [{
            stateName: "profile",
            urlPrefix: "/profile",
            url: "/views/profile.js"
        }, {
            stateName: "index",
            urlPrefix: "/index",
            url: "/views/index.js"
        },{
            stateName:'error',
            urlPrefix: "/error",
            url: "/views/error/error.js"
        }]

    };

    return p;
})
;