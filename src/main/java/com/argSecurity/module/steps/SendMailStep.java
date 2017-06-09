/**
  * SendMailStep
 * 
 * @author bryan.Barrantes
 *
 */
package com.argSecurity.module.steps;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.argSecurity.model.Message;
import com.argSecurity.model.Parameter;
import com.argSecurity.model.Step;
import com.argSecurity.model.User;
import com.argSecurity.model.UserCondition;
import com.argSecurity.model.UserModule;
import com.argSecurity.module.actions.StepException;
import com.argSecurity.service.impl.UserConditionServiceImpl;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserServiceImpl;
import com.argSecurity.utils.Constants;

@Service
@Transactional
public class SendMailStep implements IStep {
	
	private final Logger log = LoggerFactory.getLogger(SendMailStep.class);
	
	@Override
	public Message execute(Step step, Map<String, Parameter> params, Map<String, Object> context) throws StepException {
		com.argSecurity.model.Message response = new com.argSecurity.model.Message();
		int moduleId = step.getTrainingModuleId();
		int stepId = step.getId();
		
		String emailTo = (String) params.get("to").getValue();
		String emailFrom = (String) params.get("from").getValue();
		String emailSubject = (String) params.get("subject").getValue();
		String emailBody = (String) params.get("body").getValue();
		String emailCloneSource = (String) params.get("source").getValue();
		int sequenceId = step.getSequenceId();
		
		UserModuleServiceImpl userModuleServices = (UserModuleServiceImpl) context.get("userModuleServices");
		UserServiceImpl userService = (UserServiceImpl) context.get("userService");
		JavaMailSender mailSender = (JavaMailSender) context.get("mailSender");
		UserConditionServiceImpl userConditionService = (UserConditionServiceImpl) context.get("userConditionService");
		String SERVER_URL = (String) context.get("SERVER_URL");
		
		List<UserModule> userModules = userModuleServices.loadUserModuleByModuleId(moduleId);
		
		for(int i = 0; i < userModules.size(); i++) {
			int userId = userModules.get(i).getUserId();
			User userModel = userService.findOne(userId);
			emailTo = userModel.getEmail();
			
			try {
				String user = Base64.getEncoder().encodeToString(String.valueOf(userId).getBytes());
				String parent = Base64.getEncoder().encodeToString(String.valueOf(moduleId).getBytes());
				String action = Base64.getEncoder().encodeToString("trackEmail".getBytes());
				String random = UUID.randomUUID().toString().replace("-", "");
				String sequence=Base64.getEncoder().encodeToString(("SEC_"+String.valueOf(sequenceId)).getBytes());
				//String SERVER_URL = "http://190.241.13.148:8080";//TODO test
				String imageSrc = SERVER_URL+"/rest/action/collect?action=" + action
						+ "&objectId=" + user
						+ "&identifier=" + random
						+ "&parentId=" + parent
						+"&sequenceId=" + sequence;
				//sequence Id
				
				action = Base64.getEncoder().encodeToString("userVisitedUrl".getBytes());
				String linkSource = emailCloneSource + "?action="+ action
						+ "&objectId=" + user
						+ "&identifier=" + random
						+ "&parentId=" + parent
						+"&sequenceId=" + sequence;
				
				emailBody = emailBody.replaceAll("final_web_clone_url", linkSource);
				emailBody = emailBody.trim();
				emailBody = emailBody + "<img src=\""+imageSrc+"\" style=\"width:1px; height:1px;\">";
	            
				MimeMessage message = mailSender.createMimeMessage();

	            message.setFrom(new InternetAddress(emailFrom));
	            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(emailTo));
	            message.setSubject(emailSubject);
	            message.addHeader("Content-Type", "text/html");
	            message.setContent(emailBody , "text/html; charset=utf-8");
	            
	            mailSender.send(message);
	            response.setCode(com.argSecurity.utils.Constants.MessagesCodes.OK);
	            response.setMessage("");
	            //set user condition to completed for this step when sent is successful
	            List<UserCondition> userCond = userConditionService.getActiveConditionsByUserIdAndStepId(userId, stepId, true);
	            for(int j = 0; j < userCond.size(); j++) {
	            	userCond.get(j).setStatus("completed");
	            	userConditionService.save(userCond.get(j));
	            }
	            emailBody = (String) params.get("body").getValue();//clean email body
	        } catch (MessagingException ex) {
	        	log.error(String.format("Problem with method %s", "sendEmail"), ex);
	        	response.setCode(Constants.MessagesCodes.EXCEPTION);
	            response.setMessage(ex.getMessage());
	        }
		}
		return response;

	}

}
