/**
 * @author bryan barrantes
 * Condition model table
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "conditions", schema="benetech")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Condition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "timer")
	private String timer;
	
	@Column(name = "true_outcome")
	private String trueOutcome;
	
	@Column(name = "false_outcome")
	private String falseOutcome;
	
	@Column(name = "step_id")
	private int stepId;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "deleted_date")
	private Date deletedDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "deleted_by")
	private String deletedBy;
	
	@Column(name = "retries")
	private int retries;
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