<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>修改登录口令</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="account" method="post"
							action="${ctxPath}/account/changePasswd.do"
							class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">原密码</label>
								<div class="col-sm-10">
									<input id="oldPasswd" name="oldPasswd" type="password"
										class="form-control" minlength="6" maxlength="30" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">新密码</label>
								<div class="col-sm-10">
									<input id="newPasswd" name="newPasswd" type="password"
										class="form-control" minlength="6" maxlength="30" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">重复新密码</label>
								<div class="col-sm-10">
									<input id="newPasswd2" name="newPasswd2" type="password"
										class="form-control" minlength="6" maxlength="30" required>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var layerId;
		$().ready(function() {
			$("#account").validate({
				submitHandler : function(form) {
					if ($("#newPasswd").val() != $("#newPasswd2").val()) {
						layer.alert("新密码不一致!");
						return;
					}
					$.shade.show();

					form.submit();
				}
			});

		});
	</script>
</body>
</html>
