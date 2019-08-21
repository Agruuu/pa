/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.st.web;

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
import com.orhon.pa.modules.st.entity.OpaAssessment;
import com.orhon.pa.modules.st.service.OpaAssessmentService;

/**
 * 试探列表Controller
 * @author aaaaaa
 * @version 2018-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/st/opaAssessment")
public class OpaAssessmentController extends BaseController {

	@Autowired
	private OpaAssessmentService opaAssessmentService;
	
	@ModelAttribute
	public OpaAssessment get(@RequestParam(required=false) String id) {
		OpaAssessment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaAssessmentService.get(id);
		}
		if (entity == null){
			entity = new OpaAssessment();
		}
		return entity;
	}
	
	@RequiresPermissions("st:opaAssessment:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaAssessment opaAssessment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaAssessment> page = opaAssessmentService.findPage(new Page<OpaAssessment>(request, response), opaAssessment); 
		model.addAttribute("page", page);
		return "modules/st/opaAssessmentList";
	}

	@RequiresPermissions("st:opaAssessment:view")
	@RequestMapping(value = "form")
	public String form(OpaAssessment opaAssessment, Model model) {
		model.addAttribute("opaAssessment", opaAssessment);
		return "modules/st/opaAssessmentForm";
	}

	@RequiresPermissions("st:opaAssessment:edit")
	@RequestMapping(value = "save")
	public String save(OpaAssessment opaAssessment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaAssessment)){
			return form(opaAssessment, model);
		}
		opaAssessmentService.save(opaAssessment);
		addMessage(redirectAttributes, "保存试探列表成功");
		return "redirect:"+Global.getAdminPath()+"/st/opaAssessment/?repage";
	}
	
	@RequiresPermissions("st:opaAssessment:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaAssessment opaAssessment, RedirectAttributes redirectAttributes) {
		opaAssessmentService.delete(opaAssessment);
		addMessage(redirectAttributes, "删除试探列表成功");
		return "redirect:"+Global.getAdminPath()+"/st/opaAssessment/?repage";
	}

}