/**
 * User DTO object
 * 
 * @author daniela.depablos
 *
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.model.dto;

import javax.validation.constraints.Size;


public class User extends MessageObjectAbstract{

	private int id;
	
	private String username;
	
	@Size(max=40)
	private String firstName;
	
	@Size(max=40)
	private String lastName;
	
	private String password;
	
	@Size(max=40)
	private String email;
	
	@Size(max=40)
	private String phone;
	
	private Role role;
	
	private boolean accountStatus;
	
	private boolean linkedToTeam;
	
	private boolean active;
	
	private String stepsCompleted;
	
	private String stepsCompletedAverage;
	
	private float score;
	
	
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
	/**
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 
	 * @return
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * 
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isAccountStatus() {
		return accountStatus;
	}
	/**
	 * 
	 * @param accountStatus
	 */
	public void setAccountStatus(boolean accountStatus) {
		this.accountStatus = accountStatus;
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
	public String getLastName() {
		return lastName;
	}
	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public boolean isLinkedToTeam() {
		return linkedToTeam;
	}
	/**
	 * 
	 * @param linkedToTeam
	 */
	public void setLinkedToTeam(boolean linkedToTeam) {
		this.linkedToTeam = linkedToTeam;
	}
	/**
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * 
	 * @param isActive
	 */
	public void setActive(boolean isActive) {
		this.active = isActive;
	}
	/**
	 * 
	 * @return
	 */
	public String getStepsCompleted() {
		return stepsCompleted;
	}
	/**
	 * 
	 * @param stepsCompleted
	 */
	public void setStepsCompleted(String stepsCompleted) {
		this.stepsCompleted = stepsCompleted;
	}
	/**
	 * 
	 * @return
	 */
	public String getStepsCompletedAverage() {
		return stepsCompletedAverage;
	}
	/**
	 * 
	 * @param stepsCompletedAverage
	 */
	public void setStepsCompletedAverage(String stepsCompletedAverage) {
		this.stepsCompletedAverage = stepsCompletedAverage;
	}
	/**
	 * 
	 * @return
	 */
	public float getScore() {
		return score;
	}
	/**
	 * 
	 * @param score
	 */
	public void setScore(float score) {
		this.score = score;
	}
	
	
	
}