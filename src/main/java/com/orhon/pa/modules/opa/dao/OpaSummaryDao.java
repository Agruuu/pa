package com.orhon.pa.modules.opa.dao;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaSummary;

@MyBatisDao
public interface OpaSummaryDao extends CrudDao<OpaSummary>{
	
	
	OpaSummary get(String value);
	
	OpaSummary entity();

}
