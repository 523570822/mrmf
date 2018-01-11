<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style>
.form-control2 {
	width: 60%;
	display: inline;
}

.center {
	text-align: center;
}

.leftPadding {
	padding-left: 40px;
}
</style>
</head>
<body class="gray-bg">
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row">
				<div class="col-sm-12">
					<form id="userpart" method="post" class="form-horizontal">
						<input type="hidden" id="organId" name="organId"> <input
							type="hidden" id="userId" name="userId"><input
							type="hidden" id="bigsort" name="bigsort"> <input
							type="hidden" id="cardId" name="cardId"><input
							type="hidden" id="incardId" name="incardId"><input
							type="hidden" id="cardno" name="cardno"><input
							type="hidden" id="id_2" name="id_2">
						<input type="hidden"  name="userPartId" value="${ffuserpart._id}">
						<div class="form-group">
							<label class="col-sm-2 control-label">会员类型:</label>
							<div class="col-sm-2">
								<select class="form-control" id="membersort" name="membersort"
									disabled>
									<c:forEach items="${ffusersorts}" var="usersort">
										<option value="${usersort._id}">${usersort.name1}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 col-sm-offset-3 control-label">应交款额:</label>
							<div class="col-sm-2">
								<input id="money1" name="money1" type="number" min="0"
									class="form-control" value="0" disabled>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">实交款额:</label>
							<div class="col-sm-2">
								<input id="money2" name="money2" type="number" min="0"
									class="form-control" value="0" disabled>
							</div>
							<label class="col-sm-1 control-label">余 额:</label>
							<div class="col-sm-2">
								<input id="money4" name="money4" type="number" min="0"
									class="form-control" value="0" disabled>
							</div>
							<label class="col-sm-2 control-label">欠 款:</label>
							<div class="col-sm-2">
								<input id="money_qian" name="money_qian" type="number" min="0"
									class="form-control" value="0" disabled>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">员工1:</label>
							<div class="col-sm-2">
								<select class="form-control" id="staffId1" name="staffId1"
									disabled>
									<option value="">请选择</option>
									<c:forEach items="${ffstaffs}" var="staff">
										<option value="${staff._id}">${staff.name}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-1 control-label">业绩1:</label>
							<div class="col-sm-2">
								<input id="yeji1" name="yeji1" type="number" min="0"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">员工2:</label>
							<div class="col-sm-2">
								<select class="form-control" id="staffId2" name="staffId2"
									disabled>
									<option value="">请选择</option>
									<c:forEach items="${ffstaffs}" var="staff">
										<option value="${staff._id}">${staff.name}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-1 control-label">业绩2:</label>
							<div class="col-sm-2">
								<input id="yeji2" name="yeji2" type="number" min="0"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">员工3:</label>
							<div class="col-sm-2">
								<select class="form-control" id="staffId3" name="staffId3"
									disabled>
									<option value="">请选择</option>
									<c:forEach items="${ffstaffs}" var="staff">
										<option value="${staff._id}">${staff.name}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-1 control-label">业绩3:</label>
							<div class="col-sm-2">
								<input id="yeji3" name="yeji3" type="number" min="0"
									class="form-control">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-1">
								<label class="control-label"><input id="act1" name="act"
									type="radio" value="1" checked>&nbsp;补交欠款:</label>
							</div>
							<label class="col-sm-2 control-label">补交现金:</label>
							<div class="col-sm-2">
								<input id="money_cash" name="money_cash" type="number"
									class="form-control" required min="0">
							</div>
							<label class="col-sm-2 control-label">银行卡:</label>
							<div class="col-sm-2">
								<input id="money_yinhang_money" name="money_yinhang_money"
									type="number" class="form-control" required min="0">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-4 col-sm-offset-1">
								<label class="control-label"> <input id="act2"
									name="act" type="radio" value="2">&nbsp;更改卡类型:
								</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" id="membersort2" name="membersort2">
									<option value="">请选择</option>
									<c:forEach items="${ffusersorts}" var="usersort">
										<c:if
											test="${ffuserpart.membersort != usersort._id && usersort.flag1 != '1000'}">
											<option value="${usersort._id}">${usersort.name1}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-12 leftPadding">
								<button id="btnSave" class="btn btn-primary" type="submit">确定</button>
								<!-- <button id="btnPrint" class="btn btn-outline btn-info"
									type="button">打印小票</button> -->
								<button id="btnBack" class="btn btn-outline btn-danger"
									type="button">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$()
				.ready(
						function() {
							$("#userpart")
									.validate(
											{
												submitHandler : function(form) { // 保存
													var t = $(
															"input[name='act']:checked")
															.val();
													if (t == 1) { // 补交欠款
														var money_cash = parseFloat($(
																"#money_cash")
																.val());
														var money_yinhang_money = parseFloat($(
																"#money_yinhang_money")
																.val());
														var t = money_cash
																+ money_yinhang_money;
														if (!(t > 0)) {
															toastr
																	.warning("请输入续费金额");
															$("#money_cash")
																	.focus();
															return;
														}

														var money_qian = parseFloat($(
																"#money_qian")
																.val());
														if (t > money_qian) {
															toastr
																	.warning("交费金额不能大于欠款金额");
															$("#money_cash")
																	.focus();
															return;
														}

														var partObj = $(
																"#userpart")
																.formobj();
														parent.$.shade.show();
														$
																.post(
																		"${ctxPath}/user/userIncard/qianfei.do",
																		partObj,
																		function(
																				data,
																				status) {
																			parent.$.shade
																					.hide();
																			if (data.success) {
																				parent.toastr
																						.success("补交欠费成功!");
																				parent
																						.queryFrm();
																				parent.layer
																						.closeAll();
																			} else {
																				layer
																						.alert(data.message);
																			}
																		});
													} else if (t == 2) { // 更改卡类型
														var membersort2 = $(
																"#membersort2")
																.val();
														if (!membersort2) {
															toastr
																	.warning("请选择要更改的卡类型!");
															$("#membersort2")
																	.focus();
															return;
														}

														parent.$.shade.show();
														$
																.post(
																		"${ctxPath}/user/userIncard/changeCardType.do",
																		{
																			incardId : "${ffuserpart.incardId}",
																			usersortId : membersort2
																		},
																		function(
																				data,
																				status) {
																			parent.$.shade
																					.hide();
																			if (data.success) {
																				parent.toastr
																						.success("更改卡类型成功!");
																				parent
																						.queryFrm();
																				parent.layer
																						.closeAll();
																			} else {
																				layer
																						.alert(data.message);
																			}
																		});
													}
												}
											});

							$("#btnPrint").click(function() { // 打印小票

							});
							$("#btnBack").click(function() { // 返回
								parent.layer.closeAll();
							});

						});
	</script>
</body>
</html>
