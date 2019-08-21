<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>计划任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		if(${opaPlanTask.ifNum != fns:getDictValue('非数值', 'opa_item_Num_type', '')}){
			$("#score").attr("readonly","readonly");
			$("#inputResult").show();
		}else{
			$("#score").removeAttr("readonly");		
			$("#result").val("");
			$("#inputResult").hide();
		}
	
		var ifUpload = true;
		if(${opaPlanTask.attachedPath eq ''}||${opaPlanTask.attachedPath eq null}){
			ifUpload = false;
			$("#uploadedContent").text('文件未上传，请上传文件。');
		}else{
			$("#uploadedContent").text('文件已上传，继续上传文件将覆盖原文件。');
		}
		$("#inputForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
		});
		$("#uploadForm").validate({
			submitHandler: function(form){
				var filepath = $("#myFile").val();
				if(filepath == ''){
					showTip("请选择上传文件。");
					return;
				}
				var sufFix = filepath.substring(filepath.lastIndexOf(".")+1).toLowerCase();
				var filetype = "${fns:getConfig('upload.fileType')}";
				if (filetype.indexOf(sufFix) == -1) {
					showTip("文件上传格式不正确，多个文件请压缩后上传");
					return;
				}
		        var file_size = 0;
		        var fileMaxSize = ${fns:getConfig('upload.maxUploadSize')};
		        var brow=$.browser;
		        if (brow.msie) {
					var img = new Image();
					img.src = filepath;
					file_size = img.fileSize
					alert("ie:"+file_size);
					if (img.fileSize > fileMaxSize) {
						showTip("上传文件不得大于"+ fileMaxSize/(1024*1024) +"MB。");
						return;
					} 
		        }else{
					file_size = $("#myFile")[0].files[0].size;
					var size = file_size;
					if (size > fileMaxSize) {
						showTip("上传文件不得大于"+ fileMaxSize/(1024*1024) +"MB。");
						return;
					} 
		        }
		        loading('文件上传中...');
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
	
	function calScore(val){
		
		var value = ${opaPlanTask.value}+0;
		var count = ${opaPlanTask.count}+0;
		var rs = val/count*value;
		$("#score").val(rs.toFixed(2));
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li></li>
	</ul><br/>
	<form id="uploadForm" action="${ctx}/opa/opaTaskAppeal/uploadFile" method="post" enctype="multipart/form-data" class="form-horizontal">
		<sys:message content="${message}"/>
		<div id="uploadContent" class="form-search">
			<input type="hidden" name="id" value="${opaPlanTask.id}"/>
			<input type="file" name="file" id="myFile"/>   
	        <input type="submit" class="btn btn-primary" value="上 11传"/>	
        </div>
        <div id="uploadedContent" class="form-search">
			
        </div>
	</form><br>
	<form:form id="inputForm" modelAttribute="opaPlanTask" action="${ctx}/opa/opaTaskAppeal/saveInputScore" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="attachedPath" />
		<div class="form-actions">
			<shiro:hasPermission name="opa:opaTaskAppeal:excute:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="完成上传"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
	
</body>
</html>