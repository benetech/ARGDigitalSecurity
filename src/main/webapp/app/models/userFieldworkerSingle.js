/**
 * Author Daniela Depablos
 *
 *Model to get data of fieldworker selected in team status dashboard user
 *
 */
define([
    'underscore',
    'backbone'
], function (_, Backbone) {

    var UserFieldWorker = Backbone.Model.extend({
    	initialize: function( options) {
			  options || (options = {});
	           this.id = options.id;
		 },
         url: function () {
             return '/rest/users/singleData/'+this.id;
        }
    	,
        setId: function(id) {
          this.id = id;
        }
    });

    return UserFieldWorker;
});
