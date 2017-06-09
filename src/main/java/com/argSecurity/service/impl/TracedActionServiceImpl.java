/**
 * TracedActionServiceImpl
 * @author bryan.barrantes
 * 
 * Benetech trainning app Copyrights reserved
 */

package com.argSecurity.service.impl;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.argSecurity.model.TracedAction;
import com.argSecurity.repository.TracedActionRepository;

@Service
public class TracedActionServiceImpl {
	
	@Autowired
	private TracedActionRepository tracedActionRepository;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws AuthenticationException
	 */
	public List<TracedAction> loadTracedActionByUserId(int userId) throws AuthenticationException {
		List<TracedAction> tracedAction = tracedActionRepository.findByUserId(userId);
		
		if (tracedAction == null) {
			throw new ObjectNotFoundException(userId, "userId");
		}else {
			return tracedAction;
		}
	}
	
	/**
	 * 
	 * @param isActive
	 * @return
	 */
	public List<TracedAction> getIsActiveTracedActions(boolean isActive){
		return tracedActionRepository.findByisActive(true);
	}

	/**
	 * 
	 * @param tracedActionModel
	 * @return
	 */
	public TracedAction save(TracedAction tracedActionModel) {
		return tracedActionRepository.save(tracedActionModel);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public TracedAction findOne(Integer id) {
		TracedAction response = tracedActionRepository.findOne(id);
		return response;
	}
	
	/**
	 * 
	 * @param userId
	 * @param moduleId
	 * @param action
	 * @return
	 * @throws AuthenticationException
	 */
	public int validateTracedAction(int userId, int moduleId, String action, int sequenceID) throws AuthenticationException {
		List<TracedAction> tracedActionList = tracedActionRepository.findByUserId(userId);
		int response = 0;
		if (tracedActionList == null) {
			throw new ObjectNotFoundException(userId, "userId");
		}else {
			for(TracedAction tracedAction: tracedActionList) {
				if(tracedAction.getAction().equals(action) && 
						
				tracedAction.getModuleId() == moduleId && 
				
				tracedAction.getSequenceId()==sequenceID) {
					response = tracedAction.getId();
					break;
				}
			}
			return response;
		}
	}
	
	/**
	 * 
	 * @param userId
	 * @param moduleId
	 * @return
	 */
	public List<TracedAction> getActiveTracedActionsByUserIdAndModuleId(int userId, int moduleId){
		return tracedActionRepository.findByUserIdAndModuleIdAndIsActive(userId,moduleId,true);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TracedAction> getUnprocessedActiveTracedActions(){
		return tracedActionRepository.findByProcessedAndIsActive(false, true);
	}
}
