/**
 * User validations to sample data
 *  
 * @author daniela.depablos
 *
 */
package com.argSecurity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argSecurity.model.User;
import com.argSecurity.repository.UserRepository;

@Service
@Transactional
@Secured("user_management")
public class UsersServiceValidationsImpl {

	@Autowired
	private UserRepository userRepository;	
	
	/**
	 * 
	 * @param username
	 * @param status
	 */
	public void lockedAndUnlockedAccount(String username, boolean status){
		User user = userRepository.findByUsername(username);		
		user.setAccountStatus(status);
		userRepository.save(user);
		
	}
	/**
	 * 
	 * @param username
	 */
	public void updateAttempts(String username){
		User user = userRepository.findByUsername(username);
		user.setAttempts(user.getAttempts()+1);
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public int getAttempts(String username){
		User user = userRepository.findByUsername(username);
		return user.getAttempts();
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean getAccountStatus(String username){
		User user = userRepository.findByUsername(username);
		return user.isAccountStatus();
	}
	/**
	 * 
	 * @param isActive
	 * @return
	 */
	public List<User> getIsActiveUsers(boolean isActive){
		return userRepository.findByisActive(true);
	}
	/**
	 * Deleting user changing is active
	 * @param user
	 */
	public void deleteUser(User user){
		userRepository.save(user);
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public  User saveUSer(User user){
		return userRepository.save(user);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public User findUser(int id){
		return userRepository.findOne(id);
	}
	/**
	 * 
	 * @return
	 */
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public List<User> findActivesAndByLogin(String username, boolean isActive){
		return userRepository.findByisActiveAndCreatedBy(isActive, username);
	}
	
	
	public List<String> findUsersEmails(boolean isActive){
		return userRepository.find(isActive);
	}
}