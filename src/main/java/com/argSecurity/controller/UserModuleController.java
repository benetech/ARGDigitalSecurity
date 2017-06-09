/**
 * UserModuleController
 * 
 * @author  Bryan Barrantes
 *
 *  allows to load user modules data
 *  
 *  Benetech trainning app Copyrights reserved 
 *  
 */
package com.argSecurity.controller;



import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;

import org.dozer.DozerBeanMapper;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.model.Message;
import com.argSecurity.model.UserModule;
import com.argSecurity.model.dto.User;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserServiceImpl;
import com.argSecurity.utils.Constants;
/**
 * Handling User Module data 
 * 
 * @author bryan.barrantes
 *
 */
@Secured({"admin","user_management"})
@Controller
@RequestMapping("rest/usermodule")
public class UserModuleController {
	
	@Autowired
	private UserModuleServiceImpl userModuleServices;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private Date dateSystem;
	
	private final Logger log = LoggerFactory.getLogger(UserModuleController.class);
	
	/**
	 * Allows to get a module by ID or a complete list of modules
	 * 
	 * returns a module or a list of modules
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<UserModule> getUserModulesActive(
										 	HttpServletRequest request, 
										 	HttpServletResponse response){
	 
		List<com.argSecurity.model.UserModule> listUserModules = userModuleServices.getIsActiveUserModules(true);
		 
		List<UserModule> listUserModulesDTO = new ArrayList<UserModule>();
		for(com.argSecurity.model.UserModule userModule : listUserModules){
			UserModule userModuleDTO = dozerMapper.map(userModule, UserModule.class);
			listUserModulesDTO.add(userModuleDTO);
		}
		return listUserModulesDTO;
	 }
	
	@RequestMapping(path="/{id}" ,method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUserModulesActive(
										 	HttpServletRequest request, 
										 	HttpServletResponse response, @PathVariable(value="id") Integer id){
	 
		List<Integer> ids = userModuleServices.getUserIdsByModuleIdActive(id, true);
		 
		List<User> users = new ArrayList<User>();
		for(Integer userId : ids){
			com.argSecurity.model.User user = userServiceImpl.findById(userId);
			if(user!=null){
				com.argSecurity.model.dto.User userDto=dozerMapper.map(user, com.argSecurity.model.dto.User.class);
				users.add(userDto);
			}
		}
		return users;
	 }
	
/**
 * Allows to save user module to Database
* 
 * returns a message with result
 * @param userid
 * @param moduleid
 * @param active
 * @param createdby
 * @return
 */
	@RequestMapping(method = {RequestMethod.POST})
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message saveUserModule(	@FormParam("userid") int userid,
								@FormParam("moduleid") int moduleid,
								@FormParam("active") boolean active,
								@FormParam("createdby") String createdby
								)
				 					{
		try {
			UserModule userModule = new UserModule();
			
			com.argSecurity.model.UserModule userModuleModel = dozerMapper.map(userModule, com.argSecurity.model.UserModule.class);
			dateSystem.setTime(System.currentTimeMillis());
			userModuleModel.setUserId(userid);
			userModuleModel.setModuleId(moduleid);
			userModuleModel.setActive(active);
			userModuleModel.setCreatedDate(dateSystem);
			userModuleModel.setCreatedBy(createdby);
			userModuleServices.save(userModuleModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(userModuleModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "saveUserModule"), e);
			Message message = new Message(Constants.MessagesCodes.CREATION_ERROR, 
					Constants.appUserData.RESOURCE_CREATION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
/**
 * Method to edit a user_training_module record on DB
	 * 
	 * returns a message with result
 * @param id
 * @param userid
 * @param moduleid
 * @param active
 * @param createdby
 * @return
 */
	@RequestMapping(method = {RequestMethod.PUT})
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message editUserModule(	@FormParam("id") int id,
									@FormParam("userid") int userid,
									@FormParam("moduleid") int moduleid,
									@FormParam("active") boolean active,
									@FormParam("createdby") String createdby
								)
				 					{
		try {
			UserModule userModule = userModuleServices.findOne(id);
			
			com.argSecurity.model.UserModule userModuleModel = dozerMapper.map(userModule, com.argSecurity.model.UserModule.class);
			dateSystem.setTime(System.currentTimeMillis());
			userModuleModel.setUserId(userid);
			userModuleModel.setModuleId(moduleid);
			userModuleModel.setActive(active);
			userModuleModel.setCreatedDate(dateSystem);
			userModuleModel.setCreatedBy(createdby);
			userModuleServices.save(userModuleModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(userModuleModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "editUserModule"), e);
			Message message = new Message(Constants.MessagesCodes.EDITION_ERROR, 
					Constants.appUserData.RESOURCE_EDITION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
	/*
	 * Method to edit a training_module record on DB
	 * 
	 * returns a message with result
	 * 
	 *
	@RequestMapping(method = {RequestMethod.DELETE})
	@Path("{id}")
	@ResponseBody
	public Message deleteModule(@PathParam("id") int id) {
		try {
			Module module = moduleServices.findOne(id);
			if(module == null){
				Message message = new Message(Constants.MessagesCodes.DELETION_ERROR, Constants.AppMessages.WRONG_DATA);
				return message;
			}
			moduleServices.delete(id);
			Message message = new Message(Constants.MessagesCodes.OK, Constants.appUserData.DELETED.getValue());
			return message;
		} catch (ServiceException e) {
			Message message = new Message(Constants.MessagesCodes.DELETION_ERROR, 
					Constants.appUserData.RESOURCE_DELETION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}*/

}