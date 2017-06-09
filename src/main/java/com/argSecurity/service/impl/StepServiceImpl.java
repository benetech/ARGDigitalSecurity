/**
 * Steps service for the Step model data table
 * 
 * @author Bryan Barrantes
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.service.impl;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.argSecurity.model.Step;
import com.argSecurity.repository.StepRepository;

@Service
public class StepServiceImpl {
	
	@Autowired
	private StepRepository stepRepository;
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws AuthenticationException
	 */
	public Step loadStepByName(String name) throws AuthenticationException {
		Step step = stepRepository.findByName(name);
		
		if (step == null) {
			throw new ObjectNotFoundException(name, "step");
		}else {
			return step;
		}
	}
	
	/**
	 * 
	 * @param isActive
	 * @return
	 */
	public List<Step> getIsActiveSteps(boolean isActive){
		return stepRepository.findByisActive(true);
	}
		
	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<Step> getStepsByModule(int moduleId){
		return stepRepository.findByTrainingModuleIdOrderBySequenceIdAsc(moduleId);
	}
	
	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<Step> getIncompleteStepsByModule(int moduleId){
		List<Step> response = stepRepository.findByTrainingModuleIdOrderBySequenceIdAsc(moduleId);
		for(int i = 0; i < response.size(); i++){
			if(response.get(i).getStatus().equals("completed"))
				response.remove(i);
		}
		return response;
	}

	/**
	 * 
	 * @param stepModel
	 */
	public void save(Step stepModel) {
		stepRepository.save(stepModel);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void delete(int id){
		Step step = stepRepository.findOne(id);
		step.setActive(false);
		stepRepository.save(step);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Step findOne(Integer id) {
		Step response = stepRepository.findOne(id);
		return response;
	}
	
	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public int getStepsByModuleCount(int moduleId){
		return (stepRepository.findByTrainingModuleIdAndIsActive(moduleId, true)).size();
	}
	
	/**
	 * 
	 * @param moduleId
	 * @param isActive
	 * @return
	 */
	public List<Step> getStepsByModuleActive(int moduleId, boolean isActive){
		return stepRepository.findByTrainingModuleIdAndIsActive(moduleId, isActive);
	}
	
	/**
	 * 
	 * @param moduleId
	 * @param isActive
	 * @return
	 */
	public int getCountStepsByModuleActive(int moduleId, boolean isActive){
		return stepRepository.find(moduleId, isActive);
	}
	
	/**
	 * 
	 * @param moduleId
	 * @param isActive
	 * @param satus
	 * @return
	 */
	public int getCountStepsByModuleActiveAndStatus(int moduleId, boolean isActive, String satus){
		return stepRepository.find(moduleId,isActive,satus);
	}
	
	
}
