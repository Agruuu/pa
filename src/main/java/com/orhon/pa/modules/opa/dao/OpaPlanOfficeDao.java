/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;

/**
 * 考核计划部门模块DAO接口
 * @author Shawn
 * @version 2017-04-26
 */
@MyBatisDao
public interface OpaPlanOfficeDao extends CrudDao<OpaPlanOffice> {
	
	
	void insert1(OpaPlanOffice opaPlanOffice);
	
    OpaPlanOffice findOffice(String schemeId);
    
    List<OpaPlanOffice> findList1(@Param("Identificationsymbo")String Identificationsymbo);
}
