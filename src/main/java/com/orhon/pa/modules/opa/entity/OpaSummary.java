package com.orhon.pa.modules.opa.entity;

import com.orhon.pa.common.persistence.DataEntity;

/**
 * 汇总表 
 * @author Administrator
 *
 */
public class OpaSummary extends DataEntity<OpaSummary>{
	private static final long serialVersionUID = 1L;
	private String summaryId;   
	private String value; //序号
	private String label;       //汇总名
	
	

	public OpaSummary() {
		
	}
	public OpaSummary(String summaryId,String value,String label) {
		this.summaryId = summaryId;
		this.value = value;
		this.label = label;
	}
	public String getSummaryId() {
		return summaryId;
	}
	public String getValue() {
		return value;
	}
	public String getLabel() {
		return label;
	}
	
	
	
	

}
