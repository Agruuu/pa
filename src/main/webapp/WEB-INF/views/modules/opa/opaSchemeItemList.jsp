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
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.itemParentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.itemId);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaSchemeItem/index">方案指标列表</a></li>
		<shiro:hasPermission name="opa:opaSchemeItem:edit"><li><a href="${ctx}/opa/opaSchemeItem/form">方案指标添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="opaSchemeItem" action="${ctx}/opa/opaSchemeItem/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>方案编号：</label>
				<form:input path="schemeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>指标类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_item_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考核方式：</label>
				<form:select path="method" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_item_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否数值类型：</label>
				<form:input path="ifNum" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>起始日期：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateFrom}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateTo}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_scheme_item_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>指标父级编号</th>
				<th>名称</th>
				<th>指标类型</th>
				<th>指标内容描述</th>
				<th>考核方式</th>
				<th>是否数值类型</th>
				<th>考评标准</th>
				<th>数值标准</th>
				<th>更新时间</th>
				<th>审核者</th>
				<th>状态</th>
				<th>备注信息</th>
				<shiro:hasPermission name="opa:opaSchemeItem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
				<td><a href="${ctx}/opa/opaSchemeItem/form?id=${opaSchemeItem.id}">
					${opaSchemeItem.itemParentId}
				</a></td>
				<td>
					${opaSchemeItem.name}
				</td>
				<td>
					${fns:getDictLabel(opaSchemeItem.type, 'opa_item_type', '')}
				</td>
				<td>
					${opaSchemeItem.content}
				</td>
				<td>
					${fns:getDictLabel(opaSchemeItem.method, 'opa_item_method', '')}
				</td>
				<td>
					${opaSchemeItem.ifNum}
				</td>
				<td>
					${opaSchemeItem.value}
				</td>
				<td>
					${opaSchemeItem.count}
				</td>
				<td>
					<fmt:formatDate value="${opaSchemeItem.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${opaSchemeItem.auditorId}
				</td>
				<td>
					${fns:getDictLabel(opaSchemeItem.status, 'opa_scheme_item_status', '')}
				</td>
				<td>
					${opaSchemeItem.remarks}
				</td>
				<shiro:hasPermission name="opa:opaSchemeItem:edit"><td>
    				<a href="${ctx}/opa/opaSchemeItem/form?id=${opaSchemeItem.id}">修改</a>
					<a href="${ctx}/opa/opaSchemeItem/delete?id=${opaSchemeItem.id}" onclick="return confirmx('确认要删除该方案指标吗？', this.href)">删除</a>
				</td></shiro:hasPermission>	
		</tr>
	</script>
</body>
</html>