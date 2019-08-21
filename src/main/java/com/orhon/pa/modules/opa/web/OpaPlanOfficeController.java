/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

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
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;
import com.orhon.pa.modules.opa.service.OpaPlanOfficeService;

/**
 * 考核计划部门模块Controller
 * @author Shawn
 * @version 2017-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaPlanOffice")
public class OpaPlanOfficeController extends BaseController {

	@Autowired
	private OpaPlanOfficeService opaPlanOfficeService;
	
	@ModelAttribute
	public OpaPlanOffice get(@RequestParam(required=false) String id) {
		OpaPlanOffice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaPlanOfficeService.get(id);
		}
		if (entity == null){
			entity = new OpaPlanOffice();
		}
		return entity;
	}
	
	@RequiresPermissions("opa:opaPlanOffice:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaPlanOffice opaPlanOffice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaPlanOffice> page = opaPlanOfficeService.findPage(new Page<OpaPlanOffice>(request, response), opaPlanOffice); 
		model.addAttribute("page", page);
		return "modules/opa/opaPlanOfficeList";
	}

	@RequiresPermissions("opa:opaPlanOffice:view")
	@RequestMapping(value = "form")
	public String form(OpaPlanOffice opaPlanOffice, Model model) {
		model.addAttribute("opaPlanOffice", opaPlanOffice);
		return "modules/opa/opaPlanOfficeForm";
	}

	@RequiresPermissions("opa:opaPlanOffice:edit")
	@RequestMapping(value = "save")
	public String save(OpaPlanOffice opaPlanOffice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaPlanOffice)){
			return form(opaPlanOffice, model);
		}
		opaPlanOfficeService.save(opaPlanOffice);
		addMessage(redirectAttributes, "保存考核计划部门成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaPlanOffice/?repage";
	}
	
	@RequiresPermissions("opa:opaPlanOffice:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaPlanOffice opaPlanOffice, RedirectAttributes redirectAttributes) {
		opaPlanOfficeService.delete(opaPlanOffice);
		addMessage(redirectAttributes, "删除考核计划部门成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaPlanOffice/?repage";
	}

}