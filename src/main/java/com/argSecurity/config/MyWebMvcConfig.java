/**
 * Adding mvc configuration and directing to the index page
 * 
 * @author daniela.depablos
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcConfig {
	/**
	 * Loading view config 
	 * @return
	 */
    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // forward requests to /admin and /user to their index.html
                registry.addViewController("/admin").setViewName(
                        "forward:/index.html");
//                registry.addViewController("/user").setViewName(
//                        "forward:/user/index.html");
            }
        };
    }

}
