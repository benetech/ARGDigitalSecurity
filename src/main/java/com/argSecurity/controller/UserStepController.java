/**
 * User Steps controller
 * 
 * @author Bryan Barrantes 
 * 
 * Benetech trainning app Copyrights reserved
 * 
 */
package com.argSecurity.controller;

import java.io.IOException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.model.Message;
import com.argSecurity.model.UserStep;
import com.argSecurity.service.impl.UserStepServiceImpl;
import com.argSecurity.utils.Constants;
@Secured({"admin","user_management"})
@Controller
@RequestMapping("/userstep")
public class UserStepController {
	
	@Autowired
	private UserStepServiceImpl userStepServices;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private Date dateSystem;
	
	private final Logger log = LoggerFactory.getLogger(UserStepController.class);
	
	/**
	 * Allows to get a step by ID or a complete list of steps
	 * 
	 * returns a step or a list of steps
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<UserStep> getUserStepsActive(	/*ID*/
										 	HttpServletRequest request, 
										 	HttpServletResponse response) throws IOException{
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		List<com.argSecurity.model.UserStep> listUserSteps = userStepServices.getIsActiveUserSteps(true);
		 
		List<UserStep> listUserStepsDTO = new ArrayList<UserStep>();
		for(com.argSecurity.model.UserStep userStep : listUserSteps){
			UserStep userStepDTO = dozerMapper.map(userStep, UserStep.class);
			listUserStepsDTO.add(userStepDTO);
		}
		return listUserStepsDTO;
	 }
	
	/**
	 * 
	 * Allows to save user step to Database
	 * 
	 * returns a message with result
	 * 
	 * @param userid
	 * @param stepid
	 * @param status
	 * @param active
	 * @param createdby
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST})
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message saveUserStep(	@FormParam("userid") int userid,
								@FormParam("stepid") int stepid,
								@FormParam("status") String status,
								@FormParam("active") boolean active,
								@FormParam("createdby") String createdby
								)
				 					{
		try {
			UserStep userStep = new UserStep();
			
			com.argSecurity.model.UserStep userStepModel = dozerMapper.map(userStep, com.argSecurity.model.UserStep.class);
			dateSystem.setTime(System.currentTimeMillis());
			userStepModel.setUserId(userid);
			userStepModel.setStepId(stepid);
			userStepModel.setStatus(status);
			userStepModel.setActive(active);
			userStepModel.setCreatedDate(dateSystem);
			userStepModel.setCreatedBy(createdby);
			userStepServices.save(userStepModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(userStepModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "saveUserStep"), e);
			Message message = new Message(Constants.MessagesCodes.CREATION_ERROR, 
					Constants.appUserData.RESOURCE_CREATION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
	/**
	 * 
	 * Method to edit a user_training_step record on DB
	 * 
	 * returns a message with result
	 * 
	 * @param id
	 * @param userid
	 * @param stepid
	 * @param status
	 * @param active
	 * @param createdby
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.PUT})
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message editUserStep(	@FormParam("id") int id,
									@FormParam("userid") int userid,
									@FormParam("stepid") int stepid,
									@FormParam("status") String status,
									@FormParam("active") boolean active,
									@FormParam("createdby") String createdby
								)
				 					{
		try {
			UserStep userStep = userStepServices.findOne(id);
			
			com.argSecurity.model.UserStep userStepModel = dozerMapper.map(userStep, com.argSecurity.model.UserStep.class);
			dateSystem.setTime(System.currentTimeMillis());
			userStepModel.setUserId(userid);
			userStepModel.setStepId(stepid);
			userStepModel.setStatus(status);
			userStepModel.setActive(active);
			userStepModel.setCreatedDate(dateSystem);
			userStepModel.setCreatedBy(createdby);
			userStepServices.save(userStepModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(userStepModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "editUserStep"), e);
			Message message = new Message(Constants.MessagesCodes.EDITION_ERROR, 
					Constants.appUserData.RESOURCE_EDITION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
	/*
	 * Method to edit a training_step record on DB
	 * 
	 * returns a message with result
	 * 
	 *
	@RequestMapping(method = {RequestMethod.DELETE})
	@Path("{id}")
	@ResponseBody
	public Message deleteStep(@PathParam("id") int id) {
		try {
			Step step = stepServices.findOne(id);
			if(step == null){
				Message message = new Message(Constants.MessagesCodes.DELETION_ERROR, Constants.AppMessages.WRONG_DATA);
				return message;
			}
			stepServices.delete(id);
			Message message = new Message(Constants.MessagesCodes.OK, Constants.appUserData.DELETED.getValue());
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "deleteStep"), e);
			Message message = new Message(Constants.MessagesCodes.DELETION_ERROR, 
					Constants.appUserData.RESOURCE_DELETION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}*/

}