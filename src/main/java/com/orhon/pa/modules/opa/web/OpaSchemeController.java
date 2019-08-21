/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.service.OpaItemService;
import com.orhon.pa.modules.opa.service.OpaSchemeService;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 考核方案管理模块Controller
 * @author 
 * @version 2017-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaScheme")
public class OpaSchemeController extends BaseController {

	private static final Object page1 = null;

	@Autowired
	private OpaSchemeService opaSchemeService;
	
	@Autowired
	private OpaPlanTaskDao opaPlanTaskDao;
	
	@Autowired
	private OpaItemService opaItemService;
	
	@Autowired
	private OpaSchemeDao opaSchemeDao;
	
	@ModelAttribute
	public OpaScheme get(@RequestParam(required=false) String id) {
		OpaScheme entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaSchemeService.get(id);
		}
		if (entity == null){
			entity = new OpaScheme();
		}
		return entity;
	}
	

	@RequiresPermissions("opa:opaScheme:index")
	@RequestMapping(value= {"index"})
	public String index(OpaScheme opaScheme , HttpServletRequest request,HttpServletResponse response,Model model) {
		User user = UserUtils.getUser();//当前登录用户
		//opaScheme.setCreateBy(user);
		Page<OpaScheme>page = opaSchemeService.findPage(new Page<OpaScheme>(request,response),opaScheme);
		System.out.println("获取设置总数="+page.getCount());
		System.out.println("获取页面总数="+page.getTotalPage());
		model.addAttribute("page",page);
		return"modules/opa/opaSchemeIndex";
	}

	@RequiresPermissions("opa:opaScheme:view")
	@RequestMapping(value= {"list"})
	public String list(OpaScheme opaScheme,HttpServletRequest request,HttpServletResponse response,Model model) {
		Page<OpaScheme> page = opaSchemeService.findPage(new Page<OpaScheme>(request,response),opaScheme);
		model.addAttribute("page", page);
		return "modules/opa/opaSchemeList";
	}

	@RequiresPermissions("opa:opaScheme:view")
	@RequestMapping(value = "form")
	public String form(OpaScheme opaScheme, Model model) {
		model.addAttribute("opaScheme", opaScheme);
		return "modules/opa/opaSchemeForm";
	}
	
	//考核指标查看
	@RequiresPermissions("opa:opaScheme:view")
	@RequestMapping(value = "view")
	public String view(OpaScheme opaScheme, Model model) {
		model.addAttribute("opaScheme", opaScheme);
		return "modules/opa/opaSchemeView";
	}
   
	//方案保存
	@RequiresPermissions("opa:opaScheme:edit")
	@RequestMapping(value = "save")
	public String save(OpaScheme opaScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaScheme)){
			return form(opaScheme, model);
		}
		if (StringUtils.isBlank(opaScheme.getId())){
			opaScheme.setCode(IdGen.uuid());
		}
		OpaItem itemParent = opaItemService.get(opaScheme.getItemParent());
		opaScheme.setLevel(itemParent.getLevel());
		opaScheme.setStatus(DictUtils.getDictValue("制定中", "opa_scheme_status",""));
		if(opaScheme.getOfficeId()=="" |opaScheme.getOfficeId()==null) {
			opaScheme.setOfficeId("");
		}
		opaSchemeService.save(opaScheme);
		addMessage(redirectAttributes, "考核方案保存成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index?repage";
	}
	
	@RequiresPermissions("opa:opaScheme:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaScheme opaScheme, RedirectAttributes redirectAttributes) {
		opaSchemeService.delete(opaScheme);
		addMessage(redirectAttributes, "删除考核方案成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index?repage";
	}
	
	//上报考核指标
	@RequiresPermissions("opa:opaScheme:apply")
	@RequestMapping(value = "apply")
	public String apply(OpaScheme opaScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaScheme)){
			return form(opaScheme, model);
		}
		if (StringUtils.isBlank(opaScheme.getId())){
			opaScheme.setCode(IdGen.uuid());
		}
		OpaItem itemParent = opaItemService.get(opaScheme.getItemParent());
		opaScheme.setLevel(itemParent.getLevel());
		opaScheme.setStatus(DictUtils.getDictValue("待审核", "opa_scheme_status",""));
		opaSchemeService.repoted(opaScheme);
		opaSchemeService.save(opaScheme);
		addMessage(redirectAttributes, "考核指标提交成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index?repage";
		//return "modules/opa/opaSchemeAuditList";
	}
	
	//提交到审核
	@RequiresPermissions("opa:opaScheme:audit:index")
	@RequestMapping(value = {"audit/index"})
	public String auditIndex(OpaScheme opaScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
//	if (!beanValidator(model, opaScheme)){
//			return form(opaScheme, model);
//		}
		if (StringUtils.isBlank(opaScheme.getId())){
			opaScheme.setCode(IdGen.uuid());
		}
		opaScheme.setAuditor(UserUtils.getUser());
		opaScheme.setStatus(DictUtils.getDictValue("待审核", "opa_scheme_status", ""));
		Page<OpaScheme> page = opaSchemeService.findPage(new Page<OpaScheme>(request, response), opaScheme);
		model.addAttribute("page", page);
		//opaSchemeService.save(opaScheme);
		//addMessage(redirectAttributes,"考核方案提交成功");
		return "modules/opa/opaSchemeAuditList";
		//return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index?repage";
	}
	
	//考核方案审核阶段
	@RequiresPermissions("opa:opaScheme:audit:view")
	@RequestMapping(value = "audit/view")
	public String auditView(OpaScheme opaScheme, Model model) {
		model.addAttribute("opaScheme", opaScheme);
		return "modules/opa/opaSchemeAuditView";
	}
	//考核指标审核阶段
	@RequiresPermissions("opa:opaScheme:audit:pass")
	@RequestMapping(value = "audit/pass")
	public String auditPass(OpaScheme opaScheme, Model model, RedirectAttributes redirectAttributes) {
		opaScheme.setStatus(DictUtils.getDictValue("审核中", "opa_scheme_status",""));
		opaSchemeService.auditPass(opaScheme);
		addMessage(redirectAttributes, "考核指标审核成功");
	//	opaSchemeService.save(opaScheme);
		//重定向，跳转到指定的链接
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/audit/index/?repage";
	}
	
	/**
	 * 修改指标方案的状态
	 * @param opaScheme
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaScheme:excute")
	@RequestMapping(value="excute")
	public String wanCheng(OpaScheme opaScheme,Model model,RedirectAttributes redirectAttributes) {
		 opaSchemeService.over(opaScheme);
		/* // itemId字段最长的元素
		OpaPlanTask opaPlanTask=getItemIdLongest(opatask);
		String itemId = opaPlanTask.getItemId();
		String itemParentId=opaPlanTask.getItemParentId();
		String itemParentIds=opaPlanTask.getItemParentIds();
		   for (OpaPlanTask it : opatask) {
	            // 将itemId替换成最长的itemId的值
	            it.setItemId(itemId);
	            it.setItemParentId(itemParentId);
	            it.setItemParentIds(itemParentIds);
	            opaPlanTaskDao.update(it);
	        }*/
		opaScheme.setStatus(DictUtils.getDictValue("已完成", "opa_scheme_status", ""));
		opaSchemeDao.update(opaScheme);
		addMessage(redirectAttributes,"所有指标完成");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index/?repage";
	}
	
	
	/**
     * 获取itemId字段最长的元素
     *
     * @param list
     * @return
     *//*
	@SuppressWarnings("unused")
	private static OpaPlanTask getItemIdLongest(List<OpaPlanTask> list) {
	        List<OpaPlanTask> newList = new ArrayList<>(list);
	        Collections.sort(newList, new Comparator<OpaPlanTask>() {
	            @Override
	            public int compare(OpaPlanTask o1, OpaPlanTask o2) {
	                return o2.getItemId().length() - o1.getItemId().length() > 0 ? 1 : -1;
	            }
	        });
	        return newList.get(0);
	    }*/
	
	
	
	
	//审核退回
    @RequiresPermissions("opa:opaScheme:audit:return")
	@RequestMapping(value = "audit/return")
	public String auditReturn(OpaScheme opaScheme, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) {
		//add lxy start
		String reason = request.getParameter("reason");
		String remark = opaScheme.getRemarks();
		StringBuffer allReason = new StringBuffer();
		if(StringUtils.isNoneEmpty(remark)){
			allReason.append("\n").append(reason);
		}else{
			allReason.append(reason);
		}
		opaScheme.setRemarks(allReason.toString());
		//add lxy end
		opaScheme.setStatus(DictUtils.getDictValue("已退回", "opa_scheme_status",""));
		opaSchemeService.save(opaScheme);
		addMessage(redirectAttributes, "考核方案退回成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/audit/index/?repage";
	}
	// add by lhb start
	@RequiresPermissions("opa:opaSchemeMonitor:index")
	@RequestMapping(value = "monitor/index")
	public String monitorIndex(OpaScheme opaScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaScheme> page = opaSchemeService.findPage(new Page<OpaScheme>(request, response), opaScheme); 
		model.addAttribute("page", page);
		return "modules/opa/opaSchemeMonitorIndex";
	}
	
	@RequiresPermissions("opa:opaSchemeMonitor:edit")
	@RequestMapping(value = "monitor/form")
	public String montiorForm(OpaScheme opaScheme, Model model) {
		model.addAttribute("opaScheme", opaScheme);
		return "modules/opa/opaSchemeMonitorForm";
	}

	
	//组织树
		@RequiresPermissions("user")
		@ResponseBody
		@RequestMapping(value = "treeData")
		public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
			List<Map<String, Object>> mapList = Lists.newArrayList();
			List<OpaScheme> list = opaSchemeService.findList(new OpaScheme());
			for (int i=0; i<list.size(); i++){
				OpaScheme e = list.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", "");
					map.put("name", e.getName());
					mapList.add(map);
				}
			}
			return mapList;
		}
}