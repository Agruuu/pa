package com.orhon.pa.modules.opa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaSummaryType;

@MyBatisDao
public interface OpaSummaryTypeDao extends CrudDao<OpaSummaryType> {
	
	
	
	
	@Insert("INSERT INTO summary_parent_level(id,summary_id,item_id,level,create_date)values(#{id},#{summaryId},#{itemId},#{level},#{createDate})")
	void summaryInsert (OpaSummaryType type);
	
	
	@Select("SELECT id,item_id AS itemId FROM summary_parent_level")
	List<OpaSummaryType> type ();

}
