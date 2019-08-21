/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.common.utils.DateUtils;
import com.orhon.pa.common.utils.FileUtils;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.modules.opa.entity.OpaPlan;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.opa.entity.OpaTaskAppeal;
import com.orhon.pa.modules.opa.service.OpaPlanService;
import com.orhon.pa.modules.opa.service.OpaPlanTaskService;
import com.orhon.pa.modules.opa.service.OpaTaskAppealService;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 绩效反馈模块Controller
 * @author Shawn
 * @version 2017-06-01
 */
@Controller                        
@RequestMapping(value = "${adminPath}/opa/opaTaskAppeal")
public class OpaTaskAppealController extends BaseController {

	@Autowired
	private OpaTaskAppealService opaTaskAppealService;
	@Autowired
	private OpaPlanService opaPlanService;
	@Autowired
	private OpaPlanTaskService opaPlanTaskService;

	
	@ModelAttribute
	public OpaTaskAppeal get(@RequestParam(required=false) String id) {
		OpaTaskAppeal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaTaskAppealService.get(id);
		}
		if (entity == null){
			entity = new OpaTaskAppeal();
		}
		return entity;
	}
	
	@RequiresPermissions("opa:opaTaskAppeal:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpaTaskAppeal opaTaskAppeal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaTaskAppeal> page = opaTaskAppealService.findPage(new Page<OpaTaskAppeal>(request, response), opaTaskAppeal); 
		model.addAttribute("page", page);
		return "modules/opa/opaTaskAppealList";
	}
    
	/*
	 * 申诉模块添加
	 */
	@RequiresPermissions("opa:opaTaskAppeal:edit")
	@RequestMapping(value = "form")
	public String form(OpaTaskAppeal opaTaskAppeal,Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("执行中", "opa_planTask_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaPlanTaskService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		model.addAttribute("opaTaskAppeal", opaTaskAppeal);
		return "modules/opa/opaTaskAppealForm";
	}
    /*
     * 评分申诉保存
     */
	@RequiresPermissions("opa:opaTaskAppeal:edit")
	@RequestMapping(value = "save")
	public String save(OpaTaskAppeal opaTaskAppeal, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaTaskAppeal)){
			return form(opaTaskAppeal, model);
		}
		if (StringUtils.isBlank(opaTaskAppeal.getId())){
			opaTaskAppeal.setCode(IdGen.uuid());
		}
		OpaPlanTask planTask = new OpaPlanTask();
		planTask.setPlanId(planTask.getPlanId());
		planTask=opaPlanTaskService.get(planTask);
		opaTaskAppeal.setStatus(DictUtils.getDictValue("申诉中", "opa_taskAppeal_status",""));
		opaTaskAppealService.save(opaTaskAppeal);
		addMessage(redirectAttributes, "保存绩效反馈成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaTaskAppeal/index?repage";
	}
	
	@RequiresPermissions("opa:opaTaskAppeal:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaTaskAppeal opaTaskAppeal, RedirectAttributes redirectAttributes) {
		opaTaskAppealService.delete(opaTaskAppeal);
		addMessage(redirectAttributes, "删除绩效反馈成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaTaskAppeal/?repage";
	}
 
	/**
	 * 绩效反馈 评分申诉查询
	 * @param opaTaskAppeal
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("opa:opaTaskAppeal:index")
	@RequestMapping(value = "index")
	public String index(OpaTaskAppeal opaTaskAppeal,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL",OpaTaskAppeal.DEL_FLAG_NORMAL );
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if(StringUtils.isNoneBlank(opaTaskAppeal.getPlanId())){
			opaTaskAppeal.setOffice(UserUtils.getUser().getOffice());
			opaTaskAppeal.setMethod(DictUtils.getDictValue("手工考核", "opa_item_method", ""));
			opaTaskAppeal.setStatus(DictUtils.getDictValue("审核中", "opa_taskAppeal_status", ""));
			List<OpaTaskAppeal> list = opaTaskAppealService.findList(opaTaskAppeal);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaTaskAppealIndex";
	}
	
	
	
	/**
	 * 评分申诉 打分
	 * @param opaTaskAppeal
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("opa:opaTaskAppeal:edit")
	@RequestMapping(value = "saveInputScore")
	public String saveInputScore(OpaTaskAppeal opaTaskAppeal, Model model, HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes) {
		opaTaskAppeal.setStatus(DictUtils.getDictValue("审核中", "opa_taskAppeal_status", ""));
		opaTaskAppealService.save(opaTaskAppeal);
		addMessage(redirectAttributes, "评分填报成功");
    	return "redirect:"+Global.getAdminPath()+"/opa/opaTaskAppeal/inputScore?&repage"; 
	}

	
	//评分审核 查询
	@RequiresPermissions("opa:opaTaskAppeal:audit:index")
	@RequestMapping(value = {"audit/index"})
	public String auditIndex(OpaTaskAppeal opaTaskAppeal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaTaskAppeal.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if(StringUtils.isNoneBlank(opaTaskAppeal.getPlanId())){
			opaTaskAppeal.setAuditorId(UserUtils.getUser().getId());
			opaTaskAppeal.setMethod(DictUtils.getDictValue("手工考核", "opa_item_method", ""));
			opaTaskAppeal.setIfNum(DictUtils.getDictValue("非数值", "opa_item_Num_type", ""));
			List<OpaTaskAppeal> list = opaTaskAppealService.findList(opaTaskAppeal);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaTaskAppealAuditIndex";
	}
	
	@RequiresPermissions("opa:opaTaskAppeal:audit:pass")
	@RequestMapping(value = "audit/pass")
	public String auditPass(OpaTaskAppeal opaTaskAppeal, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaTaskAppeal.setStatus(DictUtils.getDictValue("已审核", "opa_taskAppeal_status", ""));
		opaTaskAppealService.applyAuditPass(opaTaskAppeal);
		addMessage(redirectAttributes, "审核计划任务成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/audit/index?planId=" + opaTaskAppeal.getPlanId()
		+ "&repage";
	}
	
	@RequiresPermissions("opa:opaTaskAppeal:audit:return")
	@RequestMapping(value = "audit/return")
	public String auditReturn(OpaTaskAppeal opaTaskAppeal, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaTaskAppeal.setStatus(DictUtils.getDictValue("已退回", "opa_taskAppeal_status", ""));
		opaTaskAppealService.save(opaTaskAppeal);
		addMessage(redirectAttributes, "退回计划任务退回成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaTaskAppeal/audit/index?planId="+opaTaskAppeal.getPlanId()+"&repage";
	}
	
	@RequiresPermissions("opa:opaTaskAppeal:edit")
	@RequestMapping(value = "view")
	public String view(OpaTaskAppeal opaTaskAppeal, HttpServletRequest request,HttpServletResponse response, Model model) {
		Page<OpaTaskAppeal> page = opaTaskAppealService.findPage(new Page<OpaTaskAppeal>(request, response), opaTaskAppeal); 
		model.addAttribute("page", page);
		return "modules/opa/opaTaskAppealView";
	}
	
	@RequiresPermissions("opa:opaTaskAppeal:audit:edit")
	@RequestMapping(value = "audit/inputScore")
	public String saveInputAuditScore(OpaTaskAppeal opaTaskAppeal, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String reason = request.getParameter("reason");
		String inputScore = request.getParameter("inputScore");
		String remark = opaTaskAppeal.getRemarks();
		StringBuffer allReason = new StringBuffer();
		if(StringUtils.isNoneEmpty(remark)){
			allReason.append("\n").append(reason);
		}else{
			allReason.append(reason);
		}
		opaTaskAppeal.setScore(Double.valueOf(inputScore));
		opaTaskAppeal.setRemarks(allReason.toString());
		opaTaskAppeal.setStatus(DictUtils.getDictValue("已通过", "opa_taskAppeal_status", ""));
		opaTaskAppealService.save(opaTaskAppeal);
		addMessage(redirectAttributes, "考核任务打分成功，已通过");
		return "redirect:" + Global.getAdminPath() + "/opa/opaTaskAppeal/audit/index?planId=" + opaTaskAppeal.getPlanId()
				+ "&repage";
	}
	
	    @RequiresPermissions("opa:opaTaskAppeal:excute:edit")
		@RequestMapping(value = "inputScore")
		public String inputScore(OpaTaskAppeal opaTaskAppeal, Model model) {
			model.addAttribute("opaTaskAppeal", opaTaskAppeal);
			return "modules/opa/opaTaskAppealInputScore";
		}
	   
	     
	@RequiresPermissions("opa:opaTaskAppeal:excute:edit")
	@RequestMapping("/uploadFile")
	public String fileUpload(@RequestParam("file") MultipartFile file, OpaTaskAppeal opaTaskAppeal,
			HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
		// 获得原始文件名
		String fileName = file.getOriginalFilename();
		System.out.println("原始文件名:" + fileName);
		// 新文件名
		// 上传位置
		String newFileName = UUID.randomUUID() + fileName;
		String sufFix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		System.out.println("原始文件后缀名:" + sufFix);
		String basePath = Global.getConfig("upload.basePath");
		StringBuffer attachedPath = new StringBuffer();
		String path = new String();
		if (!file.isEmpty()) {
			try {
				if (!Global.getConfig("upload.fileType").contains(sufFix)) {
					addMessage(model, "文件上传格式不正确，多个文件请压缩后上传");
					return "modules/opa/opaTaskAppealInputScore";
				}
				OpaPlan opaPlan = opaPlanService.get(opaTaskAppeal.getPlanId());
				String planName = opaPlan.getName();
				String officeName = UserUtils.getUser().getOffice().getName();
				attachedPath.append(basePath).append(DateUtils.getYear()).append("/").append(planName).append("/")
						.append(officeName).append(opaTaskAppeal.getName()).append(".").append(sufFix);
				path = attachedPath.toString(); // 设定文件保存的目录
				File f = new File(attachedPath.toString());
				if (!f.exists())
					f.mkdirs();
				FileOutputStream fos = new FileOutputStream(path + newFileName);
				InputStream in = file.getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) {
					fos.write(b);
				}
				fos.close();
				in.close();
			} catch (Exception e) {
				if (e instanceof MaxUploadSizeExceededException) {
					addMessage(model, "上传文件应小于 " + getFileMB(((MaxUploadSizeExceededException) e).getMaxUploadSize()));
				} else {
					addMessage(model, "未知错误: " + e.getMessage());
				}
				return "modules/opa/opaTaskAppealInputScore";
			}
		} else {
			addMessage(model, "请选择文件");
			return "modules/opa/opaPlanTaskAppealInputScore";
		}
		System.out.println("上传图片到:" + attachedPath.toString());
		opaTaskAppeal.setId(request.getParameter("id"));
		opaTaskAppeal.setAttachedPath(attachedPath.toString());
		opaTaskAppealService.save(opaTaskAppeal);
		model.addAttribute("opaTaskAppeal", opaTaskAppeal);
		addMessage(model, "文件上传成功");
		return "modules/opa/opaTaskAppealInputScore";
	}

	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, HttpServletRequest request) {
		if (e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException) {
			request.setAttribute("message", "上传文件应小于 " + getFileMB(((MaxUploadSizeExceededException) e).getMaxUploadSize()));
		}
		return "redirect:" + Global.getAdminPath() + "/opa/opaTaskAppeal/view?&repage";
	}

	private String getFileKB(long byteFile) {
		if (byteFile == 0)
			return "0KB";
		long kb = 1024;
		return "" + byteFile / kb + "KB";
	}

	private String getFileMB(long byteFile) {
		if (byteFile == 0)
			return "0MB";
		long mb = 1024 * 1024;
		return "" + byteFile / mb + "MB";
	}
	
	/***
	 * 实现文件下载
	 * 
	 * @throws IOException
	 ***/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(OpaTaskAppeal opaTaskAppeal, HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException {
		File file = new File(opaTaskAppeal.getAttachedPath());
		String fileName = new String(file.getName().getBytes("utf-8"),"iso8859-1");
		// 用来封装响应头信息
		HttpHeaders responseHeaders = new HttpHeaders();
		// 下载的附件的类型
		responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 下载的附件的名称
		responseHeaders.setContentDispositionFormData("attachment", fileName);

		/**
		 * arg1:需要响应到客户端的数据 arg2:设置本次请求的响应头 arg3:响应到客户端的状态码
		 ***/
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), responseHeaders, HttpStatus.CREATED);
	}
}