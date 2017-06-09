/**
 * Author Andres Oviedo
 *
 * View for the sample user info
 *
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../templates/user-info-template.html'
], function ($, _, Backbone, UserInfoTemplate) {
    var UserInfo = Backbone.View.extend({
        initialize: function () {
            this.template = _.template(UserInfoTemplate);
            
            this.collection.bind("reset", this.render, this);
            this.collection.bind("add", this.render, this);
            this.render();
        },
        events:{"scroll":"scroll"}
        ,
        render: function () {
            var renderedTemplate = this.template({userInfos: this.collection.toJSON()});
            this.$el.html(renderedTemplate);
            
        },
        scroll: function(){ console.log( "scrolling..." ); }
    });
    return UserInfo;
});
