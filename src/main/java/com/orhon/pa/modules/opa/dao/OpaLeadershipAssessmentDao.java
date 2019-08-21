/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaLeadershipAssessment;
import com.orhon.pa.modules.sys.entity.User;

/**
 * 领导干部考核情况DAO接口
 * @author asyt
 * @version 2018-05-22
 */
@MyBatisDao
public interface OpaLeadershipAssessmentDao extends CrudDao<OpaLeadershipAssessment> {
	
	//根据登录名称查询用户
	public User getByLoginName(OpaLeadershipAssessment opaLeadershipAssessment);
	
	//通过officeId获取用户列表，仅返回用户Id和name（树查询用户时用）
	public List<OpaLeadershipAssessment> findUserByOfficeId(OpaLeadershipAssessment opaLeadershipAssessment);
	
	//查询全部用户数目
	public long findAllCount(OpaLeadershipAssessment opaLeadershipAssessment);
	
	//删除用户角色关联数据
	public int deleteUserRole(OpaLeadershipAssessment opaLeadershipAssessment);
	
	//更新用户信息
	public int updateUserInfo(OpaLeadershipAssessment opaLeadershipAssessment);
	
	//插入用户角色关联数据
	public int insertUserRole(OpaLeadershipAssessment opaLeadershipAssessment);
	
	public int insert1(OpaLeadershipAssessment opaLeadershipAssessment);
	
	public int update1(OpaLeadershipAssessment opaLeadershipAssessment);
	
	public List<OpaLeadershipAssessment>findListFeedback(User userId);
	
	public List<OpaLeadershipAssessment> assessmentList(User id);
	
	
	
}