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
							<a href="${ctxPath}/weixin/charge/toQuery.do" class="btn-link">
								<i class="fa fa-angle-double-left"></i>返回
							</a>店铺平台卡充值
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="usercard" method="post"
							action="${ctxPath}/weixin/charge/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"> <input
								type="hidden" id="organId" name="organId">
							<div class="form-group">
								<label class="col-sm-2 control-label">公司名称</label>
								<div class="col-sm-10">
									<input id="organName" name="organName" type="text"
										class="form-control" disabled>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">累计金额</label>
								<div class="col-sm-10">
									<input id="money_leiji" name="money_leiji" type="text"
										class="form-control" disabled>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">卡余额</label>
								<div class="col-sm-10">
									<input id="money4" name="money4" type="text"
										class="form-control" disabled>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">充值金额(元)</label>
								<div class="col-sm-10">
									<input id="charge" name="charge" type="number"
										class="form-control" required> <span
										class="help-block m-b-none">店铺平台卡充值金额，负数为冲减，但冲减后余额不能为负数。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">提交</button>
									<c:if test="${param.back == null}">
										<button id="cancelBtn" class="btn btn-white" type="button">取消</button>
									</c:if>
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
		$().ready(
				function() {
					$("#usercard").validate({
						submitHandler : function(form) {
							$.shade.show();
							form.submit();
						}
					});

					$("#cancelBtn").click(
							function() {
								document.location.href = _ctxPath
										+ "/weixin/charge/toQuery.do";
							});

				});
	</script>

</body>
</html>
