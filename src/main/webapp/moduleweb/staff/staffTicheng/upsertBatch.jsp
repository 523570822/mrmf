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
							<a href="${ctxPath}/staff/staffTicheng/toQuery.do"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a> 批量员工提成设置/分配业绩
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="staffTicheng" method="post"
							action="${ctxPath}/staff/staffTicheng/upsertBatch.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"><input
								type="hidden" id="organId" name="organId"
								value="${param.organId}">
							<div class="form-group">
								<label class="col-sm-2 control-label">服务项目</label>
								<div class="col-sm-10">
									<select class="form-control" id="smallsortIds"
										name="smallsortIds" multiple size="20" required>
										<c:forEach items="${ffsmallsorts}" var="smallsort">
											<option value="${smallsort._id}">${smallsort.name}</option>
										</c:forEach>
									</select> <span class="help-block m-b-none">按住Ctrl可选择或不选择项目(可多选)，Ctrl+a为全选。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">岗位</label>
								<div class="col-sm-4">
									<select class="form-control" id="staffpost" name="staffpost">
										<c:forEach items="${staffposts}" var="staffpost">
											<option value="${staffpost._id}">${staffpost.name}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-2 control-label">现金提成（%）</label>
								<div class="col-sm-4">
									<input id="tichengCashPercent" min="0"
										name="tichengCashPercent" type="number" class="form-control"
										required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">现金固定提成（元）</label>
								<div class="col-sm-4">
									<input id="tichengCash" name="tichengCash" type="number"
										class="form-control" required>
								</div>
								<label class="col-sm-2 control-label">消卡提成（%）</label>
								<div class="col-sm-4">
									<input id="tichengCardPercent" min="0"
										name="tichengCardPercent" type="number" class="form-control"
										required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">卡固定提成（元）</label>
								<div class="col-sm-4">
									<input id="tichengCard" name="tichengCard" type="number"
										min="0" class="form-control" required>
								</div>
								<label class="col-sm-2 control-label">去除固定金额算提成</label>
								<div class="col-sm-4">
									<input id="removeAmount" name="removeAmount" type="checkbox"
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
					$("#staffTicheng").validate({
						submitHandler : function(form) {
							$.shade.show();
							$("#staffTicheng").enable();

							form.submit();
						}
					});

					/*
					$("#smallsort")
							.change(
									function() {
										var ms = fillmaps.smallsorts[$("#smallsort")[0].selectedIndex - 1];
										if (ms) {
											$("#yeji").val(ms.price);
										} else {
											$("#yeji").val(0);
										}
									});
					 */
					/*
					$("#yeji")
						.change(
								function() {
									var ms = fillmaps.smallsorts[$("#smallsort")[0].selectedIndex - 1];
									if (ms) {
										if (ms.price < $("#yeji")
												.val()) {
											$("#yeji")
													.val(ms.price);
											layer
													.alert("业绩不能超出服务项目价格!");
										}
									} else {
										$("#yeji").val(0);
									}
								});
					 */
					$("#cancelBtn").click(
							function() {
								document.location.href = _ctxPath
										+ "/staff/staffTicheng/toQuery.do";
							});

				});
	</script>
</body>
</html>
