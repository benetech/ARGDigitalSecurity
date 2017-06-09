/**
 * @author daniela.depablos
 * User Form for fieldworkers 
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../templates/add-user-form-template.html',
    'models/userInfo',
    'collections/userInfos',
    'middleware/notification',
    'collections/usersEmails'
], function ($, _, Backbone, AddUserFormTemplate, UserInfo, UserInfos,NotificationHelper,UsersEmails) {
    var AddUserForm = Backbone.View.extend({
        initialize: function (options) {
            this.template = _.template(AddUserFormTemplate);
            this.userInfo = options.userInfo;
            this.userInfoOld=options.userInfo;
            
            this.addUserModal = options.addUserModal;
            this.userCollection = options.userCollection;
            this.userCollection.bind("reset", this.render, this);
            
            this.usersEmails = new UsersEmails();
			this.usersEmails.bind("reset", this.render, this);
			this.usersEmails.fetch({reset:true});
            //this.userCollection = new Backbone.Collection(UserInfos.toJSON());
            this.isEdit = (this.userInfo.get('id')) ? true : false;
            this.render();
        },
        events: {
            'submit #addUser': 'onSubmit',
            'click #cancel': 'cancel',
            'input #phone': 'validatePhoneInput',
            'propertychange  #phone': 'validatePhoneInput',
            'blur #firstName':'validateFirstname',
            'blur #lastName':'validateLastname',
            'blur #email':'validateEmail'
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
	    		 $('#phone').val('');
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
        render: function () {
            if (this.isEdit)
                this.userInfo.set('formTitle', 'editFieldWorker');
            else
                this.userInfo.set('formTitle', 'createNewFieldWorker');

            var renderedTemplate = this.template({userInfo: this.userInfo});
            this.$el.html(renderedTemplate);
        },
        validateFirstname: function(){
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
        onSubmit: function (e) {
            e.preventDefault();
            var isValidForm = true;
            $('#emailHelp').hide();
            $('#phoneHelp').hide();
            if($.trim($('#firstName').val()).length === 0){
	    		 
	    		 
	    		 isValidForm = false;
	    	}else{
	    		$('#nameRequired').hide();
	    		if((/[^a-zA-Z0-9]/.test($('#firstName').val()))){
	    			isValidForm = false;
	    			
	    		}
	    	}
			 
			if($.trim($('#lastName').val()).length === 0){
	    		
	    		 isValidForm = false;
	    	}else{
	    		
	    		if((/[^a-zA-Z0-9]/.test($('#lastName').val()))){
	    			isValidForm = true;
	    			
	    		}else{
	    			
	    		}
	    	}
			///^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i
			
			if($.trim($('#email').val()).length === 0){
	    		
	    		 isValidForm = false;
	    	}else{
	    		
	    		if(!(/^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i.test($('#email').val()))){
	    			isValidForm = false;
	    		}
	    	}
			
			if($.trim($('#phone').val()).length === 0){
	    		
	    		 isValidForm = true;
	    	}else{
	    		
	    		if(!(/^\d*[0-9](|.\d*[0-9]|,\d*[0-9])?$/.test($('#phone').val()))){
	    			isValidForm = false;
	    			
	    		}
	    	}
            
            
            if (isValidForm) {

                var userInfo;
                if (this.isEdit) {
                    userInfo = this.userInfo;
                    
                    var email=this.$("#email").val();
                    var phone=this.$("#phone").val();
                    
                    if(userInfo.get('email') != email){
                    	
                    	this.usersEmails.each(function (model) {
                    		if (model.get("email").toLowerCase() == email.toLowerCase()) {
                    			isValidForm = false;
                                $('#emailHelp').show();
                    		}
                    		
                    	});
                    	
                    }
                    
                    if(userInfo.get('phone') != phone && phone !== ''){
                    	
                    	this.usersEmails.each(function (model) {
                    		
                    		 if (model.get("phone") == phone) {
                                isValidForm = false;
                                $('#phoneHelp').show();
                            }
                    	});
                   
                    }
                    
                    if(isValidForm){
                    	userInfo.set({
                            firstName: this.$("#firstName").val(),
                            lastName: this.$("#lastName").val(),
                            email: this.$("#email").val(),
                            phone: this.$("#phone").val(),
                            code:1
                        });
                    }
                    
                    
                } else {
                    userInfo = new UserInfo();
                    // var nextId = UserInfos.at(UserInfos.length - 1).get('id') + 1;
                    userInfo.set({
                        //id:nextId,
                        firstName: this.$("#firstName").val(),
                        lastName: this.$("#lastName").val(),
                        email: this.$("#email").val(),
                        phone: this.$("#phone").val(),
                        stepsCompleted: "0/0",
                        stepsCompletedAverage: "0",
                        score: 0,
                        linkedToTeam: true,
                        code:0
                    });
                    
                    this.usersEmails.each(function (model) {
                		if (model.get("email").toLowerCase() == userInfo.get("email").toLowerCase()) {
                			isValidForm = false;
                            $('#emailHelp').show();
                		}
                		if (model.get("phone") !== '' && model.get("phone") == userInfo.get("phone")) {
                            isValidForm = false;
                            $('#phoneHelp').show();
                        }
                	});
                    
                    
                }
                if(isValidForm){
                	var context = this;
                    userInfo.save(null, {
                        success: function (newUserInfo) {
                        	context.userCollection.add(newUserInfo);
                        	context.userCollection.fetch({reset:true});
                        	context.showNotification(newUserInfo.get("idTypeMessage"), newUserInfo.get("code"));
                        },
                        error: (function (data, e) {
                        	var error = e.responseJSON;
                        switch (error.status) {
                            case 401:
                            	context.showNotification(3, polyglot.t("requestLogIn"));
                                break;
                            default:
                            	context.showNotification(3, "Error in the service " + error);
                        }
                        })
                    });
                    this.addUserModal.modal('hide');
                	
                }
                
            }
            
            
            


        },
        cancel: function () {
            this.addUserModal.modal('hide');
        },
        showNotification: function (message, code) {
            NotificationHelper.showNotification(message,code);
        }
    });
    return AddUserForm;
});