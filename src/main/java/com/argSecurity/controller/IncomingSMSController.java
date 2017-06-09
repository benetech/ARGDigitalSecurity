package com.argSecurity.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.module.actions.SMSHandler;

@Controller
@RequestMapping("/sms")
public class IncomingSMSController {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String messageSid = request.getParameter("MessageSid");
		final String accountSid = request.getParameter("AccountSid");
		final String from = request.getParameter("From");
		final String message = request.getParameter("Body");

		SMSHandler sms = new SMSHandler();
		String responseXml = sms.replyToSMS(from, message, messageSid);
		
		response.setContentType("application/xml");
		response.getWriter().print(responseXml);
	}

}
