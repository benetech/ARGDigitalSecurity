/**
 * Contiosn service imple to load all the conditions according the config of a step
 * 
 * Benetech trainning app Copyrights reserved
 */

package com.argSecurity.service.impl;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argSecurity.model.Parameter;
import com.argSecurity.repository.ParameterRepository;

@Service
public class ParameterServiceImpl {
	
	@Autowired
	private ParameterRepository parameterRepository;
	
	/**
	 * 
	 * @param stepId
	 * @return
	 * @throws ObjectNotFoundException
	 */
	public List<Parameter> loadParameterByStepId(int stepId) throws ObjectNotFoundException {
		List<Parameter> parameter = parameterRepository.findByStepId(stepId);
		
		if (parameter == null) {
			throw new ObjectNotFoundException(stepId, "stepId");
		}else {
			return parameter;
		}
	}
	
	/**
	 * 
	 * @param isActive
	 * @return
	 */
	public List<Parameter> getIsActiveParameters(boolean isActive){
		return parameterRepository.findByisActive(true);
	}

	/**
	 * 
	 * @param parameterModel
	 * @return
	 */
	public Parameter save(Parameter parameterModel) {
		return parameterRepository.save(parameterModel);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Parameter findOne(Integer id) {
		Parameter response = parameterRepository.findOne(id);
		return response;
	}
}
