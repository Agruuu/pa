/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.entity;


import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.orhon.pa.common.persistence.DataEntity;
import com.orhon.pa.common.utils.Collections3;
import com.orhon.pa.common.utils.excel.annotation.ExcelField;
import com.orhon.pa.modules.sys.entity.Office;
import com.orhon.pa.modules.sys.entity.Role;
import com.orhon.pa.modules.sys.entity.User;

/**
 * 领导干部考核情况Entity
 * @author asyt
 * @version 2018-05-22
 */
public class OpaLeadershipAssessment extends DataEntity<OpaLeadershipAssessment> {
	
	private static final long serialVersionUID = 1L;
	private Office office;		// 部门
	private String code;		// 编码
	private String name;		// 姓名
	private User user;    //
	private String democraticAssessment;		// 民主测评
	private String socialAssessment;		// 生活圈，社交圈测评
	private String germanyAssessment;		// 德反向测评得分
	private String conversationAssessment;		// 谈话民主推荐测评
	private String departmentAssessment;		// 平时考核测评
	private String normalAssessment;		// 平时考核得分
	private String departmentId;		// 部门ID
	private String allscore;      //总分
	private Office company;     //归属部门
	private String itemParentId;     //指标编号
	private Role role;           //根据角色查询用户条件
	private String status;       //信息发送状态
	private String userId;
	
	
	private List<Role> roleList = Lists.newArrayList();  //拥有角色列表
	
	@JsonIgnore
	@NotNull(message="归属机关不能为空")
	public Office getCompany() {
		return company;
	}
	
	public void setCompany(Office company) {
        this.company=company;
	}
	
	@JsonIgnore
	public List<Role>getRoleList(){
		 return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList=roleList;
	}
	
	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}
	
	public OpaLeadershipAssessment(Role role){
		super();
		this.role = role;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public OpaLeadershipAssessment() {
		super();
	}

	public OpaLeadershipAssessment(String id){
		super(id);
	}
	@ExcelField(title="单位",align = 1,sort=20)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min=1, max=64, message="编码长度必须介于 1 和 64 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=10, message="name长度必须介于 1 和 10 之间")
	@ExcelField(title="名字", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(title="民主测评", align=2, sort=30)
	public String getDemocraticAssessment() {
		return democraticAssessment;
	}

	public void setDemocraticAssessment(String democraticAssessment) {
		this.democraticAssessment = democraticAssessment;
	}
	@ExcelField(title="生活圈,社交圈测评", align=2, sort=35)
	public String getSocialAssessment() {
		return socialAssessment;
	}

	public void setSocialAssessment(String socialAssessment) {
		this.socialAssessment = socialAssessment;
	}
	@ExcelField(title="'德'反向测评得分", align=2, sort=40)
	public String getGermanyAssessment() {
		return germanyAssessment;
	}

	public void setGermanyAssessment(String germanyAssessment) {
		this.germanyAssessment = germanyAssessment;
	}
	@ExcelField(title="谈话民主推荐测评", align=2, sort=45)
	public String getConversationAssessment() {
		return conversationAssessment;
	}

	public void setConversationAssessment(String conversationAssessment) {
		this.conversationAssessment = conversationAssessment;
	}
	@ExcelField(title="平时考核测评", align=2, sort=50)
	public String getDepartmentAssessment() {
		return departmentAssessment;
	}
   
	public void setDepartmentAssessment(String departmentAssessment) {
		this.departmentAssessment = departmentAssessment;
	}
	@ExcelField(title="平时考核得分", align=2, sort=55)
	public String getNormalAssessment() {
		return normalAssessment;
	}

	public void setNormalAssessment(String normalAssessment) {
		this.normalAssessment = normalAssessment;
	}
	
	@Length(min=0, max=64, message="部门ID长度必须介于 0 和 64 之间")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ExcelField(title="总分", align=2, sort=60)
	public String getAllscore() {
		return allscore;
	}

	public void setAllscore(String allscore) {
		this.allscore = allscore;
	}

	public String getItemParentId() {
		return itemParentId;
	}

	public void setItemParentId(String itemParentId) {
		this.itemParentId = itemParentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}