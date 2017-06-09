/**
 * @author Bryan Barrantes
 * Fetch, update, create user steps 
 * communicate with database
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.argSecurity.model.UserStep;
import com.argSecurity.repository.UserStepRepository;

@Service
public class UserStepServiceImpl {
	
	@Autowired
	private UserStepRepository userStepRepository;
	
	/**
	 * 
	 * @param stepId
	 * @return
	 * @throws AuthenticationException
	 */
	public List<UserStep> loadUserStepByStepId(int stepId) throws AuthenticationException {
		List<UserStep> userStep = userStepRepository.findByStepIdAndIsActive(stepId, true);
		
		if (userStep == null) {
			throw new ObjectNotFoundException(stepId, "stepId");
		}else {
			return userStep;
		}
	}
	
	/**
	 * 
	 * @param isActive
	 * @return
	 */
	public List<UserStep> getIsActiveUserSteps(boolean isActive){
		return userStepRepository.findByisActive(true);
	}

	/**
	 * 
	 * @param userStepModel
	 */
	public void save(UserStep userStepModel) {
		userStepRepository.save(userStepModel);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public UserStep findOne(Integer id) {
		UserStep response = userStepRepository.findOne(id);
		return response;
	}
	
	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @return
	 */
	public List<UserStep> getIsUserStepsAndIsActive(int userId,boolean isActive){
		return userStepRepository.findByUserIdAndIsActive(userId, isActive);
	}
	
	/**
	 * 
	 * @param stepId
	 * @return
	 */
	public List<UserStep> getActiveUserStepsByStepId(int stepId){
		return userStepRepository.findByStepIdAndIsActive(stepId, true);
	}
	
	/**
	 * 
	 * @param userId
	 * @param stepId
	 * @return
	 */
	public List<UserStep> getActiveUserStepByUserIdAndStepId(int userId, int stepId){
		return userStepRepository.findByUserIdAndStepIdAndIsActive(userId, stepId, true);
	}
	
	/**
	 * 
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	public List<UserStep> getActiveUserStepsByStepIdNotFailed(int stepId,boolean isActive){
		List<UserStep> userSteps = userStepRepository.findByStepIdAndIsActive(stepId, isActive);
		List<UserStep> response = new ArrayList<UserStep>();
		for(int i = 0; i < userSteps.size(); i++) { 
			if(!(userSteps.get(i).getStatus().equals("failed"))){
				response.add(userSteps.get(i));
			}
		}
		return response;
	}
	
	/**
	 * 
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	public List<UserStep> getActiveUserStepsByStepIdCompleted(int stepId,boolean isActive){
		List<UserStep> userSteps = userStepRepository.findByStepIdAndIsActive(stepId, isActive);
		List<UserStep> response = new ArrayList<UserStep>();
		for(int i = 0; i < userSteps.size(); i++) { 
			if((userSteps.get(i).getStatus().equals("passed")) || (userSteps.get(i).getStatus().equals("failed"))){
				response.add(userSteps.get(i));
			}
		}
		return response;
	}
	
	/**
	 * 
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	public List<UserStep> getActiveUserStepsByStepIdInProgress(int stepId,boolean isActive){
		List<UserStep> userSteps = userStepRepository.findByStepIdAndIsActive(stepId, isActive);
		List<UserStep> response = new ArrayList<UserStep>();
		for(int i = 0; i < userSteps.size(); i++) { 
			if(userSteps.get(i).getStatus().equals("in-progress")){
				response.add(userSteps.get(i));
			}
		}
		return response;
	}
	
	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @param status
	 * @return
	 */
	public int getIsUserStepsAndIsActiveAndStatus(int userId,boolean isActive, String status){
		return (userStepRepository.findByUserIdAndIsActiveAndStatus(userId, isActive, status)).size();
	}
	
	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @param status
	 * @return
	 */
	public int getUserStepsCount(int userId,boolean isActive, String status, int trainingId){
		return userStepRepository.find(userId, isActive, status,trainingId);//add module id
	}
	
	/**
	 * 
	 * @param userIds
	 * @param isActive
	 * @param status
	 * @return
	 */
	public int[] getCommonMistakes(List<Integer> userIds,boolean isActive, String status){
		return userStepRepository.find(userIds, isActive, status);
	}
	
	/**
	 * 
	 * @param userIds
	 * @param isActive
	 * @param status
	 * @return
	 */
	public int[] getCommonMistakesSteps(List<Integer> userIds,boolean isActive, String status){
		return userStepRepository.findCustomQuery(userIds, isActive, status);
	}
	
	/**
	 * 
	 * @param userIds
	 * @param isActive
	 * @return
	 */
	public int getTotalStepsAll(List<Integer> userIds,boolean isActive){
		return userStepRepository.findCustomQueryTop(userIds, isActive);
	}
	
	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @return
	 */
	public List<String> getStatusByUser(int userId,boolean isActive ){
		return userStepRepository.findCustomQueryStatus(userId, isActive);
	}
	
	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @param status
	 * @return
	 */
	public int getUserStepsCountTotal(int userId,boolean isActive, List<String> status){
		return userStepRepository.find(userId, isActive, status);
	}
	
	/**
	 * 
	 * @param userId
	 * @param moduleId
	 * @param isActive
	 * @return
	 */
	public int[] getUserStepId(int userId, int moduleId, boolean isActive){
		return userStepRepository.find(userId, moduleId, isActive);
	}
}
