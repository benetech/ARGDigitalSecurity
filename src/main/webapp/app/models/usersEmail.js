/**
 * Author Daniela Depablos
 * 
 * Model for the user emails 
 * 
 * sget emailsave, update and delete
 * 
 */
define([
  'underscore',
  'backbone'
], function (_, Backbone) {

  var UsersEmail = Backbone.Model.extend({
	  urlRoot: '/rest/users/emails'
  });
  
  return UsersEmail;
});