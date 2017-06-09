/**
 * Define Require module with dependencies
 */
define([
    'underscore',
    'backbone',
    'middleware/notification'
], function (_, Backbone, NotificationHelper) {

    var LoginStatus = Backbone.Model.extend({
        // Defining default values on a not connected anonymous user
        defaults: function(){
            return {
            	loggedIn: null,
                username: '',
                password: '',
                rememberMe: false,
                code: '',
                message: '',
                role: {}
            	
            };
        	
        },

        // Url binding of the REST service
        url: '/login',

        initialize: function () {
            //this.on( "change", this.updateNotification, this );
            //this.bind("change:code", this.showNotification);
        },
        showNotification: function () {
            NotificationHelper.showNotification(this.get("idTypeMessage"), this.get("code"));
        },
        // Shortcut method
        isNew: function () {
            return false;
        },
        // Check if the user has a role
        hasRole: function (role) {
            return (this.get('loggedIn') && this.get('role')!= null);
        }

    });

    // Return the view as the Require module
    return new LoginStatus();
});
