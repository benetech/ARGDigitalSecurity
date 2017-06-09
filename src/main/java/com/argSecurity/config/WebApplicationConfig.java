/**
 * Scanning configuration class
 * 
 * @author daniela.depablos
 *	
 * Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;



//@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = WebApplicationConfig.class, includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class WebApplicationConfig {

	
	
}
