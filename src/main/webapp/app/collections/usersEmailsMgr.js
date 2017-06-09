/**
 * 
 */

define([
	'underscore',
	'backbone',
	'models/usersEmailMgr'
        ],function(_, Backbone,UsersEmailMgr){
	
	return Backbone.Collection.extend({
		  model: UsersEmailMgr,
		  url:'/rest/users/emailsmgr'
	  }); 
	

	
});