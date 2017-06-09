/**
 * Author Daniela Depablos
 * 
 * User Management Dashboard View 
 * 
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/user-management-template.html',
  'collections/useraccountmanagements',
  'views/userManagementInfo',
  'bootbox',
  'views/addUserManagement',
  'models/useraccountmanagement'
], function ($, _, Backbone,UserManagementTemplate,UserAccountManagements,UserManagementInfoView,Bootbox,AddUserManagementView,UserAccountManagement) {
	 var UserManagement = Backbone.View.extend({
		 initialize: function(){
			 this.template = _.template(UserManagementTemplate);
			 this.initPrototypeViews();
			 this.originalUserInfos = new UserAccountManagements();
			 this.originalUserInfos.bind("reset", this.filterUserList, this);
	         this.originalUserInfos.fetch({reset: true});
	         this.filteredUserInfos= new Backbone.Collection();
			
			 this.render();
			 
		 },
		 initPrototypeViews: function(){
			function Views(){}
			Views.prototype.addUserManagementView;
			Views.prototype.userManagementInfoView;
			this.views=new Views();
		 },
		 events:{
			 'input #searchUserMng':'filterUserList',
			 'click #removeUserMng' : 'removeUserMng',
			 'click #btnAddUserMng' : 'showAddUserModal',
			 'click #unlockUser' : 'unlockUser',
			 'click #editUserMngInLst':'editAccountMng',
			 'click #searchclear':'clearFilter'
		 },
		 clearFilter: function (event) {
	        	console.log("clear Mng");
	        	var
	        	filteredCollection= this.originalUserInfos.toArray();
	        	this.filteredUserInfos.reset(filteredCollection);
		    	$("#searchUserMng").val("");
	        },
		 filterUserList:function(event){
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
		 showAddUserModal:function(){
			 this.userAccountManagement=new UserAccountManagement();
			 if(!this.views.addUserManagementView)
				 this.views.addUserManagementView = new AddUserManagementView({el: $("#addUserManagementModal"),
					 originalUserInfos:this.originalUserInfos,userAccountManagement:this.userAccountManagement});
			 else{
				 this.views.addUserManagementView.setElement($("#addUserManagementModal"));
				 this.views.addUserManagementView.initialize({originalUserInfos:this.originalUserInfos,userAccountManagement:this.userAccountManagement});
			 }
				 
		 },
		 removeUserMng: function (event){
			 event.preventDefault();
			 var context=this;
			 var modelUser=null;
			 Bootbox.confirm(polyglot.t('removeUserManagementMessage'), function(result) {
				 if(result===true)
				 {
					 var id=$(event.currentTarget).attr("data-id");
					 context.originalUserInfos.each(function(model) {
						if(model.get("id")==id)
							 model.set({active:false});
						
						     //model.save();
					 });
					 modelUser=context.originalUserInfos.get(id);
					 modelUser.destroy(
							 {
								    success : function (oldUserInfo) { 
								    	context.originalUserInfos.fetch({reset:true});
								    }
								}		 
					 );
					 
					 $("#searchUserMng").val("");
				 }
			 }); 
			 
		 },	 
		 unlockUser:function(event){
			 var context=this;
			 var id=$(event.target).attr("data-id");
			 var modelUser=null;
			 
			 
			 Bootbox.confirm(polyglot.t('unlockUserMng'), function(result) {
				 if(result===true)
				 {
					 modelUser= context.originalUserInfos.get(id);
					 modelUser.set({accountStatus:true});
					 modelUser.set({code:'5'});
					 modelUser.save(null,{
						    success : function (newUserInfo) { 
						    	
						    	context.originalUserInfos.fetch({reset:true});
						    	
						    }
						});
					 
				 }
			 });
			 
		 },
		 render: function(){
			 var template = _.template( UserManagementTemplate, {} );
			 this.$el.html( template );
			 this.addUserMngmListView();
		 },
		 addUserMngmListView:function(){
			if(!this.views.userManagementInfoView)
				this.views.userManagementInfoView = new UserManagementInfoView({el: $("#usersListMng") , collection: this.filteredUserInfos});
			else{
				this.views.userManagementInfoView.setElement($("#usersListMng"));;
				this.views.userManagementInfoView.initialize();
			}
				
			
			
		 }, 
		 editAccountMng:function(event){
			 var id = $(event.currentTarget).attr("data-id");
			 this.userAccountManagement=this.originalUserInfos.get(id);
			 if(!this.views.addUserManagementView)
				 this.views.addUserManagementView = new AddUserManagementView({el: $("#addUserManagementModal"),
					 originalUserInfos:this.originalUserInfos,userAccountManagement:this.userAccountManagement});
			 else{
				 this.views.addUserManagementView.setElement($("#addUserManagementModal"));
				 this.views.addUserManagementView.initialize({originalUserInfos:this.originalUserInfos,userAccountManagement:this.userAccountManagement});
			 }
		 }
	 });
	 return UserManagement;
});
