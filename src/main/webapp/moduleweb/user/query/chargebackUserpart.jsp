<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<%@ include file="/moduleweb/user/query/search.jsp"%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />
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
							<input type="hidden" id="sortField" name="sortField"
								value="updateTime"> <input type="hidden" id="sortOrder"
								name="sortOrder" value="DESC"> <input type="hidden"
								id="in:type|array-integer" name="in:type|array-integer"
								value="1,11"> <input type="hidden"
								id="in:usersortType|array" name="in:usersortType|array"
								value="1002,1003">
							<div class="col-sm-2">
								<input id="gte:updateTime|date" name="gte:updateTime|date"
									class="laydate-icon form-control layer-date" placeholder="起始日期"
									data-mask="9999-99-99"
									laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<div class="col-sm-2">
								<input id="lte:updateTime|date+1" name="lte:updateTime|date+1"
									class="laydate-icon form-control layer-date" placeholder="结束日期"
									data-mask="9999-99-99"
									laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<div class="form-group">
								<div class="col-sm-5">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
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
		var manageType;
		$()
				.ready(
						function() {
							$("#gte\\:updateTime\\|date").val(
									new Date().format());

							$("#searchForm").submit(function() {
								var o = $("#searchForm").formobj();
								$("#myTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#myTable")
									.grid(
											{
												url : _ctxPath
														+ "/chargeback/queryUserpart.do",
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												colNames : [ "流水号", "会员号",
														"卡表面号", "挂账", "是否子卡",
														"消费金额", "余额", "消费次数",
														"总次数", "剩余次数", "单次款额",
														"服务明细", "会员姓名", "电话",
														"备注", "员工1", "员工2",
														"会员类型", "员工3", "服务日期",
														"退单日期", "员工1提成",
														"员工2提成", "是否删除",
														"员工3提成", "使用产品价钱",
														"免单", "员工1业绩", "员工2业绩" ],
												colModel : [
														{
															name : "xiaopiao",
															index : "xiaopiao",
															width : 100
														},
														{
															name : "id_2",
															index : "id_2",
															width : 100
														},
														{
															name : "cardno",
															index : "cardno",
															width : 100
														},
														{
															name : "guazhang_flag",
															index : "guazhang_flag",
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
															name : "type",
															index : "type",
															width : 80,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue == 11)
																	return "是";
																else
																	return "否";
															}
														},
														{
															name : "money_xiaofei",
															index : "money_xiaofei",
															width : 80
														},
														{
															name : "money4",
															index : "money4",
															width : 80
														},
														{
															name : "cishu",
															index : "cishu",
															width : 70
														},
														{
															name : "allcishu",
															index : "allcishu",
															width : 60
														},
														{
															name : "shengcishu",
															index : "shengcishu",
															width : 70
														},
														{
															name : "danci_money",
															index : "danci_money",
															width : 70
														},
														{
															name : "smallsortName",
															index : "smallsortName"
														},
														{
															name : "name",
															index : "name",
															width : 100
														},
														{
															name : "phone",
															index : "phone",
															width : 100
														},
														{
															name : "love",
															index : "love"
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
															},
															width : 80
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
															},
															width : 80
														},
														{
															name : "usersortName",
															index : "usersortName"
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
															},
															width : 80
														},
														{
															name : "createTime",
															index : "createTime"
														},
														{
															name : "updateTime",
															index : "updateTime"
														},
														{
															name : "somemoney1",
															index : "somemoney1",
															width : 80
														},
														{
															name : "somemoney2",
															index : "somemoney2",
															width : 80
														},
														{
															name : "delete_flag",
															index : "delete_flag",
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
														},
														{
															name : "somemoney3",
															index : "somemoney3",
															width : 80
														},
														{
															name : "money_wupin",
															index : "money_wupin",
															width : 100
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
														}, {
															name : "yeji1",
															index : "yeji1",
															width : 80
														}, {
															name : "yeji2",
															index : "yeji2",
															width : 80
														} ]
											});
						});
	</script>
</body>
</html>
