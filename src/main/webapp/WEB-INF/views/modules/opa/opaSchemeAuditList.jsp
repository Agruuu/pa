<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考核方案审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		<!-- add by lxy start -->
		function returnInput(a){
			return promptRemarkx("请输入退回原因", "退回原因(最多50个汉字)", a, true);
		}
		<!-- add by lxy end -->
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
		<li class="active"><a href="${ctx}/opa/opaScheme/audit/index">考核指标审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaScheme" action="${ctx}/opa/opaScheme/audit/index" method="post" class="breadcrumb form-search">
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
				<th>审核者</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaScheme">
			<tr>
				<td>
				<!-- 
				opa:opaScheme:audit:view; 审核阶段    
				opa:opaScheme:audit:pass; 审核通过
				opa:opaScheme:audit:return;  审核退回 
				 -->
					<shiro:hasAnyPermissions name="opa:opaScheme:audit:view,opa:opaScheme:audit:pass,opa:opaScheme:audit:return">
						<a href="${ctx}/opa/opaScheme/audit/view?id=${opaScheme.id}">
					</shiro:hasAnyPermissions>
							${opaScheme.name}
					<shiro:hasAnyPermissions name="opa:opaScheme:audit:view,opa:opaScheme:audit:pass,opa:opaScheme:audit:return">
						</a>
					</shiro:hasAnyPermissions>
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
					${opaScheme.auditor.name}
				</td>
				<td>
					${fns:getDictLabel(opaScheme.status,'opa_scheme_status','')}
				</td>
				<td>
					<c:if test="${opaScheme.status eq fns:getDictValue('待审核','opa_scheme_status','')}">
						
							<shiro:hasPermission name="opa:opaScheme:audit:pass">
							<a href="${ctx}/opa/opaScheme/audit/pass?id=${opaScheme.id}" onclick="return confirmx('确认要通过该考核指标吗？', this.href)">审核通过</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="opa:opaScheme:audit:return">
							<!-- <a href="${ctx}/opa/opaScheme/audit/return?id=${opaScheme.id}" onclick="return confirmx('确认要退回该考核方案吗？', this.href)">退回</a> -->
							<!-- add by lxy start -->
							<a href="${ctx}/opa/opaScheme/audit/return?id=${opaScheme.id}&reason=" onclick="return returnInput(this.href)">退回</a>
							<!-- add by lxy end -->
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