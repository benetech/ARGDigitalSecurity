/**
 * Author Bryan Barrantes
 *
 * Fetch modules list of the app
 *
 */
define([
    'underscore',
    'backbone',
    'models/moduleModel'
], function (_, Backbone, ModuleModel) {
    return Backbone.Collection.extend({
        model: ModuleModel,
        url: '/rest/module/list',
        getById: function (id) {
            var model;
            if (model = this.get(id)) {
                return $.Deferred().resolveWith(this, model);
            } else {
                model = new ModuleModel({id: id});
                return model.fetch();
            }
        }
    });
});
