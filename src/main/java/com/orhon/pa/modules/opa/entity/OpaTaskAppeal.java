/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;

import org.hibernate.validator.constraints.Length;
import com.orhon.pa.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;

import com.orhon.pa.common.persistence.DataEntity;

/**
 * 绩效反馈模块Entity
 * @author Shawn
 * @version 2017-06-01
 */
public class OpaTaskAppeal extends DataEntity<OpaTaskAppeal> {
	
	private static final long serialVersionUID = 1L;
	private String planId;		// 计划编号
	private Office office;		// 部门编号
	private String taskId;		// 任务编号
	private String code;		// 编码
	private String name;		// 名称
	private String type;		// 指标类型
	private String method;		// 考核方式
	private String ifNum;		// 是否数值类型
	private Double value;		// 考评标准
	private Double score;		// 得分
	private Double count;		// 数值标准
	private Double result;		// 完成数目
	private String attachedPath;		// attached_path
	private String appealScore;		// 申诉分值
	private String appealResult;		// 申诉数值
	private String appealReason;		// 申诉原因
	private String returnReason;		// 退回原因
	private String appealPath;		// 申诉材料
	private String auditorName;
	private String auditorId;		// 审核者
	private String status;		// 状态
	private String auditorOfficeId;		// 监督部门
	private String auditorOfficeName;
	
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
	
	public OpaTaskAppeal() {
		super();
	}

	public OpaTaskAppeal(String id){
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
	
	@Length(min=1, max=64, message="任务编号长度必须介于 1 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	
	@Length(min=0, max=2000, message="attached_path长度必须介于 0 和 2000 之间")
	public String getAttachedPath() {
		return attachedPath;
	}

	public void setAttachedPath(String attachedPath) {
		this.attachedPath = attachedPath;
	}
	
	public String getAppealScore() {
		return appealScore;
	}

	public void setAppealScore(String appealScore) {
		this.appealScore = appealScore;
	}
	
	public String getAppealResult() {
		return appealResult;
	}

	public void setAppealResult(String appealResult) {
		this.appealResult = appealResult;
	}
	
	@Length(min=0, max=255, message="申诉原因长度必须介于 0 和 255 之间")
	public String getAppealReason() {
		return appealReason;
	}

	public void setAppealReason(String appealReason) {
		this.appealReason = appealReason;
	}
	
	@Length(min=0, max=255, message="退回原因长度必须介于 0 和 255 之间")
	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	
	@Length(min=0, max=2000, message="申诉材料长度必须介于 0 和 2000 之间")
	public String getAppealPath() {
		return appealPath;
	}

	public void setAppealPath(String appealPath) {
		this.appealPath = appealPath;
	}
	
	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	@Length(min=1, max=64, message="审核者长度必须介于 1 和 64 之间")
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