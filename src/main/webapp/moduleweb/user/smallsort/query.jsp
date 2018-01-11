<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
							<input id="organId" name="organId" type="hidden"
								value="${organId}">
							<div class="form-group">
								<label class="col-sm-2 control-label">服务大类</label>
								<div class="col-sm-3">
									<select class="form-control" id="bigcode" name="bigcode">
										<option value="">请选择</option>
										<c:forEach items="${bigsorts}" var="bigsort">
											<option value="${bigsort._id}">${bigsort.name}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-2 control-label">服务项名称</label>
								<div class="col-sm-3">
									<input id="regex:name" name="regex:name" type="text"
										class="form-control">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
									&nbsp;&nbsp;
									<button id="newSmallsort" class="btn btn-outline btn-danger"
										type="button">新增</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="smallsortTable"></table>
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
							$("#newSmallsort")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/user/smallsort/toUpsert.do?organId=${organId}";
											});

							$("#searchForm").submit(function() {
								$("#smallsortTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#smallsortTable")
									.grid(
											{
												url : _ctxPath
														+ "/user/smallsort/query.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "操作", "大类名称",
														"服务项", "价格(元)",
														"成本(元)", "小活标记",
														"所需分钟", "是否有效" ],
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/user/smallsort/toUpsert.do?organId=${organId}&smallsortId="
																		+ cellvalue
																		+ "'>详情</a>&nbsp;&nbsp;";
																if (!rowObject.valid) {
																	v += "<a href='javascript:void(0);' onclick='enable(\""
																			+ cellvalue
																			+ "\")'>启用</a>";
																} else {
																	v += "<a href='javascript:void(0);' onclick='disable(\""
																			+ cellvalue
																			+ "\")'>禁用</a>";
																}
																return v;
															}
														},
														{
															name : "bigsortName",
															index : "bigsortName"
														},
														{
															name : "name",
															index : "name"
														},
														{
															name : "price",
															index : "price"
														},
														{
															name : "price_chengben",
															index : "price_chengben"
														},
														{
															name : "charge_flag",
															index : "charge_flag",
															align : "center",
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
															name : "small_time",
															index : "small_time"
														},
														{
															name : "valid",
															index : "valid",
															align : "center",
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
		function enable(organId) {
			doGet(_ctxPath + "/user/smallsort/enable/" + organId + ".do");
		}
		function disable(organId) {
			doGet(_ctxPath + "/user/smallsort/disable/" + organId + ".do");
		}
		function doGet(url) {
			$.get(url, {}, function(data) {
				if (data) {
					if (data.success) {
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#smallsortTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
	</script>
</body>
</html>
