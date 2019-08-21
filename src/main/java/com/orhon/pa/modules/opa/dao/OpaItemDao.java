/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.orhon.pa.common.persistence.TreeDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaItem;

/**
 * 指标管理模块DAO接口
 * @author Shawn
 * @version 2017-04-18
 */
@MyBatisDao
public interface OpaItemDao extends TreeDao<OpaItem> {

	List<OpaItem> findTreeByIdLike(OpaItem itemParent);
	
	List<OpaItem> getHeadLevel();
	
	List<OpaItem> itemFindList (@Param (value="id")String id);
	
	@Select("SELECT id,name,level, parent_id AS \"parent.id\" FROM opa_item WHERE id=#{parentId}")
	List<OpaItem> itemParentId(String parentId);
}
