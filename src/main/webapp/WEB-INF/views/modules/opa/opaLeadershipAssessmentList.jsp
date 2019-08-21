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
			 $("#searchForm").attr("action","${ctx}/opa/opaLeadershipAssessment/list"); 
			$("#searchForm").submit();
        	return false;
        }
		$(function(){
			$("input[type='button']").click(function(){
				$("input[name='name']:checked").each(function(){ //遍历选中的checkbox
					n=$(this).parents("tr").index();          //获取checkbox所在行的顺序
					$("opa_opaLeadershipassessment").find("tr:eq("+n+")").remove();
				});
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaLeadershipAssessment/list">干部考核情况列表</a></li>
		<shiro:hasPermission name="opa:opaLeadershipAssessment:edit"><li><a href="${ctx}/opa/opaLeadershipAssessment/form">干部考核情况添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="opaLeadershipAssessment" action="${ctx}/opa/opaLeadershipAssessment/list" method="post" class="breadcrumb form-search">
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
			  <!-- <td><input type="checkbox" name="test"></td> -->
				<th>姓名</th>
				<th>单位</th>
				<th>民主测评</th>
				<th>生活圈, 社交圈测评</th>
				<th>反向测评得分</th>
				<th>谈话民主推荐测评</th>
				<th>平时考核测评</th>
				<th>平时考核得分</th>
				<th>总分</th>
				<th>备注</th>
				<!-- <th>状态</th> -->
				<shiro:hasPermission name="opa:opaLeadershipAssessment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opaLeadershipAssessment">
			<tr>
			   <!-- <td><input type="checkbox" name="test"></td> -->
				<td><a href="${ctx}/opa/opaLeadershipAssessment/form?id=${opaLeadershipAssessment.id}">
					${opaLeadershipAssessment.name}
				</a>
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
			<%-- 	<td>
				    ${fns:getDictLabel(opaLeadershipAssessment.status, 'opa_opaLeadershipAssessment_status', '')}
				</td> --%>
			    <td>
               <%--  <c:if test="${opaLeadershipAssessment.status eq fns:getDictValue('待下发','opa_opaLeadershipAssessment_status','')}"> 
                <shiro:hasPermission name="opa:opaLeadershipAssessment:apply">
                <a href="${ctx}/opa/opaLeadershipAssessment/apply?id=${opaLeadershipAssessment.id}" onclick="return confirmx('确认要下发该领导干部考核情况吗？', this.href">下发</a>
                </shiro:hasPermission>
               </c:if>  --%>
				<shiro:hasPermission name="opa:opaLeadershipAssessment:edit">
    				<a href="${ctx}/opa/opaLeadershipAssessment/form?id=${opaLeadershipAssessment.id}">修改</a>
					<a href="${ctx}/opa/opaLeadershipAssessment/delete?id=${opaLeadershipAssessment.id}" onclick="return confirmx('确认要删除该领导干部考核情况吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- <input type="button" class="btn btn-primary" value="下发"> -->
	<div class="pagination">${page}</div>
</body>
</html>