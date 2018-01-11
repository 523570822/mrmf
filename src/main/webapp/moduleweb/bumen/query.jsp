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
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">部门名称</label>
								<div class="col-sm-3">
									<input id="regex:name" name="regex:name" type="text"
										class="form-control">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-2">
									<button id="newBumen" class="btn btn-outline btn-danger"
										type="button">新增</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="bumenTable"></table>
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
							$("#newBumen").click(
									function() {
										document.location.href = _ctxPath
												+ "/bumen/toUpsert.do";
									});

							$("#searchForm").submit(function() {
								$("#bumenTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#bumenTable")
									.grid(
											{
												url : _ctxPath
														+ "/bumen/query.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "部门名称", "操作" ],
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
																var v = "<a href='${ctxPath}/bumen/toUpsert.do?bumenId="
																		+ cellvalue
																		+ "'>详情</a>&nbsp;&nbsp;";

																v += "<a href='javascript:void(0);' onclick='removeBumen(\""
																		+ cellvalue
																		+ "\")'>删除</a>";

																return v;
															}
														} ]
											});

						});
		function removeBumen(bumenId) {
			layer.confirm('确定要删除部门?', function(index) {
				layer.close(index);
				var url = _ctxPath + "/bumen/remove/" + bumenId + ".do";
				$.get(url, {}, function(data) {
					if (data) {
						if (data.success) {
							toastr.success("操作成功");
						} else {
							toastr.error(data.message);
						}
						$("#bumenTable").trigger("reloadGrid");
					} else {
						toastr.error("操作失败");
					}
				});
			});
		}
	</script>
</body>
</html>
