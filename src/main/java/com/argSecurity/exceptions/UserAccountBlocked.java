/**
 * @author daniela.depablos 
 * 
 * Exception of account management
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAccountBlocked extends AuthenticationException {

	/**
	 * @author daniela.depablos
	 */
	private static final long serialVersionUID = -9157423463090966492L;

	public UserAccountBlocked(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public UserAccountBlocked(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}
	
}
