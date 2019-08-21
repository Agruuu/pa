<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>计划任务管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if($("#planId").val()!=""){
			showView();
		}
	});

	
	/**
	 * 初始化表格方法
	 */
	function initTable(tableId,headstr,bodystr){
   /*如果表头数据里的属性名和默认的并不相同，那么便可以通过这个入参进行更改。例如层级属性名称为lvl，表头节点名称属性为viewname。
         那么，在调用init方法的时候传入**columConfig**入参即可进行修改：*/
	    $.fn.autoHeader.init({
	        laynum:${headAndData.headLevel}, /*默认的表头层数 */
	        headJson:headstr,
	        dataJson:bodystr,
	        tableid:'tabled',
	        //caption:'报表展示标题',
	        outspanid:'datev',
	        startlay:0,
	        datamulti:30,
	        debug:false,
	        outspanid:'datev',
	        needsort:true,//排序打开
	        /*表头描述配置 */
	        descConfig:{
	            desc_type:'div',
	            desc_th_class:'csshhn',//显示方式：可选方式有div、title。div在div处显示，title则会在th内容外包一层span，span的title置为描述值。
	            enable:0,
	            position:'fly',//div显示位置 有absolute和fly两种状态 absolute：div的位置是绝对值，以x和y作为起点。fly：div的位置以鼠标作为起点，x和y为偏移量
	            pos_x:-200,
	            pos_y:-200,
	            showevent:'click'
	        },
	        thstyle:false,
	        cssConfig:{
	            table:' table-hover  table-striped table-bordered table-responsive', 
	            tr:''//表头tr的class属性
	        },
	        colConfig:{
	            transcomp:'transanalyzz'
	        },
	        
	        sort:{
	            enable:1,//是否开启
	            rank:1,//当前实时排名是否开启
	            theme : "bootstrap",
	            debug:0,
	            
	        }
	    });
	}
	var treejson_ = ${headAndData.dept};
	var tree=JSON.stringify(treejson_);
	console.log("treejson_="+tree)
	var dataer_ = ${headAndData.data};
	var dataer=JSON.stringify(dataer_);
	console.log("dataer_="+dataer)
	var header_ = ${headAndData.header};
	var header=JSON.stringify(header_);
    console.log("header_="+header)     
	 function luru(url){
			return openWin(url);
		}
		//打开新窗口
		 function openWin(href){
			top.$.jBox.open("iframe:"+href+"", "",1000,400,
				{
					buttons:{"关闭":true}, 
					closed:function(){
						location.reload();
					},
					submit:function(v, h, f){
					}, 
					loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
			resetTip();
			return false;
		}
	</script>
<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
<!--[if lt IE 9]>
    <script src="${ctxStatic}/autoHeader/h5lib/html5shiv.js"></script>
    <script src="${ctxStatic}/autoHeader/h5lib/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/autoHeader/src/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/autoHeader/src/css/ztree/zTreeStyle.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/autoHeader/src/css/style.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/autoHeader/src/css/theme.bootstrap.css">
</head>
<body>




	<div class="container-fluid"  id="refdiv">
		<div class="col-xs-2 col-sm-2 col-md-2 ">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">结果中筛选</h3>
				</div>
				<div class="panel-body">
					<div class="cont">
						<ul id="treeDemo_org" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>

		<div class="col-xs-10 col-sm-10 col-md-10 ">
			<div class="panel panel-primary" id="listdiv">
				<div class="panel-heading">
					<h3 class="panel-title">选择指标</h3>
				</div>
				<div class="panel-body">

					<div class="cont">
						<ul class="list_main">
							<form:form id="searchForm" modelAttribute="opaPlanTask"
								action="${ctx}/opa/opaSummary/all/index" method="post"
								class="breadcrumb form-search">
							    <ul class="ul-form">
								        <li>
								          <label>指标名称：</label> 
									        <form:select path="planId" class="input-xlarge">
										      <form:options items="${schemeList}" itemLabel="label" itemValue="value" htmlEscape="false" />
										    </form:select> 
										     <select id="Identificationsymbo" name="Identificationsymbo">
										      <option value="0">请选择</option> 
										      <option value="1">2018年度政府部门领导班子实际考核</option>
										      <option value="2">2018年县级领导所在单位领导班子实际考核</option>
										      <option value="3">2018年党群部门领导班子实际考核</option>
										      <option value="4">2018年协管单位领导班子实际考核</option>
										      <option value="5">2018年事业单位领导班子实际考核</option>
										      <option value="6">2018年各镇领导班子实际考核</option>
										      
										    </select> 
										</li>
									<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
								</ul>
							</form:form>
						</ul>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" style="display: none" id="viewdiv">
				<div class="panel-heading">
					<h3 class="panel-title">
						总体功能集成展示界面<span class="backtolist" id="backlist"> &lt; 返回前页</span>
					</h3>
				</div>
				<div class="panel-body">
					<div id="buttons">
						<div class="btn-group" role="group" aria-label="...">
							<button type="button" class="btn btn-primary success"
								id="generator">
								<span class="glyphicon glyphicon-filter" aria-hidden="true"></span>&nbsp;筛选
							</button>
						</div>
						<div class="btn-group" role="group" aria-label="...">
							<button type="button" id="headerchoose" class="btn btn-success ">
								<span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>&nbsp;选择表头
							</button>
						</div>

						<div class="btn-group" role="group" aria-label="...">
							<!-- <button type="button" class="btn btn-info" id="compare_tran">比对预设值</button> -->
							<button type="button" class="btn btn-info" id="compare_avg">平均值</button>
							<button type="button" class="btn btn-info" id="compare_max">最大值</button>
							<button type="button" class="btn btn-info" id="compare_min">最小值</button>
							<!-- <button id='enter' class='btn btn-primary btn-sx' data-toggle='modal' data-target='#myModal'>录入</button> -->
							<!-- <input id="mulu" class="btn btn-primary" type="button"
								onclick="luru()" value="打分" /> -->

							<!-- <input type="text" name="balabala" style="width: 200px"/> -->
						</div>
						<div class="btn-group" role="group" aria-label="...">
							<!-- <button type="button" class="btn btn-info" id="pie_chart">饼状图</button> -->
							<!--                         <button type="button" class="btn btn-info" id="pie_chart_ddd">3D饼状图</button>
                        <button type="button" class="btn btn-info" id="column_chart">柱状图</button>
                        <button type="button" class="btn btn-info" id="line_chart">折线图</button>
                        <button type="button" class="btn btn-info" id="bar_chart">条形图</button> -->

						</div>

						<div class="btn-group" role="group" aria-label="...">
							<a type="button" class="btn btn-success" id="export_excel">
								<span class="glyphicon glyphicon-export" aria-hidden="true"></span>&nbsp;导出Excel
							</a>
						</div>
						<div class="btn-group" role="group" aria-label="...">
							<button type="button" class="btn btn-primary" id="maxbt">
								<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>&nbsp;最大化
							</button>
						</div>
						<div class="btn-group" role="group" aria-label="...">
							<button type="button" class="btn btn-primary" id="reset"
								onclick="history.go(0);">
								<span class="glyphicon glyphicon-recycle" aria-hidden="true"></span>&nbsp;重置
							</button>
						</div>

						<div class="panel panel-info" id="headtre" style="display: none;">
							<div class="panel-heading">
								<h3 class="panel-title">表头选择</h3>
							</div>
							<div class="panel-body">
								<div class="cont">
									<ul id="header_tree" class="ztree"></ul>
								</div>
							</div>
						</div>
					</div>
					<span id="datev"></span>
					<div id="containdivcnt">
						<table id="tabledcnt">
						</table>
					</div>
					<div id="containdiv">
						<div id="tablepositonid"></div>
						<div id="analyzz">
							<br />
							<div id="container" style="max-width: 2000px; height: 400px; display: none;"></div>
							<!-- 显示表头和数据的table -->
							<table id="tabled"></table>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div class="container-fluid" id="alldiv" style="display: none;">
		<div style="padding-top: 40px;">
			<span id="dtiyp"></span>
		</div>
	</div>
	<script src="${ctxStatic}/autoHeader/src/js/jquery.min.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/bootstrap.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/jquery.autoHeader.all.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/all.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/jquery.ztree.all-3.5.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/table2excel.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/highcharts.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/highcharts-3d.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/exporting.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/jquery.tablesorter.js"></script>
	<script
		src="${ctxStatic}/autoHeader/src/js/jquery.tablesorter.widgets.js"></script>
</body>
</html>