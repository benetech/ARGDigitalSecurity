/**
 * Author Andres Oviedo
 * 
 * Manage notification message view 
 * 
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/notification-template.html'
], function ($, _, Backbone,NotificationTemplate) {
	 var Notification = Backbone.View.extend({
		 initialize: function(options){
			 this.template=_.template( NotificationTemplate);
			 this.notification=options.notification;
			 this.render();
		 },
		 render: function(){
			 var renderedTemplate = this.template({notification: this.notification});
			 this.$el.append(renderedTemplate);
		 }
	 });
	 return Notification;
});
