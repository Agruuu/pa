/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaOverview;
import com.orhon.pa.modules.sys.entity.Office;

/**
 * 综合评分DAO接口
 * @author ss
 * @version 2018-06-16
 */
@MyBatisDao
public interface OpaOverviewDao extends CrudDao<OpaOverview> {
	
	OpaOverview getView(String officeId);
	
	
	OpaOverview getOverview(String Id);
	
	
	//当指标完成时id添加到手动填写比分
	@Insert("insert into opa_overview(id,scheme_id) values(#{Id},#{schemeId})")
	void insertSchemeId(String id);
	
	void OverInsert(OpaOverview opaOverview);
	
	void Overupdate(String id);
	
	//@Delete("DELETE　FROM opa_overview WHERE del_flag='3'")
	void deleteDelFlag(OpaOverview opaOverview);
	
	
}