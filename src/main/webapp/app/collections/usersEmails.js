/**
 * 
 */

define([
	'underscore',
	'backbone',
	'models/usersEmail'
        ],function(_, Backbone,UsersEmail){
	
	return Backbone.Collection.extend({
		  model: UsersEmail,
		  url:'/rest/users/emails'
	  }); 
	

	
});