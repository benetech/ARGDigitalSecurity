/**
 * 
 *  Manage Module data
 * @author bryan.barrantes
 * 
 * 
 * Benetech trainning app Copyrights reserved
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.dozer.DozerBeanMapper;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argSecurity.model.Message;
import com.argSecurity.model.Condition;
import com.argSecurity.service.impl.ConditionServiceImpl;
import com.argSecurity.utils.Constants;


@Secured({"admin","user_management"})
@Controller
@RequestMapping("/condition")
public class ConditionController {
	
	@Autowired
	private ConditionServiceImpl conditionService;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private Date dateSystem;
	
	private final Logger log = LoggerFactory.getLogger(ConditionController.class);
	
	/**Allows to get a module by ID or a complete list of modules
	 * 
	 * returns a module or a list of modules*
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Condition> getConditionsActive(
										 	HttpServletRequest request, 
										 	HttpServletResponse response){
	 
		List<com.argSecurity.model.Condition> listConditions = conditionService.getIsActiveConditions(true);
		 
		List<Condition> listStepsDTO = new ArrayList<Condition>();
		for(com.argSecurity.model.Condition condition : listConditions){
			Condition conditionDTO = dozerMapper.map(condition, Condition.class);
			listStepsDTO.add(conditionDTO);
		}
		return listStepsDTO;
	 }
	
	/***
	 * 
	 * Allows to save module configuration to Database
	 * 
	 * returns a message with result
	 * 
	 * @param name
	 * @param timer
	 * @param trueoutcome
	 * @param falseoutcome
	 * @param stepid
	 * @param active
	 * @param createdby
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST})
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message saveCondition(	@FormParam("name") String name,
									@FormParam("timer") String timer,
									@FormParam("trueoutcome") String trueoutcome,
									@FormParam("falseoutcome") String falseoutcome,
									@FormParam("stepid") int stepid,
									@FormParam("active") boolean active,
									@FormParam("createdby") String createdby
								)
				 					{
		try {
			Condition condition = new Condition();
			
			com.argSecurity.model.Condition conditionModel = dozerMapper.map(condition, com.argSecurity.model.Condition.class);
			dateSystem.setTime(System.currentTimeMillis());
			conditionModel.setName(name);
			conditionModel.setTimer(timer);
			conditionModel.setTrueOutcome(trueoutcome);
			conditionModel.setFalseOutcome(falseoutcome);
			conditionModel.setStepId(stepid);
			conditionModel.setActive(active);
			conditionModel.setCreatedDate(dateSystem);
			conditionModel.setCreatedBy(createdby);
			conditionService.save(conditionModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(conditionModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "saveCondition"), e);
			Message message = new Message(Constants.MessagesCodes.CREATION_ERROR, 
					Constants.appUserData.RESOURCE_CREATION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
	/**
	 * 
	 * Method to edit a training_module record on DB
	 * 
	 * returns a message with result
	 * 
	 * @param id
	 * @param name
	 * @param timer
	 * @param trueoutcome
	 * @param falseoutcome
	 * @param stepid
	 * @param active
	 * @param createdby
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.PUT})
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message editCondition(	@FormParam("id") int id,
									@FormParam("name") String name,
									@FormParam("timer") String timer,
									@FormParam("trueoutcome") String trueoutcome,
									@FormParam("falseoutcome") String falseoutcome,
									@FormParam("stepid") int stepid,
									@FormParam("active") boolean active,
									@FormParam("createdby") String createdby
								)
				 					{
		try {
			Condition condition = conditionService.findOne(id);
			
			com.argSecurity.model.Condition conditionModel = dozerMapper.map(condition, com.argSecurity.model.Condition.class);
			dateSystem.setTime(System.currentTimeMillis());
			conditionModel.setName(name);
			conditionModel.setTimer(timer);
			conditionModel.setTrueOutcome(trueoutcome);
			conditionModel.setFalseOutcome(falseoutcome);
			conditionModel.setStepId(stepid);
			conditionModel.setActive(active);
			conditionModel.setCreatedDate(dateSystem);
			conditionModel.setCreatedBy(createdby);
			conditionService.save(conditionModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(conditionModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "editCondition"), e);
			Message message = new Message(Constants.MessagesCodes.EDITION_ERROR, 
					Constants.appUserData.RESOURCE_EDITION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
	/**
	 * 	Method to edit a training_module record on DB
	 * 
	 * returns a message with result
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.DELETE})
	@Path("{id}")
	@ResponseBody
	public Message deleteCondition(@PathParam("id") int id) {
		try {
			Condition condition = conditionService.findOne(id);
			if(condition == null){
				Message message = new Message(Constants.MessagesCodes.DELETION_ERROR, Constants.AppMessages.WRONG_DATA);
				return message;
			}
			conditionService.delete(id);
			Message message = new Message(Constants.MessagesCodes.OK, Constants.appUserData.DELETED.getValue());
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "deleteCondition"), e);
			Message message = new Message(Constants.MessagesCodes.DELETION_ERROR, 
					Constants.appUserData.RESOURCE_DELETION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}

}