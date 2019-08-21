/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhon.pa.common.persistence.TreeEntity;

/**
 * 指标管理模块Entity
 * @author Shawn
 * @version 2017-04-18
 */
public class OpaItem extends TreeEntity<OpaItem> {
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private Integer level;		// 级别
	private String code;		// 编码
	private Integer sort;		// 排序
	private String name;		// 名称
	private Date dateFrom;		// 起始日期
	private Date dateTo;		// 截止日期
	
	public OpaItem() {
		super();
	}

	public OpaItem(String id){
		super(id);
	}

	@Length(min=1, max=3, message="类型长度必须介于 1 和 3 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
//	@NotNull(message="级别不能为空")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
//	@Length(min=1, max=64, message="编码长度必须介于 1 和 64 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=1, max=200, message="名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public OpaItem getParent() {
		return parent;
	}

	public void setParent(OpaItem parent) {
		this.parent = parent;
	}
	
//	@Length(min=1, max=2000, message="所有父级编号长度必须介于 1 和 2000 之间")
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="起始日期不能为空")
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="截止日期不能为空")
	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}