/**
 * Author Daniela Depablos
 * Handled view of the steps of the user modules 
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/user-steps-template.html'
], function ($, _, Backbone,UserInfoTemplate) {
	 var UserSteps = Backbone.View.extend({
		 initialize: function(options){
			 this.template=_.template(UserInfoTemplate);
			 this.steps=options.steps;
			 this.render();
		 },
		events:{
					/* 'input #searchUser':'filterUserList',
					 'click #unlinkUser' : 'unlinkUser',*/
					 'click #closeSteps' : 'closeModal'
		},
		 render: function(){
			 var renderedTemplate = this.template({steps: this.steps});
			 this.$el.html(renderedTemplate);
			 this.$el.addClass('modal fade')
			 this.$el.modal({});	
		 },	
		 closeModal: function(){
			 this.$el.modal('hide');
			 //$('#modalSteps').modal('hide');
		 }
	 });
	 return UserSteps;
});
	