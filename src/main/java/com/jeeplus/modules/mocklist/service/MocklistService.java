/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.mocklist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.mocklist.entity.Mocklist;
import com.jeeplus.modules.mocklist.dao.MocklistDao;

/**
 * mockService
 * @author yanyu
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class MocklistService extends CrudService<MocklistDao, Mocklist> {

	public Mocklist get(String id) {
		return super.get(id);
	}
	
	public List<Mocklist> findList(Mocklist mocklist) {
		return super.findList(mocklist);
	}
	
	public Page<Mocklist> findPage(Page<Mocklist> page, Mocklist mocklist) {
		return super.findPage(page, mocklist);
	}
	
	@Transactional(readOnly = false)
	public void save(Mocklist mocklist) {
		super.save(mocklist);
	}
	
	@Transactional(readOnly = false)
	public void delete(Mocklist mocklist) {
		super.delete(mocklist);
	}
}