/**
 * Role data model
 * @author daniela.depablos
 *
 * Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "roles", schema="benetech")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	private String description;
	
	@Column(name="is_active")
	private boolean isActive;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="deleted_date")
	private Date deletedDate;
	
	@Column(name="deleted_by")
	private String deletedBy;
	
	@OneToMany(cascade = {}, fetch = FetchType.EAGER)
	  private Set<User> users;
	
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
		this.createdDate = (Date) createdDate.clone();
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
	public Date getModifiedDate() {
		return (Date) this.modifiedDate.clone();
	}
	/**
	 * 
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = (Date) modifiedDate.clone();
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
	 * @param deleted_date
	 */
	public void setDeletedDate(Date deleted_date) {
		this.deletedDate = (Date) deleted_date.clone();
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
	
}
