/**
 * UserStatusDashboardController allows to load data according to the user status dashboard
 * @author daniela.depablos
 *	Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.model.Condition;
import com.argSecurity.model.Message;
import com.argSecurity.model.Module;
import com.argSecurity.model.Step;
import com.argSecurity.model.User;
import com.argSecurity.model.UserCondition;
import com.argSecurity.model.UserModule;
import com.argSecurity.model.UserStep;
import com.argSecurity.service.impl.ConditionServiceImpl;
import com.argSecurity.service.impl.ModuleServiceImpl;
import com.argSecurity.service.impl.StepServiceImpl;
import com.argSecurity.service.impl.UserConditionServiceImpl;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserServiceImpl;
import com.argSecurity.service.impl.UserStepServiceImpl;
import com.argSecurity.utils.Constants;

@Secured({"admin","user_management"})
@Controller
@RequestMapping("/rest/usersStatus")
public class UserStatusDashboardController {

	
	@Autowired
	private UserModuleServiceImpl userModuleServices;
	
	@Autowired
	private ModuleServiceImpl moduleServices;
	
	@Autowired
	private StepServiceImpl stepServices;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private Date dateSystem;
	
	@Autowired
	private UserStepServiceImpl userStepServices;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private ConditionServiceImpl conditionServiceImpl;
	
	@Autowired
	private UserConditionServiceImpl userConditionServiceImpl;
	
	
	private final static Logger log = Logger.getLogger(UserStatusDashboardController.class);
	
	/**
	 * Allows to get a module by ID or a complete list of modules
	 * 
	 * returns a module or a list of modules
	 * @param request
	 * @param response
	 * @return
	 */
	 @RequestMapping(method = RequestMethod.GET)
	 @ResponseBody
	 public List<com.argSecurity.model.dto.UserModule> getUsersActive(HttpServletRequest request, HttpServletResponse response)throws IOException{
	 
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
		List<UserModule> listUserModules = userModuleServices.getIsActiveUserModules(true);
		 
		List<com.argSecurity.model.dto.UserModule> listUserModulesDTO = new ArrayList<com.argSecurity.model.dto.UserModule>();
		for(UserModule userModule : listUserModules){
			com.argSecurity.model.dto.UserModule userModuleDTO = 
					dozerMapper.map(userModule, com.argSecurity.model.dto.UserModule.class);
			Module module = moduleServices.findOne(userModule.getModuleId());
			userModuleDTO.setModuleName(module.getName());
			if(userModule.getCompletedDate()==null){
				userModuleDTO.setStatus("assigned");
			}else{
				userModuleDTO.setStatus("completed");
			}
			
			List<Step> steps = stepServices.getStepsByModuleActive(userModule.getModuleId(),true);
			List<UserStep> userSteps=userStepServices.getIsUserStepsAndIsActive(userModule.getUserId(), true);
			
			List<com.argSecurity.model.dto.Step> stepsDTO = new ArrayList<com.argSecurity.model.dto.Step>();
			int stepsModule=0;
			int stepsUser=0;
			for(Step step: steps){
				
				com.argSecurity.model.dto.Step stp=dozerMapper.map(step, com.argSecurity.model.dto.Step.class);
				
				
				for(UserStep usrStep:userSteps){
					
					if(usrStep.getStepId() == step.getId() && userModule.getUserId()==usrStep.getUserId()){
						stp.setStatus(usrStep.getStatus());
						if(
						   usrStep.getStatus().equalsIgnoreCase("passed") ||  
						   usrStep.getStatus().equalsIgnoreCase("completed")){
							stepsUser++;
							
						}
						
					}
					
					
				}
				stepsDTO.add(stp);
				
				stepsModule++;
				
			}
			if(stepsDTO.size() >0){
				userModuleDTO.setStepPerc((stepsUser*100)/stepsModule);
			}else{
				userModuleDTO.setStepPerc(0);
			}
			userModuleDTO.setSteps(stepsDTO);
			
			
			listUserModulesDTO.add(userModuleDTO);
		}
		return listUserModulesDTO;
	 }
	/**
	 * 
	 * @param request
	 * @param response
	 * @param userM
	 * @return
	 * @throws IOException 
	 */
	 @RequestMapping(method = RequestMethod.GET, path="/{id}")
	 @ResponseBody
	 public List<com.argSecurity.model.dto.UserModule> getActiveUserModules(HttpServletRequest request, HttpServletResponse response,
			 @PathVariable(value = "id")Integer id) throws IOException{
	 
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
		 User user=userServiceImpl.findById(id);
		 
		 List<UserModule> listUserModules =userModuleServices.getUserModulesActives(user, true);
		 
		List<com.argSecurity.model.dto.UserModule> listUserModulesDTO = new ArrayList<com.argSecurity.model.dto.UserModule>();
		for(UserModule userModule : listUserModules){
			com.argSecurity.model.dto.UserModule userModuleDTO = 
					dozerMapper.map(userModule, com.argSecurity.model.dto.UserModule.class);
			Module module = moduleServices.findOne(userModule.getModuleId());
			userModuleDTO.setModuleName(module.getName());
			if(userModule.getCompletedDate()==null){
				userModuleDTO.setStatus("assigned");
			}else{
				userModuleDTO.setStatus("completed");
			}
			
			List<Step> steps = stepServices.getStepsByModule(userModule.getModuleId());
			List<UserStep> userSteps=userStepServices.getIsUserStepsAndIsActive(userModule.getUserId(), true);
			
			List<com.argSecurity.model.dto.Step> stepsDTO = new ArrayList<com.argSecurity.model.dto.Step>();
			int stepsModule=0;
			int stepsUser=0;
			for(Step step: steps){
				
				com.argSecurity.model.dto.Step stp=dozerMapper.map(step, com.argSecurity.model.dto.Step.class);
				
				
				for(UserStep usrStep:userSteps){
					
					if(usrStep.getStepId() == step.getId() && userModule.getUserId()==usrStep.getUserId()){
						stp.setStatus(usrStep.getStatus());
						if(
						   usrStep.getStatus().equalsIgnoreCase("passed") ||  
						   usrStep.getStatus().equalsIgnoreCase("completed")){
							stepsUser++;
							
						}
						
					}
					
					
				}
				stepsDTO.add(stp);
				
				stepsModule++;
				
			}
			if(stepsDTO.size() >0){
				userModuleDTO.setStepPerc((stepsUser*100)/stepsModule);
			}else{
				userModuleDTO.setStepPerc(0);
			}
			userModuleDTO.setSteps(stepsDTO);
			
			
			listUserModulesDTO.add(userModuleDTO);
		}
		return listUserModulesDTO;
	 }
	 
	/**
	 *  Allows to save user module to Database
	 * 
	 * returns a message with result
	 * @param request
	 * @param response
	 * @param userM
	 * @return
	 * @throws IOException 
	 */
	 @RequestMapping(method = {RequestMethod.POST})
	 @ResponseBody 	
	public com.argSecurity.model.dto.UserModule saveUserModule(HttpServletRequest request, 
			
			HttpServletResponse response, 
			
			@RequestBody com.argSecurity.model.dto.UserModule userM
		  ) throws IOException
			{
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
		    
		 
		 	UserModule userModuleModel=null;
		 	
		 	userModuleModel = new UserModule();
			
		 	dateSystem.setTime(System.currentTimeMillis());
			
		 	userModuleModel.setUserId(userM.getUserId());
			
		 	userModuleModel.setModuleId(userM.getModuleId());
			
		 	userModuleModel.setActive(true);
			
		 	userModuleModel.setCreatedDate(dateSystem);
			
		 	userModuleModel.setCreatedBy(userlogin.getUsername());
			
		 	userM=dozerMapper.map(userModuleServices.save(userModuleModel), com.argSecurity.model.dto.UserModule.class);
		 	
		 	List<Step> steps = stepServices.getStepsByModule(userModuleModel.getModuleId());
		 	
		 	
		 	
		 	dateSystem.setTime(System.currentTimeMillis());
		 	
		 	for(Step step: steps){
		 		
		 		UserStep userStep = new UserStep();
		 		
		 		userStep.setActive(true);
		 		
		 		userStep.setCreatedBy(userlogin.getUsername());
		 		
		 		userStep.setCreatedDate(dateSystem);
		 		
		 		userStep.setStatus("not-started");
		 		
		 		userStep.setStepId(step.getId());
		 		
		 		userStep.setUserId(userM.getUserId());
		 		
		 		userStepServices.save(userStep);
		 		
		 		//Adding user steps conditions
		 		
		 		List<Condition> conditions = conditionServiceImpl.getConditionsByStep(step.getId());
		 		
		 		for(Condition condition: conditions){
		 			
		 			UserCondition userCondition = new UserCondition();
		 			
		 			userCondition.setActive(true);
		 			
		 			userCondition.setCreatedDate(dateSystem);
		 			
		 			userCondition.setUserId(userM.getUserId());
		 			
		 			userCondition.setConditionId(condition.getId());
		 			
		 			userCondition.setCreatedBy(userlogin.getUsername());
		 			
		 			userCondition.setStepId(step.getId());
		 			
		 			userCondition.setStatus("not-started");
		 			
		 			userCondition.setPendingRetries(condition.getRetries());
		 			
		 			userCondition.setTimer(Integer.parseInt((condition.getTimer().split(" ")) [0]));
		 			
		 			userConditionServiceImpl.save(userCondition);
		 			
		 		}
		 		
		 	}
		 		
		 	userM.setIdTypeMessage(1);
		 	
		 	userM.setCode("moduleAdd");
		 	
		 	
		return userM;
	}
	
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(path="/{id}", method = {RequestMethod.DELETE})
	@ResponseBody
	public com.argSecurity.model.dto.UserModule deleteUserModule(	@PathVariable("id") int id,HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		
		com.argSecurity.model.UserModule userModuleModel = 	userModuleServices.findOne(id);
		
		userModuleModel.setActive(false);
		
		userModuleModel.setDeletedDate(dateSystem);
		
		userModuleModel.setDeletedBy(userlogin.getUsername());
		
		userModuleServices.save(userModuleModel);
		
		List<UserStep> userSteps=userStepServices.getIsUserStepsAndIsActive(userModuleModel.getUserId(), true);
		
		
		
		List<Step> steps = stepServices.getStepsByModule(userModuleModel.getModuleId());
		
		for(Step st:steps){
			
			for(UserStep userStep:userSteps){
				
				if(st.getId() ==userStep.getStepId()){
					
					userStep.setActive(false);
					
					userStep.setDeletedBy(userlogin.getUsername());
					
					dateSystem.setTime(System.currentTimeMillis());
					
					userStep.setDeletedDate(dateSystem);
					
					userStepServices.save(userStep);
					
					
					
					
				}
				
				
			}
			
			
			
		}
		
		
		
		
		return dozerMapper.map(userModuleServices.save(userModuleModel), com.argSecurity.model.dto.UserModule.class);	
		
		
	}
			
	/**
	 * allows to find user steps by id and user id
	 * 
	 * @param id
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(path="/{id}/{userId}", method = {RequestMethod.GET})
	 @ResponseBody
	public com.argSecurity.model.dto.UserModule findUserSteps(	@PathVariable("id") int id,@PathVariable("userId") int userId,HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		
		com.argSecurity.model.UserModule userModuleModel = 	userModuleServices.findOne(id);
		
		userModuleModel.setActive(false);
		
		userModuleModel.setDeletedDate(dateSystem);
		
		userModuleModel.setDeletedBy(userlogin.getUsername());
		
		userModuleServices.save(userModuleModel);
		
		
		
		return dozerMapper.map(userModuleServices.save(userModuleModel), com.argSecurity.model.dto.UserModule.class);	
		
		
	}
	
}
