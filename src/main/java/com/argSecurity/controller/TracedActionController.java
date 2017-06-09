/**
 * 
 * Manage Traced Actions
 * @author bryan.barrantes
 * 
 * 
 * Benetech trainning app Copyrights reserved
 *
 */

package com.argSecurity.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

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
import com.argSecurity.model.TracedAction;
import com.argSecurity.service.impl.TracedActionServiceImpl;
import com.argSecurity.utils.Constants;
@Secured({"admin","user_management"})
@Controller
@RequestMapping("/rest/action")
public class TracedActionController {
	
	@Autowired
	TracedActionServiceImpl tracedActionServices;
	
	@Autowired
	DozerBeanMapper dozerMapper;
	
	@Autowired
	Date dateSystem;
	
	@Autowired
	CoreController core;
	
	private final Logger log = LoggerFactory.getLogger(UserModuleController.class);
	
	/**
	 * Allows to get a module by ID or a complete list of modules
	 * 
	 * @param request
	 * @param response
	 * @return  a module or a list of modules
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TracedAction> getTracedActionsActive(
										 	HttpServletRequest request, 
										 	HttpServletResponse response){
	 
		List<com.argSecurity.model.TracedAction> listTracedActions = tracedActionServices.getIsActiveTracedActions(true);
		 
		List<TracedAction> listTracedActionsDTO = new ArrayList<TracedAction>();
		for(com.argSecurity.model.TracedAction tracedAction : listTracedActions){
			TracedAction tracedActionDTO = dozerMapper.map(tracedAction, TracedAction.class);
			listTracedActionsDTO.add(tracedActionDTO);
		}
		return listTracedActionsDTO;
	 }
	
	/**
	 * Allows to save user module to Database
	 * 
	 * @param action
	 * @param objectId
	 * @param identifier
	 * @param parentId
	 * @return  message with result
	 * @throws MessagingException
	 */
	@RequestMapping(path = "/collect", method = RequestMethod.GET)
	@ResponseBody
	public Message collectAction(	@QueryParam("action") String action,
									@QueryParam("objectId") String objectId,
									@QueryParam("identifier") String identifier,
									@QueryParam("parentId") String parentId,
									@QueryParam("sequenceId") String sequenceId) throws MessagingException
				 					{
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(action);
			String decodedAction = new String(decodedBytes);
			
			decodedBytes = Base64.getDecoder().decode(objectId);
			String decodedObject = new String(decodedBytes);
			int userId = Integer.valueOf(decodedObject);
			
			decodedBytes = Base64.getDecoder().decode(parentId);
			decodedObject = new String(decodedBytes);
			int moduleId = Integer.valueOf(decodedObject);
			
			decodedBytes = Base64.getDecoder().decode(sequenceId);
			decodedObject = new String(decodedBytes);
			String ids[]=decodedObject.split("_");
			
			int seqId=Integer.valueOf(ids[1]);
			
			if(action.equals("") || objectId.equals("") || parentId.equals("") || sequenceId.equals("")){
				Message message = new Message(Constants.MessagesCodes.INVALID_DATA, 
						Constants.appUserData.INVALID_REQUEST.getValue());
				return message;
			} else if(tracedActionServices.validateTracedAction(userId, moduleId, decodedAction,seqId) > 0) {
				//then see if the action has already been registered, in which case must be ignored
				Message message = new Message(Constants.MessagesCodes.CREATION_ERROR,
						Constants.appUserData.RESOURCE_CREATION_FAILED.getValue());
				return message;
			} else {
				TracedAction tracedAction = new TracedAction();
				
				com.argSecurity.model.TracedAction actionModel = dozerMapper.map(tracedAction, com.argSecurity.model.TracedAction.class);
				dateSystem.setTime(System.currentTimeMillis());
				actionModel.setUserId(userId);
				actionModel.setModuleId(moduleId);
				actionModel.setAction(decodedAction);
				actionModel.setSequenceId(seqId);
				actionModel.setProcessed(false);
				actionModel.setCreatedBy("Triggered by user");
				actionModel.setActive(true);
				actionModel.setCreatedDate(dateSystem);
				tracedActionServices.save(actionModel);
				Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(actionModel.getId()));
				return message;
			}
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "collectAction"), e);
			Message message = new Message(Constants.MessagesCodes.CREATION_ERROR, 
					Constants.appUserData.RESOURCE_CREATION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
}