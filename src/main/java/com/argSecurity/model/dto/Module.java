/**
 * Module DTO object for the model 
 * 
 * @author  Bryan Barrantes
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model.dto;

import java.sql.Date;
import java.util.List;

public class Module extends MessageObjectAbstract{

	private int id;
	
	private String name;
	
	private String description;
	
	private String model;
	
	private String status;
	
	private int duration;
	
	private String timeUnit;
	
	private int retries;
	
	private boolean isActive;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private Date deletedDate;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private String deletedBy;
	
	private Date startDate;
	
	private List<Step> steps;

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
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * @return
	 */
	public String getModel() {
		return model;
	}
	/**
	 * 
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
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
	public int getDuration() {
		return duration;
	}
	/**
	 * 
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 * 
	 * @return
	 */
	public String getTimeUnit() {
		return timeUnit;
	}
	/**
	 * 
	 * @param timeUnit
	 */
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
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
	public Date getModifiedDate() {
		return (Date)(this.modifiedDate!= null?this.modifiedDate.clone():null);
	}
	/**
	 * 
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = (modifiedDate!= null?(Date)modifiedDate.clone():null);
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
		return (Date)(this.deletedDate!= null?this.deletedDate.clone():null);
	}
	/**
	 * 
	 * @param deletedDate
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = (deletedDate!= null?(Date)deletedDate.clone():null);
	}
	/**
	 * 
	 * @return
	 */
	public String getDeletedBy() {
		return deletedBy;
	}
	/***
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
	public Date getStartDate() {
		return (Date)(this.startDate!= null?this.startDate.clone():null);
	}
	/**
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = (startDate!= null?(Date)startDate.clone():null);
	}

	/**
	 * 
	 * @return
	 */
	public List<Step> getSteps() {
		return steps;
	}

	/**
	 * 
	 * @param steps
	 */
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
}