/**
 * TestTableServiceImpl
 * @author daniela.depablos
 * 
 * Benetech trainning app Copyrights reserved
 */

package com.argSecurity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argSecurity.model.TestTable;
import com.argSecurity.repository.TestTableRepository;

@Service
public class TestTableServiceImpl implements TestTableService{
	
	@Autowired(required=true)
	TestTableRepository testTableRepository;
	

	@Override
	public void save(TestTable table) {
		// TODO Auto-generated method stub
		testTableRepository.save(table);
	}

	@Override
	public List<TestTable> listAll() {
		// TODO Auto-generated method stub
		return testTableRepository.findAll();
	}

	@Override
	public TestTable update(TestTable table) {
		// TODO Auto-generated method stub
		return testTableRepository.save(table);
	}

	@Override
	public void delete(TestTable table) {
		// TODO Auto-generated method stub
		testTableRepository.delete(table);
	}

	/**
	 * 
	 * @return
	 */
	public TestTableRepository getTestTableRepository() {
		return testTableRepository;
	}

	/**
	 * 
	 * @param testTableRepository
	 */
	public void setTestTableRepository(TestTableRepository testTableRepository) {
		this.testTableRepository = testTableRepository;
	}
	
	

}
