/**
 * @author Bryan Barrantes
 * 
 * Rest controller to load handle operations related
 * 
 * to the step model
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
import com.argSecurity.model.Step;
import com.argSecurity.service.impl.StepServiceImpl;
import com.argSecurity.utils.Constants;
@Secured({"admin","user_management"})
@Controller
@RequestMapping("/step")
public class StepController {
	
	@Autowired
	private StepServiceImpl stepService;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private Date dateSystem;
	
	private final Logger log = LoggerFactory.getLogger(StepController.class);
	
	/**
	 * 
	 * Allows to get a module by ID or a complete list of modules
	 * 
	 * returns a module or a list of modules
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Step> getStepsActive(	/*ID*/
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
	 
		List<com.argSecurity.model.Step> listSteps = stepService.getIsActiveSteps(true);
		 
		List<Step> listStepsDTO = new ArrayList<Step>();
		for(com.argSecurity.model.Step step : listSteps){
			Step stepDTO = dozerMapper.map(step, Step.class);
			listStepsDTO.add(stepDTO);
		}
		return listStepsDTO;
	 }
	
	/**
	 * 
	 * Allows to save module configuration to Database
	 * 
	 * returns a message with result
	 * 
	 * @param name
	 * @param description
	 * @param status
	 * @param trainingmoduleid
	 * @param active
	 * @param createdby
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST})
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message saveStep(	@FormParam("name") String name,
								@FormParam("description") String description,
								@FormParam("status") String status,
								@FormParam("trainingmoduleid") int trainingmoduleid,
								@FormParam("active") boolean active,
								@FormParam("createdby") String createdby
								)
				 					{
		try {
			Step step = new Step();
			
			com.argSecurity.model.Step stepModel = dozerMapper.map(step, com.argSecurity.model.Step.class);
			dateSystem.setTime(System.currentTimeMillis());
			stepModel.setName(name);
			stepModel.setDescription(description);
			stepModel.setStatus(status);
			stepModel.setTrainingModuleId(trainingmoduleid);
			stepModel.setActive(active);
			stepModel.setCreatedDate(dateSystem);
			stepModel.setCreatedBy(createdby);
			stepService.save(stepModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(stepModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "saveStep"), e);
			Message message = new Message(Constants.MessagesCodes.CREATION_ERROR, 
					Constants.appUserData.RESOURCE_CREATION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
	/***
	 * 
	 * Method to edit a training_module record on DB
	 * 
	 * returns a message with result
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param status
	 * @param trainingmoduleid
	 * @param active
	 * @param createdby
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.PUT})
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message editStep(		@FormParam("id") int id,
									@FormParam("name") String name,
									@FormParam("description") String description,
									@FormParam("status") String status,
									@FormParam("trainingmoduleid") int trainingmoduleid,
									@FormParam("active") boolean active,
									@FormParam("createdby") String createdby
								)
				 					{
		try {
			Step step = stepService.findOne(id);
			
			com.argSecurity.model.Step stepModel = dozerMapper.map(step, com.argSecurity.model.Step.class);
			dateSystem.setTime(System.currentTimeMillis());
			stepModel.setName(name);
			stepModel.setDescription(description);
			stepModel.setStatus(status);
			stepModel.setTrainingModuleId(trainingmoduleid);
			stepModel.setActive(active);
			stepModel.setCreatedDate(dateSystem);
			stepModel.setCreatedBy(createdby);
			stepService.save(stepModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(stepModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "editStep"), e);
			Message message = new Message(Constants.MessagesCodes.EDITION_ERROR, 
					Constants.appUserData.RESOURCE_EDITION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
	/***
	 * 
	 * Method to edit a training_module record on DB
	 * 
	 * returns a message with result
	 * 
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(method = {RequestMethod.DELETE})
	@Path("{id}")
	@ResponseBody
	public Message deleteStep(@PathParam("id") int id,HttpServletRequest request, 
		 	HttpServletResponse response) throws IOException {
		 Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		
		try {
			Step step = stepService.findOne(id);
			if(step == null){
				Message message = new Message(Constants.MessagesCodes.DELETION_ERROR, Constants.AppMessages.WRONG_DATA);
				return message;
			}
			stepService.delete(id);
			Message message = new Message(Constants.MessagesCodes.OK, Constants.appUserData.DELETED.getValue());
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "deleteStep"), e);
			Message message = new Message(Constants.MessagesCodes.DELETION_ERROR, 
					Constants.appUserData.RESOURCE_DELETION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}

}