/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;


import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.BaseEntity;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.common.utils.DateUtils;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.utils.excel.ExportExcel;
import com.orhon.pa.modules.opa.dao.OpaLeadershipAssessmentDao;
import com.orhon.pa.modules.opa.entity.OpaLeadershipAssessment;
import com.orhon.pa.modules.opa.service.OpaLeadershipAssessmentService;
import com.orhon.pa.modules.sys.entity.Office;
import com.orhon.pa.modules.sys.entity.Role;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 领导干部考核情况Controller
 * @author pp
 * @version 2018-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaLeadershipAssessment")
public class OpaLeadershipAssessmentController extends BaseController {

	@Autowired
	private OpaLeadershipAssessmentService opaLeadershipAssessmentService;
	
	
	@ModelAttribute
	public OpaLeadershipAssessment get(@RequestParam(required=false) String id) {
		OpaLeadershipAssessment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaLeadershipAssessmentService.get(id);
		}
		if (entity == null){
			entity = new OpaLeadershipAssessment();
		}
		return entity;
	}
	
	@RequiresPermissions("opa:opaLeadershipAssessment:index")
	@RequestMapping(value = {"index"})
	public String index(OpaLeadershipAssessment OpaLeadershipAssessment, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OpaLeadershipAssessment> list = opaLeadershipAssessmentService.findList(OpaLeadershipAssessment); 
		model.addAttribute("list", list);
		return "modules/opa/opaLeadershipAssessmentIndex";
	}
	
	@RequiresPermissions("opa:opaLeadershipAssessment:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaLeadershipAssessment opaLeadershipAssessment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaLeadershipAssessment> page = opaLeadershipAssessmentService.findPage(new Page<OpaLeadershipAssessment>(request, response), opaLeadershipAssessment); 
		model.addAttribute("page", page);
		return "modules/opa/opaLeadershipAssessmentList";
	}
	
	@RequiresPermissions("opa:opaLeadershipAssessment:view")
	@RequestMapping(value = "form")
	public String form(OpaLeadershipAssessment opaLeadershipAssessment, Model model) {
		if(opaLeadershipAssessment.getOffice()==null || opaLeadershipAssessment.getOffice().getId()==null) {
			opaLeadershipAssessment.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("allRoles", opaLeadershipAssessmentService.findAllRole());
		model.addAttribute("opaLeadershipAssessment", opaLeadershipAssessment);
		return "modules/opa/opaLeadershipAssessmentForm";
	}
    
	@RequiresPermissions("opa:opaLeadershipAssessment:view")
	@RequestMapping(value = "view")
	public String view(OpaLeadershipAssessment opaLeadershipAssessment, Model model) {
		model.addAttribute("opaLeadershipAssessment", opaLeadershipAssessment);
		return "modules/opa/opaLeadershipAssessmentView";
	}
	
	@RequiresPermissions("opa:opaLeadershipAssessment:edit")
	@RequestMapping(value = "save")
	public String save(OpaLeadershipAssessment opaLeadershipAssessment,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		opaLeadershipAssessment.setCompany(new Office(request.getParameter("company.id")));
		opaLeadershipAssessment.setOffice(new Office(request.getParameter("office.id")));
		opaLeadershipAssessment.setUser(new User(request.getParameter("primaryPerson.id")));
		if (!beanValidator(model, opaLeadershipAssessment)){
		     return form(opaLeadershipAssessment, model);
	    }
		//角色数据有效验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = opaLeadershipAssessment.getRoleIdList();
		for(Role r : opaLeadershipAssessmentService.findAllRole()) {
			if(roleIdList.contains(r.getId())) {
				roleList.add(r);
			}
		}
		
		BigDecimal b = new BigDecimal(opaLeadershipAssessment.getDemocraticAssessment());
		Double t = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String s = t.toString();
		opaLeadershipAssessment.setDemocraticAssessment(s);
		BigDecimal b1 = new BigDecimal(opaLeadershipAssessment.getSocialAssessment());
		Double t1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String s1=t1.toString();
		opaLeadershipAssessment.setSocialAssessment(s1);
		BigDecimal b2 = new BigDecimal(opaLeadershipAssessment.getGermanyAssessment());
		Double t2 =b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String s2 = t2.toString();
		opaLeadershipAssessment.setGermanyAssessment(s2);
		BigDecimal q = new BigDecimal(opaLeadershipAssessment.getConversationAssessment());
		Double q1 = q.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String w = q1.toString();
		opaLeadershipAssessment.setConversationAssessment(w);
		BigDecimal e = new BigDecimal(opaLeadershipAssessment.getDepartmentAssessment());
		Double r = e.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String r1 = r.toString();
		opaLeadershipAssessment.setDepartmentAssessment(r1);
		BigDecimal y = new BigDecimal(opaLeadershipAssessment.getNormalAssessment());
		Double y1 = y.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String u=y1.toString();
		opaLeadershipAssessment.setNormalAssessment(u);
		BigDecimal all = new BigDecimal(opaLeadershipAssessment.getNormalAssessment());
		Double score=all.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String allscore=score.toString();
		opaLeadershipAssessment.setAllscore(allscore);
		opaLeadershipAssessment.setRoleList(roleList);
		opaLeadershipAssessment.setStatus(DictUtils.getDictValue("待下发", "opa_opaLeadershipAssessment_status", ""));
		opaLeadershipAssessmentService.save(opaLeadershipAssessment);
		addMessage(redirectAttributes, "保存领导干部考核情况成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaLeadershipAssessment/?repage";
	}
	
	@RequiresPermissions("opa:opaLeadershipAssessment:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaLeadershipAssessment opaLeadershipAssessment, RedirectAttributes redirectAttributes) {
		opaLeadershipAssessmentService.delete(opaLeadershipAssessment);
		addMessage(redirectAttributes, "删除领导干部考核情况成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaLeadershipAssessment/?repage";
	}
	
	//导出考核信息
	@RequiresPermissions("opa:opaLeadershipAssessment:edit")
	@RequestMapping(value="export",method=RequestMethod.POST)
	public String exportFile(OpaLeadershipAssessment opaLeadershipAssessment,HttpServletResponse response,
			HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			String fileName = "干部考核情况统计表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<OpaLeadershipAssessment> page = opaLeadershipAssessmentService.findUser(new Page<OpaLeadershipAssessment>(request,response,-1),opaLeadershipAssessment);
			new ExportExcel("干部考核情况统计表",OpaLeadershipAssessment.class).setDataList(page.getList()).write(response,fileName).dispose();
			return null;
		}catch(Exception e){
			addMessage(redirectAttributes,"导出考核信息失败!失败信息"+e.getMessage());
		}
		return "redirect:"+ adminPath+"/opa/opaLeadershipAssessment/list?repage";
	}

	
   //考核反馈
	@RequiresPermissions("opa:opaLeadershipAssessment:assessment:index")
	@RequestMapping(value= {"assessment/index"})
	public String opaLeadershipList(OpaLeadershipAssessment opaLeadershipAssessment,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isBlank(opaLeadershipAssessment.getId())) {
			opaLeadershipAssessment.setCode(IdGen.uuid());
		}
		User user = UserUtils.getUser();
		opaLeadershipAssessment.setDelFlag("0");
		opaLeadershipAssessment.setUserId(user.getId());
		Page<OpaLeadershipAssessment> page = opaLeadershipAssessmentService.assessementfindPage(new Page<OpaLeadershipAssessment>(request, response), opaLeadershipAssessment); 
		model.addAttribute("page", page);
		return "modules/opa/opaLeadershipAssessmentAssessmentList";
	}
}