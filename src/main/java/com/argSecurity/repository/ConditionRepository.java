/**
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
import org.springframework.data.rest.core.annotation.RestResource;

import com.argSecurity.model.Condition;


@RestResource(exported = true, path = "condition")
public interface ConditionRepository extends JpaRepository<Condition, Integer> {

  /**
   * This query will be automatically implemented by it's name, "findBy" is the key work.
   *
   * @param login instance to be the value of the criteria
   * @return a single user matching the login
   */
	Condition findByName(String name);
  
	List<Condition> findByisActive(boolean isActive);
	
	List<Condition> findByStepId(int stepId);

	List<Condition> findByStepIdAndIsActive(int stepId, boolean active);
  
}
