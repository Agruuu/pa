<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导干部考核情况管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出考核数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/opa/opaLeadershipAssessment/export");
					$("#searchForm").submit();
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			 $("#searchForm").attr("action","${ctx}/opa/opaLeadershipAssessment/"); 
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>干部考核情况列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaLeadershipAssessment" action="${ctx}/opa/opaLeadershipAssessment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li>
		<label>归属机关:</label><sys:treeselect id="company" name="company.id" value="${opaLeadershipAssessment.company.id}" labelName="company.name" labelValue="${user.company.name}"
		title="机关" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"/>
		</li>
			<li><label>名字：</label>
				<form:input path="name" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();" />
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>单位</th>
				<th>民主测评(10分)</th>
				<th>生活圈,社交圈测评(10分)</th>
				<th>反向测评得分(10分)</th>
				<th>谈话民主推荐测评(10分)</th>
				<th>平时考核测评(10分)</th>
				<th>平时考核得分(10分)</th>
				<th>总分</th>
				<th>备注</th>
				<%-- <shiro:hasPermission name="opa:opaLeadershipAssessment:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaLeadershipAssessment">
			<tr>
				<td><%-- <a href="${ctx}/opa/opaLeadershipAssessment/form?id=${opaLeadershipAssessment.id}"> --%>
					${opaLeadershipAssessment.name}
				<!-- </a> -->
				</td>
				<td>
					${opaLeadershipAssessment.office.name}
				</td>
				<td>
					${opaLeadershipAssessment.democraticAssessment}
				</td>
				<td>
					${opaLeadershipAssessment.socialAssessment}
				</td>
				<td>
					${opaLeadershipAssessment.germanyAssessment}
				</td>
				<td>
					${opaLeadershipAssessment.conversationAssessment}
				</td>
				<td>
					${opaLeadershipAssessment.departmentAssessment}
				</td>
				<td>
					${opaLeadershipAssessment.normalAssessment}
				</td>
				<td>
				   ${opaLeadershipAssessment.allscore}
				</td>
				<td>
					${opaLeadershipAssessment.remarks}
				</td>

				<%-- <shiro:hasPermission name="opa:opaLeadershipAssessment:edit"><td>
    				<a href="${ctx}/opa/opaLeadershipAssessment/form?id=${opaLeadershipAssessment.id}">修改</a>
					<a href="${ctx}/opa/opaLeadershipAssessment/delete?id=${opaLeadershipAssessment.id}" onclick="return confirmx('确认要删除该领导干部考核情况吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>