<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title>任性猫美容美发综合运营平台</title>
<link rel="shortcut icon" type="image/x-icon"
	href="${ctxPath }/favicon.ico" />
<style type="text/css">
.avatar {
	width: 64px;
	height: 64px;
}

#downloadFile>div>a {
	font-size: 20px;
	margin-left: 10px;
	margin-top: 10px;
}
</style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg  pace-done"
	style="overflow: hidden">
	<div class="pace  pace-inactive">
		<div class="pace-progress" data-progress-text="100%"
			data-progress="99" style="width: 100%;">
			<div class="pace-progress-inner"></div>
		</div>
		<div class="pace-activity"></div>
	</div>
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>

			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					<li class="nav-header">
						<div class="dropdown profile-element">
							<span><img alt="image" class="img-circle avatar"
								src="${ctxPath}/icon_logo.png"></span> <a data-toggle="dropdown"
								class="dropdown-toggle" href="javascript:void(0);"
								aria-expanded="false"> <span class="clear"> <span
									class="block m-t-xs"><strong class="font-bold">${ffaccount.accountName }</strong></span>
									<span class="text-muted text-xs block">超级管理员</span>
							</span>
							</a>
							<ul class="dropdown-menu animated fadeInRight m-t-xs">
								<li><a class="J_menuItem"
									href="${ctxPath}/account/toChangePasswd.do">修改登录密码</a></li>
								<li><a id="testMessage" href="javascript:void(0);">发送测试消息</a></li>
							</ul>
						</div>
						<div class="logo-element">任性猫</div>
					</li>
					<c:forEach items="${functions}" var="function">
						<li><c:if test="${function.action != \"\"}">
								<a class="J_menuItem" href="${ctxPath}${function.actionUrl}"><i
									class="fa ${function.code}"></i> <span class="nav-label">${function.name}</span>
									<c:if test="${fn:length(function.functionList) > 0}">
										<span class="fa arrow"></span>
									</c:if></a>
							</c:if> <c:if test="${function.action == \"\"}">
								<a href="#"><i class="fa ${function.code}"></i> <span
									class="nav-label">${function.name}</span> <c:if
										test="${fn:length(function.functionList) > 0}">
										<span class="fa arrow"></span>
									</c:if></a>
							</c:if> <c:if test="${fn:length(function.functionList) > 0}">
								<ul class="nav nav-second-level">
									<c:forEach items="${function.functionList}" var="subFunction">
										<li><a class="J_menuItem"
											href="${ctxPath}${subFunction.actionUrl}">${subFunction.name}</a></li>
									</c:forEach>
								</ul>
							</c:if></li>
					</c:forEach>
				</ul>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row content-tabs">
				<div class="roll-nav roll-left">
					<a class="navbar-minimalize btn btn-primary"
						style="width: 100%; height: 100%" href="javascript:void(0);"><i
						class="fa fa-bars"></i> </a>
				</div>
				<button class="roll-nav roll-left J_tabLeft" style="left: 40px">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs" style="margin-left: 80px;">
					<div class="page-tabs-content">
						<a href="javascript:void(0);" class="active J_menuTab"
							data-id="default">首页</a>
					</div>

				</nav>

				<button class="roll-nav roll-right J_tabRight" style="right: 220px;">
					<i class="fa fa-forward"></i>
				</button>
				<button class="roll-nav roll-right J_tabRight" id="helpFile"
					style="right: 140px; width: 80px;">帮助文档</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>

				</div>
				<a href="javascript:void(0);"
					class="roll-nav roll-right J_tabExit logout"><i
					class="fa fa fa-sign-out"></i> 退出</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" id="iframe0" width="100%" height="100%"
					src="welcome.html" frameborder="0" data-id="default" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">
					Copyright © 2013-2016 <a href="http://rxmao.cn" target="_blank">任性猫有限公司</a>
					版权所有
				</div>
			</div>
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container">

					<li class="active"><a data-toggle="tab" href="#tab-1"> <i
							class="fa fa-gear"></i> 主题
					</a></li>
				</ul>

				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 主题设置
							</h3>
							<small><i class="fa fa-tim"></i>
								你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
						</div>
						<div class="skin-setttings">
							<div class="title">主题设置</div>
							<div class="setings-item">
								<span>收起左侧菜单</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="collapsemenu"
											class="onoffswitch-checkbox" id="collapsemenu"> <label
											class="onoffswitch-label" for="collapsemenu"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定顶部</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="fixednavbar"
											class="onoffswitch-checkbox" id="fixednavbar"> <label
											class="onoffswitch-label" for="fixednavbar"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span> 固定宽度 </span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="boxedlayout"
											class="onoffswitch-checkbox" id="boxedlayout"> <label
											class="onoffswitch-label" for="boxedlayout"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a href="javascript:void(0);"
									class="s-skin-0"> 默认皮肤 </a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a href="javascript:void(0);"
									class="s-skin-1"> 蓝色主题 </a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a href="javascript:void(0);"
									class="s-skin-3"> 黄色/紫色主题 </a>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="downloadFile" style="display: none">
		<div>
			<a href="${ctxPath}/colligateReport/downloadHelpFile.do?flag=1">任性猫项目微信用户端操作手册_20160923.docx</a>
		</div>
		<div>
			<a href="${ctxPath}/colligateReport/downloadHelpFile.do?flag=2">任性猫项目微信技师端操作手册_20160923.doc</a>
		</div>
		<div>
			<a href="${ctxPath}/colligateReport/downloadHelpFile.do?flag=3">任性猫项目后台用户操作手册_20160923.doc</a>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$("#helpFile").click(function() {
				layer.open({
					type : 1,
					title : "请下载帮助文档",
					area : [ '500px', '300px' ],
					content : $('#downloadFile')
				//这里content是一个DOM
				});
			});
			$(".logout").click(function() {
				eh.utils.ajax.send({
					type : 'GET',
					url : URL.logout,
					dataType : 'text',
					success : function(data) {
						eh.utils.token.quit();
						window.location.href = _ctxPath;
					},
					error : function(data) {
						alert("error");
					}
				});
			});

			$("#testMessage").click(function() {
				$.ajax({
					url : '${ctxPath}/message/test.do',
					type : "GET",
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					async : true,
					success : function(data) {
						alert("sent");
					},
					error : function(data) {
						alert("测试消息出错：" + data);
					},
				});
			});

			function requestMessage() {
				$.ajax({
					url : '${ctxPath}/message/m.do',
					type : "POST",
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					async : true,
					success : function(data) {
						alert($.toJSON(data));
						requestMessage()
					},
					error : function(data) {
						var status = data.status;
						if (status == 0) { // 断线
							$("#errorMessage").text("服务不可用，请稍后...");
						} else {
							$("#errorMessage").text("服务器连接失败：" + status);
						}
						setTimeout(requestMessage, 1000);
					},
				});
			}
			requestMessage();
		});
	</script>
</body>
</html>
