<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>计划查询</title>
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
		<li class="active"><a href="${ctx}/opa/opaPlan/monitor/index">考核计划列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaPlan" action="${ctx}/opa/opaPlan/monitor/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>父级部门：</label>
				<sys:treeselect id="officeParentId" name="officeParentId" value="${opaPlan.officeParentId}" labelName="" labelValue="${opaPlan.officeParentName}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>考核年份：</label>
				<form:input path="year" htmlEscape="false" maxlength="4" class="input-mini"/>
			</li>
			<li><label>考核周期：</label>
				<form:select path="cycle" class="input-xmini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_cycle_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>起始日期：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${opaPlan.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${opaPlan.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_plan_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="返回" onclick="history.back(-1)"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>考核部门</th>
				<th>考核年份</th>
				<th>考核周期</th>
				<th>起始日期</th>
				<th>截止日期</th>
				<th>审核人</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaPlan">
			<tr>
				<td>
					<a href="${ctx}/opa/opaPlanTask/monitor/index?id=${opaPlan.id}">
						${opaPlan.name}
					</a>
				</td>
				<td>
					${opaPlan.officeParentName}
				</td>
				<td>
					${opaPlan.year}
				</td>
				<td>
					${fns:getDictLabel(opaPlan.cycle, 'opa_cycle_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${opaPlan.dateFrom}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${opaPlan.dateTo}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${opaPlan.auditorName}
				</td>
				<td>
					${fns:getDictLabel(opaPlan.status,'opa_plan_status','')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>