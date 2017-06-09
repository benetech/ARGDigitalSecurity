define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/alert.html'
], function ($, _, Backbone,AlertInfoTemplate) {
	 var Alert = Backbone.View.extend({
		 initialize: function(){
			 this.template=_.template( AlertInfoTemplate);
			 this.collection.bind("reset",this.render,this);
			 this.render();
		 },
		 render: function(){
			 var renderedTemplate = this.template({alertInfoTemplate: this.collection.toJSON()});
			 
			 this.$el.html(renderedTemplate);
		 }
	 });
	 return Alert;
});
