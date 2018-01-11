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
								value="createTime"> <input type="hidden" id="sortOrder"
								name="sortOrder" value="DESC"> <input type="hidden"
								id="type|integer" name="type|integer" value="0"> <input
								type="hidden" id="incardId|empty" name="incardId|empty"
								value="0">
							<div class="col-sm-2">
								<input id="gte:createTime|date" name="gte:createTime|date"
									class="laydate-icon form-control layer-date" placeholder="起始日期"
									data-mask="9999-99-99"
									laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<div class="col-sm-2">
								<input id="lte:createTime|date+1" name="lte:createTime|date+1"
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
									<button id="multipleSearch" class="btn btn-primary"
										type="button">综合查询</button>
									<button id="download" class="btn btn-danger btn-outline"
										type="button">导出</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="myTable"></table>
						</div>
						<div class="jqGrid_wrapper" style="margin-top: 10px">
							<table id="myTable2"></table>
						</div>
					</div>
				</div>
				<form id="export" method="post"  action="${ctxPath}/user/userpart/download.do" class="form-horizontal">
					<input type="hidden" id="content" name="content">
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$()
				.ready(
						function() {
							$("#download")
									.click(
											function() {
												if (jQuery
														.isEmptyObject(searchCriteria)) {
													var o = $("#searchForm")
															.formobj();
													searchCriteria = {};
													for ( var key in o) {
														searchCriteria[key] = o[key];
													}
												}
                                                $("#export").html("");
												var downloadstr = "";
//												var form = $("<form>");//定义一个form表单
//												form.attr("style",
//														"display:none");
//												form.attr("target", "");
//												form.attr("method", "post");
//												form
//														.attr(
//																"action",
//																_ctxPath
//																		+ "/user/userpart/download.do");
												var input1 = $("<input>");
												input1.attr("type", "hidden");
												input1
														.attr("name",
																"queryType");
												input1.attr("value", "xiaofei");
                                                $("#export").append(input1);

												for ( var key in searchCriteria) {
													var input1 = $("<input>");
													input1.attr("type",
															"hidden");
													input1.attr("name", key);
													input1
															.attr(
																	"value",
																	searchCriteria[key]);
                                                    $("#export").append(input1);
												}
												//console.log(downloadstr);
                                                $("#export").submit();
											});
							$("#gte\\:createTime\\|date").val(
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
								$("#myTable2").jqGrid('clearGridData');
								return false;
							});
							$("#multipleSearch")
									.click(
											function() {
												showMultipleSearch(
														"searchForm",
														"myTable",
														"myTable2",
														"会员号,卡表面号,消费金额,服务明细,会员姓名,电话,员工1,员工2,服务日期,会员类型,服务大类,员工1提成,员工2提成,是否删除,挂账,员工3,员工3提成,使用产品价钱,总次数,剩余次数,免单,员工1业绩,员工2业绩,员工3业绩");
											});
							$("#myTable")
									.grid(
											{
												url : _ctxPath
														+ "/user/userpart/queryByFpi.do",
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												onSelectRow : function(id) {
													var kd = $("#" + id,
															$("#myTable"))
															.data("rawData");
													loadChanpin(kd._id);
												},
												colNames : [ "流水号", "挂账",
														"消费金额", "服务明细", "备注",
														"员工1", "员工2", "会员类型",
														"员工3", "服务日期", "员工1提成",
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
															name : "money2",
															index : "money2",
															width : 80
														},
														{
															name : "smallsortName",
															index : "smallsortName"
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
															name : "maindan",
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
							$("#myTable2").grid({
								pager : null,
								shrinkToFit : false,
								datatype : "local",
								colNames : [ "光临日期", "产品名称", "用量", "价格" ],
								colModel : [ {
									name : "createTime",
									index : "createTime"
								}, {
									name : "wupinName",
									index : "wupinName"
								}, {
									name : "yongliang",
									index : "yongliang"
								}, {
									name : "money1",
									index : "money1"
								} ]
							});
						});
		function loadChanpin(userpartId) {
			$.get("${ctxPath}/usewupin/query.do", {
				userpartId : userpartId
			}, function(data) {
				if (data) {
					$("#myTable2").jqGrid('clearGridData');
					for (var i = 0; i <= data.length; i++) {
						$("#myTable2").jqGrid('addRowData', i + 1, data[i]);
					}
				} else {
					toastr.error("操作失败");
				}
			});
		}
	</script>
</body>
</html>
