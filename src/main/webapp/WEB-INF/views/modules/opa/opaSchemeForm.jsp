<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考核方案管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit()
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
		function apply(){
			$("#inputForm").attr("action", "${ctx}/opa/opaScheme/apply");
			$("#inputForm").submit();
		}
		
		
		function save(){
			$("#inputForm").attr("action", "${ctx}/opa/opaScheme/save");
			$("#inputForm").submit();
			//获取按钮的对象
			var btn = document.getElementById("btnSubmit");
			//为按钮绑定单击响应函数
			btn.onclick = function(){
				//点击以后使按钮不可用
				this.disabled=true;
			};
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/opa/opaScheme/index">考核指标列表</a></li>
		<li class="active"><a href="${ctx}/opa/opaScheme/form?id=${opaScheme.id}">考核指标<shiro:hasPermission name="opa:opaScheme:edit">${not empty opaScheme.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="opa:opaScheme:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="opaScheme" action="${ctx}/opa/opaScheme/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">指标编号：</label>
			<div class="controls">
				<sys:treeselect id="itemParent" name="itemParent.id" value="${opaScheme.itemParent.id}" labelName="" labelValue="${opaScheme.itemParent.name}"
					title="指标" url="/opa/opaItem/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
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
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
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
					value="<fmt:formatDate value="${opaScheme.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截止日期：</label>
			<div class="controls">
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${opaScheme.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">审核者：</label>
			<div class="controls">
				<sys:treeselect id="auditor" name="auditor.id" value="${opaScheme.auditor.id}" labelName="" labelValue="${opaScheme.auditor.name}"
					title="用户" url="/sys/user/treeData1" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		 <div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<%-- <form:option value="" label=""/> --%>
					<option value="1">制定中</option>
					<%-- <form:options items="${fns:getDictList('opa_scheme_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>  --%>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		  <div class="control-group">
			<label class="control-label">考核内容：</label>
			<div class="controls">
				<form:textarea path="examination"  htmlEscape="false" rows="4" maxlength="100000" class="input-xxlarge "/>
			</div>
		</div> 
		 <div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls"> 
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="25500" class="input-xxlarge "/>
			</div>
		</div> 
		<div class="form-actions">
			<shiro:hasPermission name="opa:opaScheme:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>&nbsp;</shiro:hasPermission>
			<%-- <shiro:hasPermission name="opa:opaScheme:apply"><input id="btnApply" class="btn btn-primary" type="button" value="提交" onclick="return confirmx('确认要提交该考核方案吗？', apply)"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>