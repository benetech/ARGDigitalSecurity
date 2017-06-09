/***
 * Author Andres Oviedo
 *
 * Management of fieldworkers
 *
 *
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../templates/team-management-template.html',
    'collections/userInfos',
    'views/userInfo',
    'bootbox',
    'views/addUser',
    'views/userStatusDashboard',
    'models/login',
    'models/userInfo'
], function ($, _, Backbone, TeamManagementTemplate, UserInfos, UserInfoView, Bootbox, AddUserView, UserStatusDashboardView, LoginStatus, UserInfo) {
    var TeamManagement = Backbone.View.extend({
        initialize: function () {
        	if(LoginStatus.get('loggedIn')){
        		
        	
        	this.template = _.template(TeamManagementTemplate);
            this.initPrototypeViews();
            this.originalUserInfos = new UserInfos();
            this.userInfo= new UserInfo();
            var username=LoginStatus.get('username');
            if(username !== 'undefined'){this.originalUserInfos.setUsername(username);}
            else this.originalUserInfos.setUsername('username');
            
            this.originalUserInfos.bind("reset", this.filterUserList, this);
            this.originalUserInfos.fetch({reset: true});
            this.filteredUserInfos= new Backbone.Collection();
            this.render();
        	}
        },
        initPrototypeViews: function () {
            function Views() {
            
            }

            Views.prototype.addUserView;
            Views.prototype.userInfoView;
            Views.prototype.userStatusDashboardView;
            
            this.views = new Views();
        },
        events: {
            'input #searchUser': 'filterUserList',
            'click #unlinkUser': 'unlinkUser',
            'click #btnAddUser': 'showAddUserModal',
            'click #userIndividual': 'showUserStatusDashboard',
			'click #editUserInLst':'editFieldWorker',
			'click .searchclear':'clearFilter'
        },
        clearFilter: function (event) {
        	console.log("clear ");
        	var
        	filteredCollection= this.originalUserInfos.toArray();
        	this.filteredUserInfos.reset(filteredCollection);
	    	$("#searchUser").val("");
        },
        filterUserList: function (event) {
            var search = $(event.target).val();
            var filteredCollection;
            if(search && search.trim()!==""){
            	filteredCollection = this.originalUserInfos.filter(function (model) {
                    return _.any(model.attributes, function (val, attr) {
                        if (attr === "firstName" || attr === "lastName" || attr === "email" && (search.trim()!==""))
                            return ~val.toLowerCase().indexOf(search.toLowerCase());
                    });
                });
            	
            }else{
            	filteredCollection= this.originalUserInfos.toArray();
            }
            
            this.filteredUserInfos.reset(filteredCollection);
            
           
			
        },
        showAddUserModal: function () {
        	this.userInfo= new UserInfo();
        	if (!this.views.addUserView)
                this.views.addUserView = new AddUserView({el: $("#addUserModal"),  originalUserInfos:this.originalUserInfos, userInfo:this.userInfo});
            else{
            	this.views.addUserView.setElement($("#addUserModal"));
            	this.views.addUserView.initialize({originalUserInfos:this.originalUserInfos, userInfo:this.userInfo});
            }
                
        },
        unlinkUser: function (event) {
            var context = this;
            var modelUSer = null;
            Bootbox.confirm(polyglot.t('deleteFromTeamMessage'), function (result) {
                if (result === true) {
                    var id = $(event.target).attr("data-id");
                    context.originalUserInfos.each(function (model) {
                        if (model.get("id") == id)
                            model.set({linkedToTeam: false});
                        //model.save();
                    });

                    modelUSer = context.originalUserInfos.get(id);
                    modelUSer.set({linkedToTeam: false});
                    modelUSer.set({code: '2'});
                    modelUSer.save(null,{
    				    success : function () { 
    				    	context.originalUserInfos.fetch({reset:true});
    				    	$("#searchUser").val("");
    				    }
    				});

                   
                }
            });
        },
        render: function () {
            
            var renderedTemplate = this.template({});
            this.$el.html(renderedTemplate);
            this.addUserListView();
        },
        addUserListView: function () {
        	
        	
            if (!this.views.userInfoView) {
                this.views.userInfoView = new UserInfoView({
                    el: $("#usersList"),
                    collection: this.filteredUserInfos
                });
                
                
            }
            else {
            	
                this.views.userInfoView.setElement($("#usersList"));
                this.views.userInfoView.initialize();
            }
            
            
        },
        showUserStatusDashboard: function (event) {
            var id = $(event.currentTarget).attr("data-id");

            this.modelUser = this.originalUserInfos.get(id);

            if (!this.views.userStatusDashboardView)

                this.views.userStatusDashboardView = new UserStatusDashboardView({
                    el: $("#userStatusDashboard"),
                    userInfoSelected: this.modelUser,
                    orginalUserInfos:this.originalUserInfos
                });
            else
            	{	
            		this.views.userStatusDashboardView.setElement($("#userStatusDashboard"));
            		this.views.userStatusDashboardView.initialize({userInfoSelected: this.modelUser,orginalUserInfos:this.originalUserInfos});
            	
            	}
            $('html, body').animate({ scrollTop: 0 }, 'fast');
                
        }, editFieldWorker: function(event){
        	var id = $(event.currentTarget).attr("data-id");
        	this.userInfo=this.originalUserInfos.get(id);
        	
        	if (!this.views.addUserView)
                this.views.addUserView = new AddUserView({el: $("#addUserModal"),  originalUserInfos:this.originalUserInfos, userInfo:this.userInfo});
            else{
            	this.views.addUserView.setElement($("#addUserModal"));
            	this.views.addUserView.initialize({originalUserInfos:this.originalUserInfos, userInfo:this.userInfo});
            }
        }
    });
    return TeamManagement;
});
