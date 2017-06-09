/**
 * Author Daniela Depablos
 * 
 * Model for the user module operation 
 * 
 * save, update and delete
 * 
 */
define([
  'underscore',
  'backbone'
], function (_, Backbone) {

  var UserStatusDashboard = Backbone.Model.extend({
	  urlRoot: '/rest/usersStatus'
  });
  
  return UserStatusDashboard;
});