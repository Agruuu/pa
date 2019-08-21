<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>方案指标管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
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
		console.log("officeNotExsist:" + officeNotExsist);
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
			console.log(a)
			return confirmx("确认要审核通过该任务吗",a);
			
		}
		function auditReturn(a){
			return confirmx("确认要退回该任务吗",a);
		}
		<!-- add by lxy start -->
		function returnInput(a){
			return promptRemarkx("请输入退回原因", "退回原因(最多50个汉字)", a, true);
		}
		<!-- add by lxy end -->
		function btnRender(row){
			var str="";
			if(row.status == "${fns:getDictValue('已通过', 'opa_planTask_status', '')}"){
					//上报
					str+="<shiro:hasPermission name='opa:opaPlanTask:assign:audit:pass'><a href='${ctx}/opa/opaPlanTask/assign/audit/pass?id="+row.id 
						+"&schemeId="+row.schemeId+"' onclick='return auditPass(this.href)'>审核通过</a>&nbsp;</shiro:hasPermission>";
					str+="<shiro:hasPermission name='opa:opaPlanTask:audit:return'><a href='${ctx}/opa/opaPlanTask/audit/return?id="+row.id 
						+"&schemeId="+row.schemeId+"&reason=' onclick='return returnInput(this.href)'>退回</a>&nbsp;</shiro:hasPermission>";
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
		 /* function allAssignPass(a){
			 console.log(a)
			 var planId="${opaPlanTask.planId}";
			 return confirmx("确认要所有评分审核通过吗",a);
			
	     } */
	     
	     function allPass(){
	    	 var planId="${opaPlanTask.planId}";
	    	 if(planId!=null && planId!=""){
	    		 $.ajax({
	    			 type:"post",
			    		url: "${ctx}/opa/opaPlanTask/all/assign/pass?schemeId="+planId,
			  	    	cache:false,
			  	    	async:false, 
						dataType:'text', 
						success:function(data){
							if(data=="ok"){
								   showTip("评分全部审核通过!");
							}
							  closeLoading();
					    }
	    		 });
	    	 }
	     }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaPlanTask/rating/review/index">方案指标评分列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaPlanTask" action="${ctx}/opa/opaPlanTask/rating/review/index" method="post" class="breadcrumb form-search">
	 <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> 
		<ul class="ul-form">
		 <li><label>指标名称：</label>
				<form:select path="planId" class="input-xlarge">
					<form:options items="${schemeList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>指标名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li> --%>
			<%-- <li><label>状态：</label>
				<form:select path="status" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_scheme_item_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>指标类型：</label>
				<form:select path="type" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考核方式：</label>
				<form:select path="method" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>数值类型：</label>
				<form:select path="ifNum" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_item_Num_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<li><label>起始日期：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
<%-- 			<li class="btns">
			 <a href="${ctx}/opa/opaPlanTask/all/assign/pass">
			   <input  id="btnSubmit" class="btn btn-primary" type="submit" value="全部审核" onClick="return allAssignPass(this.href)"/>
			 </a>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="全部审核" onClick="allPass()"/></li>
			<li class="clearfix"></li>
		</ul>	
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>指标类型</th>
				<th>分值</th>
				<th>得分</th>
				<th>单位</th>
				<th>备注信息</th>
				<th>状态</th>
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
					{{row.value}}
				</td>
                <td>
					{{row.score}}
				</td>
                <td>
                    {{row.auditorName}} 
                </td>
                <td>
                    {{row.remarks}}
                </td>
				<td>
					{{dict.status}}
				</td>
				<td>
    				{{{btnCol}}}
				</td>	
		</tr>
	</script>
	 <div class="pagination">${page}</div> 
</body>
</html>