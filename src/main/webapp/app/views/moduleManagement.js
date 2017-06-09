/**
 * Author Bryan Barrantes
 * Handles view of the module management
 *
 *
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../templates/module-management-template.html',
    'collections/modulesInfo',
    'views/moduleSteps',
    'middleware/notification',
    'bootbox'
], function ($, _, Backbone, ModuleManagementTemplate, ModulesInfo, ModuleStepsView, NotificationHelper, Bootbox) {
    var ModuleManagement = Backbone.View.extend({
        initialize: function () {
            this.initPrototypeViews();
            this.template = _.template(ModuleManagementTemplate);
            //collection contains modules list
            this.collection = new ModulesInfo();
            this.collection.bind("reset", this.render, this);
            this.collection.fetch({reset: true});
            this.render();
        },
        render: function () {
            var renderedTemplate = this.template({
                modulesInfo: this.collection.toJSON(),
                statusClass: this.statusClass
            });
            this.$el.html(renderedTemplate);
            this.$el.find('[data-toggle="popover"]').popover();
        },
        statusClass: function (module) {
            switch (module.status) {
                case 'completed':
                    return 'panel-success';
                case 'in-progress':
                    return 'panel-info';
                case 'completed-failed':
                    return 'panel-warning';
                case 'not-started':
                default:
                    return 'panel-default';
            }
        },
        initPrototypeViews: function () {
            function Views() {};
            Views.prototype.moduleStepsView;
            this.views = new Views();
        },
        showStepsView: function (event) {
            event.stopPropagation();

            var $element = $(event.currentTarget);
            var id = $element.attr("data-id");
            var module = this.collection.get(id);
            var steps = module ? module.get("steps") : [];

            if (!this.views.moduleStepsView) {
                this.views.moduleStepsView = new ModuleStepsView({
                    el: $("#stepsModal"),
                    steps: steps
                });
            } else {
            	this.views.moduleStepsView.setElement($('#stepsModal'));
                this.views.moduleStepsView.initialize({steps: steps});
            }


        },
        removeModule: function (event) {
        	event.stopPropagation();
        	console.log('removeModule');
            event.preventDefault();
			var context=this;
			var modelModule=null;
			
			var id=$(event.currentTarget).attr("data-id");
			modelModule=context.collection.get(id);
			if(modelModule.attributes.status == 'in-progress') {
				Bootbox.confirm(polyglot.t('removeModuleInProgress'), function(result) {
					if(result===true)
				 	{
						 modelModule.destroy(
							 {
								    success : function (oldModuleInfo) { 
								    	context.collection.fetch({reset:true});
								    }
								}		 
						 );
					}
				});
			} else {
				Bootbox.confirm(polyglot.t('removeModuleNotStarted'), function(result) {
					if(result===true)
				 	{
						 modelModule.destroy(
							 {
								    success : function (oldModuleInfo) { 
								    	context.collection.fetch({reset:true});
								    }
								}		 
						 );
					}
				});
			}
        },
        submitUploadFile: function (event) {
            event.preventDefault();
            var form = event.currentTarget;
            var context=this;           
            var obj = {
                url: form.action,
                type: form.method,
                data: new FormData(form),
                async: false,
                cache: false,
                processData: false,
                contentType: false
            };
            
            $.ajax(obj).done(function (e, xhr, options) {
                NotificationHelper.showNotification(1, "uploadSuccess");
                context.collection.fetch({reset: true});
            }).fail(function (error) {
            	if(error.responseText != ""){
            		NotificationHelper.showNotification(3, "fileSizeExceeded");
            	}
                NotificationHelper.showNotification(3, error.responseJSON.message);
            });


        },
        events: {
            'click #stepsInfoModule': 'showStepsView',
            'click #unlinkModule': 'removeModule',
            'submit #fileUploadForm': 'submitUploadFile'
        }
    });
    return ModuleManagement;
});
