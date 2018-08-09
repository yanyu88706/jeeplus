/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.interfaceconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.interfaceconfig.entity.InterfaceConfig;
import com.jeeplus.modules.interfaceconfig.dao.InterfaceConfigDao;

/**
 * 接口管理Service
 * @author yanyu
 * @version 2018-08-06
 */
@Service
@Transactional(readOnly = true)
public class InterfaceConfigService extends CrudService<InterfaceConfigDao, InterfaceConfig> {

	public InterfaceConfig get(String id) {
		return super.get(id);
	}
	
	public List<InterfaceConfig> findList(InterfaceConfig interfaceConfig) {
		return super.findList(interfaceConfig);
	}
	
	public Page<InterfaceConfig> findPage(Page<InterfaceConfig> page, InterfaceConfig interfaceConfig) {
		return super.findPage(page, interfaceConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(InterfaceConfig interfaceConfig) {
		super.save(interfaceConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(InterfaceConfig interfaceConfig) {
		super.delete(interfaceConfig);
	}
	
	
	
	
}