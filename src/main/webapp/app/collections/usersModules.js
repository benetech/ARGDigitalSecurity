/**
 * Author Daniela Depablos
 *
 * Fetch modules list of the app
 *
 */
define([
    'underscore',
    'backbone',
    'models/userStatusDashboard'
], function (_, Backbone, userModule) {
	return Backbone.Collection.extend({
		  initialize: function( options) {
			  options || (options = {});
	           this.id = options.id;
		  },
          url: function () {
            return '/rest/usersStatus/' + this.id;
          },
          setId: function(id) {
            this.id = id;
          },
		  model: userModule,
		});
    
});

