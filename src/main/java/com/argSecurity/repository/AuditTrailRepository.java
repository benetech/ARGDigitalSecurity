/**
 * Benetech trainning app Copyrights reserved
 * 
 * Interface for the Data Access Object for Audit Trail model. It extends JpaRepository which is part of Spring Data JPA and declares all the commons
 * methods.
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.custom-behaviour-for-all-repositories</link> <br>
 * This interface aims to be automatically implemented by Spring Data JPA:
 * <link>http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.create-instances</link>
 */
package com.argSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.argSecurity.model.AuditTrail;


@RestResource(exported = true, path = "audit_trail")
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Integer> {
  
}
