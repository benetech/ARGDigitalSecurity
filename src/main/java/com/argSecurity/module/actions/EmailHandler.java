/**
 * @author Didier Cerdas Quesada
 * 
 * 
 * This class will handle sending and receiving Mail
 *
 */

package com.argSecurity.module.actions;


import java.util.Base64;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


public class EmailHandler { 
	
	private final Logger log = LoggerFactory.getLogger(EmailHandler.class);
	private static final String DEFAULT_HEADER_NAME = "Content-Type";
	private static final String DEFAULT_HEADER_VALUE = "text/html";	
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	private final EmailConfiguration config;
	
	/**
	 * 
	 * @param config
	 */
	public EmailHandler(EmailConfiguration config) {
		this.config = config;
	}
	
	/**
	 * 
	 */
	public EmailHandler() {
		this(new EmailDefaultConfiguration());
	}
	
	/**
	 * 
	 * @param mailSender
	 */
	public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
	
	/**
	 * Send an email message to the specified email address
	 * 
	 * @param emailTo emailTo
	 * @param emailSubject	emailSubject
	 * @param emailBody	emailBody
	 * @throws MailException
	 */
	public void sendSimpleMail(String emailTo, String emailSubject, String emailBody) throws MailException {
		sendSimpleMail(config.getSenderEmail(), emailTo, emailSubject, emailBody);
	}
	
	/**
	 * Send an email message to the specified email address
	 * 
	 * @param emailFrom emailFrom
	 * @param emailTo emailTo
	 * @param emailSubject emailSubject
	 * @param emailBody emailBody
	 * @throws MailException
	 */
	public void sendSimpleMail(String emailFrom, String emailTo, String emailSubject, String emailBody) throws MailException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		try {
			mailMessage.setFrom(emailFrom);
			mailMessage.setTo(emailTo);
			mailMessage.setSubject(emailSubject); 
			mailMessage.setText(emailBody);
			this.mailSender.send(mailMessage);
		} catch (MailException mailException) {
			log.error(String.format("Problem with method %s", "sendSimpleMail"), mailException);
			throw mailException;
			
		}
	}
	
	/**
	 * 
	 * Send an email message to the specified email address
	 * 
	 * @param emailTo emailTo
	 * @param emailSubject	emailSubject
	 * @param emailBody	emailBody
	 */
	public void sendHTMLMail(String emailTo, String emailSubject, String emailBody) throws MessagingException {
		sendHTMLMail(config.getSenderEmail(), emailTo, emailSubject, emailBody);
	}
	
	/**
	 * Send an email message to the specified email address
	 * 
	 * @param emailFrom emailFrom
	 * @param emailTo emailTo
	 * @param emailSubject emailSubject
	 * @param emailBody emailBody
	 * @throws MailException
	 */
	public void sendHTMLMail(String emailFrom, String emailTo, String emailSubject, String emailBody) throws MessagingException{
		
		
        MimeMessage mailMessage = mailSender.createMimeMessage();

		try {
	        mailMessage.setFrom(emailFrom);
	        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
	        mailMessage.setSubject(emailSubject);
	        mailMessage.addHeader( DEFAULT_HEADER_NAME, DEFAULT_HEADER_VALUE);
	        mailMessage.setContent(emailBody , DEFAULT_HEADER_VALUE);
			this.mailSender.send(mailMessage);
		
		} catch (MessagingException mailException) {
			log.error(String.format("Problem with method %s", "sendHTMLMail"), mailException);
			throw mailException;
			
		}
	}
	
	/**
	 * 
	 * @param emailBody
	 * @param serverURL
	 * @param stepAction
	 * @param userId
	 * @param stepId
	 * @return
	 */
	public String createFullEmailBody(String emailBody, String serverURL, String stepAction, int userId, int stepId, int sequenceId){
		
		String user = Base64.getEncoder().encodeToString(String.valueOf(userId).getBytes());
		String parent = Base64.getEncoder().encodeToString(String.valueOf(stepId).getBytes());
		String action = Base64.getEncoder().encodeToString(stepAction.getBytes());
		String random = UUID.randomUUID().toString().replace("-", "");

		StringBuffer buffer = new StringBuffer();
		buffer.append("<p>");
		buffer.append(emailBody);
		buffer.append("</p>");
		//begin imageSrc
        buffer.append("<img src=\"");
        buffer.append(serverURL);
        buffer.append("/rest/action/collect?action=");
        buffer.append(action);
        buffer.append("&objectId=");
        buffer.append(user);
        buffer.append("&identifier=");
        buffer.append(random);
        buffer.append("&parentId=");
        buffer.append(parent);
        buffer.append("&sequenceId=");
        buffer.append(sequenceId);
        buffer.append("\">");
        //end imageSrc
        return buffer.toString();
		
	}
	
}
