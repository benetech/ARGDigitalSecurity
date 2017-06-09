/**
 * IStep
 * 
 * @author bryan.Barrantes
 *
 */

package com.argSecurity.module.steps;

import java.util.Map;

import com.argSecurity.model.Message;
import com.argSecurity.model.Parameter;
import com.argSecurity.model.Step;
import com.argSecurity.module.actions.StepException;

public interface IStep {
	/**
	 * 
	 * @param step
	 * @param params
	 * @return
	 * @throws StepException
	 */
	public Message execute(Step step, Map<String, Parameter> params,  Map<String, Object> context) throws StepException;
	
}
