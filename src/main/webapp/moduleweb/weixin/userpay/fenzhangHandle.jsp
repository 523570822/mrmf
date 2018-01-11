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

.input-group .form-control2 {
	width: 60%;
	display: inline;
}

.center {
	text-align: center;
}

.leftPadding {
	padding-left: 40px;
}

.input-group {
	display: inline;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							<a href="${ctxPath}/weixin/userpayFenzhang/toQuery.do"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<div class="row">
							<div class="col-sm-12">
								<form id="userpart" method="post" class="form-horizontal"
									action="${ctxPath}/weixin/userpayFenzhang/handleFenzhang.do">
									<input type="hidden" id="organId" name="organId"
										value="${param.organId}"> <input type="hidden"
										id="userId" name="userId"><input type="hidden"
										id="money1" name="money1"> <input type="hidden"
										id="money2" name="money2"><input type="hidden"
										id="fenzhangId" name="fenzhangId"><input type="hidden"
										id="cardId" name="cardId">
									<div class="form-group">
										<label class="col-sm-2 control-label">选择项目:</label>
										<div class="col-sm-2">
											<input type="text" class="form-control suggest"
												id="smallsort" name="smallsort"
												suggest="{data :fillmaps.smallsorts,style3:true}">
										</div>
										<label class="col-sm-2 control-label">消费金额:</label>
										<div class="col-sm-2">
											<input id="money_xiaofei" name="money_xiaofei" type="number"
												min="0" class="form-control" disabled>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-1 control-label">员工1:</label>
										<div class="col-sm-4">
											<input type="text" class="form-control form-control2 suggest"
												id="staffId1" name="staffId1"
												suggest="{data :fillmaps.staffs,style2:true}"> <span
												class="feihuiyuan">&nbsp;&nbsp;<label
												class="control-label"><input id="dian1" name="dian1"
													type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
												class="control-label"><input id="quan1" name="quan1"
													type="checkbox">&nbsp;劝</label></span>
										</div>
										<label class="col-sm-1 control-label">提成1:</label>
										<div class="col-sm-2">
											<input id="somemoney1" name="somemoney1" type="number"
												min="0" class="form-control" disabled>
										</div>
										<label class="col-sm-1 control-label">业绩1:</label>
										<div class="col-sm-2">
											<input id="yeji1" name="yeji1" type="number" min="0"
												class="form-control" disabled>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-1 control-label">员工2:</label>
										<div class="col-sm-4">
											<input type="text" class="form-control form-control2 suggest"
												id="staffId2" name="staffId2"
												suggest="{data :fillmaps.staffs,style2:true}"><span
												class="feihuiyuan"> &nbsp;&nbsp;<label
												class="control-label"><input id="dian2" name="dian2"
													type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
												class="control-label"><input id="quan2" name="quan2"
													type="checkbox">&nbsp;劝</label></span>
										</div>
										<label class="col-sm-1 control-label">提成2:</label>
										<div class="col-sm-2">
											<input id="somemoney2" name="somemoney2" type="number"
												min="0" class="form-control" disabled>
										</div>
										<label class="col-sm-1 control-label">业绩2:</label>
										<div class="col-sm-2">
											<input id="yeji2" name="yeji2" type="number" min="0"
												class="form-control" disabled>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-1 control-label">员工3:</label>
										<div class="col-sm-4">
											<input type="text" class="form-control form-control2 suggest"
												id="staffId3" name="staffId3"
												suggest="{data :fillmaps.staffs,style2:true}"><span
												class="feihuiyuan"> &nbsp;&nbsp;<label
												class="control-label"><input id="dian3" name="dian3"
													type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
												class="control-label"><input id="quan3" name="quan3"
													type="checkbox">&nbsp;劝</label></span>
										</div>
										<label class="col-sm-1 control-label">提成3:</label>
										<div class="col-sm-2">
											<input id="somemoney3" name="somemoney3" type="number"
												min="0" class="form-control" disabled>
										</div>
										<label class="col-sm-1 control-label">业绩3:</label>
										<div class="col-sm-2">
											<input id="yeji3" name="yeji3" type="number" min="0"
												class="form-control" disabled>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<div class="col-sm-12 leftPadding">
											<button id="btnSave" class="btn btn-primary" type="submit">处理</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$()
				.ready(
						function() {
							$("#smallsort").change(function() { // 服务项目变化
								jisuan();
							});
							$("#staffId1").change(function() {
								jisuan();
							});
							$("#staffId2").change(function() {
								jisuan();
							});
							$("#staffId3").change(function() {
								jisuan();
							});
							function jisuan() {

								// 计算提成、业绩
								tichengYeji("staffId1", "somemoney1", "yeji1");
								tichengYeji("staffId2", "somemoney2", "yeji2");
								tichengYeji("staffId3", "somemoney3", "yeji3");
							}

							$("#yeji1")
									.change(
											function() {
												var m1 = $("#money_xiaofei")
														.val();
												var y1 = $("#yeji1").val();
												if (m1 && m1 > 0 && y1 != "") {
													tichengYeji("staffId1",
															"somemoney1",
															"yeji1", true);
												}
											});
							$("#yeji2")
									.change(
											function() {
												var m1 = $("#money_xiaofei")
														.val();
												var y1 = $("#yeji2").val();
												if (m1 && m1 > 0 && y1 != "") {
													tichengYeji("staffId2",
															"somemoney2",
															"yeji2", true);
												}
											});
							$("#yeji3")
									.change(
											function() {
												var m1 = $("#money_xiaofei")
														.val();
												var y1 = $("#yeji3").val();
												if (m1 && m1 > 0 && y1 != "") {
													tichengYeji("staffId3",
															"somemoney3",
															"yeji3", true);
												}
											});

							function tichengYeji(staffId, somemoney, yeji, flag) {
								var s1 = $("#" + staffId).val(), m1;
								$("#" + somemoney).val(0);
								if (flag) {
									m1 = $("#" + yeji).val();
								} else {
									$("#" + yeji).val(0);
								}
								var ss = fillmaps.smallsorts[$("#smallsort")
										.attr("selectedIndex")];
								if (s1 && ss) {
									m1 = $("#money_xiaofei").val();
									$("#" + yeji).val(m1); // 业绩
									// 计算提成
									var s = fillmaps.staffs[$("#" + staffId)
											.attr("selectedIndex")];
									for (var i = 0; i < fillmaps.staffTichengs.length; i++) {
										var st = fillmaps.staffTichengs[i];
										var yj = st.yeji;
										var mm1 = m1;
										/*if (!flag && yj && yj > 0) {
											mm1 = yj;
											$("#" + yeji).val(mm1); // 业绩
										}*/
										if (s && s.dutyId == st.staffpost
												&& st.smallsort == ss._id) {
											<c:if test="${organSetting.tichengDeductCost}">
											mm1 -= ss.price_chengben;
											if (mm1 < 0)
												mm1 = 0;
											</c:if>
											var tc;
											/*if (st.removeAmount) { // 去除固定
												tc = (mm1 - st.tichengCard)
											 * st.tichengCardPercent
														/ 100;
											} else {
												tc = mm1
											 * st.tichengCardPercent
														/ 100;
											}
											tc += st.tichengCard;*/
											tc = mm1 * st.tichengCardPercent
													/ 100;

											$("#" + somemoney).val(
													tc.toFixed(2));
											break;
										}
									}
								}
							}

							$("#userpart").validate({
								submitHandler : function(form) { // 保存
									var s = $("#userpart #smallsort").val();
									if (!s) {
										toastr.warning("请选择服务项目");
										$("#userpart #sg_smallsort").focus();
										return;
									}
									s = $("#userpart #staffId1").val();
									if (!s) {
										toastr.warning("请选择做活员工");
										$("#userpart #sg_staffId1").focus();
										return;
									}

									$.shade.show();
									form.submit();

								}
							});

							var mx = parseFloat($("#money_xiaofei").val());
							for (var i = 0; i < fillmaps.smallsorts.length; i++) {
								if(mx === fillmaps.smallsorts[i].price) {
									fillmaps.userpart.smallsort = fillmaps.smallsorts[i]._id;
									break;
								}
							}

							$("#userpart").fillform(fillmaps.userpart);
						});
	</script>
</body>
</html>
