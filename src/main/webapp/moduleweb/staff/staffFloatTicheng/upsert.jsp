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
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffstaffFloatTicheng._id == null}">
								新建浮动提成
							</c:if>
							<c:if test="${ffstaffFloatTicheng._id != null}">修改浮动提成
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="staffFloatTicheng" method="post"
							action="${ctxPath}/staff/staffFloatTicheng/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"> <input
								type="hidden" id="organId" name="organId"
								value="${param.organId}">
							<div class="form-group">
								<label class="col-sm-2 control-label">浮动类别</label>
								<div class="col-sm-10">
									<select class="form-control" id="floatType" name="floatType"
										required>
										<option value="">请选择</option>
										<option value="0">业绩分段固定提成</option>
										<option value="1">业绩最高额取最高提成</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">业绩1</label>
								<div class="col-sm-10">
									<input id="yeji1" name="yeji1" type="number" min="0"
										class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">业绩2</label>
								<div class="col-sm-10">
									<input id="yeji2" name="yeji2" type="number" min="0"
										class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">提成（%）</label>
								<div class="col-sm-10">
									<input id="ticheng" name="ticheng" type="number"
										class="form-control" min="0" required>
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
		var layerId;
		$()
				.ready(
						function() {
							$("#staffFloatTicheng").validate({
								submitHandler : function(form) {
									$.shade.show();
									$("#staffFloatTicheng").enable();

									form.submit();
								}
							});

							$("#cancelBtn")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/staff/staffFloatTicheng/toQuery.do";
											});

						});
	</script>
</body>
</html>
