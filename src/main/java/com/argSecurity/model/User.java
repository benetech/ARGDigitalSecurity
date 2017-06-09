/**
 * Benetech trainning app Copyrights reserved
 * 
 * @author daniela.depablos
 * 
 * User model. This class is used both as application data and and security instance. <br/>
 * "@Entity" declare the class as an JPA 2 managed bean.<br/>
 * "@Table" allow us to override standard name of the DB table because "user" is often a key word.<br/>
 * "@JsonIgnoreProperties(ignoreUnknown = true)" is a special JSON marshaling option which able to map JSON data to a User even if all parameters are
 * set. <br/>
 * The class implements UserDetails which is the Spring Security interface to be able to use our User object as a user description in Spring Security
 */
package com.argSecurity.model;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "users", schema="benetech")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

  /**
   * Generated serial version UID for serialization: Spring Security's UserDetails has to be serializable
   */
  private static final long serialVersionUID = 818129969599480161L;
  
  private final static Logger log = Logger.getLogger(User.class);

  /**
   * Unique id for the User. "@Id" declare the parameter as the primary key "@GeneratedValue" indicates JPA 2 (and behind Hibernate) which strategy
   * to use for creating a new value. "GenerationType.AUTO" value allow JPA implementation to use the better way depending to the RDBMS used.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  /**
   * Login of the user. No annotation here, the parameter will be automatically mapped in the table.
   */
  private String username;

  public void setUsername(String username) {
	this.username = username;
}

/**
   * Password of the user. No annotation here, the parameter will be automatically mapped in the table.
   */
//  @RestResource(exported = false)
  private String password;

  /**
   * Fullname of the user. No annotation here, the parameter will be automatically mapped in the table.
   */
	  @Column(name="first_name")
	  private String firstName;
  
  	  @Column(name="last_name")
	  private String lastName;
	  
	  private String email;
	  
	  private String phone;
	  
	  private int attempts;
  
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="account_status")
	private boolean accountStatus;
  
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="role_id", nullable=false)
	private Role role;
	
	@Column(name="is_active")
	private boolean isActive;
  
	@Column(name="created_date")
	private Date createdDate;
  
	@Column(name="created_by")
	private String createdBy;
  
	@Column(name="modified_date")
	private Date modifiedDate;
  
	@Column(name="modified_By")
	private String modifiedBy;
  
    @Column(name="deleted_date")
    private Date deletedDate;
  
  	@Column(name="deleted_by")
  	private String deletedBy;
  	
  	@LazyCollection(LazyCollectionOption.FALSE)
  	@OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL,mappedBy = "user")
  	private Set<UserTeam> userTeam; 

  /**
   * Get id
   *
   * @return id
   */
  public Integer getId() {
    return id;
  }
  /**
   * 
   * @return
   */
  public Set<UserTeam> getUserTeam() {
	  Set<UserTeam> userTeamList = new HashSet<UserTeam>(this.userTeam);
	  for(UserTeam usert:this.userTeam){
		  try {
			userTeamList.add((UserTeam)usert.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
		}
	  }
	   return  userTeamList;
  }
  	/**
  	 * 
  	 * @param userTeam
  	 */
	public void setUserTeam(Set<UserTeam> userTeam) {
		for(UserTeam usert:userTeam){
			 try {
				 this.userTeam.add((UserTeam) usert.clone());
			 } catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					log.info(e.getMessage());
			 }
		}
		
		//this.userTeam = userTeam;
	}

/**
   * Set id
   *
   * @param id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Get login
   *
   * @return login
   */
  public String getLogin() {
    return username;
  }

  /**
   * Set login
   *
   * @param login
   */
  public void setLogin(String login) {
    this.username = login;
  }

  /**
   * Get password. Implements UserDetails.getPassword(). <br/>
   * "@JsonIgnore" will remove password value when performing the JSON serialization in order to not sending all passwords to everyone!
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set password
   *
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }



  /**
   * Get username which here is the login. Implements UserDetails.getUsername
   *
   * @return login
   */
  public String getUsername() {
    return username;
  }

  /**
   * Get authorities which in this simple application is always user-management if the user is defined. Implements UserDetails.getAuthorities
   *
   * @return authorities
   */
  @Override
  public Set<GrantedAuthority> getAuthorities() {
      Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority(this.getRole().getName()));
      return authorities;
  }

  /**
   * Not implemented. Implements UserDetails.isAccountNonExpired
   *
   * @return true
   */
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Not implemented. Implements UserDetails.isAccountNonLocked
   *
   * @return true
   */
  public boolean isAccountNonLocked() {
    return accountStatus;
  }

  /**
   * Not implemented. Implements UserDetails.isCredentialsNonExpired
   *
   * @return true
   */
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Not implemented. Implements UserDetails.isEnabled
   *
   * @return true
   */
  public boolean isEnabled() {
    return true;
  }
/**
 * 
 * @return
 */
public String getFirstName() {
	return firstName;
}
/**
 * 
 * @param firstName
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
/**
 * 
 * @return
 */
public String getLastName() {
	return lastName;
}
/**
 * 
 * @param lastName
 */
public void setLastName(String lastName) {
	this.lastName = lastName;
}
/**
 * 
 * @return
 */
public String getEmail() {
	return email;
}
/**
 * 
 * @param email
 */
public void setEmail(String email) {
	this.email = email;
}
/**
 * 
 * @return
 */
public int getAttempts() {
	return attempts;
}
/**
 * 
 * @param attempts
 */
public void setAttempts(int attempts) {
	this.attempts = attempts;
}
/**
 * 
 * @return
 */
public boolean isAccountStatus() {
	return accountStatus;
}
/**
 * 
 * @param accountStatus
 */
public void setAccountStatus(boolean accountStatus) {
	this.accountStatus = accountStatus;
}




/**
 * 
 * @return
 */
public boolean isActive() {
	return isActive;
}
/**
 * 
 * @param isActive
 */
public void setActive(boolean isActive) {
	this.isActive = isActive;
}

/**
 * 
 * @return
 */
public Date getCreatedDate() {
	return (Date)(this.createdDate!= null?this.createdDate.clone():null);
}
/**
 * 
 * @param createdDate
 */
public void setCreatedDate(Date createdDate) {
	this.createdDate = (createdDate!= null?(Date)createdDate.clone():null);
}
/**
 * 
 * @return
 */
public Date getModifiedDate() {
	return (Date)(this.modifiedDate!= null?this.modifiedDate.clone():null);
}
/**
 * 
 * @param modifiedDate
 */
public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = (modifiedDate!= null?(Date)modifiedDate.clone():null);
}

/**
 * 
 * @return
 */
public String getCreatedBy() {
	return createdBy;
}
/**
 * 
 * @param createdBy
 */
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}

/**
 * 
 * @return
 */
public String getModifiedBy() {
	return modifiedBy;
}
/**
 * 
 * @param modifiedBy
 */
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}
/**
 * 
 * @return
 */
public Date getDeletedDate() {
	return (Date)(this.deletedDate!= null?this.deletedDate.clone():null);
}
/**
 * 	
 * @param deletedDate
 */
public void setDeletedDate(Date deletedDate) {
	this.deletedDate = (deletedDate!= null?(Date)deletedDate.clone():null);
}
/**
 * 
 * @return
 */
public String getDeletedBy() {
	return deletedBy;
}
/**
 * 
 * @param deletedBy
 */
public void setDeletedBy(String deletedBy) {
	this.deletedBy = deletedBy;
}
/**
 * 
 * @return
 */
public Role getRole() {
	return role;
}
/**
 * 
 * @param role
 */
public void setRole(Role role) {
	this.role = role;
}



}