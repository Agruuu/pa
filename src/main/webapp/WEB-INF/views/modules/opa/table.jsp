<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%-- <%@include file="/WEB-INF/views/include/treetable.jsp" %> --%>

<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<!-- <link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous"> -->
	
<style>
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    padding: 8px;
    line-height: 1.0;
    vertical-align: top;
    border-top: 1px solid #ddd;
    border-left: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
    border-right: 1px solid #ddd;
}

</style>
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
</head>
<body onload="init()">
	<table class="table">
		<thead>
			<tr id="theader">

			</tr>
		</thead>
		<form id="fm">
			<tbody id="tbody">

			</tbody>
		</form>
	</table>


	<script type="text/javascript">
		function init() {
			$.ajax({
				url : "http://localhost:8088/opa/a/opa/opaSummary/tableheader",
				type : "get",
				data : "",
				dataType : "JSON",
				success : function(data) {
					var str = "<table><td>被考核部门</td></table>";
					for (var i = 0; i < data.length; i++) {
						str += "<td>" + data[i].name + "</td>";
					}
					str += "<td>操作</td>";
				 	/*  console.log(str);  */ 
					$("#theader").html(str);
				},
				error : function() {
				}
			});
			

			$.ajax({
						url : "http://localhost:8088/opa/a/opa/opaSummary/apply/batch/view",
						type : "get",
						data : "",
						dataType : "JSON",
						success : function(data) {
							data1 = data.data;
							data1 = JSON.parse(data1);
							console.log(data1)
							var str = "";
							var stro = 1;
							for (var i = 0; i < data1.length; i++) {
								 /* console.log(data1[i].rep_org_name); */ 
								str += "<tr>" + "<td>" + data1[i].rep_org_name + "</td>";
								for (var j = 1; j <= 9; j++) {
									str += "<td><input type='text' id='text"+i+j+"' /></td>";
								}
								str += "<td><button id='"+(i+1)+"' onclick='saveOrUpData(this)'>保存</button></td>";
								str += "</tr>";
							}
							$("#tbody").html(str);
							
						},
						error : function() {

						}
				});
		};
		
		
		function saveOrUpData(e){
			var bid=$(e).attr("id");
			var tr=$("#sp"+bid).val();
			var i=bid%10;
			var j=parseInt(bid/10);
			
			
			var yearResult = $("#text"+j+1+"").val();                   /* 年底考核成绩 */
			var leadingScore  = $("#text"+j+2+"").val();		         /* 领导评价(50分) */
			var countyScore = $("#text"+j+3+"").val();		              /* 县级领导评分(30分) */
			var peerReview = $("#text"+j+4+"").val();		            /* 单位互评(30分) */
			var leadershipAssessment = $("#text"+j+5+"").val();	        /* 领导班子测评(30分) */
			var serviceRating = $("#text"+j+6+"").val();		         /* 服务对象评分(40分) */
			var assessmentScore = $("#text"+j+7+"").val();	             /* 考核组评分(20分) */
			var overallScore = $("#text"+j+8+"").val();	                /* 综合评分 */
			var daiScore = $("#text"+j+9+"").val();		               /* // 日常考核成绩 */
			var office =  tr;		
			var level = 1;	
			var alertMsg="";
			var sendData="yearResult="+yearResult+"&leadingScore="+leadingScore+"&countyScore="+
			countyScore+"&peerReview="+peerReview+"&leadershipAssessment="+leadershipAssessment+"&serviceRating="+serviceRating+
			"&assessmentScore="+assessmentScore+"&overallScore="+overallScore+"&daiScore="+daiScore;
			$.ajax({
				url:"http://localhost:8088/opa/a/opa/opaOverview/save",
				type:"POST",
				data:sendData,
				dataType:"",
				success:function(data){
					/* location.href="http://localhost:8088/opa/a/opa/opaSummary/all/index" */
						 if(data==true){
							 showTip('提交成功！');
						}else{
							showTip('提交失败！');
						}
					
					window.location.href = "http://localhost:8088/opa/a/opa/opaSummary/all/index";

				}
			});
		}
	
	</script>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
	<thead>
			<tr>
				<!-- <th>名称</th> -->
	       </tr>
	   </thead>
	   <tbody id="treeTableList"></tbody>			
	</table>
	<script type="text/template" id="treeTableTpl">
    </script>
	<script src="${ctxStatic}/autoHeader/src/js/jquery.min.js"></script>
	<script src="${ctxStatic}/autoHeader/src/js/bootstrap.js"></script>
</body>
</html>