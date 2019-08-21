/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.service.TreeService;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.modules.opa.dao.OpaItemDao;
import com.orhon.pa.modules.opa.entity.OpaItem;

/**
 * 指标管理模块Service
 * @author Shawn
 * @version 2017-04-18
 */
@Service
@Transactional(readOnly = true)
public class OpaItemService extends TreeService<OpaItemDao, OpaItem> {

	public OpaItem get(String id) {
		return super.get(id);
	}
	
	public List<OpaItem> findList(OpaItem opaItem) {
		if (StringUtils.isNotBlank(opaItem.getParentIds())){
			opaItem.setParentIds(","+opaItem.getParentIds()+",");
		}
		return super.findList(opaItem);
	}

	
	@Transactional(readOnly = false)
	public void save(OpaItem opaItem) {
		String parentId = opaItem.getParentId();
		Integer level = 0;
		if(StringUtils.isNotEmpty(parentId)){
			level = this.get(parentId).getLevel()+1;
		}
		opaItem.setLevel(level);
		opaItem.setCode(IdGen.uuid());
		super.save(opaItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaItem opaItem) {
		super.delete(opaItem);
	}
}