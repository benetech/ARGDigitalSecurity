/**
 * UserConditionServiceImpl
 * @author bryan.barrantes
 * 
 * Benetech trainning app Copyrights reserved
 */

package com.argSecurity.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.argSecurity.model.UserCondition;
import com.argSecurity.repository.UserConditionRepository;

@Service
public class UserConditionServiceImpl {
	
	@Autowired
	private UserConditionRepository userConditionRepository;
	
	/**
	 * 
	 * @param userConditionModel
	 */
	public void save(UserCondition userConditionModel) {
		userConditionRepository.save(userConditionModel);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public UserCondition findOne(Integer id) {
		UserCondition response = userConditionRepository.findOne(id);
		return response;
	}
	
	/**
	 * 
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	public List<UserCondition> getActiveConditionsByStepId(int stepId,boolean isActive){
		return userConditionRepository.findByStepIdAndIsActive(stepId, isActive);
	}
	
	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @return
	 */
	public List<UserCondition> getActiveConditionsByUserId(int userId,boolean isActive){
		return userConditionRepository.findByUserIdAndIsActive(userId, isActive);
	}
	
	/**
	 * 
	 * @param userId
	 * @param conditionId
	 * @param isActive
	 * @return
	 */
	public List<UserCondition> getActiveConditionsByUserIdAndConditionId(int userId, int conditionId, boolean isActive){
		return userConditionRepository.findByUserIdAndConditionIdAndIsActive(userId, conditionId, isActive);
	}
	
	/**
	 * 
	 * @param userId
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	public List<UserCondition> getActiveConditionsByUserIdAndStepId(int userId, int stepId, boolean isActive){
		return userConditionRepository.findByUserIdAndStepIdAndIsActive(userId, stepId, isActive);
	}
	
	/**
	 * 
	 * @param stepId
	 * @param status
	 * @return
	 */
	public List<UserCondition> getActiveConditionsByStepIdAndStatus(int stepId, String status){
		return userConditionRepository.findByStepIdAndStatusAndIsActive(stepId, status, true);
	}
	
	/**
	 * 
	 * @param stepId
	 * @param conditionId
	 * @return
	 */
	public List<UserCondition> getActiveConditionsByStepIdAndConditionId(int stepId, int conditionId){
		return userConditionRepository.findByStepIdAndConditionIdAndIsActive(stepId, conditionId, true);
	}
	
	/**
	 * 
	 * @param userId
	 * @param stepId
	 * @return
	 */
	public List<UserCondition> getIncompleteActiveConditionsByUserIdAndStepId(int userId, int stepId) {
		List<UserCondition> userConditions = userConditionRepository.findByUserIdAndStepIdAndIsActive(userId, stepId, true);
		for (int i = 0; i < userConditions.size(); i++) {
			if(userConditions.get(i).getStatus().equals("completed")) {
				userConditions.remove(i);
			}
		}
		return userConditions;
	}
	
	/**
	 * 
	 * @param stepId
	 * @return
	 */
	public List<UserCondition> getIncompleteActiveConditionsByStepId(int stepId) {
		List<UserCondition> userConditions = userConditionRepository.findByStepIdAndIsActive(stepId, true);
		for (int i = 0; i < userConditions.size(); i++) {
			if(userConditions.get(i).getStatus().equals("completed")) {
				userConditions.remove(i);
			}
		}
		return userConditions;
	}
}
