/**
 * @author Francisco Gómez Astúa
 * 
 * 
 * This class will handle the Step Exception
 *
 */
package com.argSecurity.module.actions;

public class StepException extends Exception {

	/**
	 * 
	 * @param e
	 */
	public StepException(Throwable e) {
		super(e);
	}
}
