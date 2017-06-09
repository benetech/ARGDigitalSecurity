/**
 * @author Bryan Barrantes
 * 
 * Module Service to handle module data
 * 
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.argSecurity.model.Module;
import com.argSecurity.repository.ModuleRepository;

@Service
public class ModuleServiceImpl {
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	/**
	 * Load Module according to the name
	 * @param name
	 * @return
	 * @throws AuthenticationException
	 */
	public Module loadModuleByName(String name) throws AuthenticationException {
		Module module = moduleRepository.findByName(name);
		
		if (module == null) {
			throw new ObjectNotFoundException(name, "module");
		}else {
			return module;
		}
	}
	
	/**
	 * Fetch only modules actives
	 * @param isActive
	 * @return
	 */
	public List<Module> getIsActiveModules(boolean isActive){
		return moduleRepository.findByisActive(true);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Module> getUnfinishedModules(){
		List<Module> modules = moduleRepository.findByisActive(true);
		List<Module> response = new ArrayList<Module>(); 
		for(int i = 0; i < modules.size(); i++) {
			if(!(modules.get(i).getStatus().equals("completed"))) {
				response.add(modules.get(i));
			}
		}
		return response;
	}

	/**
	 * Save the module
	 * 
	 * @param moduleModel
	 */
	public void save(Module moduleModel) {
		moduleRepository.save(moduleModel);
	}
	
	/**
	 * Delete module setting as inactive
	 * @param id
	 */
	public void delete(int id){
		Module module = moduleRepository.findOne(id);
		module.setActive(false);
		moduleRepository.save(module);
	}
	/**
	 * Find one module item according to the id
	 * @param id
	 * @return
	 */
	public Module findOne(Integer id) {
		Module response = moduleRepository.findOne(id);
		return response;
	}
	
	/**
	 * Returns true if a name has been taken by another module
	 * @param name
	 * @return
	 */
	public boolean moduleExists(String name) {
		Module module = moduleRepository.findByNameAndIsActive(name, true);
		return (module != null)? true : false;
	}
}
