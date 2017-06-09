/**
 * SendSMSStep
 * 
 * @author bryan.Barrantes
 *
 */

package com.argSecurity.module.steps;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argSecurity.model.Message;
import com.argSecurity.model.Parameter;
import com.argSecurity.model.Step;
import com.argSecurity.model.User;
import com.argSecurity.model.UserCondition;
import com.argSecurity.model.UserModule;
import com.argSecurity.module.actions.SMSDefaultConfiguration;
import com.argSecurity.module.actions.SMSHandler;
import com.argSecurity.module.actions.StepException;
import com.argSecurity.service.impl.UserConditionServiceImpl;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserServiceImpl;
import com.argSecurity.utils.Constants;
import com.twilio.sdk.TwilioRestException;

@Service
public class SendSMSStep implements IStep {

	
	private SMSHandler sms;
	
	
	
	private final Logger log = LoggerFactory.getLogger(SendMailStep.class);

	@Override
	public Message execute(Step step, Map<String, Parameter> params, Map<String, Object> context) throws StepException {
		Message response = new Message();
		Parameter pSender = params.get("sender");
		Parameter pMessage = params.get("message");
		
		String sender = (pSender != null) ? pSender.getValue() : null;
		String message = (pMessage != null) ? pMessage.getValue() : null;
		
		int moduleId = step.getTrainingModuleId();
		int stepId = step.getId();
		
		UserModuleServiceImpl userModuleServices = (UserModuleServiceImpl) context.get("userModuleServices");
		UserServiceImpl userService = (UserServiceImpl) context.get("userService");
		UserConditionServiceImpl userConditionService = (UserConditionServiceImpl) context.get("userConditionService");
		SMSDefaultConfiguration smsDefaultConfiguration=(SMSDefaultConfiguration)context.get("smsDefaultConfig");
		SMSHandler smsHandler=(SMSHandler)context.get("smsHandler");
		smsHandler.setConfig(smsDefaultConfiguration);
		this.setSms(smsHandler);
		List<UserModule> userModules = userModuleServices.loadUserModuleByModuleId(moduleId);
		for(UserModule module: userModules) {
			int userId = module.getUserId();
			User userModel = userService.findOne(userId);
			String recipient = userModel.getPhone();
			
			try {
				
				
				String result = (sender != null) ?
						this.getSms().sendSMS(sender, recipient, message)
							: this.getSms().sendSMS(recipient, message);
	            
	            response.setCode(com.argSecurity.utils.Constants.MessagesCodes.OK);
	            response.setMessage("");
	            //set user condition to completed for this step when sent is successful
	            List<UserCondition> userConditionList = userConditionService.getActiveConditionsByUserIdAndStepId(userId, stepId, true);
	            for(UserCondition condition: userConditionList) {
	            	condition.setStatus("completed");
	            	userConditionService.save(condition);
	            }
	        } catch (TwilioRestException ex) {
	        	log.error(String.format("Problem with method %s", "sendSMS"), ex);
	        	response.setCode(Constants.MessagesCodes.EXCEPTION);
	            response.setMessage(ex.getMessage());
	        }
		}
		
		return response;
		
	}

	public SMSHandler getSms() {
		return sms;
	}

	public void setSms(SMSHandler sms) {
		this.sms = sms;
	}

	
	
}
