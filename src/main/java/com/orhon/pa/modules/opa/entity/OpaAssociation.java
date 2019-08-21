package com.orhon.pa.modules.opa.entity;

import java.util.Date;

import com.orhon.pa.common.persistence.DataEntity;

public class OpaAssociation extends DataEntity<OpaAssociation>{
	
	private static final long serialVersionUID = 1L;
	private String id;   
	private String schemeId;       //方案
	private String ItemId;         //指标
	private String officeId;       //部门
	private  Date dateFrom ;      //开始时间
	private Date dateTo;          //截至时间
	private Double value;		// 考评标准
	private Double count;		// 数值标准
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	public String getItemId() {
		return ItemId;
	}
	public void setItemId(String itemId) {
		ItemId = itemId;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getCount() {
		return count;
	}
	public void setCount(Double count) {
		this.count = count;
	}
	
	
	

}
