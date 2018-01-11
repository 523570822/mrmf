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
					<form id="incard" method="post" class="form-horizontal">
						<input type="hidden" id="organId" name="organId"> <input
							type="hidden" id="userId" name="userId"><input
							type="hidden" id="flag2" name="flag2" value="false"> <input
							type="hidden" id="delete_flag" name="delete_flag" value="false">
						<input type="hidden" id="guazhang_flag" name="guazhang_flag"
							value="true"> <input type="hidden" id="bigsort"
							name="bigsort"> <input type="hidden" id="cardId"
							name="cardId"><input type="hidden" id="cardno"
							name="cardno">
						<div class="form-group">
							<label class="col-sm-1 control-label">会员编码:</label>
							<div class="col-sm-2">
								<input id="id_2" name="id_2" type="text" class="form-control"
									minlength="2" maxlength="50" disabled>
							</div>
							<label class="col-sm-2 control-label">会员姓名:</label>
							<div class="col-sm-2">
								<input id="name" name="name" type="text" class="form-control"
									minlength="2" maxlength="50" disabled>
							</div>
							<label class="col-sm-2 control-label">会员类型:</label>
							<div class="col-sm-2">
								<select class="form-control" id="membersort" name="membersort"
									disabled>
									<c:forEach items="${ffusersorts}" var="usersort">
										<option value="${usersort._id}">${usersort.name1}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label cishur">总次数:</label>
							<div class="col-sm-2 cishur">
								<input id="allcishu" name="allcishu" type="digits" min="0"
									class="form-control" value="0" disabled>
							</div>
							<label class="col-sm-1 control-label zhekour">余 额:</label>
							<div class="col-sm-2 zhekour">
								<input id="money4" name="money4" type="number" min="0"
									class="form-control" disabled>
							</div>
							<label class="col-sm-2 control-label">续费现金:</label>
							<div class="col-sm-2">
								<input id="money_cash" name="money_cash" type="number" min="0"
									class="form-control" value="0">
							</div>
							<label class="col-sm-2 control-label">总 额:</label>
							<div class="col-sm-2">
								<input id="money_leiji" name="money_leiji" type="digits" min="0"
									class="form-control" value="0" disabled>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label cishur">剩余次数:</label>
							<div class="col-sm-2 cishur">
								<input id="shengcishu" name="shengcishu" type="digits" min="0"
									class="form-control" value="0" disabled>
							</div>
							<label class="col-sm-1 control-label zhekour">欠 款:</label>
							<div class="col-sm-2 zhekour">
								<input id="money_qian" name="money_qian" type="number"
									class="form-control" min="0" value="0">
							</div>
							<label class="col-sm-2 control-label">续银行卡:</label>
							<div class="col-sm-2">
								<input id="money_yinhang_money" name="money_yinhang_money"
									type="number" min="0" class="form-control" value="0">
							</div>
							<label class="col-sm-2 control-label">有效天数:</label>
							<div class="col-sm-2">
								<input id="law_date" name="law_date" type="digits"
									class="form-control" min="0" value="0">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"><font color="red">注:续费现金和银行卡总和为总的续费金额</font></label>

							<label class="col-sm-2 control-label cishur">次数:</label>
							<div class="col-sm-2 cishur">
								<input id="cishu" name="cishu" type="number"
									class="form-control" min="0" value="0">
							</div>
							<div class="col-sm-4 center zhekour">
								<label class="control-label" style="width:45%; float: left"><input id="miandan"
									name="miandan" type="checkbox">&nbsp;赠送</label>
								<div style="width: 48%; float: right">
									<input id="songMoney" name="songMoney" type="number"
										   class="form-control" min="0" value="0">
								</div>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<span id="tichengRegion">
							<div class="form-group">
								<label class="col-sm-1 control-label">员工1:</label>
								<div class="col-sm-4">
									<select class="form-control form-control2" id="staffId1"
										name="staffId1">
										<option value="">请选择</option>
										<c:forEach items="${ffstaffs}" var="staff">
											<option value="${staff._id}">${staff.name}</option>
										</c:forEach>
									</select> <span class="feihuiyuan">&nbsp;&nbsp;<label
										class="control-label"><input id="dian1" name="dian1"
											type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
										class="control-label"><input id="quan1" name="quan1"
											type="checkbox">&nbsp;劝</label></span>
								</div>
								<c:if test="${organSetting.displayTicheng}">
									<label class="col-sm-1 control-label">提成1:</label>
									<div class="col-sm-2">
										<input id="somemoney1" name="somemoney1" type="number" min="0"
											class="form-control">
									</div>
								</c:if>
								<c:if test="${!organSetting.displayTicheng}">
									<input id="somemoney1" name="somemoney1" type="hidden">
								</c:if>
								<label class="col-sm-1 control-label">业绩1:</label>
								<div class="col-sm-2">
									<input id="yeji1" name="yeji1" type="number" min="0"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">员工2:</label>
								<div class="col-sm-4">
									<select class="form-control form-control2" id="staffId2"
										name="staffId2">
										<option value="">请选择</option>
										<c:forEach items="${ffstaffs}" var="staff">
											<option value="${staff._id}">${staff.name}</option>
										</c:forEach>
									</select><span class="feihuiyuan"> &nbsp;&nbsp;<label
										class="control-label"><input id="dian2" name="dian2"
											type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
										class="control-label"><input id="quan2" name="quan2"
											type="checkbox">&nbsp;劝</label></span>
								</div>
								<c:if test="${organSetting.displayTicheng}">
									<label class="col-sm-1 control-label">提成2:</label>
									<div class="col-sm-2">
										<input id="somemoney2" name="somemoney2" type="number" min="0"
											class="form-control">
									</div>
								</c:if>
								<c:if test="${!organSetting.displayTicheng}">
									<input id="somemoney2" name="somemoney2" type="hidden">
								</c:if>
								<label class="col-sm-1 control-label">业绩2:</label>
								<div class="col-sm-2">
									<input id="yeji2" name="yeji2" type="number" min="0"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">员工3:</label>
								<div class="col-sm-4">
									<select class="form-control form-control2" id="staffId3"
										name="staffId3">
										<option value="">请选择</option>
										<c:forEach items="${ffstaffs}" var="staff">
											<option value="${staff._id}">${staff.name}</option>
										</c:forEach>
									</select><span class="feihuiyuan"> &nbsp;&nbsp;<label
										class="control-label"><input id="dian3" name="dian3"
											type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
										class="control-label"><input id="quan3" name="quan3"
											type="checkbox">&nbsp;劝</label></span>
								</div>
								<c:if test="${organSetting.displayTicheng}">
									<label class="col-sm-1 control-label">提成3:</label>
									<div class="col-sm-2">
										<input id="somemoney3" name="somemoney3" type="number" min="0"
											class="form-control">
									</div>
								</c:if>
								<c:if test="${!organSetting.displayTicheng}">
									<input id="somemoney3" name="somemoney3" type="hidden">
								</c:if>
								<label class="col-sm-1 control-label">业绩3:</label>
								<div class="col-sm-2">
									<input id="yeji3" name="yeji3" type="number" min="0"
										class="form-control">
								</div>
							</div>
						</span>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-12 leftPadding">
								<!-- <button id="btnSubCard" class="btn btn-info" type="button">创建子卡</button> -->
								<button id="btnNew" class="btn btn-success" type="button">重置</button>
								<button id="btnSave" class="btn btn-primary" type="submit">保存</button>
								<button id="btnPrint" class="btn btn-outline btn-info"
									type="button">打印小票</button>
								<button id="btnBack" class="btn btn-outline btn-danger"
									type="button">返回</button>
							</div>
						</div>
					</form>
					<div class="jqGrid_wrapper">
						<table id="myTable"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType, selectedUserpartRowId;
		$()
				.ready(
						function() {
							var uss = fillmaps.usersorts, ms = fillmaps.incard.membersort, fd = false, ous;
							for (var i = 0; i < uss.length; i++) {
								if (ms === uss[i]._id) {
									fd = true;
									break;
								}
							}
							if (!fd) { // 不存在会员类别，为跨店消费
								$
										.post(
												"${ctxPath}/user/usersort/queryById.do",
												{
													usersort : ms
												},
												function(data, status) {
													ous = data;
													var mo = $("<option value=\""+ms+"\">"
															+ data.name1
															+ "</option>");
													$("#membersort").append(mo);
													mo[0].selected = true;
												});
							}

							function clearUserpart() {
								$("#cishu").val(0);
								$("#money_cash").val(0); // 续费现金
								$("#money_yinhang_money").val(0); // 银行卡
								$("#money_qian").val(0); // 欠款
								$("#law_date").val(0); // 有效天数

								$("#tichengRegion").clearform();
							}
							$("#btnNew").click(function() { // 重置
								clearUserpart();
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
							$("#money_cash").change(function() {
								jisuan();
							});
							$("#money_yinhang_money").change(function() {
								jisuan();
							});
							function jisuan() {
								// 计算提成、业绩
								tichengYeji("staffId1", "somemoney1", "yeji1");
								tichengYeji("staffId2", "somemoney2", "yeji2");
								tichengYeji("staffId3", "somemoney3", "yeji3");
							}

							function tichengYeji(staffId, somemoney, yeji) {
								var s1 = $("#" + staffId).val(), m1;
								$("#" + somemoney).val(0);
								$("#" + yeji).val(0);
								if (s1) {
									m1 = parseFloat($("#money_cash").val())
											+ parseFloat($(
													"#money_yinhang_money")
													.val());

									$("#" + yeji).val(m1); // 业绩
									// 计算提成
									var us = fillmaps.usersorts[$("#membersort")[0].selectedIndex];
									if (!us) {
										us = ous;
									}
                                    if(us.price_chengben)
                                        m1-=us.price_chengben;
									var tc = us.guding_ticheng * m1 / 100;
                                    switch (staffId){
                                        case "staffId1":
                                            if(us.tc1)
                                                tc = us.tc1*m1/100;
                                            break;
                                        case "staffId2":
                                            if(us.tc2)
                                                tc = us.tc2*m1/100;
                                            break;
                                        case "staffId3":
                                            if(us.tc3)
                                                tc = us.tc3*m1/100;
                                            break;
                                    }
									$("#" + somemoney).val(tc.toFixed(2));
								}
							}

							$("#incard")
									.validate(
											{
												submitHandler : function(form) { // 保存
													var money_cash = parseFloat($(
															"#money_cash")
															.val());
													var money_yinhang_money = parseFloat($(
															"#money_yinhang_money")
															.val());
													if (!(money_cash
															+ money_yinhang_money > 0)) {
														toastr
																.warning("请输入续费金额");
														$("#money_cash")
																.focus();
														return;
													}
													if (fillmaps.incard.flag1 == "1003") { // 次数卡
														var cishu = parseInt($(
																"#cishu").val());
														if (cishu < 1) {
															toastr
																	.warning("请输入续费次数");
															$("#cishu").focus();
															return;
														}
													} else { // 折扣卡

													}

													/*if (!(parseInt($(
															"#law_date").val()) > 0)) {
														toastr
																.warning("请输入有效天数");
														$("#law_date").focus();
														return;
													}*/

													var s = $(
															"#incard #staffId1")
															.val();
													if (!s) {
														toastr.warning("请选择员工");
														$("#incard #staffId1")
																.focus();
														return;
													}

													saveUserpart();
												}
											});
							function saveUserpart(obj) {
								var partObj = $("#incard").formobj();
								partObj.incardId = fillmaps.incard._id;
								partObj.cardId = fillmaps.incard.usercardId;
								parent.$.shade.show();
								$
										.post(
												"${ctxPath}/user/userIncard/xufei.do",
												partObj,
												function(data, status) {
													if (data.success) {
														reloadIncard();
														clearUserpart();
														queryUserpartList();

														// 打印小票
														var ds = [ data.data ];

														$("#prt")[0].src = '${ctxPath }/user/userpart/print.do?type=3&ids='
																+ ds.join(',');
													} else {
														layer
																.alert(data.message);
													}
													parent.$.shade.hide();
												});
							}

							$("#btnPrint")
									.click(
											function() { // 打印小票
												if (!selectedUserpartRowId) {
													toastr
															.warning("请选择要打印的续费记录");
													return;
												}
												var kd = $(
														"#"
																+ selectedUserpartRowId,
														$("#myTable")).data(
														"rawData");

												var ds = [ kd._id ];

												$("#prt")[0].src = '${ctxPath }/user/userpart/print.do?type=3&ids='
														+ ds.join(',');
											});
							$("#btnBack").click(function() { // 返回
								parent.layer.closeAll();
							});
							$("#codeDiv").hide();
							if (fillmaps.incard.flag1 == "1003") { // 次数卡
								$(".cishur").show();
								$(".zhekour").hide();
							} else { // 折扣卡
								$(".cishur").hide();
								$(".zhekour").show();
							}

							$("#myTable")
									.grid(
											{
												datatype : "local",
												colNames : [ "操作", "会员号",
														"会员姓名", "会员类型", "续费日期",
														"续费额", "总额", "员工1",
														"员工2", "员工3", "欠费",
														"续费次数", "银行卡", "现金",
														"续有效天数", "续次数",
														"员工1提成", "业绩1", "业绩2",
														"业绩3" ],
												shrinkToFit : false,
												onSelectRow : function(id) {
													selectedUserpartRowId = id;
												},
												ondblClickRow : function(id) {
													var up = $("#" + id,
															$("#myTable"))
															.data("rawData");
													if (up.incardId) { // 双击进入消卡消费界面

													}
												},
												pager : null,
												colModel : [
														{
															name : "idx",
															index : "idx",
															width : 60,
															sortable : false,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='javascript:void(0);' onClick='delRow(\""
																		+ options.rowId
																		+ "\")'>删除</a>&nbsp;&nbsp;";
																return v;
															}
														},
														{
															name : "id_2",
															index : "id_2",
															sortable : false
														},
														{
															name : "name",
															index : "name",
															sortable : false,
															width : 100
														},
														{
															name : "usersortName",
															index : "usersortName",
															sortable : false,
															width : 100
														},
														/*{
															name : "membersort",
															sortable : false,
															index : "membersort",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.usersorts;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name1;
																	}
																}
																return "无";
															}
														},*/
														{
															name : "createTime",
															sortable : false,
															index : "createTime"
														},
														{
															name : "money1",
															sortable : false,
															index : "money1"
														},
														{
															name : "money4",
															sortable : false,
															index : "money4"
														},
														{
															name : "staffId1",
															sortable : false,
															index : "staffId1",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.staffs;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "无";
															}
														},
														{
															name : "staffId2",
															sortable : false,
															index : "staffId2",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.staffs;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "无";
															}
														},
														{
															name : "staffId3",
															sortable : false,
															index : "staffId3",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.staffs;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "无";
															}
														},
														{
															name : "money_qian",
															sortable : false,
															index : "money_qian"
														},
														{
															name : "xu_cishu",
															sortable : false,
															index : "xu_cishu"
														},
														{
															name : "money_yinhang_money",
															sortable : false,
															index : "money_yinhang_money"
														},
														{
															name : "money_cash",
															sortable : false,
															index : "money_cash"
														},
														{
															name : "law_date",
															sortable : false,
															index : "law_date"
														},
														{
															name : "cishu",
															sortable : false,
															index : "cishu"
														},
														{
															name : "somemoney1",
															sortable : false,
															index : "somemoney1"
														}, {
															name : "yeji1",
															sortable : false,
															index : "yeji1"
														}, {
															name : "yeji2",
															sortable : false,
															index : "yeji2"
														}, {
															name : "yeji3",
															sortable : false,
															index : "yeji3"
														}

												]
											});

							queryUserpartList();
						});

		function queryUserpartList() {
			$.post("${ctxPath}/user/userpart/queryByIncard.do", {
				incardId : fillmaps.incard._id,
				type : 3,
				all : true
			}, function(data, status) {
				selectedUserpartRowId = null;
				$("#myTable").jqGrid('clearGridData');
				for (var i = 0; i <= data.length; i++) {
					$("#myTable").jqGrid('addRowData', i + 1, data[i]);
				}
			});
		}

		function delRow(idx) {
			var kd = $("#" + idx, $("#myTable")).data("rawData");

			layer
					.confirm(
							'确定删除续费记录?',
							function(index) {
								layer.close(index);
								$
										.post(
												"${ctxPath}/user/userIncard/verifycode.do",
												{
													incardId : kd.incardId
												},
												function(data, status) {
													if (data.success) {
														$("#codeDiv").show();
														$("#code").val("");
														$("#phone").text(
																data.data);
														layer
																.open({
																	type : 1,
																	title : "短信验证码",
																	area : [
																			'400px',
																			'240px' ],
																	btn : [
																			'确定',
																			'取消' ],
																	yes : function(
																			index,
																			layero) {
																		var code = $(
																				"#code")
																				.val();
																		if (!code) {
																			toastr
																					.warning("请输入短信验证码");
																			$(
																					"#code")
																					.focus();
																			return;
																		}

																		$
																				.post(
																						"${ctxPath}/user/userIncard/xufeiRemove.do",
																						{
																							userpartId : kd._id,
																							code : code
																						},
																						function(
																								data,
																								status) {
																							if (data.success) {
																								layer
																										.closeAll();
																								reloadIncard();
																								queryUserpartList();
																							} else {
																								layer
																										.alert(data.message);
																							}
																						});
																	},
																	cancel : function(
																			index) {
																		layer
																				.closeAll();
																	},
																	content : $("#codeDiv")
																});
													} else {
														layer
																.alert(data.message);
													}
												});
							});

		}
		function reloadIncard() {
			parent.$.shade.show();
			$.post("${ctxPath}/user/userIncard/queryById.do", {
				id : fillmaps.incard._id
			}, function(data, status) {
				$("#incard").fillform(data);
				parent.$.shade.hide();
			});
		}
		function helloDate(d) {
			//alert(d);
		}
	</script>

	<!-- 验证码div -->
	<div id="codeDiv" class="wrapper wrapper-content"
		style="width: 95%; height: 95%">
		<div class="row">
			<form id="codeForm" method="post" class="form-horizontal"
				onSubmit="return false;">
				<div class="form-group">
					<label class="col-sm-4 control-label">短信验证码：</label>
					<div class="col-sm-7">
						<input id="code" name="code" type="text" class="form-control"><span
							class="help-block m-b-none">验证码已发送到手机号<span id="phone"></span>。
						</span>
					</div>
				</div>
				<input type="submit" value="提交" style="display: none">
			</form>
		</div>
	</div>
	<iframe id="prt" src="" width="180" height="400" style="display: none"></iframe>
</body>
</html>
