package com.argSecurity.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.model.dto.User;
import com.argSecurity.model.dto.UserPwdDTO;
import com.argSecurity.service.impl.UsersServiceValidationsImpl;

@Secured({"admin","user_management"})
@Controller
@RequestMapping("/rest/updatePwd")
public class UserPwdController {
	@Autowired
	private UsersServiceValidationsImpl usersServices;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 @Secured({"admin", "user_management"})
	 @RequestMapping(path="/update", method = RequestMethod.POST)
	 @ResponseBody 
	 public User updatePassword(@Valid @RequestBody UserPwdDTO userPwd, HttpServletResponse response) throws IOException{
		 
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 User user = new User();
		 
		 if(passwordEncoder.matches(userPwd.getOld(), userlogin.getPassword())){
			 	
			 
			 userlogin.setPassword(passwordEncoder.encode(userPwd.getNewPwd()));
			 
			 usersServices.saveUSer(userlogin);
			 
			 user.setCode("updatedPwd");
			 
			 user.setIdTypeMessage(1);
			 
			 
			 
		 }else{
			 
			 user.setCode("passwordOldWrong");
			 
			 user.setIdTypeMessage(3);
		 }
		 
		 
		 
		return user;
	 }
	
}
