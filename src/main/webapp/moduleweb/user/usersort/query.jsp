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
								<label class="col-sm-2 control-label">会员类型</label>
								<div class="col-sm-3">
									<input id="regex:name1" name="regex:name1" type="text"
										class="form-control">
								</div>
								<label class="col-sm-2 control-label">优惠类别</label>
								<div class="col-sm-3">
									<select class="form-control" id="flag1" name="flag1">
										<option value="">请选择</option>
										<c:forEach items="${usersortTypes}" var="usersortType">
											<option value="${usersortType._id}">${usersortType.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
									&nbsp;&nbsp;
									<button id="newUsersort" class="btn btn-outline btn-danger"
										type="button">新增</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="usersortTable"></table>
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
							$("#newUsersort")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/user/usersort/toUpsert.do?organId=${organId}";
											});

							$("#searchForm").submit(function() {
								$("#usersortTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});

							$("#usersortTable")
									.grid(
											{
												url : _ctxPath
														+ "/user/usersort/query.do",
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												colNames : [ "操作", "会员类型",
														"优惠类别", "是否默认",
														"金额(元)", "次数", "折扣(%)",
														"有效天数", "办卡多少钱积一分",
														"消费多少钱积一分", "赠送积分",
														"办/续卡提成比例", "次数卡明细名称",
														"外卖折扣(%)" ],
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/user/usersort/toUpsert.do?organId=${organId}&usersortId="
																		+ cellvalue
																		+ "'>详情</a>&nbsp;&nbsp;";
																return v;
															}
														},
														{
															name : "name1",
															index : "name1"
														},
														{
															name : "flag1Name",
															index : "flag1Name"
														},
														{
															name : "flag_putong",
															index : "flag_putong",
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
															name : "money",
															index : "money"
														},
														{
															name : "cishu",
															index : "cishu"
														},
														{
															name : "zhekou",
															index : "zhekou"
														},
														{
															name : "law_date",
															index : "law_date"
														},
														{
															name : "coin_bilv",
															index : "coin_bilv"
														},
														{
															name : "coin_money",
															index : "coin_money"
														},
														{
															name : "coin_give",
															index : "coin_give"
														},
														{
															name : "guding_ticheng",
															index : "guding_ticheng"
														},
														{
															name : "name2Name",
															index : "name2Name"
														},
														{
															name : "waimaizhekou",
															index : "waimaizhekou"
														} ]
											});
							$(".ui-jqgrid-bdiv", $("#usersortTable")).css(
									"scroll-x", "auto");
						});
	</script>
</body>
</html>
