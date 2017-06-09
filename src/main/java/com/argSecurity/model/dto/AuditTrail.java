/**
 * Audit Trail DTO object for the model 
 * 
 * @author  Bryan Barrantes
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model.dto;

import java.sql.Date;

public class AuditTrail extends MessageObjectAbstract{

	private int id;
	
	private String objectType;
	
	private int objectId;
	
	private String action;
	
	private String status;
	
	private Date createdDate;
	
	private String createdBy;
	
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
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	public String getObjectType() {
		return objectType;
	}
	/**
	 * 
	 * @param 
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	/**
	 * 
	 * @return
	 */
	public int getObjectId() {
		return objectId;
	}
	/**
	 * 
	 * @param 
	 */
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	/**
	 * 
	 * @return
	 */
	public String getAction() {
		return action;
	}
	/**
	 * 
	 * @param 
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
}