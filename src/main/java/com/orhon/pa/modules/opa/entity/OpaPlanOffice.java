/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.orhon.pa.common.persistence.DataEntity;
import com.orhon.pa.modules.sys.entity.Office;

/**
 * 考核计划部门模块Entity
 * @author Shawn
 * @version 2017-04-26
 */
public class OpaPlanOffice extends DataEntity<OpaPlanOffice> {
	
	private static final long serialVersionUID = 1L;
	private String planId;		// 所属计划
	private Office office;		// 所属部门
	private Office officeParent;		// 父级部门
	private String officeId;
	private String officeParentId;
	private String officeParentName;
	
	
	
	public String getOfficeParentName() {
		return officeParent != null && officeParent.getName() != null ? officeParent.getName() : "";
	}

	public void setOfficeParentName(String officeParentName) {
		this.officeParentName = officeParentName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeParentId() {
		return officeParent != null && officeParent.getId() != null ? officeParent.getId() : "0";
	}

	public void setOfficeParentId(String officeParentId) {
		this.officeParentId = officeParentId;
	}

	public OpaPlanOffice() {
		super();
	}

	public OpaPlanOffice(String id){
		super(id);
	}

	@Length(min=1, max=64, message="所属计划长度必须介于 1 和 64 之间")
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	@NotNull(message="所属部门不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@NotNull(message="父级部门不能为空")
	public Office getOfficeParent() {
		return officeParent;
	}

	public void setOfficeParent(Office officeParent) {
		this.officeParent = officeParent;
	}
	
}