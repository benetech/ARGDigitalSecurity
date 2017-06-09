define([
    'jquery',
    'backbone',
    'models/login',
    'views/teamManagement',
    'views/userManagement',
    'views/moduleManagement',
    'views/teamStatusDashboard',
    'middleware/language',
    'bootbox'
], function ($, Backbone, LoginStatus, TeamManagementView, UserManagementView, ModuleManagementView, TeamStatusDashboardView, Language, Bootbox) {

    initializePrototypeViews();
    var views = new Views();

    var DashboardRouter = Backbone.Router.extend({
        routes: {
            //"": "loadTeamManagementView",
            "index": "loadTeamManagementView",
            "users": "loadUserManagementView",
            "modules": "loadModuleManagementView",
            "teamstatus": "loadTeamStatusDashboardView"
            //"settings": "loadSettingsView"
        },
        setRoute: function (route) {
            window.location.href = route;
        }
    });

    var dashboardRouter = new DashboardRouter();
    
    dashboardRouter.on('route:loadTeamManagementView', function () {
        Language.setupLanguage(loadTeamManagementView);
    });

    dashboardRouter.on('route:loadUserManagementView', function () {
        Language.setupLanguage(loadUserManagementView);
    });

    dashboardRouter.on('route:loadModuleManagementView', function () {
        Language.setupLanguage(loadModuleManagementView);
    });

    dashboardRouter.on('route:loadTeamStatusDashboardView', function () {
        Language.setupLanguage(loadTeamStatusDashboardView);
    });

//    dashboardRouter.on('route:loadSettingsView', function () {
//        Language.setupLanguage(loadSettingsView);
//    });

    function Views() {
    }

    function initializePrototypeViews() {
        Backbone.mainContentSelector = "#dashboardContent"
        Views.prototype.teamManagementView;
        //Views.prototype.settingsView;
        Views.prototype.userManagementView;
        Views.prototype.moduleManagementView;
        Views.prototype.teamStatusDashboardView;
		
    }

    var loadTeamManagementView = function () {
    	if(LoginStatus.get('loggedIn')){
        if (!views.teamManagementView) {
            views.teamManagementView = new TeamManagementView({el: Backbone.mainContentSelector});
        }
        else {
            views.teamManagementView.initialize();
        }
        afterRoute(views.teamManagementView);
    	}
    }

    var loadUserManagementView = function () {
    	if(LoginStatus.get('loggedIn')){
        checkRole('admin').then(function () {
            if (!views.userManagementView) {
                views.userManagementView = new UserManagementView({el: Backbone.mainContentSelector});
            }
            else {
                views.userManagementView.initialize();
            }
            afterRoute(views.userManagementView);
            
        })}
    }

    var loadModuleManagementView = function () {
    	if(LoginStatus.get('loggedIn')){
        if (!views.moduleManagementView) {
            views.moduleManagementView = new ModuleManagementView({el: Backbone.mainContentSelector});
        }
        else {
            views.moduleManagementView.initialize();
        }
        afterRoute(views.moduleManagementView);}
    }

    var loadTeamStatusDashboardView = function () {
    	if(LoginStatus.get('loggedIn')){
        if (!views.teamStatusDashboardView) {
            views.teamStatusDashboardView = new TeamStatusDashboardView({el: Backbone.mainContentSelector});
        }
        else {
            views.teamStatusDashboardView.initialize();
        }
        afterRoute(views.teamStatusDashboardView);}
    }
	

//    var loadSettingsView = function () {
//        if (!views.settingsView) {
//            views.settingsView = new SettingsView({el: Backbone.mainContentSelector});
//        }
//        else {
//            views.settingsView.initialize();
//        }
//        afterRoute(views.settingsView);
//    }

    // Check if a user has a role or redirect back to the index
    // Returns a deferred promise object
    var checkRole = function (role) {
        var deferred = $.Deferred();
        if (LoginStatus.hasRole(role)) {
            deferred.resolve();
        }
        else {
            deferred.fail(function () {
                window.location.replace('/#index');
            })
            deferred.reject();
        }
        return deferred;
    }

    // Post route change callback
    var afterRoute = function (routeView) {
        Backbone.currentView = routeView;
    }
    
    $(document).ajaxError(function (e, xhr, options) {
    	  // do your stuff
    	if(xhr.status == 401){
    		
    		Bootbox.alert("Your session has expired ", function() {
    		    // any code you want to happen after the alert is dismissed
    			LoginStatus.destroy();
			      LoginStatus.set({
			        loggedIn:false
			      });
			      window.location.reload();
    		});
    		
    	}
    	
    });
    
    Backbone.history.start();

    return dashboardRouter;
});