/**
 * FormWebHookStep
 * 
 * @author bryan.Barrantes
 *
 */
package com.argSecurity.module.steps;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.argSecurity.model.Message;
import com.argSecurity.model.Parameter;
import com.argSecurity.model.Step;
import com.argSecurity.module.actions.StepException;

@Service
public class FormWebHookStep implements IStep {

	@Override
	public Message execute(Step step, Map<String, Parameter> params, Map<String, Object> context)
			throws StepException {
		Message response = new Message();
		
		response.setCode(0);
        response.setMessage("");
		return response;
	}

}
