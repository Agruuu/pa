package com.orhon.pa.modules.opa.dao;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaMenu;

/**
 * demo表DAO接口
**/
@MyBatisDao
public interface OpaMenuDao extends CrudDao<OpaMenu> {
	
}