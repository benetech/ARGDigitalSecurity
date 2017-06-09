/**
 * @author Daniela Depablos 
 * 
 * Controller to display values in the team status dashboard controller
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.model.Step;
import com.argSecurity.model.UserStep;
import com.argSecurity.model.dto.CommonErrorsDTO;
import com.argSecurity.model.dto.Module;
import com.argSecurity.model.dto.TeamStatusDTO;
import com.argSecurity.model.dto.User;
import com.argSecurity.model.dto.UserStepScore;
import com.argSecurity.service.impl.StepServiceImpl;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserServiceImpl;
import com.argSecurity.service.impl.UserStepServiceImpl;
@Secured({"admin","user_management"})
@Controller
@RequestMapping("rest/teamstatus")
public class TeamStatusDashboardController {
	
	final static int COMMON_FAILURES = 3;
	
	@Autowired
	private UserModuleServiceImpl userModuleServices;
	
	@Autowired
	private UserStepServiceImpl userStepServices;
	
	@Autowired
	private UserServiceImpl userServices;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private Date dateSystem;
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private StepServiceImpl stepServices;
	
	private final static Logger log = Logger.getLogger(TeamStatusDashboardController.class);
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param teamStatusDto
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public TeamStatusDTO getUserModulesActive(	
										 	HttpServletRequest request, 
										 	HttpServletResponse response, 
										 	@RequestBody TeamStatusDTO teamStatusDto 
										 	) throws IOException{
		
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		
		log.info(teamStatusDto.toString());
		List<User> users = new ArrayList<User>();
		List<Integer> usersIds = new ArrayList<Integer>();
		List<Integer> trainningModuleIds = new ArrayList<Integer>();
		float scoreAverage=0.0f;
		
		
		for(User user: teamStatusDto.getUsers()){
			com.argSecurity.model.User userModel=userServices.findById(user.getId());
			//validar el status del modulo - completed se hace la est 
			
			User dtoUser = dozerMapper.map(userModel, User.class);
			int stepsPassed=getUserStepPassed(dtoUser, teamStatusDto.getModules().get(0).getId());//id del module
			int stepsTotal=getUserStepTotal(dtoUser,teamStatusDto.getModules().get(0).getId());//id del module
			if(stepsPassed>0)
				dtoUser.setScore(stepsTotal>0?((stepsPassed*100/stepsTotal)):0.0f);
			else
				dtoUser.setScore(100);
			scoreAverage+=dtoUser.getScore();
			
			users.add(dtoUser);
			usersIds.add(user.getId());
		}
		
		float completedSteps = 0;
		float totalSteps = 0;
		for(Module module:teamStatusDto.getModules()){
			totalSteps+=getModuleTotalSteps(module);
			completedSteps+=getModuleTotalStepsCompleted(module);
			trainningModuleIds.add(module.getId());
		}
		teamStatusDto.setStagesCompleted((totalSteps>0?completedSteps / totalSteps:0.0f)*100);
		teamStatusDto.setStagesComplement(100 - (int)teamStatusDto.getStagesCompleted());
		int topScore[] =
		userStepServices.getCommonMistakes(usersIds, true, "failed");
		int steps[]=userStepServices.getCommonMistakesSteps(usersIds, true, "failed");
		int i=0;
		List<CommonErrorsDTO> commons = new ArrayList<CommonErrorsDTO>();
		for(int top:topScore){
			log.info(top);
			CommonErrorsDTO commonError = new CommonErrorsDTO();
			Step step =stepServices.findOne(steps[i++]);
			commonError.setDescription(step.getDescription());
			commonError.setTotal(top);
			commons.add(commonError);
		}
		
		teamStatusDto.setCommonErrors(commons);
		
		teamStatusDto.setUsers(users);
		
		teamStatusDto.setUserStepsScore(getPerformanceOverTime(users, teamStatusDto.getModules().get(0).getId()));
		
		teamStatusDto.setScore(scoreAverage/users.size());
		
		return teamStatusDto;
	}
	
	
	
	
	/**
	 * 
	 * 
	 * Performance over time: takes the results of each step per person, considering the lapse for the step
	 *
	 * A precondition, is that the users are enrolled in the same modules, cannot compare users with different enrollments 
	 * and different steps
	 * 
	 * @param List<Module> Modules
	 * @param List<User> Users
	 * 
	 * @returns List<User, step, result> User id, Step id, boolean passed
	 * 
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public List<UserStepScore> getPerformanceOverTime( List<User> users, int moduleId){
		
		
		
		
		List<UserStepScore> usersScoresStep = new ArrayList<UserStepScore>();
		
		int stepsMod =stepServices.getStepsByModuleCount(moduleId);
		
		int stepsPassed=0;
		for(User user:users){
			UserStepScore userStepScore = new UserStepScore();
			userStepScore.setName(user.getFirstName());
			int stepsId[] =userStepServices.getUserStepId(user.getId(), moduleId, true);
			stepsPassed+=userStepServices.getIsUserStepsAndIsActiveAndStatus(user.getId(), true, "passed");
			stepsPassed+=userStepServices.getIsUserStepsAndIsActiveAndStatus(user.getId(), true, "completed");
			
			if(stepsId.length>0){
				int totalStep = getUserStepTotal(user, moduleId);
				int score= (totalStep>0?100/totalStep:100/stepsId.length);
				int sumScores=0;
				int i=0;
				String description="";
				for(int stepId:stepsId){
					
					UserStep usrStep=userStepServices.findOne(stepId);
					description =description+(""+(usrStep.getModifiedDate()!= null?usrStep.getModifiedDate():usrStep.getCreatedDate())+
							"-"+stepsPassed+"/" +stepsMod);
					
					if(usrStep.getStatus().equalsIgnoreCase("passed")
							|| usrStep.getStatus().equalsIgnoreCase("completed")){
						if((score %2) ==0){
							sumScores+=score;
						}else{
							sumScores+=score;
							if(i>0)
								sumScores+=1;
						}
						
					}
					description =description+  (usrStep.getStatus().equalsIgnoreCase("passed")
							|| usrStep.getStatus().equalsIgnoreCase("completed")
							?"-"+sumScores+"%<br>"
							:"-"+usrStep.getStatus()+"<br>" );
					
					i++;
	 				
				}
				userStepScore.setDescription(description);
				
			}else{
				userStepScore.setDescription("NA");
			}
			usersScoresStep.add(userStepScore);
			
		}
	
		return usersScoresStep;
	}
	
	/**
	 * 
	 *
	 *		totalSteps FOR THAT USER, so it has to be sent total steps / total users = 10
	 *		stepPassed false
	 *		currentScore 40
	 *
	 * 
	 * @param currentScore
	 * @param stepPassed
	 * @param totalSteps
	 * @return
	 */
	private int getStepScore(int currentScore, boolean stepPassed, int totalSteps){
		if(!stepPassed){
			return currentScore;
		} else {
			return (100 / totalSteps) + currentScore;
		}
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public int getUserStepPassed(User user, int idTrainingMod){
		return userStepServices.getUserStepsCount(user.getId(), true, "passed",idTrainingMod);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public int getUserStepFailed(User user, int idTrainingMod){
		return userStepServices.getUserStepsCount(user.getId(), true, "failed",idTrainingMod);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public int getUserStepTotal(User user,int idTrainingMod){
		return (getUserStepPassed(user,idTrainingMod) +getUserStepFailed(user, idTrainingMod) ) ;
	}
	
	/**
	 * 
	 * @param module
	 * @return
	 */
	public int getModuleTotalSteps(Module module){
		return stepServices.getCountStepsByModuleActive(module.getId(), true);
	}
	
	/**
	 * 
	 * @param module
	 * @return
	 */
	public int getModuleTotalStepsCompleted(Module module){
		return stepServices.getCountStepsByModuleActiveAndStatus(module.getId(), true,"completed");
	}
	
	/**
	 * 
	 * @param userDto
	 * @return
	 */
//	public int getTopStepUsers(List<User> userDto){
//		List<Integer> arrayLis=new ArrayList<Integer>();
//		for(User user: userDto){
//			arrayLis.add(getUserStepTotal(user));
//		}
//		return Collections.max(arrayLis);
//	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getSteps(){
		List<String> steps = new ArrayList<String>();
		return steps;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public List<String> getStepsNonAvailable(int i){
		
		List<String> steps = new ArrayList<String>();
		steps.add("NA");
		
		return steps;
	}
	
}