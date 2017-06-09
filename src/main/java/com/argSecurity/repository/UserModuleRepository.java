/**
 * 
 * Benetech trainning app Copyrights reserved
 * 
 * Interface for the Data Access Object for the User model. It extends JpaRepository which is part of Spring Data JPA and declares all the commons
 * methods.
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.custom-behaviour-for-all-repositories</link> <br>
 * This interface aims to be automatically implemented by Spring Data JPA:
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.create-instances</link>
 */
package com.argSecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.argSecurity.model.UserModule;


@RestResource(exported = true, path = "usermodule")
public interface UserModuleRepository extends JpaRepository<UserModule, Integer> {

  /**
   * This query will be automatically implemented by it's name, "findBy" is the key work.
   *
   * @param login instance to be the value of the criteria
   * @return a single user matching the login
   */
	List<UserModule> findByisActive(boolean isActive);
	
	/**
	 * 
	 * @param moduleId
	 * @param active
	 * @return
	 */
	List<UserModule> findByModuleIdAndIsActive(int moduleId, boolean active);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	List<UserModule> findByUserId(int id);

	/**
	 * 
	 * @param id
	 * @param isActive
	 * @return
	 */
	List<UserModule> findByUserIdAndIsActive(int id,boolean isActive);
	
	@Query("select um.userId from UserModule um "
			+ " where um.moduleId = :id "
			+ "and um.isActive = :active")	
 List<Integer> findCustomQueryStatus(@Param(value = "id")int id, @Param(value = "active")boolean isActive);
	
}
