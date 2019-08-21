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
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;

/**
 * 计划任务模块DAO接口
 * @author Shawn
 * @version 2017-04-26
 */
@MyBatisDao
public interface OpaPlanTaskDao extends CrudDao<OpaPlanTask> {

	Double getChildSum(OpaPlanTask parent);
	
	List<OpaPlanTask> findCommonChild(OpaPlanTask opt);
	
	OpaPlanTask findParentTask(OpaPlanTask opt);

	List<OpaPlanTask> findNotPassList(OpaPlanTask param);

	void setAllToStatus(OpaPlanTask param);
	
	List<OpaPlanTask> findChild(OpaPlanTask task);
	
	List<Status> findListByStatus(Map<String, Object> param);
	
	void insert1(OpaPlanTask opaPlanTask);
	
	//根据itemId来查询一条数据
	@Select("SELECT a.status FROM opa_plan_task a WHERE id=#{id}")
	OpaPlanTask findStatus (String id);
	
	//itemId=0时修改状态为待评分
	@Update("UPDATE opa_plan_task SET \n" + "status='6'\n" + "WHERE ITEM_PARENT_ID=\"0\"  ")
	void updateStatus(OpaPlanTask opaPlanTask);
	
	//父级无需打分，直接修改状态。
	@Update("UPDATE opa_plan_task SET \n" + "status='9'\n" + "WHERE ITEM_PARENT_ID=\"0\"  ")
	void updateStatus1(OpaPlanTask opaPlanTask);
	
	
	//深度为0,1,2,3时无需打分 直接修改状态
	@Update({"update opa_plan_task SET status=9 where level in (0,1,2,3)"})
	List<OpaPlanTask> updateStatusL(OpaPlanTask opaPlanTask);
	
	
	//通过itemId来查询itemParentId为0的一条数据。
	OpaPlanTask getOneList(String itemParentId);
	
	
	List<OpaPlanTask> officeFindList(OpaPlanTask param);
	
	
	//查询最大的深度
	@Select("SELECT max(a.SORT) maxsort FROM opa_plan_task a WHERE a.plan_id=#{itemParentId}")
	OpaPlanTask MaxSort(String id);
	
	/**
	 * 上传资料  修改父级状态
	 * @param id
	 */
	@Update({"UPDATE opa_plan_task SET status='6' where ITEM_ID=#{ITEM_PARENT_ID}"})
	void statusUpdate(String itemId);
	
	/**
	 * 考核打分  修改父级状态
	 * @param id
	 */
	@Update({"UPDATE opa_plan_task SET status='9' WHERE ITEM_ID=#{ITEM_PARENT_ID}"})
	void statusUpdate1(String id);
	
	
	/**
	 * 完成资料上传 修改父级状态
	 * @param plan
	 */
	@Update({"UPDATE opa_plan_task SET status='6' WHERE plan_id=#{planId}"})
	void UpdateDataStatus(String planId);
	
	
	/**
	 * 根据当前登录用户查询上传资料列表
	 * @param opaPlanTask
	 * @return
	 */
	List<OpaPlanTask> findTaskList(OpaPlanTask opaPlanTask);
	
	
	/**
	 * 根据当前登录用户查询所有指标
	 * @param param
	 * @return
	 */
	List<OpaPlanTask> allTaskList(Map<String, Object> param);
	
	
	/**
	 * 指标考核资料上传
	 * 根据当前登录单位查询所有指标
	 * @param param
	 * @return
	 */
	
	List<Status> zhibiaoName(Map<String,Object>param);
	
	
	/**
	 * 通过部门和方案id来查出所有状态值
	 * @param auditorId
	 * @param planId
	 * @return
	 */
	List<OpaPlanTask>getDepartStatus(@Param("auditorId")String auditorId,@Param("status")String status,
			@Param("planId")String planId,@Param("delFlag")String delFlag);
	
	
	/**
	 * 修改部门的指标状态
	 * @param planId
	 * @param auditorId
	 * @return
	 */
	@Update({"UPDATE opa_plan_task SET STATUS='6' WHERE PLAN_ID=#{planId} AND AUDITOR_ID=#{auditorId}"})
    void updateDepartStatus(@Param("planId")String planId,@Param("auditorId")String auditorId);
	
	
	/**
	 * 全部打分 修改其他状态值
	 * @param planId
	 */
	@Select({"UPDATE opa_plan_task SET STATUS='9' WHERE PLAN_ID=#{planId}"})
	void updateAllScoreStatus(@Param("planId")String planId);
	
	
	/**
	 * 领导办公室评分审核  全部审核通过
	 * @param planId
	 */
	@Update({"UPDATE opa_plan_task SET STATUS='' WHERE PLAN_ID=#{planId}"})
	void updateAllAssignPass(@Param("planId")String planId);
	
	
	/**
	 * 查出所有
	 * @param planId
	 * @return
	 */
    @Select({"SELECT * FROM opa_plan_task WHERE plan_id=#{planId} GROUP BY name"})
    List<OpaPlanTask> assignPassFindList(@Param("planId")String planId);
    
    
    /**
     * 查出单位
     * @return 
     */
    List<OpaPlanTask> getDepartName(@Param("planId") String planId);
    
    
    
    
    /**
     * 插入需显示汇总表单位数据
     * @param opaPlanTask
     * @return
     */
    public int insertSummaryOffice(OpaPlanTask opaPlanTask);
    
    
    
    /**
     * 查出重复数据中的最长值
     * @param opaPlanTask
     * @return
     */
    
    @Select({"SELECT * FROM opa_plan_task where name=#{name} order by length(ITEM_ID) desc LIMIT 1"})
    List<OpaPlanTask> getTasklength(@Param("name")String name);
    
    
    /**
     * 更改表头的id值
     * @param planId
     * @return
     */
    @Update({"UPDATE opa_plan_task SET item_id=#{itemId},item_parent_id = #{itemParentId},item_parent_ids = #{itemParentIds} WHERE plan_id=#{planId} AND name=#{name}"})
    void updateItem(@Param("itemId")String itemId,@Param("itemParentId")String itemParentId,@Param("itemParentIds")String itemParentIds,
    		@Param("planId")String planId,@Param("name")String name);

	
    /**
     * 通过名称查询重复数据中itemId最小的记录
     * @param name
     * @return
     */
    /*@Select({"SELECT * FROM opa_plan_task WHERE name=#{name} AND plan_id=#{planId} order by length(ITEM_ID) desc LIMIT 1"})*/
      OpaPlanTask getFindList(@Param("name")String name,@Param("planId")String planId);
   
    List<OpaPlanTask> taskFindList1(@Param("delFlag")String delFlag,@Param("auditorId")String auditorId);
    
    /**
     * 汇总反馈 查询部门分数
     * @param planId
     * @param auditorId
     * @return
     */
    @Select({"SELECT ITEM_ID,SCORE FROM opa_plan_task where DEL_FLAG='0' AND AUDITOR_ID=#{auditorId}"})
    List<OpaPlanTask> auditorScore(@Param("auditorId")String auditorId);
    
    

}