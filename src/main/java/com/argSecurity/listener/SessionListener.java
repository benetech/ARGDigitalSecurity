/**
 * 
 * @author daniela.depablos
 * 
 * Handling Session time out simple configuration
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.argSecurity.controller.LoginController;

@Component
public class SessionListener implements HttpSessionListener {
	
	private final static Logger log = Logger.getLogger(SessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		log.info("Session created !!! ");
		event.getSession().setMaxInactiveInterval(15*60);
		log.info("Session ID !!! " +event.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		log.info("Session destroyed !!! ");
		
	}

}
