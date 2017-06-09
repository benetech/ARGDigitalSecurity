/**
 * TracedAction
 * 
 * @author Bryan Barrantes
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model.dto;

import java.sql.Date;

public class TracedAction extends MessageObjectAbstract{

	private int id;	
	private String action;
	private int userId;	
	private int sequenceId;
	private boolean isActive;	
	private Date createdDate;	
	private Date modifiedDate;	
	private Date deletedDate;	
	private String createdBy;	
	private String modifiedBy;	
	private String deletedBy;
	private int moduleId;
	private boolean processed;

	
	/**
	 * 
	 * @return
	 */
	public int getSequenceId() {
		return sequenceId;
	}
	
	/**
	 * 
	 * @param sequenceId
	 */
	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
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
	public String getAction() {
		return action;
	}

	/**
	 * 
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 
	 * @return
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
		return createdDate;
	}

	/**
	 * 
	 * @param createdDate
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 
	 * @return
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * 
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * 
	 * @return
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * 
	 * @param deletedDate
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
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
	public int getModuleId() {
		return moduleId;
	}

	/**
	 * 
	 * @param moduleId
	 */
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isProcessed() {
		return processed;
	}

	/**
	 * 
	 * @param processed
	 */
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
}