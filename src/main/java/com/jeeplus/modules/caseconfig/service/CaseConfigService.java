/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.caseconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.caseconfig.entity.CaseConfig;
import com.jeeplus.modules.caseconfig.dao.CaseConfigDao;

/**
 * 用例管理Service
 * @author yanyu
 * @version 2018-08-07
 */
@Service
@Transactional(readOnly = true)
public class CaseConfigService extends CrudService<CaseConfigDao, CaseConfig> {

	public CaseConfig get(String id) {
		return super.get(id);
	}
	
	public List<CaseConfig> findList(CaseConfig caseConfig) {
		return super.findList(caseConfig);
	}
	
	public Page<CaseConfig> findPage(Page<CaseConfig> page, CaseConfig caseConfig) {
		return super.findPage(page, caseConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseConfig caseConfig) {
		super.save(caseConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseConfig caseConfig) {
		super.delete(caseConfig);
	}
	
	
	
	
}