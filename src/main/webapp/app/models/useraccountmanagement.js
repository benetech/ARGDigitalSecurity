/**
 * Author 
 * Daniela Depablos
 * 
 * Model to save, update, delete account management user
 */
define([
  'underscore',
  'backbone'
], function (_, Backbone) {

  var UserAccountManagement = Backbone.Model.extend({
	  urlRoot: '/rest/users'
  });
  
  return UserAccountManagement;
});