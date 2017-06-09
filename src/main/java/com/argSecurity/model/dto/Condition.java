/**
 * Condition DTO object for model
 * 
 * @author Bryan Barrantes
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model.dto;

import java.sql.Date;

public class Condition extends MessageObjectAbstract{

	private int id;
	
	private String name;
	
	private String timer;
	
	private String trueOutcome;
	
	private String falseOutcome;
	
	private int stepId;
	
	private boolean isActive;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	
	private Date deletedDate;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private String deletedBy;
	
	private int retries;

	
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
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	public String getTimer() {
		return timer;
	}
	/**
	 * 
	 * @param timer
	 */
	public void setTimer(String timer) {
		this.timer = timer;
	}
	/**
	 * 
	 * @return
	 */
	public String getTrueOutcome() {
		return trueOutcome;
	}
	/**
	 * 
	 * @param trueOutcome
	 */
	public void setTrueOutcome(String trueOutcome) {
		this.trueOutcome = trueOutcome;
	}
	/**
	 * 
	 * @return
	 */
	public String getFalseOutcome() {
		return falseOutcome;
	}
	/**
	 * 
	 * @param falseOutcome
	 */
	public void setFalseOutcome(String falseOutcome) {
		this.falseOutcome = falseOutcome;
	}
	/**
	 * 
	 * @return
	 */
	public int getStepId() {
		return stepId;
	}
	/**
	 * 
	 * @param stepId
	 */
	public void setStepId(int stepId) {
		this.stepId = stepId;
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
		return (Date) this.createdDate.clone();
	}
	/**
	 * 
	 * @param createdDate
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	/**
	 * 
	 * @return
	 */
	public Date getModifiedDate() {
		return (Date)this.modifiedDate.clone();
	}
	/**
	 * 
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = (Date)modifiedDate.clone();
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
		return (Date) this.deletedDate.clone();
	}
	/**
	 * 
	 * @param deletedDate
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = (Date)deletedDate.clone();
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
	public int getRetries() {
		return retries;
	}
	/**
	 * 
	 * @param retries
	 */
	public void setRetries(int retries) {
		this.retries = retries;
	}
	
}