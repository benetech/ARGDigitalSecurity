/**
 * Author Francisco Gómez
 * Handled view of the module steps
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/module-steps-template.html'
], function ($, _, Backbone, ModuleInfoTemplate) {
	 var ModuleSteps = Backbone.View.extend({
		 initialize: function(options){
			 this.template=_.template(ModuleInfoTemplate);
			 this.steps=options.steps;
			 this.render();
		 },
		events:{
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
	 return ModuleSteps;
});
	