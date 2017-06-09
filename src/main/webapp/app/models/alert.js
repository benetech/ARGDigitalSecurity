/**
 * Author Andres Oviedo alert js model 
 */
define([
  'underscore',
  'backbone'
], function (_, Backbone) {
	
   var Error = Backbone.Model.extend({
    defaults:{
      code:'',
      message:''
    }
    

  });

  // Return the view as the Require module
  return new Error();
});