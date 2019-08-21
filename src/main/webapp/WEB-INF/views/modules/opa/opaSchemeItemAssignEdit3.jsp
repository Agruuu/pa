<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>方案指标分配</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<style type="text/css">
.ztree {
	overflow: auto;
	margin: 0;
	_margin-top: 10px;
	padding: 10px 0 0 10px;
}
</style>
</head>
<body>
	<sys:message content="${message}" />
	<div id="content" class="row-fluid">
		<div id="office" class="accordion-group span3 height-full">
			<div class="accordion-heading" >
				<a class="accordion-toggle">部门选择<i class="icon-refresh pull-right" onclick="refreshOfficeTree();"></i></a>
			</div>
			<div id="officeTree" class="ztree scoller-down"></div>
		</div>
		  <div id="user" class="accordion-group span2 height-full">
			<div class="accordion-heading">
				<a class="accordion-toggle" >被考核部门选择</a>
			</div>
			<div id="userTree" class="ztree scoller-down"></div>
		</div>  
		<div id="unselected" class="accordion-group span3 height-full">
			<div class="accordion-heading" >
				<a class="accordion-toggle">未分配指标</a>
			</div>
			<div id="unselectedTree" class="ztree scoller-down"></div>
		</div>
		<div id="btnSpan" class="span1 height-full btn-left-right">
			<a href="#" class="btn btn-primary btn-left-right" onclick="return confirmx('确认要将指标分配至被考和部门吗？', addItem)">
				<i class="icon-chevron-right"></i>
			</a>
			<a href="#" class="btn btn-primary btn-left-right" onclick="return confirmx('确认要将指标从被考核部门中移除吗？', removeItem)">
				<i class="icon-chevron-left"></i>
			</a>
		</div>
		<div id="selected" class="accordion-group span3 height-full">
			<div class="accordion-heading">
				<a class="accordion-toggle">已分配指标</a>
			</div>
			<div id="selectedTree" class="ztree scoller-down"></div>
		</div>
	</div>
	<script type="text/javascript">
		var selectedUserId = "";
		var selectedOfficeId = "";
		/* 配置详情 */
		var settingOfficeTree = {
				data:{
					simpleData:{
						enable:true,        //当true时，务必设置setting.data.simpleData内的其他参数:idKey/pIdKey/rootPId,并且让数据满足父子关系。
						idKey:"id",         //节点数据中保存唯一标识的属性名, 默认值:"id"
						pIdKey:"pId",       //节点数据中保存其父节点唯一标识的属性名称,默认值:"pid"
						rootPId:'0'         //用于修正根节点父节点数据,即pIdKey指定的属性值。 默认值:null
						}
		},
		
				callback:{                           
					/* onClick 用于捕获节点被点击的事件回调函数  默认值:null
					       参数说明  event:标准的js event 对象
					           treeId:对应zTree的treeId,便于用户操控
					           treeNode: 被点击的节点JSON数据对象
					*/
					//每次点击节点后，弹出以下节点selectedUserId,selectedOfficeId,refreshUserTree
					onClick:function(event, treeId, treeNode){  
							var id = treeNode.id == '0' ? '' :treeNode.id;
							selectedUserId = id;
							selectedOfficeId = id;
							refreshUserTree(id);
							refreshUnselectedTree(id);
							refreshSelectedTree();
					}
				}
		};
		//用户
		var settingUserTree = {
				data:{
					simpleData:{
					enable:true,
					idKey:"id",
					pIdKey:"pId",
					rootPId:'0'}
		},
		//扩展
		 check:{
			enable: true,                        //设置ztree的节点上是否显示
			nocheckInherit: false,               
			chkStyle: "checkbox",                //勾选框类型(checkbox或radio) 默认值:"checkbox"
			chkboxType: { "Y": "ps", "N": "ps" }    //勾选checkbox 对于父子节点的关系。
			
	},  
			callback:{
				  onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
					selectedUserId = id;
					refreshUnselectedTree();
					refreshSelectedTree(id); 
				}  
			}
		};
		//未分配指标
		var settingUnselectedTree = {
				data:{
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
					    schemeItemId:'schemeItemId', 
						/* schemeId:'schemeId', */
						rootPId:'0'}
		        },  
		//扩展
			check:{
				enable: true,                        //设置ztree的节点上是否显示
				nocheckInherit: false,               //只使用于初始化节点时，便于批量操作。 对于已存在的节点请利用 updateNode 方法单个节点设置。
				chkStyle: "checkbox",                //勾选框类型(checkbox或radio) 默认值:"checkbox"
				chkboxType: { "Y": "s", "N": "" }    //勾选checkbox 对于父子节点的关系。
		}, 
			callback:{
				onClick: function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
				}
			}
		};
		var settingSelectedTree = {
				data:{
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
					    schemeItemId:'schemeItemId', 
						/* schemeId:'schemeId', */
						rootPId:'0'}
		},
			check:{
				enable: true,
				nocheckInherit: false,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "" }
		}, 
			callback:{
				onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
				}
			}
		};
	
		/*
		*zTree初始化： 
		分配弹框
		*/
		function refreshOfficeTree(){
			$.getJSON("${ctx}/sys/office/treeData?type=2",
					function(data){                                     //启动树节点
				/* ztree初始化方法 fn.zTree.init */
				$.fn.zTree.init($("#officeTree"), settingOfficeTree, data).expandAll(true);
					/* console.log(data) */
			});
		}
		 refreshOfficeTree(); 
		 
		 //部门选项
		function refreshUserTree(id){
			$.getJSON("${ctx}/sys/user/treeDataAssignList?officeId="+id,
					function(data){
				$.fn.zTree.init($("#userTree"), settingUserTree, data).expandAll(true);
				 //console.log("部门选项"+data+id) 
			});
		}
		 
		// 被考核部门选择
		function getCheckedNodes() {
			var treeObj = $.fn.zTree.getZTreeObj("userTree");
			var nodes = treeObj.getCheckedNodes(true);
			var ids = "";
			for (var i = 0; i < nodes.length;i++) {
				if (ids == "") {
					ids=nodes[i].id;
				}else{
					ids += "," + nodes[i].id;
				}
			}
			return ids;
		}
		
		 //未分配指标
		function refreshUnselectedTree(){
			$.getJSON("${ctx}/opa/opaSchemeItem/TreeDataForAssign?schemeId=${schemeId}&status=${fns:getDictValue('待分配', 'opa_schemeItem_status', '')}",
					function(data){
				$.fn.zTree.init($("#unselectedTree"), settingUnselectedTree, data).expandAll(true);
			});
		}
		 
		refreshUnselectedTree();
		function refreshSelectedTree(auditorId){
			$.getJSON("${ctx}/opa/opaSchemeItem/TreeDataForAssign?schemeId=${schemeId}&auditorId="+auditorId+"&status=${fns:getDictValue('已分配', 'opa_schemeItem_status', '')}",
					function(data){
				$.fn.zTree.init($("#selectedTree"), settingSelectedTree, data).expandAll(true);
			});
		} 
		
		function removeItem(){
			if(selectedUserId == ""){
				showTip("请先选择负责人再进行指标分配！");
				return;
			}
			loading();
			/* //根据id为selectedTree获取ztree对象的方法。  前提是必须在初始化zTree以后才可以使用此方法 */
			var treeObj = $.fn.zTree.getZTreeObj("selectedTree");  
			/* 
			  获取输入框被勾选 或 未勾选的节点集合。 通过zTree 对象执行此方法
			  checked=true 获取 被勾选的节点集合   返回值为符合要求的节点结合Array
			*/
			var checkedNodes = treeObj.getCheckedNodes(true);    
			var selectedNodes = new Array();
			for(var i=0; i<checkedNodes.length; i++){
				var id = checkedNodes[i].schemeItemId;
				selectedNodes = (selectedNodes + id) + (((i + 1)== checkedNodes.length) ? '':',');
			}
			$.ajax({ 
			    type: "post", 
			    url: "${ctx}/opa/opaSchemeItem/assign/remove?ids="+selectedNodes, 
			    cache:false, 
			    async:false, 
			    dataType:'text', 
			    success: function(data){ 
			    	if(data=="ok"){
			    		showTip("指标移除成功！");
			    	}
			    	refreshUnselectedTree();
					refreshSelectedTree(selectedUserId);
			    	closeLoading();
			    } 
			});
		}
		function addItem(){
			selectedOfficeId = getCheckedNodes();
	        console.log("selectedOfficeId="+selectedOfficeId)
			if(selectedUserId == ""){
				showTip("请先选择负责人再进行指标分配！");
				return;
			}
			loading();
			var treeObj = $.fn.zTree.getZTreeObj("unselectedTree");
			var str=JSON.stringify(treeObj);
		    console.log("str="+str)
			var checkedNodes = treeObj.getCheckedNodes(true);
		    var sr=JSON.stringify(checkedNodes);
		    console.log("sr="+sr)
			var selectedNodes = new Array();
			for(var i=0; i<checkedNodes.length; i++){
				var id = checkedNodes[i].schemeItemId;
				console.log("id="+id)
				//多个id拼接成一个字符串，每个id之间用逗号隔开。最后面那个三目表达式的意思是如果是最后一个id后面就不要加逗号了
				selectedNodes = (selectedNodes + id) + (((i + 1)== checkedNodes.length) ? '':',');
				console.log("selectedNodes="+selectedNodes)
			}
			$.ajax({ 
			    type: "post", 
			    url: "${ctx}/opa/opaSchemeItem/assign/add?ids="+selectedNodes+"&auditorId="+selectedUserId+"&officeId="+selectedOfficeId, 
			    cache:false, 
			    async:false, 
			    dataType:'text', 
			    success: function(data){ 
			    	if(data=="ok"){
			    		showTip("指标分配成功！");
			    	}
			    	refreshUnselectedTree();
					refreshSelectedTree(selectedUserId);
			    	closeLoading();
			    } 
			});
		}
	</script>
</body>
</html>