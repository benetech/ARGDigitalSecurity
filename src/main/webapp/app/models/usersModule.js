/**
 * Author Daniela DEpab;ps
 * Handle operations for the modules   
 * save, delete, update operations to communicates with rest controller of the module
 * 
 */
define([
        'underscore',
        'backbone'
    ], function (_, Backbone) {

	var UserModule = Backbone.Model.extend({
	   urlRoot: '/rest/usermodule'
    });
        
    return UserModule;
});