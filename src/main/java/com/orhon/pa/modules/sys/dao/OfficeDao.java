/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.sys.dao;

import java.util.List;

import com.orhon.pa.common.persistence.TreeDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	List<Office> findTreeByIdLike(Office officeParent);
	
	//多个满足del_flag=0,1,2 满足一个条件查询部门
	List<Office> findOfficeAllList(Office office);
	
}
