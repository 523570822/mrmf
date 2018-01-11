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
								<label class="col-sm-2 control-label">浮动类别</label>
								<div class="col-sm-3">
									<select class="form-control" id="floatType|integer"
										name="floatType|integer">
										<option value="">请选择</option>
										<option value="0">业绩分段固定提成</option>
										<option value="1">业绩最高额取最高提成</option>
									</select>
								</div>
								<div class="col-sm-7">
									<button id="newStaffFloatTicheng"
										class="btn btn-outline btn-danger" type="button">新增</button>
									&nbsp;&nbsp;
									<button id="settingBtn" class="btn btn-outline btn-primary"
										type="button">流水设置</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="staffFloatTichengTable"></table>
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
							$("#newStaffFloatTicheng")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/staff/staffFloatTicheng/toUpsert.do?organId=${organId}&floatType="
														+ $("#floatType").val();
											});
							$("#settingBtn")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/organ/toSettingTichengLiushui.do";
											});
							$("#floatType\\|integer").change(function() {
								$("#searchForm").submit();
							});

							$("#searchForm").submit(function() {
								$("#staffFloatTichengTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#staffFloatTichengTable")
									.grid(
											{
												url : _ctxPath
														+ "/staff/staffFloatTicheng/query.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "业绩1", "业绩2",
														"提成（%）", "浮动类别", "操作" ],
												colModel : [
														{
															name : "yeji1",
															index : "yeji1"
														},
														{
															name : "yeji2",
															index : "yeji2"
														},
														{
															name : "ticheng",
															index : "ticheng"
														},
														{
															name : "floatType",
															index : "floatType",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue == 0)
																	return "业绩分段固定提成";
																else if (cellvalue == 1)
																	return "业绩最高额取最高提成";
																else
																	return "未知";
															}
														},
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/staff/staffFloatTicheng/toUpsert.do?organId=${organId}&floatType="
																		+ $(
																				"#floatType")
																				.val()
																		+ "&staffFloatTichengId="
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
