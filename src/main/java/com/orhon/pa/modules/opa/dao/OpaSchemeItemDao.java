/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaOverview;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;

/**
 * 方案指标模块DAO接口
 * @author Shawn
 * @version 2017-04-21
 */
@MyBatisDao
public interface OpaSchemeItemDao extends CrudDao<OpaSchemeItem> {

	OpaSchemeItem getParentSchemeItem(OpaSchemeItem opaSchemeItem);
	
	List<OpaSchemeItem> findListForAssign(OpaSchemeItem opaSchemeItem);

	void editAssign(Map<String, Object> param);

	void auditPass(OpaSchemeItem opaSchemeItem);

	List<OpaSchemeItem> findNotPassList(OpaSchemeItem param);

	void setAllToStatus(OpaSchemeItem param);

	Double getChildSum(OpaSchemeItem parent);

	int getHeadLevel(OpaSchemeItem itemParam);

	List<OpaSchemeItem> findChild(OpaSchemeItem item);
	
	List<OpaSchemeItem>schemefindList(String schemeId);
	
	@Update("UPDATE opa_scheme_item "
			+ "SET auditor_id = #{auditorId}, auditor_office_id = #{auditorOfficeId},status =  #{status} WHERE id=#{id}")
	void updateGex(Map<String,Object>param);
	
	
	@Select("SELECT item_id AS \"itemId\",level,item_parent_id AS \"itemParentId\",item_parent_ids AS \"itemParentIds\" FROM opa_scheme_item where item_id=#{itemId} and scheme_id=#{schemeId}")
	List<OpaSchemeItem> ItemList(@Param("itemId")String itemId,@Param("schemeId")String schemeId);
	
	@Update("UPDATE opa_scheme_item set item_parent_id = #{itemParentId}, item_parent_ids=#{itemParentIds} where item_id=#{itemId} and scheme_id=#{schemeId}")
	void updateParentIdParentIds(Map<String,Object>param);
	
	
	
	
	
	/**
	 * 通过父id查询子节点
	 * @param parentId
	 * @return
	 */
	@Select({
		 " SELECT id,name,level FROM opa_item where PARENT_ID=#{id} and remarks not in (-2) order by sort asc"
	 })
	List<OpaSchemeItem> findImmobilization(String parentId);
	
	
	/**
	 * 通过父id查询子节点
	 * @param parentId
	 * @return
	 */
	@Select({"SELECT id,name,level FROM opa_item where PARENT_ID=#{id} and remarks!='-1,-3' and name!='操作' order by sort asc"})
	List<OpaSchemeItem> findAutoHeader(String parentId);
	
	
	/**
	 * 通过父id查询子节点
	 * @param parentId
	 * @return
	 */
	@Select({
		 " SELECT id,name,level FROM opa_item where PARENT_ID=#{id} and remarks='-2'"
	 })
	List<OpaSchemeItem> findButton(String parentId);
	
	
	/**
	 * 通过计划id查询评分信息
	 * @param id
	 * @return
	 */
	@Select({
		"select YEAR_RESULT yearResult,"
		+ "LEADING_SCORE leadingScore,"
		+ "COUNTY_SCORE countyScore,"
		+ "PEER_REVIEW peerReview,"
		+ "LEADERSHIP_ASSESSMENT leadershipAssessment,"
		+ "SERVICE_RATING serviceRating,"
		+ "ASSESSMENT_SCORE assessmentScore,"
		+ "DAI_SCORE daiScore,"
		+ "OVERALL_SCORE overallScore"
		+ " from opa_overview"
		+ " where SCHEME_ID=#{id}"
	})
	OpaOverview findImmobilizationContent(String id);
	
	
	/**
	 * 修改个性指标状态
	 * @param parentId
	 * @return
	 */
	@Update({"UPDATE opa_scheme_item SET status='7' where type='2'"})
	List<OpaSchemeItem> updateStatus(OpaSchemeItem opaSchemeItem);
	
	/**
	 * 修改固定表头状态
	 * @param opaSchemeItem
	 * @return
	 */
    @Update({"UPDATE opa_scheme_item SET status='4' where status=1"})
    List<OpaSchemeItem>updateBstatus(OpaSchemeItem opaSchemeItem);
    
    /**
     * 查询分数为0的个性指标
     * @param opaSchemeItem
     * @return
     */
    @Select({"select * from opa_scheme_item where type='2' and VALUE=0"})
    List<OpaSchemeItem>TypeFind(String type);
    
    
    
    /**
     * 查询所有同样的姓名
     * @param opaSchemeItem
     * @return
     */
    @Select({"SELECT * FROM opa_scheme_item where name=#{name}"})
    List<OpaSchemeItem>opaSchemeItemName(String name);
    
    
    /**
     * @param tm
     * @return
     */
    @Update({"UPDATE opa_scheme_item SET value=#{value},if_num=#{ifNum}, status=#{status}, scheme_id=#{schemeId} where id=#{id}"})
    void updateValue(OpaSchemeItem tm);
    
   /**
    * 办公室全部审核
    * @param schemeId
    */
	void updateAllByStatus(String schemeId);
	
	@Update({"update opa_scheme_item set status='4' where scheme_id=#{schemeId}"})
	void updateAllIssued(String schemeId);
	
	
	/**
	 * 指标分数填报   全部下发
	 * @param schemeId
	 */
	@Update({"UPDATE opa_scheme_item set status=#{status} where scheme_id=#{schemeId}"})
	void updateAllScoreIssued(OpaSchemeItem param);
	
	
	/**
	 * 查出指标方案
	 * @param schemeId
	 * @return
	 */
	@Select({"SELECT * FROM opa_scheme_item where  scheme_id=#{schemeId}"})
	List<OpaSchemeItem> getAllScoreList(String schemeId);
	
	//查询列表数据 除去多余重复
	List<OpaSchemeItem> getFindList(OpaSchemeItem opaSchemeItem);
	
	
	
	
	List<OpaSchemeItem> getSummaryList(String Identificationsymbo);
	
	
	@Select("select COUNT(*) as total from opa_scheme_item where SCHEME_ID = #{schemeId} and STATUS = '7' or STATUS = '3' or STATUS = '2' ")
	int findStatus(OpaSchemeItem opaSchemeItem);
	
	@Select("select COUNT(*) as total from opa_scheme_item where SCHEME_ID = #{schemeId} and STATUS = '4' ")
	int findStatus2(OpaSchemeItem opaSchemeItem);

}