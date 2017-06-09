/**
 * @author bryan barrantes
 * Benetech trainning app Copyrights reserved
Interface for the Data Access Object for the User model. It extends JpaRepository which is part of Spring Data JPA and declares all the commons
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

import com.argSecurity.model.UserStep;

/**
 * Interface for the Data Access Object for the User model. It extends JpaRepository which is part of Spring Data JPA and declares all the commons
 * methods.
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.custom-behaviour-for-all-repositories</link> <br>
 * This interface aims to be automatically implemented by Spring Data JPA:
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.create-instances</link>
 */
@RestResource(exported = true, path = "userstep")

public interface UserStepRepository extends JpaRepository<UserStep, Integer> {

  /**
   * This query will be automatically implemented by it's name, "findBy" is the key work.
   *
   * @param login instance to be the value of the criteria
   * @return a single user matching the login
   */
	List<UserStep> findByisActive(boolean isActive);
	
	/**
	 * 
	 * @param stepId
	 * @return
	 */
	List<UserStep> findByStepId(int stepId);
	
	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @return
	 */
	List<UserStep> findByUserIdAndIsActive(int userId, boolean isActive);
	
	/**
	 * 
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	List<UserStep> findByStepIdAndIsActive(int stepId, boolean isActive);
	
	/**
	 * 
	 * @param userId
	 * @param stepId
	 * @param isActive
	 * @return
	 */
	List<UserStep> findByUserIdAndStepIdAndIsActive(int userId, int stepId, boolean isActive);

	/**
	 * 
	 * @param userId
	 * @param isActive
	 * @param status
	 * @return
	 */
	List<UserStep> findByUserIdAndIsActiveAndStatus(int userId, boolean isActive, String status);
	
	@Query("select count(*) from UserStep us , Step st where us.userId = :userId "
			+ "and us.status= :status and us.isActive = :active"
			+ " and us.stepId=st.id and st.trainingModuleId=:trainningId")
	int find(@Param(value = "userId")int userId, @Param(value = "active")boolean isActive,
			@Param(value = "status")String status,@Param(value = "trainningId")int trainningId);
  
	
	@Query("select count(*) AS total from UserStep us where us.userId in :userIds"
			+ " and us.status= :status and us.isActive = :active GROUP BY us.stepId ORDER BY total DESC")
 int[] find(@Param(value = "userIds")List<Integer> userId, @Param(value = "active")boolean isActive,
			@Param(value = "status")String status);
	@Query("select us.stepId from UserStep us where us.userId in :userIds"
			+ " and us.status= :status and us.isActive = :active GROUP BY us.stepId")
 int[] findCustomQuery(@Param(value = "userIds")List<Integer> userId, @Param(value = "active")boolean isActive,
			@Param(value = "status")String status);
	
	@Query("select count(*) from UserStep us "
			+ " where us.userId in :userIds "
			+ "and us.isActive = :active")
 int findCustomQueryTop(@Param(value = "userIds")List<Integer> userIds, @Param(value = "active")boolean isActive);
	
	@Query("select us.status from UserStep us "
			+ " where us.userId = :userId "
			+ "and us.isActive = :active")	
 List<String> findCustomQueryStatus(@Param(value = "userId")int userId, @Param(value = "active")boolean isActive);	
	
	@Query("select count(*) from UserStep us where us.userId = :userId "
			+ "and us.status in :status and us.isActive = :active")
	int find(@Param(value = "userId")int userId, @Param(value = "active")boolean isActive,
			@Param(value = "status")List<String> status);
	
	@Query("select us.id from UserStep us  , Step st where us.userId= :userId"
			+ " and st.trainingModuleId= :moduleId and us.isActive = :active"
			+ " and st.id= us.stepId")
	int []find(@Param(value = "userId")int userId,@Param(value = "moduleId") int moduleId, @Param(value = "active")boolean isActive);
	
}
