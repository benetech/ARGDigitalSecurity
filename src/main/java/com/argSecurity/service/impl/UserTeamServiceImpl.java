/**
 * 
 * @author daniela.depablos
 *
 *Manipulates team impl business logic 
 *
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argSecurity.model.UserTeam;
import com.argSecurity.repository.UserTeamRepository;

@Service
@Transactional
@Secured("user_management")
public class UserTeamServiceImpl {

	@Autowired
	private UserTeamRepository userTeamRepository;
	
	/**
	 * 
	 * @param userTeam
	 * @return
	 */
	public UserTeam save(UserTeam userTeam){
		return userTeamRepository.save(userTeam);
		
	}
	/**
	 * 
	 * @param userTeam
	 * @return
	 */
	public UserTeam update(UserTeam userTeam){
		return userTeamRepository.save(userTeam);
		
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public UserTeam findOne(UserTeam user){
		return userTeamRepository.findByUser(user);
	}
	
	public int findTeamActives(int userId, boolean isActive){
		return userTeamRepository.find(userId, isActive);
	}
	
}
