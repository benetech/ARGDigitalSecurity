/**
 * Benetech trainning app Copyrights reserved
 */

package com.argSecurity.utils;

import java.io.Serializable;
import java.util.Map;

import com.argSecurity.utility.Key;


/**
 * 
 */
public abstract class AbstractComponent implements Serializable {
	
	/* Component name. */
	protected String name = "";
	
	/* Id for serialization version. */
	private static final long serialVersionUID = 780879439785460413L;

	/**
	 * 
	 *  <p>Unique constructor with parameters.
	 *  
	 *  @param name The component name.
	 * */
	public AbstractComponent (String name) {
		
		// Call to super class.
		super ();
		
		// Set the internal values.
		this.name = name == null ? this.getClass ().toString () : name.trim ();
	}
	
	/***
	 * 
	 *  <p>Method that execute the rules logic.
	 * 
	 *  @param context Context for execute the business logic. 
	 */
	public abstract void execute (Map<Key, Object> args);
}