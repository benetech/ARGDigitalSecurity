/**
 * @author Bryan Barrantes
 * Model Table Audit Trail
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "audit_trail", schema="benetech")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditTrail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "object_type")
	private String objectType;
	
	@Column(name = "object_id")
	private int objectId;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
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
	public Timestamp getCreatedDate() {
		return (Timestamp)(this.createdDate!= null?this.createdDate.clone():null);
	}
	/**
	 * 
	 * @param createdDate
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = (createdDate!= null?(Timestamp)createdDate.clone():null);
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