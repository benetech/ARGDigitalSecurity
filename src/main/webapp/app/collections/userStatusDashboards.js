/**
 * Author Daniela Depablos
 *
 * Collection to get all the user modules
 *
 */
define([
    'underscore',
    'backbone',
    'models/userStatusDashboard'
], function (_, Backbone, UserStatusDashboard) {

    var UserStatusDashboards = Backbone.Collection.extend({
        model: UserStatusDashboard,
        url: '/rest/usersStatus',
        getById: function (id) {
            var model;
            if (model = this.get(id)) {
                return $.Deferred().resolveWith(this, model);
            } else {
                model = new UserStatusDashboard({id: id});
                return model.fetch();
            }
        }
    });

    return UserStatusDashboards;
});
