/**
 * 
 * Benetech trainning app Copyrights reserved
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

import com.argSecurity.model.Step;


@RestResource(exported = true, path = "step")
public interface StepRepository extends JpaRepository<Step, Integer> {

  /**
   * This query will be automatically implemented by it's name, "findBy" is the key work.
   *
   * @param login instance to be the value of the criteria
   * @return a single user matching the login
   */
	Step findByName(String name);
	
	List<Step> findByisActive(boolean isActive);

	List<Step> findByTrainingModuleIdOrderBySequenceIdAsc(int moduleId);
  
	List<Step> findByTrainingModuleIdAndIsActive(int trainingModuleId, boolean isActive);
	
	@Query("select count(*) from Step s where s.trainingModuleId = :trainingModuleId "
			+ "and s.isActive = :active")
	int find(@Param(value = "trainingModuleId")int trainingModuleId, @Param(value = "active")boolean isActive);
	
	@Query("select count(*) from Step s where s.trainingModuleId = :trainingModuleId "
			+ "and s.status= :status and s.isActive = :active")
	int find(@Param(value = "trainingModuleId")int trainingModuleId, @Param(value = "active")boolean isActive,
			@Param(value = "status")String status);

	List<Step> findByTrainingModuleId(int moduleId);
	
}
