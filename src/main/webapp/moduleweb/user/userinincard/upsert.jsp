<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style>
.form-control2 {
	width: 20%;
	margin-left: 100px;
	display: inline;
}

.center {
	text-align: center;
}

.leftPadding {
	padding-left: 140px;
}
</style>
</head>
<body class="gray-bg">
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row">
				<div class="col-sm-12">
					<form id="userpart" method="post" class="form-horizontal">
						<div id="incard" class="form-group">
							<input type="hidden" id="organId" name="organId"> <input
								type="hidden" id="userId" name="userId"> <input
								type="hidden" id="cardId" name="cardId"><input
								type="hidden" id="cardno" name="cardno"> <label
								class="col-sm-2 control-label">会员类型:</label>
							<div class="col-sm-2">
								<select class="form-control" id="membersort2" name="membersort2"
									disabled>
									<c:forEach items="${ffusersorts}" var="usersort">
										<option value="${usersort._id}">${usersort.name1}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-1 control-label zhekour">余 额:</label>
							<div class="col-sm-2 zhekour">
								<input id="money4" name="money4" type="number" min="0"
									class="form-control" disabled>
							</div>

							<label class="col-sm-1 control-label zhekour">折 扣:</label>
							<div class="col-sm-2 zhekour">
								<input id="money6" name="money6" value="100" type="number"
									min="0" max="100" class="form-control" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-3 center zhekour">
								<label class="control-label"><input id="miandan"
									name="miandan" type="checkbox">&nbsp;免单</label>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">卡中卡类型:</label>
							<div class="col-sm-2">
								<select class="form-control" id="membersort" name="membersort"
									required>
									<option value="">请选择</option>
									<c:forEach items="${ffusersorts}" var="usersort">
										<c:if test="${usersort.flag1 == '1003'}">
											<option value="${usersort._id}">${usersort.name1}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-1 control-label cishur">总次数:</label>
							<div class="col-sm-2 cishur">
								<input id="allcishu" name="allcishu" type="digits" min="0"
									class="form-control" value="0">
							</div>
							<label class="col-sm-1 control-label">办卡金额:</label>
							<div class="col-sm-2">
								<input id="money1" name="money1" type="number" min="0"
									class="form-control" value="0">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">明 细:</label>
							<div class="col-sm-2">
								<select class="form-control" id="smallsort" name="smallsort"
									required>
									<option value="">请选择</option>
									<c:forEach items="${ffsmallsorts}" var="smallsort">
										<option value="${smallsort._id}">${smallsort.name}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-1 control-label cishur">次数:</label>
							<div class="col-sm-2 cishur">
								<input id="shengcishu" name="shengcishu" type="digits" min="0"
									class="form-control" value="0">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-12 leftPadding">
								<button id="btnSave" class="btn btn-info" type="submit">确定</button>
								<button id="btnBack" class="btn btn-outline btn-danger"
									type="button">返回</button>

								<input id="danciTui" name="danciTui" type="number" min="0"
									class="form-control form-control2" placeholder="请输入单次返款金额">
								<button id="btnTuika" class="btn btn-outline btn-info"
									type="button">退卡返款</button>
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
		var selectedRowId, usersorts;
		$()
				.ready(
						function() {
							$("#membersort2").val(fillmaps.incard.membersort);
							//$("#money6").val(fillmaps.incard.zhekou);

							//alert(navigator.userAgent);
							if(navigator.userAgent.indexOf('MSIE 9.0') != -1||navigator.userAgent.indexOf('MSIE 8.0') != -1){
                                //改变val颜色样式
                                $("#danciTui").css("color","#999");
                                $("#danciTui").attr('value','请输入单次返款金额');
                                $("#danciTui").focus(function () {
                                    if($("#danciTui").attr('value')=='请输入单次返款金额'){
                                        $("#danciTui").css("color","#000");
                                        $("#danciTui").attr('value','');
                                    }
                                });

                                $("#danciTui").blur(function () {
                                    $("#danciTui").css("color","#000");
                                    if($("#danciTui").val() == ''){
                                        $("#danciTui").css("color","#999");
                                        $("#danciTui").attr('value','请输入单次返款金额');
                                    }
                                });
							}

							$("#membersort")
									.change(
											function() {
												if (!usersorts) {
													usersorts = [];
													for (var i = 0; i < fillmaps.usersorts.length; i++) {
														if (fillmaps.usersorts[i].flag1 == '1003') {
															usersorts
																	.push(fillmaps.usersorts[i]);
														}
													}
												}
												var ms = usersorts[$("#membersort")[0].selectedIndex - 1];
												if (ms) {
													$("#allcishu")
															.val(ms.cishu);
													$("#money1").val(ms.money);
													$("#smallsort").val(
															ms.name2);
												}
											});

							$("#userpart")
									.validate(
											{
												submitHandler : function(form) { // 保存
													var partObj = $("#userpart")
															.formobj();
													partObj.incardId = fillmaps.incard._id;
													partObj.cardId = fillmaps.incard.usercardId;
													parent.$.shade.show();
													$
															.post(
																	"${ctxPath}/user/userInincard/insert.do",
																	partObj,
																	function(
																			data,
																			status) {
																		if (data.success) {
																			reloadIncard();
																			queryInincardList();
																		} else {
																			layer
																					.alert(data.message);
																		}
																		parent.$.shade
																				.hide();
																	});
												}
											});

							$("#btnTuika")
									.click(
											function() {
												if (!selectedRowId) {
													toastr.warning("请选择要退卡的子卡");
													return;
												}
												var kd = $("#" + selectedRowId,
														$("#myTable")).data(
														"rawData");

												var danciTui = parseFloat($(
														"#danciTui").val());
												if (!danciTui || danciTui <= 0) {
													toastr
															.warning("请输入退卡单次金额！");
													$("#danciTui").focus();
													return;
												}
												layer
														.confirm(
																'将退还'
																		+ (danciTui * kd.shengcishu)
																				.toFixed(2)
																		+ '元到主卡，确定要退卡?',
																function(index) {
																	layer
																			.close(index);
																	$
																			.post(
																					"${ctxPath}/user/userInincard/remove.do",
																					{
																						id : kd._id,
																						danciTui : danciTui
																					},
																					function(
																							data,
																							status) {
																						if (data.success) {
																							queryInincardList();
																							reloadIncard()
																							layer
																									.alert("退卡成功!");
																						} else {
																							layer
																									.alert(data.message);
																						}
																					});
																});
											});
							$("#btnBack").click(function() { // 返回
								parent.layer.closeAll();
							});
							$("#myTable")
									.grid(
											{
												datatype : "local",
												colNames : [ "操作", "卡表面号",
														"子卡类别", "总次数", "剩余次数",
														"单价", "会员名称", "免单",
														"主卡折扣", "创建日期",
														"子卡办卡金额" ],
												shrinkToFit : false,
												onSelectRow : function(id) {
													selectedRowId = id;
												},
												ondblClickRow : function(id) {
													var up = $("#" + id,
															$("#myTable"))
															.data("rawData");
												},
												pager : null,
												colModel : [
														{
															name : "idx",
															index : "idx",
															width : 100,
															sortable : false,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='javascript:void(0);' onClick='delRow(\""
																		+ options.rowId
																		+ "\")'>删除</a>&nbsp;&nbsp;";

																v += "<a href='javascript:void(0);' onClick='disableRow(\""
																		+ options.rowId
																		+ "\")'>禁用</a>&nbsp;&nbsp;";
																return v;
															}
														},
														{
															name : "cardno",
															index : "cardno",
															width : 80,
															sortable : false
														},
														{
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
														},
														{
															name : "allcishu",
															sortable : false,
															index : "allcishu"
														},
														{
															name : "shengcishu",
															sortable : false,
															index : "shengcishu"
														},
														{
															name : "danci_money",
															sortable : false,
															index : "danci_money"
														},
														{
															name : "name",
															sortable : false,
															index : "name"
														},
														{
															name : "miandan",
															sortable : false,
															index : "miandan",
															formatter : function(
																	cellvalue) {
																if (cellvalue) {
																	return "是";
																} else {
																	return "否";
																}
															}
														},
														{
															name : "zhekou",
															sortable : false,
															index : "zhekou"
														},
														{
															name : "createTime",
															sortable : false,
															index : "createTime"
														},
														{
															name : "money_leiji",
															sortable : false,
															index : "money_leiji"
														}

												]
											});

							queryInincardList();
						});

		function queryInincardList() {
			$.post("${ctxPath}/user/userInincard/query.do", {
				incardId : fillmaps.incard._id
			}, function(data, status) {
				selectedRowId = null;
				$("#myTable").jqGrid('clearGridData');
				for (var i = 0; i <= data.length; i++) {
					$("#myTable").jqGrid('addRowData', i + 1, data[i]);
				}
			});
		}

		function delRow(idx) {
			var kd = $("#" + idx, $("#myTable")).data("rawData");

			layer.confirm('删除子卡将退还'
					+ (kd.danci_money * kd.shengcishu).toFixed(2)
					+ '元到主卡，确定删除子卡?', function(index) {
				layer.close(index);
				$.post("${ctxPath}/user/userInincard/remove.do", {
					id : kd._id,
					danciTui : -1
				}, function(data, status) {
					if (data.success) {
						queryInincardList();
						reloadIncard()
						layer.alert("删除子卡成功!");
					} else {
						layer.alert(data.message);
					}
				});
			});

		}

		function disableRow(idx) {
			var kd = $("#" + idx, $("#myTable")).data("rawData");

			layer.confirm('禁用子卡将不退还余额到主卡，确定要禁用子卡?', function(index) {
				layer.close(index);
				$.post("${ctxPath}/user/userInincard/remove.do", {
					id : kd._id,
					danciTui : 0
				}, function(data, status) {
					if (data.success) {
						queryInincardList();
						reloadIncard()
						layer.alert("禁用子卡成功!");
					} else {
						layer.alert(data.message);
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

</body>
</html>
