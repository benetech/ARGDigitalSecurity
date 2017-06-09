/**
 * User Team 
 * @author daniela.depablos
 *vBenetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.argSecurity.model.UserTeam;

@RestResource(exported = true, path = "userteam")
public interface UserTeamRepository extends JpaRepository<UserTeam, Integer>{
	/**
	 * 
	 * @param user
	 * @return
	 */
	public UserTeam findByUser(UserTeam user);
	
	
	@Query("select count(*) from UserTeam us where us.user.id = :userId "
			+ "and  us.isActive = :active")
	int find(@Param(value = "userId")int userId, @Param(value = "active")boolean isActive);
}
