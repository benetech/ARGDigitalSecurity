/**
 * @author Francisco Gómez Astúa
 * 
 * 
 * This class will handle sending and receiving SMS
 *
 */

package com.argSecurity.module.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;


public class SMSHandler {
	
	@Autowired
	private SMSConfiguration config;
	
	/**
	 * 
	 * @param config
	 */
	public SMSHandler(SMSConfiguration config) {
		this.config = config;
	}
	
	/**
	 * 
	 */
	public SMSHandler() {
		this(new SMSDefaultConfiguration());
	}
	
	/**
	 * Send an SMS message to the specified phone number
	 * 
	 * @param phoneNumber
	 * 		The number to which the message will be sent.
	 * 		For best results use the E.164 number formatting: +12345678901
	 * @param messageText
	 * 		The body of the message to be sent.
	 * @return
	 * 		The Message SID.
	 * @throws TwilioRestException
	 * 		If the message cannot be sent for an invalid formatted number or any other reason relating to the SMS service.
	 */
	public String sendSMS(String phoneNumber, String messageText) throws TwilioRestException {
		return sendSMS(config.getDefaultPhoneNumber(), phoneNumber, messageText);
	}
	
	/**
	 * Send an SMS message to the specified phone number
	 * 
	 * @param sender
	 * 		The number from which the message will be sent.
	 * 		For best results use the E.164 number formatting: +12345678901
	 * @param recipient
	 * 		The number to which the message will be sent.
	 * 		For best results use the E.164 number formatting: +12345678901
	 * @param messageText
	 * 		The body of the message to be sent.
	 * 		The Message SID.
	 * @return
	 * 		The Message SID.
	 * @throws TwilioRestException
	 * 		If the message cannot be sent for an invalid formatted number or any other reason relating to the SMS service.
	 */
	public String sendSMS(String sender, String recipient, String messageText) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(config.getAccountSid(), config.getAuthToken());
		
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("From", sender));
		params.add(new BasicNameValuePair("To", "+"+recipient));
		params.add(new BasicNameValuePair("Body", messageText));
		
		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		Message message = messageFactory.create(params);
		return message.getSid();
	}
	
	/**
	 * 
	 * @param from
	 * @param message
	 * @param messageSid
	 * @return
	 */
	public String replyToSMS(String from, String message, String messageSid) {
		String reply = "This is a reply";
		return createSMSReply(reply);
	}
	
	/**
	 * 
	 * @param reply
	 * @return
	 */
	public String createSMSReply(String reply) {
		TwiMLResponse twiml = new TwiMLResponse();
		com.twilio.sdk.verbs.Message message = new com.twilio.sdk.verbs.Message(reply);
		try {
			twiml.append(message);
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		
		return twiml.toXML();
	}

	public SMSConfiguration getConfig() {
		return config;
	}

	public void setConfig(SMSConfiguration config) {
		this.config = config;
	}
	
	
	
}
