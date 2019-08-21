<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效反馈管理</title>
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
		<li class="active"><a href="${ctx}/opa/opaTaskAppeal/index">绩效反馈列表</a></li>
		<shiro:hasPermission name="opa:opaTaskAppeal:edit"><li><a href="${ctx}/opa/opaTaskAppeal/form">绩效反馈添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="opaTaskAppeal" action="${ctx}/opa/opaTaskAppeal/" method="post" class="breadcrumb form-search">
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
				<sys:treeselect id="office" name="office.id" value="${opaTaskAppeal.office.id}" labelName="office.name" labelValue="${opaTaskAppeal.office.name}"
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
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否数值类型：</label>
				<form:select path="ifNum" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>审核者：</label>
				<form:input path="auditorId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
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
				<th>考核方式</th>
				<th>是否数值类型</th>
				<th>考评标准</th>
				<th>得分</th>
				<th>数值标准</th>
				<th>完成数目</th>
				<th>审核者</th>
				<th>状态</th>
				<th>备注信息</th>
				<shiro:hasPermission name="opa:opaTaskAppeal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaTaskAppeal">
			<tr>
				<td><a href="${ctx}/opa/opaTaskAppeal/form?id=${opaTaskAppeal.id}">
					${fns:getDictLabel(opaTaskAppeal.planId, '', '')}
				</a></td>
				<td>
					${opaTaskAppeal.office.name}
				</td>
				<td>
					${opaTaskAppeal.code}
				</td>
				<td>
					${opaTaskAppeal.name}
				</td>
				<td>
					${fns:getDictLabel(opaTaskAppeal.type, '', '')}
				</td>
				<td>
					${fns:getDictLabel(opaTaskAppeal.method, '', '')}
				</td>
				<td>
					${fns:getDictLabel(opaTaskAppeal.ifNum, '', '')}
				</td>
				<td>
					${opaTaskAppeal.value}
				</td>
				<td>
					${opaTaskAppeal.score}
				</td>
				<td>
					${opaTaskAppeal.count}
				</td>
				<td>
					${opaTaskAppeal.result}
				</td>
				<td>
					${opaTaskAppeal.auditorId}
				</td>
				<td>
					${opaTaskAppeal.status}
				</td>
				<td>
					${opaTaskAppeal.remarks}
				</td>
				<shiro:hasPermission name="opa:opaTaskAppeal:edit"><td>
    				<a href="${ctx}/opa/opaTaskAppeal/form?id=${opaTaskAppeal.id}">申诉</a>
					<a href="${ctx}/opa/opaTaskAppeal/delete?id=${opaTaskAppeal.id}" onclick="return confirmx('确认要删除该绩效反馈吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>