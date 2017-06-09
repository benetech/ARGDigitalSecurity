/**
 * Role repository 
 * @author daniela.depablos
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.argSecurity.model.Role;

@RestResource(exported = true, path = "role")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	/**
	 * 
	 * @param name
	 * @return
	 */
	Role findByName(String name);
	
}
