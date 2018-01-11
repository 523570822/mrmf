<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />
<html>
<head>
<title></title>
<style type="text/css">
.form-group {
	margin-bottom: 5px;
}
</style>
</head>
<body class="gray-bg">
	<form id="myForm" method="post" class="form-horizontal">
		<div class="wrapper wrapper-content">
			<div class="row">
				<div class="col-sm-12">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<input type="hidden" id="organId" name="organId"
								value="${organId }"> <input type="hidden" id="kaidanId"
								name="kaidanId" value="${param.kaidanId }"> <input
								type="hidden" id="delete_flag" name="delete_flag" value="false">
							<input type="hidden" id="guazhang_flag" name="guazhang_flag"
								value="true">
							<div class="form-group">
								<div class="row">
									<label class="col-sm-1 control-label"><input
										id="dinggou" name="dinggou" type="checkbox">&nbsp;订购</label> <label
										class="col-sm-1 control-label">姓名</label>
									<div class="col-sm-2">
										<input type="text" class="form-control" id="name" name="name">
									</div>
									<label class="col-sm-1 control-label">电话</label>
									<div class="col-sm-2">
										<input id="phone" name="phone" type="text"
											class="form-control" maxlength="20">
									</div>
									<label class="col-sm-1 control-label">剩余款额</label>
									<div class="col-sm-2">
										<input id="money_qian" name="money_qian" type="number"
											class="form-control" min="0">
									</div>
									<label class="col-sm-2 control-label" style="text-align: left;">免单赠送&nbsp;<input
										id="miandan" name="miandan" type="checkbox"></label>
								</div>
								<div class="row" style="margin-top: 10px">
									<div class="col-sm-4 col-sm-offset-2">
										<button id="btnNew" class="btn btn-success" type="button">重置</button>
										<button id="btnSave" class="btn btn-info" type="submit">保存</button>
										<button id="btnBack" class="btn btn-outline btn-danger"
											type="button">返回</button>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-4 well">
									<div class="form-group">
										<label class="col-sm-4 control-label">物品</label>
										<div class="col-sm-8">
											<input type="text" class="form-control suggest" id="wupinId"
												name="wupinId"
												suggest="{data :fillmaps.leibies,searchFields : [ '_id' ],keyField : 'mingcheng',style2:true}">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">价钱</label>
										<div class="col-sm-8">
											<input id="money2" name="money2" type="number"
												class="form-control" min="0" maxlength="50">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">外卖折扣(%)</label>
										<div class="col-sm-8">
											<input id="zhekou" name="zhekou" type="number"
												class="form-control" min="0" max="100" value="100">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">售出人员:</label>
										<div class="col-sm-8">
											<input type="text" class="form-control form-control2 suggest"
												id="staffId1" name="staffId1"
												suggest="{data :fillmaps.staffs,style2:true}">
										</div>
									</div>
									<div class="form-group">
										<c:if test="${organSetting.displayTicheng}">
											<label class="col-sm-4 control-label">员工提成:</label>
											<div class="col-sm-8">
												<input id="ticheng1" name="ticheng1" type="number" min="0"
													class="form-control" disabled>
											</div>
										</c:if>
										<c:if test="${!organSetting.displayTicheng}">
											<input id="ticheng1" name="ticheng1" type="hidden">
										</c:if>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">员工业绩:</label>
										<div class="col-sm-8">
											<input id="yeji1" name="yeji1" type="number" min="0"
												class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">售出人员2:</label>
										<div class="col-sm-8">
											<input type="text" class="form-control form-control2 suggest"
												id="staffId2" name="staffId2"
												suggest="{data :fillmaps.staffs,style2:true}">
										</div>
									</div>
									<div class="form-group">
										<c:if test="${organSetting.displayTicheng}">
											<label class="col-sm-4 control-label">员工提成2:</label>
											<div class="col-sm-8">
												<input id="ticheng2" name="ticheng2" type="number" min="0"
													class="form-control" disabled>
											</div>
										</c:if>
										<c:if test="${!organSetting.displayTicheng}">
											<input id="ticheng2" name="ticheng2" type="hidden">
										</c:if>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">员工业绩2:</label>
										<div class="col-sm-8">
											<input id="yeji2" name="yeji2" type="number" min="0"
												class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">购买人</label>
										<div class="col-sm-8">
											<input id="buyname" name="buyname" type="text"
												class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">数量</label>
										<div class="col-sm-8">
											<input id="num" name="num" type="number" class="form-control"
												min="1" value="1">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">总价</label>
										<div class="col-sm-8">
											<input id="money1" name="money1" type="text"
												class="form-control" disabled>
										</div>
									</div>
								</div>
								<div class="col-sm-8">
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
	</form>
	<script type="text/javascript">
		$()
				.ready(
						function() {
							$("#btnBack")
									.click(
											function() { // 返回
												if (parent.takeChanpinLayerId) {
													parent.layer
															.close(parent.takeChanpinLayerId);
												} else {
													parent.layer.closeAll();
												}
											});
							$("#btnNew").click(function() { // 重置
								$("#myForm").clearform();
								//<c:if test="${!organSetting.displayTicheng}">
								$("#ticheng1").val("");
								$("#ticheng2").val("");
								//</c:if>
								if ("${param.zhekou}") {
									$("#zhekou").val("${param.zhekou}");
								} else {
									$("#zhekou").val(100);
								}
								$("#num").val(1);
							});

							$("#btnNew").click();

							$("#wupinId").change(function() {
								jisuan();
							});
							$("#num").change(function() {
								jisuan();
							});
							$("#zhekou").change(function() {
								jisuan();
							});
							$("#staffId1").change(function() {
								jisuan();
							});
							$("#staffId2").change(function() {
								jisuan();
							});
							$("#miandan").change(function() {
								jisuan();
							});

							function jisuan() {
								$("#yeji1").val("");
								$("#ticheng1").val("");
								$("#yeji2").val("");
								$("#ticheng2").val("");
								$("#money1").val("");
								$("#money2").val("");

								// 获取物品单价
								var s = fillmaps.leibies[$("#wupinId").attr(
										"selectedIndex")];
								if (s) {
									var p = s.price_xs;
									if (s.price_xss
											&& s.price_xss['${organId}']) {
										p = s.price_xss['${organId}'];
									}
									$("#money2").val(p);

									// 计算总价
									var zhekou = $("#zhekou").val();
									var num = $("#num").val();
									if (num && num !== "" && zhekou
											&& zhekou !== "") {
										var m = parseFloat(p) * parseFloat(num)
												* parseFloat(zhekou) / 100;
										m = m.toFixed(2);
										$("#money1").val(m);

										if (!$("#miandan").is(':checked')) {
											var ticheng = m * s.ticheng / 100;
											ticheng = ticheng.toFixed(2);

											// 计算员工提成
											var staff = $("#staffId1").val();
											if (staff) {
												$("#yeji1").val(m);
												$("#ticheng1").val(ticheng);
											}
											staff = $("#staffId2").val();
											if (staff) {
												$("#yeji2").val(m);
												$("#ticheng2").val(ticheng);
											}
										}
									}

								}
							}

							$("#myForm")
									.submit(
											function() { // 保存
												var v = $("#wupinId").val();
												if (v === "") {
													toastr.warning("请选择物品");
													$("#sg_wupinId").focus();
													return false;
												}
												v = $("#zhekou").val();
												if (v === "") {
													toastr.warning("请输入外卖折扣");
													$("#zhekou").focus();
													return false;
												}
												v = $("#staffId1").val();
												if (v === "") {
													toastr.warning("请选择服务人员");
													$("#sg_staffId1").focus();
													return false;
												}
												v = $("#num").val();
												if (v === "") {
													toastr.warning("请输入数量");
													$("#num").focus();
													return false;
												}
												if ($('#dinggou')
														.is(':checked')) { // 订购判断
													v = $("#name").val();
													if (v === "") {
														toastr
																.warning("订购时，姓名不能为空");
														$("#name").focus();
														return false;
													}
													v = $("#phone").val();
													if (v === "") {
														toastr
																.warning("订购时，电话不能为空");
														$("#phone").focus();
														return false;
													}
													v = $("#money_qian").val();
													if (v === "") {
														toastr
																.warning("订购时，剩余款额不能为空");
														$("#money_qian")
																.focus();
														return false;
													}
													var money1 = parseFloat($(
															"#money1").val());
													var qian = parseFloat(v);
													if (qian > money1) {
														toastr
																.warning("剩余款额不能不能大于总价");
														$("#money_qian")
																.focus();
														return false;
													}
												}

													$
															.post(
																	"${ctxPath}/waimai/upsert.do",
																	$("#myForm")
																			.formobj(),
																	function(
																			data) {
																		if (data) {
																			if (data.success) {
																				toastr
																						.success("操作成功");
																				reloadTable();
																				$(
																						"#btnNew")
																						.click();
																			} else {
																				toastr
																						.error(data.message);
																			}
																		} else {
																			toastr
																					.error("操作失败");
																		}
																	});
												return false;
											});
							$("#myTable")
									.grid(
											{
												pager : null,
												shrinkToFit : false,
												datatype : "local",
												colNames : [ "操作", "物品名称",
														"规格", "单价", "数量", "折扣",
														"总价", "欠费", "售出日期",
														"售出人员", "购买人", "售出人员2",
														"业绩1", "业绩2", "免单",
														"是否挂账" ],
												colModel : [
														{
															name : "_id",
															index : "_id",
															width : 60,
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='javascript:void(0);' onclick='removeWaimai(\""
																		+ cellvalue
																		+ "\")'>删除</a>";

																return v;
															}
														},
														{
															name : "wupinName",
															index : "wupinName"
														},
														{
															name : "guige",
															width : 80,
															index : "guige"
														},
														{
															name : "money2",
															width : 80,
															index : "money2"
														},
														{
															name : "num",
															width : 80,
															index : "num"
														},
														{
															name : "zhekou",
															width : 80,
															index : "zhekou"
														},
														{
															name : "money1",
															width : 80,
															index : "money1"
														},
														{
															name : "money_qian",
															width : 80,
															index : "money_qian"
														},
														{
															name : "createTime",
															index : "createTime"
														},
														{
															name : "staffId1",
															sortable : false,
															width : 100,
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
															name : "buyname",
															width : 100,
															index : "buyname"
														},
														{
															name : "staffId2",
															sortable : false,
															index : "staffId2",
															width : 100,
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
															name : "yeji1",
															width : 60,
															index : "yeji1"
														},
														{
															name : "yeji2",
															width : 60,
															index : "yeji2"
														},
														{
															name : "miandan",
															index : "miandan",
															width : 60,
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
															name : "guazhang_flag",
															index : "guazhang_flag",
															width : 70,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue)
																	return "是";
																else
																	return "否";
															}
														} ]
											});
							reloadTable();

						});
		function reloadTable() {
			$.post("${ctxPath}/waimai/query.do", {kaidanId : '${param.kaidanId}'
			}, function(data) {
				if (data) {
					$("#myTable").jqGrid('clearGridData');
					for (var i = 0; i < data.length; i++) {
						$("#myTable").jqGrid('addRowData', i + 1, data[i]);
					}
				} else {
					toastr.error("操作失败");
				}
			});
		}
		function removeWaimai(waimaiId) {
			layer.confirm('确定要删除外卖?', function(index) {
				layer.close(index);
				var url = _ctxPath + "/waimai/remove/" + waimaiId + ".do";
				$.post(url, {}, function(data) {
					if (data) {
						if (data.success) {
							toastr.success("操作成功");
							reloadTable();
						} else {
							toastr.error(data.message);
						}
					} else {
						toastr.error("操作失败");
					}
				});
			});
		}
	</script>
</body>
</html>
