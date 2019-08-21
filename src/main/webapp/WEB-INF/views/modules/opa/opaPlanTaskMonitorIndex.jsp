<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>计划任务管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].itemId);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].itemParentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].itemParentId+',') == -1){
						rootIds.push(data[i].itemParentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			
			$("#treeTable").treeTable({expandLevel : 10}); 
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.itemParentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict:{
							type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
							ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
							method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
							status: getDictLabel(${fns:toJson(fns:getDictList('opa_planTask_status'))}, row.status), blank123:0}, 
							pid: (root?0:pid), 
							row: row,
							
					}));
					addRow(list, tpl, data, row.itemId);
				}
			}
		}
		function getRestValue(planId, itemParentId){
			var result;
			$.ajax({
				type: "POST",
				url: '${ctx}/opa/opaPlanTask/getRestValue?planId='+planId+'&itemParentId='+itemParentId,
				dataType:'text',
				async:false,
				cache: false,
				success: function(data){
					result = data;
				},
				error:function(data){
					console.log(data);
				}
				
			});
			return result;
		} 
		function inputValue(planId, itemParentId, thisValue, a){
			var restValue = getRestValue(planId, itemParentId);
			inputPromptx("请输入考评标准分值", "分值", restValue, thisValue, a, true);
			return false;
		} 
		function inputCount(a){
			return promptx("请输入考评标准数值", "数值", a, true);
		}
		function apply(a){
			return confirmx("上报后将无法添加子任务，确认要上报该计划指标吗",a);
		}
		function del(a){
			return confirmx("确认要删除该计划指标吗",a);
		}
		
		function btnRender(row){
			var str="";
			if((row.status == "${fns:getDictValue('待填报', 'opa_planTask_status', '')}")
					||(row.status == "${fns:getDictValue('已填报', 'opa_planTask_status', '')}")
					||(row.status == "${fns:getDictValue('已退回', 'opa_planTask_status', '')}")){
					//上报
					if(row.status == "${fns:getDictValue('已填报', 'opa_planTask_status', '')}"){
						str+="<shiro:hasPermission name='opa:opaPlanTask:apply'><a href='${ctx}/opa/opaPlanTask/apply?id="+row.id 
						+"&planId="+row.planId+"' onclick='return apply(this.href)'>上报</a>&nbsp;</shiro:hasPermission>";
					}
					
					if(row.ifNum == "${fns:getDictValue('个性数值', 'opa_item_Num_type', '')}"){
						str+="<shiro:hasPermission name='opa:opaPlanTask:edit'><a href='${ctx}/opa/opaPlanTask/inputCount?id="+row.id 
						+"&planId="+row.planId+"&count=' onclick='return inputCount(this.href)'>数值标准</a>&nbsp;</shiro:hasPermission>";
					}
					if(row.type == "${fns:getDictValue('个性指标', 'opa_item_type', '')}"){
						var thisValue = row.value;
						 thisValue = (typeof(thisValue) == "undefined")? 0 : thisValue;
						str+="<shiro:hasPermission name='opa:opaPlanTask:edit'><a href='${ctx}/opa/opaPlanTask/inputValue?id="+row.id 
							+"&planId="+row.planId+"&value=' onclick='return inputValue(\""+row.planId+"\",\""+row.itemParentId+"\",\""+thisValue
							+"\",this.href)'>评分标准</a>&nbsp;</shiro:hasPermission>";
					}
					if(row.method == "${fns:getDictValue('机器汇总', 'opa_item_method', '')}" && !row.hasCommonChild){
						str+="<shiro:hasPermission name='opa:opaPlanTask:addTask'><a href='${ctx}/opa/opaPlanTask/addTask?parentId="+row.id 
							+"'>添加子任务</a>&nbsp;</shiro:hasPermission>";
					}
					//若指标为子任务, 则可以修改删除
					if(row.id == row.itemId){
						str+="<shiro:hasPermission name='opa:opaPlanTask:edit'><a href='${ctx}/opa/opaPlanTask/edit?id="+row.id 
							+"&planId="+row.planId+"' >修改</a>&nbsp;</shiro:hasPermission>";
						str+="<shiro:hasPermission name='opa:opaPlanTask:edit'><a href='${ctx}/opa/opaPlanTask/delete?id="+row.id 
							+"&planId="+row.planId+"' onclick='return del(this.href)'>删除</a>&nbsp;</shiro:hasPermission>";
					}
					
			}
			return str;
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaPlanTask/monitor/index">计划任务列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaPlanTask" action="${ctx}/opa/opaPlanTask/monitor/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>计划名称：</label>
				<form:select path="planId" class="input-xlarge">
					<form:options items="${planList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>被考核部门</label>
				<sys:treeselect id="office" name="office.id" value="${opaPlanTask.office.id}" labelName="office.name" labelValue="${opaPlanTask.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li> --%>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>指标类型：</label>
				<form:select path="type" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>数值类型：</label>
				<form:select path="ifNum" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_Num_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>审核者：</label>
				<sys:treeselect id="auditorId" name="auditorId" value="${opaPlanTask.auditorId}" labelName="auditorName" labelValue="${opaPlanTask.auditorName}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_planTask_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>内容描述</th>
				<th>指标类型</th>
				<th>考核方式</th>
				<th>数值类型</th>
				<th>考评标准</th>
				<th>得分</th>
				<th>数值标准</th>
				<th>完成数目</th>
				<th>审核者</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.itemId}}" pId="{{pid}}">
				<td><a href="${ctx}/opa/opaPlanTask/monitor/view?id={{row.id}}">
					{{row.name}}
				</a></td>
				<td>
					{{row.content}}
				</td>
				<td>
					{{dict.type}}
				</td>
				<td>
					{{dict.method}}
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
					{{row.count}}
				</td>
				<td>
					{{row.result}}
				</td>
				<td>
					{{row.auditorName}}
				</td>
				<td>
					{{dict.status}}
				</td>
				<td>
					{{{btnCol}}}
				</td>	
		</tr>
		
	</script>
</body>
</html>