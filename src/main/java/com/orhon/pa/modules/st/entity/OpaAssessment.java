/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.st.entity;

import org.hibernate.validator.constraints.Length;

import com.orhon.pa.common.persistence.DataEntity;

/**
 * 试探列表Entity
 * @author aaaaaa
 * @version 2018-05-23
 */
public class OpaAssessment extends DataEntity<OpaAssessment> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名字
	private String departmentId;		// department_id
	private String democraticScore;		// 得分
	private String socialScore;		// social_score
	private String germanInverseScore;		// german_inverse_score
	private String conversationScore;		// conversation_score
	private String recommendedScore;		// recommended_score
	private String ordinaryTimeSocre;		// ordinary_time_socre
	private String remark;		// remark
	
	public OpaAssessment() {
		super();
	}

	public OpaAssessment(String id){
		super(id);
	}

	@Length(min=1, max=50, message="名字长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=20, message="department_id长度必须介于 1 和 20 之间")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getDemocraticScore() {
		return democraticScore;
	}

	public void setDemocraticScore(String democraticScore) {
		this.democraticScore = democraticScore;
	}
	
	public String getSocialScore() {
		return socialScore;
	}

	public void setSocialScore(String socialScore) {
		this.socialScore = socialScore;
	}
	
	public String getGermanInverseScore() {
		return germanInverseScore;
	}

	public void setGermanInverseScore(String germanInverseScore) {
		this.germanInverseScore = germanInverseScore;
	}
	
	public String getConversationScore() {
		return conversationScore;
	}

	public void setConversationScore(String conversationScore) {
		this.conversationScore = conversationScore;
	}
	
	public String getRecommendedScore() {
		return recommendedScore;
	}

	public void setRecommendedScore(String recommendedScore) {
		this.recommendedScore = recommendedScore;
	}
	
	public String getOrdinaryTimeSocre() {
		return ordinaryTimeSocre;
	}

	public void setOrdinaryTimeSocre(String ordinaryTimeSocre) {
		this.ordinaryTimeSocre = ordinaryTimeSocre;
	}
	
	@Length(min=1, max=255, message="remark长度必须介于 1 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}