/**
 * Allows to load data for the useraccountmanagement, fieldworker 
 * 
 * and to manage data for the users of benetech
 * 
 * @author daniela.depablos
 *
 *Benetech trainning app
 *
 */
package com.argSecurity.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.model.Role;
import com.argSecurity.model.Team;
import com.argSecurity.model.UserModule;
import com.argSecurity.model.UserTeam;
import com.argSecurity.model.dto.User;
import com.argSecurity.service.impl.ModuleServiceImpl;
import com.argSecurity.service.impl.RoleServiceImpl;
import com.argSecurity.service.impl.StepServiceImpl;
import com.argSecurity.service.impl.TeamServiceImpl;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserStepServiceImpl;
import com.argSecurity.service.impl.UserTeamServiceImpl;
import com.argSecurity.service.impl.UsersServiceValidationsImpl;
import com.google.gson.Gson;


@Controller
@RequestMapping("/rest/users")
public class UserController {
	
	@Autowired
	private UsersServiceValidationsImpl usersServices;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleServiceImpl roleServices;
	
	@Autowired
	private TeamServiceImpl teamServices;
	
	@Autowired 
	private UserTeamServiceImpl userTeamServices;
	
	@Autowired
	private UserModuleServiceImpl userModuleServices;
	
	@Autowired
	private ModuleServiceImpl moduleServices;
	
	@Autowired
	private StepServiceImpl stepServices;
	
	@Autowired
	private UserStepServiceImpl userStepServices;
	
	private final static Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	private Date dateSystem;
		/**
		 * allows to load the useraccountmanagement
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException 
		 */
	 
	 @RequestMapping(path="/usersmanagements",method = RequestMethod.GET)
	 @ResponseBody
	 public List<User> getUsersActive(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		 
		 
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
		 List<com.argSecurity.model.User> listUsers = usersServices.getIsActiveUsers(true);
		 Role role=roleServices.findRoleByName("user_management");
		 com.argSecurity.model.dto.Role roleDto =dozerMapper.map(role, com.argSecurity.model.dto.Role.class);
		 List<User> listUsersDTO = new ArrayList<User>();
		 for(com.argSecurity.model.User user : listUsers){
			 User userDTO =dozerMapper.map(user, User.class);
			 	  if(userDTO.getRole().getId()== roleDto.getId())	
			 		  listUsersDTO.add(userDTO);
			 
		 }
		 
		 Comparator<User> byFirstName = (e1, e2) -> e1
		            .getFirstName().compareTo(e2.getFirstName());
		 
		 Comparator<User> byLastName = (e1, e2) -> e1
		            .getLastName().compareTo(e2.getLastName());
		 
		 listUsersDTO.sort((p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()));
		 
		 listUsersDTO.stream().sorted(byFirstName.thenComparing(byLastName));
		 
		
		 
		 return listUsersDTO;
	 }
	 
	 /**
	  * allows to load the fieldworkers
	  * @param request
	  * @param response
	  * @return
	 * @throws IOException 
	  */
	 @Secured({"admin","user_management"})
	 @RequestMapping(path="/fieldworkers/{username}", method = RequestMethod.GET)
	 @ResponseBody
	 public List<User> getUsersFieldWorkersActive(HttpServletRequest request, HttpServletResponse response,
			 @PathVariable(value="username") String username) throws IOException{
		 
		 //com.argSecurity.model.User userlogin = (com.argSecurity.model.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
  		 List<com.argSecurity.model.User> listUsers = usersServices.findActivesAndByLogin(userlogin.getUsername(),true);
		 Role role=roleServices.findRoleByName("field-workers");
		 com.argSecurity.model.dto.Role roleDto =dozerMapper.map(role, com.argSecurity.model.dto.Role.class);
		 List<User> listUsersDTO = new ArrayList<User>();
		 for(com.argSecurity.model.User user : listUsers){
			 User userDTO =dozerMapper.map(user, User.class);
			 		if(userDTO.getRole().getId()== roleDto.getId())	{
			 			List<UserModule> userModules = userModuleServices.getUserModulesActives(user, true);
			 			int stepsMod=0, stepsPassed=0;
			 			stepsPassed+=userStepServices.getIsUserStepsAndIsActiveAndStatus(user.getId(), true, "passed");
		 				stepsPassed+=userStepServices.getIsUserStepsAndIsActiveAndStatus(user.getId(), true, "completed");
			 			for(UserModule userMod: userModules){
			 				
			 				stepsMod+=stepServices.getStepsByModuleCount(userMod.getModuleId());
			 				
			 				
			 				
			 				
			 			}// end user modules 
			 			userDTO.setStepsCompleted(stepsPassed+"/"+stepsMod);
			 			int average=0;
			 			if(stepsMod >0)
			 				average=(stepsPassed*100)/stepsMod;
			 			
			 			userDTO.setStepsCompletedAverage(String.valueOf(average));
			 			
			 			userDTO.setLinkedToTeam((userTeamServices.findTeamActives(user.getId(), true) > 0 ?true:false));
			 			
			 			listUsersDTO.add(userDTO);
			 			
			 		}
			 		  
			 
		 }
		 Comparator<User> byFirstName = (e1, e2) -> e1
		            .getFirstName().compareTo(e2.getFirstName());
		 
		 Comparator<User> byLastName = (e1, e2) -> e1
		            .getLastName().compareTo(e2.getLastName());
		 
		 listUsersDTO.sort((p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()));
		 
		 listUsersDTO.stream().sorted(byFirstName.thenComparing(byLastName));
		 
		 return listUsersDTO;
		 
	
	 }
	 
	 /**
	  * allows to save user data 
	  * @param request
	  * @param response
	  * @param user
	  * @return
	  */
	 @Secured({"admin", "user_management"})
	 @RequestMapping(method = {RequestMethod.POST})
	 @ResponseBody 
	  public User saveUser(@Valid @RequestBody User user, HttpServletResponse response)throws IOException{
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
		 
		 
		 Role role=null;
		 
		 com.argSecurity.model.User userModel = new com.argSecurity.model.User();
		 
		 userModel.setFirstName(user.getFirstName());
		 
		 userModel.setLastName(user.getLastName());
		 
		 userModel.setEmail(user.getEmail());
		 
		 userModel.setPhone(user.getPhone());
		 
		 userModel.setAccountStatus(true);
		 
		 dateSystem.setTime(System.currentTimeMillis());
		 
		 userModel.setCreatedDate(dateSystem);
		 
		 userModel.setCreatedBy(userlogin.getUsername());
		 
		 userModel.setActive(true);
		 
		 if((user.getRole() != null) && user.getRole().getName().equalsIgnoreCase("user_management")){
			 	
			 userModel.setPassword(passwordEncoder.encode(user.getPassword()));
			 
			 userModel.setUsername(user.getUsername());
			 
			 role = roleServices.findRoleByName("user_management");
			 
			 userModel.setRole(role);
			 
			 Team team = new Team();
			 
			 team.setIdActive(true);
			 
			 team.setName(user.getEmail()+"-Team");
			 
			 team.setCreatedBy(userlogin.getUsername());
			 
			 team.setCreatedDate(dateSystem);
			 
			 team = teamServices.save(team);
			 
			 UserTeam userTeam = new UserTeam();
			 
			 userTeam.setTeam(team);
			 
			 userTeam.setUser(userModel);
			 
			 userTeam.setCreatedBy(userlogin.getUsername());
			 
			 userTeam.setCreatedDate(dateSystem);
			 
			 userTeam.setActive(true);
			 
			 userTeamServices.save(userTeam);
			 
			
			 
			 
			 
		 }else{
			 
			 role = roleServices.findRoleByName("field-workers");
			 
			 
			 userModel.setRole(role);
			 
			 Iterator<UserTeam> uIterator = userlogin.getUserTeam().iterator();
			 
			 UserTeam userTeam=null;
			 
			 while(uIterator.hasNext()){
				 userTeam = uIterator.next();
			 }
			 
			 if(userTeam != null){
			 
			    Team team = teamServices.findbyId(userTeam.getTeam().getId());
			 	
			 	UserTeam userTeamModel = new UserTeam();
			 	
			 	userTeamModel.setCreatedBy(userlogin.getUsername());
			 	
			 	userTeamModel.setCreatedDate(dateSystem);
			 	
			 	userTeamModel.setTeam(team);
			 	
			 	userTeamModel.setUser(userModel);
			 	
			 	userTeamModel.setActive(true);
			 	
			 	userTeamModel =userTeamServices.save(userTeamModel);

			 }
			 
		 }
		 
		 
		 
		 
		 usersServices.saveUSer(userModel);
		 
		 user.setId(userModel.getId());
		 
		 user.setPassword(userModel.getPassword());
		 
		 user.setLinkedToTeam(true);
		 
		 user.setCode("userSaveSuccess");
		 
		 user.setIdTypeMessage(1);
		 
		 return user;
		 
	 }
	 
	 /**
	  * allows to update user data
	  * 
	  * @param request
	  * @param response
	  * @param user
	  * @return
	 * @throws IOException 
	  */
	 @Secured({"admin","user_management"})
	 @RequestMapping(path="/{id}", method = {RequestMethod.PUT})
	 @ResponseBody 
	  public User updateUser(@Valid @RequestBody User user, HttpServletResponse response) throws IOException{
		 
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
		 com.argSecurity.model.User userModel = usersServices.findUser(user.getId());
		 
		 userModel.setEmail(user.getEmail());
		 
		 userModel.setFirstName(user.getFirstName());
		 
		 userModel.setLastName(user.getLastName());
		 
		 userModel.setPhone(user.getPhone());
		 
		 
		 
		 if(userModel.getRole().getName().equalsIgnoreCase("user_management")){
			 
			 if(!passwordEncoder.matches(userModel.getPassword(), passwordEncoder.encode(user.getPassword()))){
				 
				 userModel.setPassword(user.getPassword());
					
				 userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
				 
			 }
			 
			 if( !userModel.isAccountStatus() && user.isAccountStatus() && user.getCode().equalsIgnoreCase("5") ){
				 
				 usersServices.lockedAndUnlockedAccount(userModel.getUsername(), user.isAccountStatus());
				 
				 userModel.setAttempts(0);
				 
			 }
			 
			 
		 }
		 
		 
		 dateSystem.setTime(System.currentTimeMillis());
		 
		 userModel.setModifiedDate(dateSystem);
		 
		 userModel.setModifiedBy(userlogin.getUsername());
		 
		 if(user.isLinkedToTeam() && userModel.getRole().getName().equalsIgnoreCase("field-workers") && (user.getCode() != null && user.getCode().equalsIgnoreCase("2"))){
			 
			// SessionFactory sessionfactory  = new AnnotationConfiguration().configure().buildSessionFactory();
			 if(! (userTeamServices.findTeamActives(user.getId(), true) > 0)) {
				 //Session session = sessionfactory.openSession();
				 Iterator<UserTeam> uIterator = userlogin.getUserTeam().iterator();
				 
				 UserTeam userTeam=null;
				 
				 while(uIterator.hasNext()){
					 userTeam = uIterator.next();
				 }
				 
				 if(userTeam != null){
				 
				    Team team = teamServices.findbyId(userTeam.getTeam().getId());
				 	
				 	UserTeam userTeamModel = new UserTeam();
				 	
				 	userTeamModel.setCreatedBy(userlogin.getUsername());
				 	
				 	userTeamModel.setCreatedDate(dateSystem);
				 	
				 	userTeamModel.setTeam(team);
				 	
				 	userTeamModel.setActive(true);
				 	
				 	userTeamModel.setUser(userModel);
				 	
				 	userTeamModel =userTeamServices.save(userTeamModel);

				 } 
			 }
			 
			
			 
			 
			 
		 }else  if(((!user.isLinkedToTeam()) && userModel.getRole().getName().equalsIgnoreCase("field-workers")) && (user.getCode() != null && user.getCode().equalsIgnoreCase("2"))){
			 //Avoiding ConcurrentModification Exception
			 
			 if( ((userModel.getUserTeam() != null) && (userModel.getUserTeam().size() >0))) {
				 List< UserTeam > list = new CopyOnWriteArrayList< UserTeam >();
				 
				 list.addAll(userModel.getUserTeam());
				 
				 for (UserTeam userTeam :list) {	
					
					    if (userTeam != null) {  
					    	
					                
					    	userTeam.setDeletedBy(userlogin.getUsername());
							 
				    		userTeam.setDeletedDate(dateSystem);
						 
				    		userTeam.setActive(false);
				 
			    		    userTeamServices.save(userTeam);
				
					    }
					 
					}
			 }
			
			 
			 
		 }
		 
		 
		 usersServices.saveUSer(userModel);
		 
		 user.setCode("userUpdateSuccess");
		 
		 user.setIdTypeMessage(1);
		 
		 
		 return user;
		 
	 }
	 /**
	  * allows to delete user
	  * 
	  * @param id
	  * @param request
	  * @param response
	  * @return
	 * @throws IOException 
	  */
	 @Secured({"admin","user_management"})
	 @RequestMapping(path="/{id}", method = {RequestMethod.DELETE})
	 @ResponseBody 
	  public User deleteUser(@PathVariable("id") int id,HttpServletRequest request, HttpServletResponse response) throws IOException{
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
		
		 
		 com.argSecurity.model.User userModel =  usersServices.findUser(id);
		 
		 
//		 
		
//		 
		 dateSystem.setTime(System.currentTimeMillis());
//		 
		 userModel.setActive(false);
//		 
		 userModel.setDeletedDate(dateSystem);
//		 
		 userModel.setDeletedBy(userlogin.getUsername());
		 
		 
//		 
		 usersServices.saveUSer(userModel);
		 
		 com.argSecurity.model.dto.User user =
				 
		 dozerMapper.map(userModel, com.argSecurity.model.dto.User.class);
		
		 
		 
		 user.setCode("User updated succesfully");
		 	 
		 user.setIdTypeMessage(1);
		 
		 
		 return user;
		 
	 }
	 @Secured({"admin","user_management"})
	 @RequestMapping(path="/singleData/{id}", method = RequestMethod.GET)
	 @ResponseBody 
	 public User getFieldworkerData(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") int id) throws IOException{
		 
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 
		 com.argSecurity.model.User userModel =  usersServices.findUser(id);
		 User userDTO =dozerMapper.map(userModel, User.class);
		 List<UserModule> userModules = userModuleServices.getUserModulesActives(userModel, true);
			int stepsMod=0, stepsPassed=0;
			
			for(UserModule userMod: userModules){
				stepsPassed+=userStepServices.getUserStepsCount(userModel.getId(), true, "passed",userMod.getModuleId());
				stepsPassed+=userStepServices.getUserStepsCount(userModel.getId(), true, "completed",userMod.getModuleId());
				stepsMod+=stepServices.getStepsByModuleCount(userMod.getModuleId());
				
				
				
				
				
			}// end user modules 
			userDTO.setStepsCompleted(stepsPassed+"/"+stepsMod);
			int average=0;
			if(stepsMod >0)
				average=(stepsPassed*100)/stepsMod;
			
			userDTO.setStepsCompletedAverage(String.valueOf(average));
			
			userDTO.setLinkedToTeam((userTeamServices.findTeamActives(userModel.getId(), true) > 0 ?true:false));
			
			

		 
		 return userDTO;
	 }
	 
	 
	 @Secured({"admin","user_management"})
	 @RequestMapping(path="/emails", method = RequestMethod.GET)
	 @ResponseBody 
	 public List<User> getEmailsData(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 List<com.argSecurity.model.User> userModels= usersServices.getIsActiveUsers(true);
		
		 List<User> listUsersDTO = new ArrayList<User>();
		 for(com.argSecurity.model.User user : userModels){
			 User userDTO =dozerMapper.map(user, User.class);
					if(user.getRole().getName().equalsIgnoreCase("field-workers"))
			 	  	{
			 		  listUsersDTO.add(userDTO);
					}
		 }
		 return listUsersDTO;
		 
		
	 }
	 
	 @Secured({"admin","user_management"})
	 @RequestMapping(path="/emailsmgr", method = RequestMethod.GET)
	 @ResponseBody 
	 public List<User> getEmailsDataMgr(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		 List<com.argSecurity.model.User> userModels= usersServices.getIsActiveUsers(true);
		
		 List<User> listUsersDTO = new ArrayList<User>();
		 for(com.argSecurity.model.User user : userModels){
			 User userDTO =dozerMapper.map(user, User.class);
					if(!user.getRole().getName().equalsIgnoreCase("field-workers"))
			 	  	{
			 		  listUsersDTO.add(userDTO);
					}
		 }
		 return listUsersDTO;
		 
		
	 }
	 
	 
	 
}