/**
 * TracedActionRepository
 * @author bryan.barrantes
 * Benetech trainning app Copyrights reserved
 * 
 */

package com.argSecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.argSecurity.model.TracedAction;

/**
 * Interface for the Data Access Object for the User model. It extends JpaRepository which is part of Spring Data JPA and declares all the commons
 * methods.
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.custom-behaviour-for-all-repositories</link> <br>
 * This interface aims to be automatically implemented by Spring Data JPA:
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.create-instances</link>
 */
@RestResource(exported = true, path = "tracedaction")
public interface TracedActionRepository extends JpaRepository<TracedAction, Integer> {

  /**
   * This query will be automatically implemented by it's name, "findBy" is the key work.
   *
   * @param login instance to be the value of the criteria
   * @return a single user matching the login
   */
	List<TracedAction> findByisActive(boolean isActive);
	
	List<TracedAction> findByUserId(int userId);
	
	List<TracedAction> findByUserIdAndModuleIdAndIsActive(int userId, int moduleId, boolean active);

	List<TracedAction> findByProcessedAndIsActive(boolean processed, boolean active);
  
}
