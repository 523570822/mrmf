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
						<h5>
							<a href="${ctxPath}/staff/staffFloatTicheng/toQuery.do"
								class="btn-link"><i class="fa fa-angle-double-left"></i>返回</a>员工浮动提成计算，业绩设置
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="setting" method="post"
							action="${ctxPath}/organ/upsertSettingTichengLiushui.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id">

							<div class="form-group">
								<label class="col-sm-4 control-label">提成计算包含流水：</label>
								<div class="col-sm-6">
									<select class="form-control" id="tichengLiushui"
										name="tichengLiushui" multiple size="15">
										<option value="sanke">散客流水</option>
										<option value="waimai">外卖流水</option>
										<option value="huiyuanka">会员卡流水</option>
										<option value="banka">办卡流水</option>
										<option value="xufei">续费流水</option>
										<option value="huiyuankawaimai">会员卡外卖流水</option>
										<option value="miandan">免单流水</option>
									</select><span class="help-block m-b-none">设置员工浮动提成计算时，包括哪些流水。按住Ctrl可选择或不选择流水(可多选)，Ctrl+a为全选。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">提交</button>
									<button id="cancelBtn" class="btn btn-white" type="button">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$()
				.ready(
						function() {
							$("#cancelBtn")
									.click(
											function() {
												document.location.href = "${ctxPath}/staff/staffFloatTicheng/toQuery.do";
											});
							$("#setting").validate({
								submitHandler : function(form) {
									$.shade.show();
									form.submit();
								}
							});
						});
	</script>
</body>
</html>
