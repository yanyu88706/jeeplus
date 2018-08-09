/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.interfaceconfig.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.interfaceconfig.entity.InterfaceConfig;

/**
 * 接口管理DAO接口
 * @author yanyu
 * @version 2018-08-06
 */
@MyBatisDao
public interface InterfaceConfigDao extends CrudDao<InterfaceConfig> {

	
}