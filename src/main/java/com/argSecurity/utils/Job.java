/**
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.argSecurity.controller.CoreController;

@Component
public class Job {
	
	@Autowired
	CoreController core;
	
	@Scheduled(cron="0 0 */3 * * *") //once every day at 09:00:00
	public void mainJob() {
		System.out.println("Running");
		core.coreValidations();
	}
	
}
