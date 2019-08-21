/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.sys.entity.SummaryOfficeRole;
import com.orhon.pa.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	
	
	/**
	 * 
	 *通过部门id获取列表，返回用户id,name,loginName(树查询用户) 
	 */
	public List<User> findUserByOfficeIdList(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	
	/**
	 *  插入关联汇总表
	 * @param t
	 * @return
	 */
	public int summaryInsert(SummaryOfficeRole t);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	
	
	List<User> userfindlist(User user);
	
	@Select("SELECT * FROM sys_user WHERE id='1'")
	public User FindList(User id);
	
	
	
	/**
	 * 通过单位id查询汇总表里是否有记录
	 * 
	 * @param id
	 * @return
	 */
	@Select("select count(*) as zs from summary_office_role a \n" + 
			"left join sys_user r on r.id= a.Association_office_id\n" + 
			"where r.id=#{id} ")
	public int count(String id);
	

}
