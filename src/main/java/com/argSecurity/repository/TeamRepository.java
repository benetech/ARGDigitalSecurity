/**
 * Team Repository 
 * @author daniela.depablos
 *
 *Communicates with database to fetch o manipulate DML data for team
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.argSecurity.model.Team;

@RestResource(exported = true, path = "team")
public interface TeamRepository extends JpaRepository<Team, Integer>{

}
