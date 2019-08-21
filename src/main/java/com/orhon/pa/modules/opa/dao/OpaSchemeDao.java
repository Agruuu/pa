/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.modules.opa.entity.OpaScheme;

/**
 * 考核方案管理模块DAO接口
 * @author Shawn
 * @version 2017-04-18
 */
@MyBatisDao
public interface OpaSchemeDao extends CrudDao<OpaScheme> {

	List<Status> findListByStatus(Map<String, Object> param);
	
	List<Status> findlistByStatus1(Map<String,Object>param);
	
	List<Status> findlistByStatus2(Map<String,Object>param);
	
	/**
	 * 考核指标上传资料
	 * 指标名称根据当前登录单位显示所有指标
	 * @param param
	 * @return
	 */
	List<Status> zhibiaoName(Map<String,Object>param);
	
	List<OpaScheme> findList(OpaScheme opaScheme );
	
	void updateStatus(OpaScheme opaScheme);
	
}