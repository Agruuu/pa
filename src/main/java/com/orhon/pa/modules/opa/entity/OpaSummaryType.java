package com.orhon.pa.modules.opa.entity;

import java.util.Date;

import com.orhon.pa.common.persistence.DataEntity;


public class OpaSummaryType extends DataEntity<OpaSummaryType> {
	
	private static final long serialVersionUID=1L;
	private String id;
	private String summaryId;    //汇总表类型
	private String itemId;      //指标
	private String departId;    //单位
	private Integer level;       // 深度
	private Date createDate;      //创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSummaryId() {
		return summaryId;
	}
	public void setSummaryId(String summaryId) {
		this.summaryId = summaryId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
	
	
	

}
