/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.service.TreeService;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.modules.sys.dao.OfficeDao;
import com.orhon.pa.modules.sys.entity.Office;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList1();
		}
	}
	
	/**
	 * del_flag 0,1,2查询部门
	 */
	public List<Office>findOfficeList(Boolean isAll){
		if(isAll !=null && isAll) {
			return UserUtils.getOfficeAllList();
		}else {
			return UserUtils.getfindOfficeAllList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	public List<Office> findList1(Office office) {
		
			if (StringUtils.isNotBlank(office.getParentIds())){
				office.setParentIds(","+office.getParentIds()+",");
			}
			return super.findList(office);
		}

	
}
