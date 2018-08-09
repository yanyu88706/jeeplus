/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.caseconfig.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.caseconfig.entity.CaseConfig;

/**
 * 用例管理DAO接口
 * @author yanyu
 * @version 2018-08-07
 */
@MyBatisDao
public interface CaseConfigDao extends CrudDao<CaseConfig> {

	
}