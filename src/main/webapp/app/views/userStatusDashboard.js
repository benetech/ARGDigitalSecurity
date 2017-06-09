define([
    'jquery',
    'underscore',
    'backbone',
    'text!../templates/users-dashboard-template.html',
    'models/userInfo',
    'models/userStatusDashboard',
    'collections/userStatusDashboards',
    'collections/usersModules',
    'bootbox',
    'views/userSteps',
    'views/addUserModule',
    'text!../templates/users-dashboard/module-template.html',
    'models/userFieldworkerSingle',
], function ($, _, Backbone, UserStatusDashboardTemplate, UserInfo, UserStatusDashboard,
             UserStatusDashboards,UsersModules, Bootbox, UserStepsView, AddUserModuleView, modulePartialTemplate, UserFieldWorkerSingle) {
    var UserStatusDashboard = Backbone.View.extend({
        initialize: function (options) {
            this.initPrototypeViews();
            this.userInfoSelected = options.userInfoSelected;
            this.originalUserInfos=options.orginalUserInfos;
            
            
            this.template = _.template(UserStatusDashboardTemplate);
            this.usersModules = new UsersModules();
			this.usersModules.setId(this.userInfoSelected.get("id"));
			this.usersModules.bind("reset", this.render, this);
            this.usersModules.fetch({reset: true});
            
            this.moduleUserInfos = new UserStatusDashboards();
            this.moduleUserInfos.bind("reset", this.render, this);
            this.moduleUserInfos.fetch({reset: true});
            
             
            this.userFieldworker = new UserFieldWorkerSingle();
            this.userFieldworker.setId(this.userInfoSelected.get("id"));
            this.userFieldworker.on('change', this.render, this);
            
            this.userFieldworker.fetch({reset: true});
            this.render();
        },
        initPrototypeViews: function () {
            function Views() {
            }

            Views.prototype.userStepsView;
            Views.prototype.addUserModuleView;
            this.views = new Views();
        },
        showStepsView: function (event) {
            event.stopPropagation();
            var id = $(event.currentTarget).attr("data-id");
            var steps = [];
            for (var i = 0; i < this.modules.length; i++) {

                if ('' + this.modules[i].id === id) {
                    steps = this.modules[i].steps;
                    break;
                }
            }
            if (!this.views.userStepsView)
                this.views.userStepsView = new UserStepsView({
                    el: $("#stepsModal"),
                    steps: steps
                });
            else{
            	this.views.userStepsView.setElement($('#stepsModal'));
            	this.views.userStepsView.initialize({steps: steps});
            }
                
        },
        showAddUserModule: function (event) {
            event.stopPropagation();
          if (!this.views.addUserModuleView)
                this.views.addUserModuleView = new AddUserModuleView({
                    el: $("#userModuleModal"),
                    userModule: this.modules,
                    userSelected: this.userSelected,
                    moduleUserInfos: this.moduleUserInfos,
                    usersModulesCoL:this.usersModules,
                    userFieldWorker:this.userFieldworker,
                    originalUserInfos:this.originalUserInfos
                });
            else{
            		this.views.addUserModuleView.setElement($("#userModuleModal"));
            		this.views.addUserModuleView.initialize({userModule: this.modules,
                        userSelected: this.userSelected,
                        moduleUserInfos: this.moduleUserInfos,
                        usersModulesCoL:this.usersModules,
                        userFieldWorker:this.userFieldworker,
                        originalUserInfos:this.originalUserInfos});
            	
            }
                
        },
        events: {
            /* 'input #searchUser':'filterUserList',
             'click #unlinkUser' : 'unlinkUser',*/
            'click #removeUserModule': 'removeModule',
            'click #stepsUserModule': 'showStepsView',
            'click #btnAddModule': 'showAddUserModule'
           
        },
        render: function () {
        	
            var context = this;
            var renderedTemplate;
            this.prepareModules();
            console.log('context user fieldworker render',context.userFieldworker.toJSON());
            renderedTemplate = this.template({
                userInfoSelected: context.userFieldworker.toJSON(),
                assignedModules: context.assignedModules,
                completedModules: context.completedModules,
                otherModules: context.otherModules,
                modulePartial: context.modulePartial
            });
            this.$el.html(renderedTemplate);
            this.$el.find('[data-toggle="popover"]').popover();
            
        },
        prepareModules: function () {
            var context = this;
            this.modules = [];
            this.assignedModules = [];
            this.completedModules = [];
            this.otherModules = [];
            this.usersModules.each(function (model) {
               // if (model.get("userId") === context.userSelected.id) {
                    var userModule = {
                        id: model.get("id"),
                        userId: model.get("userId"),
                        moduleId: model.get("moduleId"),
                        moduleName: model.get("moduleName"),
                        status: model.get("status"),
                        createdDate: model.get("createdDate"),
                        completedDate: model.get("completedDate"),
                        active: model.get("active")
                    };
                    userModule.steps = {};
                    userModule.steps = model.get("steps");
                    userModule.stepPerc = model.get("stepPerc");
                    context.modules.push(userModule);

                    switch (model.get("status")) {
                        case "assigned":
                            context.assignedModules.push(userModule);
                            break;
                        case "completed":
                            context.completedModules.push(userModule);
                            break;
                        default:
                            context.otherModules.push(userModule);
                    }
                //}
            });
        },
        modulePartial: function (moduleUser) {
            var template = _.template(modulePartialTemplate);
            return template({
                moduleUser: moduleUser
            });
        },
        removeModule: function (event) {
            event.stopPropagation();
            var context = this;
            var userModuleDel = null;

            Bootbox.confirm(polyglot.t('deleteMsgModuleUs'), function (result) {
                if (result === true) {
                    var id = $(event.currentTarget).attr("data-id");
                    userModuleDel=context.usersModules.get(id);
                    userModuleDel.destroy({success:function(){
                    	console.log('Destroying module !!!');
                    	context.usersModules.fetch({reset:true});
                        context.userFieldworker.fetch({reset: true});
                        context.originalUserInfos.fetch({reset: true});
                    }});
                    
                    

                    
                }
            });


        },
        showModal: function (show) {
            if (show)
                this.stepsUserModal.modal('show');
            else
                this.stepsUserModal.modal('hide');
        }
        
    });
    return UserStatusDashboard;
});
