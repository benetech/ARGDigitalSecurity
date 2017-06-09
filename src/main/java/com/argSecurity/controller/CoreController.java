/**
 * Controller for the steps workflows
 * @author Bryan Barrantes
 *  
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import com.argSecurity.model.AuditTrail;
import com.argSecurity.model.Condition;
import com.argSecurity.model.Message;
import com.argSecurity.model.Module;
import com.argSecurity.model.Parameter;
import com.argSecurity.model.Step;
import com.argSecurity.model.TracedAction;
import com.argSecurity.model.UserCondition;
import com.argSecurity.model.UserStep;
import com.argSecurity.module.actions.SMSDefaultConfiguration;
import com.argSecurity.module.actions.SMSHandler;
import com.argSecurity.module.conditions.ConditionExecutor;
import com.argSecurity.module.steps.StepExecutor;
import com.argSecurity.service.impl.AuditTrailServiceImpl;
import com.argSecurity.service.impl.ConditionServiceImpl;
import com.argSecurity.service.impl.ModuleServiceImpl;
import com.argSecurity.service.impl.ParameterServiceImpl;
import com.argSecurity.service.impl.StepServiceImpl;
import com.argSecurity.service.impl.TracedActionServiceImpl;
import com.argSecurity.service.impl.UserConditionServiceImpl;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserServiceImpl;
import com.argSecurity.service.impl.UserStepServiceImpl;

@Controller
public class CoreController {
	
	@Autowired
	StepExecutor stepExecutor;
	
	@Autowired
	ConditionExecutor conditionExecutor;
	
	@Autowired
	ModuleServiceImpl moduleService;
	
	@Autowired
	StepServiceImpl stepService;
	
	@Autowired
	ConditionServiceImpl conditionService;
	
	@Autowired
	ParameterServiceImpl parameterService;
	
	@Autowired
	UserStepServiceImpl userStepService;
	
	@Autowired
	UserConditionServiceImpl userConditionService;
	
	@Autowired
	TracedActionServiceImpl tracedActionService;
	
	@Autowired
	AuditTrailServiceImpl auditTrailService;
	
	@Autowired
	UserModuleServiceImpl userModuleServices;
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${base.url}")
	private String SERVER_URL;
	
	@Autowired
	SMSDefaultConfiguration smsDefaultConfiguration;
	
	@Autowired
	SMSHandler smsHandler;
	
	private final Logger log = LoggerFactory.getLogger(CoreController.class);
	
	public List<String> errors = new ArrayList<String>(); 
	

	/**
	 * Main function called by cron job to start corresponding modules
	 * and continue the flow for current, and put to an end in cases
	 * where corresponds. 
	 */
	public void coreValidations() {
		
		updateTracedActions();
		
		validateModules();
		
		if(!errors.isEmpty()) {
			for(String error: errors) {
				System.out.println(error);
			}
		}
	}
	
	/**
	 * Method to go over traced actions, and update userSteps to users that 
	 * failed a given step
	 * 
	 * Table traced_actions holds references to user and step, which allows
	 * to update userSteps
	 */
	public void updateTracedActions() {
		java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		List<TracedAction> actions = tracedActionService.getUnprocessedActiveTracedActions();
		try {
			
			for(TracedAction action: actions) {
				int userId = action.getUserId();
				int moduleId = action.getModuleId();
				int stepId = 0;
				//to do validar q el modulo este activo bug 197
				
				List<Step> steps = stepService.getStepsByModule(moduleId);
				for(Step step : steps) {
					if(action.getAction().toLowerCase().equals(step.getName().toLowerCase())) {
						stepId = step.getId();
						break;
					}
				}
				
				if(stepId > 0) {
					List<UserStep> userStep = userStepService.getActiveUserStepByUserIdAndStepId(userId, stepId);
					
					if(userStep != null && userStep.size() > 0){
						userStep.get(0).setStatus("failed");
						userStep.get(0).setModifiedDate(today);
						userStep.get(0).setModifiedBy("application");
						userStepService.save(userStep.get(0));
						
						saveAuditTrailAction("Step", userStep.get(0).getStepId(), "User Failed Step", "failed", "application");	
						
						List<UserCondition> userConditions = userConditionService.getActiveConditionsByUserIdAndStepId(userId, stepId, true);
						for(UserCondition condition: userConditions) {
							condition.setStatus("completed");
							condition.setModifiedBy("User failed step");
							condition.setModifiedDate(today);
							userConditionService.save(condition);
						}
						
						action.setProcessed(true);
						action.setModifiedDate(today);
						action.setModifiedBy("application");
						tracedActionService.save(actions.get(0));
					}
				} else {
					log.error(String.format("Problem with method %s", "updateTracedActions"), "Action Registered not found");
				}
			}
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "updateTracedActions"), e);
		}
	}
	

	/**
	 * Modules validations
	 * 
	 * Loop through unfinished modules, review if today equals start_date 
	 * and then set status in-progress. If already in-progress start with
	 * step validations. Steps will return 1 in case this module needs to
	 * be set to completed
	 */
	public void validateModules(){
		try {
			List<Module> unfinished = moduleService.getUnfinishedModules();
			
			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			
			for(Module unfinishedModule: unfinished) {
				if(unfinishedModule.getStartDate().toLocalDate().compareTo(today.toLocalDate()) == 0){
					//validar si tiene usuarios
					//status completed failed
					//record audit trail con error
					if(userModuleServices.getUsersModule(unfinishedModule.getId(), true) > 0){
						saveModuleStatus(unfinishedModule, "in-progress");
					}else{
						saveModuleStatus(unfinishedModule, "completed-failed");
						saveAuditTrailAction("Module Failed", unfinishedModule.getId(), "Module Completed Failed", "failed", "application");
						
					}
				}
				if(userModuleServices.getUsersModule(unfinishedModule.getId(), true) > 0){
					int endsToday = validateSteps(unfinishedModule);
//					if(endsToday == 1) {
//						saveModuleStatus(unfinishedModule, "completed");
//					}	
				}
			}
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "validateModules"), e);
		}
	}
	
	/**
	 * Step validations
	 * 
	 * Get all steps for given module, if completed keep moving until current
	 * step in-progress is found. if all completed set notification to module
	 * to be set as completed. 
	 * 
	 * Steps not done will be:
	 * Not started: need to be set in-progress and call execute their method
	 * In Progress: need to call condition validations, to attempt retries or
	 * set flags to system, for example. 
	 * 
	 * @param module
	 * @return 1 if all steps are completed, 0 otherwise
	 */
	public int validateSteps(Module module) {
		try {
			int response = 0;
			
			// if the module hasnt started yet
			if(module.getStatus().equalsIgnoreCase("not-started")){
				return response;	
			}
			
			List<Step> steps = stepService.getStepsByModule(module.getId());
			
			updateCompletedSteps(steps);
			
			steps = getUnfinishedSteps(steps);

			
			for(int i = 0; i < steps.size(); i++){
				
				if ((i == 0) || ((i>0) && (steps.get(i-1).getStatus().equalsIgnoreCase("completed"))) ) {// if is is 1st step
					if(steps.get(i).getStatus().equalsIgnoreCase("not-started")) {
						saveStepStatus(steps.get(i), "in-progress");
						executeStep(steps.get(i)); //also set in-progress all user-steps linked to step
						
						break;
					} else { //step is in-progress
						boolean stepCompleted = validateStepConditions(steps.get(i), module);
						if(stepCompleted) {
							saveStepStatus(steps.get(i), "completed");
							updateUsersPassedStep(steps.get(i).getId());
							if(steps.size() == 1){ // is the last step and it is completed...the module must be completed too
								saveModuleStatus(module, "completed");
							}
						} else {
							validateConditions(steps.get(i), module);//check if execution applies
						}
					}
				}					
			}
			return response;
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "validateSteps"), e);
			errors.add(e.getMessage());
			return -1;
		}
	}

	
	/**
	 * Returns true if all conditions for a step are completed.
	 * @param step
	 * @param module
	 * @return
	 */
	public boolean validateStepConditions(Step step, Module module){
		List<Condition> conditions = conditionService.getConditionsByStep(step.getId());
		for(int i = 0; i < conditions.size(); i++) {
			List<UserCondition> userConditions = userConditionService.getActiveConditionsByStepIdAndConditionId(step.getId(), conditions.get(i).getId());
			for(int j = 0; j < userConditions.size(); j++) {
				if(!(userConditions.get(j).getStatus().equalsIgnoreCase("completed")))
					return false;
			}
		}
		return true;
	}

	
	/**
	 * Go through all steps in module, and for each userStep passed or failed, 
	 * set the corresponding userConditions to complete
	 * @param module
	 */
	private void cleanConditions(Module module) {
		List<Step> steps = stepService.getIncompleteStepsByModule(module.getId());
		for(Step step: steps) {
			List<UserStep> userSteps = userStepService.getActiveUserStepsByStepIdCompleted(step.getId(), true);
			for(UserStep userStep: userSteps) {
				List<UserCondition> userConditions = userConditionService.getActiveConditionsByUserIdAndStepId(userStep.getUserId(), step.getId(), true);
				for(UserCondition condition: userConditions) {
					if(!(condition.getStatus().equalsIgnoreCase("completed"))) {
						saveUserConditionStatus(condition, "completed");
					}
				}
			}
		}
	}
	

	/**
	 * Conditions validations
	 * 
	 * I know there are incomplete userConditions, so try to close them here
	 * 
	 * Check if conditions for a given step apply to execute, to know that,
	 * it gets: 
	 * 1-all conditions for the given step
	 * 2-all userSteps for the given step
	 * 	if userStep is passed || failed? do nothing 
	 * 	else {
	 * 		review if today must be executed
	 * 
	 * 	}
	 * 
	 * @param step
	 * @param module
	 */
	public void validateConditions(Step step, Module module) {
		java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		try {
			cleanConditions(module);
			
			List<UserStep> userSteps = userStepService.getActiveUserStepsByStepIdInProgress(step.getId(), true);
			
			for(UserStep userStep: userSteps) {
				List<UserCondition> incompleteConditions = userConditionService.getIncompleteActiveConditionsByUserIdAndStepId(userStep.getUserId(), userStep.getStepId());
				if(userStep.getStatus().equalsIgnoreCase("in-progress")){
				if(incompleteConditions.isEmpty()) {//if 0 then set userSteps to passed
					saveUserStepStatus(userStep, "passed");//set User Step to passed
				} else {
					for(UserCondition condition: incompleteConditions) {
						Condition currentCondition = conditionService.findOne(condition.getConditionId());
						
						Date top=module.getStartDate();
						top.setDate(top.getDate()+condition.getTimer());
						if(condition.getPendingRetries() == 0  && (top.compareTo(today)<=0)) {/// dias del timer del condition de cada step
							//if(days left 0){ }// PARA Q ESTE COMPLETADO TIENE Q CUMPLIRSE LOS DIAS
							//NO TIENE Q TENER RETRIES
							saveUserConditionStatus(condition, "completed");
						} else {
							//DEBE TENER AL MENOS UN RETRIE
							
							
							String[] timerTxt = currentCondition.getTimer().split(" ");
							int timer = Integer.valueOf(timerTxt[0]); 
							int pendingRetries = condition.getPendingRetries();
							int currentTimer = condition.getTimer(); 
							if(today.toLocalDate().compareTo(module.getStartDate().toLocalDate()) == currentTimer) { 
								executeCondition(currentCondition, step, userStep);
								saveUserConditionRetriesAndTimer(condition, pendingRetries-1, currentTimer+timer);
							}
						}
					}
				}//end if
				}
			}
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "validateConditions"), e);
		}
	}
	
	/**
	 * 
	 * @param module
	 * @param status
	 */
	private void saveModuleStatus(Module module, String status) {
		try {
			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			module.setModifiedDate(today);
			module.setModifiedBy("application");
			module.setStatus(status);
			moduleService.save(module);
			
			saveAuditTrailAction("Module", module.getId(), "Module updated", status, "application");
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "saveModuleStatus"), e);
			errors.add(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param step
	 * @param status
	 */
	private void saveStepStatus(Step step, String status) {
		try {
			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			step.setModifiedDate(today);
			step.setModifiedBy("application");
			step.setStatus(status);
			stepService.save(step);
			
			saveAuditTrailAction("Step", step.getId(), "Step updated", status, "application");
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "saveStepStatus"), e);
			errors.add(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param userStep
	 * @param status
	 */
	private void saveUserStepStatus(UserStep userStep, String status) {
		try {
			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			userStep.setModifiedDate(today);
			userStep.setModifiedBy("application");
			userStep.setStatus(status);
			userStepService.save(userStep);
			
			saveAuditTrailAction("UserStep", userStep.getId(), "UserStep updated", status, "application");
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "saveUserStepStatus"), e);
			errors.add(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param userCondition
	 * @param status
	 */
	private void saveUserConditionStatus(UserCondition userCondition, String status) {
		try {
			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			userCondition.setModifiedDate(today);
			userCondition.setModifiedBy("application");
			userCondition.setStatus(status);
			userConditionService.save(userCondition);
			
			saveAuditTrailAction("UserCondition", userCondition.getId(), "UserCondition updated", status, "application");
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "saveConditionStatus"), e);
			errors.add(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param userCondition
	 * @param newRetries
	 * @param newTimer
	 */
	private void saveUserConditionRetriesAndTimer(UserCondition userCondition, int newRetries, int newTimer) {
		try {
			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			userCondition.setModifiedDate(today);
			userCondition.setModifiedBy("application");
			userCondition.setPendingRetries(newRetries);
			userCondition.setTimer(newTimer);
			userConditionService.save(userCondition);
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "saveUserConditionRetries"), e);
			errors.add(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param stepId
	 */
	private void updateUsersPassedStep(int stepId) {
		try {
			List<UserStep> userSteps = userStepService.getActiveUserStepsByStepIdNotFailed(stepId, true);
			for(UserStep step: userSteps) {
				UserStep userModel = step;
				java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				userModel.setModifiedDate(today);
				userModel.setModifiedBy("application");
				userModel.setStatus("passed");
				userStepService.save(userModel);
				
				saveAuditTrailAction("UserStep", userModel.getUserId(), "UserStep passed", "passed", "application");
			}
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "updateUsersPassedStep"), e);
			errors.add(e.getMessage());
		}
		
	}
	
	
	/**
	 * This function executes and step, and return status message
	 * 
	 * Also will go and update status for for user_steps associated
	 * with given step.
	 * 
	 * @param step
	 * @return
	 */
	private String executeStep(Step step) {
		String response = "";
		try {
			List<Parameter> params = getParameters(step);
			Map<String, Object> context = createContext();
			Message resp = stepExecutor.executeMethod(step, params, context);
			if(resp.getCode() != 0) {
				response = resp.getMessage();
				errors.add(response);
			} else {
				saveAuditTrailAction("Step", step.getId(), "Step executed", "in-progress", "application");
				
				List<UserStep> userSteps = userStepService.getActiveUserStepsByStepIdNotFailed(step.getId(), true);
				for(UserStep userStep: userSteps) {
					saveUserStepStatus(userStep, "in-progress");
					saveAuditTrailAction("UserStep", userStep.getId(), "UserStep started", "in-progress", "application");
				}
			}
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "executeStep"), e);
			errors.add(e.getMessage());
		}
		return response;
	}
	

	/**
	 * Method to execute a condition step for a single user, could be a 
	 * retry, raise a flag, do something on database, etc. 
	 * 
	 * @param condition
	 * @param step
	 * @param userStep
	 * @return "" if success or an error
	 */
	private String executeCondition(Condition condition, Step step, UserStep userStep) {
		String response = "";
		try {
			Message resp = conditionExecutor.executeCondition(condition, step, userStep);
			if(resp.getCode() != 0) {
				response = resp.getMessage();
				errors.add(response);
			}
			saveAuditTrailAction("Condition", condition.getId(), "Condition executed", "in-progress", "application");
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "executeCondition"), e);
			errors.add(e.getMessage());
		}
		return response;
	}
	
	
	/**
	 * Brings all parameters associated to a step method, and pass this as a 
	 * string array to method for execution
	 * 
	 * @param step
	 * @return
	 */
	public List<Parameter> getParameters(Step step) {
		try {
			List<Parameter> params = parameterService.loadParameterByStepId(step.getId());
			return params;
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "getParameters"), e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param steps
	 * @return
	 */
	private List<Step> getUnfinishedSteps(List<Step> steps) {
		List<Step> unfinished = new ArrayList<Step>();
		for(Step step: steps) {
			if(!step.getStatus().equalsIgnoreCase("completed")) {
				unfinished.add(step);
			}
		}
		return unfinished;
	}
	
 
	/**
	 * Go through userSteps, and if all already passes || failed, complete the step
	 * @param steps
	 */
	private void updateCompletedSteps(List<Step> steps){
		for(Step step: steps) {
			List<UserStep> userSteps = userStepService.getActiveUserStepsByStepId(step.getId());
			for(UserStep userStep: userSteps) {
				if(!userStep.getStatus().equalsIgnoreCase("passed") && !userStep.getStatus().equalsIgnoreCase("failed")){
					return;
				}
			}
			saveStepStatus(step, "completed");
		}
	}
	
	private void saveAuditTrailAction(String objectType, int objectId, String action, String status, String createdBy) {
		try {
			Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			AuditTrail actionModel = new AuditTrail();
			actionModel.setObjectType(objectType);
			actionModel.setObjectId(objectId);
			actionModel.setAction(action);
			actionModel.setStatus(status);
			actionModel.setCreatedBy(createdBy);
			actionModel.setCreatedDate(currentTimestamp);
			auditTrailService.save(actionModel);
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "saveAuditTrailAction"), e);
		}
	}
	
	private Map<String, Object> createContext() {
		Map<String, Object> context = new HashMap<>();
		context.put("userModuleServices", userModuleServices);
		context.put("moduleService", moduleService);
		context.put("stepService", stepService);
		context.put("conditionService", conditionService);
		context.put("parameterService", parameterService);
		context.put("userStepService", userStepService);
		context.put("userConditionService", userConditionService);
		context.put("userModuleServices", userModuleServices);
		context.put("userService", userService);
		context.put("mailSender", mailSender);
		context.put("SERVER_URL", SERVER_URL);
		context.put("smsDefaultConfig", smsDefaultConfiguration);
		context.put("smsHandler", smsHandler);
		
		
		return context;
	}

}