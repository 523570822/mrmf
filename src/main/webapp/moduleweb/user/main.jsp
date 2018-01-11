<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />
<html>
<head>
<title></title>
<style>
.maxHeight {
	height: 100%;
	border: 1px solid #ddd;
	overflow: auto;
	padding-bottom: 15px;
}

.maxWidth {
	width: 100%;
}

.form-control2 {
	width: 60%;
	display: inline;
}

.input-group .form-control2 {
	width: 60%;
	display: inline;
}

.input-group {
	display: inline;
}

.center {
	text-align: center;
}

.form-control, .single-line {
	padding: 4px 6px;
}

.form-group {
	margin-bottom: 4px;
}

.hr-line-dashed {
	margin: 6px 0px;
}

.wrapper-content {
	padding: 0px;
}

.ibox-content {
	padding-top: 6px;
}

.ibox-title {
	padding-top: 6px;
	padding-bottom: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<div class="row">
							<div class="col-sm-3">
								<h5>会员管理</h5>
							</div>
							<form id="queryFrm" method="post">
								<div class="col-sm-6">
									<input id="condition" name="condition" type="text"
										class="form-control" maxlength="20"
										placeholder="输入会员电话、姓名、卡号或卡表面号进行查询">
								</div>
								<div class="col-sm-2">
									<button class="btn btn-success" type="submit">查询</button>
								</div>
							</form>
						</div>
					</div>
					<div class="ibox-content">
						<div class="row">
							<form id="kaidanFrm" method="post">
								<div class="col-sm-2 maxHeight">
									开单 <input id="kaidan_id" name="kaidan_id" type="text"
										class="form-control">
									<button id="btnKaidan" class="btn btn-primary maxWidth"
										style="margin-top: 5px;" type="button">开单</button>
									<button class="btn btn-success maxWidth" type="submit">查询</button>
									<button id="btnUnionKaidan" class="btn btn-warning maxWidth"
										type="button">并单</button>
									<button id="btnDelKaidan" class="btn btn-danger maxWidth"
										style="margin-top: 15px;" type="button">删除单号</button>
									<div class="jqGrid_wrapper">
										<table id="kaidanTable"></table>
									</div>
								</div>
							</form>
							<div class="col-sm-10">
								<form id="userpart" method="post" class="form-horizontal">
									<input type="hidden" id="_id" name="_id"><input
										type="hidden" id="organId" name="organId" value="${organId}">
									<input type="hidden" id="userId" name="userId"> <input
										type="hidden" id="flag2" name="flag2" value="false"> <input
										type="hidden" id="delete_flag" name="delete_flag"
										value="false"> <input type="hidden" id="guazhang_flag"
										name="guazhang_flag" value="true"> <input
										type="hidden" id="kaidanId" name="kaidanId"> <input
										type="hidden" id="bigsort" name="bigsort"> <input
										type="hidden" id="cardId" name="cardId"> <input
										type="hidden" id="incardId" name="incardId"><input
										type="hidden" id="id_2" name="id_2"><input
										type="hidden" id="images" name="images"> <input
										type="hidden" id="userpartId" name="userpartId">
									<div class="form-group">
										<label class="col-sm-2 control-label">卡表面号:</label>
										<div class="col-sm-2">
											<input id="cardno" name="cardno" type="text"
												class="form-control" minlength="2" maxlength="50">
										</div>
										<label class="col-sm-2 control-label">会员姓名:</label>
										<div class="col-sm-2">
											<input id="name" name="name" type="text" class="form-control"
												minlength="2" maxlength="50">
										</div>
										<label class="col-sm-2 control-label">手机号:</label>
										<div class="col-sm-2">
											<input id="phone" name="phone" type="text"
												class="form-control" mobile="true">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">会员类型:</label>
										<div class="col-sm-2">
											<input type="text" class="form-control suggest"
												id="membersort" name="membersort"
												suggest="{data :fillmaps.usersorts,searchFields : [ 'name1' ],keyField : 'name1'}">
										</div>
										<label class="col-sm-2 control-label">会员生日:</label>
										<div class="col-sm-2">
											<input id="birthday" name="birthday"
												class="laydate-icon form-control layer-date" placeholder=""
												data-mask="9999-99-99"
												laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59',choose: helloDate}">
										</div>
										<label class="col-sm-2 control-label">居住地:</label>
										<div class="col-sm-2">
											<input id="place" name="place" type="text"
												class="form-control" minlength="2" maxlength="100">
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label feihuiyuan">折扣:</label>
										<div class="col-sm-2 feihuiyuan">
											<input id="money6" name="money6" type="number" min="0"
												class="form-control" value="100" required>
										</div>
										<label class="col-sm-2 control-label feihuiyuan">抹零:</label>
										<div class="col-sm-2 feihuiyuan">
											<input id="money5" name="money5" type="number" min="0"
												class="form-control">
										</div>
										<label class="col-sm-2 control-label huiyuan">实交款额:</label>
										<div class="col-sm-2 huiyuan">
											<input id="money2" name="money2" type="number" min="0"
												class="form-control">
										</div>
										<label class="col-sm-2 control-label huiyuan">欠费:</label>
										<div class="col-sm-2 huiyuan">
											<input id="money_qian" name="money_qian" type="number"
												min="0" class="form-control" value="0">
										</div>
										<label class="col-sm-2 control-label">应交款额:</label>
										<div class="col-sm-2">
											<input id="money1" name="money1" type="number" min="0"
												class="form-control" required value="0">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"><span
											class="xiangmu">选择项目:</span></label>
										<div class="col-sm-2">
											<span class="xiangmu"><input type="text"
												class="form-control suggest" id="smallsort" name="smallsort"
												suggest="{data :fillmaps.smallsorts}"> </span>
										</div>
										<label class="col-sm-1 control-label"><span
											class="cishur">次数:</span></label>
										<div class="col-sm-1">
											<span class="cishur"> <input id="allcishu"
												name="allcishu" type="digits" min="0" class="form-control"
												value="0"></span>
										</div>
										<label class="col-sm-1 control-label huiyuan">积分:</label>
										<div class="col-sm-1 huiyuan">
											<input id="coin" name="coin" type="digits" min="0"
												class="form-control" value="0">
										</div>
										<label class="col-sm-2 control-label huiyuan">余额:</label>
										<div class="col-sm-2 huiyuan">
											<input id="money4" name="money4" type="number" min="0"
												class="form-control" value="0" disabled>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">员工1:</label>
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
										<c:if test="${organSetting.displayTicheng}">
											<label class="col-sm-1 control-label">提成1:</label>
											<div class="col-sm-2">
												<input id="somemoney1" name="somemoney1" type="number"
													min="0" class="form-control" disabled>
											</div>
										</c:if>
										<c:if test="${!organSetting.displayTicheng}">
											<input id="somemoney1" name="somemoney1" type="hidden">
										</c:if>

										<label class="col-sm-1 control-label">业绩1:</label>
										<div class="col-sm-2">
											<input id="yeji1" name="yeji1" type="number" min="0"
												class="form-control" disabled>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">员工2:</label>
										<div class="col-sm-4">
											<input type="text" class="form-control form-control2 suggest"
												id="staffId2" name="staffId2"
												suggest="{data :fillmaps.staffs,style2:true}"> <span
												class="feihuiyuan"> &nbsp;&nbsp;<label
												class="control-label"><input id="dian2" name="dian2"
													type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
												class="control-label"><input id="quan2" name="quan2"
													type="checkbox">&nbsp;劝</label></span>
										</div>
										<c:if test="${organSetting.displayTicheng}">
											<label class="col-sm-1 control-label">提成2:</label>
											<div class="col-sm-2">
												<input id="somemoney2" name="somemoney2" type="number"
													min="0" class="form-control" disabled>
											</div>
										</c:if>
										<c:if test="${!organSetting.displayTicheng}">
											<input id="somemoney2" name="somemoney2" type="hidden">
										</c:if>
										<label class="col-sm-1 control-label">业绩2:</label>
										<div class="col-sm-2">
											<input id="yeji2" name="yeji2" type="number" min="0"
												class="form-control" disabled>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">员工3:</label>
										<div class="col-sm-4">
											<input type="text" class="form-control form-control2 suggest"
												id="staffId3" name="staffId3"
												suggest="{data :fillmaps.staffs,style2:true}"> <span
												class="feihuiyuan"> &nbsp;&nbsp;<label
												class="control-label"><input id="dian3" name="dian3"
													type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
												class="control-label"><input id="quan3" name="quan3"
													type="checkbox">&nbsp;劝</label></span>
										</div>
										<c:if test="${organSetting.displayTicheng}">
											<label class="col-sm-1 control-label">提成3:</label>
											<div class="col-sm-2">
												<input id="somemoney3" name="somemoney3" type="number"
													min="0" class="form-control" disabled>
											</div>
										</c:if>
										<c:if test="${!organSetting.displayTicheng}">
											<input id="somemoney3" name="somemoney3" type="hidden">
										</c:if>
										<label class="col-sm-1 control-label">业绩3:</label>
										<div class="col-sm-2">
											<input id="yeji3" name="yeji3" type="number" min="0"
												class="form-control" disabled>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">备注(爱好):</label>
										<div class="col-sm-10">
											<input id="love" name="love" type="text" class="form-control"
												minlength="2" maxlength="50">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">赠送金额:</label>
										<div class="col-sm-2">
											<input id="song_money" name="song_money" type="number"
												min="0" class="form-control" value="0">
										</div>
										<label class="col-sm-2 control-label"><span
											class="huiyuan">有效期:</span></label>
										<div class="col-sm-2">
											<span class="huiyuan"> <input id="law_day"
												name="law_day" type="text" class="form-control" disabled></span>
										</div>
										<div class="col-sm-4">
											<button id="takeChanpin" class="btn btn-info" type="button">选择产品</button>
											&nbsp;&nbsp;
											<button id="takePic" class="btn btn-success" type="button">照片</button>
											&nbsp;&nbsp;
											<button id="takeDoc" class="btn btn-primary" type="button">VIP档案</button>
											<input type="hidden" id="doc" name="doc">
										</div>
									</div>

									<div class="hr-line-dashed"></div>

									<div class="form-group">
										<label class="col-sm-2 control-label"><input
											id="flag1" name="flag1" type="checkbox">&nbsp;美容会员</label>
										<div class="col-sm-3 center">
											<label class="control-label"><input id="miandan"
												name="miandan" type="checkbox">&nbsp;免单</label>
										</div>
										<div class="col-sm-2 center">
											<label class="control-label"><input id="flag_sheng"
												name="flag_sheng" type="checkbox">&nbsp;生客</label>
										</div>
										<div class="col-sm-2 center">
											<label class="control-label"><input id="laibin_flag"
												name="laibin_flag" type="checkbox">&nbsp;来宾</label>
										</div>
										<label class="col-sm-1 control-label">性别:</label>
										<div class="col-sm-2">
											<label class="control-label"><input id="sex1"
												name="sex" type="radio" value="男" class="sex">&nbsp;男</label>&nbsp;&nbsp;<label
												class="control-label"><input id="sex2" name="sex"
												type="radio" value="女" class="sex" checked>&nbsp;女</label>
										</div>
									</div>

									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<div class="col-sm-12">
											<button id="btnNew" class="btn btn-success" type="button">重置</button>
											<button id="btnSave" class="btn btn-primary" type="submit">保存</button>
											<button id="btnEdit" class="btn btn-info" type="button">修改</button>
											<button id="btnWaimai" class="btn btn-warning" type="button">外卖</button>
											<button id="btnJiezhang" class="btn btn-danger" type="button"
												style="width: 80px; margin-left: 5px; margin-right: 5px;">结账</button>
											<button id="btnXufei" class="btn btn-outline btn-success"
												type="button">续费</button>
											<button id="btnBuka" class="btn btn-outline btn-primary"
												type="button">补卡/密码</button>
											<button id="btnZhuanka" class="btn btn-outline btn-warning"
												type="button">转卡/欠费</button>
											<button id="btnPrint" class="btn btn-outline btn-info"
												type="button">打印小票</button>
											<c:if test="${organSetting.canTixian }">
												<button id="btnTixian" class="btn btn-outline btn-danger"
													type="button">提取现金</button>
											</c:if>
											<button id="btnZika" class="btn btn-outline btn-primary"
												type="button">子卡管理</button>
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
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var manageType, selectedKaidanRowId, selectedUserpartRowId, printds, images = [], waimai, editUser = false;
		$()
				.ready(
						function() {
						    var flag = false;//次数卡办理使用
							var flag1 = true;//change事件控制
							// ----------------开单开始-----------------
							function queryKaidanList() {
								var kaidan_id = $("#kaidan_id").val();
								$.post("${ctxPath}/user/kaidan/query.do", {
									kaidan_id : kaidan_id
								}, function(data, status) {
									$.shade.hide();
									layer.closeAll();
									clearUserpart();
									selectedKaidanRowId = null;
									$("#myTable").jqGrid('clearGridData');
									$("#kaidanTable").jqGrid('clearGridData');
									var c = $("#kaidanList")[0];
									c.options.length = 0;
									for (var i = 0; i < data.length; i++) {
										var d = data[i];
										$("#kaidanTable").jqGrid('addRowData',
												i, d);
										var option = new Option(d.kaidan_id,
												d._id);
										c.options[c.options.length] = option;
									}
								});
							}
							$("#kaidanFrm").validate({
								submitHandler : function(form) { // 开单查询按钮
									queryKaidanList();
								}
							});
							$("#kaidanTable")
									.grid(
											{
												datatype : "local",
												colNames : [ "单号", "金额" ],
												shrinkToFit : false,
												onSelectRow : function(id) {
													if (!selectedKaidanRowId
															|| selectedKaidanRowId != id) {
														selectedKaidanRowId = id;
														queryUserpartList();
													}
												},
												pager : null,
												colModel : [ {
													name : "kaidan_id",
													index : "kaidan_id",
													sortable : false,
													width : 60
												}, {
													name : "yujiesuan_money",
													index : "yujiesuan_money",
													sortable : false,
													width : 60
												} ]
											});
							$("#btnDelKaidan")
									.click(
											function() {
												if (!selectedKaidanRowId) {
													toastr.warning("请选择要删除的开单");
													return;
												}
												if ($("#myTable").jqGrid(
														'getDataIDs').length > 0) {
													toastr
															.warning("改单号下面还有未结账的消费信息!");
													return;
												}
												var kd = $(
														"#"
																+ selectedKaidanRowId,
														$("#kaidanTable"))
														.data("rawData");
												$
														.post(
																"${ctxPath}/user/kaidan/remove.do",
																{
																	id : kd._id
																},
																function(data,
																		status) {
																	if (data.success) {
																		queryKaidanList();
																	} else {
																		layer
																				.alert(data.message);
																	}
																});
											});
							$("#btnKaidan")
									.click(
											function() {
                                                var kaidan_id = $("#kaidan_id").val();
												$
														.post(
																"${ctxPath}/user/kaidan/upsert.do",
																{kdId:kaidan_id},
																function(data,
																		status) {
																	if (data.success) {
                                                                        $("#kaidan_id").val("");
																		queryKaidanList();
																	} else {
																		layer
																				.alert(data.message);
																	}
																});
											});
							$("#btnUnionKaidan")
									.click(
											function() {
												$("#unionDiv").show();
												layer
														.open({
															type : 1,
															title : "并单",
															area : [ '250px',
																	'400px' ],
															btn : [ '确定', '取消' ],
															yes : function(
																	index,
																	layero) {
																var obj = $(
																		"#unionForm")
																		.formobj();
																var kdList = obj.kaidanList;
																if (!kdList
																		|| kdList.length < 2) {
																	layer
																			.alert("请选择多个要合并的单号!");
																	return;
																}
																$.shade.show();
																$
																		.post(
																				"${ctxPath}/user/kaidan/union.do",
																				{
																					kaidanList : kdList
																							.join(',')
																				},
																				function(
																						data,
																						status) {
																					if (data.success) {
																						queryKaidanList();
																						$(
																								"#myTable")
																								.jqGrid(
																										'clearGridData');
																					} else {
																						layer
																								.alert(data.message);
																					}
																					$.shade
																							.hide();
																				});
															},
															cancel : function(
																	index) {
																layer
																		.closeAll();
															},
															content : $("#unionDiv")
														});
											});
							$("#unionDiv").hide();
							// 初始化未结算开单列表
							queryKaidanList();
							// ----------------开单结束-----------------

							// ----------------会员查询开始-----------------
							// 会员信息查询
							$("#queryFrm").validate({
								submitHandler : function(form) {
									var obj = $("#queryFrm").formobj();
									if (!obj.condition) {
										toastr.warning("请输入查询条件!");
										$("#queryFrm #condition").focus();
										return;
									}
									queryFrm();
								}
							});
							// ----------------会员查询结束-----------------

							// ----------------结账开始-----------------
							$("#jiezhangDiv").hide();
							var userpartIds;// 是否存在单打折
							function jiezhangJisuan() {
								var ids = $("#myTable").jqGrid('getDataIDs');
								if (ids.length < 1
										&& (!waimai || waimai.length < 1)) {
									toastr.warning("没有需要结账的消费记录");
									return false;
								}
								var money1 = 0;
								userpartIds = [];
								for (var i = 0; i < ids.length; i++) {
									var up = $("#" + ids[i], $("#myTable"))
											.data("rawData");
									if (!up.flag2 && up.guazhang_flag
											&& !up.miandan) { // 未交款和挂账的，以及非免单的计入总金额
										money1 += up.money1 - up.money5; // 结算金额刨除抹零金额
										money1 -= up.money_qian; // 结算金额刨除欠费金额
										userpartIds.push(up._id);
									}
								}

								if (waimai) { // 外卖结账金额
									for (var i = 0; i < waimai.length; i++) {
										if (!waimai[i].miandan) { // 非免单计费
											money1 += waimai[i].money1
													- waimai[i].money_qian;
										}
									}
								}

								$("#jiezhangDiv #money1").val(money1);
								$("#jiezhangDiv #yingshou_cash").val(money1);
								var rest = money1;
								var yinhang = $("#money_yinhang_money").val();
								var li = $("#money_li_money").val();
								var cash = $("#money_cash").val();
								if (yinhang) {
									rest -= parseFloat(yinhang);
								} else {
									$("#money_yinhang_money").val(0);
								}
								if (li) {
									rest -= parseFloat(li);
								} else {
									$("#money_li_money").val(0);
								}
								if (!cash) {
									cash = 0;
									$("#money_cash").val(0);
								}

								$("#jiezhangDiv #yingshou_cash").val(rest);

								$("#jiezhangDiv #money3").val(cash - rest);// 找零
								return true;
							}
							function jiezhang(form) {
								var money3 = parseFloat($(
										"#jiezhangDiv #money3").val());
								if (money3 < 0) {
									layer.alert("金额不足!");
									return;
								}
								if (selectedKaidanRowId) {
									// 开单结算
									var kd = $("#" + selectedKaidanRowId,
											$("#kaidanTable")).data("rawData");
									$("#jiezhangDiv #kaidanId").val(kd._id);
								} else {
									// 非开单结算（会员查询）
									$("#jiezhangDiv #kaidanId").val("");
								}
								$("#jiezhangDiv #userpartIds").val(
										userpartIds.join(","));

								var ids = $("#myTable").jqGrid('getDataIDs');
								printds = [];
								for (var i = 0; i < ids.length; i++) {
									var up = $("#" + ids[i], $("#myTable"))
											.data("rawData");
									printds.push(up._id);
								}
								$.shade.show();
								$.post("${ctxPath}/user/userpart/jiezhang.do",
										$("#jiezhangDiv").formobj(), function(
												data, status) {
											if (data.success) {
												layer.closeAll();
												$("#btnPrint").click();
												queryKaidanList();
												$("#myTable").jqGrid(
														'clearGridData');
											} else {
												layer.alert(data.message);
											}
											$.shade.hide();
										});
							}
							$("#money_yinhang_money").change(function() {
								jiezhangJisuan();
							});
							$("#money_li_money").change(function() {
								jiezhangJisuan();
							});
							$("#money_cash").change(function() {
								jiezhangJisuan();
							});
							$("#jiezhangForm").validate({
								submitHandler : function(form) { // 保存
									jiezhang(form);
								}
							});
							$("#btnJiezhang").click(
									function() { // 结账
										if (selectedKaidanRowId) {
											// 开单结账，获取是否有外卖
											var kd = $(
													"#" + selectedKaidanRowId,
													$("#kaidanTable")).data(
													"rawData");
											$.get("${ctxPath}/waimai/query.do",
													{
														kaidanId : kd._id
													}, function(data) {
														waimai = data;
														popupJiezhang();
													});
										} else {
											waimai = null;
											popupJiezhang();
										}
									});
							function popupJiezhang() {
								$("#jiezhangDiv").clearform();
								if (!jiezhangJisuan()) {
									return;
								}
								$("#cardDiv #id_2").enable();
								$("#cardDiv #id_2").val("");
								$("#jiezhangDiv").show();
								layer.open({
									type : 1,
									title : "结账收银",
									area : [ '400px', '440px' ],
									btn : [ '结账', '取消' ],
									yes : function(index, layero) {
										$("#jiezhangForm").submit();
									},
									cancel : function(index) {
										layer.closeAll();
									},
									content : $("#jiezhangDiv")
								});
							}
							// ----------------结账结束-----------------

							// ----------------保存相关-----------------
							$("#cardForm").validate({
								submitHandler : function(form) { // 保存
									var obj = $("#cardForm").formobj();
									if (!(obj.passwd == obj.passwd2)) {
										layer.alert("密码不一致!");
									} else {
										saveUserpart(obj);
									}
								}
							});
							function saveUserpart(obj) {
								var kd = $("#" + selectedKaidanRowId,
										$("#kaidanTable")).data("rawData");
								var partObj = $("#userpart").formobj();
								partObj.kaidanId = kd._id;
								partObj.guazhang_flag = true; // 是否挂账
								partObj.flag2 = false; // 是否交款
								partObj.delete_flag = false; // 是否删除
								if (obj) {
									partObj.id_2 = obj.id_2; // 卡号
									partObj.passwd = obj.passwd;// 卡密码
								}
								doSaveUserpart(partObj);
							}
							function doSaveUserpart(partObj) {
								$.shade.show();
								$.post("${ctxPath}/user/userpart/upsert.do",
										partObj, function(data, status) {
											$.shade.hide();
											if (data.success) {
												if (editUser) {
													$("#queryFrm").submit();
												} else {
													queryUserpartList();
												}
												clearUserpart();
												layer.closeAll();
												$("#cardForm").clearform();
											} else {
												layer.alert(data.message);
											}
										});
							}
							$("#cardDiv").hide();
							$("#userpart")
									.validate(
											{
												submitHandler : function(form) { // 保存
													var s = $(
															"#userpart #membersort")
															.val();
													var ms = fillmaps.usersorts[$(
															"#membersort")
															.attr(
																	"selectedIndex")];
													if (!s) {
														toastr
																.warning("请选择会员类型");
														$(
																"#userpart #sg_membersort")
																.focus();
														return;
													}
													s = $("#userpart #name")
															.val();
													if (!s
															&& ms.flag1 != "1000") {
														toastr
																.warning("请输入会员姓名");
														$("#userpart #name")
																.focus();
														return;
													}
													s = $("#userpart #staffId1")
															.val();
													if (!s) {
														toastr
																.warning("请选择做活员工");
														$(
																"#userpart #sg_staffId1")
																.focus();
														return;
													}
													if (ms.flag1 != "1000") { // 会员
														if ($(
																"#userpart #phone")
																.val() == "") {
															toastr
																	.warning("会员手机号不能为空!");
															$(
																	"#userpart #phone")
																	.focus();
															return;
														}
														if (ms.flag1 == "1003" && $("#userpart #smallsort").val() == "") { // 次数卡服务项目不能为空
															toastr.warning("服务项目不能为空!");
															$("#userpart #sg_smallsort").focus();
															return;
														}
                                                        if ((ms.flag1 == "1003") && ($("#userpart #allcishu").val() == "")) { // 次数卡次数不能为空
                                                            toastr.warning("次数卡次数不可为空!");
                                                            $("#userpart #sg_smallsort").focus();
                                                            return;
                                                        }
													} else {
														var s = $(
																"#userpart #smallsort")
																.val();
														if (!s) {
															toastr
																	.warning("请选择服务项目");
															$(
																	"#userpart #sg_smallsort")
																	.focus();
															return;
														}
													}
													if (!selectedKaidanRowId
															&& !editUser) {
														toastr
																.warning("请选择开单号");
														return;
													}
													var money1 = parseFloat($(
															"#money1").val());
													var qian = parseFloat($(
															"#money_qian")
															.val());
													if (qian > money1) {
														toastr
																.warning("欠费金额不能大于应缴金额");
														return;
													}
													if (ms.flag1 != "1000") { // 会员卡
														if (editUser) {// 修改会员信息
															var partObj = $(
																	"#userpart")
																	.formobj();
															doSaveUserpart(partObj);
														} else {
															$("#cardDiv")
																	.clearform();
															$("#cardDiv #cardType2")[0].checked = true;
															$("#cardDiv")
																	.show();
															$("#cardForm #id_2")
																	.val(
																			$(
																					"#userpart #id_2")
																					.val());
															layer
																	.open({
																		type : 1,
																		title : "会员卡密码录入",
																		area : [
																				'500px',
																				'330px' ],
																		btn : [
																				'确定',
																				'取消' ],
																		yes : function(
																				index,
																				layero) {
																			$(
																					"#cardForm")
																					.submit();
																		},
																		cancel : function(
																				index) {
																			layer
																					.closeAll();
																		},
																		content : $("#cardDiv")
																	});
														}
													} else {
														saveUserpart();
													}

												}
											});
							// ----------------保存结束-----------------

							// ----------------计算开始-----------------
							function jisuan(f) {
                                    var ms = fillmaps.usersorts[$("#membersort")
                                        .attr("selectedIndex")];
                                    var ss = fillmaps.smallsorts[$("#smallsort")
                                        .attr("selectedIndex")];
                                    if (!ms || ms.flag1 === "1000") { // 非会员
                                        var m = $("#money6").val();
                                        $(".huiyuan").hide();
                                        $(".cishur").hide();
                                        $(".feihuiyuan").show();
                                        $(".xiangmu").show();
                                        var inited = $("#money1").data("inited");
                                        if (!f && !inited) {
                                            if (ss) {
                                                if (m) {
                                                    $("#money1").val(
                                                        ss.price * m / 100);
                                                } else {
                                                    $("#money1").val(ss.price);
                                                }
                                            } else {
                                                $("#money1").val(0); // 应交款额
                                            }
                                            $("#money1").data("inited", true);
                                        }
                                        $("#coin").val(0);

                                    } else if (ms.flag1 === "1001") { // 单纯打折卡
                                        $(".huiyuan").show();
                                        $(".cishur").hide();
                                        $(".feihuiyuan").hide();
                                        $(".xiangmu").hide();

                                        jisuanFieldValue("", 0, ms, f);
                                    } else if (ms.flag1 === "1002") { // 存钱打折卡
                                        $(".huiyuan").show();
                                        $(".cishur").hide();
                                        $(".feihuiyuan").hide();
                                        $(".xiangmu").hide();

                                        jisuanFieldValue("", 0, ms, f);
                                    } else if (ms.flag1 === "1003") { // 次数卡
                                        $(".huiyuan").show();
                                        $(".cishur").show();
                                        $(".feihuiyuan").hide();
                                        $(".xiangmu").show();

                                        jisuanFieldValue(ms.name2, ms.cishu, ms, f);
                                    }
                                    if (ss)
                                        $("#bigsort").val(ss.bigcode);
                                    // 计算提成、业绩
                                    tichengYeji("staffId1", "somemoney1", "yeji1");
                                    tichengYeji("staffId2", "somemoney2", "yeji2");
                                    tichengYeji("staffId3", "somemoney3", "yeji3");

							}
							$("#yeji1")
									.change(
											function() {
												var m1 = $("#money1").val();
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
												var m1 = $("#money1").val();
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
												var m1 = $("#money1").val();
												var y1 = $("#yeji3").val();
												if (m1 && m1 > 0 && y1 != "") {
													tichengYeji("staffId3",
															"somemoney3",
															"yeji3", true);
												}
											});
							function tichengYeji(staffId, somemoney, yeji, flag) {//alert(flag1)
								var s1 = $("#" + staffId).val(), m1 = $(
										"#money1").val();
								var ms = fillmaps.usersorts[$("#membersort")
										.attr("selectedIndex")];
								var ss = fillmaps.smallsorts[$("#smallsort")
										.attr("selectedIndex")];
								$("#" + somemoney).val(0);
								if (flag) {
									m1 = $("#" + yeji).val();
								} else {
									$("#" + yeji).val(0);
								}
								if (s1 && m1 && m1 > 0) {
									var m5 = $("#money5").val();
									if (m5 == "")
										m5 = "0";
                                    $("#" + yeji).val(m1 - parseFloat(m5)); // 业绩 = money1 - money5抹零
									//在这里计算提成和业绩，取三个框子提成和业绩比例
                                    var ktc = 0;
                                    if(ms){
                                        if(ms.price_chengben)
                                           m1-=ms.price_chengben;
                                        ktc = ms.guding_ticheng*m1/100;
                                        switch (staffId){
                                            case "staffId1":
                                                if(ms.tc1)
                                                    ktc = ms.tc1*m1/100;
                                                break;
                                            case "staffId2":
                                                if(ms.tc2)
                                                    ktc = ms.tc2*m1/100;
                                                break;
                                            case "staffId3":
                                                if(ms.tc3)
                                                    ktc = ms.tc3*m1/100;
                                                break;
                                        }
									}
									if (ms && ms.flag1 === "1000") { // 非会员
										// 计算提成
										var s = fillmaps.staffs[$("#" + staffId)
												.attr("selectedIndex")];
										for (var i = 0; i < fillmaps.staffTichengs.length; i++) {
											var st = fillmaps.staffTichengs[i];
											if (s.dutyId == st.staffpost
													&& ss._id == st.smallsort) {
												var yj = st.yeji;
												var mm1 = m1;
//												if (!flag && yj && yj > 0) {
//													mm1 = yj;
//													$("#" + yeji).val(mm1); // 业绩
//												}
												<c:if test="${organSetting.tichengDeductCost}">
												mm1 -= ss.price_chengben;
												if (mm1 < 0)
													mm1 = 0;
												</c:if>
												var tc;
                                                mm1-=parseFloat(m5);
												if (st.removeAmount) { // 去除固定
													tc = (mm1 - st.tichengCash)
															* st.tichengCashPercent
															/ 100;
												} else {
													tc = mm1
															* st.tichengCashPercent
															/ 100;
												}
												tc += st.tichengCash;

												$("#" + somemoney).val(
														tc.toFixed(2));
												break;
											}
										}
									} else if (ms) {
										$("#" + somemoney).val(ktc.toFixed(2));
									}
								}
							}
							function jisuanFieldValue(smallsort, cishu, ms, f) {
								$("#smallsort").fillform({
									smallsort : smallsort
								}); // 服务项目
//								if(flag){
//                                    $("#allcishu").val(cishu); // 次数
//								}
								var inited = $("#money1").data("inited");
								if (!f && !inited) {
									$("#money1").val(ms.money); // 金额
									$("#money1").data("inited", true);
								}
								$("#law_day").val(ms.law_day); // 有效期

								// 积分计算
								var cb = ms.coin_bilv > 0 ? $("#money1").val()
										/ ms.coin_bilv : 0;
								$("#coin").val(ms.coin_give + cb);
							}
							$("#money1").change(function() {
							    flag=false;
								jisuan(true);
							});
							$("#money5").change(function() { // 抹零变化
                                flag=false;
								jisuan();
							});
							$("#membersort").change(function() {
                                flag=false;
								$("#money1").data("inited", false);
								jisuan();
							});
							$("#smallsort").change(function() {
                                flag=true;
								$("#money1").data("inited", false);
								jisuan();
							});
							$("#staffId1").change(function() {
                                flag=false;
								jisuan();
							});
							$("#staffId2").change(function() {
                                flag=false;
								jisuan();
							});
							$("#staffId3").change(function() {
                                flag=false;
								jisuan();
							});
							$("#miandan").change(function() {
                                flag=false;
								jisuan();
							});
							$("#money6").change(
									function() { // 折扣变更事件
										var ss = fillmaps.smallsorts[$(
												"#smallsort").attr(
												"selectedIndex")];
										if (ss) {
											var m = $("#money6").val();
											if (m) {
												$("#money1").val(
														ss.price * m / 100);
											}
										}
									});
							// ----------------计算结束-----------------

							$("#btnNew").click(function() { // 重置/新建
								clearUserpart();
							});
							$("#btnEdit")
									.click(
											function() { // 编辑
												if (!selectedUserpartRowId) {
													toastr
															.warning("请选择要修改的消费信息");
													return;
												}
												var kd = $(
														"#"
																+ selectedUserpartRowId,
														$("#myTable")).data(
														"rawData");
												/*if (kd.flag2 || !guazhang_flag) {
													toastr.warning("已结账信息不能修改！");
												} else {*/
												if ("${organId}" != kd.organId) {
													toastr
															.warning("非本公司会员信息不能修改！");
												} else {
													clearUserpart();
                                                    flag1 = false;
													$("#userpart").fillform(kd);
													images = kd.images;
													for (var i = 0; i < images.length; i++) {
														$("#img" + i)
																.attr(
																		"src",
																		_ossImageHost
																				+ images[i]);
													}
													$("#membersort").disable();
													editUser = true;
												}
                                                flag1 = true;
                                                //alert("这里");
											});
							$("#btnWaimai")
									.click(
											function() { // 外卖
												if (!selectedKaidanRowId) {
													toastr.warning("请选择开单号");
													return;
												}
												var kd = $(
														"#"
																+ selectedKaidanRowId,
														$("#kaidanTable"))
														.data("rawData");

												var idx = layer
														.open({
															type : 2,
															area : [ '400px',
																	'330px' ],
															fix : false, //不固定
															title : "外卖品销售管理",
															maxmin : false,
															content : '${ctxPath}/waimai/toQuery.do?kaidanId='
																	+ kd._id
														});
												layer.full(idx);
											});

							$("#btnXufei")
									.click(
											function() { // 续费
												if (!selectedUserpartRowId) {
													toastr
															.warning("请选择要续费的会员卡信息");
													return;
												}
												var kd = $(
														"#"
																+ selectedUserpartRowId,
														$("#myTable")).data(
														"rawData");
												if (!kd.incardId) {
													toastr.warning("非会员卡不能续费！");
													return;
												} else if (!kd.flag2
														|| kd.guazhang_flag) {
													toastr
															.warning("未结账信息不能续费！");
													return;
												} else if (kd.money_qian > 0) {
													toastr
															.warning("此会员是欠费会员，请先补缴欠费！");
													return;
												}
												var ms = fillmaps.usersorts;
												for (var j = 0; j < ms.length; j++) {
													if (ms[j]._id == kd.membersort) {
														if (ms[j].flag1 == "1001") {
															toastr
																	.warning("单纯打折卡不能续费！");
															return;
														}
													}
												}
												var idx = parent.layer
														.open({
															type : 2,
															area : [ '400px',
																	'330px' ],
															fix : false, //不固定
															title : "会员卡续费",
															maxmin : false,
															content : '${ctxPath}/user/userIncard/toXufei.do?id='
																	+ kd.incardId
														});
												parent.layer.full(idx);
											});
							$("#codeDiv").hide();
							$("#codeOrganDiv").hide();
							$("#changeDiv").hide();
							$("#btnBuka")
									.click(
											function() { // 补卡/密码
												if (!selectedUserpartRowId) {
													toastr
															.warning("请选择要操作的会员卡信息");
													return;
												}
												var kd = $(
														"#"
																+ selectedUserpartRowId,
														$("#myTable")).data(
														"rawData");
												if (!kd.incardId) {
													toastr.warning("非会员卡不能操作！");
													return;
												} else if (!kd.flag2
														|| kd.guazhang_flag) {
													toastr
															.warning("未结账信息不能操作！");
													return;
												}
												if ("${organId}" !== kd.organId) {
													toastr
															.warning("非本公司办卡不能操作！");
													return;
												}
												$("#changeDiv").clearform();
												$("#act1")[0].checked = true;
												$("#changeDiv").show();
												$("#changeDiv #id_2").val("");
												$("#changeDiv #cardType2")[0].checked = true;
												layer
														.open({
															type : 1,
															title : "会员卡补卡/密码修改",
															area : [ '530px',
																	'350px' ],
															btn : [ '确定', '取消' ],
															yes : function(
																	index,
																	layero) {
																var t = $(
																		"input[name='act']:checked")
																		.val();
																if (t == 1) { // 补卡
																	var id_2 = $(
																			"#changeDiv #id_2")
																			.val();
																	if (!id_2
																			|| id_2.length < 4) {
																		layer
																				.alert("请输入正确的卡号");
																		$(
																				"#changeDiv #id_2")
																				.focus();
																		return;
																	}
																	$.shade
																			.show();
																	$
																			.post(
																					"${ctxPath}/user/userIncard/changeId_2.do",
																					{
																						incardId : kd.incardId,
																						id_2 : id_2
																					},
																					function(
																							data,
																							status) {
																						if (data.success) {
																							toastr
																									.success("会员卡号修改成功");
																							$(
																									"#queryFrm")
																									.submit();
																							layer
																									.closeAll();
																						} else {
																							layer
																									.alert(data.message);
																						}
																						$.shade
																								.hide();
																					});
																} else if (t == 2) { // 修改密码
																	$.shade
																			.show();
																	$
																			.post(
																					"${ctxPath}/user/userIncard/verifycode.do",
																					{
																						incardId : kd.incardId
																					},
																					function(
																							data,
																							status) {
																						if (data.success) {
																							$(
																									"#codeDiv")
																									.show();
																							$(
																									"#codeDiv #code")
																									.val(
																											"");
																							$(
																									"#codeDiv #phone")
																									.text(
																											data.data);
																							layer
																									.open({
																										type : 1,
																										title : "修改会员卡密码",
																										area : [
																												'400px',
																												'280px' ],
																										btn : [
																												'确定',
																												'取消' ],
																										yes : function(
																												index,
																												layero) {
																											var code = $(
																													"#codeDiv #code")
																													.val();
																											if (!code) {
																												toastr
																														.warning("请输入短信验证码");
																												$(
																														"#codeDiv #code")
																														.focus();
																												return;
																											}
																											var passwd = $(
																													"#codeDiv #passwd")
																													.val();
																											if (!passwd
																													|| passwd.length != 6) {
																												toastr
																														.warning("请输入6位会员卡密码");
																												$(
																														"#codeDiv #passwd")
																														.focus();
																												return;
																											}
																											$.shade
																													.show();
																											$
																													.post(
																															"${ctxPath}/user/userIncard/changePasswd.do",
																															{
																																incardId : kd.incardId,
																																passwd : passwd,
																																code : code
																															},
																															function(
																																	data,
																																	status) {
																																if (data.success) {
																																	toastr
																																			.success("修改会员卡密码成功");
																																	layer
																																			.closeAll();
																																} else {
																																	layer
																																			.alert(data.message);
																																}
																																$.shade
																																		.hide();
																															});
																										},
																										cancel : function(
																												index) {
																											layer
																													.close(index);
																										},
																										content : $("#codeDiv")
																									});
																						} else {
																							layer
																									.alert(data.message);
																						}
																						$.shade
																								.hide();
																					});
																}
															},
															cancel : function(
																	index) {
																layer
																		.closeAll();
															},
															content : $("#changeDiv")
														});
											});
							$("#btnZhuanka")
									.click(
											function() { // 转卡/欠费
												if (!selectedUserpartRowId) {
													toastr
															.warning("请选择要操作的会员卡信息");
													return;
												}
												var kd = $(
														"#"
																+ selectedUserpartRowId,
														$("#myTable")).data(
														"rawData");
												if (!kd.incardId) {
													toastr.warning("非会员卡不能操作！");
													return;
												} else if (!kd.flag2
														|| kd.guazhang_flag) {
													toastr
															.warning("未结账信息不能操作！");
													return;
												}

												if ("${organId}" !== kd.organId) {
													toastr
															.warning("非本公司办卡不能操作！");
													return;
												}

												var idx = layer
														.open({
															type : 2,
															area : [ '500px',
																	'400px' ],
															fix : false, //不固定
															title : "更改卡类型和补缴欠款",
															maxmin : false,
															content : '${ctxPath}/user/userIncard/toQianfei.do?id='
																	+ kd._id
														});
												layer.full(idx);
											});
							$("#btnPrint")
									.click(
											function() { // 打印小票
												var ids = $("#myTable").jqGrid(
														'getDataIDs');
												var ds = [];
												for (var i = 0; i < ids.length; i++) {
													var up = $("#" + ids[i],
															$("#myTable"))
															.data("rawData");
													ds.push(up._id);
												}
												if (ds.length == 0 && printds) {
													ds = printds;
												}

												var wms = [];// 外卖
												if (waimai) {
													for (var i = 0; i < waimai.length; i++) {
														wms.push(waimai[i]._id);
													}
												}

												if ((ds && ds.length > 0)
														|| (wms && wms.length > 0)) {
													$("#prt")[0].src = '${ctxPath }/user/userpart/print.do?type=0&ids='
															+ ds.join(',')
															+ '&wmids='
															+ wms.join(',');
												} else {
													toastr
															.warning("没有可打印的消费记录");
												}

											});
							$("#btnZika")
									.click(
											function() { // 创建子卡
												if (!selectedUserpartRowId) {
													toastr
															.warning("请选择要创建子卡的会员卡信息");
													return;
												}
												var kd = $(
														"#"
																+ selectedUserpartRowId,
														$("#myTable")).data(
														"rawData");
												if (!kd.incardId) {
													toastr
															.warning("非会员卡不能创建子卡！");
													return;
												} else if (!kd.flag2
														|| kd.guazhang_flag) {
													toastr
															.warning("未结账信息不能创建子卡！");
													return;
												}
												if ("${organId}" !== kd.organId) {
													toastr
															.warning("非本公司办卡不能操作！");
													return;
												}

												var ms = fillmaps.usersorts, flag1;
												for (var i = 0; i < ms.length; i++) {
													if (ms[i]._id == kd.membersort) {
														flag1 = ms[i].flag1;
														break;
													}
												}
												if (!flag1) {
													toastr.warning("会员卡类型未知！");
													return;
												}
												if (flag1 != '1002') {
													toastr
															.warning("非存钱会员不能办理子卡！");
													return;
												}
												var idx = parent.layer
														.open({
															type : 2,
															area : [ '400px',
																	'330px' ],
															fix : false, //不固定
															title : "子卡管理",
															maxmin : false,
															content : '${ctxPath}/user/userInincard/toUpsert.do?id='
																	+ kd.incardId
														});
												parent.layer.full(idx);
											});
							$("#tixianDiv").hide();
							$("#btnTixian")
									.click(
											function() { // 提现
												if (!selectedUserpartRowId) {
													toastr
															.warning("请选择要提现的会员卡信息");
													return;
												}
												var kd = $(
														"#"
																+ selectedUserpartRowId,
														$("#myTable")).data(
														"rawData");
												if (!kd.incardId) {
													toastr.warning("非会员卡不能提现！");
													return;
												} else if (!kd.flag2
														|| kd.guazhang_flag) {
													toastr
															.warning("未结账信息不能提现！");
													return;
												} else if (!(kd.nowMoney4 > 0)) {
													toastr.warning("余额不足不能提现！");
													return;
												}

												if ("${organId}" !== kd.organId) {
													toastr
															.warning("非本公司办卡不能操作！");
													return;
												}

												$("#tixianDiv").show();
												$("#tixianDiv").clearform();
												$("#tixianDiv").fillform(kd);
												$("#tixianDiv #money4").val(
														kd.nowMoney4);

												layer
														.open({
															type : 1,
															title : "提取现金",
															area : [ '600px',
																	'250px' ],
															btn : [ '提取现金',
																	"取消",
																	'打印小票' ],
															yes : function(
																	index,
																	layero) {
																var ti = parseFloat($(
																		"#tixian")
																		.val());
																if (!(ti > 0)) {
																	toastr
																			.warning("请输入提现金额");
																	$("#tixian")
																			.focus();
																	return;
																} else if (ti > kd.nowMoney4) {
																	toastr
																			.warning("提现金额不能大于余额");
																	$("#tixian")
																			.focus();
																	return;
																}

																$.shade.show();
																$
																		.post(
																				"${ctxPath}/user/userIncard/tixian.do",
																				{
																					incardId : kd.incardId,
																					tixian : ti
																				},
																				function(
																						data,
																						status) {
																					if (data.success) {
																						queryFrm();
																						toastr
																								.success("提现操作成功");
																						layer
																								.closeAll();
																					} else {
																						layer
																								.alert(data.message);
																					}
																					$.shade
																							.hide();
																				});
															},
															btn2 : function(
																	index,
																	layero) {
																//取消
															},
															btn3 : function(
																	index,
																	layero) {
																//打印小票

															},
															content : $("#tixianDiv")
														});
											});
							$("#changeDiv input:radio[name='cardType']")
									.change(
											function() {
												var v = $(
														'#changeDiv input[name="cardType"]:checked ')
														.val();
												$("#changeDiv #id_2").enable();
												if ("5" === v) { // 电子卡
													$
															.post(
																	"${ctxPath}/user/userIncard/getElecCardNum.do",
																	{},
																	function(
																			data,
																			status) {
																		$(
																				"#changeDiv #id_2")
																				.val(
																						data);
																		$(
																				"#changeDiv #id_2")
																				.disable();

																	});
												} else {
													$("#changeDiv #id_2").val(
															"");
												}

											});

							$("#cardDiv input:radio[name='cardType']")
									.change(
											function() {
												var v = $(
														'#cardDiv input[name="cardType"]:checked ')
														.val();
												$("#cardDiv #id_2").enable();
												if ("5" === v) { // 电子卡
													$
															.post(
																	"${ctxPath}/user/userIncard/getElecCardNum.do",
																	{},
																	function(
																			data,
																			status) {
																		$(
																				"#cardDiv #id_2")
																				.val(
																						data);
																		$(
																				"#cardDiv #id_2")
																				.disable();

																	});
												} else {
													$("#cardDiv #id_2").val("");
												}

											});
							$(".huiyuan").hide();
							$(".cishur").hide();
							$("#myTable")
									.grid(
											{
												datatype : "local",
												colNames : [ "操作", "挂账",
														"卡表面号", "会员号", "会员姓名",
														"卡余额", "欠费", "总次数",
														"剩余次数", "单次款额", "赠送余额",
														"会员类型", "服务项目", "性别",
														"有效日期", "员工1", "员工2",
														"员工3", "员工1提成",
														"员工2提成", "员工3提成", "生日",
														"光临时间", "应交款额", "实交款额",
														"银行卡付款", "代币券", "其他付款",
														"找零", "是否美容会员", "是否交款",
														"爱好", "电话", "居住地",
														"折扣%", "现金", "业绩1",
														"业绩2", "业绩3", "办卡公司" ],
												shrinkToFit : false,
												onSelectRow : function(id) {
													selectedUserpartRowId = id;
                                                    $('#btnEdit').click();
												},
												ondblClickRow : function(id) {
													var up = $("#" + id,
															$("#myTable"))
															.data("rawData");
													if (up.incardId) { // 双击进入消卡消费界面
														if (!up.flag2
																|| up.guazhang_flag) {
															toastr
																	.warning("未结账会员卡不能消费!");
														} else if (up.delete_flag) {
															toastr
																	.warning("已删除会员卡不能消费!");
														} else {
															if (!up.law_day
																	|| up.law_day >= new Date()
																			.format()) {
																var idx = parent.layer
																		.open({
																			type : 2,
																			area : [
																					'400px',
																					'330px' ],
																			fix : false, //不固定
																			title : "会员卡消费",
																			maxmin : false,
																			content : '${ctxPath}/user/userIncard/toJiezhang.do?id='
																					+ up.incardId
																		});
																parent.layer
																		.full(idx);
															} else {
																toastr
																		.warning("会员卡已过有效期，请续费后使用!");
															}
														}
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
																if (!rowObject.flag2) { // 未结账，可删除消费记录
																	var v = "<a href='javascript:void(0);' onClick='delRow(\""
																			+ options.rowId
																			+ "\",0)'>删除</a>&nbsp;&nbsp;";
																	return v;
																} else if (rowObject.incardId) { // 已结账会员卡，可删除会员卡
																	var v = "<a href='javascript:void(0);' onClick='delRow(\""
																			+ options.rowId
																			+ "\",1)'>删除</a>&nbsp;&nbsp;";
																	return v;
																} else {
																	return "";
																}
															}
														},
														{
															name : "guazhang_flag",
															index : "guazhang_flag",
															sortable : false,
															width : 50,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue)
																	return "是";
																else
																	return "否";
															}
														},
														{
															name : "cardno",
															sortable : false,
															index : "cardno",
															width : 90
														},
														{
															name : "id_2",
															sortable : false,
															index : "id_2",
															width : 90
														},
														{
															name : "name",
															index : "name",
															sortable : false,
															width : 80
														},
														{
															name : "nowMoney4",
															sortable : false,
															index : "nowMoney4",
															width : 70,
															formatter : function(
																	cellvalue) {
																if (cellvalue < 0) {
																	return "<font color=\"red\">"
																			+ cellvalue
																			+ "</font>";
																} else {
																	return cellvalue;
																}
															}
														},
														{
															name : "money_qian",
															sortable : false,
															index : "money_qian",
															width : 70,
															formatter : function(
																	cellvalue) {
																if (cellvalue > 0) {
																	return "<font color=\"red\">"
																			+ cellvalue
																			+ "</font>";
																} else {
																	return cellvalue;
																}
															}
														},
														{
															name : "allcishu",
															sortable : false,
															index : "allcishu",
															width : 70
														},
														{
															name : "nowShengcishu",
															sortable : false,
															index : "nowShengcishu",
															width : 80
														},
														{
															name : "danci_money",
															sortable : false,
															index : "danci_money",
															width : 80
														},
														{
															name : "nowSong_money",
															sortable : false,
															index : "nowSong_money",
															width : 80
														},
														{
															name : "usersortName",
															sortable : false,
															index : "usersortName"
														},
														{
															name : "smallsortName",
															sortable : false,
															index : "smallsortName",
															width : 120
														},
														{
															name : "sex",
															sortable : false,
															index : "sex",
															width : 40
														},
														{
															name : "law_day",
															sortable : false,
															index : "law_day",
															width : 100,
															formatter : function(
																	cellvalue) {
																if (cellvalue) {
																	return cellvalue
																			.substring(
																					0,
																					10);
																} else {
																	return "";
																}
															}
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
															name : "somemoney1",
															sortable : false,
															index : "somemoney1"
														},
														{
															name : "somemoney2",
															sortable : false,
															index : "somemoney2"
														},
														{
															name : "somemoney3",
															sortable : false,
															index : "somemoney3"
														},
														{
															name : "birthday",
															sortable : false,
															index : "birthday",
															width : 100,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue) {
																	return cellvalue
																			.substring(
																					0,
																					10);
																} else {
																	return "";
																}
															}
														},
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
															name : "money2",
															sortable : false,
															index : "money2"
														},
														{
															name : "money_yinhang_money",
															sortable : false,
															index : "money_yinhang_money"
														},
														{
															name : "money_li_money",
															sortable : false,
															index : "money_li_money"
														},
														{
															name : "money_other_money",
															sortable : false,
															index : "money_other_money"
														},
														{
															name : "money3",
															sortable : false,
															index : "money3"
														},
														{
															name : "flag1",
															sortable : false,
															index : "flag1",
															formatter : function(
																	cellvalue) {
																if (cellvalue)
																	return "是";
																else
																	return "否";
															}
														},
														{
															name : "flag2",
															sortable : false,
															index : "flag2",
															formatter : function(
																	cellvalue) {
																if (cellvalue)
																	return "是";
																else
																	return "否";
															}
														},
														{
															name : "love",
															sortable : false,
															index : "love"
														},
														{
															name : "phone",
															sortable : false,
															index : "phone"
														},
														{
															name : "place",
															sortable : false,
															index : "place"
														},
														{
															name : "money6",
															sortable : false,
															index : "money6"
														},
														{
															name : "money_cash",
															sortable : false,
															index : "money_cash"
														},
														{
															name : "yeji1",
															sortable : false,
															index : "yeji1"
														},
														{
															name : "yeji2",
															sortable : false,
															index : "yeji2"
														},
														{
															name : "yeji3",
															sortable : false,
															index : "yeji3"
														},
														{
															name : "organName",
															sortable : false,
															index : "organName",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if ("${organId}" === rowObject.organId)
																	return "本公司";
																else
																	return cellvalue;
															}
														}

												]
											});


							// ---------------会员关联文档
							$("#takeDoc")
									.click(
											function() {
												var ms = fillmaps.usersorts[$(
														"#membersort").attr(
														"selectedIndex")];
												if (!ms || ms.flag1 === '1000') {
													toastr.warning("会员才可以关联文档");
													return;
												}

												var doc = $("#doc").val();
												if (doc !== '') {
													layer
															.confirm(
																	'请选择会员档案操作?',
																	{
																		btn : [
																				"下载",
																				"重新上传",
																				"取消" ]
																	},
																	function(
																			index) {
																		layer
																				.close(index);
																		document.location.href = _ossImageHost
																				+ doc
																						.split('|')[0];
																	},
																	function(
																			index) {
																		layer
																				.close(index);
																		selectFile();
																	},
																	function(
																			index) {
																		layer
																				.close(index);
																	});
												} else {
													selectFile();
												}

											});

							// ---------------会员照片按钮
							$("#imagesDiv").hide();
							$("#takePic").click(
									function() {
										var ms = fillmaps.usersorts[$(
												"#membersort").attr(
												"selectedIndex")];
										if (!ms || ms.flag1 === '1000') {
											toastr.warning("会员才可以上传照片");
											return;
										}
										layer.open({
											type : 1,
											title : "会员照片",
											area : [ '740px', '520px' ],
											btn : [ '关闭' ],
											content : $("#imagesDiv")
										});
									});

							// ---------------选择产品按钮
							$("#takeChanpin")
									.click(
											function() {
												var ms = fillmaps.usersorts[$(
														"#membersort").attr(
														"selectedIndex")];
												if (!ms || ms.flag1 != '1000') {
													toastr
															.warning("非会员消费才可以选择产品");
													return;
												}
												var userpartId = $(
														"#userpartId").val();
												var idx = layer
														.open({
															type : 2,
															area : [ '400px',
																	'330px' ],
															fix : false, //不固定
															title : "选择产品",
															maxmin : false,
															content : '${ctxPath}/usewupin/toQuery.do?userpartId='
																	+ userpartId
														});
												layer.full(idx);
											});
						});
		function queryFrm() {
			var obj = $("#queryFrm").formobj();
			$.shade.show();
			$.post("${ctxPath}/user/userpart/query2.do", obj, function(data,
					status) {
				$.shade.hide();
				$("#kaidanTable").jqGrid('resetSelection');
				selectedKaidanRowId = null;
				selectedUserpartRowId = null;
				$("#myTable").jqGrid('clearGridData');
				for (var i = 0; i <= data.length; i++) {
					$("#myTable").jqGrid('addRowData', i + 1, data[i]);
				}
				$("#myTable").jqGrid("setSelection",1);
				selectedUserpartRowId = 1;
				var kd = $(
						"#"
						+ selectedUserpartRowId,
						$("#myTable")).data(
						"rawData");
				/*if (kd.flag2 || !guazhang_flag) {
				 toastr.warning("已结账信息不能修改！");
				 } else {*/
				if ("${organId}" != kd.organId) {
					toastr
							.warning("非本公司会员信息不能修改！");
				} else {
					clearUserpart();
					$("#userpart").fillform(kd);
					images = kd.images;
					for (var i = 0; i < images.length; i++) {
						$("#img" + i)
								.attr(
										"src",
										_ossImageHost
										+ images[i]);
					}
					$("#membersort").disable();
					editUser = true;
				}
			});
		}
		function clearUserpart() {
			editUser = false;
			$("#userpart").enable();
			$("#userpart").clearform({
				clearHidden : true
			});
			$("#flag2").val(false);
			$("#delete_flag").val(false);
			$("#guazhang_flag").val(true);
			$("#money6").val(100); // 默认折扣100%
			$("#money1").val(0); // 默认应交款额0
			$("#somemoney1").val(0);
			$("#yeji1").val(0);
			$("#somemoney2").val(0);
			$("#yeji2").val(0);
			$("#somemoney3").val(0);
			$("#yeji3").val(0);
			var ms = fillmaps.usersorts;
			for (var i = 0; i < ms.length; i++) {
				if (ms[i].flag_putong) {
					$("#membersort").fillform({
						membersort : ms[i]._id
					});
					break;
				}
			}
			$("#membersort").trigger('change');
			images = [];
			$("#images").val("");
			$(".img").attr("src", "");

			$("#cardForm #passwd").val("");
			$("#cardForm #passwd2").val("");
		}
		function queryUserpartList(f) {
			if (!selectedKaidanRowId) {
				if (!f)
					toastr.warning("请选择开单号");
				return;
			}
			var kd = $("#" + selectedKaidanRowId, $("#kaidanTable")).data(
					"rawData");
			$.post("${ctxPath}/user/userpart/query.do", {
				kaidanId : kd._id
			}, function(data, status) {
				selectedUserpartRowId = null;
				$("#myTable").jqGrid('clearGridData');
				for (var i = 0; i <= data.length; i++) {
					$("#myTable").jqGrid('addRowData', i + 1, data[i]);
				}
			});
		}

		function delRow(idx, tp) {
			var kd = $("#" + idx, $("#myTable")).data("rawData");
			if (tp == 0) {
				// 消费记录删除
				layer.confirm('确定删除消费记录?', function(index) {
					layer.close(index);
					$.shade.show();
					$.post("${ctxPath}/user/userpart/remove.do", {
						id : kd._id
					}, function(data, status) {
						if (data.success) {
							queryUserpartList(true);
						} else {
							layer.alert(data.message);
						}
						$.shade.hide();
					});

				});
			} else if (tp == 1) {
				// 会员卡删除
				layer.confirm('确定删除会员卡信息?', function(index) {
					layer.close(index);
					removeIncard(kd);
				});
			}
		}

		function removeIncard(kd) {
			$.shade.show();
			$.post("${ctxPath}/user/userIncard/verifycodeOrgan.do", {
				incardId : kd.incardId
			}, function(data, status) {
				if (data.success) {
					$("#codeOrganDiv").show();
					$("#codeOrganDiv #code").val("");
					$("#codeOrganDiv #phone").text(data.data);
					layer.open({
						type : 1,
						title : "短信验证码",
						area : [ '400px', '240px' ],
						btn : [ '确定', '取消' ],
						yes : function(index, layero) {
							var code = $("#codeOrganDiv #code").val();
							if (!code) {
								toastr.warning("请输入短信验证码");
								$("#codeOrganDiv #code").focus();
								return;
							}
							$.shade.show();
							$.post("${ctxPath}/user/userIncard/remove.do", {
								incardId : kd.incardId,
								code : code
							}, function(data, status) {
								if (data.success) {
									toastr.success("删除会员卡成功");
									layer.closeAll();
									queryFrm(); // 重新查询
								} else {
									layer.alert(data.message);
								}
								$.shade.hide();
							});
						},
						cancel : function(index) {
							layer.close(index);
						},
						content : $("#codeOrganDiv")
					});
				} else {
					layer.alert(data.message);
				}
				$.shade.hide();
			});
		}
		function helloDate(d) {
			//alert(d);
		}

		var fileAction, doc, fileName;
		function selectFile() {
			fileAction = "doc";
			$('#onlyFile').click();
		}

		function selectImg() {
			if (images.length >= 8) {
				toastr.warning("");
				return;
			}
			fileAction = "img";
			$('#onlyFile').click();
		}
		function changeFile(_this) {
			$.shade.show();
			$("#fileForm").ajaxSubmit({
				type : 'post',
				headers : {
					'type' : $(_this).attr("rel"),
					'isPublic' : 'true'
				},
				url : URL.fileupload,
				success : function(data) {
					$.shade.hide();
					if (fileAction === 'img') {
						var imgpath = _ossImageHost + data.data[0];
						$("#img" + images.length).attr("src", imgpath);

						images.push(data.data[0]);

						$("#onlyFile").val("");
						$("#images").val(images.join(','));
						toastr.success("图片上传成功");
					} else if (fileAction === 'doc') {
						fileName = getFileName(_this);
						var fileId = data.data[0];
						var f = fileId + "|" + fileName;
						doc = f;

						$("#doc").val(doc);

						$("#onlyFile").val("");
						toastr.success("上传成功");
					}
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					$.shade.hide();
					toastr.success("上传失败");
				}
			});

		}

		function getFileName(obj) {
			var val = obj.value;
			var idx = val.lastIndexOf("/");
			if (idx == -1)
				idx = val.lastIndexOf("\\");
			return val.substring(idx + 1);
		}
	</script>

	<!-- 结账div -->
	<div id="jiezhangDiv" class="wrapper wrapper-content"
		style="width: 90%; height: 90%">
		<div class="row" style="margin-top: 20px">
			<form id="jiezhangForm" method="post" class="form-horizontal"
				onSubmit="return false;">
				<input id="kaidanId" name="kaidanId" type="hidden"> <input
					id="userpartIds" name="userpartIds" type="hidden">
				<div class="form-group">
					<label class="col-sm-4 control-label">应交款额</label>
					<div class="col-sm-7">
						<input id="money1" name="money1" type="text" class="form-control"
							disabled>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">银 行 卡</label>
					<div class="col-sm-7">
						<input id="money_yinhang_money" name="money_yinhang_money"
							type="number" min="0" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">代 金 券</label>
					<div class="col-sm-7">
						<input id="money_li_money" name="money_li_money"
							class="form-control" type="number" min="0">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">代金券号</label>
					<div class="col-sm-7">
						<input id="money_lijuan" name="money_lijuan" type="text"
							class="form-control">
					</div>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="form-group">
					<label class="col-sm-4 control-label">现 金</label>
					<div class="col-sm-7">
						<input id="money_cash" name="money_cash" type="number" min="0"
							class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">应收现金</label>
					<div class="col-sm-7">
						<input id="yingshou_cash" name="yingshou_cash" type="text"
							class="form-control" disabled>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">找 零</label>
					<div class="col-sm-7">
						<input id="money3" name="money3" type="text" class="form-control"
							disabled>
					</div>
				</div>
				<input type="submit" value="提交" style="display: none">
			</form>
		</div>
	</div>

	<!-- 办卡div -->
	<div id="cardDiv" class="wrapper wrapper-content"
		style="width: 90%; height: 90%">
		<form id="cardForm" method="post" class="form-horizontal"
			onSubmit="return false;">
			<div class="row" style="margin-top: 20px">

				<div class="col-sm-4 well">
					<div class="col-sm-12">
						<label class="control-label"><input name="cardType"
							type="radio" value="1">&nbsp;磁卡</label>
					</div>
					<div class="col-sm-12">
						<label class="control-label"><input id="cardType2"
							name="cardType" type="radio" value="2" checked>&nbsp;ID卡</label>
					</div>
					<div class="col-sm-12">
						<label class="control-label"><input name="cardType"
							type="radio" value="3">&nbsp;IC卡</label>
					</div>
					<div class="col-sm-12">
						<label class="control-label"><input name="cardType"
							type="radio" value="4">&nbsp;非接触IC卡</label>
					</div>
					<div class="col-sm-12">
						<label class="control-label"><input name="cardType"
							type="radio" value="5">&nbsp;电子卡</label>
					</div>
				</div>
				<div class="col-sm-8 form-group">
					<div class="form-group">
						<label class="col-sm-5 control-label">请划卡:</label>
						<div class="col-sm-7">
							<input id="id_2" name="id_2" type="text" class="form-control"
								required minlength="4" cardno="true">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-5 control-label">输入密码:</label>
						<div class="col-sm-7">
							<input id="passwd" name="passwd" type="password"
								class="form-control" minlength="6" maxlength="6" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-5 control-label">再次输入密码:</label>
						<div class="col-sm-7">
							<input id="passwd2" name="passwd2" type="password" minlength="6"
								maxlength="6" class="form-control" required>
						</div>
					</div>
				</div>
			</div>
			<input type="submit" value="提交" style="display: none">
		</form>
	</div>

	<!-- 并单div -->
	<div id="unionDiv" class="wrapper wrapper-content"
		style="width: 90%; height: 90%">
		<form id="unionForm" method="post" class="form-horizon
			onSubmit="returnfalse;">
			<div class="row" style="margin-left: 5px; margin-top: 15px">
				<label class="col-sm-12">请选择要合并的单号:</label>
				<div class="col-sm-12">
					<select class="form-control form-control2" style="width: 100%"
						id="kaidanList" name="kaidanList" size="10" multiple>
					</select> <span class="help-block m-b-none">按住Ctrl键可进行多选；要取消选择，可按住Ctrl键，点击已选中项即可。</span>
				</div>
			</div>
		</form>
	</div>

	<!-- 修改卡号/卡密码div -->
	<div id="changeDiv" class="wrapper wrapper-content"
		style="width: 90%; height: 90%">
		<div class="row" style="margin-top: 20px">
			<form id="changeForm" method="post" class="form-horizontal"
				onSubmit="return false;">
				<div class="col-sm-12 well">
					<div class="col-sm-2">
						<label class="control-label"><input name="cardType"
							type="radio" value="1">&nbsp;磁卡</label>
					</div>
					<div class="col-sm-2">
						<label class="control-label"><input id="cardType2"
							name="cardType" type="radio" value="2" checked>&nbsp;ID卡</label>
					</div>
					<div class="col-sm-2">
						<label class="control-label"><input name="cardType"
							type="radio" value="3">&nbsp;IC卡</label>
					</div>
					<div class="col-sm-3">
						<label class="control-label"><input name="cardType"
							type="radio" value="4">&nbsp;非接触IC卡</label>
					</div>
					<div class="col-sm-3">
						<label class="control-label"><input name="cardType"
							type="radio" value="5">&nbsp;电子卡</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-3 col-sm-offset-1">
						<label class="control-label"><input id="act1" name="act"
							type="radio" value="1" checked>&nbsp;补 卡:</label>
					</div>
					<label class="col-sm-3 control-label">请划卡:</label>
					<div class="col-sm-4">
						<input id="id_2" name="id_2" type="text" class="form-control"
							required minlength="4" cardno="true">
					</div>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="form-group">
					<div class="col-sm-11 col-sm-offset-1">
						<label class="control-label"> <input id="act2" name="act"
							type="radio" value="2">&nbsp;重新设置密码:
						</label>
					</div>
				</div>
				<input type="submit" value="提交" style="display: none">
			</form>
		</div>
	</div>

	<!-- 验证码修改卡密码div -->
	<div id="codeDiv" class="wrapper wrapper-content"
		style="width: 90%; height: 95%">
		<div class="row" style="margin-top: 20px">
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
				<div class="form-group">
					<label class="col-sm-4 control-label">卡密码</label>
					<div class="col-sm-7">
						<input id="passwd" name="passwd" type="password"
							class="form-control">
					</div>
				</div>
				<input type="submit" value="提交" style="display: none">
			</form>
		</div>
	</div>

	<!-- 店铺验证码div -->
	<div id="codeOrganDiv" class="wrapper wrapper-content"
		style="width: 90%; height: 95%">
		<div class="row" style="margin-top: 20px">
			<form id="codeOrganForm" method="post" class="form-horizontal"
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

	<!-- 提现div -->
	<div id="tixianDiv" class="wrapper wrapper-content"
		style="width: 90%; height: 95%">
		<div class="row" style="margin-top: 20px">
			<form id="tixianForm" method="post" class="form-horizontal"
				onSubmit="return false;">
				<div class="form-group">
					<label class="col-sm-2 control-label">会员类型：</label>
					<div class="col-sm-4">
						<select class="form-control" id="membersort" name="membersort"
							disabled>
							<option value="">请选择</option>
							<c:forEach items="${ffusersorts}" var="usersort">
								<option value="${usersort._id}">${usersort.name1}</option>
							</c:forEach>
						</select>
					</div>
					<label class="col-sm-2 control-label">会员姓名：</label>
					<div class="col-sm-4">
						<input id="name" name="name" type="text" class="form-control"
							disabled>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">余 额：</label>
					<div class="col-sm-4">
						<input id="money4" name="money4" type="text" class="form-control"
							disabled>
					</div>
					<label class="col-sm-2 control-label">提取金额：</label>
					<div class="col-sm-4">
						<input id="tixian" name="tixian" type="number"
							class="form-control" min="0">
					</div>
				</div>
				<input type="submit" value="提交" style="display: none">
			</form>
		</div>
	</div>

	<!-- 会员照片div -->
	<div id="imagesDiv" class="wrapper wrapper-content"
		style="width: 90%; height: 90%;">
		<div class="row" style="margin-top: 10px; margin-left: 10px">
			<div class="col-sm-4" style="padding: 5px">
				<input type="button" value="选择照片" onclick="selectImg()"
					class="btn btn-primary btn-block">
			</div>
		</div>
		<div class="row" style="margin-left: 10px">
			<div class="col-sm-12" style="padding: 5px">
				<img class="img" id="img0" src=""
					style="width: 160px; height: 160px;" /> <img class="img" id="img1"
					src="" style="width: 160px; height: 160px;" /> <img class="img"
					id="img2" src="" style="width: 160px; height: 160px;" /> <img
					class="img" id="img3" src="" style="width: 160px; height: 160px;" />
			</div>
		</div>
		<div class="row" style="margin-left: 10px">
			<div class="col-sm-12" style="padding: 5px">
				<img class="img" id="img4" src=""
					style="width: 160px; height: 160px;" /> <img class="img" id="img5"
					src="" style="width: 160px; height: 160px;" /> <img class="img"
					id="img6" src="" style="width: 160px; height: 160px;" /> <img
					class="img" id="img7" src="" style="width: 160px; height: 160px;" />
			</div>
		</div>
	</div>

	<div class="hidden">
		<form action="" id="fileForm">
			<input type="file" rel="msgImage" autocomplete="off" name="onlyFile"
				id="onlyFile" onchange="changeFile(this)" placeholder="File here" />
		</form>
	</div>
	<iframe id="prt" src="" width="180" height="400" style="display: none;"></iframe>
</body>
</html>
