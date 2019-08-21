/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.entity.OpaPlan;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.opa.service.OpaPlanService;
import com.orhon.pa.modules.opa.service.OpaSchemeService;
import com.orhon.pa.modules.sys.utils.DictUtils;

/**
 * 考核计划模块Controller
 * @author Shawn
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaPlan")
public class OpaPlanController extends BaseController {

	@Autowired
	private OpaPlanService opaPlanService;
	
	@Autowired
	private OpaSchemeService opaSchemeService;
	
	@ModelAttribute
	public OpaPlan get(@RequestParam(required=false) String id) {
		OpaPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaPlanService.get(id);
		}
		if (entity == null){
			entity = new OpaPlan();
		}
		return entity;
	}
	
	@RequiresPermissions("opa:opaPlan:index")
	@RequestMapping(value = {"index"})
	public String index(OpaPlan opaPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaPlan> page = opaPlanService.findPage(new Page<OpaPlan>(request, response), opaPlan); 
		model.addAttribute("page", page);
		return "modules/opa/opaPlanList";
	}

	
	@RequiresPermissions("opa:opaPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaPlan opaPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaPlan> page = opaPlanService.findPage(new Page<OpaPlan>(request, response), opaPlan); 
		model.addAttribute("page", page);
		return "modules/opa/opaPlanList";
	}
 
	/**
	 * 计划添加
	 * @param opaPlan
	 * @param model
	 * @return
	 */
	@RequiresPermissions("opa:opaPlan:view")
	@RequestMapping(value = "form")
	public String form(OpaPlan opaPlan, Model model) {
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		model.addAttribute("opaPlan", opaPlan);
		return "modules/opa/opaPlanForm";
	}
    
	/**
	 * 计划保存
	 * @param opaPlan
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaPlan:edit")
	@RequestMapping(value = "save")
	public String save(OpaPlan opaPlan, Model model, RedirectAttributes redirectAttributes) {
		OpaScheme opaScheme = new OpaScheme();
		opaScheme.setId(opaPlan.getSchemeId());
		opaScheme = opaSchemeService.get(opaScheme);
		opaPlan.setLevel(opaScheme.getLevel());
		if (StringUtils.isBlank(opaPlan.getId())){
			opaPlan.setCode(IdGen.uuid());
		}
		if (!beanValidator(model, opaPlan)){
			return form(opaPlan, model);
		}
		opaPlan.setStatus(DictUtils.getDictValue("制定中", "opa_plan_status",""));
		opaPlanService.save(opaPlan);
		addMessage(redirectAttributes, "保存考核计划成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaPlan/index?repage";
	}

	@RequiresPermissions("opa:opaPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaPlan opaPlan, RedirectAttributes redirectAttributes) {
		opaPlanService.delete(opaPlan);
		addMessage(redirectAttributes, "删除考核计划成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaPlan/?repage";
	}
	
	/**
	 * 考核计划下发
	 * @param opaPlan
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaPlan:apply")
	@RequestMapping(value = "apply")
	public String apply(OpaPlan opaPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaPlan)){
			return form(opaPlan, model);
		}
		opaPlanService.apply(opaPlan);
		addMessage(redirectAttributes, "考核计划下发成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaPlan/index?repage";
	}
   
	
	/**
	 * 待执行完进行开始执行
	 * @param opaPlan
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaPlan:excute")
	@RequestMapping(value = "excute")
	public String excute(OpaPlan opaPlan, Model model, RedirectAttributes redirectAttributes) {
		opaPlan.setStatus(DictUtils.getDictValue("待执行", "opa_plan_status", ""));
		opaPlanService.excute(opaPlan);
		addMessage(redirectAttributes, "考核计划执行成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaPlan/index?repage";
	}
	
	@RequiresPermissions("opa:opaPlan:summary")
	@RequestMapping(value = "summary")
	public String summary(OpaPlan opaPlan, Model model, RedirectAttributes redirectAttributes) {
		opaPlanService.summary(opaPlan);
		addMessage(redirectAttributes, "考核计划已完成");
		return "redirect:"+Global.getAdminPath()+"/opa/opaPlan/index?repage";
	}
	
	// add by lhb start
	@RequiresPermissions("opa:opaPlan:monitor:index")
	@RequestMapping(value = "monitor/index")
	public String monitorIndex(OpaPlan opaPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaPlan> page = opaPlanService.findPage(new Page<OpaPlan>(request, response), opaPlan); 
		model.addAttribute("page", page);
		return "modules/opa/opaPlanMonitorIndex";
	}
	// add by lhb end
}