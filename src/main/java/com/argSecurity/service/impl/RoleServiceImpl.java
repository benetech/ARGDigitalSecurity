/**
 * Role service
 * @author daniela.depablos
 *
 *	Fetch Roles data of benetech app
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argSecurity.model.Role;
import com.argSecurity.repository.RoleRepository;

@Service
@Transactional
@Secured("user_management")
public class RoleServiceImpl {

	@Autowired
	private RoleRepository roleRepository;
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Role findRoleByName(String name){
		return roleRepository.findByName(name); 
	}
	
}
