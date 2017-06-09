/**
 * @author bryan barrantes
 * Pair DTO 
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.model;

public class Pair {

	private int key;
	private float value;
	private String text;
	
/**
 * 
 * @param key
 * @param value
 * @param text
 */
	public Pair(int key, float value, String text) {
		this.key = key;
		this.value = value;
		this.text = text;
	}
	
	/**
	 * 
	 */
	public Pair(){}

	/**
	 * 
	 * @return
	 */
	public int getKey() {
		return key;
	}

	/**
	 * 
	 * @param key
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * 
	 * @return
	 */
	public float getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(float value) {
		this.value = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

}