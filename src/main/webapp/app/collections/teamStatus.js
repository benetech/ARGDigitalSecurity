/**
 * Author
 *
 * 
 *
 */
define([
    'underscore',
    'backbone',
    'models/teamStatusModel'
], function (_, Backbone, TeamStatusModel) {
    return Backbone.Collection.extend({
        model: TeamStatusModel,
        url: '/rest/teamstatus',
        getById: function (id) {
            var model;
            if (model = this.get(id)) {
                return $.Deferred().resolveWith(this, model);
            } else {
                model = new UserInfo({id: id});
                return model.fetch();
            }
        }
    });
});
