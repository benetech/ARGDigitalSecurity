/**
 * @author Francisco Gómez Astúa
 * 
 * 
 * This class will handle the SMS Configuration
 *
 */
package com.argSecurity.module.actions;

public interface SMSConfiguration {

	/**
	 * 
	 * @return
	 */
	public String getAccountSid();

	/**
	 * 
	 * @return
	 */
	public String getAuthToken();

	/**
	 * 
	 * @return
	 */
	public String getDefaultPhoneNumber();

}
