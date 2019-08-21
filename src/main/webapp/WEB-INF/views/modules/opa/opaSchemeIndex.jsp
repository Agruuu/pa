<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考核方案管理</title>
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
		<li class="active"><a href="${ctx}/opa/opaScheme/index">考核指标列表</a></li>
		<shiro:hasPermission name="opa:opaScheme:edit"><li><a href="${ctx}/opa/opaScheme/form">考核指标添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="opaScheme" action="${ctx}/opa/opaScheme/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>起始日期：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${opaScheme.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${opaScheme.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>根指标</th>
				<th>备注信息</th>
				<th>更新时间</th>
				<th>状态</th>
				<th>审核部门</th>
				<shiro:hasPermission name="opa:opaScheme:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaScheme">
			<tr>
				<td>
					<c:if test="${opaScheme.status eq fns:getDictValue('制定中','opa_scheme_status','') 
							or opaScheme.status eq fns:getDictValue('已退回','opa_scheme_status','') 
							or opaScheme.status eq fns:getDictValue('执行中','opa_scheme_status','')
							or opaScheme.status eq fns:getDictValue('已完成','opa_scheme_status','')}">
						<shiro:hasAnyPermissions name="opa:opaScheme:edit,opa:opaScheme:apply">
							<a href="${ctx}/opa/opaScheme/form?id=${opaScheme.id}">
						</shiro:hasAnyPermissions>
								${opaScheme.name}
						<shiro:hasAnyPermissions name="opa:opaScheme:edit,opa:opaScheme:apply">
							</a>
						</shiro:hasAnyPermissions>
					</c:if>
					<c:if test="${(opaScheme.status eq fns:getDictValue('待审核','opa_scheme_status',''))
							or (opaScheme.status eq fns:getDictValue('指标分配中','opa_scheme_status','')) 
							or (opaScheme.status eq fns:getDictValue('审核中','opa_scheme_status',''))
							or (opaScheme.status eq fns:getDictValue('指标填报中','opa_scheme_status','')) 
							or (opaScheme.status eq fns:getDictValue('已发布','opa_scheme_status',''))}">
						<shiro:hasPermission name="opa:opaScheme:view">
							<a href="${ctx}/opa/opaScheme/view?id=${opaScheme.id}">
						</shiro:hasPermission>
								${opaScheme.name}
						<shiro:hasPermission name="opa:opaScheme:view">
							</a>
						</shiro:hasPermission>
					</c:if>
				</td>
				<td>
					${opaScheme.itemParent.name}
				</td>
				<td>
					${opaScheme.remarks}
				</td>
				<td>
					<fmt:formatDate value="${opaScheme.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(opaScheme.status,'opa_scheme_status','')}
				</td>
				<td>
				   ${opaScheme.auditor.name}
				</td>
				<td>
					<c:if test="${opaScheme.status eq fns:getDictValue('制定中','opa_scheme_status','') or opaScheme.status eq fns:getDictValue('已退回','opa_scheme_status','')}">
						<shiro:hasPermission name="opa:opaScheme:apply">
						<a href="${ctx}/opa/opaScheme/apply?id=${opaScheme.id}" onclick="return confirmx('确认要上报该考核方案吗？', this.href)">上报</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="opa:opaScheme:edit">
						<a href="${ctx}/opa/opaScheme/form?id=${opaScheme.id}">修改</a>
						<a href="${ctx}/opa/opaScheme/delete?id=${opaScheme.id}" onclick="return confirmx('确认要删除该考核方案吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${opaScheme.status eq fns:getDictValue('待审核','opa_scheme_status','') or opaScheme.status eq fns:getDictValue('已审核','opa_scheme_status','')}">
					</c:if>
					<c:if test="${opaScheme.status eq fns:getDictValue('执行中','opa_scheme_status','')}">
						<shiro:hasPermission name="opa:opaScheme:excute">
						<a href="${ctx}/opa/opaScheme/excute?id=${opaScheme.id}" onclick="return confirmx('确认要完成该指标吗？', this.href)">完成</a>
						</shiro:hasPermission>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
 <div class="pagination">${page}</div>
</body>
</html>