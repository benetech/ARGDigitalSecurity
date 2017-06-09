/**
 * Start the environment of the app
 * 
 * @author daniela.depablos
 *	
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.argSecurity.controller.CoreController;
import com.argSecurity.module.actions.SMSDefaultConfiguration;
import com.argSecurity.module.actions.SMSHandler;
import com.argSecurity.module.conditions.ConditionExecutor;
import com.argSecurity.module.steps.StepExecutor;

@PropertySource({ "classpath:application.properties" })
@SpringBootApplication
@EnableScheduling
public class SpringBootARGSecurity {
	
	public final static String SMTP_AUTH = "mail.smtp.auth";
	public final static String STARTTLS = "starttls";
	public final static String STARTTLS_ENABLED = "mail.smtp.starttls.enable";
	
	@Value("${mail.host}")
	 private String host;
	@Value("${mail.username}")
	 private String username;
	@Value("${mail.password}")
	 private String password;
	 
	@Value("${mail.properties.mail.smtp.auth}")
	private String auth;
	@Value("${mail.port}")
	private int port;
	
	@Value("${mail.properties.smtp.starttls.required}")
	private String fallback;
	@Value("${mail.properties.mail.smtp.starttls.enable}")
	private String starttls;
	
	@Bean
	public JavaMailSender javaMailService() {
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		Properties mailProperties = new Properties();
		mailProperties.put(SMTP_AUTH, auth);
		mailProperties.put(STARTTLS, fallback);
		mailProperties.put(STARTTLS_ENABLED, starttls);


		javaMailSender.setJavaMailProperties(mailProperties);
		javaMailSender.setHost(host);
		javaMailSender.setPort(port);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);

		return javaMailSender;
	}
	
	@Bean
	public SimpleMailMessage mailMessage() {
		return new SimpleMailMessage();
	}
	
	@Bean
	public CoreController core() {
		return new CoreController();
	}
	
	@Bean
	public StepExecutor stepExecutor() {
		return new StepExecutor();
	}
	
	@Bean
	public ConditionExecutor conditionExecutor() {
		return new ConditionExecutor();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Bean(name="smsDefaultConfig")
	public SMSDefaultConfiguration smsDefaultConfig(){
		return new SMSDefaultConfiguration();
	}
	
	@Bean
	@DependsOn(value="smsDefaultConfig")
	public SMSHandler smsHandler(){
		return new SMSHandler(smsDefaultConfig());
	}
	
	
	/**
	 * static main to initialize app 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootARGSecurity.class, args);
	}
}