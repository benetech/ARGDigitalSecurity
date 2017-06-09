/**
 * Condition DTO object for model
 * 
 * @author Bryan Barrantes
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.model.dto;

public class CommonErrorsDTO {
	
	private String description;
	
	private int total;

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
	public int getTotal() {
		return total;
	}

	/**
	 * 
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 
	 * @param description
	 * @param total
	 */
	public CommonErrorsDTO(String description, int total) {
		super();
		this.description = description;
		this.total = total;
	}

	/**
	 * 
	 */
	public CommonErrorsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
