/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

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
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.service.OpaItemService;

/**
 * 指标管理模块Controller
 * @author Shawn
 * @version 2017-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaItem")
public class OpaItemController extends BaseController {

	@Autowired
	private OpaItemService opaItemService;
	
	@ModelAttribute
	public OpaItem get(@RequestParam(required=false) String id) {
		OpaItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaItemService.get(id);
		}
		if (entity == null){
			entity = new OpaItem();
		}
		return entity;
	}
	
    	
	@RequiresPermissions("opa:opaItem:view")
	@RequestMapping(value = {"index"})
	public String index(OpaItem opaItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OpaItem> list = opaItemService.findList(opaItem); 
		model.addAttribute("list", list);
		return "modules/opa/opaItemIndex";
	}
	
	@RequiresPermissions("opa:opaItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaItem opaItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OpaItem> list = opaItemService.findList(opaItem); 
		model.addAttribute("list", list);
		return "modules/opa/opaItemList";
	}

	@RequiresPermissions("opa:opaItem:view")
	@RequestMapping(value = "form")
	public String form(OpaItem opaItem, Model model) {
		if (opaItem.getParent()!=null && StringUtils.isNotBlank(opaItem.getParent().getId())){
			opaItem.setParent(opaItemService.get(opaItem.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(opaItem.getId())){
				OpaItem opaItemChild = new OpaItem();
				opaItemChild.setParent(new OpaItem(opaItem.getParent().getId()));
				List<OpaItem> list = opaItemService.findList(opaItem); 
				if (list.size() > 0){
					opaItem.setSort(list.get(list.size()-1).getSort());
					if (opaItem.getSort() != null){
						opaItem.setSort(opaItem.getSort() + 30);
					}
				}
			}
		}
		if (opaItem.getSort() == null){
			opaItem.setSort(30);
		}
		
		model.addAttribute("opaItem", opaItem);
		return "modules/opa/opaItemForm";
	}

    @RequiresPermissions("opa:opaItem:edit")
	@RequestMapping(value = "save")
	public String save(OpaItem opaItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaItem)){
			return form(opaItem, model);
		}
		
		if (StringUtils.isBlank(opaItem.getId())){
			Integer level = 0;
			opaItem.setCode(IdGen.uuid());
			if (opaItem.getParent()!=null && StringUtils.isNotBlank(opaItem.getParent().getId())){
				opaItem.setParent(opaItemService.get(opaItem.getParent().getId()));
				level = opaItem.getParent().getLevel();
			}
			opaItem.setLevel(level);
		}
		opaItemService.save(opaItem);
		addMessage(redirectAttributes, "保存指标成功");
 		return "redirect:"+Global.getAdminPath()+"/opa/opaItem/?repage";
	}
    
	@RequiresPermissions("opa:opaItem:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaItem opaItem, RedirectAttributes redirectAttributes) {
		opaItemService.delete(opaItem);
		addMessage(redirectAttributes, "删除指标成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaItem/?repage";
	}
   
    
	//组织树
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<OpaItem> list = opaItemService.findList(new OpaItem());
		for (int i=0; i<list.size(); i++){
			OpaItem e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}