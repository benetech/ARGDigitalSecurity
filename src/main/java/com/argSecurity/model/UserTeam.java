/**
 * user teams model 
 * @author daniela.depablos
 *
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_teams",catalog="benetech")
public class UserTeam implements Cloneable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	 
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName="id") 
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TEAM_ID", referencedColumnName="id")
	private Team team;
	
	 @Column(name="is_active")
	  private boolean isActive;
	  
	  @Column(name="created_date")
	  private Date createdDate;
	  
	  @Column(name="created_by")
	  private String createdBy;
	  
	  @Column(name="modified_date")
	  private Date modifiedDate;
	  
	  @Column(name="modified_By")
	  private String modifiedBy;
	  
	  @Column(name="deleted_date")
	  private Date deletedDate;
	  
	  @Column(name="deleted_by")
	  private String deletedBy;

	
	  /**
	   * 
	   * @return
	   */
	public boolean isActive() {
		return isActive;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
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
		return this.modifiedDate = (modifiedDate!= null?(Date)modifiedDate.clone():null);
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
	public User getUser() {
		return user;
	}
	/**
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 
	 * @return
	 */
	public Team getTeam() {
		return team;
	}
	/**
	 * 
	 * @param team
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	
	
}
