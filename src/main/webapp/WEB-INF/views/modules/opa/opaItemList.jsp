<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>方案管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
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
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaItem/">方案列表</a></li>
		<shiro:hasPermission name="opa:opaItem:edit"><li><a href="${ctx}/opa/opaItem/form">方案添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="opaItem" action="${ctx}/opa/opaItem/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			
			<li><label>父级方案：</label>
				<sys:treeselect id="parent" name="parent.id" value="${opaItem.parent.id}" labelName="parent.name" labelValue="${opaItem.parent.name}"
					title="指标" url="/opa/opaItem/treeData" cssClass="input-small" allowClear="true"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_type')}" itemLabel="label" itemValue="value" htmlEscape="true"/>
				</form:select>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>创建者：</label>
				<sys:treeselect id="createBy" name="createBy.id" value="${opaItem.createBy.id}" labelName="createBy.name" labelValue="${opaItem.createBy.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
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
				<th>类型</th>
				<th>创建者</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="opa:opaItem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			
			<td>
			<shiro:hasPermission name="opa:opaItem:edit">
			<a href="${ctx}/opa/opaItem/form?id={{row.id}}">
			</shiro:hasPermission>
				{{row.name}}
			<shiro:hasPermission name="opa:opaItem:edit">
			</a>
			</shiro:hasPermission>
			</td>
			<td>	
				{{dict.type}}
			</td>
			<td>
				{{row.createBy.name}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="opa:opaItem:edit"><td>
   				<a href="${ctx}/opa/opaItem/form?id={{row.id}}">修改</a>
				<a href="${ctx}/opa/opaItem/delete?id={{row.id}}" onclick="return confirmx('确认要删除该指标及所有子指标吗？', this.href)">删除</a>
				<a href="${ctx}/opa/opaItem/form?parent.id={{row.id}}">添加下级指标</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>