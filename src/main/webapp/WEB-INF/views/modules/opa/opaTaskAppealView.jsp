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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/opa/opaTaskAppeal/index">计划任务列表</a></li>
		<li class="active"><a href="${ctx}/opa/opaTaskAppeal/view?parentId=${parentId}">子任务添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="opaTaskAppeal" action="${ctx}/opa/opaTaskAppeal/save?parentId=${parentId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="office"/>
		<form:hidden path="auditorId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">计划名称：</label>
			<div class="controls">
				<form:select path="planId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${planList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父级指标：</label>
			<div class="controls">
				<sys:treeselect id="itemParentId" name="itemParentId" value="${opaPlanTask.itemParentId}" labelName="itemParentName" labelValue="${opaPlanTask.itemParentName}"
					title="指标" url="/opa/opaPlanTask/treeData?planId=${opaPlanTask.planId}&type=1" cssClass="required" allowClear="false" notAllowSelect="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">指标内容描述：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数值类型：</label>
			<div class="controls">
				<form:select path="ifNum" class="input-mini" onchange="onNumTypeChange(this.value);" >
					<form:options items="${fns:getDictList('opa_item_Num_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group" id="count-input">
			<label class="control-label">数值标准：</label>
			<div class="controls">
				<form:input path="count" htmlEscape="false" class="input-mini required number"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考评标准：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分<font color="red">*</font>
				<c:if test="${not ifParent}"><span class="help-inline">剩余 ${restValue} 分</span></c:if></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-mini required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起始日期：</label>
			<div class="controls">
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${opaSchemeItem.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截止日期：</label>
			<div class="controls">
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${opaSchemeItem.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<!-- <shiro:hasPermission name="opa:opaPlanTask:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> -->
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>