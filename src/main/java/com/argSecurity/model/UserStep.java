/**
 * User Step data model
 * 
 * @author bryan.barrantes
 *
 *Benetech trainning app Copyrights reserved
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
@Table(name = "user_steps", schema="benetech")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStep {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "step_id")
	private int stepId;
	
	@Column(name = "status")
	private String status;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStepId() {
		return stepId;
	}

	public void setStepId(int stepId) {
		this.stepId = stepId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return (Date)(this.createdDate!= null?this.createdDate.clone():null);
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = (createdDate!= null?(Date)createdDate.clone():null);
	}

	public Date getModifiedDate() {
		return (Date)(this.modifiedDate!= null?this.modifiedDate.clone():null);
	}

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

	public Date getDeletedDate() {
		return (Date) this.deletedDate.clone();
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = (Date) deletedDate.clone();
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}
	
}