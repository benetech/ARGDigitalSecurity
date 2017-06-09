/**
 * DTO Object for step model table
 * 
 * @author Bryan Barrantes
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model.dto;

import java.io.Serializable;
import java.sql.Date;

public class Step extends MessageObjectAbstract implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6479056935571321332L;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	private int id;
	
	private String name;
	
	private String description;
	
	private String status;
	
	private int trainingModuleId;
	
	private boolean isActive;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private Date deletedDate;
	
	private String createdBy;
	
	
	private String modifiedBy;
	
	private String deletedBy;
	
	private int sequenceId;

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
	public int getTrainingModuleId() {
		return trainingModuleId;
	}
	/**
	 * 
	 * @param trainingModuleId
	 */
	public void setTrainingModuleId(int trainingModuleId) {
		this.trainingModuleId = trainingModuleId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

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
	
}