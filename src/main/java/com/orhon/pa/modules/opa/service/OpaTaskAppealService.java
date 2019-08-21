/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.BaseEntity;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.modules.opa.entity.OpaPlan;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaTaskAppeal;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;
import com.orhon.pa.modules.opa.dao.OpaPlanDao;
import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.dao.OpaTaskAppealDao;

/**
 * 绩效反馈模块Service
 * @author Shawn
 * @version 2017-06-01
 */
@Service
@Transactional(readOnly = true)
public class OpaTaskAppealService extends CrudService<OpaTaskAppealDao, OpaTaskAppeal> {
	@Autowired
	OpaPlanDao opaPlanDao;
	
	@Autowired
	OpaPlanTaskDao opaPlanTaskDao;
	
	@Autowired
	OpaPlanTaskService opaPlanTaskService;
	
	@Autowired
	OpaSchemeDao  opaSchemeDao;

	@Autowired
	private OpaTaskAppealDao opaTaskAppealDao;
	public OpaTaskAppeal get(String id) {
		return super.get(id);
	}
	
	public List<OpaTaskAppeal> findList(OpaTaskAppeal opaTaskAppeal) {
		return super.findList(opaTaskAppeal);
	}
	
	public Page<OpaTaskAppeal> findPage(Page<OpaTaskAppeal> page, OpaTaskAppeal opaTaskAppeal) {
		return super.findPage(page, opaTaskAppeal);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaTaskAppeal opaTaskAppeal) {
		super.save(opaTaskAppeal);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaTaskAppeal opaTaskAppeal) {
		super.delete(opaTaskAppeal);
	}

	public List<Status> findListByStatus(Map<String, Object> param) {
		return opaTaskAppealDao.findListByStatus(param);
	}
	@Transactional(readOnly = false)
	public void applyAuditPass(OpaTaskAppeal OpaTaskAppeal) {
		OpaTaskAppeal = opaTaskAppealDao.get(OpaTaskAppeal);
		OpaTaskAppeal.setStatus(DictUtils.getDictValue("已审核", "opa_taskAppeal_status", ""));
		super.save(OpaTaskAppeal);
		
		//全部审核后更新状态为填报已审核
		String planId = OpaTaskAppeal.getPlanId();
		OpaTaskAppeal param = new OpaTaskAppeal();
		param.setPlanId(planId);
		param.setStatus(DictUtils.getDictValue("已审核", "opa_taskAppeal_status", ""));
		List<OpaTaskAppeal> list = opaTaskAppealDao.findNotPassList(param);
		if(null == list || list.isEmpty()){
//			param.setStatus(DictUtils.getDictValue("填报已审核", "opa_schemeItem_status", ""));
//			opaSchemeItemDao.setAllToStatus(param);
			OpaPlan opaPlan = opaPlanDao.get(planId);
			opaPlan.setStatus(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
			opaPlanDao.update(opaPlan);
		}
	}
	@SuppressWarnings("unlikely-arg-type")
	@Transactional(readOnly=false)
	public void appealupdate(OpaPlanTask opaPlanTask) {
		OpaTaskAppeal appealscoreappachedpath = new OpaTaskAppeal();
		User user = UserUtils.getUser();
		appealscoreappachedpath.setId(IdGen.uuid());
		appealscoreappachedpath.setPlanId(opaPlanTask.getPlanId());
		appealscoreappachedpath.setOffice(opaPlanTask.getOffice());
		appealscoreappachedpath.setTaskId(opaPlanTask.getId());
		appealscoreappachedpath.setName(opaPlanTask.getName());
		appealscoreappachedpath.setType(opaPlanTask.getType());
		appealscoreappachedpath.setMethod(opaPlanTask.getMethod());
		appealscoreappachedpath.setIfNum(opaPlanTask.getIfNum());
		appealscoreappachedpath.setValue(opaPlanTask.getValue());
		appealscoreappachedpath.setScore(opaPlanTask.getScore());
		appealscoreappachedpath.setCount(opaPlanTask.getCount());
		appealscoreappachedpath.setCode(opaPlanTask.getCode());
		appealscoreappachedpath.setCreateBy(user);
		appealscoreappachedpath.setUpdateDate(opaPlanTask.getUpdateDate());
		appealscoreappachedpath.setCreateDate(new Date());
		appealscoreappachedpath.setRemarks(opaPlanTask.getRemarks());
		appealscoreappachedpath.setResult(opaPlanTask.getResult());
		appealscoreappachedpath.setUpdateBy(opaPlanTask.getUpdateBy());
		appealscoreappachedpath.setAuditorOfficeId(opaPlanTask.getAuditorOfficeId());
		appealscoreappachedpath.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		appealscoreappachedpath.setAuditorId(opaPlanTask.getAuditorId()); 
		appealscoreappachedpath.setAttachedPath(opaPlanTask.getAttachedPath());
		appealscoreappachedpath.setStatus(DictUtils.getDictValue("审核中", "opa_taskAppeal_status",""));
		opaTaskAppealDao.insert(appealscoreappachedpath);
		String itemParentId = "0";
		String parentId=opaPlanTask.getPlanId();
		OpaPlanTask task = opaPlanTaskDao.getOneList(parentId);
		OpaPlanTask sort = opaPlanTaskDao.MaxSort(parentId);
		String MaxSort =sort.getMaxSort(); 
		if(opaPlanTask.getSort().equals(MaxSort)) {
			if(task.getItemParentId().equals(itemParentId)) {
						OpaTaskAppeal ap = new OpaTaskAppeal();
						ap.setId(IdGen.uuid());
						ap.setDelFlag(task.getDelFlag());
						ap.setPlanId(task.getPlanId());
						ap.setOffice(task.getOffice());
						ap.setTaskId(task.getId());
						ap.setName(task.getName());
						ap.setType(task.getType());
						ap.setMethod(task.getMethod());
						ap.setIfNum(task.getIfNum());
						ap.setValue(task.getValue());
						ap.setScore(task.getScore());
						ap.setCount(task.getCount());
						ap.setCode(task.getCode());
						ap.setCreateBy(user);
						ap.setUpdateDate(task.getUpdateDate());
						ap.setCreateDate(new Date());
						ap.setRemarks(task.getRemarks());
						ap.setResult(task.getResult());
						ap.setUpdateBy(task.getUpdateBy());
						ap.setAuditorOfficeId(task.getAuditorOfficeId());
						ap.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
						ap.setAuditorId(task.getAuditorId()); 
						ap.setAttachedPath(task.getAttachedPath());
						ap.setStatus(DictUtils.getDictValue("审核中", "opa_taskAppeal_status",""));
						opaTaskAppealDao.insert(ap);
			}
		}

	}
	@Transactional(readOnly=false)
	public void updatestatus(OpaTaskAppeal opaTaskAppeal) {
		opaTaskAppeal.setStatus(DictUtils.getDictValue("审核中", "opa_taskAppeal_status",""));
		opaTaskAppealDao.update(opaTaskAppeal);
	}
		}