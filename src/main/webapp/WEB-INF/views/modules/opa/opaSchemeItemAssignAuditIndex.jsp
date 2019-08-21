<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>方案指标管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].itemId);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].itemParentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].itemParentId+',') == -1){
						rootIds.push(data[i].itemParentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 10});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.itemParentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict:{
							type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
							ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
							method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
							status: getDictLabel(${fns:toJson(fns:getDictList('opa_schemeItem_status'))}, row.status), blank123:0}, 
							pid: (root?0:pid), 
							row: row,
							btnCol: btnRender(row),
					}));
					addRow(list, tpl, data, row.itemId);
				}
			}
		}
		function auditPass(a){
			return confirmx("确认要审核通过该任务吗",a);
		}
		function auditReturn(a){
			return confirmx("确认要退回该任务吗",a);
		}
		function returnInput(a){
			return promptRemarkx("请输入退回原因", "退回原因(最多50个汉字)", a, true);
		}
		function btnRender(row){
			var str="";
			if(row.status == "${fns:getDictValue('待审核', 'opa_schemeItem_status', '')}"){
					//上报
					str+="<shiro:hasPermission name='opa:opaSchemeItem:assign:audit:pass'><a href='${ctx}/opa/opaSchemeItem/assign/audit/pass?id="+row.id 
						+"&schemeId="+row.schemeId+"' onclick='return auditPass(this.href)'>审核通过</a>&nbsp;</shiro:hasPermission>";
					str+="<shiro:hasPermission name='opa:opaSchemeItem:assign:audit:return'><a href='${ctx}/opa/opaSchemeItem/assign/audit/return?id="+row.id 
						+"&schemeId="+row.schemeId+"&reason=' onclick='return returnInput(this.href)'>退回</a>&nbsp;</shiro:hasPermission>";
			}
			return str;
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //全选
		 function allCheck(check){
	　　　　var checkbox=document.getElementsByName("shi");
	       var schemeId;
	       if(checkbox.length){
	    	   for(i=0;i<checkbox.length;i++){
	    		   schemeId=checkbox[i].value;
	    	   }
	       }
	　　　　if(check.checked){//true
	　　　　　　for(var i=0;i<checkbox.length;i++){
	　　　　　　　　checkbox[i].checked="checked";
	　　　　　　}
	　　　　}else{
	　　　　　　for(var i=0;i<checkbox.length;i++){
	　　　　　　　　checkbox[i].checked="";
	　　　　　　}
	　　　　}
	　　} 
        
        //全部审核
        function allPass(){
        	var checkbox=document.getElementsByName("shi");
        	var schemeId;
  	       if(checkbox.length){
  	    	   for(i=0;i<checkbox.length;i++){
  	    		   schemeId=checkbox[i].value;
  	    	   }
  	       }
  	       $.ajax({
  	    	   type: "post",
  	    	   url: "${ctx}/opa/opaSchemeItem/audit/all/pass?schemeId="+schemeId,
  	    	   cache:false,
  	    	   async:false, 
			   dataType:'text',
			   success:function(schemeId){
				   if(schemeId==""){
					   showTip("全部审核通过!");
				   }
				   closeLoading();
			   }
  	       });
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaSchemeItem/assign/audit/index">方案指标列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaSchemeItem" action="${ctx}/opa/opaSchemeItem/assign/audit/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>指标名称：</label>
				<form:select path="schemeId" class="input-xlarge">
					<form:options items="${schemeAuditedList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>指标名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li> --%>
			<%-- <li><label>状态：</label>
				<form:select path="status" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_scheme_item_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>指标类型：</label>
				<form:select path="type" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考核方式：</label>
				<form:select path="method" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>数值类型：</label>
				<form:select path="ifNum" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('opa_item_Num_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<li><label>起始日期：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>指标类型</th>
				<th>备注信息</th>
				<th>状态</th>
				<th>操作 <input type="checkbox" name="all" value="全选" onclick="allCheck(this)"/>
				  <input type="button" value="全部审核" onClick="allPass()"/></th>
				<!-- <th>全选<input type="checkbox" name="all" value="全选" onclick="allCheck(this)"/>
				     <input type="button" value="全部审核" onClick="allPass()"/>
				</th> -->
				
			</tr>
		</thead>
		<tbody id="treeTableList">
		<c:forEach items="{$.page.list}" var="opaSchemeItem">
		</c:forEach>
		</tbody> 
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.itemId}}" pId="{{pid}}">
				<td><a href="${ctx}/opa/opaSchemeItem/assign/audit/view?id={{row.id}}">
					{{row.name}}
				</a></td>
				<td>
					{{dict.type}}
				</td>
                <td>
                    {{row.remarks}}
                </td>
				<td>
					{{dict.status}}
				</td>
				<td>   <input type="checkbox" name="shi" value="{{row.schemeId}}">
    				{{{btnCol}}}
				</td>
		</tr>
	</script>
	<div class="pagination">${page}</div>
</body>
</html>