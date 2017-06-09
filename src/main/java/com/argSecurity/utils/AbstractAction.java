/**
 * Benetech trainning app Copyrights reserved
 */

package com.argSecurity.utils;


import java.util.Map;

import org.apache.log4j.Logger;

import com.argSecurity.utility.Key;


public abstract class AbstractAction extends AbstractComponent {

	/* Define the next component to execute. */
	private AbstractComponent nextStep = null;

	/* Logger object. */
	private final static Logger log = Logger.getLogger(AbstractAction.class);

	/* Id for serialization version. */
	private static final long serialVersionUID = 780879459775470433L;

	/**
	 * 
	 *  <p>Constructor with parameters.
	 *  
	 *  @param name Action name.
	 * */
	public AbstractAction (String name) {

		// Call to super class.
		super (name);
	}

	/**
	 * 
	 *  <p>Method that execute the business logic.
	 *  
	 *  @param context Context rules business logic.
	 * */
	protected abstract void doExecute (Map<Key, Object> context);


	/**
	 * 
	 *  <p>Method that set the next step to execute.
	 *  
	 *  @param nextStep Next component to execute.
	 * */
	public void setNextStep (AbstractComponent nextStep) {

		// Set the next step.
		this.nextStep = nextStep;
	}

	/**
	 * 
	 *  <p>Method that return the next step to execute.
	 *  
	 *  @param nextStep Next component to execute.
	 * */
	public AbstractComponent getNextStep () {

		// Return the next step.
		return nextStep;
	}

	@Override
	public final void execute (Map<Key, Object> context) {

		this.doExecute (context);

		if (nextStep != null) {
			nextStep.execute (context);
		}
	}
}