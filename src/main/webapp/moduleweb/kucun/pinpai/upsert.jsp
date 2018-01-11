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
							<a href="${ctxPath}/kucun/pinpai/toQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffpinpai._id == null}">
								新建品牌
							</c:if>
							<c:if test="${ffpinpai._id != null}">修改品牌
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="pinpai" method="post" action="${ctxPath}/kucun/pinpai/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id">
							<input type="hidden" id="organId" name="organId">
							<div class="form-group">
								<label class="col-sm-2 control-label">名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
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
		$().ready(function() {
			$("#pinpai").validate({
				submitHandler : function(form) {
					$.shade.show();
					$("#pinpai").enable();
					form.submit();
				}
			});

			$("#cancelBtn").click(function() {
				document.location.href = _ctxPath + "/kucun/pinpai/toQuery.do";
			});

		});
	</script>
</body>
</html>
