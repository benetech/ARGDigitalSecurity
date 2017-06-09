/**
 * StepExecutor
 * 
 * @author bryan.Barrantes
 *
 */
package com.argSecurity.module.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.argSecurity.model.Message;
import com.argSecurity.model.Parameter;
import com.argSecurity.model.Step;
import com.argSecurity.module.actions.StepException;

@Service
public class StepExecutor {
	
	/**
	 * 
	 * @param step
	 * @param params
	 * @return
	 */
	public Message executeMethod(Step step, List<Parameter> params, Map<String, Object> context) {
		Message msg = null;
		String stepName = step.getName();
		String packageName = this.getClass().getPackage().getName();
		String className = packageName + "." + stepName + "Step";
		
		try {
			Class<?> stepClass = Class.forName(className);
			IStep concreteStep = (IStep) stepClass.newInstance();
			Map<String, Parameter> mappedParams = new HashMap<>();
			for(Parameter param: params) mappedParams.put(param.getName(), param);
			msg = concreteStep.execute(step, mappedParams, context);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (StepException e) {
			e.printStackTrace();
		}

		return (msg != null) ? msg :
			new Message(
					com.argSecurity.utils.Constants.MessagesCodes.INVALID_DATA,
					com.argSecurity.utils.Constants.AppMessages.INVALID_ACTION);
	}
	
}
