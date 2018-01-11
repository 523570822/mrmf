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
								<label class="col-sm-2 control-label">大类名称</label>
								<div class="col-sm-3">
									<input id="regex:name" name="regex:name" type="text"
										class="form-control">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-2">
									<button id="newBigsort" class="btn btn-outline btn-danger"
										type="button">新增</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="bigsortTable"></table>
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
							$("#newBigsort")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/user/bigsort/toUpsert.do?organId=${organId}";
											});

							$("#searchForm").submit(function() {
								$("#bigsortTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#bigsortTable")
									.grid(
											{
												url : _ctxPath
														+ "/user/bigsort/query.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "名称", "操作" ],
												colModel : [
														{
															name : "name",
															index : "name"
														},
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/user/bigsort/toUpsert.do?organId=${organId}&bigsortId="
																		+ cellvalue
																		+ "'>详情</a>&nbsp;&nbsp;";
																return v;
															}
														} ]
											});

						});
	</script>
</body>
</html>
