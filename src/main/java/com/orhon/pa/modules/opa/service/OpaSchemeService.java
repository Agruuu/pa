/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.BaseEntity;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.modules.opa.dao.OpaAssociationDao;
import com.orhon.pa.modules.opa.dao.OpaItemDao;
import com.orhon.pa.modules.opa.dao.OpaOverviewDao;
import com.orhon.pa.modules.opa.dao.OpaPlanOfficeDao;
import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaAssociation;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaOverview;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.opa.entity.OpaSummaryType;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 考核方案管理模块Service
 * @author Shawn
 * @version 2017-04-18
 */
@Service
@Transactional(readOnly = true)
public class OpaSchemeService extends CrudService<OpaSchemeDao, OpaScheme>{

	@Autowired
	private OpaItemDao opaItemDao;
	@Autowired
	private OpaSchemeDao opaSchemeDao;
	@Autowired
	private OpaSchemeItemDao opaSchemeItemDao;
	@Autowired
	private OpaPlanOfficeDao opaPlanOfficeDao;
	@Autowired
	private OpaOverviewDao opaOverviewDao;
	@Autowired
	private OpaPlanTaskDao opaPlanTaskDao;
	
	@Autowired
	private OpaItemService opaItemService;
	
    @Autowired
    private OpaSummaryTypeService TypeService;
	
	@Autowired
	private OpaAssociationDao opaAssociationDao;

	
	public OpaScheme get(String id) {
		OpaScheme opaScheme = super.get(id);
		return opaScheme;
	}
	
	public List<OpaScheme> findList(OpaScheme opaScheme) {
		return super.findList(opaScheme);
	}
	
	public List<Status> findListByStatus(Map<String,Object> param) {
		return opaSchemeDao.findListByStatus(param);
	}
	
	
	public List<Status> findlistByStatus1(Map<String,Object>param){
		return opaSchemeDao.findlistByStatus1(param);
	}
	
	public List<Status> findlistByStatus2(Map<String,Object>param){
		return opaSchemeDao.findlistByStatus2(param);
	}
	
	
	/*
	 * 考核资料上传
	 * 根据当前登录用户查询当前单位的所有指标名称
	 */
	public List<Status> zhibiaoName(Map<String,Object>param){
		return opaSchemeDao.zhibiaoName(param);
	}
	
	public Page<OpaScheme> findPage(Page<OpaScheme> page, OpaScheme opaScheme) {
		return super.findPage(page, opaScheme);
	}
	
	
	@Transactional(readOnly = false)
	public void save(OpaScheme opaScheme) {
		super.save(opaScheme);
	}
	
	@Lazy(true)
	@Transactional(readOnly = false)
	public void repoted(OpaScheme opaScheme) {
		String rootId=opaScheme.getItemParent().getId();
		List<OpaItem> opaItemList = opaItemDao.findTreeByIdLike(opaScheme.getItemParent());
		String rootType= new String();
		User user = UserUtils.getUser();
		int sort=0;
		OpaSchemeItem opaBounsItem = new OpaSchemeItem();
		for(OpaItem item:opaItemList) {
			 OpaAssociation opaAssociation = new OpaAssociation();
		     opaAssociation.setId(IdGen.uuid());
		     opaAssociation.setSchemeId(opaScheme.getId());
		     opaAssociation.setItemId(item.getId());
		     opaAssociation.setOfficeId("");
		     opaAssociation.setCreateBy(user);
		     opaAssociation.setCreateDate(new Date());
		     opaAssociation.setDateFrom(item.getDateFrom());
		     opaAssociation.setDateTo(item.getDateTo());
		     opaAssociation.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		     opaAssociationDao.insert(opaAssociation);
		}
		for(OpaItem opaItem:opaItemList) {
				OpaSchemeItem opaSchemeItem=new OpaSchemeItem();
				opaSchemeItem.setId(IdGen.uuid());
				opaSchemeItem.setSchemeId(opaScheme.getId());
				opaSchemeItem.setName(opaItem.getName());
				opaSchemeItem.setItemId(opaItem.getId());
				rootType=opaItem.getType();
				if(opaItem.getId().equals(rootId)) {
					//opaSchemeItem.setItemParentId("0");
					//opaSchemeItem.setItemParentIds("0,");
					opaSchemeItem.setItemParentId(opaItem.getParentId());
					opaSchemeItem.setItemParentIds(opaItem.getParentIds());
					rootType=opaItem.getType();
				    DictUtils.getDictValue("共性指标","opa_item_type","");
				    String parentIds=opaItem.getParentId();
				    if(parentIds.equals("0")) {
				    	opaBounsItem.setItemParentIds("0,"+parentIds);
				    }else {
				    	opaBounsItem.setItemParentIds("0,"+parentIds);
				    }
				    opaBounsItem.setLevel(opaItem.getLevel());
				    if(opaItem.getSort()>=sort) {
						sort=opaItem.getSort()+30;
						opaBounsItem=opaSchemeItem;
						opaBounsItem.setSort(sort);
					}
				}else {
					opaSchemeItem.setItemParentId(opaItem.getParentId());
					String parentIds=opaItem.getParentId();
					if(parentIds.indexOf(rootType)==-1) {
	                 opaSchemeItem.setItemParentIds("0,"+parentIds);
					}else {
						opaSchemeItem.setItemParentIds("0,"+parentIds.substring(parentIds.indexOf(rootType)));
					}
				}
				opaSchemeItem.setLevel(opaItem.getLevel()-opaScheme.getLevel());
				opaSchemeItem.setCode(opaItem.getCode());
				opaSchemeItem.setSort(opaItem.getSort());
				opaSchemeItem.setLevel(opaItem.getLevel());
				opaSchemeItem.setContent("");
				opaSchemeItem.setMethod(DictUtils.getDictValue("机器汇总","opa_item_method",""));
				opaSchemeItem.setIfNum(DictUtils.getDictValue("非数值", "opa_item_num_type",""));
				opaSchemeItem.setValue(0D);
				opaSchemeItem.setType(rootType);
				opaSchemeItem.setDateFrom(opaItem.getDateFrom());
				opaSchemeItem.setRemarks(opaItem.getRemarks());
				opaSchemeItem.setDateTo(opaItem.getDateTo());
				opaSchemeItem.setUpdateDate(new Date());
				opaSchemeItem.setCreateDate(opaItem.getCreateDate());
				opaSchemeItem.setExamination(opaScheme.getExamination());
				opaSchemeItem.setCreateBy(user);
				opaSchemeItem.setUpdateBy(opaItem.getUpdateBy());
				opaSchemeItem.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
				opaSchemeItem.setAuditorId("");
				opaSchemeItem.setStatus(DictUtils.getDictValue("待审核", "opa_schemeItem_status",""));
				opaSchemeItemDao.insert(opaSchemeItem);
				if(opaItem.getParentId().equals(rootId)) {
					if(opaItem.getSort()>=sort) {
						sort=opaItem.getSort()+30;
						opaBounsItem=opaSchemeItem;
						opaBounsItem.setSort(sort);
				}
			}
		}
		opaSchemeDao.update(opaScheme);
	}

	
	@Transactional(readOnly = false)
	public void delete(OpaScheme opaScheme) {
		super.delete(opaScheme);
	}
	
	@Transactional(readOnly = false)
	public void auditPass(OpaScheme opaScheme) {
		String rootId = opaScheme.getItemParent().getId();
		List<OpaItem> opaItemList = opaItemDao.findTreeByIdLike(opaScheme.getItemParent());
		String rootType = new String();
		User user = UserUtils.getUser();
		int sort = 0;
		OpaSchemeItem opaBounsItem = new OpaSchemeItem();
		for(OpaItem opaItem : opaItemList){
			OpaSchemeItem opaSchemeItem = new OpaSchemeItem();
			opaSchemeItem.setId(IdGen.uuid());
			opaSchemeItem.setSchemeId(opaScheme.getId());
			opaSchemeItem.setName(opaItem.getName());
			opaSchemeItem.setItemId(opaItem.getId());
			rootType = opaItem.getType();
			if(opaItem.getId().equals(rootId)){
				opaSchemeItem.setItemParentId("0");
				opaSchemeItem.setItemParentIds("0,");
				rootType = opaItem.getType();
				DictUtils.getDictValue("共性指标", "opa_item_type", "");
			}else{
				opaSchemeItem.setItemParentId(opaItem.getParentId());
				String parentIds = opaItem.getParentIds();
				opaSchemeItem.setItemParentIds("0," + parentIds.substring(parentIds.indexOf(rootId)));
			}
			opaSchemeItem.setCode(opaItem.getCode());
			opaSchemeItem.setSort(opaItem.getSort());
			opaSchemeItem.setLevel(opaItem.getLevel()-opaScheme.getLevel());
			opaSchemeItem.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
			opaSchemeItem.setIfNum(DictUtils.getDictValue("非数值", "opa_item_num_type", ""));
			opaSchemeItem.setType(rootType);
			opaSchemeItem.setDateFrom(opaItem.getDateFrom());
			opaSchemeItem.setDateTo(opaItem.getDateTo());
			opaSchemeItem.setRemarks(opaItem.getRemarks());
			opaSchemeItem.setCreateBy(user);
			opaSchemeItem.setUpdateBy(user);
			opaSchemeItem.setCreateDate(new Date());
			opaSchemeItem.setUpdateDate(opaSchemeItem.getCreateDate());
			opaSchemeItem.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
			opaSchemeItem.setStatus(DictUtils.getDictValue("待审核", "opa_schemeItem_status", ""));
			opaSchemeItemDao.update(opaSchemeItem);
			if(opaItem.getParentId().equals(rootId)){
				if(opaItem.getSort()>=sort){
					sort = opaItem.getSort()+30;
					opaBounsItem = opaSchemeItem;
					opaBounsItem.setSort(sort);
				}
			}
		}
		opaSchemeDao.update(opaScheme);
	}
	
	
	@Transactional(readOnly = false)
	public void over(OpaScheme opaScheme) {
		String planId = opaScheme.getId();
		List<OpaPlanTask> list=opaPlanTaskDao.getDepartName(planId);//排除多余的单位
		int i;
		for(i=0;i<list.size();i++) {
			OpaPlanTask task=list.get(i);
			OpaPlanOffice planOffice = new OpaPlanOffice();
			planOffice.setId(IdGen.uuid());
			planOffice.setPlanId(planId);
			planOffice.setOfficeId(task.getAuditorId());
			planOffice.setCreateBy(opaScheme.getCreateBy());
			planOffice.setCreateDate(opaScheme.getCreateDate());
			planOffice.setDelFlag(opaScheme.getDelFlag());
			planOffice.setRemarks(opaScheme.getRemarks());
			planOffice.setUpdateBy(opaScheme.getUpdateBy());
			planOffice.setUpdateDate(opaScheme.getUpdateDate());
			opaPlanOfficeDao.insert1(planOffice);
			
            
			List<OpaItem> opaItemList = opaItemService.findList(new OpaItem());
		    for(int j=0;j<opaItemList.size();j++ ) {
		    	OpaItem item = opaItemList.get(j);
		    	if(item.getId().equals(task.getItemId()) && !item.getLevel().equals(0)) {
		    		List<OpaItem> itemNameList = opaItemDao.itemFindList(task.getItemId()); 
		    		for(int k =0;k<itemNameList.size();k++) {
		    			OpaItem  itemParent = itemNameList.get(k);
		    			if(itemParent.getLevel()>1 ) {
		    			List<OpaItem> tem=opaItemDao.itemParentId(itemParent.getParent().getId());
		    			for(int t=0;t<tem.size();t++) {
		    				OpaItem temlist = tem.get(t);
		    				OpaSummaryType  ty = new OpaSummaryType();
			    			ty.setId(IdGen.uuid());
			    			ty.setSummaryId(task.getTableValue());
			    			ty.setItemId(temlist.getId());
			    			ty.setLevel(temlist.getLevel());
			    			ty.setCreateDate(new Date());
			    			TypeService.insert(ty);
			    			if(temlist.getLevel()>=2) {
			    				List<OpaItem> opaitem = opaItemDao.itemParentId(temlist.getParent().getId());
			    				for(int y =0;y<opaitem.size();y++) {
			    					ty.setId(IdGen.uuid());
			    					ty.setSummaryId(task.getTableValue());
			    					ty.setItemId(opaitem.get(y).getId());
			    					ty.setLevel(opaitem.get(y).getLevel());
			    					ty.setCreateDate(new Date());
			    					TypeService.insert(ty);
			    					if(2<=opaitem.get(y).getLevel()) {
			    						List<OpaItem> opaitem1 = opaItemDao.itemParentId(opaitem.get(y).getParent().getId());
			    						for(int u=0;u<opaitem1.size();u++) {
			    							ty.setId(IdGen.uuid());
			    							ty.setSummaryId(task.getTableValue());
			    							ty.setItemId(opaitem1.get(u).getId());
			    							ty.setLevel(opaitem1.get(u).getLevel());
			    							ty.setCreateDate(new Date());
			    							TypeService.insert(ty);
			    						}
			    					}
			    				}
			    			}
		    			 }
		    		   }
		    		}
		    	}
		    	OpaItem schme=opaItemDao.get(opaScheme.getItemParent().getId());
		    	List<OpaSchemeItem> item1=opaSchemeItemDao.ItemList(schme.getId(),planId);
		    	for(int ll=0;ll<item1.size();ll++) {
		    		if(item1.get(ll).getLevel()!=0 && item1.get(ll).getItemParentId().equals("0") ) {
			    		Map<String,Object>param = new HashMap<String,Object>();
			    		param.put("itemParentId", schme.getParentId());
			    		param.put("itemParentIds", schme.getParentIds());
			    		param.put("itemId", schme.getId());
			    		param.put("schemeId", planId);
			    			opaSchemeItemDao.updateParentIdParentIds(param);
			    	}
		    	}
		    	
		    }
		}
		OpaOverview op = new OpaOverview();
		op.setId(IdGen.uuid());
		op.setSchemeId(planId);
		op.setCreateDate(opaScheme.getCreateDate());
		op.setAssessmentScore("0");   // 考核组评分(20分)
		op.setCountyScore("0");      // 县级领导评分(30分)
		op.setLeadershipAssessment("0"); // 领导班子测评(30分)
		op.setYearResult("0");      // 年底考核成绩
		op.setPeerReview("0");      // 单位互评(30分)
		op.setOverallScore("0");    // 综合评分
		op.setServiceRating("0");   // 服务对象评分(40分)
		op.setDaiScore("0");
		op.setLevel(0);
		op.setLeadingScore("0");   // 领导评价(50分)
		op.setCreateBy(opaScheme.getCreateBy());
		op.setDelFlag("3");
		op.setUpdateBy(opaScheme.getUpdateBy());
		op.setUpdateDate(opaScheme.getUpdateDate());
        opaOverviewDao.OverInsert(op);

	}
	
}