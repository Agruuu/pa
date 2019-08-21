<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>方案指标分配</title>
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

		function assignApply(a){
			return confirmx("确认要下发该指标分配吗", a);
		}
		/*
		opa:opaSchemeItem:assign:edit; 指标分配
		
		*/
		function btnRender(row){
			var str="";
			if(row.status == "${fns:getDictValue('已分配', 'opa_schemeItem_status', '')}"){
				str+="<shiro:hasPermission name='opa:opaSchemeItem:assign:edit'><a href='${ctx}/opa/opaSchemeItem/assign/apply?id="+row.id
					+"' onclick='return assignApply(this.href)'>下发</a>&nbsp;</shiro:hasPermission>";
			}
	    	
			if(row.status == "${fns:getDictValue('分配已退回', 'opa_schemeItem_status', '')}"){
				str+="<shiro:hasPermission name='opa:opaSchemeItem:assign:edit'><a href='${ctx}/opa/opaSchemeItem/assign/view?id="+
						row.id+"' onclick='return form(this.href)'>修改</a>&nbsp;</shiro:hasPermission>";
			} 
		/* 	if(row.status == "${fns:getDictValue('分配已退回', 'opa_schemeItem_status', '')}"){
				str+="<a href='${ctx}/opa/opaSchemeItem/delete?id="+row.id+"' onclick='return confirmx('确认要删除该指标分配吗？',this.href)'>删除</a>&nbsp;";
			}  */
			 
			return str;
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function allIssud(){
			var schemeId="${opaSchemeItem.schemeId}";
			if(schemeId!=null){
				//debugger
				//alert(schemeId)
				//return confirmx("确认要将所有下发指标分配吗");
				 $.ajax({
		  	    	   type: "post",
		  	    	   url: "${ctx}/opa/opaSchemeItem/all/issud?schemeId="+schemeId,
		  	    	   cache:false,
		  	    	   async:false, 
					   dataType:'text', 
					   success:function(data){
						   if(data=="ok"){
							   showTip("全部下发成功!");
						   }
						   closeLoading();
					   } 
		  	       });
				 
			}
			
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaSchemeItem/assign/index">分配指标列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaSchemeItem" action="${ctx}/opa/opaSchemeItem/assign/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>指标名称：</label>
				<form:select path="schemeId" class="input-xlarge">
					<form:options items="${schemeAuditedList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>方案名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li> --%>
			<%-- <li><label>状态：</label>
				<form:select path="status" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_schemeItem_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			
			<%-- <li><label>指标类型：</label>
				<form:select path="type" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			
			<%-- <li><label>考核方式：</label>
				<form:select path="method" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<%-- <li><label>数值类型：</label>
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
			<li class="btns"><input id="btnSetAuditor" class="btn btn-primary" type="button" value="分配指标" /></li>
			<li class="btns"> <input id="btnSetAuditor" class="btn btn-primary" type="button" value="全部下发" onClick="allIssud()"/></li>
				<script type="text/javascript">
				/* 	打开指标分配窗口 */
					var schemeId = $("#schemeId").find("option:selected").val();
					$("#btnSetAuditor").click(function(){
						top.$.jBox.open("iframe:${ctx}/opa/opaSchemeItem/assign/edit?schemeId="+schemeId, "方案指标分配",1000,$(top.document).height()-100,{
							buttons:{"关闭":true}, closed:function(){location.reload();}, bottomText:"通过选择部门，然后为列出的部门分配指标。",submit:function(v, h, f){
								
							}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
			<li class="clearfix">
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>指标类型</th>
			    <th>分配部门</th> 
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="treeTableList">
		
		</tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.itemId}}" pId="{{pid}}">
				<td><a href="${ctx}/opa/opaSchemeItem/assign/view?id={{row.id}}">
					{{row.name}}
				</a></td>
				<td>
					{{dict.type}}
				</td>
                <td>
                    {{row.auditorName}}
                </td>
				<td>
					{{dict.status}}
				</td>
				<td>
					{{{btnCol}}}
				</td>	
		</tr>
	</script>
	<div class="pagination">${page}</div>
</body>
</html>