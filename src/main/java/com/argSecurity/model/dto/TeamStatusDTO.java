/**
 * @author daniela.depablos
 * 
 */
package com.argSecurity.model.dto;
import java.util.Arrays;
import java.util.List;

public class TeamStatusDTO {
	
	private double score;
	
	private List<Module> modules;
	
	private List<User> users;
	
	private List<CommonErrorsDTO> commonErrors;
	
	private float stagesCompleted;
	
	private Integer stagesComplement;
	
	private List<UserStepScore> userStepsScore;
	
	public Integer getStagesComplement() {
		
		return stagesComplement;
		
	}
	public void setStagesComplement(Integer stagesComplement) {
		
		this.stagesComplement = stagesComplement;
		
	}
	private String [][] performanceOverTime;
	/**
	 * 
	 * @return
	 */
	public double getScore() {
		return score;
	}
	/**
	 * 
	 * @param score
	 */
	public void setScore(double score) {
		this.score = score;
	}
	/**
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		return users;
	}
	/**
	 * 
	 * @param users
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	/**
	 * 
	 * @return
	 */
	public List<CommonErrorsDTO> getCommonErrors() {
		return commonErrors;
	}
	/**
	 * 
	 * @param commonErrors
	 */
	public void setCommonErrors(List<CommonErrorsDTO> commonErrors) {
		this.commonErrors = commonErrors;
	}
	/**
	 * 
	 * @return
	 */
	public float getStagesCompleted() {
		return stagesCompleted;
	}
	/**
	 * 
	 * @param stagesCompleted
	 */
	public void setStagesCompleted(float stagesCompleted) {
		this.stagesCompleted = stagesCompleted;
	}
	/**
	 * 
	 * @return
	 */
	public String[][] getPerformanceOverTime() {
		return performanceOverTime;
	}
	/**
	 * 
	 * @param performanceOverTime
	 */
	public void setPerformanceOverTime(String[][] performanceOverTime) {
		this.performanceOverTime = performanceOverTime;
	}
	/**
	 * 
	 * @return
	 */
	public List<Module> getModules() {
		return modules;
	}
	/**
	 * 
	 * @param modules
	 */
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	/**
	 * 
	 * @return
	 */
	public List<UserStepScore> getUserStepsScore() {
		return userStepsScore;
	}
	/**
	 * 
	 * @param userStepsScore
	 */
	public void setUserStepsScore(List<UserStepScore> userStepsScore) {
		this.userStepsScore = userStepsScore;
	}
	@Override
	public String toString() {
		return "TeamStatusDTO [score=" + score + ", modules=" + modules
				+ ", users=" + users + ", commonErrors=" + commonErrors
				+ ", stagesCompleted=" + stagesCompleted
				+ ", stagesComplement=" + stagesComplement
				+ ", userStepsScore=" + userStepsScore
				+ ", performanceOverTime="
				+ Arrays.toString(performanceOverTime) + "]";
	}
	
	
	
	
	

}
