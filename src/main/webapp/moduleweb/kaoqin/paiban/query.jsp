<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<input type="hidden" id="organId" name="organId"
								value="${organId}">
							<div class="form-group">
								<label class="col-sm-1 control-label">员工姓名</label>
								<div class="col-sm-2">
									<input id="regex:staffName" name="regex:staffName"
										placeholder="员工姓名" type="text" class="form-control">
								</div>
								
								<label class="col-sm-1 control-label">开始年月</label>
								<div class="col-sm-2">
									<input id="gte:yearmonth|integer" placeholder="格式:yyyyMM"
										name="gte:yearmonth|integer" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label">结束年月</label>
								<div class="col-sm-2">
									<input id="lte:yearmonth|integer" placeholder="格式:yyyyMM"
										name="lte:yearmonth|integer" type="text" class="form-control">
								</div>
								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-2"></div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="paibanTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$().ready(
				function() {
					$("#searchForm").submit(function() {
						$("#paibanTable").reloadGrid({
							postData : $("#searchForm").formobj()
						});
						return false;
					});
					$("#paibanTable").grid(
							{
								url : _ctxPath + "/kaoqin/queryPaiBanList.do",
								shrinkToFit : false,
								colNames : ["操作", "员工姓名", "排班年月", "01", "02", "03",
										"04", "05", "06", "07", "08", "09",
										"10", "11", "12", "13", "14", "15",
										"16", "17", "18", "19", "20", "21",
										"22", "23", "24", "25", "26", "27",
										"28", "29", "30", "31" ],
								colModel : [
										{
											name : "_id",
											index : "_id",
											align : "center",
											width : "80px",
											formatter : function(cellvalue,options,rowObject) {
													var v = "<a href='${ctxPath}/kaoqin/toPaiban.do?paibanId="
													+ cellvalue
													+ "'>修改</a>";
													return v;
											 }
										},
										{
											name : "staffName",
											index : "staffName",
											align : "center",
											width : "80px"
										},
										{
											name : "yearmonth",
											index : "yearmonth",
											align : "center",
											width : "70px"
										},
										{
											name : "day01B",
											index : "day01B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day02B",
											index : "day02B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day03B",
											index : "day03B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day04B",
											index : "day04B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day05B",
											index : "day05B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day06B",
											index : "day06B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day07B",
											index : "day07B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day08B",
											index : "day08B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day09B",
											index : "day09B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day10B",
											index : "day10B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day11B",
											index : "day11B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day12B",
											index : "day12B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day13B",
											index : "day13B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day14B",
											index : "day14B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day15B",
											index : "day15B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day16B",
											index : "day16B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day17B",
											index : "day17B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day18B",
											index : "day18B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day19B",
											index : "day19B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day20B",
											index : "day20B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day21B",
											index : "day21B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day22B",
											index : "day22B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day23B",
											index : "day23B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day24B",
											index : "day24B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day25B",
											index : "day25B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day26B",
											index : "day26B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day27B",
											index : "day27B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day28B",
											index : "day28B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day29B",
											index : "day29B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day30B",
											index : "day30B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										},
										{
											name : "day31B",
											index : "day31B",
											align : "center",
											width : "40px",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "") {
													return "无";
												} else {
													return cellvalue;
												}
											}
										} ],
								gridComplete : function() {
									$(".ui-jqgrid-sortable").css("text-align",
											"center");
								},
							});

				});
	</script>
</body>
</html>
