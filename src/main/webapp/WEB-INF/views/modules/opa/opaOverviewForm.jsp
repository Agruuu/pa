<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>综合评分管理</title>
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
		<li><a href="${ctx}/opa/opaOverview/">评分列表</a></li>
		<li class="active"><a href="${ctx}/opa/opaOverview/form?id=${opaOverview.id}">评分<shiro:hasPermission name="opa:opaOverview:edit">${not empty opaOverview.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="opa:opaOverview:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="opaOverview" action="${ctx}/opa/opaOverview/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		 <div class="control-group">
			<label class="control-label">指标编号：</label>
			<div class="controls">
				<sys:treeselect id="opScheme" name="opaScheme.id" value="${opaScheme.id}" labelName="opaScheme.name" labelValue="${opaScheme.name}"
					title="指标" url="/opa/opaScheme/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		<%-- <div class="control-group">
			<label class="control-label">scheme_id：</label>
			<div class="controls">
				<form:input path="schemeId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">scheme_item_id：</label>
			<div class="controls">
				<form:input path="schemeItemId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">code：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">单位:</label>
			<div class="controls">
                <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
					title="单位" url="/sys/office/treeData?type=2" cssClass="required" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年底考核成绩：</label>
			<div class="controls">
				<form:input path="yearResult" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日常考核成绩：</label>
			<div class="controls">
				<form:input path="daiScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">折算得分(年考核成绩x50%+日常考核成绩x50%)x80%：</label>
			<div class="controls">
				<form:input path="convertScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">领导评价(50分)：</label>
			<div class="controls">
				<form:input path="leadingScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">县级领导评分(30分)：</label>
			<div class="controls">
				<form:input path="countyScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位互评(30分)：</label>
			<div class="controls">
				<form:input path="peerReview" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">领导班子测评(30分)：</label>
			<div class="controls">
				<form:input path="leadershipAssessment" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务对象评价(40分)：</label>
			<div class="controls">
				<form:input path="serviceRating" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核组评价(20分)：</label>
			<div class="controls">
				<form:input path="assessmentScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">综合评价得分：</label>
			<div class="controls">
				<form:input path="overallScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">创新项目加分：</label>
			<div class="controls">
				<form:input path="projectsScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">表彰奖励加分：</label>
			<div class="controls">
				<form:input path="recogintionScore" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">扣分：</label>
			<div class="controls">
				<form:input path="deduction" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="opa:opaOverview:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>