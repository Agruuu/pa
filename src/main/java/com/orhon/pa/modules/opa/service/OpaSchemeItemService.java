/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.modules.opa.dao.OpaPlanOfficeDao;
import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 方案指标模块Service
 * @author Shawn
 * @version 2017-04-21
 */
@Service
@Transactional(readOnly = true)
public class OpaSchemeItemService extends CrudService<OpaSchemeItemDao, OpaSchemeItem> {

	@Autowired
	private OpaSchemeItemDao opaSchemeItemDao;
	@Autowired
	private OpaSchemeDao opaSchemeDao;

	@Autowired
	private OpaSchemeService opaSchemeServie;
	@Autowired
	private OpaPlanTaskDao opaPlanTaskDao;

	
	
	public OpaSchemeItem get(String id) {
		OpaSchemeItem opaSchemeItem = super.get(id);
		return opaSchemeItem;
	}
	
	public OpaSchemeItem getParentSchemeItem(OpaSchemeItem OpaSchemeItem) {
		OpaSchemeItem opaSchemeItem = opaSchemeItemDao.getParentSchemeItem(OpaSchemeItem);
		return opaSchemeItem;
	}
	
	public List<OpaSchemeItem> findList(OpaSchemeItem opaSchemeItem) {
		return super.findList(opaSchemeItem);
	}
	
	public Page<OpaSchemeItem> findPage(Page<OpaSchemeItem> page, OpaSchemeItem opaSchemeItem) {
		return super.findPage(page, opaSchemeItem);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaSchemeItem opaSchemeItem) {
		super.save(opaSchemeItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaSchemeItem opaSchemeItem) {
		super.delete(opaSchemeItem);
	}

	public List<OpaSchemeItem> findListForAssign(OpaSchemeItem opaSchemeItem) {
		return opaSchemeItemDao.findListForAssign(opaSchemeItem);
	}

	@Transactional(readOnly = false)
	public void editAssign(Map<String, Object> param) {
		opaSchemeItemDao.editAssign(param);
	}
	
	
	@Transactional(readOnly = false)
	public void updateGx(Map<String,Object>param) {
		opaSchemeItemDao.updateGex(param);
	}
	
	
	@Transactional(readOnly=false)
	public List<OpaSchemeItem> schemeFindList(String shemeId) {
	  return opaSchemeItemDao.schemefindList(shemeId);
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(OpaSchemeItem opaSchemeItem,OpaPlanOffice opaPlanOffice) {
		String schemeId = opaSchemeItem.getSchemeId();
		String officeId=opaSchemeItem.getAuditorOfficeId();
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
		int k=0;
		for(int i=0;i<list.size();i++) {
			if(i==list.size()-1) {
				OpaScheme status = opaSchemeDao.get(param.getSchemeId());
				status.setStatus(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
				status.setOfficeId(officeId);
				opaSchemeDao.update(status);
			}
			k++;
		}
	}
	
	
	@Transactional(readOnly = false)
	public void itemInsert(OpaSchemeItem entity) {
		if(entity.getId()!="") {
			OpaSchemeItem m = new OpaSchemeItem ();
			m.setId(IdGen.uuid());
			m.setSchemeId(entity.getSchemeId());
		    m.setItemId(entity.getItemId());
		    m.setItemParentId(entity.getItemParentId());
		    m.setItemParentIds(entity.getItemParentIds());
		    m.setCode(entity.getCode());
		    m.setName(entity.getName());
		    m.setType(entity.getType());
		    m.setLevel(entity.getLevel());
		    m.setContent(entity.getContent());
		    m.setMethod(entity.getMethod());
		    m.setIfNum(entity.getIfNum());
		    m.setValue(entity.getValue());
		    m.setDateFrom(entity.getDateFrom());
		    m.setDateTo(entity.getDateTo());
		    m.setDelFlag(entity.getDelFlag());
		    m.setCreateBy(entity.getCreateBy());
		    m.setCreateDate(entity.getCreateDate());
		    m.setUpdateBy(entity.getUpdateBy());
		    m.setUpdateDate(entity.getUpdateDate());
		    m.setStatus(entity.getStatus());
		    m.setRemarks(entity.getRemarks());
		    m.setAuditorOfficeId(entity.getAuditorOfficeId());
		    m.setExamination(entity.getExamination());
		    m.setSort(entity.getSort());
		    m.setAuditorId(entity.getAuditorId());
		    opaSchemeItemDao.insert(m);
		}
	}
	
	
	@Transactional(readOnly= false)
	public void updateIssud(OpaSchemeItem opaSchemeItem,String schemeId,HttpServletRequest request) {
		String officeId=opaSchemeItem.getAuditorOfficeId();
		OpaScheme status=opaSchemeDao.get(schemeId);
		status.setStatus(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
		status.setOfficeId(officeId);
		opaSchemeDao.update(status);
	}
	/*@Transactional(readOnly=false)
	public void allvalue(OpaSchemeItem opaSchemeItem) {
		String schemeId=opaSchemeItem.getSchemeId();
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
		StringBuilder builder = new StringBuilder();
		 for(OpaSchemeItem item : list) {
	          // 如果不存在返回 -1。
	          if(builder.indexOf(","+item+",") > -1) {
	        	  List<OpaSchemeItem> Sname=opaSchemeItemDao.opaSchemeItemName(item.getName());
	        	  for(OpaSchemeItem it:Sname) {
	        		  OpaSchemeItem m = new OpaSchemeItem ();
	        		  m.setValue(item.getValue());
	        		  opaSchemeItemDao.updateValue(it.getName());
	        	  }
	          } else {
	              builder.append(",").append(item).append(",");
	          }
	      }

		
	}
	*/
	
	@Transactional(readOnly = false)
	public void updateStatus1(OpaSchemeItem opaSchemeItem) {
		String schemeId = opaSchemeItem.getSchemeId();
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		OpaScheme status = opaSchemeDao.get(param.getSchemeId());
		status.setStatus(DictUtils.getDictValue("已完成", "opa_scheme_status", ""));
		opaSchemeDao.update(status);
	}
	
	@Transactional(readOnly = false)
	public void assignAuditPass(OpaSchemeItem opaSchemeItem) {
		//查询方案状态
		opaSchemeItem = opaSchemeItemDao.get(opaSchemeItem);
		opaSchemeItem.setStatus(DictUtils.getDictValue("待分配", "opa_schemeItem_status", ""));
		super.save(opaSchemeItem);
		
		//全部审核后更新状态为填报
		String schemeId = opaSchemeItem.getSchemeId();
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		param.setStatus(DictUtils.getDictValue("待分配", "opa_schemeItem_status", ""));
		opaSchemeItemDao.update(param);
		
		List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
		for(OpaSchemeItem item: list) {
			if(list.size()-list.size()-1==1) {
				//更新考核方案表状态
				OpaScheme opaScheme2 = opaSchemeDao.get(param.getSchemeId());
				opaScheme2.setStatus(DictUtils.getDictValue("指标分配中", "opa_scheme_status", ""));
				opaSchemeDao.update(opaScheme2);
			}else {
				continue;
			}
			continue;
		}
		
		if(null == list || list.isEmpty()){
			param.setStatus(DictUtils.getDictValue("待分配", "opa_schemeItem_status", ""));
			opaSchemeItemDao.setAllToStatus(param);
			OpaScheme opaScheme = opaSchemeDao.get(schemeId);
			opaScheme.setStatus(DictUtils.getDictValue("指标分配中", "opa_scheme_status", ""));
			opaSchemeDao.update(opaScheme);
		}
	}
	
	@Transactional(readOnly = false)
	public void allPass(OpaSchemeItem opaSchemeItem,String schemeId) {
		opaSchemeItemDao.updateAllByStatus(schemeId);
		OpaScheme scheme=opaSchemeDao.get(schemeId);
		scheme.setStatus(DictUtils.getDictValue("指标分配中", "opa_scheme_status", ""));
		opaSchemeDao.update(scheme);
		
	}
	
	@Transactional(readOnly = false)
	public void applyAuditPass(OpaSchemeItem opaSchemeItem) {
		//String schemeItemType=opaSchemeItem.getType();
		//User user=UserUtils.getUser();
		opaSchemeItem = opaSchemeItemDao.get(opaSchemeItem);
		opaSchemeItem.setStatus(DictUtils.getDictValue("已提交", "opa_schemeItem_status", ""));
		super.save(opaSchemeItem);
		
		//全部审核后更新状态为填报待审核
		String schemeId = opaSchemeItem.getSchemeId();
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		param.setStatus(DictUtils.getDictValue("已提交", "opa_schemeItem_status", ""));
		opaSchemeItemDao.update(param);
		String officeId = opaSchemeItem.getAuditorOfficeId();
		System.out.println(officeId);
		OpaPlanTask task = new OpaPlanTask();
		task.setId(IdGen.uuid());
		task.setPlanId(param.getSchemeId());
		task.setOfficeId(officeId);
		task.setItemId(opaSchemeItem.getItemId());
		task.setItemParentId(opaSchemeItem.getItemParentId());
		task.setItemParentIds(opaSchemeItem.getItemParentIds());
		task.setCode(opaSchemeItem.getCode());
		task.setName(opaSchemeItem.getName());
		task.setType(opaSchemeItem.getType());
		task.setLevel(opaSchemeItem.getLevel());
		task.setContent(opaSchemeItem.getContent());
		task.setMethod(opaSchemeItem.getMethod());
		task.setIfNum(opaSchemeItem.getIfNum());
		task.setValue(opaSchemeItem.getValue());
		task.setCount(opaSchemeItem.getCount());
		task.setSort(opaSchemeItem.getSort());
		task.setDateFrom(opaSchemeItem.getDateFrom());
		task.setDateTo(opaSchemeItem.getDateTo());
		task.setDelFlag(opaSchemeItem.getDelFlag());
		task.setCreateBy(opaSchemeItem.getCreateBy());
		task.setCreateDate(opaSchemeItem.getCreateDate());
		task.setUpdateBy(opaSchemeItem.getUpdateBy());
		task.setUpdateDate(opaSchemeItem.getUpdateDate());
		//task.setAuditorId(opaSchemeItem.getAuditorId());
		task.setStatus(DictUtils.getDictValue("待填报", "opa_planTask_status", ""));
		task.setRemarks(opaSchemeItem.getRemarks());
		task.setAuditorId(opaSchemeItem.getAuditorOfficeId());
		task.setAuditorOfficeId(opaSchemeItem.getAuditorOfficeId());
		opaPlanTaskDao.insert1(task);
/*		//查询分数为0的个性指标
		List<OpaSchemeItem> gxClass=opaSchemeItemDao.TypeFind(schemeItemType);
		for(OpaSchemeItem item:gxClass) {
		String Type=item.getType();
			if(schemeItemType.equals(Type)) {
				task.setId(IdGen.uuid());
				task.setPlanId(param.getSchemeId());
				task.setOfficeId(officeId);
				task.setItemId(item.getItemId());
				task.setItemParentId(item.getItemParentId());
				task.setItemParentIds(item.getItemParentIds());
				task.setCode(item.getCode());
				task.setName(item.getName());
				task.setType(item.getType());
				task.setLevel(item.getLevel());
				task.setContent(item.getContent());
				task.setMethod(item.getMethod());
				task.setIfNum(item.getIfNum());
				task.setValue(item.getValue());
				task.setCount(item.getCount());
				task.setSort(item.getSort());
				task.setDateFrom(item.getDateFrom());
				task.setDateTo(item.getDateTo());
				task.setDelFlag(item.getDelFlag());
				task.setCreateBy(user);
				task.setCreateDate(item.getCreateDate());
				task.setUpdateBy(user);
				task.setUpdateDate(item.getUpdateDate());
				//task.setAuditorId(opaSchemeItem.getAuditorId());
				task.setStatus(DictUtils.getDictValue("待填报", "opa_planTask_status", ""));
				task.setRemarks(item.getRemarks());
				task.setAuditorId(item.getAuditorOfficeId());
				opaPlanTaskDao.insert1(task);
			}
	     }*/
		//更新方案表状态
		/*OpaScheme opaScheme2 = opaSchemeDao.get(param.getSchemeId());
		opaScheme2.setStatus(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
		opaSchemeDao.update(opaScheme2);
*/		
	List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
	int i=0;
	for( i=0;i<list.size();i++) {
		if(list.size() == i && list.get(i).getStatus().equals("4")) {
			OpaScheme opaScheme=opaSchemeDao.get(schemeId);
			opaScheme.setStatus(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
			opaSchemeDao.update(opaScheme);
		}
		i++;
	}
	if(null == list || list.isEmpty()){
	//更新考核方案表状态
    	param.setStatus(DictUtils.getDictValue("已提交", "opa_schemeItem_status", ""));
	    opaSchemeItemDao.setAllToStatus(param);
	    OpaScheme opaScheme = opaSchemeDao.get(schemeId);
	    opaScheme.setStatus(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
	    opaSchemeDao.update(opaScheme);
	}
	for(OpaSchemeItem Item:list) {
		if(Item.getStatus()=="1") {
			opaSchemeItemDao.updateBstatus(Item);
		}
	}
	}
	
	
	@Transactional(readOnly = false)
	public void allScore(String schemeId,OpaSchemeItem opaSchemeItem) {
		User user=UserUtils.getUser();
		List<OpaSchemeItem> list = opaSchemeItemDao.getAllScoreList(schemeId);
		for(OpaSchemeItem item:list) {
			String officeId = item.getAuditorOfficeId();
			OpaPlanTask task = new OpaPlanTask();
			task.setId(IdGen.uuid());
			task.setPlanId(schemeId);
			task.setOfficeId(officeId);
			task.setItemId(item.getItemId());
			task.setItemParentId(item.getItemParentId());
			task.setItemParentIds(item.getItemParentIds());
			task.setCode(item.getCode());
			task.setName(item.getName());
			task.setType(item.getType());
			task.setLevel(item.getLevel());
			task.setContent(item.getContent());
			task.setMethod(item.getMethod());
			task.setIfNum(item.getIfNum());
			task.setValue(item.getValue());
			task.setCount(item.getCount());
			task.setSort(item.getSort());
			task.setDateFrom(item.getDateFrom());
			task.setDateTo(item.getDateTo());
			task.setDelFlag(item.getDelFlag());
			task.setCreateBy(user);
			task.setCreateDate(item.getCreateDate());
			task.setUpdateBy(user);
			task.setUpdateDate(item.getUpdateDate());
			task.setAuditorId(item.getAuditorId());
			task.setStatus(DictUtils.getDictValue("待填报", "opa_planTask_status", ""));
			task.setRemarks(item.getRemarks());
			task.setAuditorId(item.getAuditorOfficeId());
			opaPlanTaskDao.insert1(task);
		}
		OpaScheme scheme=opaSchemeDao.get(schemeId);
		scheme.setStatus(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
		opaSchemeDao.update(scheme);
	}
	
	
	@Transactional(readOnly = false)
	public void applyAuditPass2(OpaSchemeItem opaSchemeItem) {
		opaSchemeItem = opaSchemeItemDao.get(opaSchemeItem);
		opaSchemeItem.setStatus(DictUtils.getDictValue("填报待审核", "opa_schemeItem_status", ""));
		super.save(opaSchemeItem);
		//全部审核后更新状态为填报已审核
		String schemeId = opaSchemeItem.getSchemeId();
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		//更新方案表状态
		OpaScheme opaScheme2 = opaSchemeDao.get(param.getSchemeId());
		opaScheme2.setStatus(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
		opaSchemeDao.update(opaScheme2);
		List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
		if(null == list || list.isEmpty()){
			param.setStatus(DictUtils.getDictValue("填报已审核", "opa_schemeItem_status", ""));
			opaSchemeItemDao.setAllToStatus(param);
			OpaScheme opaScheme = opaSchemeDao.get(schemeId);
			opaScheme.setStatus(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
			opaSchemeDao.update(opaScheme);
		}
	}

	

	public Double getChildSum(OpaSchemeItem parent) {
		return opaSchemeItemDao.getChildSum(parent);
	}
	
	/**
	 * @param schemeId
	 * @param itemParentId
	 * @return 返回父级评分标准及本级评分标准合计值Map
	 */
	public Map<String, Double> getValueMap(String schemeId, String itemParentId) {
		OpaSchemeItem parent = new OpaSchemeItem();
		parent.setSchemeId(schemeId);
		parent.setItemId(itemParentId);
		parent = opaSchemeItemDao.get(parent);
		Double childSum = opaSchemeItemDao.getChildSum(parent);
		Double sumValue = parent.getValue();
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("sumValue", sumValue==null?0:sumValue);
		map.put("childSum", childSum==null?0:childSum);
		return map;
	}
	
	/*
	 * @author: xiaobai
	 * @date: 2019年7月23日
	 * @parameter: @param opaSchemeItem
	 * @parameter: @return
	 * @Description: 为了分配都可以完成
	 */
	public int findStatus(OpaSchemeItem opaSchemeItem) {
		return opaSchemeItemDao.findStatus(opaSchemeItem);
	}
	
	/*
	 * @author: xiaobai
	 * @date: 2019年7月23日
	 * @parameter: @param opaSchemeItem
	 * @parameter: @return
	 * @Description: 为了分配都可以完成
	 */
	public int findStatus2(OpaSchemeItem opaSchemeItem) {
		return opaSchemeItemDao.findStatus2(opaSchemeItem);
	}
}