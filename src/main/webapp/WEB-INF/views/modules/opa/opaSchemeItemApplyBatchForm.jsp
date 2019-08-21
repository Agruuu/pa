<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>方案指标管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			onNumTypeChange($("#ifNum").val());
			$("#inputForm").validate({
				submitHandler: function(form){
					if(${opaSchemeItem.type} == '1'){
						if(!${ifParent}){
							/* if(${parentValue}==0){
								showTip('请先填写父级指标评分标准 !');
								return;
							} */
							/* if($("#value").val() > ${restValue}){
								showTip('填写分值必须小于剩余分值');
								return;
							} */
						}
					}
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
		function onNumTypeChange(val){
			if(val == '1'){
				$("#count-input").show();
			}else{
				$("#count").val('');
				$("#count-input").hide();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/opa/opaSchemeItem/apply/batch/view?id=${opaSchemeItem.id}">方案指标填报</a></li>
	
	</ul>
	<form:form id="inputForm" modelAttribute="opaSchemeItem" action="${ctx}/opa/opaSchemeItem/apply/batch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">指标类型：</label>
			<div class="controls">
				${fns:getDictLabel(opaSchemeItem.type,'opa_item_type',"")}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">方案名称：</label>
			<div class="controls">
				<form:input path="schemeName" htmlEscape="false" maxlength="64" readonly="true" class="input-xlarge required"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">父级指标:</label>
				<div class="controls">
					<sys:treeselect id="opaSchemeItem" name="itemParentId" value="${opaSchemeItem.itemParentId}" labelName="itemParentName" labelValue="${opaSchemeItem.itemParentName}"
						title="父级编号" url="/opa/opaSchemeItem/treeData?schemeId=${opaSchemeItem.schemeId}" cssClass="required" notAllowSelect="true"/>
				</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">编码：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" readonly="true" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		
		<%-- <div class="control-group">
			<label class="control-label">深度：</label>
			<div class="controls">
				<form:input path="level" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		
		<div class="control-group">
			<label class="control-label">考核方式：</label>
			<div class="controls">
				<form:select path="method" class="input-small ">
					<form:options items="${fns:getDictList('opa_item_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
		
		 <%-- <div class="control-group" id="count-input">
			<label class="control-label">数值标准：</label>
			<div class="controls">
				<form:input path="count" htmlEscape="false" class="input-mini required number"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>  --%>
		<%-- <c:if test="${opaSchemeItem.type eq fns:getDictValue('共性指标','opa_item_type','')}"> --%>
		<div class="control-group">
			<label class="control-label">分值：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <!-- <font color="red">*</font> -->
				<%-- <c:if test="${not ifParent}"><span class="help-inline">剩余 ${restValue} 分</span></c:if></span> --%>
			</div>
		</div>
		<%-- </c:if> --%>
		
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
		<%-- <div class="control-group">
			<label class="control-label">审核者：</label>
			<div class="controls">
				<form:input path="auditorId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_scheme_item_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">指标内容描述：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			 <c:if test="${(opaSchemeItem.status eq fns:getDictValue('已发布','opa_schemeItem_status','')) or (opaSchemeItem.status eq fns:getDictValue('待填报','opa_schemeItem_status','')) or (opaSchemeItem.status eq fns:getDictValue('填报已退回','opa_schemeItem_status',''))}">
			<shiro:hasPermission name="opa:opaSchemeItem:apply:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if> 
		</div>
	</form:form>
</body>
</html>