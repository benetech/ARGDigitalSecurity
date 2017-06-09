/***
 * @author Bryan Barrantes
 * 
 * Handle all the message dto for the steps
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model;

public class Message {

	private int code;
	private String message;

	/**
	 * 
	 * @param code
	 * @param message
	 */
	public Message(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * 
	 */
	public Message(){}

	public Message(int code) {
		this(code, "");
	}

	/**
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}