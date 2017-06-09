/**
 * Author Bryan Barrantes
 * Handle operations for the modules   
 * save, delete, update operations to communicates with rest controller of the module
 * 
 */
define([
        'underscore',
        'backbone',
        'middleware/notification'
    ], function (_, Backbone,NotificationHelper) {

	var ModuleInfo = Backbone.Model.extend({
	   urlRoot: '/rest/module',
	   
	   showNotification: function () {
           NotificationHelper.showNotification(this.get("idTypeMessage"), this.get("code"));
       }
       
    });
        
    return ModuleInfo;
});