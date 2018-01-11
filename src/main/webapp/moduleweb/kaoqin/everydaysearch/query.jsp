<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style type="text/css">
.col-sm-1 {
	width: auto;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">请假人姓名:</label>
								<div class="col-sm-2">
									<input id="regex:staffNames" name="regex:staffNames"
										placeholder="请输入请假人的姓名" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label">查询时间:</label>
								<div class="col-sm-3">
									<input id="gte:date1|date" name="gte:date1|date"
										class="laydate-icon form-control" placeholder="开始日期">
								</div>
								<label class="col-sm-1 control-label">至</label>
								<div class="col-sm-3">
									<input id="lte:date1|date" name="lte:date1|date"
										class="laydate-icon form-control" placeholder="结束日期">
								</div>
								<div class="col-sm-1">
									<button class="btn btn-primary" id="search" name="search"
										type="submit">
										<strong>搜索</strong>
									</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="kaoqindengjiTable"></table>
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
							var start = {
								elem : '#gte:date1|date',
								format : 'YYYY-MM-DD hh:mm:ss',
							/* 	min : laydate.now(), //设定最小日期为当前日期 */
								max : '2099-06-16 23:59:59', //最大日期
								istime : true,
								istoday : false,
								choose : function(datas) {
									end.min = datas; //开始日选好后，重置结束日的最小日期
									end.start = datas; //将结束日的初始值设定为开始日
								}
							};
							var end = {
								elem : '#lte:date1|date',
								format : 'YYYY-MM-DD hh:mm:ss',
							/* 	min : laydate.now(), */
								max : '2099-06-16 23:59:59',
								istime : true,
								istoday : false,
								choose : function(datas) {
									start.max = datas; //结束日选好后，重置开始日的最大日期
								}
							};
							laydate(start);
							laydate(end);
							$("#searchForm").submit(function() {
								$("#kaoqindengjiTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#kaoqindengjiTable")
									.grid(
											{
												url : _ctxPath
														+ "/kaoqin/findQingjiadengji.do",
												shrinkToFit : false,
												colNames : [ "姓名", "请假开始日期",
														"请假结束日期", "请假类型",
														"是否早班", "是否晚班", "扣罚金额",
														"请假理由", "是否删除" ],
												postData : $("#searchForm")
														.formobj(),
												colModel : [
														{
															name : "staffNames",
															index : "staffNames",
															align : "center",
														},
														{
															name : "date1",
															index : "date1",
															align : "center"
														},
														{
															name : "date3",
															index : "date3",
															align : "center"
														},
														{
															name : "type1Names",
															index : "type1Names",
															align : "center"
														},
														{
															name : "zao_flag",
															index : "zao_flag",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v;
																if (cellvalue == true) {
																	v = "是";
																} else {
																	v = "否";
																}
																return v;
															}
														},
														{
															name : "wan_flag",
															index : "wan_flag",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v;
																if (cellvalue == true) {
																	v = "是";
																} else {
																	v = "否";
																}
																return v;
															}
														},
														{
															name : "money_koufa",
															index : "money_koufa",
															align : "center",
														},
														{
															name : "reason",
															index : "reason",
															align : "center",
														},
														{
															name : "delete_flag",
															index : "delete_flag",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v;
																if (cellvalue == true) {
																	v = "<span style='color:red;'>是</span>";
																} else {
																	v = "否";
																}
																return v;
															}
														} ],
												gridComplete : function() {
													$(".ui-jqgrid-sortable")
															.css("text-align",
																	"center");
												},
											});
						});
	</script>
</body>
</html>
