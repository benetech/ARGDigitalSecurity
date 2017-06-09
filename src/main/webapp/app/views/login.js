/**
 * Define Require module with dependencies
 */
define([
  'bootstrap',
  'underscore',
  'backbone',
  'models/login',
  'text!../templates/login-template.html',
  'middleware/notification',
  'bootbox'
], function ($, _, Backbone, LoginStatus,LoginTemplate,NotificationHelper,Bootbox) {

  /**
   * Login view which represents the login popup
   */
  var LoginView = Backbone.View.extend({
    // Wired on the login modal
    el:'#digitalSecurityTrainingApp',
    // Listen view events on modal buttons
    events:{
      'click .modal-footer .btn:not(.btn-primary)':'cancel',
      'click .modal-footer .btn.btn-primary':'ok',
      'blur #username': 'validateLogin',
      'blur #password': 'validatePassword'
    },
    initialize:function (callback) {
      this.render();
      this.callback = callback;
      $("a.logout").click(this.logout);
      this.loginModal=$("#loginModal");
      LoginStatus.on('change:loggedIn', this.loggedInChange, this);
      LoginStatus.fetch();
    },
    render: function(){
		 var template = _.template( LoginTemplate, {} );
		 this.$el.html( template );
		 console.log('render login');
	},
    loggedInChange:function () {
      if (LoginStatus.get('loggedIn')) {
        this.loginModal.modal('hide');
        $('.modal-backdrop').remove();
        if (this.callback) {
          this.callback();
        }
      } else {
        this.$("form input").val(null);

        //this.$el.modal('show');
        ///var error = new Error({code:LoginStatus.get('code'), message:LoginStatus.get('message')});
        //this.alertView  = new Alert({model:error});
        //this.alertView.render();
        ///$(".alert-message").alert();

        this.loginModal.modal('show');

      }
    },
    validateLogin:function(){
    	var validLogin=true;
    	if($.trim($('#username').val()).length===0){
   		 $('#loginRequired').show();
   		$('#username').val("");
   		 validLogin=false;
   	}else{
   		$('#loginRequired').hide();
   		if((/[^a-zA-Z0-9]/.test($('#username').val()))){
   			validLogin=false;
   			$('#loginInvalid').show();
   		}else{
   			$('#loginInvalid').hide();
   		}
   	}
   
    }
    ,
    validatePassword:function(){
    	if($.trim($('#password').val()).length===0){
       		$('#passwordRequired').show();
       		$('#password').val("");
      		 	validLogin=false;
       	}else{
       		$('#passwordRequired').hide();
       	}
    },
    ok:function () {
    	var validLogin=true;
    	
    	if($.trim($('#username').val()).length===0){
    		 
    		 validLogin=false;
    	}else{
    		
    		if((/[^a-zA-Z0-9]/.test($('#username').val()))){
    			validLogin=false;
    			
    		}
    	}
    	if($.trim($('#password').val()).length===0){
    		
   		 	validLogin=false;
    	}else{
    		
    	}
    	
    	
     if(validLogin){	
	      LoginStatus.set({
	        username:this.$("#username").val(),
	        password:this.$("#password").val(),
	        rememberMe:this.$("#rememberMe:checked").length > 0,
	      });
	      var context=this;
	      LoginStatus.save(null,{
			    success : function (login) { 
			    	
			    	LoginStatus.set({idTypeMessage:login.get("idTypeMessage"), 
			    					code: login.get("code")
			    	});
			    	context.showNotification(login.get("idTypeMessage"),login.get("code"));
			    }
	      });
     }
    },
    cancel:function () {
      this.loginModal.modal('hide');
    },
    logout:function () {
    	Bootbox.confirm(polyglot.t('logoutMessage'), function(result) {
			 if(result===true)
			 {
				 LoginStatus.destroy();
			      LoginStatus.set({
			        loggedIn:false
			      });
			 }
		 });
      
    },
    showError: function (message){
    	return "<div class='validation' style='color:red;margin-bottom: 20px;'>"+message+"</div>";
    },
    showNotification: function (message, code) {
        NotificationHelper.showNotification(message,code);
    }
  });

  // Return the view as the Require module
  return LoginView;

});
