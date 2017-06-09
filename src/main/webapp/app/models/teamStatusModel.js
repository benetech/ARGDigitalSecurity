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

  var TeamStatusModel = Backbone.Model.extend({
	  urlRoot: '/rest/teamstatus'
  });
  
  return TeamStatusModel;
});