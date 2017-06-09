/**
 * User Module Service
 * 
 * @author daniela.depablos
 * 
 * Manipulate data user module 
 *
 */
package com.argSecurity.service.impl;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argSecurity.model.User;
import com.argSecurity.model.UserModule;
import com.argSecurity.repository.UserModuleRepository;

@Service
@Transactional
public class UserModuleServiceImpl {
	
	@Autowired
	private UserModuleRepository userModuleRepository;
	
	/**
	 * 
	 * @param moduleId
	 * @return
	 * @throws AuthenticationException
	 */
	public List<UserModule> loadUserModuleByModuleId(int moduleId) throws AuthenticationException {
		List<UserModule> userModule = userModuleRepository.findByModuleIdAndIsActive(moduleId, true);
		
		if (userModule == null) {
			throw new ObjectNotFoundException(moduleId, "moduleId");
		}else {
			return userModule;
		}
	}
	
	/**
	 * 
	 * @param isActive
	 * @return
	 */
	public List<UserModule> getIsActiveUserModules(boolean isActive){
		return userModuleRepository.findByisActive(true);
	}

	/**
	 * 
	 * @param userModuleModel
	 * @return
	 */
	public UserModule save(UserModule userModuleModel) {
		return userModuleRepository.save(userModuleModel);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public UserModule findOne(Integer id) {
		UserModule response = userModuleRepository.findOne(id);
		return response;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public List<UserModule> getUserModules(User user){
		return userModuleRepository.findByUserId(user.getId());
	}
	
	/**
	 * 
	 * @param user
	 * @param isActive
	 * @return
	 */
	public List<UserModule> getUserModulesActives(User user, boolean isActive){
		return userModuleRepository.findByUserIdAndIsActive(user.getId(), isActive);
	}
	
	/**
	 * 
	 * @param moduleId
	 * @param isActive
	 * @return
	 */
	public List<Integer> getUserIdsByModuleIdActive(int moduleId,boolean isActive ){
		return userModuleRepository.findCustomQueryStatus(moduleId, isActive);
	}
	
	public int getUsersModule(int moduleId, boolean active){
		return (userModuleRepository.findByModuleIdAndIsActive(moduleId, active)).size();
	}
}
