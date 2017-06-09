define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/add-user-template.html',
  'collections/userInfos',
  'views/simpleUserInfo',
  'models/userInfo',
  'views/addUserForm',
  'bootbox'
], function ($, _, Backbone,AddUserTemplate,UserInfos,SimpleUserInfoView,UserInfo,AddUserFormView,Bootbox) {
	 var AddUser = Backbone.View.extend({
		 initialize: function(options){
			 this.template = _.template( AddUserTemplate);
			 this.initPrototypeViews();
			 this.userInfo=options.userInfo;
			 this.originalUserInfos = options.originalUserInfos;
			 this.render();
			 this.addUserListView();
			 //this.collection.bind('remove', this.render,this );
			 
	         
	         
		 },
		 initPrototypeViews: function(){
			function Views(){}
			Views.prototype.simpleUserInfoView;
			Views.prototype.addUserFormView;
			this.views = new Views();
		 },
		 events:{
			 'input #searchAllUsers':'filterUserList',
			 'click #editUser': 'editUser',
			 'click #addUserToTeam':'addUserToTeam',
			 'click #removeUser':'removeUser',
			 'click #close':'close'
		 },
		 filterUserList:function(event){
			 var search = $(event.target).val();
			 var filteredCollection = this.originalUserInfos.filter(function(model) {
				    return _.any(model.attributes, function(val, attr) {
				    	if((attr === "firstName" || attr === "lastName" || attr === "email") && (search.trim() !== ""))
				    		return ~val.toLowerCase().indexOf(search.toLowerCase());
				    });;
			 });
			 this.collection.reset(filteredCollection);
		 },
		 addUserToTeam:function(event){
			 var context = this;
			 var id = $(event.currentTarget).attr("data-id");
			 var modelUSer = null;
			 this.originalUserInfos.each(function(model) {
				if(model.get("id") == id)
					 model.set({linkedToTeam:true});
			 });
			 //collection
			 modelUSer = context.originalUserInfos.get(id);
		     modelUSer.set({linkedToTeam:true});
		     modelUSer.set({code:'2'});
		     modelUSer.save(null,{
				    success : function () { 
				    	context.originalUserInfos.reset(context.originalUserInfos.toJSON());
				    }
				});
		
			 //UserInfos.reset(this.originalUserInfos.toJSON());
			 this.showModal(false);
		 },
		 editUser:function(event){
			 event.stopPropagation();
			 var id = $(event.currentTarget).attr("data-id");
			 this.userInfo = this.originalUserInfos.get(id);
			 
			 if(!this.views.addUserFormView)
				 this.views.addUserFormView = new AddUserFormView({el: $("#addUserForm"),userInfo:this.userInfo,addUserModal:this.addUserModal,userCollection:this.originalUserInfos});
			 else
				 this.views.addUserFormView.initialize({userInfo:this.userInfo,addUserModal:this.addUserModal,userCollection:this.originalUserInfos});
			 this.collection.reset([]);
			
			 $("#searchAllUsers").val("");
			
			 
			 
		 },
		 removeUser:function(event){
			 event.stopPropagation();
			 var context = this;
			 var userInfo = null;
			 Bootbox.confirm(polyglot.t('deleteMessage'), function(result) {
				 if(result === true)
				 {
					 var id = $(event.currentTarget).attr("data-id");
					 userInfo = context.originalUserInfos.get(id);
					 userInfo.destroy({
						 success:function(){
							 context.originalUserInfos.fetch({reset:true});
								// UserInfos.fetch();
								 context.collection.reset(context.originalUserInfos.toJSON());
								 context.collection.reset([]);
								 $('#searchAllUsers').val("");
								 $("#searchUser").val("");
						 }
					 });
					 
					
				 }
			 });
			 
			
			
		 },
		 addUserListView:function(){
			 this.collection = new Backbone.Collection();
			 if(!this.views.simpleUserInfoView)
				 this.views.simpleUserInfoView = new SimpleUserInfoView({el: $("#allUsersList") , collection: this.collection});
			 else
				 this.views.simpleUserInfoView.initialize();
		 },
		 render: function(){
			 var renderedTemplate = this.template({});
			 this.$el.html(renderedTemplate);
			 this.addUserModal = $('#addUserModal > .modal');
			 this.showModal(true);
			 
			 if(!this.views.addUserFormView)
				 this.views.addUserFormView = new AddUserFormView({el: $("#addUserForm"),userInfo:this.userInfo, addUserModal:this.addUserModal,userCollection:this.originalUserInfos});
			 else{
				 this.views.addUserFormView.setElement($('#addUserModal > .modal'));
				 this.views.addUserFormView.initialize({userInfo:this.userInfo,addUserModal:this.addUserModal,userCollection:this.originalUserInfos});
			 }
				 
		 },
		 showModal:function(show){
			 if(show)
				 this.addUserModal.modal('show');
			 else
				 this.addUserModal.modal('hide');
		 },
		 close: function(){
			 $("#addUserForm").hide();
		 }
	 });
	 return AddUser;
});
