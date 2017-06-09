/**
 * @author Bryan Barrantes
 * 
 * AuditTrail Service to handle Audit Trail data
 * 
 * Benetech trainning app Copyrights reserved
 */
package com.argSecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.argSecurity.model.AuditTrail;
import com.argSecurity.repository.AuditTrailRepository;

@Service
public class AuditTrailServiceImpl {
	
	@Autowired
	private AuditTrailRepository auditTrailRepository;
	
	/**
	 * Fetch AuditTrail By Id
	 * @param Id
	 * @param isActive
	 * @return
	 */
	public AuditTrail getActiveAuditTrailById(int id){
		return auditTrailRepository.findOne(id);
	}
	
	
	/**
	 * Save the module
	 * 
	 * @param moduleModel
	 */
	public void save(AuditTrail auditTrailModel) {
		auditTrailRepository.save(auditTrailModel);
	}
	
}
