package com.orhon.pa.modules.sys.entity;

import com.orhon.pa.common.persistence.DataEntity;

public class SummaryOfficeRole extends DataEntity<SummaryOfficeRole> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String asscociationOfficeId;    
	private String identificationSymbo;     // 1:各镇  2:旗政府部门 3:事业单位 4：政协部门
	private String delFlag;      //0: 未删   1： 已删
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAsscociationOfficeId() {
		return asscociationOfficeId;
	}
	public void setAsscociationOfficeId(String asscociationOfficeId) {
		this.asscociationOfficeId = asscociationOfficeId;
	}
	public String getIdentificationSymbo() {
		return identificationSymbo;
	}
	public void setIdentificationSymbo(String identificationSymbo) {
		this.identificationSymbo = identificationSymbo;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
