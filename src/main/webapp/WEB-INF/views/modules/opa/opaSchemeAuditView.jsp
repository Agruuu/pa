<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考核方案审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		function auditReturn(){
			$("#inputForm").attr("action", "${ctx}/opa/opaScheme/audit/return");
			$("#inputForm").submit();	
		}
		function auditPass(){
			$("#inputForm").attr("action", "${ctx}/opa/opaScheme/audit/pass");
			$("#inputForm").submit();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/opa/opaScheme/audit/index">考核方案审核列表</a></li>
		<li class="active"><a href="${ctx}/opa/opaScheme/audit/view?id=${opaScheme.id}">考核方案明细</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="opaScheme" action="${ctx}/opa/opaScheme/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">父级指标编号：</label>
			<div class="controls">
				<sys:treeselect id="itemParent" name="itemParent.id" value="${opaScheme.itemParent.id}" labelName="" labelValue="${opaScheme.itemParent.name}"
					title="指标" url="/opa/opaItem/treeData" cssClass="required" allowClear="false" notAllowSelect="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">编码：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" readonly="true" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">深度：</label>
			<div class="controls">
				<form:input path="level" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">起始日期：</label>
			<div class="controls">
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${opaScheme.dateFrom}" pattern="yyyy-MM-dd"/>" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截止日期：</label>
			<div class="controls">
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${opaScheme.dateTo}" pattern="yyyy-MM-dd"/>" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核者：</label>
			<div class="controls">
				<sys:treeselect id="auditor" name="auditor.id" value="${opaScheme.auditor.id}" labelName="" labelValue="${opaScheme.auditor.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="false" notAllowSelect="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_scheme_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		 <div class="control-group">
			<label class="control-label">考核内容：</label>
			<div class="controls">
				<form:textarea path="examination" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${opaScheme.status eq fns:getDictValue('待审核','opa_scheme_status','')}">
				<shiro:hasPermission name="opa:opaScheme:audit:pass"><input id="btnSubmit" class="btn btn-primary" type="button" value="审核通过" onclick="return confirmx('确认要通过该考核方案吗？', auditPass)"/>&nbsp;</shiro:hasPermission>
				<shiro:hasPermission name="opa:opaScheme:audit:return"><input id="btnApply" class="btn btn-primary" type="button" value="退回" onclick="return confirmx('确认要退回该考核方案吗？', auditReturn)"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>