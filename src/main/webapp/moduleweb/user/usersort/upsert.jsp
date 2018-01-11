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
							<a href="${ctxPath}/user/usersort/toQuery.do" class="btn-link">
								<i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffusersort._id == null}">
								新建会员类别
							</c:if>
							<c:if test="${ffusersort._id != null}">修改会员类别
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="usersort" method="post"
							action="${ctxPath}/user/usersort/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"><input
								type="hidden" id="organId" name="organId"
								value="${param.organId}"> <input type="hidden" id="bilv"
								name="bilv"> <input type="hidden" id="ticheng"
								name="ticheng"><input type="hidden" id="ticheng2"
								name="ticheng2"><input type="hidden" id="ticheng3"
								name="ticheng3"> <input type="hidden" id="xufei_ti1"
								name="xufei_ti1"><input type="hidden" id="xufei_ti2"
								name="xufei_ti2"><input type="hidden" id="xufei_ti3"
								name="xufei_ti3">

							<div class="form-group">
								<label class="col-sm-2 control-label">会员类型</label>
								<div class="col-sm-10">
									<input id="name1" name="name1" type="text" class="form-control"
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
								<label class="col-sm-2 control-label">优惠类别</label>
								<div class="col-sm-10">
									<select class="form-control" id="flag1" name="flag1">
										<c:forEach items="${usersortTypes}" var="usersortType">
											<option value="${usersortType._id}">${usersortType.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否默认</label>
								<div class="col-sm-10">
									<input id="flag_putong" name="flag_putong" type="checkbox"
										class="switcher" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">有效天数</label>
								<div class="col-sm-10">
									<input id="law_date" name="law_date" type="digits"
										class="form-control" required><span
										class="help-block m-b-none">0表示不设有效期，永久有效。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">金额（元）</label>
								<div class="col-sm-10">
									<input id="money" min="0" name="money" type="number"
										class="form-control" required><span
										class="help-block m-b-none">会员卡的办卡金额，单纯打折卡将不计入会员卡余额。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">次数</label>
								<div class="col-sm-10">
									<input id="cishu" name="cishu" type="digits"
										class="form-control" required><span
										class="help-block m-b-none">次数卡消费次数定义。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">次数卡明细名称</label>
								<div class="col-sm-10">
									<select class="form-control" id="name2" name="name2">
										<option value="">请选择</option>
										<c:forEach items="${smallsorts}" var="smallsort">
											<option value="${smallsort._id}">${smallsort.name}</option>
										</c:forEach>
									</select><span class="help-block m-b-none">次数卡可消费项目。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">折扣（%）</label>
								<div class="col-sm-10">
									<input id="zhekou" min="0" name="zhekou" type="number"
										class="form-control" required><span
										class="help-block m-b-none">存钱打折卡或单纯打折卡的折扣。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">办/续卡提成比例</label>
								<div class="col-sm-10">
									<input id="guding_ticheng" min="0" name="guding_ticheng"
										type="number" class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">办卡提成比例1</label>
								<div class="col-sm-4">
									<input id="tc1" min="0" name="tc1"
										   type="number" class="form-control" required>
								</div>
								<label class="col-sm-2 control-label">续费提成比例1</label>
								<div class="col-sm-4">
									<input id="xtc1" min="0" name="xtc1"
										   type="number" class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">办卡提成比例2</label>
								<div class="col-sm-4">
									<input id="tc2" min="0" name="tc2"
										   type="number" class="form-control" required>
								</div>
								<label class="col-sm-2 control-label">续费提成比例2</label>
								<div class="col-sm-4">
									<input id="xtc2" min="0" name="xtc2"
										   type="number" class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">办卡提成比例3</label>
								<div class="col-sm-4">
									<input id="tc3" min="0" name="tc3"
										   type="number" class="form-control" required>
								</div>
								<label class="col-sm-2 control-label">续费提成比例3</label>
								<div class="col-sm-4">
									<input id="xtc3" min="0" name="xtc3"
										   type="number" class="form-control" required>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">办卡多少钱积一分</label>
								<div class="col-sm-10">
									<input id="coin_bilv" min="0" name="coin_bilv" type="number"
										class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">消费多少钱积一分</label>
								<div class="col-sm-10">
									<input id="coin_money" min="0" name="coin_money" type="number"
										class="form-control" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">赠送积分</label>
								<div class="col-sm-10">
									<input id="coin_give" name="coin_give" type="digits"
										class="form-control" required>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">外卖折扣（%）</label>
								<div class="col-sm-10">
									<input id="waimaizhekou" min="0" name="waimaizhekou"
										type="number" class="form-control" required>
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
							var functionIds = [], functionNames = [];
							if (fillmaps && fillmaps.usersort
									&& fillmaps.usersort._id) {
								var usersort = fillmaps.usersort;
								$("#flag1").disable();
							}

							$("#flag1").change(function() {
								if ($("#flag1").val() === "1001") { // 单纯打折卡
									//$("#money").val(0);
									//$("#money").disable();
									$("#cishu").val(0);
									$("#name2").val("");
									$("#cishu").disable();
									$("#name2").disable();
								} else {
									//$("#money").enable();
									$("#cishu").enable();
									$("#name2").enable();
								}
							});

							$("#usersort")
									.validate(
											{
												submitHandler : function(form) {
													if ($("#flag1").val() === "1003"
															&& $("#name2")
																	.val() === "") {// 次数卡
														layer
																.alert("请选择次数卡明细名称");
														$("#name2").focus();
														return;
													}

													$.shade.show();
													$("#usersort").enable();

													form.submit();
												}
											});

							$("#cancelBtn").click(
									function() {
										document.location.href = _ctxPath
												+ "/user/usersort/toQuery.do";
									});

						});
	</script>
</body>
</html>
