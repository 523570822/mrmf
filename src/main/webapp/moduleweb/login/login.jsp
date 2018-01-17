<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title>任性猫美容美发综合运营平台-登录</title>
<link rel="shortcut icon" type="image/x-icon"
	href="${ctxPath }/favicon.ico" />
<style>
.form-control2 {
	width: 60%;
	display: inline;
}
</style>
<script src="${ctxPath}/moduleweb/login/login.js" type="text/javascript"></script>
<script type="text/javascript">
	function prt() {
	/*	if (!("print" in document.getElementById("jatoolsPrinter"))) {
			layer
					.alert("请安装打印控件（若未自动弹出，请点<a href=\"${ctxPath}/setup.exe\">此处下载</a>安装），并设置浏览器安全属性：<br>菜单[工具]-->Internet选项-->安全-->受信任的站点-->[站点]按钮，添加站点http://rxmao.cn<br><font color=\"red\">注意：不按此操作打印功能将不可用!!!仅支持IE内核浏览器</font>");
		}*/
	}
	$().ready(function() {
		<c:if test="${needVerify }">
		var intervalId, timepass;
		$("#verifyCodeBtn").click(function() {
			var accountName = $("#accountName").val();
			if (!accountName || accountName == "") {
				layer.alert("请输入登录用户名");
				$("#accountName").focus();
				return;
			}
			$.post('${ctxPath}/login/sendVerifyCode.do', {
				accountName : accountName
			}, function(data, status) {
				if (data && data.success) {
					layer.alert("验证码已发送");
					timepass = 60;
					if (intervalId) {
						clearInterval(intervalId);
					}
					intervalId = setInterval(function() {
						if (timepass > 0) {
							$("#verifyCodeBtn").val("已发送(" + timepass + ")");
							$("#verifyCodeBtn").disable();
							timepass--;
						} else {
							$("#verifyCodeBtn").val("重新发送");
							$("#verifyCodeBtn").enable();
							if (intervalId) {
								clearInterval(intervalId);
							}
						}
					}, 1000);
				} else {
					layer.alert("验证码发送失败，请重试!");
				}
			});
		});

		</c:if>

		$("#loginFrm").validate({
			submitHandler : function(form) {
				<c:if test="${needVerify }">
				var verifyCode = $("#verifyCode").val();
				if (!verifyCode || verifyCode.length() != 4) {
					layer.alert("请输入正确的验证码");
					$("#verifyCodeBtn").focus();
					return;
				}
				</c:if>
				$.shade.show();
				submitForm();
			}
		});
	});
</script>
</head>
<body class="gray-bg">
	<div class="middle-box text-center loginscreen  animated fadeInDown">
		<div>
			<div style="margin-top: 60px; margin-bottom: 40px;">
				<img width="200" height="200" src="${ctxPath }/icon_logo.png">
			</div>
			<h3>欢迎登陆任性猫美容美发综合运营平台</h3>
			<c:if test="${errorMessage != null}">
				<div class="alert alert-danger">
					<i class="fa fa-exclamation-triangle"></i>${errorMessage}</div>
			</c:if>
			<form id="loginFrm" method="post">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="用户名"
						required="" minlength="2" maxlength="30" id="accountName"
						name="accountName">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="密码"
						required="" minlength="6" maxlength="30" id="accountPwd"
						name="accountPwd">
				</div>
				<c:if test="${needVerify }">
					<div class="form-group">
						<input type="text" class="form-control form-control2"
							placeholder="验证码" id="verifyCode" name="verifyCode"> <input
							id="verifyCodeBtn" class="btn btn-outline btn-info" type="button"
							data-placement="bottom" data-toggle="tooltip" title="发送短信验证码"
							value="获取验证码">
					</div>
				</c:if>
				<input type="submit" class="btn btn-primary block full-width m-b"
					value="登 录" />
			</form>
		</div>
	</div>
	<OBJECT ID="jatoolsPrinter"
		CLASSID="CLSID:B43D3361-D075-4BE2-87FE-057188254255"
		codebase="jatoolsPrinter.cab#version=8,6,0,0"></OBJECT>
	<script type="text/javascript">
		prt();
	</script>
</body>
</html>
