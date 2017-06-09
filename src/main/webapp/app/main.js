/**
 * Author Andres Oviedo
 *
 * This file is the application bootstrap. It contains RequireJS configuration and Backbone bootstrap.
 *
 *
 */
require.config({
    /**
     * Require 2.0 introduced shim config which allows to configure dependencies for
     * scripts that do not call define() to register a module
     */
    shim: {
        "underscore": {
            exports: "_"
        },
        "backbone": {
            deps: [
                "underscore",
                "jquery"
            ],
            exports: "Backbone"
        },
        "bootstrap": {
            deps: [
                "jquery"
            ],
            exports: "jQuery"
        },
        "polyglot": {
            exports: "Polyglot"
        }
    },
    /**
     * Shortcut configuration for libs
     */
    paths: {
        jquery: "../libs/jquery/jquery-2.2.4.min",
        bootstrap: "../libs/bootstrap/js/bootstrap.min",
        underscore: "../libs/underscore/underscore-min",
        backbone: "../libs/backbone/backbone-min",
        text: "../libs/require/text",
        bootbox: "../libs/bootbox/bootbox.min",
        polyglot: "../libs/polyglot/polyglot.min",
        chartist: "../libs/chartist-js/dist/chartist.min",
        multiselect:"../libs/bootstrap/js/bootstrap-multiselect",
        chartistPlugin:"../libs/chartist-js/dist/chartist-plugin-legend"
    }
});

require([
    'jquery',
    'bootstrap',
    'views/login',
    'views/dashboard',
    'middleware/language'
], function ($, bootstrap, LoginView, DashboardView, Language) {
    // Initialize
    var loadLoginView = function () {
        new LoginView(function () {
            var dashboardView = new DashboardView({el: $("#digitalSecurityTrainingApp")});
            dashboardView.setup();
        });
    }

    Language.setupLanguage(loadLoginView);

});
