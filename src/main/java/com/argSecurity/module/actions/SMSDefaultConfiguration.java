/**
 * @author Francisco GÃ³mez AstÃºa
 * 
 * 
 * This class will handle the SMS configuration
 *
 */
package com.argSecurity.module.actions;

import org.springframework.beans.factory.annotation.Value;

public class SMSDefaultConfiguration implements SMSConfiguration {
	
	@Value("${twilio.account_sid}")
	private String accountSid;
	
	@Value("${twilio.auth_token}")
	private String authToken;
	
	@Value("${twilio.default_phone_number}")
	private String defaultPhoneNumber;
	
	/**
	 * 
	 */
	public SMSDefaultConfiguration() {
	}
	
	/**
	 * 
	 * @param account_sid
	 * @param auth_token
	 * @param senderPhoneNumber
	 */
	public SMSDefaultConfiguration(String account_sid, String auth_token, String senderPhoneNumber) {
		this.accountSid = account_sid;
		this.authToken = auth_token;
		this.defaultPhoneNumber = senderPhoneNumber;
	}

	@Override
	public String getAccountSid() {
		return accountSid;
	}

	/**
	 * 
	 * @param accountSid
	 */
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	@Override
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * 
	 * @param authToken
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	@Override
	public String getDefaultPhoneNumber() {
		return defaultPhoneNumber;
	}

	/**
	 * 
	 * @param senderPhoneNumber
	 */
	public void setDefaultPhoneNumber(String senderPhoneNumber) {
		this.defaultPhoneNumber = senderPhoneNumber;
	}
	
}