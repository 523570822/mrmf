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
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<input type="hidden" id="sortField" name="sortField"
								value="updateTime"> <input type="hidden" id="sortOrder"
								name="sortOrder" value="DESC">
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
							<c:if test="${!empty organList }">
								<label class="col-sm-1 control-label" id="company_label">子公司</label>
								<div class="col-sm-2" id="company_div">
									<select class="form-control" id="organId" name="organId">
										<option value="">请选择</option>
										<c:forEach items="${organList}" var="organ">
											<option value="${organ._id}">${organ.name}</option>
										</c:forEach>
									</select><span class="help-block m-b-none"></span>
								</div>
							</c:if>
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
		$()
				.ready(
						function() {
							$("#gte\\:updateTime\\|date").val(
									new Date().format());
							$("#searchForm").submit(function() {
								var o = $("#searchForm").formobj();
								searchCriteria = {};
								for ( var key in o) {
									searchCriteria[key] = o[key];
								}
								$("#myTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});

							$("#myTable")
									.grid(
											{
												url : _ctxPath
														+ "/chargeback/queryWaimai.do",
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												colNames : [ "流水号", "物品名称",
														"规格", "单价", "数量", "折扣",
														"总价", "欠费", "售出日期",
														"退单日期", "售出人员", "购买人",
														"售出人员2", "业绩1", "业绩2",
														"免单", "是否挂账", "是否删除" ],
												colModel : [
														{
															name : "xiaopiao",
															width : 90,
															index : "xiaopiao"
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
															name : "updateTime",
															index : "updateTime"
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
														} ]
											});
						});
	</script>
</body>
</html>
