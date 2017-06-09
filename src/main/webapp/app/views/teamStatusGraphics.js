/**
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/team-status-graphics.html',
  //'collections/userInfos',
  //'views/userInfo',
  'bootbox',
  'chartist',
  'multiselect',
  'collections/modulesInfo',
  'collections/userInfos',
  'collections/usersModules',
  'collections/teamStatus',
  'models/teamStatusModel',
  'chartistPlugin'
  //'views/addUser'
], function ($, _, Backbone,TeamStatusGraphicsTemplate,Bootbox,Chartist,MultiSelect,ModulesInfo,UserInfos,UsersModules,chartistPlugin) {
	var TeamStatusGraphic = Backbone.View.extend({
		initialize: function(options){
			this.usersInfo=options.usersInfos;
			this.commonErrors=options.commonErrors;
			this.score=options.score;
			this.stagesCompleted=options.stagesCompleted;
			this.stagesComplement=	options.stagesComplement;
			this.userStepsScore=options.userStepsScore;
			
			
					
			this.template=_.template( TeamStatusGraphicsTemplate);
			this.render();
		},
		render: function(){
			
					
			var renderedTemplate = this.template({usersInfo: this.usersInfo,commonErrors:this.commonErrors,
				score:this.score,stagesCompleted:this.stagesCompleted,stagesComplement: this.stagesComplement,
				userStepsScore:this.userStepsScore
			});
			 
			 this.$el.html(renderedTemplate);
		}
	});
	 return TeamStatusGraphic;
});