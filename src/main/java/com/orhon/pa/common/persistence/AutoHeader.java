/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.common.persistence;

/**
 * AutoHeader表头实体
 * 
 * @author Shawn
 * @version 2017-04-26
 */
public class AutoHeader {

	private static final long serialVersionUID = 1L;
	private String checked;
	private String cssstyle;//表头自定义css样式
	private String headDesc;//表头描述
	private String id;
	private String isleaf;//该节点是否为叶子节点
	private String isopen;
	private String level;
	private String name;//表头节点名称
	private String pid;
	private String reportColumnName;//对应数据json的属性名
	private String reportColumnType;
	private String sts;
	private String transanalyzz;
	private String treeCode;//表头树形编码
	private String type;

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getCssstyle() {
		return cssstyle;
	}

	public void setCssstyle(String cssstyle) {
		this.cssstyle = cssstyle;
	}

	public String getHeadDesc() {
		return headDesc;
	}

	public void setHeadDesc(String headDesc) {
		this.headDesc = headDesc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public String getIsopen() {
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getReportColumnName() {
		return reportColumnName;
	}

	public void setReportColumnName(String reportColumnName) {
		this.reportColumnName = reportColumnName;
	}

	public String getReportColumnType() {
		return reportColumnType;
	}

	public void setReportColumnType(String reportColumnType) {
		this.reportColumnType = reportColumnType;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getTransanalyzz() {
		return transanalyzz;
	}

	public void setTransanalyzz(String transanalyzz) {
		this.transanalyzz = transanalyzz;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}