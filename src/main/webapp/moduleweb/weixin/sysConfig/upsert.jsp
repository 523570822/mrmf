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
						<h5>平台业务参数设置</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="config" method="post"
							action="${ctxPath}/weixin/sysConfig/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id">
							<div class="form-group">
								<label class="col-sm-4 control-label">平台预存现金统一店铺折扣(%)</label>
								<div class="col-sm-6">
									<input id="allzhekou" name="allzhekou" type="number"
										class="form-control" min="0" max="100" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">满1 </label>
								<div class="col-sm-6">
									<input id="man1" name="man1" type="digits" class="form-control"
										min="0" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">返1 </label>
								<div class="col-sm-6">
									<input id="fan1" name="fan1" type="digits" class="form-control"
										min="0" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">满2 </label>
								<div class="col-sm-6">
									<input id="man2" name="man2" type="digits" class="form-control"
										min="0" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">返2 </label>
								<div class="col-sm-6">
									<input id="fan2" name="fan2" type="digits" class="form-control"
										min="0" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">满3 </label>
								<div class="col-sm-6">
									<input id="man3" name="man3" type="digits" class="form-control"
										min="0" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">返3 </label>
								<div class="col-sm-6">
									<input id="fan3" name="fan3" type="digits" class="form-control"
										min="0" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">扫码返现 </label>
								<div class="col-sm-6">
									<input id="saoMaFan" name="saoMaFan"
										   min="0" onkeyup="value=value.replace(/\.\d{2,}$/,value.substr(value.indexOf('.'),3))" class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">手机号</label>
								<div class="col-sm-6">
									<input id="phone" name="phone" type="text" mobile="true"
										class="form-control" required><span
										class="help-block m-b-none">平台相关业务通知短信发送到此手机号，如：店铺平台卡余额不足提醒。</span>
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
		$().ready(function() {
            jQuery.validator.addMethod("lrunlv", function(value, element) {
                return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);
            }, "小数位不能超过三位");

            //验证小数点后的位数
            jQuery.validator.addMethod("decimals", function(value, element,d){
                var a = value.indexOf(".")+1;
                if(a==0){
                    a=value.length;
                }
                var b = value.length;
                var c = b-a;
                return this.optional(element) || c<=d;
            });

			$("#config").validate({
				submitHandler : function(form) {
					var m1 = parseInt($("#man1").val());
					var m2 = parseInt($("#man2").val());
					var m3 = parseInt($("#man3").val());

					if (m2 != 0 && m2 < m1) {
						toastr.warning("满2必须大于满1");
						$("#man2").focus();
						return;
					} else if (m3 != 0 && m3 < m2) {
						toastr.warning("满3必须大于满2");
						$("#man3").focus();
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
