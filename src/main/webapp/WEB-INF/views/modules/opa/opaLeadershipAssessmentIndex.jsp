<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<sys:message content="${message}"/>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">部门结构<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="officeTree" class="ztree scoller-down"></div> 
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="opaLeadershipAssessmentContent" src="${ctx}/opa/opaLeadershipAssessment/list?id=&parentIds=" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
	var settingOfficeTree = {
			data:{
				simpleData:{
					enable:true,   /* 默认值为false,如果设置为true,务必设置setting.data.simpleData内的其他参数:idKey/pIdKey/rootPId,并且让数据满足父子关系 */
					idKey:"id",    /* 当前节点的id。[setting.data.simpleData.enable=true]时生效 */
					pIdKey:"pId",  //节点的父节点id
					rootPId:'0'    /* 用于修正根节点父节点数据,即pIdKey指定德属性值。[setting.data.simpleData.enable=true 时生效] */
				}
				
			},
			callback:{                                 //回调函数
				onClick:
					function(
							event,
							treeId,
							treeNode){    //对得到的json数据进行过滤,加载树的时候执行
					var id = treeNode.id == '0' ? '' :treeNode.id;
					$('#opaLeadershipAssessmentContent').attr("src","${ctx}/opa/opaLeadershipAssessment/list?office.id="+id+"&office.name="+treeNode.name);
				}
			}
	};
	function refreshTree(){
		$.getJSON("${ctx}/sys/office/treeOfficeData",function(data){
			$.fn.zTree.init($("#ztree"), settingOfficeTree, data).expandAll(true);
		});
	}
	refreshTree();
	//zTree初始化： 
	function refreshTree(){
		$.getJSON("${ctx}/sys/office/treeOfficeData?type=2",
				function(data){
			$.fn.zTree.init($("#officeTree"), settingOfficeTree, data).expandAll(true);
		});
	}
	refreshTree();
	/* function refreshUserTree(id){
		$.getJSON("${ctx}/sys/user/treeDataAssign?officeId="+id,
				function(data){
			$.fn.zTree.init($("#userTree"), settingUserTree, data).expandAll(true)	
		});
	} */
			
		var leftWidth = 240; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".officeTree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/autoHeader/src/js/table2excel.js"></script>
</body>
</html>