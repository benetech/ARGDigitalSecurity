/**
 * ICondition
 * 
 * @author bryan.Barrantes
 *
 */
package com.argSecurity.module.conditions;

import com.argSecurity.model.Condition;
import com.argSecurity.model.Message;
import com.argSecurity.model.Step;
import com.argSecurity.model.UserStep;
import com.argSecurity.module.actions.StepException;

public interface ICondition {

	/**
	 * 
	 * @param condition
	 * @param step
	 * @param userStep
	 * @return
	 * @throws StepException
	 */
	public Message execute(Condition condition, Step step, UserStep userStep) throws StepException;
	
}
