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
				<div class="ibox ">
					<div class="ibox-title">
						<h5>店铺平台卡管理</h5>
					</div>
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">城市</label>
								<div class="col-sm-2">
									<select class="form-control" id="city" name="city">
										<option value="">请选择</option>
										<c:forEach items="${ffcitys}" var="city">
											<option value="${city._id }">${city.name }</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">区域</label>
								<div class="col-sm-2">
									<select class="form-control" id="district" name="district">
										<option value="">请选择</option>
									</select>
								</div>
								<label class="col-sm-1 control-label">商圈</label>
								<div class="col-sm-2">
									<select class="form-control" id="region" name="region">
										<option value="">请选择</option>
									</select>
								</div>
								<div class="col-sm-3">
									<input id="lte:money4|double" name="lte:money4|double"
										type="number" class="form-control" min="0"
										placeholder="余额低于多少">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">名称</label>
								<div class="col-sm-2">
									<input id="organName" name="organName" type="text"
										class="form-control">
								</div>
								<label class="col-sm-1 control-label">简称</label>
								<div class="col-sm-2">
									<input id="organAbname" name="organAbname" type="text"
										class="form-control">
								</div>
								<div class="col-sm-2">
									<input id="gte:createTime|date" name="gte:createTime|date"
										class="laydate-icon form-control layer-date"
										placeholder="起始日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-2">
									<input id="lte:createTime|date+1" name="lte:createTime|date+1"
										class="laydate-icon form-control layer-date"
										placeholder="结束日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="organTable"></table>
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
							$("#searchForm").submit(function() {
								$("#organTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#organTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/charge/query.do",
												colNames : [ "操作", "名称", "级别",
														"简称", "支付总额", "余额",
														"累计金额", "有效状态", "联系人",
														"联系电话", "负责人" ],
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												colModel : [
														{
															name : "organId",
															index : "organId",
															align : "center",
															sortable : false,
															width : 180,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "";
																if (!rowObject.organIsNotPrepay) {
																	v += "<a href='${ctxPath}/weixin/charge/toUpsert.do?organId="
																			+ cellvalue
																			+ "'>充值</a>&nbsp;&nbsp;";
																}
																v += "<a href='${ctxPath}/weixin/userpayFenzhang/toQuery.do?organId="
																		+ cellvalue
																		+ "'>分账记录</a>&nbsp;&nbsp;";
																if (!rowObject.organIsNotPrepay) {
																	v += "<a href='${ctxPath}/weixin/charge/toQueryHis.do?organId="
																			+ cellvalue
																			+ "'>充值记录</a>&nbsp;&nbsp;";
																}

																return v;
															}
														},
														{
															name : "organName",
															sortable : false,
															index : "organName"
														},
														{
															name : "parentId",
															index : "parentId",
															align : "center",
															width : 60,
															sortable : false,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																return cellvalue == "0" ? "总公司"
																		: "分公司";
															}
														},
														{
															name : "organAbname",
															index : "organAbname",
															align : "center",
															sortable : false,
															width : 100
														},
														{
															name : "userPayTotal",
															index : "userPayTotal",
															align : "center",
															width : 100,
															formatter : function(
																	cellvalue) {
																return parseFloat(
																		cellvalue)
																		.toFixed(
																				2);
															}
														},
														{
															name : "money4",
															index : "money4",
															align : "center",
															width : 100,
															formatter : function(
																	cellvalue) {
																var m4 = parseFloat(
																		cellvalue)
																		.toFixed(
																				2);
																if (m4 < 200
																		&& m4 != 0) {
																	return "<font color='red'>"
																			+ m4
																			+ "</font>";
																} else {
																	return m4;
																}
															}
														},
														{
															name : "money_leiji",
															index : "money_leiji",
															align : "center",
															width : 100,
															formatter : function(
																	cellvalue) {
																return parseFloat(
																		cellvalue)
																		.toFixed(
																				2);
															}
														},
														{
															name : "organValid",
															index : "organValid",
															align : "center",
															width : 70,
															sortable : false,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																return cellvalue == "1" ? "是"
																		: "<font color=\"red\">否</font>";
															}
														},
														{
															name : "contactMan",
															index : "contactMan",
															align : "center",
															sortable : false,
															width : 100
														}, {
															name : "phone",
															index : "phone",
															align : "center",
															sortable : false,
															width : 100
														}, {
															name : "master",
															index : "master",
															align : "center",
															sortable : false,
															width : 100
														} ]
											});

							$("#city")
									.change(
											function() {
												var t = $("#city")[0];
												var v = t.options[t.selectedIndex];
												if (v.value) {
													$
															.post(
																	'${ctxPath}/weixin/s/queryDistrict.do',
																	{
																		cityId : v.value
																	},
																	function(
																			data,
																			status) {
																		console
																				.log(data.length);
																		var c = $("#district")[0], ds = [];
																		c.options.length = 1;
																		for (var i = 0; i < data.length; i++) {
																			var d = data[i];
																			var option = new Option(
																					d.name,
																					d.name);
																			option.value = d._id;
																			c.options[c.options.length] = option;
																			$(c)
																					.trigger(
																							'change');
																		}
																	});
												} else {
													$("#district")[0].options.length = 1;
												}
											});

							$("#district")
									.change(
											function() {
												var t = $("#district")[0];
												var v = t.options[t.selectedIndex];
												console.log(v);
												if (v.value) {
													$
															.post(
																	'${ctxPath}/weixin/s/queryRegion.do',
																	{
																		districtId : v.value
																	},
																	function(
																			data,
																			status) {
																		var c = $("#region")[0];
																		c.options.length = 1;
																		for (var i = 0; i < data.length; i++) {
																			var d = data[i];
																			var option = new Option(
																					d.name,
																					d.name);
																			option.value = d._id;
																			c.options[c.options.length] = option;
																		}
																	});
												} else {
													$("#region")[0].options.length = 1;
												}
											});

						});
	</script>
</body>
</html>
