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
			var data = ${fns:toJson(list)}, ids = [], rootIds = [], officeIds = [], officeIdss = [], officeNames = [], rootIdss = [], officeNotExsist = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].itemId);
			}
			ids = ',' + ids.join(',') + ',';
			console.log(ids);
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].itemParentId+',') == -1){
						rootIds.push(data[i].itemParentId);
						officeNotExsist.push(data[i].auditorOfficeId);
						rootIdss.push(data[i].itemParentId + "," + data[i].auditorOfficeId + "," + data[i].auditorName + "," + data[i].name);
				}
				var ele = data[i].auditorOfficeId;
				var itemPid = data[i].itemParentId;
		        if(officeIds.indexOf(ele) == -1){
		    	    officeIds.push(ele);
		    	    officeIdss.push(ele + "," + itemPid);
		        }
		        var ele2 = data[i].auditorName;
		        if(officeNames.indexOf(ele2) == -1){
		        	officeNames.push(ele2);
		        }
			}
			console.log("rootIds:" + rootIds);
			console.log("officeIds:" + officeIds);
			console.log("officeNames:" + officeNames);
			if(officeNotExsist[0] == undefined || officeNotExsist[0] == null || officeNotExsist[0] == 'undefined' || officeNotExsist.length == 0 || officeNotExsist == null) {
				for (var i=0; i<rootIdss.length; i++){
					addRow("#treeTableList", tpl, data, rootIdss[i], true);
				}
			} else {
				for (var i=0; i<officeIds.length; i++){
					if(rootIds.indexOf(officeIdss[i].split(",")[1]) > -1) {
						addRowsDes("#treeTableList", tpl, data, officeIdss[i], true);
					} else {
						addRowSec("#treeTableList", tpl, data, officeIdss[i], true, officeNotExsist);
					}
				}
			}
			$("#treeTable").treeTable({expandLevel : 10});
		});
		
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				console.log("fns:jsGetVal:" + ${fns:jsGetVal('row.itemParentId')});
				console.log("pid:" + pid);
				if ((${fns:jsGetVal('row.itemParentId')}) == pid.split(",")[0]){
					if(row.auditorOfficeId == null || row.auditorOfficeId == pid.split(",")[1]) {
						
					
					$(list).append(Mustache.render(tpl, {
						dict:{
							
							type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
							ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
							method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
							status: getDictLabel(${fns:toJson(fns:getDictList('opa_schemeItem_status'))}, row.status), blank123:0}, 
							pid: (root?0:pid.split(",")[0]), 
							row: row,
							btnCol: btnRender(row)
							
					}));
					addRow(list, tpl, data, row.itemId+","+row.auditorOfficeId);
					}
				}
			}
		}
		function addRowsDes(list, tpl, data, pid, root){
			/* debugger; */
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if(row.auditorOfficeId == null || row.auditorOfficeId == pid.split(",")[0]) {
					if ((${fns:jsGetVal('row.itemParentId')}) == pid.split(",")[1]){
						$(list).append(Mustache.render(tpl, {
							dict:{
								
								type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
								ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
								method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
								status: getDictLabel(${fns:toJson(fns:getDictList('opa_schemeItem_status'))}, row.status), blank123:0}, 
								pid: (root?0:pid.split(",")[1]), 
								row: row,
								btnCol: btnRender(row)
								
						}));
						console.log($(list));
						addRowsDes(list, tpl, data, row.auditorOfficeId+","+row.itemId);
					}
				}
			}
		}
		
		function addRowSec(list, tpl, data, pid, root, officeNotExsist){
			/* debugger; */
			for (var i=0; i<data.length; i++){
				var row = data[i];
				console.log("fns:jsGetVal:" + ${fns:jsGetVal('row.itemParentId')});
				if(officeNotExsist.indexOf(pid.split(",")[0]) == -1) {
					if(row.auditorOfficeId == null || row.auditorOfficeId == pid.split(",")[0]) {
							$(list).append(Mustache.render(tpl, {
								dict:{
									
									type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
									ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
									method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
									status: getDictLabel(${fns:toJson(fns:getDictList('opa_schemeItem_status'))}, row.status), blank123:0}, 
									pid: (root?0:pid.split(",")[1]), 
									row: row,
									btnCol: btnRender(row)
									
							}));
					}
				}
			}
		}
		function btnRender(row){
			var str="";
			if(row.status == "${fns:getDictValue('已填报', 'opa_schemeItem_status', '')}"){
					str+="<shiro:hasPermission name='opa:opaSchemeItem:apply:apply'><a href='${ctx}/opa/opaSchemeItem/apply/apply?id="+row.id 
					+"&schemeId="+row.schemeId+"' onclick='return apply(this.href)'>提交</a>&nbsp;</shiro:hasPermission>";
			}
			if(row.status == "${fns:getDictValue('填报已退回', 'opa_schemeItem_status', '')}"){
				str+="<shiro:hasPermission name='opa:opaSchemeItem:apply:view'><a href='${ctx}/opa/opaSchemeItem/apply/view?id="+row.id 
				+"&schemeId="+row.schemeId+"' onclick='return applyView(this.href)'>修改</a>&nbsp;</shiro:hasPermission>";
		}
			return str;
		}
		function apply(a){
			return confirmx("确认要提交该指标吗",a);
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			console.log("pageSize=",$("#pageSize").val(s))
			$("#searchForm").submit();
        	return false;
        }
		function allScore(){
			var schemeId ="${opaSchemeItem.schemeId}";
			alert(schemeId)
			if(schemeId !=null){
				 $.ajax({
		  	    	   type: "post",
		  	    	   url: "${ctx}/opa/opaSchemeItem/all/score/Issued?schemeId="+schemeId,
		  	    	   cache:false,
		  	    	   async:false, 
					   dataType:'text', 
					   success:function(data){
						   if(data=="ok"){
							   showTip("全部已下发!");
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
		<li class="active"><a href="${ctx}/opa/opaSchemeItem/apply/index">方案指标列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaSchemeItem" action="${ctx}/opa/opaSchemeItem/apply/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>指标名称：</label>
				<form:select path="schemeId" class="input-xlarge">
					<form:options items="${schemeAuditedList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>方案名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<%-- <li><label>状态：</label>
				<form:select path="status" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_schemeItem_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>指标类型：</label>
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
					<form:options items="${fns:getDictBlankList('opa_item_Num_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<%-- <li><label>起始日期：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSetItemValue" class="btn btn-primary" type="button" value="指标批量填报"/></li>
			<!-- <li class="btns"><input id="btnSumit" class="btn btn-primary" type="submit" value="全部下发" onClick="allScore()"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<script>
		//打开指标分配窗口
		$("#btnSetItemValue").click(function(){
			top.$.jBox.open("iframe:${ctx}/opa/opaSchemeItem/apply/batch/index?schemeId="+$("#schemeId").find("option:selected").val(), "指标批量填报",800,$(top.document).height()-100,{
				buttons:{"关闭":true
					}, 
				closed:function(){
					location.reload();
					},
					bottomText:"点选左侧指标后，即可即可进行指标填报。",
				submit:function(v, h, f){
				}, 
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		});
	</script>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>内容描述</th>
				<th>指标类型</th>
				<th>考核方式</th>
				<th>数值类型</th>
				<th>分值</th>
				<!-- <th>数值标准</th> --> <!-- 	<td>
					{{row.count}}
				</td> -->
				<!-- <th>审核者</th> -->
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="treeTableList">
		<c:forEach items="${page.list}" var="opaSchemeItem">
		</c:forEach>
		</tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.itemId}}" pId="{{pid}}">
				<td><a href="${ctx}/opa/opaSchemeItem/apply/view?id={{row.id}}">
					{{row.name}}
				</a></td>
				<td>
					{{row.content}}
				</td>
				<td>
					{{dict.type}}
				</td>
				<td>
					{{dict.method}}
				</td>
				<td>
					{{dict.ifNum}}
				</td>
				<td>
					{{row.value}}
				</td>
			
				<!-- <td>
					{{row.auditorName}}
				</td>-->
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