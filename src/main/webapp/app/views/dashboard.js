define([
    'jquery',
    'underscore',
    'backbone',
    'models/login',
    'text!../templates/dashboard-template.html',
    'routers/dashboard',
    'bootbox',
	'views/changePasswordForm'
], function ($, _, Backbone, LoginStatus, DashboardTemplate, DashboardRouter,Bootbox,ChangePasswordFormView) {
    var Dashboard = Backbone.View.extend({
        initialize: function (callback) {
			this.initPrototypeViews();
            this.render();
        },
		
		initPrototypeViews: function(){
			function Views() {
			}
			Views.prototype.changePasswordFormView;
			this.views=new Views();
		},
		
		
		
        render: function () {
            var template = _.template(DashboardTemplate, {LoginStatus: LoginStatus});
            this.$el.html(template);
            if (Backbone.currentView) {
                Backbone.currentView.setElement(Backbone.mainContentSelector);
                Backbone.currentView.initialize();
            }
        },
        events: {
            'click #logout-btn': 'logout',
			'click #btnChngPwd': 'changePassword'
            
        },
        setup: function () {
        	$('a').tooltip();
            var windowHeight, navHeight, mainHeight;

            setDashboardHeight();
            setOnWindowChanges();
            setDashboardNavStyle();
            
            
            function setOnWindowChanges() {
                $(window).resize(function () {
                    setDashboardHeight();
                });

                $(window).scroll(function () {
                    var mainHeight = $('.main').height();
                    setDashboardHeight(mainHeight);
                });
            }

            function setDashboardHeight(newMainHeight) {
                windowHeight = $(window).height();
                navHeight = $('.nav').height() + 50;
                mainHeight = (newMainHeight >= 0) ? newMainHeight : $('.main').height();

                if (mainHeight > windowHeight) {
                    $('html').height(Math.max(navHeight, windowHeight, mainHeight) + 10);
                    $('body').height(Math.max(navHeight, windowHeight, mainHeight) + 10);
                }
                else {
                    $('html').height(Math.max(navHeight, windowHeight, mainHeight));
                    $('body').height(Math.max(navHeight, windowHeight, mainHeight));
                }
            }

            function setDashboardNavStyle() {
                var li = $('.navbar-nav li').click(function () {
                    li.removeClass('selected');
                    $(this).addClass('selected');
                });
            }


            //Default

            //new UserManagementView({ el: $("#dashboardContent") });
            //new TeamManagementView({ el: $("#dashboardContent") });
        },
        logout:function () {
        	Bootbox.confirm(polyglot.t('logoutMessage'), function(result) {
   			 if(result===true)
   			 {
   				 LoginStatus.destroy();
   			      LoginStatus.set({
   			        loggedIn:false
   			      });
   			   window.location.reload();
   			 }
   		 });
            
        },
		changePassword: function(event){
        	console.log("Event change Pwd");
        	if(!this.views.changePasswordFormView)
				this.changePasswordFormView=new ChangePasswordFormView({el: $("#msgPwd")});
        	
            else{
            	this.views.changePasswordFormView.setElement($('#msgPwd'));
            	this.views.changePasswordFormView.initialize({});
            }
            	
            	
			
        }
    });
    return Dashboard;
});
