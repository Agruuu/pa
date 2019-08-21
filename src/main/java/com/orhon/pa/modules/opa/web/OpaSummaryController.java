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
import org.springframework.web.bind.annotation.ResponseBody;

import com.orhon.pa.common.persistence.BaseEntity;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.opa.service.OpaItemService;
import com.orhon.pa.modules.opa.service.OpaSchemeService;
import com.orhon.pa.modules.opa.service.OpaSummaryService;
import com.orhon.pa.modules.sys.utils.DictUtils;

/**
 * 汇总模块Controller
 * @author Shawn
 * @version 2017-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaSummary")
public class OpaSummaryController extends BaseController {

	@Autowired
	private OpaItemService opaItemService;
	@Autowired
	private OpaSummaryService opaSummaryService;
	@Autowired
	private OpaSchemeService opaSchemeService;
	@Autowired 
	private OpaSchemeItemDao opaSchemeItemDao;
	
	@Autowired
	private OpaSchemeDao opaSchemeDao;
	
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
	
	
	/**
	 * 获取表头内容
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/tableheader"})
	@ResponseBody
	public Object getHeader( HttpServletRequest request, HttpServletResponse response, Model model){
		//查询死表头
		List<OpaSchemeItem> dieList = opaSchemeItemDao.findAutoHeader("ca39eb8cbbee43cca4a05dc80b679096");
		return dieList;
	}
	
	/*@RequiresPermissions("opa:summary:all:index")
	@RequestMapping(value = {"all/index"})
	public String allIndex(OpaPlanTask opaPlanTask, Integer Identificationsymbo,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		//将planId放入sesison   用于弹框查询
		request.getSession().setAttribute("planId", opaPlanTask.getPlanId());
		statusList.add(DictUtils.getDictValue("已完成", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", BaseEntity.DEL_FLAG_NORMAL);
		List<Status> schemeList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeList", schemeList);
		Map<String, Object> headAndData= new HashMap<String, Object>();
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			headAndData = opaSummaryService.getHeadAndDataMap(opaPlanTask.getPlanId());
			model.addAttribute("headAndData", headAndData);
			System.out.println("将所有数据为="+headAndData);
		}
		return "modules/opa/opaSummaryAllIndex";
	}
	*/
	
	@RequiresPermissions("opa:summary:all:index")
	@RequestMapping(value = {"all/index"})
	public String allIndex(OpaPlanTask opaPlanTask, String Identificationsymbo,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		//将planId放入sesison   用于弹框查询
		request.getSession().setAttribute("planId", opaPlanTask.getPlanId());
		statusList.add(DictUtils.getDictValue("已完成", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", BaseEntity.DEL_FLAG_NORMAL);
		List<Status> schemeList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeList", schemeList);
		System.out.println("schemeList="+schemeList);
		Map<String, Object> headAndData= new HashMap<String, Object>();
		//String ymbo =String.valueOf(Identificationsymbo);
		if (Identificationsymbo!=null && !"0".equals(Identificationsymbo)) {
			headAndData = opaSummaryService.getHeadAndDataMap(Identificationsymbo);
			model.addAttribute("headAndData", headAndData);
			System.out.println("headAndData="+headAndData);
		}
		return "modules/opa/opaSummaryAllIndex";
	}
	
	
	@RequiresPermissions("opa:summary:office:index")
	@RequestMapping(value = {"office/index"})
	public String officeIndex(OpaItem opaItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OpaItem> list = opaItemService.findList(opaItem); 
		model.addAttribute("list", list);
		return "modules/opa/opaAllSummary";
	}
	
	@RequiresPermissions("opa:summary:apply:batch:edit")
	@RequestMapping(value= {"/InputScore"})
	public String applyBatch(OpaSchemeItem opaSchemeItem,HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OpaSchemeItem> list = opaSchemeItemDao.findImmobilization("ca39eb8cbbee43cca4a05dc80b679096");
		model.addAttribute("list",list);
		return "modules/opa/opaSummaryApplyBatchForm";
	}
	
	@RequiresPermissions("opa:summary:apply:batch:view")
	@RequestMapping(value="apply/batch/view")
	@ResponseBody
     public Object getofficeList(HttpServletRequest request, HttpServletResponse response,String Identificationsymbo, Model model){
		String planId= (String) request.getSession().getAttribute("planId");
		OpaScheme opaScheme = opaSchemeDao.get(planId);
		Map<String, Object> headAndData =null;
		if (StringUtils.isNoneBlank(opaScheme.getId())) {
			headAndData = opaSummaryService.getHeadAndDataMap(Identificationsymbo);
		}
		return headAndData;
	}
	
}