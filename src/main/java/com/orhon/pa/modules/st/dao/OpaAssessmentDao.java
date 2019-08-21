/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.st.dao;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.st.entity.OpaAssessment;

/**
 * 试探列表DAO接口
 * @author aaaaaa
 * @version 2018-05-23
 */
@MyBatisDao
public interface OpaAssessmentDao extends CrudDao<OpaAssessment> {
	
}