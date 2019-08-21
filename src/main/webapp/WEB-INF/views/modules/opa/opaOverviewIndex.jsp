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
		<li class="active"><a href="${ctx}/opa/opaOverview/">评分列表</a></li>
		<shiro:hasPermission name="opa:opaOverview:edit"><li><a href="${ctx}/opa/opaOverview/form">添加分数</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="opaOverview" action="${ctx}/opa/opaOverview/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li> --%>
			<%-- <li><label>起始日期：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${opaScheme.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${opaScheme.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li> --%>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li> -->
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>年底考核成绩</th>
				<th>日常考核成绩</th>
				<th>折算得分</th>
				<th>主要领导评价(50分)</th>
				<th>县级领导评价(30分)</th>
				<th>单位互评(30分)</th>
				<th>领导班子测评(30分)</th>
				<th>服务对象评价(40分)</th>
				<th>考核组评价(20分)</th>
				<th>综合评价得分</th>
				<th>创新项目加分</th>
				<th>表彰奖励加分</th>
				<th>扣分</th>
				<th>全年得分</th>
				<shiro:hasPermission name="opa:opaOverview:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaOverview">
			<tr>
				<td>
                    ${opaOverview.yearResult} 
				</td>
				<td>
					${opaOverview.daiScore}
				</td>
				<td>
					${opaOverview.convertScore}
				</td>
				<td>
				    ${opaOverview.leadingScore}
				</td>
				<td>
		            ${opaOverview.countyScore}		
				</td>
				<td>
				    ${opaOverview.peerReview }
				</td>
                <td>
                    ${opaOverview.leadershipAssessment}
                </td>
                <td>
                    ${opaOverview.serviceRating}
                </td>
                <td>
                    ${opaOverview.assessmentScore}
                </td>
                <td>
                    ${opaOverview.overallScore}
                </td>
                <td>
                    ${opaOverview.projectsScore}
                </td>
                <td>
                    ${opaOverview.recogintionScore}
                </td>
                <td>
                    ${opaOverview.deduction}
                </td>
                <td>
                    ${opaOverview.annualScore}
                </td>
				<td>
						<shiro:hasPermission name="opa:opaOverview:edit">
						<a href="${ctx}/opa/opaOverview/form?id=${opaOverview.id}">修改</a>
						<a href="${ctx}/opa/opaOverview/delete?id=${opaOverview.id}" onclick="return confirmx('确认要删除该考核方案吗？', this.href)">删除</a>
						</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>