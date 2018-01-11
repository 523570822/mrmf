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
								<label class="col-sm-2 control-label">服务项目</label>
								<div class="col-sm-3">
									<select class="form-control" id="smallsort" name="smallsort">
										<option value="">请选择</option>
										<c:forEach items="${smallsorts}" var="smallsort">
											<option value="${smallsort._id}">${smallsort.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-7">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
									&nbsp;&nbsp;
									<button id="newStaffTicheng" class="btn btn-outline btn-danger"
										type="button">新增</button>
									&nbsp;&nbsp;
									<button id="batchStaffTicheng"
										class="btn btn-outline btn-success" type="button">批量设置</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="staffTichengTable"></table>
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
							$("#batchStaffTicheng")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/staff/staffTicheng/toUpsertBatch.do?organId=${organId}";
											});
							$("#newStaffTicheng")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/staff/staffTicheng/toUpsert.do?organId=${organId}&smallsort="
														+ $("#smallsort").val();
											});

							$("#searchForm").submit(function() {
								$("#staffTichengTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#staffTichengTable")
									.grid(
											{
												url : _ctxPath
														+ "/staff/staffTicheng/query.do",
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												colNames : [ "操作", "服务项目",
														"岗位", "现金提成（%）",
														"现金固定提成（元）", "消卡提成（%）",
														"卡固定提成（元）", "业绩",
														"去除固定金额算提成" ],
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/staff/staffTicheng/toUpsert.do?organId=${organId}&staffTichengId="
																		+ cellvalue
																		+ "'>详情</a>&nbsp;&nbsp;";
																return v;
															},
															sortable : false
														},
														{
															name : "smallsortName",
															index : "smallsortName",
															sortable : false
														},
														{
															name : "staffpostName",
															index : "staffpostName",
															sortable : false
														},
														{
															name : "tichengCashPercent",
															index : "tichengCashPercent"
														},
														{
															name : "tichengCash",
															index : "tichengCash"
														},
														{
															name : "tichengCardPercent",
															index : "tichengCardPercent"
														},
														{
															name : "tichengCard",
															index : "tichengCard"
														},
														{
															name : "yeji",
															index : "yeji"
														},
														{
															name : "removeAmount",
															index : "removeAmount",
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
	</script>
</body>
</html>
