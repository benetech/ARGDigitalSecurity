define([
        'jquery',
        'underscore',
        'backbone',
        'text!../templates/change-password-form-template.html',
        'middleware/notification',
        'models/login',
        'models/userPwdMng'
        ],function($, _, Backbone,ChangePasswordFormTemplate,NotificationHelper,LoginStatus, UserPwd){
	
	var ChangePaswordForm = Backbone.View.extend({
		initialize: function(){
			this.template=_.template( ChangePasswordFormTemplate);
			    
			this.render();
		},
		events:{
			'click #saveChng':'onSubmit',
			'click #cancelChng':'cancelChng',
			'click #closeChngPwd':'closeChng',
            'blur #oldPassword':'validatePasswordOld',
            'blur #newPassword':'validatePasswordNew',
            'blur #newPasswordConfirm':'validatePasswordConf'
		},
		validatePasswordOld: function(event){
		
				if ($.trim($('#oldPassword').val()).length===0) {
					$('#passwordRequiredOld').show();
					$('#oldPassword').val("");
					
				}else{
					$('#passwordRequiredOld').hide();
					if($('#newPassword').val()===$('#oldPassword').val()){
						$('#passwordEqualNew').show();
					}else{
						$('#passwordEqualNew').hide();
					}
					
				}
			
		},validatePasswordNew: function(event){
			
			if ($.trim($('#newPassword').val()).length===0) {
				$('#passwordRequiredNw').show();
				$('#newPassword').val("");
				$('#passwordInvalidNw').hide();
				$('#passwordEqualNew').hide();
				
			}else{
				$('#passwordRequiredNw').hide();
				
					if( /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,64}$/.test($('#newPassword').val())){
						$('#passwordInvalidNw').hide();
						if($('#newPassword').val()===$('#oldPassword').val()){
							$('#passwordEqualNew').show();
						}else{
							$('#passwordEqualNew').hide();
						}
						if (!($.trim($('#newPasswordConfirm').val()).length===0)){
							if(!($('#newPasswordConfirm').val()=== $('#newPassword').val())){
								isValidForm=false;
								$('#passwordNotEquals').show();
							}else{
								$('#passwordNotEquals').hide();
								
								
							}
							
						}
					}else{
						$('#passwordEqualNew').hide();
						$('#passwordInvalidNw').show();
						
					}
				
			}
		
	},validatePasswordConf: function(event){
		
		if ($.trim($('#newPasswordConfirm').val()).length===0) {
			$('#passwordRequiredConf').show();
			$('#newPasswordConfirm').val("");
			$('#passwordInvalidConf').hide();
			$('#passwordNotEquals').hide();
			
		}else{
			$('#passwordRequiredConf').hide();
			
			if (!($.trim($('#newPassword').val()).length===0)){
				if(!($('#newPassword').val()=== $('#newPasswordConfirm').val())){
					isValidForm=false;
					$('#passwordNotEquals').show();
					$('#passwordInvalidConf').hide();
				}else{
					$('#passwordNotEquals').hide();
					if( /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,64}$/.test($('#newPasswordConfirm').val())){
					$('#passwordInvalidConf').hide();
					
					if($('#newPasswordConfirm').val()===$('#oldPassword').val()){
						
						$('#passwordEqualConf').show();
					}else{
						$('#passwordEqualConf').hide();
					}
					
					}else{
						
						$('#passwordInvalidConf').show();
					}
					
				}
			}
			
			
			
				
			
		}
	
},
		render: function(){
		
			
			var renderedTemplate = this.template();
			this.$el.html(renderedTemplate);
			this.$el.addClass('modal fade');
	        this.$el.modal({});
		},
		onSubmit: function(e) {
			e.preventDefault();
			var isValidForm=true;
			
			if ($.trim($('#oldPassword').val()).length===0) {
				
				isValidForm=false;
			}else{

				
			}
			
			if ($.trim($('#newPassword').val()).length===0) {
				isValidForm=false;
				
			}else{
				$('#passwordRequiredNw').hide();
				if($('#newPassword').val()===$('#oldPassword').val()){
					
					isValidForm=false;
				}
					if( /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,64}$/.test($('#newPassword').val())){
						
					}else{
						
						isValidForm=false;
					}
				
			}
			if ($.trim($('#newPasswordConfirm').val()).length===0) {
				isValidForm=false;
				
			}else{
				$('#passwordRequiredConf').hide();
				
					if( /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,64}$/.test($('#newPasswordConfirm').val())){
						$('#passwordInvalidConf').hide();
						if($('#newPasswordConfirm').val()===$('#oldPassword').val()){
							
							isValidForm=false;
						}
						if (!($.trim($('#newPassword').val()).length===0)){
							if(!($('#newPassword').val()=== $('#newPasswordConfirm').val())){
								isValidForm=false;
							}
						}
					}else{
						
						isValidForm=false;
					}
				
			}
                
                
			
				
			
			
			
			if (isValidForm) {
				$('#passwordOldWrong').hide();
				var context = this;
				 this.userPwd = new UserPwd();
				 this.userPwd.set({
						//id:nextId,
						old: this.$("#oldPassword").val(),
						newPwd:this.$("#newPassword").val()
					});
				
				 this.userPwd.save(null, {
					success : function (newUserInfo) { 
						if(newUserInfo.get("idTypeMessage") ==1){
							context.showNotification(newUserInfo.get("idTypeMessage"),polyglot.t(newUserInfo.get("code")));
							context.close();
							context.$el.modal('hide');
						}else if (newUserInfo.get("idTypeMessage") ==3){
							$('#'+newUserInfo.get("code")).show();
						}
					}
				});
				 	

			}
			
			
			
		},
		cancelChng: function(event) {
			console.log("event Cancel change Pwd");
			 this.$el.modal('hide');
			 //this.close();
		},
		showNotification: function (message, code) {
	    	NotificationHelper.showNotification(message,code);
		},
        close: function() {
    	 	
      	  this.undelegateEvents();
      	 	
      },
      closeChng: function(event){
    	  this.$el.modal('hide');
      }
		
	});
	
	return ChangePaswordForm;
});