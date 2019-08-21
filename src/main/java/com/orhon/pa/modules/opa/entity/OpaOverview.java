/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.orhon.pa.common.persistence.DataEntity;
import com.orhon.pa.modules.sys.entity.Office;

/**
 * 综合评分Entity
 * @author ss
 * @version 2018-06-16
 */
public class OpaOverview extends DataEntity<OpaOverview> {
	
	private static final long serialVersionUID = 1L;
	private String schemeId;		// 指标编号
	private String departId;      //单位
	private String code;		// 编码
	private String yearResult;		// 年底考核成绩
	private String convertScore;		// 折算得分(年考核成绩x50%+日常考核成绩x50%)x80%
	private String leadingScore;		// 领导评价(50分)
	private String countyScore;		// 县级领导评分(30分)
	private String peerReview;		// 单位互评(30分)
	private String leadershipAssessment;		// 领导班子测评(30分)
	private String serviceRating;		// 服务对象评分(40分)
	private String assessmentScore;		// 考核组评分(20分)
	private String overallScore;		// 综合评分
	private Date dateFrom;		// 开始时间
	private Date dateTo;		// 结束时间
	private String daiScore;		// 日常考核成绩
	private Office office;
	private Integer level;     //深度
	private String annualScore;   //全年得分(折算得分+综合评价)/1.2
	
	public OpaOverview() {
		super();
	}

	public OpaOverview(String id){
		super(id);
	}

	@Length(min=1, max=64, message="scheme_id长度必须介于 1 和 64 之间")
	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	
	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	@Length(min=0, max=64, message="code长度必须介于 0 和 64 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getYearResult() {
		return yearResult;
	}

	public void setYearResult(String yearResult) {
		this.yearResult = yearResult;
	}
	
	public String getConvertScore() {
		return convertScore;
	}

	
	public String getLeadingScore() {
		return leadingScore;
	}

	public void setLeadingScore(String leadingScore) {
		this.leadingScore = leadingScore;
	}
	
	public String getCountyScore() {
		return countyScore;
	}

	public void setCountyScore(String countyScore) {
		this.countyScore = countyScore;
	}
	
	public String getPeerReview() {
		return peerReview;
	}

	public void setPeerReview(String peerReview) {
		this.peerReview = peerReview;
	}
	
	public String getLeadershipAssessment() {
		return leadershipAssessment;
	}

	public void setLeadershipAssessment(String leadershipAssessment) {
		this.leadershipAssessment = leadershipAssessment;
	}
	
	public String getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(String serviceRating) {
		this.serviceRating = serviceRating;
	}
	
	public String getAssessmentScore() {
		return assessmentScore;
	}

	public void setAssessmentScore(String assessmentScore) {
		this.assessmentScore = assessmentScore;
	}
	
	public String getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(String overallScore) {
		this.overallScore = overallScore;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public String getDaiScore() {
		return daiScore;
	}

	public void setDaiScore(String daiScore) {
		this.daiScore = daiScore;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getAnnualScore() {
		return annualScore;
	}
}