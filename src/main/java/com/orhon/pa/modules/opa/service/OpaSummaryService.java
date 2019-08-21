/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.mapper.JsonMapper;
import com.orhon.pa.common.persistence.AutoHeader;
import com.orhon.pa.common.service.TreeService;
import com.orhon.pa.modules.opa.dao.OpaItemDao;
import com.orhon.pa.modules.opa.dao.OpaOverviewDao;
import com.orhon.pa.modules.opa.dao.OpaPlanDao;
import com.orhon.pa.modules.opa.dao.OpaPlanOfficeDao;
import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaOverview;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.sys.entity.Office;
import com.orhon.pa.modules.sys.utils.DictUtils;

/**
 * 指标管理模块Service
 * 
 * @author Shawn
 * @version 2017-04-18
 */
@Service
@Transactional(readOnly = true)
public class OpaSummaryService extends TreeService<OpaItemDao, OpaItem> {
	@Autowired
	OpaPlanTaskDao opaPlanTaskDao;
	@Autowired
	OpaSchemeItemDao opaSchemeItemDao;
	@Autowired
	OpaPlanDao opaPlanDao;
	@Autowired
	OpaPlanOfficeDao opaPlanOfficeDao;
	@Autowired
	OpaSchemeDao opaSchemeDao;
	@Autowired
	OpaOverviewDao opaOverviewDao;
	@Autowired
	OpaOverviewService opaOverviewService;
	@Autowired
	OpaItemService opaItemService;

	
	
	@Transactional(readOnly=true)
	public Map<String, Object> getHeadAndDataMap(String Identificationsymbo) {
		//OpaSchemeItem itemParam = new OpaSchemeItem();
		//int headLevel = opaSchemeItemDao.getHeadLevel(itemParam);
		//List<OpaSchemeItem> parentItemList1=opaSchemeItemDao.getFindList(itemParam);//通过父id和方案id查询当前方案和指标
		List<OpaSchemeItem> opaSchemeItemList = opaSchemeItemDao.getSummaryList(Identificationsymbo);
		Map<String, Object> headAndData= new HashMap<String, Object>();
		List<Object> headList = new ArrayList<Object>();
		int treeCode = 10;                   //表头树形编码,根据编码进行上下级关系的解析，子表头的编码必须包含父表头的全部编码每个表头的编码不可重复。
		for (OpaSchemeItem opaItem : opaSchemeItemList) {
			treeCode++;
			AutoHeader dept = new AutoHeader();
			dept.setChecked("1");
			dept.setCssstyle("");             //  表头自定义css样式,选填字段,表头的style属性会对应添加指定的css内容值。
			dept.setHeadDesc("");            //表头描述:选填字段，如果开启表头描述功能，则表头描述的内容会取此属性的值。
			dept.setId("DEPT000");
			dept.setIsleaf("Y");             //该节点是否为叶子节点，即直接与数据接触的节点，也就是最底层的表头,本属性值为'Y'的数据将会被认定为是叶子节点。
			dept.setIsopen("0");
			if(opaItem.getLevel()!=1) {
				dept.setLevel(String.valueOf(1));//startlay:数据从哪层开始计入表头,默认为1，该表头节点的层级(从上至下)，插件会综合startlay和level对表头数据进行筛选
			}else {
				dept.setLevel(String.valueOf(opaItem.getLevel()));//startlay:数据从哪层开始计入表头,默认为1，该表头节点的层级(从上至下)，插件会综合startlay和level对表头数据进行筛选
			}
			
			dept.setName("被考核部门");     //表头节点名称,显示在表头单元格里的节点名称。
			dept.setPid("");
			dept.setReportColumnName("rep_org_name");//对应数据json的属性名,与dataJson相关，叶子节点的表头需要书写该属性，其他则不需要，插件会根据属性名将数据和表头进行对应书写。
			dept.setReportColumnType("String");
			dept.setTransanalyzz("");
			dept.setSts("Y");
			dept.setTreeCode(String.valueOf(treeCode));
			dept.setType("opa");
			headList.add(dept);
			treeCode++;
			for(OpaSchemeItem schemeItem:opaSchemeItemList) {
				//if(	opaItem.getId().equals(schemeItem.getItemId())) {
				AutoHeader header = new AutoHeader();
				header.setChecked("1");
				header.setCssstyle("");
				if(schemeItem.getType().equals(DictUtils.getDictValue("个性指标", "opa_item_type", ""))){
					header.setHeadDesc(schemeItem.getName());
					header.setName(schemeItem.getName());
				}else{
					header.setHeadDesc(opaItem.getName() + " (" + schemeItem.getValue() + " 分)");
					header.setName(opaItem.getName() + " (" + schemeItem.getValue() + " 分)");
				}
				header.setId(schemeItem.getItemId());
				List<OpaSchemeItem> childList = opaSchemeItemDao.findChild(schemeItem);//给部门下发的所有指标
				if(childList!=null&&!childList.isEmpty()){
					header.setIsleaf("N");
					header.setReportColumnName("");
				}else{
					header.setIsleaf("Y");
					header.setReportColumnName(schemeItem.getItemId());
				}
				header.setIsopen("0");
				if(opaItem.getLevel()!=1) {
					header.setLevel(String.valueOf(1));
				}else {
					header.setLevel(String.valueOf((opaItem.getLevel())));
				}
				
				header.setPid(schemeItem.getItemParentId());
				header.setReportColumnType("Number");
				header.setTransanalyzz("");
				header.setSts("Y");
				header.setTreeCode(String.valueOf(treeCode));
				header.setType("opa");
				headList.add(header);
				this.findItemChild(schemeItem, headList, treeCode);
			}
			
		//	}

		}
		//查询手动录入死表头
		/*List<OpaSchemeItem> dieList = opaSchemeItemDao.findImmobilization("ca39eb8cbbee43cca4a05dc80b679096");
		for (OpaSchemeItem parentItem : dieList) {
			AutoHeader dept = new AutoHeader();
			dept.setChecked("1");
			dept.setCssstyle("");             //  表头自定义css样式,选填字段,表头的style属性会对应添加指定的css内容值。
			dept.setHeadDesc(parentItem.getName());            //表头描述:选填字段，如果开启表头描述功能，则表头描述的内容会取此属性的值。
			dept.setId(parentItem.getId());
			dept.setIsleaf("Y");             //该节点是否为叶子节点，即直接与数据接触的节点，也就是最底层的表头,本属性值为'Y'的数据将会被认定为是叶子节点。
			dept.setIsopen("0");
			if(parentItem.getLevel()!=1) {
				dept.setLevel(String.valueOf((1)));//startlay:数据从哪层开始计入表头,默认为1，该表头节点的层级(从上至下)，插件会综合startlay和level对表头数据进行筛选
			}else {
				dept.setLevel(String.valueOf((parentItem.getLevel())));//startlay:数据从哪层开始计入表头,默认为1，该表头节点的层级(从上至下)，插件会综合startlay和level对表头数据进行筛选
			}
			dept.setName(parentItem.getName());     //表头节点名称,显示在表头单元格里的节点名称。
			dept.setPid("");
			dept.setReportColumnName(parentItem.getId());//对应数据json的属性名,与dataJson相关，叶子节点的表头需要书写该属性，其他则不需要，插件会根据属性名将数据和表头进行对应书写。
			dept.setReportColumnType("String");
			dept.setTransanalyzz("");
			dept.setSts("Y");
			dept.setTreeCode(String.valueOf(++treeCode));
			dept.setType("opa");
			headList.add(dept);
		}
		// 手动录入一栏 《操作》栏数据
		/*List<OpaSchemeItem> buttons = opaSchemeItemDao.findButton("ca39eb8cbbee43cca4a05dc80b679096");
		for (OpaSchemeItem parentItem : buttons) {
			AutoHeader dept = new AutoHeader();
			dept.setChecked("1");
			dept.setCssstyle("");             //  表头自定义css样式,选填字段,表头的style属性会对应添加指定的css内容值。
			dept.setHeadDesc(parentItem.getName());            //表头描述:选填字段，如果开启表头描述功能，则表头描述的内容会取此属性的值。
			dept.setId(parentItem.getId());
			dept.setIsleaf("Y");             //该节点是否为叶子节点，即直接与数据接触的节点，也就是最底层的表头,本属性值为'Y'的数据将会被认定为是叶子节点。
			dept.setIsopen("0");
			dept.setLevel(String.valueOf((parentItem.getLevel())));//startlay:数据从哪层开始计入表头,默认为1，该表头节点的层级(从上至下)，插件会综合startlay和level对表头数据进行筛选
			dept.setName(parentItem.getName());     //表头节点名称,显示在表头单元格里的节点名称。
			dept.setPid("");
			dept.setReportColumnName(parentItem.getId());//对应数据json的属性名,与dataJson相关，叶子节点的表头需要书写该属性，其他则不需要，插件会根据属性名将数据和表头进行对应书写。
			dept.setReportColumnType("String");
			dept.setTransanalyzz("");
			dept.setSts("Y");
			dept.setTreeCode(String.valueOf(++treeCode));
			dept.setType("opa");
			headList.add(dept);
		}*/
		headAndData.put("header", JsonMapper.toJsonString(headList));

		//获取数据集
		List<Object> dataList = new ArrayList<Object>();
		List<Object> deptList = new ArrayList<Object>();
		//OpaPlanOffice officeParam = new OpaPlanOffice();
		OpaPlanTask taskParam = new OpaPlanTask();
		//taskParam.setPlanId(planId);
		taskParam.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
		//officeParam.setPlanId(planId);
		List<OpaPlanOffice> officeList =opaPlanOfficeDao.findList1(Identificationsymbo);
		for(OpaPlanOffice office : officeList){
			Office off = new Office();
			off.setId(office.getOfficeId());
			taskParam.setOffice(off);
			List<OpaPlanTask>taskList=opaPlanTaskDao.auditorScore(office.getOffice().getId());
			Map<String,String> deptMap = new HashMap<String,String>();
			deptMap.put("id", office.getOffice().getId());
			deptMap.put("pid", "");
			deptMap.put("name", office.getOffice().getName());
			deptMap.put("open", "false");
			deptList.add(deptMap);
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("rep_org_code", office.getOffice().getId());
			dataMap.put("rep_org_name", office.getOffice().getName());//数据行单位的标示字段名，标明该行数据归属的说明，将会横向成为统计表格的表头
			dataMap.put("id", office.getOffice().getId());
			dataMap.put("main_id", "");
			dataMap.put("report_id", "");
			for(OpaPlanTask task : taskList){
				String score = task.getScore() == null? "0": String.valueOf(task.getScore());
				dataMap.put(task.getItemId(), score);
			}
			
			
			//查询手动录入死表头数据  
			/*OpaOverview dieData = opaSchemeItemDao.findImmobilizationContent(planId);
			// 折算得分(年考核成绩x50%+日常考核成绩x50%)x80%
			Double YearResult = new Double(dieData.getYearResult());
			Double daiScore = new Double(dieData.getDaiScore());
			String convertScore=Double.toString((YearResult*0.5+daiScore*0.5)*0.8);
			//dataMap.put(dieList.get(0).getId(), dieData.getLeadingScore());// 年底考核成绩
			dataMap.put(dieList.get(0).getId(), dieData.getDaiScore());
			dataMap.put(dieList.get(1).getId(), dieData.getYearResult());
			dataMap.put(dieList.get(2).getId(), convertScore);
			dataMap.put(dieList.get(3).getId(), dieData.getLeadingScore());	
			dataMap.put(dieList.get(4).getId(), dieData.getCountyScore());
			dataMap.put(dieList.get(5).getId(), dieData.getPeerReview());
			dataMap.put(dieList.get(6).getId(), dieData.getLeadershipAssessment());
			dataMap.put(dieList.get(7).getId(), dieData.getServiceRating());
			dataMap.put(dieList.get(8).getId(), dieData.getAssessmentScore());
			dataMap.put(dieList.get(9).getId(), dieData.getOverallScore());
			//全年得分(折算得分+综合评价)/1.2
			Double annual=new Double(convertScore);
			Double over=new Double(dieData.getOverallScore());
			String annualScore=Double.toString((annual+over)/1.2);
			Double cny = Double.parseDouble(annualScore);
			 DecimalFormat df = new DecimalFormat("0.00");
			 String aScore = df.format(cny);
			dataMap.put(dieList.get(10).getId(),aScore);
			//遍历死数据，map key   id关联表头id
			for (OpaSchemeItem opaSchemeItem : dieList) {
				dataMap.put(opaSchemeItem.getId(), "123");
			}
			for (OpaSchemeItem parentItem : buttons) {
				//dataMap.put(parentItem.getId(), "<button id='enter' onclick='luru(\"http://localhost:8088/opa/a/opa/view/table\")' class='btn btn-primary btn-sx' data-toggle='modal' data-target='#myModal'>录入</button>");
			}
			dataList.add(dataMap);*/
		}
		
		headAndData.put("data", JsonMapper.toJsonString(dataList));
		//headAndData.put("headLevel", headLevel+1);
		//headAndData.put("startLevel", headLevel+1);
		headAndData.put("dept", JsonMapper.toJsonString(deptList));
		return headAndData;
	}

	private void findItemChild(OpaSchemeItem item, List<Object> headList, int treeCode) {
		int childTreeCode = treeCode * 100;
		List<OpaSchemeItem> childList = opaSchemeItemDao.findChild(item);
		if (childList != null) {
			for (int i = 0; i < childList.size(); i++) {
				OpaSchemeItem child = childList.get(i);
				childTreeCode++;
				AutoHeader childHeader = new AutoHeader();
				childHeader.setChecked("1");
				childHeader.setCssstyle("");
				if(child.getType().equals(DictUtils.getDictValue("个性指标", "opa_item_type", ""))){
					childHeader.setHeadDesc(child.getName());
					childHeader.setName(child.getName());
				}else{
					childHeader.setHeadDesc(child.getName() + " (" + child.getValue() + " 分)");
					childHeader.setName(child.getName() + " (" + child.getValue() + " 分)");
				}
				childHeader.setId(child.getItemId());
				List<OpaSchemeItem> hasChild = opaSchemeItemDao.findChild(child);//空
				if(hasChild!=null&&!hasChild.isEmpty()){
					childHeader.setIsleaf("N");
					childHeader.setReportColumnName("1");
				}else{
					childHeader.setIsleaf("Y");
					childHeader.setReportColumnName(child.getItemId());
				}
				childHeader.setIsopen("0");
				childHeader.setLevel(String.valueOf((child.getLevel())));
				childHeader.setPid(child.getItemParentId());
				childHeader.setReportColumnType("Number");
				childHeader.setTransanalyzz("");
				childHeader.setSts("Y");
				childHeader.setTreeCode(String.valueOf(childTreeCode));
				childHeader.setType("opa");
				headList.add(childHeader);
				this.findItemChild(child, headList, childTreeCode);
				if(i==childList.size()-1){
					childTreeCode++;
					AutoHeader sumHeader = new AutoHeader();
					sumHeader.setChecked("1");
					sumHeader.setCssstyle("");
					sumHeader.setHeadDesc(item.getName() + " (" + item.getValue() + " 分)");
					sumHeader.setId("SUMMARY"+childTreeCode);
					sumHeader.setIsleaf("Y");
					sumHeader.setIsopen("0");
					sumHeader.setLevel(String.valueOf((child.getLevel())));
					sumHeader.setName("合计 (" + item.getValue() + " 分)");
					sumHeader.setPid(child.getItemParentId());
					sumHeader.setReportColumnName(child.getItemParentId());
					sumHeader.setReportColumnType("Number");
					sumHeader.setTransanalyzz("");
					sumHeader.setSts("Y");
					sumHeader.setTreeCode(String.valueOf(childTreeCode));
					sumHeader.setType("opa");
					headList.add(sumHeader);
				}
				
			}
		}
	}
	
	
	
	
	@Transactional(readOnly=true)
	public Map<String, Object> getHeadAndDataScoreMap(String planId) {
		OpaOverview opaOverview = opaOverviewDao.getOverview(planId);
		Map<String, Object> headAndData= new HashMap<String, Object>();
		List<Object> headList1 = new ArrayList<Object>();
		//List<OpaOverview> opaOverviewList = opaOverviewDao.findList(opaOverview);
		int treeCode = 10;                   //表头树形编码,根据编码进行上下级关系的解析，子表头的编码必须包含父表头的全部编码每个表头的编码不可重复。
			treeCode++;
			AutoHeader dept1 = new AutoHeader();
			dept1.setChecked("1");
			dept1.setCssstyle("");             //  表头自定义ass样式,选填字段,表头的style属性会对应添加指定的css内容值。
			dept1.setHeadDesc("");            //表头描述:选填字段，如果开启表头描述功能，则表头描述的内容会取此属性的值。
			dept1.setId("DEPT001");
			dept1.setIsleaf("Y");             //该节点是否为叶子节点，即直接与数据接触的节点，也就是最底层的表头,本属性值为'Y'的数据将会被认定为是叶子节点。
			dept1.setIsopen("0");
			dept1.setLevel(String.valueOf((opaOverview.getLevel())));//startlay:数据从哪层开始计入表头,默认为1，该表头节点的层级(从上至下)，插件会综合startlay和level对表头数据进行筛选
			dept1.setName("年终考核成绩");     //表头节点名称,显示在表头单元格里的节点名称。
			dept1.setPid("");
			dept1.setReportColumnName("rep_org_name");//对应数据json的属性名,与dataJson相关，叶子节点的表头需要书写该属性，其他则不需要，插件会根据属性名将数据和表头进行对应书写。
			dept1.setReportColumnType("String");
			dept1.setTransanalyzz("");
			dept1.setSts("Y");
			dept1.setTreeCode(String.valueOf(treeCode));
			dept1.setType("opa");
			headList1.add(dept1);
			treeCode++;
			AutoHeader header1 = new AutoHeader();
			header1.setChecked("1");
			header1.setCssstyle("");
		    header1.setHeadDesc("日常考核成绩"+"(30分)");
			header1.setId(opaOverview.getId());
			header1.setIsleaf("N");
			header1.setReportColumnName("");
			header1.setIsopen("0");
			header1.setLevel(String.valueOf((opaOverview.getLevel())));
			header1.setReportColumnType("Number");
			header1.setTransanalyzz("");
			header1.setSts("Y");
			header1.setTreeCode(String.valueOf(treeCode));
			header1.setType("opa");
			headList1.add(header1);
			//this.overviewChild(opaOverview, headList1, treeCode);
		headAndData.put("header1", JsonMapper.toJsonString(headList1));
		List<Object> dataList1 = new ArrayList<Object>();
		List<Object> deptList1 = new ArrayList<Object>();
		List<OpaOverview> officeList = opaOverviewDao.findList(opaOverview);
		for(OpaOverview office : officeList){
			Office off = new Office();
			off.setId(office.getOffice().getId());
			Map<String,String> deptMap1 = new HashMap<String,String>();
			deptMap1.put("id", office.getOffice().getId());
			deptMap1.put("pid", "");
			deptMap1.put("name", office.getOffice().getName());
			deptMap1.put("open", "false");
			deptList1.add(deptMap1);
			Map<String,String> dataMap1 = new HashMap<String,String>();
			dataMap1.put("rep_org_code", office.getOffice().getId());
			dataMap1.put("rep_org_name", office.getOffice().getName());//数据行单位的标示字段名，标明该行数据归属的说明，将会横向成为统计表格的表头
			dataMap1.put("id", office.getOffice().getId());
			dataMap1.put("main_id", "");
			dataMap1.put("report_id", "");
			dataList1.add(dataMap1);
		}
		headAndData.put("data1", JsonMapper.toJsonString(dataList1));
		headAndData.put("dept1", JsonMapper.toJsonString(deptList1));
		return headAndData;
	}
}