package com.orhon.pa.modules.opa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.modules.opa.dao.OpaSummaryTypeDao;
import com.orhon.pa.modules.opa.entity.OpaSummaryType;


@Service
@Transactional(readOnly = true)
public class OpaSummaryTypeService extends CrudService<OpaSummaryTypeDao,OpaSummaryType> {
	
	
	@Autowired
	private OpaSummaryTypeDao typeDao;
	
	public void  insert(OpaSummaryType type) {
		 typeDao.summaryInsert(type);
	}
	
	
	public List<OpaSummaryType> getType() {
		return typeDao.type();
	}
	
	
	

}
