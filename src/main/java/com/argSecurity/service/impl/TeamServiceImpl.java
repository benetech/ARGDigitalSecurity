/**
 * Team service
 * @author daniela.depablos
 *
 * Manipulates team data 
 * 
 * Benetech trainning app Copyrights reserved
 */	
package com.argSecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argSecurity.model.Team;
import com.argSecurity.repository.TeamRepository;


@Service
@Transactional
@Secured("user_management")
public class TeamServiceImpl {

	@Autowired
	private TeamRepository teamRepository;
	
	/**
	 * 
	 * @param team
	 * @return
	 */
	public Team save(Team team){
		return teamRepository.save(team);
	}
	/**
	 * 
	 * @param team
	 * @return
	 */
	public Team update(Team team){
		return teamRepository.save(team);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Team findbyId(int id){
		return teamRepository.findOne(id);
	}
	
	
	
	
}
