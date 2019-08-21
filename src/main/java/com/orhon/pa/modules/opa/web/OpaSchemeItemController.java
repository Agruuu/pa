/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.opa.service.OpaSchemeItemService;
import com.orhon.pa.modules.opa.service.OpaSchemeService;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 方案指标模块Controller
 * @author Shawn
 * @version 2017-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaSchemeItem")
public class OpaSchemeItemController extends BaseController {

	@Autowired
	private OpaSchemeItemService opaSchemeItemService;
	@Autowired
	private OpaSchemeService opaSchemeService;
	@Autowired
	private OpaSchemeDao opaSchemeDao;
	@Autowired
	private OpaSchemeItemDao opaSchemeItemDao;
	
	@ModelAttribute
	public OpaSchemeItem get(@RequestParam(required=false) String id) {
		OpaSchemeItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaSchemeItemService.get(id);
		}
		if (entity == null){
			entity = new OpaSchemeItem();
		}
		return entity;
	}
	
	/**
	 * 指标分配模块  查询列表
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:index")
	@RequestMapping(value = {"assign/index"})
	public String assignIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaSchemeItem>page = opaSchemeItemService.findPage(new Page<OpaSchemeItem>(request,response),opaSchemeItem);
		model.addAttribute("page", page);
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标分配中", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
		model.addAttribute("list", list);
	}
		return "modules/opa/opaSchemeItemAssignIndex819";
	}
	@RequiresPermissions("opa:opaSchemeItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaSchemeItem> page = opaSchemeItemService.findPage(new Page<OpaSchemeItem>(request, response), opaSchemeItem); 
		model.addAttribute("page", page);
		return "modules/opa/opaSchemeItemList";
	}

	/**
	 * 方案指标明细表
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:view")
	@RequestMapping(value = "assign/view")
	public String assignView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
		}
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemAssignView";
	}

	/**
	 * 指标分配 -- 打开指标分配对话框
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:edit")
	@RequestMapping(value = "assign/edit")
	public String assignEdit(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		String schemeId = request.getParameter("schemeId");
		opaSchemeItem.setSchemeId(schemeId);
		model.addAttribute("schemeId", schemeId);
		return "modules/opa/opaSchemeItemAssignEdit";
	}
	
	/**
	 * 方案指标分配完成
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:edit")
	@ResponseBody
	@RequestMapping(value = "assign/adds")
	public String addAssigns(HttpServletRequest request, HttpServletResponse response) {
		String msg = "ok";
		String ids = request.getParameter("ids");
		String auditorId = request.getParameter("auditorId");
		String auditorOfficeId = request.getParameter("officeId");
		if(auditorOfficeId.contains(",")) {//是否有逗号
//			String[] str = auditorOfficeId.split(",");
//			String str = auditorOfficeId.substring(0, auditorOfficeId.indexOf(","));//截取逗号之前的字符
//			if(!auditorOfficeId.equals(str)) {
			String schemeItemId[] = ids.split(",");
			List<OpaSchemeItem> lists = new ArrayList<>();
			for (int i = 0; i < schemeItemId.length; i++) {
				OpaSchemeItem entity=opaSchemeItemService.get(schemeItemId[i]);
				opaSchemeItemService.itemInsert(entity);//多少个部门 插入多少数据
//				lists.add(entity);
			}
				String[] a=auditorOfficeId.split(",");
//				OpaSchemeItem entity=opaSchemeItemService.get(ids);
				for(int i = 0;i<lists.size();i++) {
					
					Map<String,Object> param = new HashMap<String,Object>();
					String[] idss = ids.split(",");
					param.put("ids", idss);
					param.put("auditorId", auditorId);
					param.put("auditorOfficeId", a[i]);
					param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
					param.put("updateBy", UserUtils.getUser().getId());
					param.put("updateDate", new Date());
					opaSchemeItemService.editAssign(param);
				}
//				List<OpaSchemeItem>list=opaSchemeItemService.schemeFindList(entity.getSchemeId());
//				for(int i=0;i<list.size();i++) {
//					OpaSchemeItem item=list.get(i);
//					if(item.getAuditorOfficeId()!=a[i]) {
//						for(int j=0;j<a.length;j++){
//							Map<String,Object>param1=new HashMap<String,Object>();
//							param1.put("id", item.getId());
//						    param1.put("auditorOfficeId",a[i]);
//							param1.put("auditorId", auditorId);
//							param1.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
//							opaSchemeItemService.updateGx(param1);
//						}
//					}
//				}
//			}
		}else {
			Map<String,Object> param = new HashMap<String,Object>();
			String[] idss = ids.split(",");
			param.put("ids", idss);
			param.put("auditorId", auditorId);
			param.put("auditorOfficeId", auditorOfficeId);
			param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
			param.put("updateBy", UserUtils.getUser().getId());
			param.put("updateDate", new Date());
			opaSchemeItemService.editAssign(param);
		}
		return msg;
	}
	
	@RequiresPermissions("opa:opaSchemeItem:assign:edit")
	@ResponseBody
	@RequestMapping(value = "assign/add")
	public String addAssign(HttpServletRequest request, HttpServletResponse response) {
		String msg = "ok";
		String ids = request.getParameter("ids"); // schemeItemId
		String auditorId = request.getParameter("auditorId");
		String auditorOfficeId = request.getParameter("officeId");
		if(auditorOfficeId.contains(",")) {//是否有逗号
			String str = auditorOfficeId.substring(0, auditorOfficeId.indexOf(","));//截取逗号之前的字符
//			if(!auditorOfficeId.equals(str)) {
				String officeIds[] = auditorOfficeId.split(",");
				String[] schemeItemIds = ids.split(",");
				OpaSchemeItem entity = null;
				for (int i = 0; i < officeIds.length; i++) {
					String string = officeIds[i];
					
					if(i == 0) {
						Map<String,Object> param = new HashMap<String,Object>();
						String[] idss = ids.split(",");
						param.put("ids", idss);
						param.put("auditorId", auditorId);
						param.put("auditorOfficeId", string);
						param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
						param.put("updateBy", UserUtils.getUser().getId());
						param.put("updateDate", new Date());
						opaSchemeItemService.editAssign(param);
					} else {
						for (String id : schemeItemIds) {
							entity=opaSchemeItemService.get(id);
							entity.setStatus(DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
							entity.setAuditorId(auditorId);
							entity.setAuditorOfficeId(string);
							entity.setUpdateDate(new Date());
							entity.setUpdateBy(UserUtils.getUser());
							opaSchemeItemService.itemInsert(entity);//多少个部门 插入多少数据
						}
					}
					
//					for (String id : schemeItemIds) {
//						if(i == 0) {
//							Map<String,Object> param = new HashMap<String,Object>();
//							String[] idss = ids.split(",");
//							param.put("ids", idss);
//							param.put("auditorId", auditorId);
//							param.put("auditorOfficeId", string);
//							param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
//							param.put("updateBy", UserUtils.getUser().getId());
//							param.put("updateDate", new Date());
//							opaSchemeItemService.editAssign(param);
//						} else {
//							
//						}
//					}
				}
				
//				for (String string : officeIds) {
//					for (String id : schemeItemIds) {
//					
//						entity=opaSchemeItemService.get(id);
//						entity.setStatus(DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
//						entity.setAuditorId(auditorId);
//						entity.setAuditorOfficeId(string);
//						entity.setUpdateDate(new Date());
//						entity.setUpdateBy(UserUtils.getUser());
//						opaSchemeItemService.itemInsert(entity);//多少个部门 插入多少数据
//					}
//				}
				
				
////				for (String id : schemeItemIds) {
////					entity=opaSchemeItemService.get(id);
////					opaSchemeItemService.itemInsert(entity);//多少个部门 插入多少数据
////				}
//				
//				String[] a=auditorOfficeId.split(",");
////				OpaSchemeItem entity=opaSchemeItemService.get(ids);
//				for(int i = 0;i<a.length-1;i++) {
////					opaSchemeItemService.itemInsert(entity);//多少个部门 插入多少数据
//					Map<String,Object> param = new HashMap<String,Object>();
//					String[] idss = ids.split(",");
//					param.put("ids", idss);
//					param.put("auditorId", auditorId);
//					param.put("auditorOfficeId", a[i]);
//					param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
//					param.put("updateBy", UserUtils.getUser().getId());
//					param.put("updateDate", new Date());
//					opaSchemeItemService.editAssign(param);
//				}
//				List<OpaSchemeItem>list=opaSchemeItemService.schemeFindList(entity.getSchemeId());
//				Map<String, List<OpaSchemeItem>> map = new HashMap<String, List<OpaSchemeItem>>();
//				for(OpaSchemeItem item : list) {
//					map.put("", value)
//				}
				
				
//				for(int i=0;i<list.size();i++) {
//					OpaSchemeItem item=list.get(i);
//					for(int j=0;j<a.length;j++){
//						if(item.getAuditorOfficeId()!=a[j]) {
//							Map<String,Object>param1=new HashMap<String,Object>();
//							param1.put("id", item.getId());
//						    param1.put("auditorOfficeId",a[j]);
//							param1.put("auditorId", auditorId);
//							param1.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
//							opaSchemeItemService.updateGx(param1);
//						}
//					}
//				}
//			}
		}else {
			Map<String,Object> param = new HashMap<String,Object>();
			String[] idss = ids.split(",");
			param.put("ids", idss);
			param.put("auditorId", auditorId);
			param.put("auditorOfficeId", auditorOfficeId);
			param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
			param.put("updateBy", UserUtils.getUser().getId());
			param.put("updateDate", new Date());
			opaSchemeItemService.editAssign(param);
		}
		return msg;
	}
	
//	@RequiresPermissions("opa:opaSchemeItem:assign:edit")
//	@ResponseBody
//	@RequestMapping(value = "assign/add")
//	public String addAssign(HttpServletRequest request, HttpServletResponse response) {
//		String msg = "ok";
//		String ids = request.getParameter("ids"); // schemeItemId
//		String auditorId = request.getParameter("auditorId");
//		String auditorOfficeId = request.getParameter("officeId");
//		if(auditorOfficeId.contains(",")) {//是否有逗号
//			String str = auditorOfficeId.substring(0, auditorOfficeId.indexOf(","));//截取逗号之前的字符
//			if(!auditorOfficeId.equals(str)) {
//				String[] a=auditorOfficeId.split(",");
//				OpaSchemeItem entity=opaSchemeItemService.get(ids);
//				for(int i = 0;i<a.length-1;i++) {
//					opaSchemeItemService.itemInsert(entity);//多少个部门 插入多少数据
//					Map<String,Object> param = new HashMap<String,Object>();
//					String[] idss = ids.split(",");
//					param.put("ids", idss);
//					param.put("auditorId", auditorId);
//					param.put("auditorOfficeId", a[i]);
//					param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
//					param.put("updateBy", UserUtils.getUser().getId());
//					param.put("updateDate", new Date());
//					opaSchemeItemService.editAssign(param);
//				}
//				List<OpaSchemeItem>list=opaSchemeItemService.schemeFindList(entity.getSchemeId());
//				for(int i=0;i<list.size();i++) {
//					OpaSchemeItem item=list.get(i);
//					if(item.getAuditorOfficeId()!=a[i]) {
//						for(int j=0;j<a.length;j++){
//							Map<String,Object>param1=new HashMap<String,Object>();
//							param1.put("id", item.getId());
//						    param1.put("auditorOfficeId",a[i]);
//							param1.put("auditorId", auditorId);
//							param1.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
//							opaSchemeItemService.updateGx(param1);
//						}
//					}
//				}
//			}
//		}else {
//			Map<String,Object> param = new HashMap<String,Object>();
//			String[] idss = ids.split(",");
//			param.put("ids", idss);
//			param.put("auditorId", auditorId);
//			param.put("auditorOfficeId", auditorOfficeId);
//			param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
//			param.put("updateBy", UserUtils.getUser().getId());
//			param.put("updateDate", new Date());
//			opaSchemeItemService.editAssign(param);
//		}
//		return msg;
//	}

	
	@RequiresPermissions("opa:opaSchemeItem:assign:edit")
	@ResponseBody
	@RequestMapping(value = "assign/remove")
	public String removeAssign( HttpServletRequest request, HttpServletResponse response) {
		String msg = "ok";
		String ids = request.getParameter("ids");
		Map<String,Object> param = new HashMap<String,Object>();
		String[] idss = ids.split(",");
		param.put("ids", idss);
		param.put("auditorId", "");
		param.put("auditorOfficeId", "");
		param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
		param.put("updateBy", UserUtils.getUser().getId());
		param.put("updateDate", new Date());
		opaSchemeItemService.editAssign(param);
		return msg;
	}
	
	/**
	 * 指标分配 -- 上报指标分配   下发
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:edit")
	@RequestMapping(value = "assign/apply")
	public String assignApply(OpaSchemeItem opaSchemeItem, OpaPlanOffice opaPlanOffice,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		opaSchemeItem = opaSchemeItemService.get(request.getParameter("id"));
		opaSchemeItem.setStatus(DictUtils.getDictValue("已发布", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		int total = opaSchemeItemService.findStatus(opaSchemeItem);
		System.out.println("-------------------"+total);
		if(total == 0) {
			opaSchemeItemService.updateStatus(opaSchemeItem, opaPlanOffice);
		}
		addMessage(redirectAttributes, "方案指标下发成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	
	
	/**
	 * 指标分配完全部下发
	 * @param opaSchemeItem
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:all:issud")
	@RequestMapping(value="all/issud")
	public String allIssud(OpaSchemeItem opaSchemeItem,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String ok="ok";
		String schemeId=request.getParameter("schemeId");
		opaSchemeItemDao.updateAllIssued(schemeId);
		opaSchemeItemService.updateIssud(opaSchemeItem, schemeId, request);
		addMessage(redirectAttributes, "方案指标全部下发成功");
		return ok;
		//return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/index?schemeId="+schemeId+"&repage";
	}
	
	
	
    /**
     * 领导小组办公室审核查询
     * @param opaSchemeItem
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequiresPermissions("opa:opaSchemeItem:assign:audit:index")
	@RequestMapping(value = {"assign/audit/index"})
	public String assignAuditIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaSchemeItem>page = opaSchemeItemService.findPage(new Page<OpaSchemeItem>(request,response),opaSchemeItem);
		model.addAttribute("page", page);
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("审核中", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			opaSchemeItem.setAuditorId(UserUtils.getUser().getId());
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}	
		return "modules/opa/opaSchemeItemAssignAuditIndex";
	}
	
	
	
	/**
	 * 办公室全部审核
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:audit:all:edit")
	@ResponseBody
	@RequestMapping(value="audit/all/pass")
	public String updateAssign(HttpServletRequest request, HttpServletResponse response) {
		String ok = "ok";
		String schemeId=request.getParameter("schemeId");
		//System.out.println("id:"+schemeId);
		//String str[]=schemeId.split(","); 
		 //List<String> list = Arrays.asList(str);
		/*HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("schemeId", schemeId);*/
	    opaSchemeItemDao.updateAllByStatus(schemeId);
		OpaSchemeItem schemeItem = new OpaSchemeItem ();
        schemeItem.setSchemeId(schemeId);
        opaSchemeItemService.allPass(schemeItem,schemeId);
		return ok;
	} 
	
	//领导小组办公室审核
	@RequiresPermissions("opa:opaSchemeItem:leadership:review:index")
	@RequestMapping(value = {"leadership/review/index"})
	public String leadershipReviewIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标分配中", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			opaSchemeItem.setAuditorId(UserUtils.getUser().getId());
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}	
		return "modules/opa/opaSchemeItemLeadershipReviewIndex";
	}
	
	
	
	
	/*
	 * 查看方案指标明细表
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:audit:view")
	@RequestMapping(value = "assign/audit/view")
	public String assignAuditView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
		}
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemAssignAuditView";
	}
	
    /**
     * 领导小组  审核通过 
     * 
     * @param opaSchemeItem
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("opa:opaSchemeItem:assign:audit:pass")
	@RequestMapping(value = "assign/audit/pass")
	public String assignAuditPass(OpaSchemeItem opaSchemeItem, Model model, HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes) {
		opaSchemeItem.setStatus(DictUtils.getDictValue("待分配","opa_schemeItem_status",""));
		opaSchemeItemService.assignAuditPass(opaSchemeItem);
		addMessage(redirectAttributes, "方案指标审核通过");
		opaSchemeItemService.save(opaSchemeItem);
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	
	
	@RequiresPermissions("opa:opaSchemeItem:all:audit:pass")
	@RequestMapping(value="all/audit/pass")
	public String AllAuditPass(OpaSchemeItem opaSchemeItem,Model model ,HttpServletRequest request,HttpServletResponse response) {
		String schemeId=opaSchemeItem.getSchemeId();
		OpaScheme opaScheme = opaSchemeService.get(schemeId);
		opaScheme.setStatus(DictUtils.getDictLabel("指标分配中", "opa_scheme_status", ""));
		opaSchemeDao.update(opaScheme);
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		param.setStatus(DictUtils.getDictValue("待分配", "opa_schemeItem_status", ""));
		//List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	/**
	 * 审核不通过 退回
	 * @param opaSchemeItem
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaSchemeItem:assign:audit:return")
	@RequestMapping(value = "assign/audit/return")
	public String assignAuditReturn(OpaSchemeItem opaSchemeItem, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		/*add lxy start*/
		String reason = request.getParameter("reason");
		String remark = opaSchemeItem.getRemarks();
		StringBuffer allReason = new StringBuffer();
		if(StringUtils.isNoneEmpty(remark)){
			allReason.append("\n").append(reason);
		}else{
			allReason.append(reason);
		}
		opaSchemeItem.setRemarks(allReason.toString());
		/*add lxy end*/
		opaSchemeItem.setStatus(DictUtils.getDictValue("分配已退回", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		addMessage(redirectAttributes, "指标分配已退回");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	
	/**
	 * lxy
	 * 指标填报审核模块 
	 */
	@RequiresPermissions("opa:opaSchemeItem:apply:audit:index")
	@RequestMapping(value = {"apply/audit/index"})
	public String applyAuditIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
//		statusList.add(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaSchemeItemApplyAuditIndex";
	}
	//lxy
	@RequiresPermissions("opa:opaSchemeItem:apply:audit:view")
	@RequestMapping(value = "apply/audit/view")
	public String applyAuditView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
		}
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemApplyAuditView";
	}
	
	/**
	 * 填报审核通过阶段
	 * @param opaSchemeItem
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	//lxy
	@RequiresPermissions("opa:opaSchemeItem:apply:audit:pass")
	@RequestMapping(value = "apply/audit/pass")
	public String applyAuditPass(OpaSchemeItem opaSchemeItem, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaSchemeItem.setStatus(DictUtils.getDictValue("填报已审核", "opa_schemeItem_status", ""));
		opaSchemeItemService.applyAuditPass2(opaSchemeItem);
		addMessage(redirectAttributes, "方案指标填报审核通过");
		opaSchemeItemService.save(opaSchemeItem);
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/apply/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	/**
	 * 填报审核失败 退回
	 * @param opaSchemeItem
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	//lxy
	@RequiresPermissions("opa:opaSchemeItem:apply:audit:return")
	@RequestMapping(value = "apply/audit/return")
	public String applyAuditReturn(OpaSchemeItem opaSchemeItem, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		/*add lxy start*/
		String reason = request.getParameter("reason");
		String remark = opaSchemeItem.getRemarks();
		StringBuffer allReason = new StringBuffer();
		if(StringUtils.isNoneEmpty(remark)){
			allReason.append("\n").append(reason);
		}else{
			allReason.append(reason);
		}
		opaSchemeItem.setRemarks(allReason.toString());
		/*add lxy end*/
		opaSchemeItem.setStatus(DictUtils.getDictValue("填报已退回", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		addMessage(redirectAttributes, "方案指标填报已退回");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/apply/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	/**
	 * 填报退回 修改内容
	 * @param opaSchemeItem
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("opa:opaSchemeItem:modifly:view")
	@RequestMapping(value = "modilfy/view")
	public String applymodiflyView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		if(opaSchemeItem==null){
			opaSchemeItem = opaSchemeItemService.get(request.getParameter("id"));
		}
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		boolean ifParent = true;
		Double parentValue = 0D;
		Double childSum = 0D;
		Double restValue = 0D;
		Double thisValue = 0D;
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
			parentValue = parent.getValue();
			parentValue = parentValue==null?0:parentValue;
			childSum = opaSchemeItemService.getChildSum(parent);
			childSum = childSum==null?0:childSum;
			thisValue = opaSchemeItem.getValue();
			thisValue = thisValue==null?0:thisValue; 
			restValue = parentValue - childSum + thisValue;
			ifParent = false;
		}
		model.addAttribute("ifParent", ifParent);
		model.addAttribute("parentValue", parentValue);
		model.addAttribute("restValue", restValue);
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemApplyModiflyForm";
	}
	
	/**
	 * 指标填报阶段
	 * @param opaSchemeItem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("opa:opaSchemeItem:apply:index")
	@RequestMapping(value = {"apply/index"})
	public String applyIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaSchemeItem>page = opaSchemeItemService.findPage(new Page<OpaSchemeItem>(request,response),opaSchemeItem);
		model.addAttribute("page", page);
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaSchemeItemApplyIndex";
	}
	
	
	/**
	 * 提交      指标填报 
	 * @param opaSchemeItem
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaSchemeItem:apply:apply")
	@RequestMapping(value = "apply/apply")
	public String applyApply(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		int total = opaSchemeItemService.findStatus2(opaSchemeItem);
		System.out.println("-------------------"+total);
		if(total == 0) {
			opaSchemeItem.setStatus(DictUtils.getDictValue("已提交", "opa_schemeItem_status", ""));
			opaSchemeItemService.save(opaSchemeItem);
				opaSchemeItemService.applyAuditPass(opaSchemeItem);
			addMessage(redirectAttributes, "方案指标填报提交成功");
		} else {
			addMessage(redirectAttributes, "方案指标未填报完");
		}
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/apply/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	/**
	 * 指标批量填报模块 
	 */
	@RequiresPermissions("opa:opaSchemeItem:apply:batch:index")
	@RequestMapping(value = {"apply/batch/index"})
	public String applyBatchIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("schemeId",request.getParameter("schemeId"));
		return "modules/opa/opaSchemeItemApplyBatchIndex";
	}
	
	@RequiresPermissions("opa:opaSchemeItem:apply:batch:view")
	@RequestMapping(value = "apply/batch/view")
	public String applyBatchView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(opaSchemeItem==null){
			opaSchemeItem = opaSchemeItemService.get(request.getParameter("id"));
		}
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		boolean ifParent = true;
		Double parentValue = 0D;
		Double childSum = 0D;
		Double restValue = 0D;
		Double thisValue = 0D;
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
			parentValue = parent.getValue();
			parentValue = parentValue==null?0:parentValue;
			childSum = opaSchemeItemService.getChildSum(parent);
			childSum = childSum==null?0:childSum;
			thisValue = opaSchemeItem.getValue();
			thisValue = thisValue==null?0:thisValue; 
			restValue = parentValue - childSum + thisValue;
			ifParent = false;
		}
		model.addAttribute("ifParent", ifParent);
		model.addAttribute("parentValue", parentValue);
		model.addAttribute("restValue", restValue);
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemApplyBatchForm";
	}
	
	/**
	 * 指标分数填报
	 * @param opaSchemeItem
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaSchemeItem:apply:edit")
	@RequestMapping(value = "apply/batch/save")
	public String applyBatchSave(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaSchemeItem)){
			return applyBatchView(opaSchemeItem, request,response, model);
		}
		opaSchemeItem.setStatus(DictUtils.getDictValue("已填报", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		List<OpaSchemeItem> list = opaSchemeItemDao.opaSchemeItemName(opaSchemeItem.getName());
		for(OpaSchemeItem item:list) {
			if(item.getSchemeId().equals(opaSchemeItem.getSchemeId())) {
				OpaSchemeItem tm = new OpaSchemeItem();
				tm.setId(item.getId());
				tm.setValue(opaSchemeItem.getValue());
				tm.setSchemeId(opaSchemeItem.getSchemeId());
				tm.setStatus(opaSchemeItem.getStatus());
				tm.setIfNum(opaSchemeItem.getIfNum());
				opaSchemeItemDao.updateValue(tm);
			}
		}
		addMessage(model, "保存方案指标成功!!");
		return applyBatchView(opaSchemeItem, request, response, model);
	}
	
	//指标填报明细查看
	@RequiresPermissions("opa:opaSchemeItem:apply:view")
	@RequestMapping(value = "apply/view")
	public String applyView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		if(opaSchemeItem==null){
			opaSchemeItem = opaSchemeItemService.get(request.getParameter("id"));
		}
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		boolean ifParent = true;
		Double parentValue = 0D;
		Double childSum = 0D;
		Double restValue = 0D;
		Double thisValue = 0D;
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
			parentValue = parent.getValue();
			parentValue = parentValue==null?0:parentValue;
			childSum = opaSchemeItemService.getChildSum(parent);
			childSum = childSum==null?0:childSum;
			thisValue = opaSchemeItem.getValue();
			thisValue = thisValue==null?0:thisValue; 
			restValue = parentValue - childSum + thisValue;
			ifParent = false;
		}
		model.addAttribute("ifParent", ifParent);
		model.addAttribute("parentValue", parentValue);
		model.addAttribute("restValue", restValue);
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemApplyForm";
	}
	
	
	
	/**
	 * 指标分数填报完成   全部下发
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaSchemeItem:all:edit")
	@RequestMapping(value="all/score/Issued")
	public String allScoreIssued(OpaSchemeItem opaSchemeItem,Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String msg="ok";
		String schemeId = request.getParameter("schemeId");
		OpaSchemeItem param = new OpaSchemeItem();
		param.setStatus(DictUtils.getDictValue("已发布", "opa_schemeItem_status", ""));
		param.setSchemeId(schemeId);
		opaSchemeItemDao.updateAllScoreIssued(param);
		opaSchemeItemService.allScore(schemeId, opaSchemeItem);
		return msg;
	}
	
	
	
	
	@RequiresPermissions("opa:opaSchemeItem:apply:edit")
	@RequestMapping(value = "apply/save")
	public String applySave(OpaSchemeItem opaSchemeItem, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaSchemeItem)){
			return applyView(opaSchemeItem,request, model);
		}
		opaSchemeItem.setStatus(DictUtils.getDictValue("已填报", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		addMessage(redirectAttributes, "保存方案指标成功!");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/apply/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	//分配退回  
	@RequiresPermissions("opa:opaSchemeItem:assign:edit")
	@RequestMapping(value = "form")
	public String form(OpaSchemeItem opaSchemeItem, Model model) {
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemModifyForm";
	}
	
	
	//指标分配退回 保存
		@RequiresPermissions("opa:opaSchemeItem:assign:edit")
		@RequestMapping(value = "save")
		public String save(OpaSchemeItem opaSchemeItem, Model model, RedirectAttributes redirectAttributes) {
			if (!beanValidator(model, opaSchemeItem)){
				return form(opaSchemeItem, model);
			}
			if (StringUtils.isBlank(opaSchemeItem.getId())){
				opaSchemeItem.setCode(IdGen.uuid());
			}
			OpaScheme schemeId = opaSchemeService.get(opaSchemeItem.getSchemeId());
			opaSchemeItem.setLevel(schemeId.getLevel());
			opaSchemeItem.setStatus(DictUtils.getDictValue("已分配", "opa_schemeItem_status",""));
			opaSchemeItemService.save(opaSchemeItem);
			addMessage(redirectAttributes, "指标分配修改成功");
			return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
		
		}
        //分配退回  删除
		/*@RequiresPermissions("opa:opaSchemeItem:assign:edit")
		@RequestMapping(value = "assigndelete")
		public String assigndelete(OpaSchemeItem opaSchemeItem, RedirectAttributes redirectAttributes) {
			opaSchemeItemService.delete(opaSchemeItem);
			addMessage(redirectAttributes, "删除指标分配成功");
			return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/?repage";
		}*/
	@RequiresPermissions("opa:opaSchemeItem:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaSchemeItem opaSchemeItem, RedirectAttributes redirectAttributes) {
		opaSchemeItemService.delete(opaSchemeItem);
		addMessage(redirectAttributes, "删除方案指标成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/?repage";
	}
	
	@RequiresPermissions("opa:baseData:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData( HttpServletRequest request, String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		OpaSchemeItem osi = new OpaSchemeItem();
		osi.setSchemeId(request.getParameter("schemeId"));
		List<OpaSchemeItem> list = opaSchemeItemService.findList(osi);
		for (int i=0; i<list.size(); i++){
			OpaSchemeItem e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getItemParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getItemId());
				map.put("pId", e.getItemParentId());
				map.put("name", e.getName());
				map.put("schemeItemId", e.getId());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 分配指标弹框  未分配指标
	 * @param opaScheme
	 * @param request
	 * @param extId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("opa:baseData:view")
	@ResponseBody
	@RequestMapping(value = "TreeDataForAssign")
	public List<Map<String, Object>> TreeDataForAssign(OpaScheme opaScheme, HttpServletRequest request, String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		OpaSchemeItem osi = new OpaSchemeItem();
		osi.setSchemeId(request.getParameter("schemeId"));
		osi.setAuditorId(request.getParameter("auditorId"));
		osi.setStatus(request.getParameter("status"));
		osi.setItemId(request.getParameter("itemId"));
		List<OpaSchemeItem> list = opaSchemeItemService.findListForAssign(osi);
		//List<OpaScheme> li = opaSchemeService.findList(opaScheme);
		for (int i=0; i<list.size(); i++){
			OpaSchemeItem e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getItemParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getItemId());
				map.put("pId", e.getItemParentId());
				map.put("name", e.getName());
				map.put("schemeItemId", e.getId());
				map.put("itemId", e.getItemId());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
	/**
	 * 
	 */
	@RequiresPermissions("opa:opaSchemeItem:monitor:index")
	@RequestMapping(value = {"monitor/index"})
	public String monitorIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		String str = "已发布";
		String str1 = "指标填报中";
		String str2 = "指标分配中";
		statusList.add(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaSchemeItemMonitorIndex";
	}
	/* add by lxy start */
	@RequiresPermissions("opa:opaSchemeItem:monitor:view")
	@RequestMapping(value = {"monitor/list"})
	public String monitorList(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaSchemeItem> page = opaSchemeItemService.findPage(new Page<OpaSchemeItem>(request, response), opaSchemeItem); 
		model.addAttribute("page", page);
		return "modules/opa/opaSchemeItemMonitorList";
	}
}