define(['jquery',
        'underscore',
        'backbone',
        'text!../templates/add-user-management-template.html',
        'collections/useraccountmanagements',
        'views/SimpleUserManagementInfo',
        'models/useraccountmanagement',
        'views/addUserManagementForm',
        'bootbox'],
        function($,_,Backbone,AddUserManagementTemplate,UserAccountManagements,SimpleUserManagementInfoView,
        		UserAccountManagement,AddUserManagementFormView,Bootbox){
	var AddUserManagement=Backbone.View.extend({
		initialize: function(options){
			this.template=_.template( AddUserManagementTemplate);
			this.initPrototypeViews();
			this.userAccountManagement=options.userAccountManagement;
			this.originalUserManagementInfos =options.originalUserInfos;
			this.render();
			this.addUserManagementListView();
			this.collection.bind('remove', this.render,this );
			 
			 
//			 this.originalUserManagementInfos = new UserAccountManagements();
//			 this.originalUserManagementInfos.bind('reset', this.filterUserList,this );
//			 this.originalUserManagementInfos.fetch({reset:true});
			 //this.filteredUserInfos= new Backbone.Collection();
			// this.UserAccountManagements=UserAccountManagements;
			// this.UserAccountManagements.bind('reset', this.update,this );
		},
		initPrototypeViews: function(){
				function Views(){}
				Views.prototype.simpleUserManagementInfoView;
				Views.prototype.addUserManagementFormView;
				this.views=new Views();
	    },
		events:{
			'input #searchAllUsersMng': 'filterUserList',
			'click #editUserMng': 'editUser',
            'input #phone': 'validatePhoneInput',
            'propertychange #phone': 'validatePhoneInput'

			 //'click .add-user-list-item':'addUserToTeam',
			 //'click #removeUser':'removeUser'
		},
		validatePhoneInput: function (event) {
			var elem = $(event.currentTarget);
			elem.val(elem.val().replace(/[^0-9]/g, ''));
		},
		filterUserList:function(event){
			var search=$(event.target).val();
			var filteredCollection=this.originalUserManagementInfos.filter(function(model) {
				return _.any(model.attributes, function(val, attr) {
					if((attr==="firstName" || attr==="lastName" || attr==="email") && (search.trim()!==""))
						return ~val.toLowerCase().indexOf(search.toLowerCase());
				});
			});
			this.collection.reset(filteredCollection);
		},
		editUser:function(event){
			event.stopPropagation();
			var id=$(event.currentTarget).attr("data-id");
			this.userAccountManagement=this.originalUserManagementInfos.get(id);
			 
			if(!this.views.addUserManagementFormView)
				this.views.addUserManagementFormView=new AddUserManagementFormView({el: $("#addUserManagementForm"),
					useraccountmanagement:this.userAccountManagement,
					addUserManagementModal:this.addUserManagementModal,
					useraccountmanagements:this.originalUserManagementInfos});
			else
				this.views.addUserManagementFormView.initialize({
					useraccountmanagement: this.userAccountManagement,
					addUserManagementModal: this.addUserManagementModal,
					useraccountmanagements: this.originalUserManagementInfos
				});
			this.collection.reset([]);
			$('#searchAllUsersMng').val("");
		},
		addUserManagementListView:function(){
			 
			this.collection = new Backbone.Collection();
			if(!this.views.simpleUserManagementInfoView)
				this.views.simpleUserManagementInfoView = new SimpleUserManagementInfoView({el: $("#allUsersListMng") , collection: this.collection});
			else{
				this.views.simpleUserInfoView.setElement($("#allUsersListMng"));
				this.views.simpleUserInfoView.initialize();
			}
		},
		render: function(){
			var renderedTemplate = this.template({});
			this.$el.html(renderedTemplate);
			this.addUserManagementModal= $('#addUserManagementModal > .modal');
			this.showModal(true);
			 
			if(!this.views.addUserManagementFormView)
				this.views.addUserManagementFormView=new AddUserManagementFormView({
					el: $("#addUserManagementForm"),
					useraccountmanagement: this.userAccountManagement,
					addUserManagementModal: this.addUserManagementModal,
					useraccountmanagements:this.originalUserManagementInfos
				});
			else
				this.views.addUserManagementFormView.initialize({
					useraccountmanagement: this.userAccountManagement,
					addUserManagementModal: this.addUserManagementModal,
					useraccountmanagements: this.originalUserManagementInfos
				});
		},
		showModal:function(show){
			if(show)
				this.addUserManagementModal.modal('show');
			else
				this.addUserManagementModal.modal('hide');
		},
		update:function(){
			 
			this.originalUserManagementInfos.fetch({reset:true});
			//this.views.simpleUserManagementInfoView = new SimpleUserManagementInfoView({el: $("#allUsersListMng") , collection: this.filteredUserInfos});
			//this.showModal(false);
			//this.collection.reset([]);
		}
	});
	return AddUserManagement;
});