/**
 * Author Daniela Depablos
 * 
 * Defining view to the sample user account management info
 * 
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/simple-usermanagement-info-template.html'
], function ($, _, Backbone,UserManagementInfoTemplate) {
	 var SimpleUserManagementInfo = Backbone.View.extend({
		 initialize: function(){
			 this.template=_.template( UserManagementInfoTemplate);
			 this.collection.bind("reset",this.render,this);
			 this.render();
		 },
		 render: function(){
			 var renderedTemplate = this.template({userAccountManagements: this.collection.toJSON()});
			 this.$el.html(renderedTemplate);
		 }
	 });
	 return SimpleUserManagementInfo;
});
