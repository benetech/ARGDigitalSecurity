define([
  'jquery',  
  'polyglot'
], function ($,Polyglot) {
	var setLanguage=function(then){
		var language = localStorage.getItem('language') || 'en';
		$.getJSON('../languages/'+language+'.json', function(data) {
			window.polyglot = new Polyglot({phrases: data});
			
			if(then)then();
		});
	}
	
	var setupLanguage=function(then){
		if(!window.polyglot)
			setLanguage(then);
		else
			then();
	}
	
	return {
		setupLanguage:setupLanguage,
		setLanguage:setLanguage
	};

});
