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
import com.orhon.pa.modules.opa.entity.OpaMenu;
import com.orhon.pa.modules.opa.service.OpaMenuService;

/**
 * demo表Controller
 * @author yyyyy
 * @version 2018-05-30
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaMenu")
public class OpaMenuController extends BaseController {

	@Autowired
	private OpaMenuService opaMenuService;
	
	@ModelAttribute
	public OpaMenu get(@RequestParam(required=false) String id) {
		OpaMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaMenuService.get(id);
		}
		if (entity == null){
			entity = new OpaMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("opa:opaMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaMenu opaMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaMenu> page = opaMenuService.findPage(new Page<OpaMenu>(request, response), opaMenu); 
		model.addAttribute("page", page);
		return "modules/opa/opaMenuList";
	}

	@RequiresPermissions("opa:opaMenu:view")
	@RequestMapping(value = "form")
	public String form(OpaMenu opaMenu, Model model) {
		model.addAttribute("opaMenu", opaMenu);
		return "modules/opa/opaMenuForm";
	}

	@RequiresPermissions("opa:opaMenu:edit")
	@RequestMapping(value = "save")
	public String save(OpaMenu opaMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaMenu)){
			return form(opaMenu, model);
		}
		opaMenuService.save(opaMenu);
		addMessage(redirectAttributes, "保存demo表成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaMenu/?repage";
	}
	
	@RequiresPermissions("opa:opaMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaMenu opaMenu, RedirectAttributes redirectAttributes) {
		opaMenuService.delete(opaMenu);
		addMessage(redirectAttributes, "删除demo表成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaMenu/?repage";
	}

}