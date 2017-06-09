/**
 * Message Abstract object to handle error messages
 * 
 * @author daniela.depablos
 *
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.model.dto;

public abstract class MessageObjectAbstract {
	
	protected int idTypeMessage;

	protected String code;
	
	protected String message;
	
	/**
	 * 
	 * @param code
	 */
	public abstract void setCode(String code);
	
	/**
	 * 
	 * @return
	 */
	public abstract String getCode();
	
	/**
	 * 
	 * @param message
	 */
	public abstract void setMessage(String message);
	
	/**
	 * 
	 * @return
	 */
	public abstract String getMessage();
	
	/**
	 * 
	 * @param idTypeMessage
	 */
	public abstract void setIdTypeMessage(int idTypeMessage);
	
	/**
	 * 
	 * @return
	 */
	public abstract int getIdTypeMessage();
}
