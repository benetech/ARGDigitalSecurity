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

  var UsersEmailMgr = Backbone.Model.extend({
	  urlRoot: '/rest/users/emailsmgr'
  });
  
  return UsersEmailMgr;
});