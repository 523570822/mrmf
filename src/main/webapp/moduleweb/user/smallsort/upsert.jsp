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
							<a href="${ctxPath}/user/smallsort/toQuery.do" class="btn-link">
								<i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffsmallsort._id == null}">
								新建服务价目表
							</c:if>
							<c:if test="${ffsmallsort._id != null}">修改服务价目表
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="smallsort" method="post"
							action="${ctxPath}/user/smallsort/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"><input
								type="hidden" id="organId" name="organId"
								value="${param.organId}"><input type="hidden" id="valid"
								name="valid" value="true">
							<div class="form-group">
								<label class="col-sm-2 control-label">服务大类</label>
								<div class="col-sm-10">
									<select class="form-control" id="bigcode" name="bigcode"
										required>
										<c:forEach items="${bigsorts}" var="bigsort">
											<option value="${bigsort._id}">${bigsort.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">服务项名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">助记符</label>
								<div class="col-sm-10">
									<input id="zjfCode" name="zjfCode" type="text"
										class="form-control" maxlength="30" readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">成本（元）</label>
								<div class="col-sm-10">
									<input id="price_chengben" min="0" name="price_chengben"
										type="number" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">价钱（元）</label>
								<div class="col-sm-10">
									<input id="price" name="price" type="number" min="0"
										class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">所需时间（分钟）</label>
								<div class="col-sm-10">
									<input id="small_time" name="small_time" type="digits"
										class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">服务项目介绍</label>
								<div class="col-sm-10">
									<textarea id="jieshao" name="jieshao" class="form-control"
										maxlength="200"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">小活标记</label>
								<div class="col-sm-10">
									<input id="charge_flag" name="charge_flag" type="checkbox"
										class="switcher" />
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
		$().ready(
				function() {
					var functionIds = [], functionNames = [];
					if (fillmaps && fillmaps.smallsort) {
						var smallsort = fillmaps.smallsort;

					}

					$("#smallsort").validate({
						submitHandler : function(form) {
							$.shade.show();
							$("#smallsort").enable();

							form.submit();
						}
					});

					$("#cancelBtn").click(
							function() {
								document.location.href = _ctxPath
										+ "/user/smallsort/toQuery.do";
							});

				});
	</script>
</body>
</html>
