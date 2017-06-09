/**
 * Author Andres Oviedo
 *
 *Model to save, update and delete user
 *
 */
define([
    'underscore',
    'backbone'
], function (_, Backbone) {

    var UserInfo = Backbone.Model.extend({
        urlRoot: '/rest/users'
    });

    return UserInfo;
});
