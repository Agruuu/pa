/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.modules.opa.dao.OpaOverviewDao;
import com.orhon.pa.modules.opa.entity.OpaOverview;
import com.orhon.pa.modules.opa.service.OpaOverviewService;
import com.orhon.pa.modules.sys.entity.Office;

/**
 * 综合评分Controller
 * @author ss
 * @version 2018-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaOverview")
public class OpaOverviewController extends BaseController {

	@Autowired
	private OpaOverviewService opaOverviewService;
	
	@Autowired
	private OpaOverviewDao opaOverviewDao;
	
	@ModelAttribute
	public OpaOverview get(@RequestParam(required=false) String id) {
		OpaOverview entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaOverviewService.get(id);
		}
		if (entity == null){
			entity = new OpaOverview();
		}
		return entity;
	}
	
	
	@RequiresPermissions("opa:opaOverview:index")
	@RequestMapping(value = {"index"})
	public String index(OpaOverview opaOverview, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OpaOverview> list = opaOverviewService.findList(opaOverview); 
		model.addAttribute("list", list);
		return "modules/opa/opaOverviewIndex";
	}
	
	
	@RequiresPermissions("opa:opaOverview:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaOverview opaOverview, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaOverview> page = opaOverviewService.findPage(new Page<OpaOverview>(request, response), opaOverview); 
		model.addAttribute("page", page);
		return "modules/opa/opaOverviewList";
	}

	@RequiresPermissions("opa:opaOverview:view")
	@RequestMapping(value = "form")
	public String form(OpaOverview opaOverview, Model model) {
		model.addAttribute("opaOverview", opaOverview);
		return "modules/opa/opaOverviewForm";
	}

	@RequiresPermissions("opa:opaOverview:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(OpaOverview opaOverview, HttpServletRequest request,Model model, RedirectAttributes redirectAttributes) {
		String planId= (String) request.getSession().getAttribute("planId");
		if (!beanValidator(model, opaOverview)){
			return form(opaOverview, model);
		}
		opaOverview.setId(IdGen.uuid());
		opaOverview.setLevel(1);
		opaOverview.setOffice(new Office(request.getParameter("rep_org_code")));
		BigDecimal Year = new BigDecimal(opaOverview.getYearResult());
		Double result = Year.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String yearResult = result.toString();
		opaOverview.setYearResult(yearResult);
		BigDecimal dai = new BigDecimal(opaOverview.getDaiScore());
		Double score = dai.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		String daiScore=score.toString();
		opaOverview.setDaiScore(daiScore);
		BigDecimal leading = new BigDecimal(opaOverview.getLeadingScore());
		Double score1 = leading.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		String leadingScore=score1.toString();
		opaOverview.setLeadingScore(leadingScore);
		BigDecimal county = new BigDecimal(opaOverview.getCountyScore());
		Double score2 = county.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		String contyScore=score2.toString();
		opaOverview.setCountyScore(contyScore);
		BigDecimal peer = new BigDecimal(opaOverview.getPeerReview());
		Double review = peer.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		String peerReview=review.toString();
		opaOverview.setPeerReview(peerReview);
		BigDecimal leader = new BigDecimal(opaOverview.getLeadershipAssessment());
		Double leadership = leader.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		String leadershipAssessment=leadership.toString();
		opaOverview.setLeadershipAssessment(leadershipAssessment);
		BigDecimal service = new BigDecimal(opaOverview.getServiceRating());
		Double rating = service.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		String serviceRating=rating.toString();
		opaOverview.setServiceRating(serviceRating);
		BigDecimal assessment = new BigDecimal(opaOverview.getAssessmentScore());
		Double ssess = assessment.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		String assessmentScore=ssess.toString();
		opaOverview.setAssessmentScore(assessmentScore);
		opaOverview.setSchemeId(planId);
      /*  Double con=((result*0.5)+(score*0.5))*0.8;
		String converScore=con.toString();
		opaOverview.setConvertScore(converScore);*/
		//opaOverviewService.save(opaOverview);
		opaOverviewDao.insert(opaOverview);
		opaOverviewDao.deleteDelFlag(opaOverview);
		addMessage(redirectAttributes, "保存综合评分成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaOverview/index?repage";
	}
	
	@RequiresPermissions("opa:opaOverview:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaOverview opaOverview, RedirectAttributes redirectAttributes) {
		opaOverviewService.delete(opaOverview);
		addMessage(redirectAttributes, "删除综合评分成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaOverview/?repage";
	}

}