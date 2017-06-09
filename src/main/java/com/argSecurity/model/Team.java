/**
 * Team model table data
 * 
 * @author daniela.depablos
 * 
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="teams", schema="benetech")
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	@Column(name="id_active")
	private boolean idActive;
	
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
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Set<UserTeam> userTeam;
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
	public boolean isIdActive() {
		return idActive;
	}
	/**
	 * 
	 * @param idActive
	 */
	public void setIdActive(boolean idActive) {
		this.idActive = idActive;
	}
	/**
	 * 
	 * @return
	 */
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
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
		return (Date)this.modifiedDate.clone();
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
		return (Date)this.deletedDate.clone();
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
	public Set<UserTeam> getUserTeam() {
		return this.userTeam;
	}
	/**
	 * 
	 * @param userTeam
	 */
	public void setUserTeam(Set<UserTeam> userTeam) {
		this.userTeam = userTeam;
	}
	

}
