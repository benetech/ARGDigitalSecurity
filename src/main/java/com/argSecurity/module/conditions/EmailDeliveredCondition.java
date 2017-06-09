/**
 * EmailDeliveredCondition
 * 
 * @author bryan.Barrantes
 *
 */
package com.argSecurity.module.conditions;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import com.argSecurity.model.Condition;
import com.argSecurity.model.Message;
import com.argSecurity.model.Parameter;
import com.argSecurity.model.Step;
import com.argSecurity.model.User;
import com.argSecurity.model.UserCondition;
import com.argSecurity.model.UserStep;
import com.argSecurity.service.impl.ParameterServiceImpl;
import com.argSecurity.service.impl.UserConditionServiceImpl;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserServiceImpl;
import com.argSecurity.utils.Constants;

public class EmailDeliveredCondition implements ICondition {
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	UserModuleServiceImpl userModuleService;
	
	@Autowired
	UserConditionServiceImpl userConditionService;
	
	@Autowired
	ParameterServiceImpl parameterService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${base.url}")
	private String SERVER_URL;
	
	private final Logger log = LoggerFactory.getLogger(EmailDeliveredCondition.class);
	
	@Override
	public Message execute(Condition condition, Step step, UserStep userStep) {
		com.argSecurity.model.Message response = new com.argSecurity.model.Message();
		int stepId = step.getId();
		
		List<Parameter> params = parameterService.loadParameterByStepId(step.getId());
		Map<String, Parameter> mappedParams = new HashMap<>();
		for(Parameter param: params) mappedParams.put(param.getName(), param);
		
		String emailTo = (String) mappedParams.get("to").getValue();
		String emailFrom = (String) mappedParams.get("from").getValue();
		String emailSubject = (String) mappedParams.get("subject").getValue();
		String emailBody = (String) mappedParams.get("body").getValue();
		String emailCloneSource = (String) mappedParams.get("source").getValue();
		
		int userId = userStep.getUserId();
		User userModel = userService.findOne(userId);
		emailTo = userModel.getEmail();
		
		try {
			String user = Base64.getEncoder().encodeToString(String.valueOf(userId).getBytes());
			String parent = Base64.getEncoder().encodeToString(String.valueOf(stepId).getBytes());
			String action = Base64.getEncoder().encodeToString("trackEmail".getBytes());
			String random = UUID.randomUUID().toString().replace("-", "");
			
			String imageSrc = SERVER_URL+"/rest/action/collect?action=" + action
					+ "&objectId=" + user
					+ "&identifier=" + random
					+ "&parentId=" + parent;
			
			action = Base64.getEncoder().encodeToString("userVisitedUrl".getBytes());
			String linkSource = emailCloneSource + "?action="+ action
					+ "&objectId=" + user
					+ "&identifier=" + random
					+ "&parentId=" + parent; 
			
            MimeMessage message = mailSender.createMimeMessage();

            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(emailTo));
            message.setSubject(emailSubject);
            message.addHeader("Content-Type", "text/html");
            message.setContent(emailBody + linkSource + 
            "<img src=\""+imageSrc+"\" style=\"width:1px; height:1px;\">" , "text/html");
            
            mailSender.send(message);
            response.setCode(0);
            response.setMessage("");
            //set user condition to completed for this step when sent is successful
            List<UserCondition> userCond = userConditionService.getActiveConditionsByUserIdAndStepId(userId, stepId, true);
            for(int j = 0; j < userCond.size(); j++) {
            	userCond.get(j).setStatus("completed");
            	userConditionService.save(userCond.get(j));
            }
        } catch (MessagingException ex) {
        	log.error(String.format("Problem with method %s", "sendEmail"), ex);
        	response.setCode(Constants.MessagesCodes.EXCEPTION);
            response.setMessage(ex.getMessage());
        }
		
		return response;

	}

}
