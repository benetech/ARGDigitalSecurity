/**
 * Author Andres Oviedo
 *
 *Model to manipulate Object of message of the service
 *
 * 
 */
define([
  'underscore',
  'backbone'
], function (_, Backbone) {

  var Notification = Backbone.Model.extend({ 
	  defaults:{
		  idTypeMessage:null,
		  code:''
	  }
  });
  
  return Notification;
});