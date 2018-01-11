<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="parentId"
	value="${param.parentId == null?sessionScope.organId:param.parentId}" />
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox ">
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<div class="form-group">
								<div class="col-sm-2">
									<input id="startTime" name="startTime"
										class="laydate-icon form-control layer-date"
										placeholder="起始日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-2">
									<input id="endTime" name="endTime"
										class="laydate-icon form-control layer-date"
										placeholder="结束日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-1">
									<!-- 									<button id="downLoad" class="btn btn-outline btn-danger" type="button">导出</button> -->
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
	<script type="text/javascript">
		$()
				.ready(
						function() {
							$("#startTime").val(new Date().format());
							$("#downLoad")
									.click(
											function() {
												var startTime = $("#startTime")
														.val().parseDate();
												var endTime = $("#endTime")
														.val().parseDate();
												if (!startTime) {
													toastr.warning("开始日期不能为空");
													$("#startTime").focus();
													return;
												}
												if (!endTime) {
													endTime = new Date()
															.format()
															.parseDate();
												}
												if (startTime - endTime === 0) {
												} else if (startTime > endTime) {
													toastr
															.warning("开始日期需早于结束日期");
													$("#endTime").focus();
													return;
												} else {
													var ok = false, maxDays = 400;
													for (var i = 1; i <= maxDays; i++) { // 400天之内
														var sd = new Date(
																startTime
																		.getTime())
																.addDate(i);
														if (sd - endTime === 0) {
															ok = true;
															break;
														}
													}
													if (!ok) {
														toastr
																.warning("开始日期与结束日期间隔不能超过"
																		+ maxDays
																		+ "天");
														$("#endTime").focus();
														return;
													}
												}
												window.location.href = _ctxPath
														+ "/staffTichengReport/download.do?startTime="
														+ $("#startTime").val()
														+ "&endTime="
														+ $("#endTime").val();
											});
							function loadAll() {
								var startTime = $("#startTime").val()
										.parseDate();
								var endTime = $("#endTime").val().parseDate();
								if (!startTime) {
									toastr.warning("开始日期不能为空");
									$("#startTime").focus();
									return;
								}
								if (!endTime) {
									endTime = new Date().format().parseDate();
								}
								if (startTime - endTime === 0) {
								} else if (startTime > endTime) {
									toastr.warning("开始日期需早于结束日期");
									$("#endTime").focus();
									return;
								} else {
									var ok = false, maxDays = 400;
									for (var i = 1; i <= maxDays; i++) { // 400天之内
										var sd = new Date(startTime.getTime())
												.addDate(i);
										if (sd - endTime === 0) {
											ok = true;
											break;
										}
									}
									if (!ok) {
										toastr.warning("开始日期与结束日期间隔不能超过"
												+ maxDays + "天");
										$("#endTime").focus();
										return;
									}
								}

								// -------------------------加载普通会员记录------------------------
								$.shade.show();
								$
										.post(
												"${ctxPath}/staffTichengReport/query.do",
												$("#searchForm").formobj(),
												function(data) {
													$.shade.hide();
													$("#myTable").jqGrid(
															'clearGridData');
													if (data) {
														for (var i = 0; i < data.length; i++) {
															var d = data[i];
															$("#myTable")
																	.jqGrid(
																			'addRowData',
																			i,
																			d);
														}
													}
												});
							}

							$("#searchForm").submit(function() {
								loadAll();
								return false;
							});
							$("#myTable")
									.grid(
											{
												colNames : [ "员工", "总流水",
														"总提成", "业绩分段提成",
														"取最高业绩提成", "散客流水",
														"外卖流水", "会员卡流水",
														"办卡流水", "续费流水",
														"会员卡外卖流水", "免单流水",
														"微信流水", "散客提成", "外卖提成",
														"会员卡提成", "办卡提成",
														"续费提成", "会员卡外卖提成",
														"免单提成", "微信提成", "部门" ],
												shrinkToFit : false,
												datatype : "local",
												pager : null,
												colModel : [
														{
															name : "staffName",
															index : "staffName",
															width : 70
														},
														{
															name : "totalLiushui",
															index : "totalLiushui",
															width : 70
														},
														{
															name : "totalTicheng",
															index : "totalTicheng",
															width : 70
														},
														{
															name : "tichengFenduan",
															index : "tichengFenduan",
															width : 100
														},
														{
															name : "tichengZuigao",
															index : "tichengZuigao",
															width : 110
														},
														{
															name : "sankeLiushui",
															index : "sankeLiushui",
															width : 70
														},
														{
															name : "waimaiLiushui",
															index : "waimaiLiushui",
															width : 70
														},
														{
															name : "cardLiushui",
															index : "cardLiushui",
															width : 80
														},
														{
															name : "newCardLiushui",
															index : "newCardLiushui",
															width : 70
														},
														{
															name : "xufeiLiushui",
															index : "xufeiLiushui",
															width : 70
														},
														{
															name : "cardWaimaiLiushui",
															index : "cardWaimaiLiushui",
															width : 110
														},
														{
															name : "miandanLiushui",
															index : "miandanLiushui",
															width : 70
														},
														{
															name : "weixinLiushui",
															index : "weixinLiushui",
															width : 70
														},
														{
															name : "sankeTicheng",
															index : "sankeTicheng",
															width : 70
														},
														{
															name : "waimaiTicheng",
															index : "waimaiTicheng",
															width : 70
														},
														{
															name : "cardTicheng",
															index : "cardTicheng",
															width : 80
														},
														{
															name : "newCardTicheng",
															index : "newCardTicheng",
															width : 70
														},
														{
															name : "xufeiTicheng",
															index : "xufeiTicheng",
															width : 70
														},
														{
															name : "cardWaimaiTicheng",
															index : "cardWaimaiTicheng",
															width : 110
														},
														{
															name : "miandanTicheng",
															index : "miandanTicheng",
															width : 70
														},
														{
															name : "weixinTicheng",
															index : "weixinTicheng",
															width : 70
														},
														{
															name : "bumenId",
															index : "bumenId",
															width : 100,
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.bumens;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "无";
															}
														} ]
											});
							loadAll();
						});
	</script>
</body>
</html>
