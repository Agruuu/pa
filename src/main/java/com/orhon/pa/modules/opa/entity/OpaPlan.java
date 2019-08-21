/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.orhon.pa.common.persistence.DataEntity;

/**
 * 考核计划模块Entity
 * @author Shawn
 * @version 2017-04-25
 */
public class OpaPlan extends DataEntity<OpaPlan> {
	
	private static final long serialVersionUID = 1L;
	private String schemeId;		// 方案编号
	private String officeParentId;		// 考核部门父级编号
	private String code;		// 编码
	private String name;		// 名称
	private Integer level;		// 深度
	private String year;		// 考核年份
	private String cycle;		// 考核周期
	private Date dateFrom;		// 起始日期
	private Date dateTo;		// 截止日期
	private String auditorId;		// 审核人
	private String status;		// 状态
	private String auditorName;
	private String officeParentName;
	
	
	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public String getOfficeParentName() {
		return officeParentName;
	}

	public void setOfficeParentName(String officeParentName) {
		this.officeParentName = officeParentName;
	}

	public OpaPlan() {
		super();
	}

	public OpaPlan(String id){
		super(id);
	}

	@Length(min=1, max=64, message="方案编号长度必须介于 1 和 64 之间")
	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	
	@Length(min=1, max=64, message="考核部门父级编号长度必须介于 1 和 64 之间")
	public String getOfficeParentId() {
		return officeParentId;
	}

	public void setOfficeParentId(String officeParentId) {
		this.officeParentId = officeParentId;
	}
	
	@Length(min=1, max=100, message="编码长度必须介于 1 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=200, message="名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="深度不能为空")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Length(min=1, max=4, message="考核年份长度必须介于 1 和 4 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=1, max=64, message="考核周期长度必须介于 1 和 64 之间")
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="起始日期不能为空")
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="截止日期不能为空")
	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	@Length(min=1, max=64, message="审核人长度必须介于 1 和 64 之间")
	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}
	
	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}