/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.modules.opa.entity.OpaMenu;
import com.orhon.pa.modules.opa.dao.OpaMenuDao;

/**
 * demo表Service
 * @author yyyyy
 * @version 2018-05-30
 */
@Service
@Transactional(readOnly = true)
public class OpaMenuService extends CrudService<OpaMenuDao, OpaMenu> {

	public OpaMenu get(String id) {
		return super.get(id);
	}
	
	public List<OpaMenu> findList(OpaMenu opaMenu) {
		return super.findList(opaMenu);
	}
	
	public Page<OpaMenu> findPage(Page<OpaMenu> page, OpaMenu opaMenu) {
		return super.findPage(page, opaMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaMenu opaMenu) {
		super.save(opaMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaMenu opaMenu) {
		super.delete(opaMenu);
	}
	
}