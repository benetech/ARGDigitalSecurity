/**
 * @author Bryan Barrantes
 * <odel Table Module
 * Benetech trainning app Copyrights reserved
 * 
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
@Table(name = "training_modules", schema="benetech")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "time_unit")
	private String timeUnit;
	
	@Column(name = "retries")
	private int retries;
	
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
	
	@Column(name = "start_date")
	private Date startDate;
	
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
	
}