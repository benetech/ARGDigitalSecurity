/**
 * DTO User Module
 * 
 * @author daniela.depablos
 *
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.model.dto;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class UserModule extends MessageObjectAbstract{
	
	private int id;
	
	private int userId;
	
	private int moduleId;
	
	private String status;
	
	private String moduleName;
	
	private boolean isActive;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private Date deletedDate;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private String deletedBy;
	
	private Date completedDate;
	
	private List<Step> steps;
	
	private int stepPerc;
	
	private final static Logger log = Logger.getLogger(UserModule.class);

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
	public Date getCompletedDate() {
		return (Date) (this.completedDate!= null?(Date)this.completedDate.clone():null);
	}
	/**
	 * 
	 * @param completedDate
	 */
	public void setCompletedDate(Date completedDate) {
		this.completedDate = (completedDate!= null?(Date)completedDate.clone():null);
	}
	/**
	 * 
	 * @return
	 */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * 
	 * @param moduleName
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * 
	 * @return
	 */
	public List<Step> getSteps() {
		
		return this.steps;
	}
	/**
	 * 
	 * @param steps
	 */
	public void setSteps(List<Step> steps) {

		this.steps=steps;
		
	}
	/**
	 * 
	 * @return
	 */
	public int getStepPerc() {
		return stepPerc;
	}
	/**
	 * 
	 * @param stepPerc
	 */
	public void setStepPerc(int stepPerc) {
		this.stepPerc = stepPerc;
	}
	
	
}