/**
 * Authors Bryan Barantes
 * Daniela Depablos
 * 
 * View for the team status dashboard
 * 
 */
define([
  'jquery',
  'underscore',
  'backbone',
  'text!../templates/team-status-dashboard-template.html',
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
  'views/teamStatusGraphics'
], function ($, _, Backbone,TeamStatusDashboardTemplate,Bootbox,Chartist,MultiSelect,ModulesInfo,UserInfos,
		UsersModules,TeamStatus,TeamStatusModel,TeamStatusGraphicsView) {
	 var TeamStatusDashboard = Backbone.View.extend({
		 initialize: function(){
			 this.initPrototypeViews();
			 
			 this.modules = new ModulesInfo();
			 this.modules.bind("reset", this.render, this);
			 this.modules.fetch({reset: true});
			 
			 this.users=new UserInfos();
			 this.users.bind("reset", this.render, this);
			 this.users.fetch({reset: true});
			 
			 this.usersModules= new UsersModules();
			 this.usersModules.bind("reset", this.render, this);
			 this.idModule=0;
			 this.newTeamStatus= new TeamStatusModel();
			 this.on("changed:newTeamStatus", this.addGraphicView, this);
			 this.render();
			 
			 
			// this.addUserListView();
		 },
		 initPrototypeViews: function(){
			function Views(){}
			 Views.prototype.teamStatusGraphicsView;
			//Views.prototype.userInfoView;
			this.views=new Views();
		 },
		 events:{
			'click .dropdown-menu li':'onChangeLstModules',
			'change #lstUsers':'onChangeLstUsers',
			'click #calculate' : 'calculate'
			
		 },
		 onChangeLstModules: function(event){
			 console.log(event);
			 var id=$(event.currentTarget).attr("data-id");
			console.log($(event.currentTarget).attr("data-id"));
			this.idModule=id;
			$('#currentModuleTSD').text((this.modules._byId[id].attributes.name.length > 39?this.modules._byId[id].attributes.name.substr(0,20):this.modules._byId[id].attributes.name ));
			
//			 $('#lstUsers').prop('disabled', false);
//			  var usersModules = new UsersModules();
//			  usersModules.setId(id);
			 if(($('#lstUsers').val() != null) && $('#lstUsers').val().length >0 && (this.idModule != 0)){
				 $('#calculate').prop('disabled', false);
			 }else{
				 $('#calculate').prop('disabled', true);
				 
			 }
			 
		 },
		 onChangeLstUsers: function(event){
			 console.log(event);
			 if(($('#lstUsers').val() != null) && $('#lstUsers').val().length >0 && (this.idModule != 0)){
				 $('#calculate').prop('disabled', false);
			 }else{
				 $('#calculate').prop('disabled', true);
				 
			 }
			 
			 $("#lstUsers").multiselect("refresh");
			 
		 },
		 calculate: function(){
			 var context=this;
			 var teamStatus = new TeamStatusModel();
			 
			 teamStatus.set({modules :[{id:this.idModule}]});
			 
			 var foo = []; 
			 $('#lstUsers :selected').each(function(i, selected){ 
			   foo[i] = {id:$(selected).attr("data-id"),firstname:$(selected).text()}; 
			   console.log(" i -> "+i);
			   console.log(" selected -> "+foo[i]);
			 });
			 this.users=foo;
			 teamStatus.set({users :this.users});
			 teamStatus.save(null,{
				    success : function (newTeamStatus) { 
				    	 context.newTeamStatus=newTeamStatus;
				    	 context.trigger("changed:newTeamStatus");
				    }
				});
			 
		 },
		 addGraphicView: function(){
			 console.log("change on the model");
			 this.usersScores = this.newTeamStatus.get('users');
			 this.commonErrors = this.newTeamStatus.get('commonErrors');
			 this.score = this.newTeamStatus.get('score');
			 this.stagesCompleted=this.newTeamStatus.get('stagesCompleted');
			 this.stagesComplement=this.newTeamStatus.get('stagesComplement');
			 this.userStepsScore=this.newTeamStatus.get('userStepsScore');
			 if(!this.views.teamStatusGraphicsView)
				 this.views.teamStatusGraphicsView = new TeamStatusGraphicsView({el: $("#teamStatusGraphic") , 
				 usersInfos: this.usersScores, commonErrors:this.commonErrors, score:this.score, stagesCompleted:this.stagesCompleted,
				 stagesComplement: this.stagesComplement,userStepsScore:this.userStepsScore});
			 else
				 this.views.teamStatusGraphicsView.initialize({usersInfos: this.usersScores, commonErrors:this.commonErrors,
					 score:this.score, stagesCompleted:this.stagesCompleted,stagesComplement: this.stagesComplement,
					 userStepsScore:this.userStepsScore});
		 },
		 render: function(){
			 var template = _.template( TeamStatusDashboardTemplate, {
				 modulesInfo:this.modules.toJSON(),
				 usersInfo:this.users.toJSON()} );
			 this.$el.html( template );
		 }
	 });
	 return TeamStatusDashboard;
});
