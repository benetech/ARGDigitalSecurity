/**
 * Author Daniela Depablos
 *
 *Model to update Pwd
 *
 */
define([
    'underscore',
    'backbone'
], function (_, Backbone) {

    var UserPwd = Backbone.Model.extend({
    	initialize: function( ) {
			 
		 },
         url: function () {
             return '/rest/updatePwd/update';
        }
    	
    });

    return UserPwd;
});