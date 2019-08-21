<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>计划任务管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treetable.jsp"%>
<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [], officeIds = [], officeIdss = [], officeNames = [], rootIdss = [], officeNotExsist = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].itemId);
			}
			ids = ',' + ids.join(',') + ',';
			console.log(ids);
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].itemParentId+',') == -1){
						rootIds.push(data[i].itemParentId);
						officeNotExsist.push(data[i].auditorId);
						rootIdss.push(data[i].itemParentId + "," + data[i].auditorId + "," + data[i].auditorName + "," + data[i].name);
				}
				var ele = data[i].auditorId;
				var itemPid = data[i].itemParentId;
		        if(officeIds.indexOf(ele) == -1){
		    	    officeIds.push(ele);
		    	    officeIdss.push(ele + "," + itemPid);
		        }
		        var ele2 = data[i].auditorName;
		        if(officeNames.indexOf(ele2) == -1){
		        	officeNames.push(ele2);
		        }
			}
			console.log("rootIds:" + rootIds);
			console.log("officeIds:" + officeIds);
			console.log("officeNames:" + officeNames);
			if(officeNotExsist[0] == undefined || officeNotExsist[0] == null || officeNotExsist[0] == 'undefined' || officeNotExsist.length == 0 || officeNotExsist == null) {
				for (var i=0; i<rootIdss.length; i++){
					addRow("#treeTableList", tpl, data, rootIdss[i], true);
				}
			} else {
				for (var i=0; i<officeIds.length; i++){
					if(rootIds.indexOf(officeIdss[i].split(",")[1]) > -1) {
						addRowsDes("#treeTableList", tpl, data, officeIdss[i], true);
					} else {
						addRowSec("#treeTableList", tpl, data, officeIdss[i], true, officeNotExsist);
					}
				}
			}
			$("#treeTable").treeTable({expandLevel : 10});
		});
		
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				console.log("fns:jsGetVal:" + ${fns:jsGetVal('row.itemParentId')});
				console.log("pid:" + pid);
				if ((${fns:jsGetVal('row.itemParentId')}) == pid.split(",")[0]){
					if(row.auditorId == null || row.auditorId == pid.split(",")[1]) {
						
					
					$(list).append(Mustache.render(tpl, {
						dict:{
							
							type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
							ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
							method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
							status: getDictLabel(${fns:toJson(fns:getDictList('opa_schemeItem_status'))}, row.status), blank123:0}, 
							pid: (root?0:pid.split(",")[0]), 
							row: row,
							btnCol: btnRender(row)
							
					}));
					addRow(list, tpl, data, row.itemId+","+row.auditorId);
					}
				}
			}
		}
		function addRowsDes(list, tpl, data, pid, root){
			/* debugger; */
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if(row.auditorId == null || row.auditorId == pid.split(",")[0]) {
					if ((${fns:jsGetVal('row.itemParentId')}) == pid.split(",")[1]){
						$(list).append(Mustache.render(tpl, {
							dict:{
								
								type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
								ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
								method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
								status: getDictLabel(${fns:toJson(fns:getDictList('opa_schemeItem_status'))}, row.status), blank123:0}, 
								pid: (root?0:pid.split(",")[1]), 
								row: row,
								btnCol: btnRender(row)
								
						}));
						console.log($(list));
						addRowsDes(list, tpl, data, row.auditorId+","+row.itemId);
					}
				}
			}
		}
		
		function addRowSec(list, tpl, data, pid, root, officeNotExsist){
			/* debugger; */
			for (var i=0; i<data.length; i++){
				var row = data[i];
				console.log("fns:jsGetVal:" + ${fns:jsGetVal('row.itemParentId')});
				if(officeNotExsist.indexOf(pid.split(",")[0]) == -1) {
					if(row.auditorId == null || row.auditorId == pid.split(",")[0]) {
							$(list).append(Mustache.render(tpl, {
								dict:{
									
									type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
									ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
									method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
									status: getDictLabel(${fns:toJson(fns:getDictList('opa_schemeItem_status'))}, row.status), blank123:0}, 
									pid: (root?0:pid.split(",")[1]), 
									row: row,
									btnCol: btnRender(row)
									
							}));
					}
				}
			}
		}
		function auditPass(a){
			return confirmx("确认通过该任务吗",a);
		}
		function auditReturn(a){
			return confirmx("确认不通过该任务吗",a);
		}
		function inputScore(value, thisValue, a){
			inputPromptx("请输入考评分值", "分值", value, thisValue, a, true);
			return false;
		}
		//提示输入对话框
		function inputPromptx(title, lable, value, thisValue, href, closed){
			top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable
					+ "：<input type='text' id='txt' name='txt'/> /"+value+"分&nbsp;<span class='help-inline'>自评:"+thisValue+"分</span></div>"+
					"<div class='form-search' style='padding:20px;text-align:center;'>打分：<div><textarea type='textarea' htmlEscape='false' rows='5' cols='20' maxlength='50' id='reason' name='reason'/></div></div>", {
					title: title, submit: function (v, h, f){
			    if (f.txt == '') {
			        top.$.jBox.tip("请输入" + lable + "。", "error");
			        return false;
			    }
			    if (f.txt*1 >thisValue*1) {
			        top.$.jBox.tip('填写分值不能大于标准分值');
			        return false;
			    }
				if (typeof href == 'function') {
					href();
				}else{
					resetTip(); //loading();
					location = href + encodeURIComponent(f.txt) + "&reason=" + encodeURIComponent(f.reason);
				}
			},closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
			resetTip();
			return false;
		}
		function btnRender(row){
			var str="";
			var level=row.level;
			var name=row.name;
			//var lname="全面深化改革";
			if((row.status == "${fns:getDictValue('待评分', 'opa_planTask_status', '')}")){
					//if((name==lname || row.itemParentId!=0  && level!=1 && level!=2)){
						//上报
						str+="<shiro:hasPermission name='opa:opaPlanTask:excute:audit:edit'><a href='${ctx}/opa/opaPlanTask/excute/audit/inputScore?id="+row.id 
							+"&planId="+row.planId+"&inputScore=' onclick='return inputScore(\""+row.value+"\",\""+row.score+"\",this.href)'>打分</a>&nbsp;</shiro:hasPermission>";
					//}
			}
			if((row.attachedPath != null && row.attachedPath != undefined && row.attachedPath != '')){ 
					str+="<shiro:hasPermission name='opa:opaPlanTask:excute:audit:edit'><a href='${ctx}/opa/opaPlanTask/download?id="+row.id 
						+"&planId="+row.planId+"' >下载</a>&nbsp;</shiro:hasPermission>";
			}
			return str;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		 function allScore(){
		    var id="${opaPlanTask.planId}";
		    var error="请重新选择";
		    if(id!=null){
		    	$.ajax({
		    		type:"post",
		    		url: "${ctx}/opa/opaPlanTask/allScore/out?planId="+id,
		  	    	cache:false,
		  	    	async:false, 
					dataType:'text', 
					success:function(data){
						if(data=="ok"){
							   showTip("打分已完成!");
						}
						  closeLoading();
				    }
		    	});
		    }else{
		    	alert(error);
		    }
		} 
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaPlanTask/excute/audit/index">指标列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaPlanTask"
		action="${ctx}/opa/opaPlanTask/excute/audit/index" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>指标名称：</label> <form:select path="planId"
					class="input-xlarge">
					<form:options items="${schemeList}" itemLabel="label"
						itemValue="value" htmlEscape="false" />
				</form:select></li>
			<%-- <li><label>被考核部门</label> <sys:treeselect id="office"
					name="office.id" value="${opaPlanTask.office.id}"
					labelName="office.name" labelValue="${opaPlanTask.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small"
					allowClear="true" notAllowSelectParent="true" /></li> --%>
			<li><label>名称：</label> <form:input path="name"
					htmlEscape="false" maxlength="200" class="input-medium" /></li>
			<li><label>指标类型：</label> <form:select path="type"
					class="input-mini">
					<form:option value="" label="" />
					<form:options items="${fns:getDictBlankList('opa_item_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
		<%-- 	<li><label>数值类型：</label> <form:select path="ifNum"
					class="input-mini">
					<form:option value="" label="" />
					<form:options items="${fns:getDictBlankList('opa_item_Num_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li> --%>
			<%-- <li><label>审核者：</label>
				<sys:treeselect id="auditorId" name="auditorId" value="${opaPlanTask.auditorId}" labelName="auditorName" labelValue="${opaPlanTask.auditorName}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li> --%>
			<li><label>状态：</label> <form:select path="status"
					class="input-mini">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictBlankList('opa_planTask_status')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
				  <li class="btns"> <input id="btnSubmitl" class="btn btn-primary" type="submit" value="打分完成" onClick="allScore()"/></li>  
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="treeTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>指标类型</th>
				<!-- <th>考核方式</th> -->
				<th>数值类型</th>
				<th>分值</th>
				<th>得分</th>
				<!-- <th>数值</th>
				<td>
					{{row.count}}
				</td> -->
				<!-- <th>完成</th> -->
				<!-- <th>考核部门</th>
				<td>
					{{row.office.name}}
				</td> -->
				<th>状态</th>
				<th>单位</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.itemId}}" pId="{{pid}}">
				<td><a href="${ctx}/opa/opaPlanTask/view?id={{row.id}}">
					{{row.name}}
				</a></td>
				<td>
					{{dict.type}}
				</td>
				
				<td>
					{{dict.ifNum}}
				</td>
				<td>
					{{row.value}}
				</td>
				<td>
					{{row.score}}
				</td>
				
				<td>
					{{dict.status}}
				</td>
				<td>
					{{row.auditorName}}
				</td>
				<td>
					{{{btnCol}}}
				</td>	
		</tr>
	</script>
	<div class="pagination">${page}</div>
</body>
</html>