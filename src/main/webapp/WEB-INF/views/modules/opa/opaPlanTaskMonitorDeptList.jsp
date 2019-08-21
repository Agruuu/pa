<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>计划任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaPlanTask/monitor/">计划任务列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaPlanTask" action="${ctx}/opa/opaPlanTask/monitor" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>计划编号：</label>
				<form:select path="planId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>部门编号：</label>
				<sys:treeselect id="office" name="office.id" value="${opaPlanTask.office.id}" labelName="office.name" labelValue="${opaPlanTask.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="100" class="input-medium"/>
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
			<li><label>数值类型：</label>
				<form:select path="ifNum" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_item_Num_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>审核者：</label>
				<sys:treeselect id="auditorId" name="auditorId" value="${opaPlanTask.auditorId}" labelName="" labelValue="${opaPlanTask.}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_plan_task_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>计划编号</th>
				<th>部门编号</th>
				<th>编码</th>
				<th>名称</th>
				<th>指标类型</th>
				<th>指标内容描述</th>
				<th>考核方式</th>
				<th>是否数值类型</th>
				<th>考评标准</th>
				<th>得分</th>
				<th>数值标准</th>
				<th>完成数目</th>
				<th>审核者</th>
				<th>状态</th>
				<th>备注信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaPlanTask">
			<tr>
				<td><a href="${ctx}/opa/opaPlanTask/form?id=${opaPlanTask.id}">
					${fns:getDictLabel(opaPlanTask.planId, '', '')}
				</a></td>
				<td>
					${opaPlanTask.office.name}
				</td>
				<td>
					${opaPlanTask.code}
				</td>
				<td>
					${opaPlanTask.name}
				</td>
				<td>
					${fns:getDictLabel(opaPlanTask.type, 'opa_item_type', '')}
				</td>
				<td>
					${opaPlanTask.content}
				</td>
				<td>
					${fns:getDictLabel(opaPlanTask.method, 'opa_item_mothod', '')}
				</td>
				<td>
					${fns:getDictLabel(opaPlanTask.ifNum, 'opa_item_Num_type', '')}
				</td>
				<td>
					${opaPlanTask.value}
				</td>
				<td>
					${opaPlanTask.score}
				</td>
				<td>
					${opaPlanTask.count}
				</td>
				<td>
					${opaPlanTask.result}
				</td>
				<td>
					${opaPlanTask.}
				</td>
				<td>
					${fns:getDictLabel(opaPlanTask.status, 'opa_plan_task_status', '')}
				</td>
				<td>
					${opaPlanTask.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>