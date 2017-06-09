/**
 * Benetech trainning app Copyrights reserved
 */

package com.argSecurity.utils;

import java.util.Map;

import org.apache.log4j.Logger;

import com.argSecurity.utility.Key;



/**
 * 

 */
public abstract class AbstractRule extends AbstractComponent {

	/* Logger object. */
	private final static Logger log = Logger.getLogger(AbstractRule.class);
	
	/* Component to execute in case of positive response. */
	private AbstractComponent positiveOutcomeStep = null;

	/* Component to execute in case of negative response. */
	private AbstractComponent negativeOutcomeStep = null;

	/* Id for serialization version. */
	private static final long serialVersionUID = 1216990187432545045L;

	/**
	 * 
	 *  <p>Constructor with parameters.
	 *  
	 *  @param name Rule name.
	 * */
	public AbstractRule (String name) {

		// Call to super class.
		super (name);
	}

	/**
	 * 
	 *  <p>Method that define the rule to execute.
	 *  
	 *  @param context Rule context.
	 * */
	protected abstract boolean makeDecision (Map<Key, Object> context);

	/**
	 * 
	 *  <p>Method that set the positive rule to execute.
	 *  
	 *  @param positiveOutcomeStep The positiveOutcomeStep to set.
	 */
	public void setPositiveOutcomeStep (AbstractComponent positiveOutcomeStep) {

		// set the positive rule.
		this.positiveOutcomeStep = positiveOutcomeStep;
	}

	/**
	 * 
	 *  <p>Method that return the positive rule to execute.
	 *  
	 *  @return The positiveOutcomeStep.
	 */
	public AbstractComponent getPositiveOutcomeStep () {

		// Return the positive rule.
		return positiveOutcomeStep;
	}

	/**
	 * 
	 *  <p>Method that set the negative rule to execute.
	 *  
	 *  @param negativeOutcomeStep The negativeOutcomeStep to set.
	 */
	public void setNegativeOutcomeStep (AbstractComponent negativeOutcomeStep) {

		// Set the negative rule.
		this.negativeOutcomeStep = negativeOutcomeStep;
	}

	/**
	 * 
	 *  <p>Method that return the negative rule to execute.
	 *  
	 *  @return The negativeOutcomeStep.
	 */
	public AbstractComponent getNegativeOutcomeStep () {

		// Return the negative rule.
		return negativeOutcomeStep;
	}

	@Override
	public final void execute (Map<Key, Object> context) {

		// Execute the business and get the result.
		boolean outcome = makeDecision (context);

		if (outcome) {
			positiveOutcomeStep.execute (context);
		} else {
			negativeOutcomeStep.execute (context);
		}
	}
}