/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orhon.pa.common.persistence.DataEntity;
import com.orhon.pa.modules.sys.entity.Office;

/**
 * 计划任务模块Entity
 * @author Shawn
 * @version 2017-04-26
 */
public class OpaPlanTask extends DataEntity<OpaPlanTask> {
	
	private static final long serialVersionUID = 1L;
	private String planId;		// 计划编号
	private Office office;		// 部门编号
	private String itemId;		// 指标编号
	private String itemParentId;		// 父级指标编号
	private String itemParentIds;		// 指标所有父级编号
	private String code;		// 编码
	private String name;		// 名称
	private String type;		// 指标类型
	private Integer level;		// 深度
	private String content;		// 指标内容描述
	private String attachedPath;// 附件
	private String method;		// 考核方式
	private String ifNum;		// 是否数值类型
	private Double value;		// 考评标准
	private Double score;		// 得分
	private Double count;		// 数值标准
	private Double result;		// 完成数目
	private Integer sort;		// 排序
	private Date dateFrom;		// 起始日期
	private Date dateTo;		// 截止日期
	private String auditorId;		// 审核者
	private String status;		// 状态
	private String auditorName;
	private String itemParentName;		// 父级指标编号
	private boolean hasCommonChild;		// 是否有公共子任务，用于判断是否可添加子任务
	private String auditorOfficeId;		// 监督部门
	private String auditorOfficeName;
	private String officeId;
	private String examination;  //考核内容
	private String tableValue;    //huizong lei xing 
	
	
	
	
	public String getExamination() {
		return examination;
	}


	private String maxsort;        //最大的深度
	
	public String getMaxSort() {
		return maxsort;
	}
	
	public String getAuditorOfficeId() {
		return auditorOfficeId;
	}

	public void setAuditorOfficeId(String auditorOfficeId) {
		this.auditorOfficeId = auditorOfficeId;
	}

	public String getAuditorOfficeName() {
		return auditorOfficeName;
	}

	public void setAuditorOfficeName(String auditorOfficeName) {
		this.auditorOfficeName = auditorOfficeName;
	}
	
	
	public boolean isHasCommonChild() {
		return hasCommonChild;
	}

	public void setHasCommonChild(boolean hasCommonChild) {
		this.hasCommonChild = hasCommonChild;
	}

	public String getItemParentName() {
		return itemParentName;
	}

	public void setItemParentName(String itemParentName) {
		this.itemParentName = itemParentName;
	}

	public String getAttachedPath() {
		return attachedPath;
	}

	public void setAttachedPath(String attachedPath) {
		this.attachedPath = attachedPath;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public OpaPlanTask() {
		super();
	}

	public OpaPlanTask(String id){
		super(id);
	}

	@Length(min=1, max=64, message="计划编号长度必须介于 1 和 64 之间")
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	@NotNull(message="部门编号不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=1, max=64, message="指标编号长度必须介于 1 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=1, max=64, message="父级指标编号长度必须介于 1 和 64 之间")
	public String getItemParentId() {
		return itemParentId;
	}

	public void setItemParentId(String itemParentId) {
		this.itemParentId = itemParentId;
	}
	
	@Length(min=1, max=2000, message="指标所有父级编号长度必须介于 1 和 2000 之间")
	public String getItemParentIds() {
		return itemParentIds;
	}

	public void setItemParentIds(String itemParentIds) {
		this.itemParentIds = itemParentIds;
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
	
	@Length(min=1, max=3, message="指标类型长度必须介于 1 和 3 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
//	@Length(min=1, max=10, message="深度长度必须介于 1 和 10 之间")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Length(min=0, max=2000, message="指标内容描述长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=1, max=64, message="考核方式长度必须介于 1 和 64 之间")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@Length(min=0, max=1, message="是否数值类型长度必须介于 0 和 1 之间")
	public String getIfNum() {
		return ifNum;
	}

	public void setIfNum(String ifNum) {
		this.ifNum = ifNum;
	}
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}
	
	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
	
	@NotNull(message="审核者不能为空")
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

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	
	@Override
	public String toString() {
		 return "OpaPlanTask{" +
	                "id=" + id +
	                ", itemId='" + itemId + '\'' +
	                ", name='" + name + '\'' +
	                '}';
	    }

	public String getTableValue() {
		return tableValue;
	}

	
}