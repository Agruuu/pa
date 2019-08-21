/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orhon.pa.common.persistence.DataEntity;
import com.orhon.pa.modules.sys.entity.User;

/**
 * 考核方案管理模块Entity
 * 
 * @author Shawn
 * @version 2017-04-18
 */
public class OpaScheme extends DataEntity<OpaScheme> {

	private static final long serialVersionUID = 1L;
	private OpaItem itemParent; // 父级指标
	private String code; // 编码
	private String name; // 名称
	private Integer level; // 深度
	private Date dateFrom; // 起始日期
	private Date dateTo; // 截止日期
	private User auditor; // 
	private String status; // 状态	
	private String examination;//考核内容
	private String officeId;  //部门
	
	@javax.persistence.Transient
	private List<OpaItem> OpaItem;
	
	public List<OpaItem> getOpaItem() {
		return OpaItem;
	}

	public void setOpaItem(List<OpaItem> opaItem) {
		OpaItem = opaItem;
	}

	@JsonIgnore
	@JsonBackReference
	@NotNull(message="父级指标不能为空")
	public OpaItem getItemParent() {
		return itemParent;
	}

	public void setItemParent(OpaItem itemParent) {
		this.itemParent = itemParent;
	}

	public OpaScheme() {
		super();
	}

	public OpaScheme(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "编码长度必须介于 1 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min = 1, max = 200, message = "名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Length(min = 1, max = 10, message = "深度长度必须介于 1 和 10 之间")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "起始日期不能为空")
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "截止日期不能为空")
	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
//	@JsonIgnore
//	@NotNull(message="审核者不能为空")
	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}
	@Transient
	public String getExamination() {
		return examination;
	}
	public void setExamination(String examination) {
		this.examination = examination;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
    
	
}