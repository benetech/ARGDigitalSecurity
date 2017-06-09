var dispatcher = _.clone(Backbone.Events)

define([
    'jquery',
    'underscore',
    'backbone',
    'text!../templates/add-user-module-template.html',
    'collections/modulesInfo',
    'models/userStatusDashboard',
    'middleware/notification'
], function ($, _, Backbone, UserModuleTemplate, ModulesInfo, UserStatusDashboard,NotificationHelper) {
    var AddUserModule = Backbone.View.extend({
        initialize: function (options) {
            this.template = _.template(UserModuleTemplate);
            this.collection = new ModulesInfo();
            this.collection.bind("reset", this.render, this);
            this.collection.fetch({reset: true});
            this.userModule = options.userModule;
            this.userSelected = options.userSelected;
            this.moduleUserInfos = options.moduleUserInfos;
            this.userModulesCollection=options.usersModulesCoL;
            this.userFieldWorker=options.userFieldWorker;
            this.originalUserInfos=options.originalUserInfos;
            dispatcher.on( 'CloseView', this.close, this );
            this.render();
            
        },
        render: function () {
            var renderedTemplate = this.template({
                modulesInfo: this.collection.toJSON(),
                userModule: this.userModule
            });

            this.$el.html(renderedTemplate);
            this.$el.addClass('modal fade');
            this.$el.modal({});
        },
        events: {
            'click #moduleUserDiv': 'addModule',
            'click #closeAddModule' : 'closeModal'
        },
        addModule: function (event) {

            var id = $(event.currentTarget).attr("data-id");
            console.log('Module ID ' + id);
            var userStatusDahsboard = new UserStatusDashboard();

            var context = this;
            userStatusDahsboard.set({
                moduleId: id,
                status: 'assigned',
                userId: this.userFieldWorker.get("id"),
                moduleName: "",
                active: true

            });
            userStatusDahsboard.save(null, {
                success: function (model) {
                	context.userModulesCollection.fetch({reset: true});
                	context.userFieldWorker.fetch({reset: true});
                	
                	context.originalUserInfos.fetch({reset: true});
                	context.showNotification(model.get("idTypeMessage"),model.get("code"));
                }, error: (function (e) {
                	context.showNotification(3,"Error in service, please contact your administrator");
                })
            });
            this.close();
            this.$el.modal('hide');
            
            
        },	
		 closeModal: function(){
			 this.$el.modal('hide');
			 
		 },
        showNotification: function (message, code) {
            NotificationHelper.showNotification(message,code);
        },
        close: function() {
        	 	
        	  this.undelegateEvents();
        	 	
        }
    });
    return AddUserModule;
});
