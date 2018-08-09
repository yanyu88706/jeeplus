/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.mocklist.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.mocklist.entity.Mocklist;

/**
 * mockDAO接口
 * @author yanyu
 * @version 2018-07-24
 */
@MyBatisDao
public interface MocklistDao extends CrudDao<Mocklist> {

   // public Mocklist setMocklistJson(){};
	
}