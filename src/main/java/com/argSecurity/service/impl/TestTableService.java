/**
 * TestTableService
 * @author daniela.depablos
 * 
 * Benetech trainning app Copyrights reserved
 */	
package com.argSecurity.service.impl;

import java.util.List;

import com.argSecurity.model.TestTable;

public interface TestTableService {
	/**
	 * 
	 * @param table
	 */
	public void save(TestTable table);
	
	/**
	 * 
	 * @return
	 */
	public List<TestTable> listAll();
	
	/**
	 * 
	 * @param table
	 * @return
	 */
	public TestTable update(TestTable table);
	
	/**
	 * 
	 * @param table
	 */
	public void delete(TestTable table);

}
