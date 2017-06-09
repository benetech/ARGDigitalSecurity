/**
 * Loading spring security configuration and authenticate process
 * 
 * @author daniela.depablos
 * 
 *	Benetech trainning app Copyrights reserved
 */
package com.argSecurity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.argSecurity.service.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig
    extends WebSecurityConfigurerAdapter  {
	
	@Autowired
    @Qualifier("userServiceImpl")
    private UserServiceImpl userServiceImpl;
	
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl);
         auth.authenticationProvider(authenticationProvider());
    }
	/**
	 * Loading encrypter for the pasword
	 * @return
	 */
	 @Bean
	  public PasswordEncoder passwordEncoder() {
		  
	   	  return new BCryptPasswordEncoder();
		  
	  }
	 	/**
	 	 * Loading configuration for the authentication provider
	 	 * 
	 	 * encrypter and verification of the locking account
	 	 * @return
	 	 */
	 	@Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userServiceImpl);
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        authenticationProvider.setPreAuthenticationChecks(new UserDetailsChecker() {
				
				@Override
				public void check(UserDetails user) {
					// TODO Auto-generated method stub
					if(!user.isAccountNonLocked()){
						throw new LockedException("Account is locked"+user.getUsername());
					}
					
				}
			});
	        return authenticationProvider;
	    }
	 /**
	  * Disabling crsf token
	  */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    
    
//    protected void registerAuthentication(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
////        authManagerBuilder
////            .inMemoryAuthentication()
////                .withUser("admin").password("admin");
//    }
}