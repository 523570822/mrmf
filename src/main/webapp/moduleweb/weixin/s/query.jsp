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
								<div id="tree"></div>
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
											<button id="newBtn" class="btn btn-outline btn-danger"
												type="button">新增</button>
										</div>
									</div>
								</form>
								<div class="jqGrid_wrapper">
									<table id="myTable"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var ctype = parseInt("${param.type==null?1:param.type}"), cityId, districtId;
		$()
				.ready(
						function() {
							$("#tree").tree({
								onClick : clk,
								idKey : "_id",
								pIdKey : "parentId",
								nameKey : "name",
								nodeHandler : function(n) {
									if (n._id == "0") // 展开根节点
										n.open = true;
								}
							});
							$("#newBtn")
									.click(
											function() {
												if (ctype == 1) { // 新建城市
													document.location.href = _ctxPath
															+ "/weixin/s/toUpsertCity.do";
												} else if (ctype == 2) { // 新建区域
													document.location.href = _ctxPath
															+ "/weixin/s/toUpsertDistrict.do?cityId="
															+ cityId;
												} else if (ctype == 3) { // 新建商圈
													document.location.href = _ctxPath
															+ "/weixin/s/toUpsertRegion.do?cityId="
															+ cityId
															+ "&districtId="
															+ districtId;
												}
											});
							// 默认选择parentId指定节点
							var pid = 0;
							if (ctype == 2) {
								pid = "${param.cityId}";
							} else if (ctype == 3) {
								pid = "${param.districtId}";
							}
							var treeObj = $.fn.zTree.getZTreeObj("tree");
							treeObj.selectNode(treeObj.getNodeByParam("_id",
									pid));

							function loadCity() {
								$("#newBtn").text("新增城市");
								ctype = 1;
								$
										.post(
												"${ctxPath}/weixin/s/queryCity.do",
												{},
												function(data, status) {
													$("#myTable").jqGrid(
															'clearGridData');
													for (var i = 0; i <= data.length; i++) {
														$("#myTable").jqGrid(
																'addRowData',
																i + 1, data[i]);
													}

												});
							}
							function loadDistrict(cid) {
								$("#newBtn").text("新增区域");
								ctype = 2;
								cityId = cid;
								$
										.post(
												"${ctxPath}/weixin/s/queryDistrict.do",
												{
													cityId : cityId
												},
												function(data, status) {
													$("#myTable").jqGrid(
															'clearGridData');
													for (var i = 0; i <= data.length; i++) {
														$("#myTable").jqGrid(
																'addRowData',
																i + 1, data[i]);
													}

												});
							}
							function loadRegion(cid, did) {
								$("#newBtn").text("新增商圈");
								ctype = 3;
								cityId = cid;
								districtId = did;
								$.post("${ctxPath}/weixin/s/queryRegion.do", {
									districtId : districtId
								}, function(data, status) {
									$("#myTable").jqGrid('clearGridData');
									for (var i = 0; i <= data.length; i++) {
										$("#myTable").jqGrid('addRowData',
												i + 1, data[i]);
									}

								});
							}
							if (ctype == 1) {
								loadCity();
							} else if (ctype == 2) {
								loadDistrict("${param.cityId}");
							} else if (ctype == 3) {
								loadRegion("${param.cityId}",
										"${param.districtId}");
							}
							function clk(e, treeId, node) {
								if (node._id == "0") {
									loadCity();
								} else if (node.data.type == 1) {
									loadDistrict(node._id);
								} else if (node.data.type == 2) {
									loadRegion(node.data.cityId, node._id);
								}

								if (node.level == 2) {
									$("#newFunction").hide();
								} else {
									$("#newFunction").show();
								}
							}
							$("#myTable")
									.grid(
											{
												datatype : "local",
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
																var v= "<a href='${ctxPath}/weixin/s/toUpsertCity.do?cityId="
																			+ cellvalue
																			+ "'>修改</a>&nbsp;&nbsp;";
																 if (rowObject.type == 2) {
																	v = "<a href='${ctxPath}/weixin/s/toUpsertDistrict.do?districtId="
																			+ cellvalue
																			+ "'>修改</a>&nbsp;&nbsp;";
																} else if (rowObject.type == 3) {
																	v = "<a href='${ctxPath}/weixin/s/toUpsertRegion.do?regionId="
																			+ cellvalue
																			+ "'>修改</a>&nbsp;&nbsp;";
																}
																return v;
															}
														} ]
											});

						});
	</script>
</body>
</html>
