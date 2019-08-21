/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.modules.opa.entity.OpaOverview;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.dao.OpaOverviewDao;

/**
 * 综合评分Service
 * @author ss
 * @version 2018-06-16
 */
@Service
@Transactional(readOnly = true)
public class OpaOverviewService extends CrudService<OpaOverviewDao, OpaOverview> {

	
	public OpaOverview get(String id) {
		OpaOverview opaOverview = super.get(id);
		return opaOverview;
	}
	
	public List<OpaOverview> findList(OpaOverview opaOverview) {
		return super.findList(opaOverview);
	}
	
	public Page<OpaOverview> findPage(Page<OpaOverview> page, OpaOverview opaOverview) {
		return super.findPage(page, opaOverview);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaOverview opaOverview) {
		super.save(opaOverview);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaOverview opaOverview) {
		super.delete(opaOverview);
	}
	
	@Transactional(readOnly = false)
	public void update(OpaOverview opaOverview) {
		super.update(opaOverview);
	}
}