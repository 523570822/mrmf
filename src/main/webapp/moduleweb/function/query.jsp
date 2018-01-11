<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style>
.maxHeight {
	height: 480px;
	border: 1px solid #ddd;
	overflow: auto;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-3 maxHeight">
								<div id="function"></div>
							</div>
							<div class="col-sm-9">
								<form id="searchForm" method="get" class="form-horizontal">
									<input id="parentId" name="parentId" type="hidden"
										value="${param.parentId}">
									<div class="form-group">
										<label class="col-sm-2 control-label">名称</label>
										<div class="col-sm-3">
											<input id="regex:name" name="regex:name" type="text"
												class="form-control">
										</div>
										<div class="col-sm-2">
											<button id="search" class="btn btn-primary" type="submit">查询</button>
										</div>
										<div class="col-sm-2">
											<button id="newFunction" class="btn btn-outline btn-danger"
												type="button">新增</button>
										</div>
									</div>
								</form>
								<div class="jqGrid_wrapper">
									<table id="functionTable"></table>
								</div>
							</div>
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
							$("#function").tree({
								onClick : clk,
								idKey : "_id",
								pIdKey : "parentId",
								nameKey : "name",
								nodeHandler : function(n) {
									if (n._id == "0") // 展开根节点
										n.open = true;
								}
							});
							$("#newFunction")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/function/toUpsert.do?parentId="
														+ $("#parentId").val();
											});
							// 默认选择parentId指定节点
							var treeObj = $.fn.zTree.getZTreeObj("function");
							treeObj.selectNode(treeObj.getNodeByParam("_id",
									"${param.parentId}"));

							function clk(e, treeId, node) {
								$("#parentId", $("#searchForm")).val(node._id);
								$("#functionTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});

								if (node.level == 2) {
									$("#newFunction").hide();
								} else {
									$("#newFunction").show();
								}
							}
							$("#searchForm").submit(function() {
								$("#functionTable").reloadGrid( {
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#functionTable")
									.grid(
											{
												url : _ctxPath
														+ "/function/query.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "名称", "标识代码",
														"功能地址", "操作" ],
												colModel : [
														{
															name : "name",
															index : "name"
														},
														{
															name : "code",
															index : "code"
														},
														{
															name : "action",
															index : "action"
														},
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/function/toUpsert.do?parentId="
																		+ $(
																				"#parentId")
																				.val()
																		+ "&functionId="
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
