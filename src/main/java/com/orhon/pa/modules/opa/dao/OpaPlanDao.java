/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;
import java.util.Map;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.modules.opa.entity.OpaPlan;

/**
 * 考核计划模块DAO接口
 * @author Shawn
 * @version 2017-04-25
 */
@MyBatisDao
public interface OpaPlanDao extends CrudDao<OpaPlan> {

	List<Status> findListByStatus(Map<String, Object> param);
	
}