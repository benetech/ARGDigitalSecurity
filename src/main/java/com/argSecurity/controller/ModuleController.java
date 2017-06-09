/**
 * Manage Module data
 * 
 * @author bryan.barrantes
 *  
 *  Benetech trainning app Copyrights reserved
 */

package com.argSecurity.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dozer.DozerBeanMapper;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.argSecurity.model.Condition;
import com.argSecurity.model.Message;
import com.argSecurity.model.Pair;
import com.argSecurity.model.Parameter;
import com.argSecurity.model.UserCondition;
import com.argSecurity.model.UserModule;
import com.argSecurity.model.UserStep;
import com.argSecurity.model.dto.Module;
import com.argSecurity.model.dto.Step;
import com.argSecurity.service.impl.ConditionServiceImpl;
import com.argSecurity.service.impl.ModuleServiceImpl;
import com.argSecurity.service.impl.ParameterServiceImpl;
import com.argSecurity.service.impl.StepServiceImpl;
import com.argSecurity.service.impl.UserConditionServiceImpl;
import com.argSecurity.service.impl.UserModuleServiceImpl;
import com.argSecurity.service.impl.UserStepServiceImpl;
import com.argSecurity.utils.Constants;

@Secured({"admin","user_management"})
@Controller
@RequestMapping("/rest/module")
public class ModuleController {

	@Autowired
	private ModuleServiceImpl moduleServices;

	@Autowired
	private StepServiceImpl stepService;

	@Autowired
	private ParameterServiceImpl parameterService;

	@Autowired
	private ConditionServiceImpl conditionService;
	
	@Autowired
	private UserModuleServiceImpl userModuleService;
	
	@Autowired
	private UserStepServiceImpl userStepService;
	
	@Autowired
	private UserConditionServiceImpl userConditionService;

	@Autowired
	private DozerBeanMapper dozerMapper;

	@Autowired
	private Date dateSystem;
	
	private final Logger log = LoggerFactory.getLogger(ModuleController.class);

	@Value("${base.upload.path}")
	private String UPLOAD_PATH;
	
	private List<String> module = new ArrayList<String>();
	private String stepResponse = "";
	private final static int startIndex = 0;
	private final static int moduleName = 3;
	private final static int moduleDescription = 6;
	private final static int moduleModel = 9; // 1-time vs recurrent
	private final static int moduleDuration = 12;
	private final static int moduleTimeUnit = 15;
	private final static int moduleDate = 18;
	private final static int stepsIndex = 21;
	private List<String> moduleData = new ArrayList<String>();
	private List<String> stepData = new ArrayList<String>();	
	private int stepSequence = 0;
	private int parameterSequence = 0;
	private List<Integer> stepIds = new ArrayList<Integer>();
	private List<Pair> conditionData = new ArrayList<Pair>();
	private List<Pair> parameterData = new ArrayList<Pair>();


	/**
	 * Allows to get a module by ID or a complete list of modules
	 * @param request
	 * @param response
	 * @return a module or a list of modules
	 * @throws IOException 
	 */
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Module> getModulesActive(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		
		List<com.argSecurity.model.Module> listModules = moduleServices.getIsActiveModules(true);
		
		List<Module> listModulesDTO = new ArrayList<>();
		for (com.argSecurity.model.Module module : listModules) {
			
			List<com.argSecurity.model.Step> steps = stepService.getStepsByModuleActive(module.getId(), module.isActive());
			
			List<Step> listStepsDTO = new ArrayList<>();			
			for(com.argSecurity.model.Step step: steps) {
				Step stepDTO = dozerMapper.map(step, Step.class);
				listStepsDTO.add(stepDTO);
			}
			Module moduleDTO = dozerMapper.map(module, Module.class);
			moduleDTO.setSteps(listStepsDTO);
			listModulesDTO.add(moduleDTO);
		}
		
		listModulesDTO.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
		return listModulesDTO;
	}


	/**
	 * Function to upload XML configuration file and trigger validations
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return  Function to upload XML configuration file and trigger validations
	 * @throws Exception 
	 */
	@RequestMapping(path = "/upload", method = RequestMethod.POST)
	public void handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {

		Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		 }
		 
		if (!file.isEmpty()) {
			
			try {
				if (!file.getContentType().equals("text/xml")) {
					response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "uploadFileInvalid");
				} else {
					//validar size del file 189
					Path path = Paths.get(UPLOAD_PATH);
					if(!Files.exists(path)) {
						Files.createDirectories(path);
					}
					
					String filename;
					do {
						filename = UUID.randomUUID() + "." + file.getOriginalFilename();
						path = Paths.get(UPLOAD_PATH, filename);
					} while (Files.exists(path));
					
					Files.copy(file.getInputStream(), path);
					readXMLFile(path.toString(), response);
					System.out.println("You successfully uploaded " + file.getOriginalFilename() + " to " + path.toString());
					response.setStatus(HttpServletResponse.SC_OK);
				}
			} catch (IOException | RuntimeException e) {
				System.err.println("Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
				e.printStackTrace(System.err);
				log.error("Failed to upload", e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "uploadFileError");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "uploadFileEmpty");
		}
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	private String readXMLFile(String path, HttpServletResponse httpResponse) throws IOException {
		String response = "";
		try {
			File file = new File(path);

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			cleanLists();
			if (doc.hasChildNodes()) {
				walkNodes(doc.getChildNodes());
			}
			String invalidXML = validateXML();
			if (invalidXML.equals("")) {
				response = "XML is Valid and has been processed.";
				com.argSecurity.model.User userlogin = (com.argSecurity.model.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				if(moduleServices.moduleExists(moduleData.get(0))) {//Validate Module Name to be Unique
					httpResponse.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "uploadFileDuplicate");
				} else {
					int moduleId = createModule(moduleData.get(0), moduleData.get(1), moduleData.get(2),
							Integer.valueOf(moduleData.get(3)), moduleData.get(4), true, userlogin.getUsername(),
							moduleData.get(5));
					if (moduleId > 0) {
						insertSteps(moduleId, true, userlogin.getUsername());
						insertParameters(true, userlogin.getUsername());
						insertConditions(true, userlogin.getUsername());
						moduleData.clear();
						stepData.clear();
						conditionData.clear();
						stepIds.clear();
						stepSequence = 0;
					}
				}
			} else {
				System.out.println(invalidXML);
				throw new Exception("uploadFileError");
			}
		} catch (Exception e) {
			log.error(String.format("Problem with method %s", "readXMLFile"), e);
			System.out.println(e.getMessage());
			httpResponse.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "malformedXML");
		}
		return response;
	}
	
	/**
	 * 
	 * @param moduleId
	 * @param active
	 * @param currentUser
	 */
	private void insertSteps(int moduleId, boolean active, String currentUser) {
		int length = stepData.size();
		for(int i = 0; i < length; i+=3){
			int stepId = 0;
			stepId = createStep(Integer.valueOf(stepData.get(i)), stepData.get(i+1), stepData.get(i+2), moduleId, true, currentUser);
			stepIds.add(stepId);
			if(stepId <= 0){
				log.error(String.format("Problem with method %s", "insertSteps"), "Error inserting step with sequenceId "+stepData.get(i));
			}
		}
	}
	
	/**
	 * 
	 * @param active
	 * @param createdBy
	 */
	private void insertParameters(boolean active, String createdBy) {
		int length = parameterData.size();
		for(int i = 0; i < length; i+=2){
			int paramId = 0;
			
			paramId = createParameter(parameterData.get(i).getText(),parameterData.get(i+1).getText(), 
					stepIds.get(parameterData.get(i).getKey()),	active, createdBy);
			if(paramId <= 0){
				log.error(String.format("Problem with method %s", "insertParameters"), "Error inserting step with sequenceId "+stepIds.get(parameterData.get(i).getKey()));
			}
		}
	}
	
	/**
	 * 
	 * @param active
	 * @param currentUser
	 */
	private void insertConditions(boolean active, String currentUser) {
		int length = conditionData.size();
		int stepIdsIndex = 0;
		for(int i = 0; i < length; i+=5){
			stepIdsIndex = conditionData.get(i).getKey();
			
			int condId = 0;
			condId = createCondition(conditionData.get(i).getText(), conditionData.get(i+1).getText(), 
					Integer.valueOf(conditionData.get(i+2).getText()),conditionData.get(i+3).getText(), 
					conditionData.get(i+4).getText(), stepIds.get(stepIdsIndex), active, currentUser);
			
			if(condId <= 0){
				log.error(String.format("Problem with method %s", "insertConditions"), "Error inserting step with id "+stepIds.get(stepIdsIndex));
			}
			
		}
	}
	
	/**
	 * 
	 * @param nodeList
	 */
	private void walkNodes(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				module.add(tempNode.getNodeName());
				System.out.println("Node Value =" + tempNode.getTextContent());

				String temp = tempNode.getTextContent();
				String[] lines = temp.split("\\r?\\n");

				for (int i = 0; i < lines.length; i++) {
					lines[i] = lines[i].trim();
				}
				List<String> list = new ArrayList<String>(Arrays.asList(lines));
				list.removeAll(Arrays.asList("", null));
				if ((list.size() > 0) && (!tempNode.hasAttributes())) {
					System.out.println(list.get(0));
					module.add(list.get(0));
				}
				/*
				 * Parsing attributes, not implemented for this version.
				 * 
				 * if (tempNode.hasAttributes()) {
				 * 
				 * NamedNodeMap nodeMap = tempNode.getAttributes(); for (int i =
				 * 0; i < nodeMap.getLength(); i++) { Node node =
				 * nodeMap.item(i); System.out.println("attr name : " +
				 * node.getNodeName()); System.out.println("attr value : " +
				 * node.getNodeValue()); //Do something useful with attr } }
				 */
				if (tempNode.hasChildNodes()) {
					walkNodes(tempNode.getChildNodes());
				}
				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
				module.add(tempNode.getNodeName());
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private String validateXML() {
		String response = "";
		boolean moduleData = validateModuleDetails();
		if (moduleData) {
			String moduleSteps = validateStep(stepsIndex, stepResponse);
			if (!(moduleSteps.equals(""))) {
				response = moduleSteps;
			}
		} else {
			response = "Error processing module general data";
		}
		return response;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateModuleDetails() {
		boolean response = true;
		if (!(module.get(startIndex).equals("module") && module.get(startIndex + 1).equals("config"))) {
			response = false;
		} else if (!(module.get(moduleName - 1).equals("name") && module.get(moduleName + 1).equals("name"))) {
			response = false;
		} else if (!(module.get(moduleDescription - 1).equals("description")
				&& module.get(moduleDescription + 1).equals("description"))) {
			response = false;
		} else if (!(module.get(moduleModel - 1).equals("model") && module.get(moduleModel + 1).equals("model"))) {
			response = false;
		} else if (!(module.get(moduleDuration - 1).equals("duration")
				&& module.get(moduleDuration + 1).equals("duration"))) {
			response = false;
		} else if (!(module.get(moduleTimeUnit - 1).equals("timeunit")
				&& module.get(moduleTimeUnit + 1).equals("timeunit"))) {
			response = false;
		} else if (!(module.get(moduleDate - 1).equals("date") && module.get(moduleDate + 1).equals("date"))) {
			response = false;
		} else if (!(module.get(moduleDate + 2).equals("config") && module.get(moduleDate + 3).equals("steps"))) {
			response = false;
		} else {
			moduleData.add(module.get(moduleName));
			moduleData.add(module.get(moduleDescription));
			moduleData.add(module.get(moduleModel));
			moduleData.add(module.get(moduleDuration));
			moduleData.add(module.get(moduleTimeUnit));
			moduleData.add(module.get(moduleDate));
		}
		return response;
	}


	/**
	 * Validate step 1 by one, calling itself recursively with next step
	 * 
	 * @param moduleIndex
	 * @param response
	 * @return
	 */
	private String validateStep(int moduleIndex, String response) {
		response = stepResponse;
		int index = moduleIndex;
		int addition = 0;

		if ((module.get(index + 1).equals("steps")) && (module.get(index + 2).equals("module"))) {
			return response;
		} else if ((module.get(index + 1).equals("step"))) {
			index += 2;
			if (!(module.get(index).equals("sequenceid") && module.get(index + 2).equals("sequenceid"))) {
				stepResponse = "Error found in sequenceid at index " + String.valueOf(index);
				return stepResponse;
			}
			//get sequence ID for step
			stepData.add(module.get(index + 1));
						
			index += 3;
			// Method validations
			if (!(module.get(index).equals("method"))) {
				stepResponse = "Error found in method at index " + String.valueOf(index);
				return stepResponse;
			}
			// Call function to validate method and return next index
			addition = validateStepMethod(index);
			if (addition == 0) {
				stepResponse = "Error found in method at index " + String.valueOf(index);
				return stepResponse;
			} else {
				index = addition + 1;
			}
			// ----------------------------------------------------------------------------

			// Conditions Validation
			if (!(module.get(index).equals("conditions"))) {
				stepResponse = "Error found in conditions at index " + String.valueOf(index);
			}
			// Call function to validate conditions and return next index
			addition = validateStepConditions(index);
			if (addition == 0) {
				stepResponse = "Error found in conditions at index " + String.valueOf(index);
				return stepResponse;
			} else {				
				stepSequence++; //Index to control which step have each condition(s)
				index = addition;
			}
			// ----------------------------------------------------------------------------

			if (module.get(index) != null) {
				validateStep(index, stepResponse);
			}
		}
		return stepResponse;
	}

	
	/**
	 * Validate step method
	 * @param index
	 * @return
	 */
	private int validateStepMethod(int index) {
		index++;
		int response = index;
		if (!(module.get(index).equals("name") && module.get(index + 2).equals("name"))) {
			response = 0;
			return response;
		}
		stepData.add(module.get(index + 1)); //add method name
		index += 3;
		response += 3;
		if (!(module.get(index).equals("description") && module.get(index + 2).equals("description"))) {
			response = 0;
			return response;
		}
		stepData.add(module.get(index + 1)); //add step description
		response += 3;
		if (module.get(response).equals("parameters")) {
			response++;
			for (int i = response; i > 0; i += 3) {
				if (module.get(i) == "parameters") {
					break;
				} else if (module.get(i) != module.get(i + 2)) {
					response = 0;
					return response;
				} else {
					//Add parameters to list for further insertion
					Pair paramData = new Pair();
					paramData.setKey(parameterSequence);
					paramData.setText(module.get(i));
					parameterData.add(paramData);
					
					paramData = new Pair();
					paramData.setKey(parameterSequence);
					paramData.setText(module.get(i+1));
					parameterData.add(paramData);
					
					response += 3;
				}
			}
			parameterSequence++;
		}
		return response + 1;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	private int validateStepConditions(int index) {
		int response = index;
		if (module.get(response).equals("conditions")) {
			response++;
			while (!(module.get(response).equals("conditions"))) {
				response = validateStepCondition(response);
				if (response == 0) {
					return response;
				}
			}
			return response + 1;
		} else {
			return 0;
		}

	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	private int validateStepCondition(int index) {
		int response = index + 1;
		Pair condition;
		for (int i = response; i > 0; i += 3) {
			if (module.get(i) == "c") {
				break;
			} else if (module.get(i) != module.get(i + 2)) {
				return 0;
			} else {
				condition = new Pair();
				condition.setKey(stepSequence);
				condition.setText(module.get(i+1));
				conditionData.add(condition);
				response += 3;
			}
		}
		return response + 1;
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param model
	 * @param duration
	 * @param timeUnit
	 * @param active
	 * @param createdBy
	 * @param startDate
	 * @return
	 */
	public int createModule(String name, String description, String model, int duration, String timeUnit,
			boolean active, String createdBy, String startDate) {
				
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date utilDate = formatter.parse(startDate);
			java.sql.Date startDateParsed = new java.sql.Date(utilDate.getTime());
			
			int id = 0;
			Module module = new Module();
			com.argSecurity.model.Module moduleModel = dozerMapper.map(module, com.argSecurity.model.Module.class);

			dateSystem.setTime(System.currentTimeMillis());
			moduleModel.setName(name);
			moduleModel.setDescription(description);
			moduleModel.setModel(model);
			moduleModel.setStatus("not-started");
			moduleModel.setDuration(duration);
			moduleModel.setTimeUnit(timeUnit);
			moduleModel.setActive(active);
			moduleModel.setCreatedDate(dateSystem);
			moduleModel.setCreatedBy(createdBy);
			moduleModel.setStartDate(startDateParsed);
			moduleServices.save(moduleModel);
			id = moduleModel.getId();
			return id;
		} catch (ServiceException | ParseException e) {
			log.error(String.format("Problem with method %s", "createModule"), e);
			return -1;
		}
	}

	/**
	 * 
	 * @param sequenceId
	 * @param name
	 * @param description
	 * @param trainingModuleId
	 * @param active
	 * @param createdBy
	 * @return
	 */
	public int createStep(int sequenceId, String name, String description, int trainingModuleId, boolean active, 
			String createdBy) {
		try {
			int id = 0;
			Step step = new Step();

			com.argSecurity.model.Step stepModel = dozerMapper.map(step, com.argSecurity.model.Step.class);
			dateSystem.setTime(System.currentTimeMillis());
			stepModel.setSequenceId(sequenceId);
			stepModel.setName(name);
			stepModel.setDescription(description);
			stepModel.setStatus("not-started");
			stepModel.setTrainingModuleId(trainingModuleId);
			stepModel.setActive(active);
			stepModel.setCreatedDate(dateSystem);
			stepModel.setCreatedBy(createdBy);
			stepService.save(stepModel);
			id = stepModel.getId();
			return id;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "createStep"), e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @param stepId
	 * @param active
	 * @param createdBy
	 * @return
	 */
	public int createParameter(	String name, String value, int stepId, boolean active, String createdBy) {
		try {
			int id = 0;
			Parameter param = new Parameter();
		
			com.argSecurity.model.Parameter paramModel = dozerMapper.map(param, com.argSecurity.model.Parameter.class);
			dateSystem.setTime(System.currentTimeMillis());
			paramModel.setName(name);
			paramModel.setValue(value);
			paramModel.setStepId(stepId);
			paramModel.setActive(active);
			paramModel.setCreatedDate(dateSystem);
			paramModel.setCreatedBy(createdBy);
			parameterService.save(paramModel);
			id = paramModel.getId();
			return id;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "createParameter"), e);
			return -1;
		}
	}

	/**
	 * 
	 * @param name
	 * @param timer
	 * @param retries
	 * @param trueOutcome
	 * @param falseOutcome
	 * @param stepId
	 * @param active
	 * @param createdBy
	 * @return
	 */
	public int createCondition(	String name, String timer, int retries, String trueOutcome, String falseOutcome,
			int stepId, boolean active,	String createdBy) {
		try {
			int id = 0;
			Condition condition = new Condition();
		
			com.argSecurity.model.Condition conditionModel = dozerMapper.map(condition, com.argSecurity.model.Condition.class);
			dateSystem.setTime(System.currentTimeMillis());
			conditionModel.setName(name);
			conditionModel.setTimer(timer);
			conditionModel.setRetries(retries);
			conditionModel.setTrueOutcome(trueOutcome);
			conditionModel.setFalseOutcome(falseOutcome);
			conditionModel.setStepId(stepId);
			conditionModel.setActive(active);
			conditionModel.setCreatedDate(dateSystem);
			conditionModel.setCreatedBy(createdBy);
			conditionService.save(conditionModel);
			id = conditionModel.getId();
			return id;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "createCondition"), e);
			return -1;
		}
	}
	

	/**
	 * Allows to save module configuration to Database
	 * 
	 * @param name
	 * @param description
	 * @param model
	 * @param status
	 * @param duration
	 * @param timeunit
	 * @param retries
	 * @param active
	 * @param createdby
	 * @return a message with result
	 */
	@RequestMapping(method = { RequestMethod.POST })
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message saveModule(@FormParam("name") String name, @FormParam("description") String description,
			@FormParam("model") String model, @FormParam("status") String status, @FormParam("duration") int duration,
			@FormParam("timeunit") String timeunit, @FormParam("retries") int retries,
			@FormParam("active") boolean active, @FormParam("createdby") String createdby) {
		
		
		
		try {
			Module module = new Module();

			com.argSecurity.model.Module moduleModel = dozerMapper.map(module, com.argSecurity.model.Module.class);
			dateSystem.setTime(System.currentTimeMillis());
			moduleModel.setName(name);
			moduleModel.setDescription(description);
			moduleModel.setModel(model);
			moduleModel.setStatus(status);
			moduleModel.setDuration(duration);
			moduleModel.setTimeUnit(timeunit);
			moduleModel.setRetries(retries);
			moduleModel.setActive(active);
			moduleModel.setCreatedDate(dateSystem);
			moduleModel.setCreatedBy(createdby);
			moduleServices.save(moduleModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(moduleModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "saveModule"), e);
			Message message = new Message(Constants.MessagesCodes.CREATION_ERROR,
					Constants.appUserData.RESOURCE_CREATION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}

	/**
	 * Method to edit a training_module record on DB
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param model
	 * @param status
	 * @param duration
	 * @param timeunit
	 * @param retries
	 * @param active
	 * @param createdby
	 * @return a message with result
	 */
	@RequestMapping(method = { RequestMethod.PUT })
	@Consumes("application/x-www-form-urlencoded")
	@ResponseBody
	public Message editModule(@FormParam("id") int id, @FormParam("name") String name,
			@FormParam("description") String description, @FormParam("model") String model,
			@FormParam("status") String status, @FormParam("duration") int duration,
			@FormParam("timeunit") String timeunit, @FormParam("retries") int retries,
			@FormParam("active") boolean active, @FormParam("createdby") String createdby) {
		try {
			com.argSecurity.model.Module moduleModel = moduleServices.findOne(id);

			dateSystem.setTime(System.currentTimeMillis());
			moduleModel.setName(name);
			moduleModel.setDescription(description);
			moduleModel.setModel(model);
			moduleModel.setStatus(status);
			moduleModel.setDuration(duration);
			moduleModel.setTimeUnit(timeunit);
			moduleModel.setRetries(retries);
			moduleModel.setActive(active);
			moduleModel.setCreatedDate(dateSystem);
			moduleModel.setCreatedBy(createdby);
			moduleServices.save(moduleModel);
			Message message = new Message(Constants.MessagesCodes.OK, String.valueOf(moduleModel.getId()));
			return message;
		} catch (ServiceException e) {
			log.error(String.format("Problem with method %s", "editModule"), e);
			Message message = new Message(Constants.MessagesCodes.EDITION_ERROR,
					Constants.appUserData.RESOURCE_EDITION_FAILED.getValue() + e.getMessage());
			return message;
		}
	}
	
	/**
	 * Method to delete a training_module record on DB
	 * 
	 * @param id
	 * @return object list updated
	 * @throws IOException 
	 */
	@RequestMapping(path="/{id}", method = {RequestMethod.DELETE})
	@ResponseBody
	public com.argSecurity.model.dto.Module deleteModule(	@PathVariable("id") int id,HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.argSecurity.model.User userlogin;
		 if(authenticationPrincipal instanceof com.argSecurity.model.User) {
			 userlogin = (com.argSecurity.model.User) authenticationPrincipal;
		 } else {
			 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			 return null;
		 }
		
		dateSystem.setTime(System.currentTimeMillis());
		com.argSecurity.model.Module moduleModel = 	moduleServices.findOne(id);
		moduleModel.setActive(false);
		moduleModel.setDeletedDate(dateSystem);
		moduleModel.setDeletedBy(userlogin.getUsername());
		moduleServices.save(moduleModel);
		setChildrenInactive(id, userlogin.getUsername());
		
		return dozerMapper.map(moduleModel, com.argSecurity.model.dto.Module.class);
	}
	
	
	private void setChildrenInactive(int moduleId, String currentUser) {
		dateSystem.setTime(System.currentTimeMillis());
		List<UserModule> userModules = userModuleService.loadUserModuleByModuleId(moduleId);
		for(UserModule um : userModules) {
			um.setActive(false);
			um.setDeletedBy(currentUser);
			um.setDeletedDate(dateSystem);
			userModuleService.save(um);
		}
		List<com.argSecurity.model.Step> steps = stepService.getStepsByModule(moduleId);
		for(com.argSecurity.model.Step step : steps) {
			step.setActive(false);
			step.setDeletedBy(currentUser);
			step.setDeletedDate(dateSystem);
			stepService.save(step);
			
			List<Condition> conditions = conditionService.getConditionsByStep(step.getId());
			for(Condition c : conditions){
				c.setActive(false);
				c.setDeletedBy(currentUser);
				c.setDeletedDate(dateSystem);
				conditionService.save(c);
			}
			
			List<UserStep> userSteps = userStepService.getActiveUserStepsByStepId(step.getId());
			for(UserStep userStep : userSteps) {
				userStep.setActive(false);
				userStep.setDeletedBy(currentUser);
				userStep.setDeletedDate(dateSystem);
				userStepService.save(userStep);
				List<UserCondition> userConditions = userConditionService.getActiveConditionsByStepId(userStep.getStepId(), true);
				for(UserCondition userCond : userConditions) {
					userCond.setActive(false);
					userCond.setDeletedBy(currentUser);
					userCond.setDeletedDate(dateSystem);
					userConditionService.save(userCond);
				}
			}
		}
	}
	
	private void cleanLists() {
		module.clear();
		moduleData.clear();
		stepData.clear();	
		stepSequence = 0;
		parameterSequence = 0;
		stepIds.clear();
		conditionData.clear();
		parameterData.clear();
		stepResponse = "";
	}

}