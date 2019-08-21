/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.common.utils.CacheUtils;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.modules.opa.entity.OpaLeadershipAssessment;
import com.orhon.pa.modules.sys.entity.Role;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.UserUtils;
import com.orhon.pa.modules.opa.dao.OpaLeadershipAssessmentDao;

/**
 * 领导干部考核情况Service
 * @author asyt
 * @version 2018-05-22
 */
@Service
@Transactional(readOnly = true)
public class OpaLeadershipAssessmentService extends CrudService<OpaLeadershipAssessmentDao, OpaLeadershipAssessment> {
	
	@Autowired
	private OpaLeadershipAssessmentDao opaLeadershipAssessmentDao;

	public OpaLeadershipAssessment get(String id) {
		return super.get(id);
	}
	
	public List<OpaLeadershipAssessment> findList(OpaLeadershipAssessment opaLeadershipAssessment) {
		return super.findList(opaLeadershipAssessment);
	}
	
	public Page<OpaLeadershipAssessment> findPage(Page<OpaLeadershipAssessment> page, OpaLeadershipAssessment opaLeadershipAssessment) {
		return super.findPage(page, opaLeadershipAssessment);
	}
	public Page<OpaLeadershipAssessment>assessementfindPage(Page<OpaLeadershipAssessment>page,OpaLeadershipAssessment opaLeadershipAssessment){
		return super.assessementfindPage(page, opaLeadershipAssessment);
	}
	
	public List<Role>findAllRole(){
	    return UserUtils.getRoleList();
	}
	public Page<OpaLeadershipAssessment> findUser(Page<OpaLeadershipAssessment> page, OpaLeadershipAssessment opaLeadershipAssessment) {
		opaLeadershipAssessment.getSqlMap().put("dsf", dataScopeFilter(opaLeadershipAssessment.getCurrentUser(), "o", "a"));
		// 设置分页参数
		opaLeadershipAssessment.setPage(page);
		List<OpaLeadershipAssessment> dataList = new ArrayList<OpaLeadershipAssessment>();
        User user=opaLeadershipAssessment.getCurrentUser();
        List<OpaLeadershipAssessment>leadeFindList = dao.assessmentList(opaLeadershipAssessment);
        for(OpaLeadershipAssessment t:leadeFindList) {
        	if(t.getUserId().equals(user.getId())) {
        		dataList.add(t);
        	}
        }
		page.setList((List<OpaLeadershipAssessment>) dataList);
		// 执行分页查询事
		page.setList(dataList);
		return page;
		
	}
	
	@Transactional(readOnly = false)
	public void save(OpaLeadershipAssessment opaLeadershipAssessment) {
		if(StringUtils.isBlank(opaLeadershipAssessment.getId())) {
			opaLeadershipAssessment.preInsert();
			opaLeadershipAssessmentDao.insert(opaLeadershipAssessment);
		}else {
			//清除原用户机构缓存
			OpaLeadershipAssessment oldUser = opaLeadershipAssessmentDao.get(opaLeadershipAssessment.getId());
			if(oldUser.getOffice() !=null && oldUser.getOffice().getId()!=null) {
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice());
			}
			//更新用户数据
			opaLeadershipAssessment.preUpdate();
			opaLeadershipAssessmentDao.update(opaLeadershipAssessment);
		}
		if(StringUtils.isNotBlank(opaLeadershipAssessment.getId())) {
			//更新用户与角色关联
			opaLeadershipAssessmentDao.deleteUserRole(opaLeadershipAssessment);
			if (opaLeadershipAssessment.getRoleList() != null && opaLeadershipAssessment.getRoleList().size() > 0){
				opaLeadershipAssessmentDao.insertUserRole(opaLeadershipAssessment);
				opaLeadershipAssessmentDao.update1(opaLeadershipAssessment);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaLeadershipAssessment opaLeadershipAssessment) {
		super.delete(opaLeadershipAssessment);
	}
}