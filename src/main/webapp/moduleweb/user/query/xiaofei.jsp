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
								id="type|integer" name="type|integer" value="0"><input
								type="hidden" id="ne:incardId|empty" name="ne:incardId|empty"
								value="nil">
							<div class="col-sm-5">
								<input id="condition" name="condition" type="text"
									class="form-control" maxlength="20"
									placeholder="输入会员电话、姓名、卡号或卡表面号进行查询">
							</div>
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
							<div class="form-group">
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
									<!-- <button id="multipleSearch" class="btn btn-primary" type="button">综合查询</button> -->
								</div>
							</div>
						</form>
						<div class="row">
							<div class="col-sm-6">
								<div class="jqGrid_wrapper">
									<table id="myTable"></table>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="jqGrid_wrapper">
									<table id="myTable2"></table>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">外卖情况</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<table id="myTable3"></table>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">消费明细</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<table id="myTable4"></table>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">续费记录</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<table id="myTable5"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var selectedIncardRowId;
		$()
				.ready(
						function() {
							//综合查询方法
							$("#multipleSearch")
									.click(
											function() {
												showMultipleSearch(
														"searchForm",
														"myTable",
														"myTable2,myTable3,myTable4,myTable5",
														"会员号,服务明细,会员姓名,电话,员工1,员工2,光临日期,会员类型,服务大类,员工1提成,员工2提成,是否删除,挂账,员工3,员工3提成,使用产品价钱,总次数,剩余次数,免单,员工1业绩,员工2业绩,员工3业绩");
											});
							$("#searchForm").submit(function() {
								var o = $("#searchForm").formobj();
								$("#myTable").reloadGrid({
									postData : o
								});
								$("#myTable2").jqGrid('clearGridData');
								$("#myTable3").jqGrid('clearGridData');
								$("#myTable4").jqGrid('clearGridData');
								$("#myTable5").jqGrid('clearGridData');
								return false;
							});

							function selectRow() {
								var kd = $("#" + selectedIncardRowId,
										$("#myTable")).data("rawData");
								// 子卡列表
								$
										.post(
												"${ctxPath}/user/userInincard/query.do",
												{
													incardId : kd.incardId
												},
												function(data, status) {
													$("#myTable2").jqGrid(
															'clearGridData');
													for (var i = 0; i <= data.length; i++) {
														$("#myTable2").jqGrid(
																'addRowData',
																i + 1, data[i]);
													}
												});

								// 外卖列表
								$.post("${ctxPath}/waimai/queryAll.do", {
									kaidanId : kd.incardId
								}, function(data, status) {
									$("#myTable3").jqGrid('clearGridData');
									for (var i = 0; i <= data.length; i++) {
										$("#myTable3").jqGrid('addRowData',
												i + 1, data[i]);
									}
								});

								// 消费记录
								$
										.post(
												"${ctxPath}/user/userpart/queryByIncard.do",
												{
													incardId : kd.incardId,
													type : 1,
													all : true,
													typeFlag:true
												},
												function(data, status) {
													$("#myTable4").jqGrid(
															'clearGridData');
													for (var i = 0; i <= data.length; i++) {
														$("#myTable4").jqGrid(
																'addRowData',
																i + 1, data[i]);
													}
												});

								// 续费记录
								$
										.post(
												"${ctxPath}/user/userpart/queryByIncard.do",
												{
													incardId : kd.incardId,
													type : 3,
													all : true
												},
												function(data, status) {
													$("#myTable5").jqGrid(
															'clearGridData');
													for (var i = 0; i <= data.length; i++) {
														$("#myTable5").jqGrid(
																'addRowData',
																i + 1, data[i]);
													}
												});
							}

							$("#myTable")
									.grid(
											{
												url : _ctxPath
														+ "/user/userpart/queryByFpi.do",
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												height : 180,
												onSelectRow : function(id) {
													selectedIncardRowId = id;
													selectRow();
												},
												colNames : [ "卡表面号", "会员姓名",
														"会员类型", "余额", "赠送余额" ],
												colModel : [ {
													name : "cardno",
													index : "cardno",
													width : 80
												}, {
													name : "name",
													index : "name",
													width : 100
												}, {
													name : "usersortName",
													index : "usersortName"
												}, {
													name : "nowMoney4",
													index : "nowMoney4",
													width : 80
												}, {
													name : "nowSong_money",
													index : "nowSong_money",
													width : 80
												} ]
											});

							$("#myTable2").grid(
									{
										datatype : "local",
										shrinkToFit : false,
										pager : null,
										height : 220,
										colNames : [ "子卡类型", "子卡金额", "总次数",
												"剩余次数", "卡表面号" ],
										colModel : [ {
											name : "usersortName",
											index : "usersortName",
											sortable : false,
											width : 120
										}, {
											name : "money_leiji",
											sortable : false,
											width : 70,
											index : "money_leiji"
										}, {
											name : "allcishu",
											sortable : false,
											width : 60,
											index : "allcishu"
										}, {
											name : "shengcishu",
											sortable : false,
											width : 70,
											index : "shengcishu"
										}, {
											name : "cardno",
											index : "cardno",
											width : 100
										} ]
									});

							$("#myTable3")
									.grid(
											{
												datatype : "local",
												shrinkToFit : false,
												pager : null,
												height : 180,
												colNames : [ "挂账", "流水号",
														"物品条码号", "物品名称", "单价",
														"数量", "折扣", "总价", "欠费",
														"欠款是否结清", "售出日期", "售出人员",
														"购买人" ],
												colModel : [
														{
															name : "guazhang_flag",
															index : "guazhang_flag",
															width : 40,
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
															name : "xiaopiao",
															index : "xiaopiao",
															width : 80
														},
														{
															name : "wupinCode",
															sortable : false,
															width : 100,
															index : "wupinCode"
														},
														{
															name : "wupinName",
															index : "wupinName"
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
															name : "flag",
															index : "flag",
															width : 100,
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
														}, {
															name : "buyname",
															width : 100,
															index : "buyname"
														} ]
											});

							$("#myTable4")
									.grid(
											{
												datatype : "local",
												shrinkToFit : false,
												pager : null,
												height : 180,
												colNames : [ "挂账", "会员号",
														"会员姓名", "会员类型", "服务明细",
														"服务日期","成交金额", "是否子卡","总次数", "剩余次数",
														"服务人员", "余额", "免单",
														"服务人员二", "服务人员三" ],
												colModel : [
														{
															name : "guazhang_flag",
															index : "guazhang_flag",
															sortable : false,
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
															name : "id_2",
															index : "id_2",
															width : 100
														},
														{
															name : "name",
															index : "name",
															width : 100
														},
														{
															name : "usersortName",
															index : "usersortName"
														},
														{
															name : "smallsortName",
															index : "smallsortName"
														},
														{
															name : "createTime",
															index : "createTime",
															width : 140
														},
														{
                                                            name : "money_xiaofei",
                                                            index : "money_xiaofei",
                                                            width : 80
														},
														{
															name:"type",
															index:"type",
															width:80,
															align:"center",
                                                            formatter : function(
                                                                cellvalue,
                                                                options,
                                                                rowObject) {
                                                                if (cellvalue==11)
                                                                    return "是";
                                                                else
                                                                    return "否";
                                                            }
														},
														{
															name : "allcishu",
															index : "allcishu",
															width : 80
														},
														{
															name : "nowShengcishu",
															index : "nowShengcishu",
															width : 80
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
															name : "money4",
															index : "money4",
															width : 80
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
														} ]
											});

							$("#myTable5")
									.grid(
											{
												datatype : "local",
												shrinkToFit : false,
												pager : null,
												height : 180,
												colNames : [ "会员号", "会员姓名",
														"会员类型", "续费日期", "余额",
														"续费额", "服务人员", "欠费",
														"续费次数" ],
												colModel : [
														{
															name : "id_2",
															index : "id_2",
															width : 100
														},
														{
															name : "name",
															index : "name",
															width : 100
														},
														{
															name : "usersortName",
															index : "usersortName"
														},
														{
															name : "createTime",
															index : "createTime",
															width : 140
														},
														{
															name : "money4",
															index : "money4",
															width : 80
														},
														{
															name : "money2",
															index : "money2",
															width : 80
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
															name : "money_qian",
															index : "money_qian",
															width : 80
														}, {
															name : "xu_cishu",
															index : "xu_cishu",
															width : 80
														} ]
											});
						});
	</script>
</body>
</html>
