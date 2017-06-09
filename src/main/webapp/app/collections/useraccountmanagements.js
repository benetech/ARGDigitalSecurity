/**
 * Author Daniela Depablos
 * 
 * Collection backbone to get all the accountmanagement users
 * 
 */
define([
	'underscore',
	'backbone',
	'models/useraccountmanagement'
        ],function(_, Backbone,UserAccountManagement){
	
	return Backbone.Collection.extend({
		  model: UserAccountManagement,
		  url:'/rest/users/usersmanagements',
		  getById:function(id){
			  var model;
			  if(model = this.get(id)){
				  return $.Deferred().resolveWith(this, model);
			  }else {
				  model = new UserAccountManagement({id: id});
				  return model.fetch();
			  }
		  }
	  }); 
	

	
});