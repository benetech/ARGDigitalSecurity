/**
 * LoginStatus. Simple DTO (Data Transfert Object) used to give a structure to a login status return in the login process
 * 
 * @author daniela.depablos
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model.dto;


public class LoginStatus extends MessageObjectAbstract{

  /**
   * Boolean logged in current status
   */
  private boolean loggedIn;

  /**
   * Username (or login) currently logged in (or null if not logged in)
   */
  private String username;

  private String password;

  private Boolean rememberMe;

  private Role role;
  
  /**
   * Is logged in
   *
   * @return loggedIn
   */
  public boolean isLoggedIn() {
    return loggedIn;
  }

  /**
   * Set logged in
   *
   * @param loggedIn
   */
  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  /**
   * Get username (or login)
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set username (or login)
   *
   * @param username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getRememberMe() {
    return rememberMe;
  }

  public void setRememberMe(Boolean rememberMe) {
    this.rememberMe = rememberMe;
  }

@Override
public void setCode(String code) {
	// TODO Auto-generated method stub
	this.code=code;
	
}

@Override
public String getCode() {
	// TODO Auto-generated method stub
	return this.code;
}

@Override
public void setMessage(String message) {
	// TODO Auto-generated method stub
	this.message=message;
}

@Override
public String getMessage() {
	// TODO Auto-generated method stub
	return this.message;
}

@Override
public void setIdTypeMessage(int idTypeMessage) {
	// TODO Auto-generated method stub
	this.idTypeMessage=idTypeMessage;
}

@Override
public int getIdTypeMessage() {
	// TODO Auto-generated method stub
	return this.idTypeMessage;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}




}
