/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.st.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.modules.st.entity.OpaAssessment;
import com.orhon.pa.modules.st.dao.OpaAssessmentDao;

/**
 * 试探列表Service
 * @author aaaaaa
 * @version 2018-05-23
 */
@Service
@Transactional(readOnly = true)
public class OpaAssessmentService extends CrudService<OpaAssessmentDao, OpaAssessment> {

	public OpaAssessment get(String id) {
		return super.get(id);
	}
	
	public List<OpaAssessment> findList(OpaAssessment opaAssessment) {
		return super.findList(opaAssessment);
	}
	
	public Page<OpaAssessment> findPage(Page<OpaAssessment> page, OpaAssessment opaAssessment) {
		return super.findPage(page, opaAssessment);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaAssessment opaAssessment) {
		super.save(opaAssessment);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaAssessment opaAssessment) {
		super.delete(opaAssessment);
	}
	
}