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
import com.orhon.pa.modules.opa.dao.OpaPlanDao;
import com.orhon.pa.modules.opa.dao.OpaPlanOfficeDao;
import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaPlan;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.sys.dao.OfficeDao;
import com.orhon.pa.modules.sys.entity.Office;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 考核计划模块Service
 * @author Shawn
 * @version 2017-04-25
 */
@Service
@Transactional(readOnly = true)
public class OpaPlanService extends CrudService<OpaPlanDao, OpaPlan> {
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private OpaPlanOfficeDao opaPlanOfficeDao;
	@Autowired
	private OpaSchemeItemDao opaSchemeItemDao;
	@Autowired
	private OpaPlanDao opaPlanDao;
	@Autowired
	private OpaPlanTaskDao opaPlanTaskDao;

	
	public OpaPlan get(String id) {
		OpaPlan opaPlan = super.get(id);
		return opaPlan;
	}
	
	public List<OpaPlan> findList(OpaPlan opaPlan) {
		return super.findList(opaPlan);
	}
	
	public Page<OpaPlan> findPage(Page<OpaPlan> page, OpaPlan opaPlan) {
		return super.findPage(page, opaPlan);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaPlan opaPlan) {
		super.save(opaPlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaPlan opaPlan) {
		super.delete(opaPlan);
	}

	@Transactional(readOnly = false)
	public void apply(OpaPlan opaPlan) {
		Office officeParent = new Office();
		officeParent.setId(opaPlan.getOfficeParentId());
		List<Office> officeList = officeDao.findTreeByIdLike(officeParent);
		OpaSchemeItem schemeItem = new OpaSchemeItem();
		schemeItem.setSchemeId(opaPlan.getSchemeId());
		List<OpaSchemeItem> schemeItemList = opaSchemeItemDao.findList(schemeItem);
		int unAuditCount = 0;
		for(Office office : officeList){
			User user = UserUtils.getUser();
			OpaPlanOffice planOffice = new OpaPlanOffice();
			planOffice.setId(IdGen.uuid());
			planOffice.setPlanId(opaPlan.getId());
			planOffice.setOffice(office);
			planOffice.setOfficeParent(office.getParent());
			planOffice.setCreateBy(user);
			planOffice.setUpdateBy(user);
			planOffice.setCreateDate(new Date());
			planOffice.setUpdateDate(planOffice.getCreateDate());
			planOffice.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
			opaPlanOfficeDao.insert(planOffice);
			for(OpaSchemeItem item : schemeItemList){
				OpaPlanTask task = new OpaPlanTask();
				task.setId(IdGen.uuid());                                                      
				task.setPlanId(opaPlan.getId());
				task.setOffice(planOffice.getOffice());
				task.setName(item.getName());                                               
				task.setItemId(item.getItemId());                                               
				task.setItemParentId(item.getItemParentId());                                   
				task.setItemParentIds(item.getItemParentIds());                                 
				task.setCode(item.getCode());                                               
				task.setSort(item.getSort());                                               
				task.setLevel(item.getLevel());                                             
				task.setContent(item.getContent());                                                           
				task.setMethod(item.getMethod());     
				task.setIfNum(item.getIfNum());                                                       
				task.setCount(item.getCount());                                                          
				task.setValue(item.getValue()); 
				task.setType(item.getType());  
				task.setDateFrom(item.getDateFrom());                                       
				task.setDateTo(item.getDateTo());                                           
				task.setRemarks(item.getRemarks());                                         
				task.setCreateBy(user);                                                        
				task.setUpdateBy(user);                                                        
				task.setCreateDate(new Date());                                                
				task.setUpdateDate(task.getCreateDate());
				task.setAuditorOfficeId(item.getAuditorOfficeId());
				task.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
				if(item.getIfNum().equals(DictUtils.getDictValue("个性数值", "opa_if_num_type", ""))
					||item.getType().equals(DictUtils.getDictValue("个性指标", "opa_item_type", ""))){
					task.setStatus(DictUtils.getDictValue("待填报", "opa_planTask_status", ""));
					unAuditCount++;
				}else{
					task.setStatus(DictUtils.getDictValue("已填报", "opa_planTask_status", ""));
				}
				if(task.getCode().equals("BONUS_PARENT")){
					task.setStatus(DictUtils.getDictValue("待执行", "opa_planTask_status", ""));
				}
				task.setAuditorId(item.getAuditorId());                                                         
				opaPlanTaskDao.insert(task);
		    	}
			}
		opaPlan.setStatus(DictUtils.getDictValue("指标填报中", "opa_plan_status",""));
		opaPlanDao.update(opaPlan);
	}
	
	public List<Status> findListByStatus(Map<String, Object> param) {
		return opaPlanDao.findListByStatus(param);
	}
	
	@Transactional(readOnly = false)
	public void excute(OpaPlan opaPlan) {
		opaPlan.setStatus(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		opaPlanDao.update(opaPlan);
		OpaPlanTask param = new OpaPlanTask();
		param.setPlanId(opaPlan.getId());
		param.setMethod(DictUtils.getDictValue("手工考核", "opa_item_method", ""));
		param.setStatus(DictUtils.getDictValue("待评分", "opa_planTask_status", ""));
		opaPlanTaskDao.setAllToStatus(param);
		
		param.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
		param.setStatus(DictUtils.getDictValue("已完成", "opa_planTask_status", ""));
		opaPlanTaskDao.setAllToStatus(param);
		
	/*	OpaPlanTask plantask = new OpaPlanTask();
		plantask.setPlanId(opaPlan.getId());
		List<OpaPlanTask> planTaskList = opaPlanTaskDao.findList(plantask);
		int unAuditCount = 0;
		for(OpaPlanTask planTask :planTaskList) {
			User user = UserUtils.getUser();
			OpaTaskAppeal appeal = new OpaTaskAppeal();
			appeal.setId(IdGen.uuid());
			appeal.setPlanId(opaPlan.getId());
			appeal.setOffice(planTask.getOffice());
			appeal.setTaskId(planTask.getId());
			appeal.setName(planTask.getName());
			appeal.setType(planTask.getType());
			appeal.setMethod(planTask.getMethod());
			appeal.setIfNum(planTask.getIfNum());
			appeal.setValue(planTask.getValue());
			appeal.setScore(planTask.getScore());
			appeal.setCount(planTask.getCount());
			appeal.setCode(planTask.getCode());
			appeal.setCreateBy(user);
			appeal.setUpdateDate(planTask.getUpdateDate());
			appeal.setCreateDate(new Date());
			appeal.setRemarks(planTask.getRemarks());
			appeal.setUpdateBy(user);
			appeal.setAuditorOfficeId(planTask.getAuditorOfficeId());
			appeal.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
			appeal.setAuditorId(planTask.getAuditorId()); 
			if(planTask.getIfNum().equals(DictUtils.getDictValue("个性数值", "opa_if_num_type", ""))
					||planTask.getType().equals(DictUtils.getDictValue("个性指标", "opa_item_type", ""))){
					appeal.setStatus(DictUtils.getDictValue("申诉中", "opa_taskAppeal_status", ""));
					unAuditCount++;
				}else{
					appeal.setStatus(DictUtils.getDictValue("已审核", "opa_taskAppeal_status", ""));
				}
				appeal.setAuditorOfficeId(planTask.getAuditorOfficeId());
				opaPlanTaskDao.update(planTask);
				opaTaskAppealDao.insert(appeal);
    		}*/
    	}
		
	
	@Transactional(readOnly = false)
	public void summary(OpaPlan opaPlan) {
		OpaPlanOffice officeParam = new OpaPlanOffice();
		officeParam.setPlanId(opaPlan.getId());
		List<OpaPlanOffice> officeList = opaPlanOfficeDao.findList(officeParam);
		for(OpaPlanOffice off : officeList){
			OpaPlanTask taskParam = new OpaPlanTask();
			taskParam.setPlanId(opaPlan.getId());
			taskParam.setOffice(off.getOffice());
			taskParam.setItemParentId("0");
			List<OpaPlanTask> parentList = opaPlanTaskDao.findList(taskParam);
			for(OpaPlanTask parentTask : parentList){
				this.findChildAndSum(parentTask);
			}
		}
		opaPlan.setStatus(DictUtils.getDictValue("已完成", "opa_plan_status", ""));
		opaPlanDao.update(opaPlan);
		
	}
	
	private void findChildAndSum(OpaPlanTask task){
		double sum = 0;
		List<OpaPlanTask> childList = opaPlanTaskDao.findChild(task);
		if(childList != null && childList.size()>0){
			for(OpaPlanTask child : childList){
				this.findChildAndSum(child);
				sum += child.getScore()==null? 0:child.getScore();
			}
			task.setScore(sum);
			opaPlanTaskDao.update(task);
		}
	}
	
}