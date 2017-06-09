/**
 * Author Andres Oviedo
 * 
 * Defining view behavior for the user info display items in the  adduserform view search
 * 
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/simple-user-info-template.html'
], function ($, _, Backbone,UserInfoTemplate) {
	 var SimpleUserInfo = Backbone.View.extend({
		 initialize: function(){
			 this.template=_.template( UserInfoTemplate);
			 this.collection.bind("reset",this.render,this);
			 this.render();
		 },
		 render: function(){
			 var renderedTemplate = this.template({userInfos: this.collection.toJSON()});
			 
			 this.$el.html(renderedTemplate);
		 }
	 });
	 return SimpleUserInfo;
});
