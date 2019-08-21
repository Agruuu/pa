/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;
import com.orhon.pa.modules.opa.dao.OpaPlanOfficeDao;

/**
 * 考核计划部门模块Service
 * @author Shawn
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = true)
public class OpaPlanOfficeService extends CrudService<OpaPlanOfficeDao, OpaPlanOffice> {

	
	public OpaPlanOffice get(String id) {
		OpaPlanOffice opaPlanOffice = super.get(id);
		return opaPlanOffice;
	}
	
	public List<OpaPlanOffice> findList(OpaPlanOffice opaPlanOffice) {
		return super.findList(opaPlanOffice);
	}
	
	public Page<OpaPlanOffice> findPage(Page<OpaPlanOffice> page, OpaPlanOffice opaPlanOffice) {
		return super.findPage(page, opaPlanOffice);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaPlanOffice opaPlanOffice) {
		super.save(opaPlanOffice);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaPlanOffice opaPlanOffice) {
		super.delete(opaPlanOffice);
	}
	
}