/**
 * @author Didier Cerdas Quesada
 * 
 * 
 * This class will handle sending and receiving Mail
 *
 */

package com.argSecurity.module.actions;

public class EmailDefaultConfiguration implements EmailConfiguration {
	
	public static final String DEFAULT_SENDER_MAIL = "notifications@accountmail.co";

	private final String senderEmail;


	/**
	 * 
	 * @param senderMail
	 */
	public EmailDefaultConfiguration(String senderMail) {
		this.senderEmail = senderMail;
	}

	/**
	 * 
	 */
	public EmailDefaultConfiguration() {
		this(DEFAULT_SENDER_MAIL);
	}
	
	@Override
	public String getSenderEmail() {
		return senderEmail;
	}
	
	
}
