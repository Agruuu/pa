package com.orhon.pa.modules.opa.dao;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaAssociation;



/**
 * 关联表接口
 * @author Administrator
 *
 */
@MyBatisDao
public interface OpaAssociationDao extends CrudDao<OpaAssociation> {

	
}
