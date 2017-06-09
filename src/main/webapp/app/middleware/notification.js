/**
 * Author Andres Oviedo
 * 
 * 
 * Notification Message manage 
 * 
 */
define([
  'jquery',
  'views/notification',
  'models/notification' 
], function ($,NotificationView,Notification) {
	
	NotificationProto.prototype.view;
	var notificationProto=new NotificationProto();
	var notification=new Notification();
	
	var showNotification=function(idTypeMessage,code){
		if(code && code!=='' ){
			notification.set('code',code);
			notification.set('idTypeMessage',idTypeMessage);
			notificationProto.view=new NotificationView({el: $("#notification"),notification:notification});
			setTimeout(function() { $(".alert-success").alert('close'); }, 2000);
			setTimeout(function() { $(".alert-danger").alert('close'); }, 2000);
		}
	}
	function NotificationProto(){}
	
	return {
		showNotification:showNotification
	};

});


