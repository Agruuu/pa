<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="${ctxStatic}/css/style.css" rel="stylesheet" />
<title> 实绩考核 - 登录系统</title>

</head>
<body>

	<!-- 头部 -->
	<div class="header loginHeader">
		 <div class="bgImg"><img src="${ctxStatic}/images/3E/topBg.png" /></div>
    <div class="contain">
        <ul class="nav">
            <li>
                <p class="title" id="topNavTitle">智慧党建平台</p>
                <ul class="navDownList" id="navDownList">
                    <li class="dangjian"><a href="#">基层党建</a></li>
                    <li class="jiaoyu"><a href="#">干部教育</a></li>
                    <li class="kaoping"><a href="#">实绩考核</a></li>
                    <li class="malan"><a href="#">马兰英才</a></li>
                    <li class="jiancha"><a href="#">纪检监察</a></li>
                   <li class="zhanxian"><a href="#">统一战线</a></li>
                </ul>
            </li>
        </ul>
    </div>
	</div>




	<!-- 登录主体区域 -->
	<div class="login">
		<div class="bgImg">
			<img src="${ctxStatic}/images/online/loginBg.png" />
		</div>
		<div class="contain">
			<!-- 登录框 -->
			<div class="loginBox" id="loginBox" style="display: block;">
				<p class="bg"></p>
				<div class="cont">
					<h3 class="form-signin-heading">${fns:getConfig('productName')}</h3>
					<p>&nbsp;</p>
					<!-- <p class="title">在线考评 - 登录系统</p> -->
					<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
						<div class="label">
							<span class="userIcon icon"></span> 
							<input type="text" class="text" placeholder="用户名" value="${username}" id="username" name="username"/>
							<%-- <input id="username" name="username" class="text" value="${username}"> --%>
						</div>
						<div class="label">
							<span class="pwdIcon icon"></span>
							<input type="password" class="text" placeholder="密码" id="password" name="password"/>
							<!-- <input type="password" id="password" name="password" class="text"> -->
						</div>
						<div class="radio">
							<label>
							<input type="checkbox" /> 记住登录</label>
						</div>
						<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
					</form>
				</div>
			</div>
		</div>
	</div>




	<!-- 底部 -->
	<div class="footer">
		<div class="bgImg">
			<img src="${ctxStatic}/images/3E/footerBg.jpg" />
		</div>
		<div class="cont">
			<img src="${ctxStatic}/images/3E/footerLogo.png" />
			<p style="color:black;font-size:16px;">主办单位：中共鄂托克前旗委员会组织部 ｜ 技术支持：内蒙古奥尔弘科技有限公司</p>
			<p style="color:black;font-size:16px;">400-805-2232 京 ICP 备 11008151 号 京公网安备 11010802014853</p>
		</div>
	</div>
	<script src="${ctxStatic}/js/jquery-1.12.4.min.js"></script>
	<script src="${ctxStatic}/js/common.js"></script>
</body>
</html>
