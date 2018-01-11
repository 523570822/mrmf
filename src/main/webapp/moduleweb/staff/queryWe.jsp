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
				<div class="ibox ">
					<div class="ibox-title">
						<h5>签约技师管理</h5>
					</div>
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">名称</label>
								<div class="col-sm-2">
									<input id="regex:name" name="regex:name" type="text"
										class="form-control">
								</div>
								<label class="col-sm-2 control-label">手机号</label>
								<div class="col-sm-2">
									<input id="regex:phone" name="regex:phone" type="text"
										class="form-control">
								</div>
								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="staffTable"></table>
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
								$("#staffTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#staffTable")
									.grid(
											{
												url : _ctxPath
														+ "/staff/queryWe.do",
												shrinkToFit : false,
												colNames : [ "操作", "姓名", "性别",
														"联系电话" ],
												postData : $("#searchForm")
														.formobj(),
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															width : 100,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='javascript:void(0);' onclick='removeStaff(\""
																		+ cellvalue
																		+ "\")'>解约</a>";
																return v;
															}
														}, {
															name : "name",
															index : "name"
														}, {
															name : "sex",
															index : "sex",
															align : "center"
														}, {
															name : "phone",
															index : "phone",
															align : "center"
														} ]
											});

						});

		function removeStaff(id) {
			layer.confirm('确定解约技师?', function(index) {
				layer.close(index);
				$.shade.show();
				$.get("${ctxPath}/staff/removeFromOrgan.do", {
					staffId : id
				}, function(data) {
					$.shade.hide();
					if (data) {
						if (data.success) {
							toastr.success("操作成功");
							$("#staffTable").trigger("reloadGrid");
						} else {
							toastr.error(data.message);
						}
					} else {
						toastr.error("操作失败");
					}
				});
			});
		}
	</script>
</body>
</html>
