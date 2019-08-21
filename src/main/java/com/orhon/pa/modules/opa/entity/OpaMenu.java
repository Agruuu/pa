/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;

import org.hibernate.validator.constraints.Length;

import com.orhon.pa.common.persistence.DataEntity;

/**
 * demo表Entity
 * @author yyyyy
 * @version 2018-05-30
 */
public class OpaMenu extends DataEntity<OpaMenu> {
	
	private static final long serialVersionUID = 1L;
	private String pid;		// 父级Id
	private String name;		// 名称
	
	public OpaMenu() {
		super();
	}

	public OpaMenu(String id){
		super(id);
	}

	@Length(min=1, max=64, message="父级Id长度必须介于 1 和 64 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=12, message="名称长度必须介于 0 和 12 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}