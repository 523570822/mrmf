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
		$().ready(
				function() {
					$("#startTime").val(new Date().format());
					$("#downLoad").click(
							function() {
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
								window.location.href = _ctxPath
										+ "/staffSalary/download.do?startTime="
										+ $("#startTime").val() + "&endTime="
										+ $("#endTime").val();
							});
					function loadAll() {
						var startTime = $("#startTime").val().parseDate();
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
								toastr.warning("开始日期与结束日期间隔不能超过" + maxDays
										+ "天");
								$("#endTime").focus();
								return;
							}
						}

						// -------------------------加载普通会员记录------------------------
						$.shade.show();
						$.post("${ctxPath}/staffSalary/query.do", $(
								"#searchForm").formobj(), function(data) {
							$.shade.hide();
							$("#myTable").jqGrid('clearGridData');
							if (data) {
								for (var i = 0; i < data.length; i++) {
									var d = data[i];
									$("#myTable").jqGrid('addRowData', i, d);
								}
							}
						});
					}

					$("#searchForm").submit(function() {
						loadAll();
						return false;
					});
					$("#myTable").grid(
							{
								colNames : [ "员工", "指定客数", "总提成", "办卡业绩",
										"外卖业绩", "劳动业绩", "微信业绩", "考勤天数", "底薪",
										"补助", "奖金", "需扣押金", "备注", "合计工资" ],
								shrinkToFit : false,
								datatype : "local",
								pager : null,
								colModel : [ {
									name : "staffName",
									index : "staffName",
									width : 70
								}, {
									name : "dianCount",
									index : "dianCount",
									width : 70
								}, {
									name : "totalTicheng",
									index : "totalTicheng",
									width : 70
								}, {
									name : "bankaYeji",
									index : "bankaYeji",
									width : 70
								}, {
									name : "waimaiYeji",
									index : "waimaiYeji",
									width : 70
								}, {
									name : "laodongYeji",
									index : "laodongYeji",
									width : 70
								}, {
									name : "weixinYeji",
									index : "weixinYeji",
									width : 70
								}, {
									name : "kaoqinDays",
									index : "kaoqinDays",
									width : 70
								}, {
									name : "baseSalary",
									index : "baseSalary",
									width : 70
								}, {
									name : "subsidy",
									index : "subsidy",
									width : 70
								}, {
									name : "reward",
									index : "reward",
									width : 70
								}, {
									name : "deposit",
									index : "deposit",
									width : 70
								}, {
									name : "memo",
									index : "memo",
									width : 70
								}, {
									name : "total",
									index : "total",
									width : 70
								} ]
							});
					loadAll();
				});
	</script>
</body>
</html>
