<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jquery -->
 
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script> 
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css"> 
 
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-3.3.1.min.js"></script> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/static/js/bootstrap.js"></script> --%>
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/bootstrap.css">--%>
 
<script type="text/javascript">
$(function(){
	$("#all").on("click",function(){
		  if($(this).prop("checked")){
		    $("input[name='aa']").each(function(){
		      $(this).prop("checked","checked");
		    })
		  }
		  else{
		    $("input[name='aa']").each(function(){
		      $(this).prop("checked",false);
		      $(this).removeAttr("checked");//若上一行代码失效，可以用这行代码
		    })
		  }
		  
		})
		
		$("#del_selected").on("click",function(){
			var userIds = "";
			$("input[name='aa']").each(function(){
			     if($(this).prop("checked")){
			    	 userIds += $(this).attr("userId")+"※";
			     }
			})
			
			if(userIds==""){
				alert("请选择需要删除的用户");
				return;
			}
			
			alert("需要删除的用户id为："+userIds+"\n"+"然后ajax传参数到后台异步更新并返回操作结果。")
		})
})
</script>
 
 
</head>
<body>
<div id="main" style="width:400px;height:auto;margin:auto;margin-top:40px;"> 
  	<table class="table table-bordered">
  	<thead>
  	<tr>
  	<th><input  id="all" type="checkbox" /></th>
  	<th>姓名</th>
  	<th>年龄</th>
  	</tr>
  	</thead>
  	<tr>
  	<td><input userId="001" name="aa" type="checkbox" /></td>
  	<td>Demonor</td>
  	<td>22</td>
  	</tr>
  	
  	<tr>
  	<td><input userId="002" name="aa" type="checkbox" /></td>
  	<td>Amy</td>
  	<td>19</td>
  	</tr>
  	
  	<tr>
  	<td><input userId="003" name="aa" type="checkbox" /></td>
  	<td>Sky</td>
  	<td>20</td>
  	</tr>
  	
  	<tbody>
  	
  	</tbody>
  	
  	</table>	
  	
  	<button class="btn btn-sm btn-danger" id="del_selected" style="width:80px;height:30px;">删除</button>
  	</div>
  	
  			 
</body>
</html> 
