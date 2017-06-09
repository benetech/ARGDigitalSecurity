/**
 * Spring MVC controller for the login actions.<br/>
 * The Spring Security configuration make this controller the only one reachable without a valid authentication. It's goal is to handle Spring
 * Security context to publish Ajax compatible status, login and logout commands.<br/>
 * "@Controller" the Spring MVC controller nature<br/>
 * "@RequestMapping("/login")" map the request mapping of all the methods under "/login"<br/>
 * <p/>
 * @author daniela.depablos
 *  
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.model.User;
import com.argSecurity.model.dto.LoginStatus;
import com.argSecurity.model.dto.Role;
import com.argSecurity.service.impl.UsersServiceValidationsImpl;


@Controller
@RequestMapping("/login")
public class    LoginController {

  /**
   * Spring Security center object "authentication manager"
   */
  @Autowired
  @Qualifier("authenticationManager")
  private AuthenticationManager authenticationManager;

  @Autowired
  private UsersServiceValidationsImpl userServiceValid;
  
  
  @Autowired
  private Environment environment;
  
  /**
   * Spring Security default remember me service which provides direct access to the context from the remember me cookie.
   */
  @Autowired
  private RememberMeServices rememberMeServices;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  private final static Logger log = Logger.getLogger(LoginController.class);

  /**
   * Get login status request. RequestMapping is done on the HTTP method GET. Check authentication on session and if not found, from remember me
   * cookie.
   *
   * @param request  HttpServletRequest provided by Spring MVC and needed to use remember me service.
   * @param response HttpServletResponse provided by Spring MVC and needed to use remember me service.
   * @return LoginStatus instance corresponding to the status found serialized in JSON because of "@ResponseBody" which triggered serialization,
   *         presence of Jackson library in classpath which allow JSON serialization and request header which ask for JSON.
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public LoginStatus getStatus(HttpServletRequest request, HttpServletResponse response) {
    Authentication authentication = getSessionAuthentication(request);
    if (isAuthenticated(authentication)) {
      return authenticationToLoginStatus(authentication,"Login-M0001","User is authenticated",1);
    }
    Authentication rememberMeAuthentication = rememberMeServices.autoLogin(request, response);
    if (isAuthenticated(rememberMeAuthentication)) {
      return authenticationToLoginStatus(rememberMeAuthentication,"Login-M0001","User is authenticated",1);
    }
    return authenticationToLoginStatus(authentication,"Login-E0004","Non Authenticated",3);
  }

  /**
   * Login request. RequestMapping is done on the HTTP method POST. Try to log user with the HTTP parameters. Also trigger remember me cookie if
   * asked.
   *
   * @param request     HttpServletRequest provided by Spring MVC and needed to use remember me service.
   * @param response    HttpServletResponse provided by Spring MVC and needed to use remember me service.
   * @param loginStatus login information automatically de-serialized from JSON by Spring MVC. Contains login, password and remember me choice
   * @return the new login status after login. The LoginStatus instance is serialized in JSON because of "@ResponseBody" which triggered
   *         serialization, presence of Jackson library in classpath which allow JSON serialization and request header which ask for JSON.
   */
  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
  @ResponseBody
  public LoginStatus login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginStatus loginStatus) {
    Authentication authentication = null;
    String code="", message="";
    int typeMessage=0;
    try {
    	
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginStatus.getUsername().toLowerCase(), loginStatus.getPassword());
      authentication = authenticationManager.authenticate(token);
      
      setSessionAuthentication(request, authentication);
      
      code="Login-M0001";
      message="User is authenticated";
      typeMessage=1;
    } catch (BadCredentialsException e) {
    	log.error(e.getMessage());
    	authentication = SecurityContextHolder.getContext().getAuthentication();
      
      try{
		      int attempts = userServiceValid.getAttempts(loginStatus.getUsername());
		      if(attempts <5){
		    	  userServiceValid.updateAttempts(loginStatus.getUsername());
		      }else if(userServiceValid.getAccountStatus(loginStatus.getUsername())){
		    	  //Locking account
		    	  userServiceValid.lockedAndUnlockedAccount(loginStatus.getUsername(), false);
		      }
	      	
  		}catch(Exception exc){
  			log.error(exc.getMessage());
  			
	  		
  		}finally{
  			code="Login-E0001";
	  		message="We don't recognize your Username and/or Password. Please try again";
	  		typeMessage=3;
  		}
    }catch (LockedException e) {
		// TODO: handle exception
    	log.error(e.getMessage());
    	authentication = SecurityContextHolder.getContext().getAuthentication();
    	code="Login-E0003";
    	message="Account is locked";
    	typeMessage=3;
    	
	}
    
    
    if (loginStatus.getRememberMe() != null && loginStatus.getRememberMe().equals(environment.getRequiredProperty("rememberme.on")) && isAuthenticated(authentication)) {
      rememberMeServices.loginSuccess(request, response, authentication);
    }
    return authenticationToLoginStatus(authentication,code,message,typeMessage);
  }

  /**
   * Logout request. RequestMapping is done on the HTTP method DELETE. Logout the user by removing context in session and calling remember me service.
   *
   * @param request  HttpServletRequest provided by Spring MVC and needed to use remember me service.
   * @param response HttpServletResponse provided by Spring MVC and needed to use remember me service.
   * @throws IOException Can appends when writing the "logged out" message on response stream.
   */
  @RequestMapping(method = RequestMethod.DELETE)
  @ResponseBody
  public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
    setSessionAuthentication(request, null);
    log.info("Id en logout "+session.getId());
    session.invalidate();
    rememberMeServices.loginFail(request, response);
    response.getWriter().println("logged out");
  }

  /**
   * Get current session authentication.<br/>
   * As this controller is out of Spring Security filter, the standard way to access Security Context (SecurityContextHolder.getContext()) is not
   * working and we have to do it the old way.
   *
   * @param request HttpServletRequest provided by Spring MVC to look for session attributes.
   * @return The current authentication in session or null if there is none.
   */
  private Authentication getSessionAuthentication(HttpServletRequest request) {
    SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
    if (securityContext == null) {
      return null;
    } else {
      return securityContext.getAuthentication();
    }
  }

  /**
   * Set the authentication in session.<br/>
   * As this controller is out of Spring Security filter, the standard way to access Security Context (SecurityContextHolder.getContext()) is not
   * working and we have to do it the old way.
   *
   * @param request        HttpServletRequest provided by Spring MVC to write session attributes.
   * @param authentication authentication to set
   */
  private void setSessionAuthentication(HttpServletRequest request, Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
    request.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    
  }

  /**
   * Check if an authentication is a logged user. As Spring Security handle authentication instances which are not real logged user but can be
   * anonymous authentication for example, this method allow to test it simply.
   *
   * @param authentication authentication to test
   * @return true if the authentication is for a real user and false in all other cases
   */
  private boolean isAuthenticated(Authentication authentication) {
    return authentication != null && !authentication.getName().equals("anonymousUser") && authentication.isAuthenticated();
  }

  /**
   * Convert Spring Security authentication to LoginStatus DTO.
   *
   * @param authentication authentication to convert
   * @return LoginStatus instance corresponding to the authentication
   */
  private LoginStatus authenticationToLoginStatus(Authentication authentication, String code, String message, int typeMessage) {
    LoginStatus loginStatus = new LoginStatus();
    if (isAuthenticated(authentication)) {
      loginStatus.setLoggedIn(true);
      loginStatus.setUsername(authentication.getName());
      User userlogin = (User) authentication.getPrincipal();
      userlogin.setAttempts(0);
      userServiceValid.saveUSer(userlogin);
      Role role = new Role();
      role.setId(userlogin.getRole().getId());
      role.setName(userlogin.getRole().getName());
      loginStatus.setRole(role);
    } else {
      loginStatus.setLoggedIn(false);
      loginStatus.setUsername(null);
    }
    loginStatus.setCode(code);
    loginStatus.setMessage(message);
    loginStatus.setIdTypeMessage(typeMessage);
    return loginStatus;
  }

}