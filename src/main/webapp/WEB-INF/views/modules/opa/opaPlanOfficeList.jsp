<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考核计划部门管理</title>
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
		<li class="active"><a href="${ctx}/opa/opaPlanOffice/">考核计划部门列表</a></li>
		<shiro:hasPermission name="opa:opaPlanOffice:edit"><li><a href="${ctx}/opa/opaPlanOffice/form">考核计划部门添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="opaPlanOffice" action="${ctx}/opa/opaPlanOffice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属计划：</label>
				<form:select path="planId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属部门：</label>
				<sys:treeselect id="office" name="office.id" value="${opaPlanOffice.office.id}" labelName="office.name" labelValue="${opaPlanOffice.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>父级部门：</label>
				<sys:treeselect id="officeParentId" name="officeParentId" value="${opaPlanOffice.officeParentId}" labelName="" labelValue="${opaPlanOffice.}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>所属计划</th>
				<th>所属部门</th>
				<th>父级部门</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="opa:opaPlanOffice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaPlanOffice">
			<tr>
				<td><a href="${ctx}/opa/opaPlanOffice/form?id=${opaPlanOffice.id}">
					${fns:getDictLabel(opaPlanOffice.planId, '', '')}
				</a></td>
				<td>
					${opaPlanOffice.office.name}
				</td>
				<td>
					${opaPlanOffice.}
				</td>
				<td>
					<fmt:formatDate value="${opaPlanOffice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${opaPlanOffice.remarks}
				</td>
				<shiro:hasPermission name="opa:opaPlanOffice:edit"><td>
    				<a href="${ctx}/opa/opaPlanOffice/form?id=${opaPlanOffice.id}">修改</a>
					<a href="${ctx}/opa/opaPlanOffice/delete?id=${opaPlanOffice.id}" onclick="return confirmx('确认要删除该考核计划部门吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>