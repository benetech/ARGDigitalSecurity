/**
 * UserConditionRepository
 * @author bryan.barrantes
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.argSecurity.model.UserCondition;

/**
 * Interface for the Data Access Object for the User model. It extends JpaRepository which is part of Spring Data JPA and declares all the commons
 * methods.
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.custom-behaviour-for-all-repositories</link> <br>
 * This interface aims to be automatically implemented by Spring Data JPA:
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.create-instances</link>
 */
@RestResource(exported = true, path = "usermodule")
public interface UserConditionRepository extends JpaRepository<UserCondition, Integer> {

  /**
   * This query will be automatically implemented by it's name, "findBy" is the key work.
   *
   * @param login instance to be the value of the criteria
   * @return a single user matching the login
   */
	List<UserCondition> findByisActive(boolean isActive);
	
	/**
	 * 
	 * @param stepId
	 * @return
	 */
	List<UserCondition> findByStepId(int stepId);
	
	/**
	 * 
	 * @param conditionId
	 * @return
	 */
	List<UserCondition> findByConditionId(int conditionId);
	
	/**
	 * 
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	List<UserCondition> findByStepIdAndIsActive(int stepId, boolean isActive);

	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @return
	 */
	List<UserCondition> findByUserIdAndIsActive(int userId, boolean isActive);

	/**
	 * 
	 * @param userId
	 * @param conditionId
	 * @param isActive
	 * @return
	 */
	List<UserCondition> findByUserIdAndConditionIdAndIsActive(int userId, int conditionId, boolean isActive);

	/**
	 * 
	 * @param userId
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	List<UserCondition> findByUserIdAndStepIdAndIsActive(int userId, int stepId, boolean isActive);

	/**
	 * 
	 * @param stepId
	 * @param status
	 * @param active
	 * @return
	 */
	List<UserCondition> findByStepIdAndStatusAndIsActive(int stepId, String status, boolean active);

	/**
	 * 
	 * @param stepId
	 * @param conditionId
	 * @param active
	 * @return
	 */
	List<UserCondition> findByStepIdAndConditionIdAndIsActive(int stepId, int conditionId, boolean active);
	
}
