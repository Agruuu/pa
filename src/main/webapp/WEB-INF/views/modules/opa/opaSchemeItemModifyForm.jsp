<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>方案指标管理</title>
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
		function save(){
			$("#inputForm").attr("action", "${ctx}/opa/opaSchemeItem/save");
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/opa/opaSchemeItem/assign/index">指标分配列表</a></li>
		<li class="active"><a href="${ctx}/opa/opaSchemeItemModify/form?id=${opaSchemeItem.id}">指标分配修改</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="opaSchemeItem" action="${ctx}/opa/opaSchemeItem/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">方案名称：</label>
			<div class="controls">
				<form:input path="schemeName" htmlEscape="false" maxlength="64" readonly="true" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父级指标编号：</label>
			<div class="controls">
				<sys:treeselect id="opaSchemeItem" name="itemParentId" value="${opaSchemeItem.itemParentId}" labelName="itemParentName" labelValue="${opaSchemeItem.itemParentName}"
					title="指标" url="/opa/opaSchemeItem/treeData?schemeId=${opaSchemeItem.schemeId}" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
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
		<div class="control-group">
			<label class="control-label">指标类型：</label>
			<div class="controls">
				<form:select path="type" readonly="true" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_item_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">深度：</label>
			<div class="controls">
				<form:input path="level" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">指标内容描述：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">考核方式：</label>
			<div class="controls">
				<form:select path="method" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_item_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否数值类型：</label>
			<div class="controls">
				<form:input path="ifNum" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评标准：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数值标准：</label>
			<div class="controls">
				<form:input path="count" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">起始日期：</label>
			<div class="controls">
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${opaSchemeItem.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截止日期：</label>
			<div class="controls">
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${opaSchemeItem.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">审核者：</label>
			<div class="controls">
				<sys:treeselect id="auditorId" name="auditorId" value="${opaSchemeItem.auditorId}" labelName="" labelValue="${opaSchemeItem.auditorName}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="false" notAllowSelect="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<%--  <div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_scheme_item_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div> 
		</div>  --%>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="opa:opaSchemeItem:assign:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>&nbsp;
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="opa:opaSchemeItem:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>