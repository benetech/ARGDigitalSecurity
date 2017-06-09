/**
 * Contiosn service imple to load all the conditions according the config of a step
 * 
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.service.impl;

import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.argSecurity.model.Condition;
import com.argSecurity.repository.ConditionRepository;

@Service
public class ConditionServiceImpl {
	
	@Autowired
	private ConditionRepository conditionRepository;
	
	public Condition loadConditionByName(String name) throws AuthenticationException {
		Condition condition = conditionRepository.findByName(name);
		
		if (condition == null) {
			throw new ObjectNotFoundException(name, "condition");
		}else {
			return condition;
		}
	}
	
	/**
	 * 
	 * @param isActive
	 * @return
	 */
	public List<Condition> getIsActiveConditions(boolean isActive){
		return conditionRepository.findByisActive(true);
	}
	
	/**
	 * 
	 * @param stepId
	 * @return
	 */
	public List<Condition> getConditionsByStep(int stepId){
		return conditionRepository.findByStepIdAndIsActive(stepId, true);
	}
	
	
	/**
	 * 
	 * @param conditionModel
	 */
	public void save(Condition conditionModel) {
		conditionRepository.save(conditionModel);
	}
	

	/**
	 * Set module as inactive
	 * @param id
	 */
	public void delete(int id){
		Condition condition = conditionRepository.findOne(id);
		condition.setActive(false);
		conditionRepository.save(condition);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Condition findOne(Integer id) {
		Condition response = conditionRepository.findOne(id);
		return response;
	}
}
