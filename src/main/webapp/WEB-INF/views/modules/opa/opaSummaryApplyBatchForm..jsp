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
							if(${parentValue}==0){
								showTip('请先填写父级指标评分标准');
								return;
							}
							if($("#value").val() > ${restValue}){
								showTip('填写分值必须小于剩余分值');
								return;
							}
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
			<li class="active"><a href="${ctx}/opa/opaSummary/apply/batch/view">方案指标填报</a></li>
	
	</ul>
	<form:form id="inputForm" modelAttribute="opaSummary" action="${ctx}/opa/opaSummary/apply/batch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">日常考核成绩：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">年底考核成绩：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要领导评价（50分）：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">县级领导评价（30分）：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位互评（30分）：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">领导班子测评（30分）：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务对象评价（40分）：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div> 	
		<div class="control-group">
			<label class="control-label">考核组评价（30分）：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div> 	
		<div class="control-group">
			<label class="control-label">综合评价得分：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div> 	
		<div class="control-group">
			<label class="control-label">创新项目加分：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div> 	
		<div class="control-group">
			<label class="control-label">表彰奖励加分：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
			</div>
		</div> 	
		<div class="control-group">
			<label class="control-label">扣分：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" class="input-mini required number"/><span class="help-inline">分 <font color="red">*</font>
				</span>
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