/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.DataEntity;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.modules.opa.dao.OpaLeadershipAssessmentDao;
import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.entity.OpaLeadershipAssessment;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.UserUtils;



/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	@Autowired
	protected OpaSchemeDao opaSchemeDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}
	
	
	
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}
	
	@SuppressWarnings("unchecked")
	public Page<T> assessementfindPage(Page<T> page,T entity){
		List<OpaLeadershipAssessment> dataList = new ArrayList<OpaLeadershipAssessment>();
		entity.setPage(page);
        User user=entity.getCurrentUser();
        List<OpaLeadershipAssessment>leadeFindList = dao.assessmentList(entity);
        for(OpaLeadershipAssessment t:leadeFindList) {
        	if(t.getUserId().equals(user.getId())) {
        		dataList.add(t);
        	}
        }
		page.setList((List<T>) dataList);
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}
	
	
	//更新数据
	@Transactional(readOnly=false)
	public void update(T entity) {
		dao.update(entity);
	}
}
