/**
 * Author Daniela Depablos
 * 
 * View for the sample unser account management info 
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/usermanagement-info.html'
], function ($, _, Backbone,UserManagementInfoTemplate) {
	 var UserManagementInfo = Backbone.View.extend({
		 initialize: function(){
			 this.template=_.template( UserManagementInfoTemplate);
			 
			 this.collection.bind("reset",this.render,this);
			 this.collection.bind("add",this.render,this);
			 this.render();
		 },
		 render: function(){
			 
			 var renderedTemplate = this.template({userAccountManagements: this.collection.toJSON()});
			 this.$el.html(renderedTemplate);
		 }
	 });
	 return UserManagementInfo;
});
