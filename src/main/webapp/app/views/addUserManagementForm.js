define([
        'jquery',
        'underscore',
        'backbone',
        'text!../templates/add-usermanagement-form-template.html',
        'models/useraccountmanagement',
        'collections/useraccountmanagements',
        'middleware/notification',
        'collections/usersEmailsMgr'
        ],function($, _, Backbone,AddUseManagementFormTemplate,UserAccountManagement,UserAccountManagements,NotificationHelper,UsersEmailsMgr){
	
	var AddUserManagementForm = Backbone.View.extend({
		initialize: function(options){
			this.template=_.template( AddUseManagementFormTemplate);
			this.useraccountmanagement=options.useraccountmanagement;
			this.addUserManagementModal=options.addUserManagementModal;
			this.useraccountmanagements=options.useraccountmanagements;
			this.usersEmails = new UsersEmailsMgr();
			this.usersEmails.bind("reset", this.render, this);
			this.usersEmails.fetch({reset:true});
			//this.useraccountmanagements = new Backbone.Collection(UserAccountManagements.toJSON());
			this.isEdit=(this.useraccountmanagement.get('id'))?true:false; 
			this.render();
		},
		events:{
			'submit #addUserMng':'onSubmit',
			'click  #cancelMng':'cancel',
			'input #phone': 'validatePhoneInput',
            'propertychange  #phone': 'validatePhoneInput',
			'blur #firstName':'validateFirstname',
            'blur #lastName':'validateLastname',
            'blur #email':'validateEmail',
            'blur #username':'validateUsername',
            'blur #password':'validatePassword',
            'click #resetPwd':'enabledPassword'
		},
		enabledPassword:function(event){
			$("#password").attr('readonly',false);
			$("#password").val("");
		},
		validatePassword: function(event){
			if(!($("#password").is('[readonly]'))){
				if ($.trim($('#password').val()).length===0) {
					$('#passwordRequired').show();
					$('#password').val("");
					
				}else{
					$('#passwordRequired').hide();
					
						if( /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,64}$/.test($('#password').val())){
							$('#passwordInvalid').hide();
						}else{
							
							$('#passwordInvalid').show();
						}
					
				}
			}
		},
		validateUsername: function(event){
			if ($.trim($('#username').val()).length===0) {
				$('#usernameRequired').show();
				$('#username').val("");
				
			} else {
				$('#usernameRequired').hide();
				if ((/[^a-zA-Z0-9]/.test($('#username').val()))) {
					
					$('#loginInvalid').show();
				} else {
					$('#loginInvalid').hide();
				}
			}
		},
		validateFirstname: function(event){
        	if($.trim($('#firstName').val()).length === 0){
	    		 $('#nameRequired').show();
	    		 $('#firstName').val("");
	    		 
	    	}else{
	    		$('#nameRequired').hide();
	    		if((/[^a-zA-Z0-9]/.test($('#firstName').val()))){
	    			
	    			$('#nameInvalid').show();
	    		}else{
	    			$('#nameInvalid').hide();
	    		}
	    	}
       },
		
        validatePhoneInput: function (event) {
            var elem = $(event.currentTarget);
            elem.val(elem.val().replace(/[^0-9]/g, ''));
        },
        validateLastname: function (event) {
        	if($.trim($('#lastName').val()).length === 0){
	    		 $('#lastNameRequired').show();
	    		 $('#lastName').val("")
	    		
	    	}else{
	    		$('#lastNameRequired').hide();
	    		if((/[^a-zA-Z0-9]/.test($('#lastName').val()))){
	    			
	    			$('#lastNameInvalid').show();
	    		}else{
	    			$('#lastNameInvalid').hide();
	    		}
	    	}
        },
        validateEmail: function(event){
        	if($.trim($('#email').val()).length === 0){
        		 $('#emailRequired').show();
        		 $('#email').val("");
	    	}else{
	    		$('#emailRequired').hide();
	    		if(!(/^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i.test($('#email').val()))){
	    			$('#emailWrongFormat').show();
	    		}else{
	    			$('#emailWrongFormat').hide();
	    		}

	    	}
        },
        validatePhone: function(event){
        	if($.trim($('#phone').val()).length === 0){
	    		 $('#phoneRequired').show();
	    		 $('#phone').val("");
	    		 isValidForm = false;
	    	}else{
	    		$('#phoneRequired').hide();
	    		if(!(/^\d*[0-9](|.\d*[0-9]|,\d*[0-9])?$/.test($('#phone').val()))){
	    			isValidForm = false;
	    			$('#phoneNumeric').show();
	    		}else{
	    			$('#phoneNumeric').hide();
	    		}
	    	}
        },
		render: function(){
			if (this.isEdit)
				this.useraccountmanagement.set('formTitle','editUserManagement');
			else
				this.useraccountmanagement.set('formTitle','createUserManagement');
			
			var renderedTemplate = this.template({useraccountmanagement: this.useraccountmanagement});
			this.$el.html(renderedTemplate);
		},
		onSubmit: function(e) {
			e.preventDefault();
			var useraccountmanagement;
			var isValidForm=true;
			$('#usermanagementEmailHelp').hide();
			$('#phoneHelp').hide();
			$('#usermanagementLoginHelp').hide();
			if ($.trim($('#firstName').val()).length===0) {
				
				isValidForm=false;
			} else {
				
				if((/[^a-zA-Z0-9]/.test($('#firstName').val()))) {
					isValidForm=false;
					
				} 
			}
			
			if ($.trim($('#lastName').val()).length===0) {
				isValidForm=false;
			} else {
				$('#lastNameRequired').hide();
				if ((/[^a-zA-Z0-9]/.test($('#lastName').val()))) {
					isValidForm=false;
					
				} 
			}
			
			
			if ($.trim($('#email').val()).length===0) {
				
				isValidForm=false;
			}else{
				if(!(/^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i.test($('#email').val()))){
	    			isValidForm = false;
	    		}
			}
			
			if ($.trim($('#phone').val()).length === 0) {
				isValidForm = true;
			} else {
				if (!(/^\d*[0-9](|.\d*[0-9]|,\d*[0-9])?$/.test($('#phone').val()))) {
					isValidForm=false;
					
				} 
			}
			
			
			if ($.trim($('#username').val()).length===0) {
				
				$('#username').val("");
				isValidForm=false;
			} else {
				
				if ((/[^a-zA-Z0-9]/.test($('#username').val()))) {
					isValidForm=false;
					
				}
			}
			if ($.trim($('#password').val()).length===0) {
				
				isValidForm=false;
			}
			if( /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,64}$/.test($('#password').val())){
					
			}else{
					
					isValidForm=false;
			}
			
			
			
			if (this.isEdit) {
				var context=this;
				useraccountmanagement =context.useraccountmanagements.get(this.useraccountmanagement.get('id'));
				
				var email=this.$("#email").val();
                var phone=this.$("#phone").val();
                var login=$("#username").val();
                if(useraccountmanagement.get("email") != email){
                	this.usersEmails.each(function (model) {
                		if (model.get("email").toLowerCase() == email.toLowerCase()) {
                			isValidForm = false;
                			$('#usermanagementEmailHelp').show();
                		}
                	});
                	
                	
                	

                }
                if(useraccountmanagement.get("phone") != phone && phone !== ''){
                	
                	this.usersEmails.each(function (model) {
                		if (model.get("phone")==phone) {
                			isValidForm = false;
                			$('#phoneHelp').show();
                		}
                	});
                	
                	

                	
                }
                
                if(useraccountmanagement.get("username") != login){
                	
                	this.usersEmails.each(function (model) {
                		if (model.get("username") != null){
                			if (model.get("username").toLowerCase()==login.toLowerCase()) {
	                			isValidForm = false;
	                			$('#usermanagementLoginHelp').show();
	                		}
                		}
                	});
                	
                	

                	
                }
                
                
                if(isValidForm){
                	
                	useraccountmanagement.set({
    					firstName: this.$("#firstName").val(),
    					lastName:this.$("#lastName").val(),
    					email:this.$("#email").val(),
    					phone:this.$("#phone").val(),
    					username:this.$("#username").val().toLowerCase(),
    					password:this.$("#password").val(),
    					role:{
    						"id": 43,
    						"name": "user_management",
    						"description": "user-management is encharged to handle team management and the admin accounts"
    					}
    				});
                }
				
				
				
			} else {
				var email=this.$("#email").val();
				useraccountmanagement= new UserAccountManagement();
				
				useraccountmanagement.set({
					//id:nextId,
					firstName: this.$("#firstName").val(),
					lastName:this.$("#lastName").val(),
					email:this.$("#email").val(),
					phone:this.$("#phone").val(),
					username:this.$("#username").val().toLowerCase(),
					password:this.$("#password").val(),
					role:{
						"id": 43,
						"name": "user_management",
						"description": "user-management is encharged to handle team management and the admin accounts"
					},
					linkedToTeam:true
				});
				
				if (isValidForm) {
					
					this.usersEmails.each(function (model) {
                		if (model.get("email").toLowerCase() == useraccountmanagement.get("email").toLowerCase()) {
                			isValidForm = false;
                			$('#usermanagementEmailHelp').show();
                		}
                		if (model.get("phone") !== '' && model.get("phone")==useraccountmanagement.get("phone")) {
							isValidForm = false;
							$('#phoneHelp').show();
						}
                		if (model.get("username") != null){
							if (model.get("username").toLowerCase()==useraccountmanagement.get("username").toLowerCase()) {
								isValidForm = false;
								$('#usermanagementLoginHelp').show();
							}
						}
                	});
					
					
					
					
				}
			}
			
			
			if (isValidForm) {
				var context = this;
				useraccountmanagement.save(null, {
					success : function (newUserInfo) { 
						context.useraccountmanagements.add(newUserInfo);
						context.useraccountmanagements.fetch({reset:true});
						context.showNotification(newUserInfo.get("idTypeMessage"),newUserInfo.get("code"));
					}
				});
				
//				if (!this.isEdit) {
				this.addUserManagementModal.modal('hide');
//				}
				
			}
			
			
			
		},
		cancel: function() {
			this.addUserManagementModal.modal('hide');
			//this.useraccountmanagements.fetch({reset:true});
		},
		showNotification: function (message, code) {
	    	NotificationHelper.showNotification(message,code);
		}
		
	});
	
	return AddUserManagementForm;
});