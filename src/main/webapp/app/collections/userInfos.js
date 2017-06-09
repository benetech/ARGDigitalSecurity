/**
 * Author Andres Oviedo
 * 
 * Collection of users 
 * 
 * 
 */
define([
  'underscore',
  'backbone',
  'models/userInfo'
], function (_, Backbone,UserInfo) {

  return  Backbone.Collection.extend({
	  initialize: function( options) {
		  options || (options = {});
           this.username = options.username;
	  },
	  model: UserInfo,
	  url:'/rest/users/fieldworkers/'+this.username,
	  getById:function(id){
		  var model;
		  if(model = this.get(id)){
			  return $.Deferred().resolveWith(this, model);
		  }else {
			  model = new UserInfo({id: id});
			  return model.fetch();
		  }
	  },
      setUsername: function(username) {
        this.username = username;
      }
  });
 
});