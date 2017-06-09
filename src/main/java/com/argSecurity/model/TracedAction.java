/**
 * TracedAction
 *
 * @author bryan.barrantes
 * 
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
@Table(name = "traced_actions", schema="benetech")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TracedAction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "action")
	private String action;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "sequence_id")
	private Integer sequenceId;
	
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
	
	@Column(name = "module_id")
	private int moduleId;
	
	@Column(name = "processed")
	private boolean processed;

	
	/**
	 * 
	 * @return
	 */
	public Integer getSequenceId() {
		return sequenceId;
	}
	
	/**
	 * 
	 * @param sequenceId
	 */
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
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