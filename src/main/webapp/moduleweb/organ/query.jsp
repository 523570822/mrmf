<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="parentId"
	value="${param.parentId == null?sessionScope.organId:param.parentId}" />
<html>
<head>
<title></title>
	<style>
		.ui-jqgrid{height: 96% !important;}
		.ui-jqgrid-view{height: 92% !important;}
		.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{border-top: 0 !important;}
		.ui-jqgrid .ui-jqgrid-pager, .ui-jqgrid .ui-jqgrid-toppager{padding: 10px 0 10px !important;}
		.ibox-content{padding-bottom: 0 !important;}
		.ui-jqgrid .ui-jqgrid-view{overflow-y: hidden;}
	</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight" style="height: 99% !important;">
		<div class="row" style="height: 100% !important;">
			<div class="col-sm-12" style="height: 100% !important;">
				<div class="ibox " style="height: 100% !important;">
					<c:if
						test="${param.parentId != \"0\" && !sessionScope.isOrganAdmin}">
						<div class="ibox-title">
							<h5>
								<a href="${ctxPath}/organ/toQuery.do?parentId=0"
									class="btn-link"><i class="fa fa-angle-double-left"></i>返回</a>
								${parentOrgan.name} - 子公司管理
							</h5>
						</div>
					</c:if>
					<div class="ibox-content" style="height: 100% !important;">
						<form id="searchForm" method="get" class="form-horizontal">
							<input type="hidden" id="parentId" name="parentId"
								value="${parentId}">
							<div class="form-group">
								<label class="col-sm-1 control-label">名称</label>
								<div class="col-sm-2">
									<input id="regex:name" name="regex:name" type="text"
										class="form-control">
								</div>
								<label class="col-sm-1 control-label">手机号</label>
								<div class="col-sm-2">
									<input id="regex:phone" name="regex:phone" type="text"
										class="form-control">
								</div>
								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-5">
									<c:if test="${parentId != \"0\"}">
										<button id="newOrgan" class="btn btn-danger" type="button">新增子公司</button>
										<c:if test="${sessionScope.isOrganAdmin}">
											<button id="cardBtn" class="btn btn-info" type="button">会员卡通用设置</button>
										</c:if>
									</c:if>
									<c:if test="${parentId == \"0\"}">
										<button id="newOrgan" class="btn btn-danger" type="button">
											新增主公司</button>
									</c:if>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper" style="height: 90% !important;">
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
							$("#cardBtn").click(function(){
								document.location.href = _ctxPath
								+ "/organ/toChangeCardOrganIds.do";
							});
							$("#newOrgan")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/organ/toUpsert.do?parentId=${parentId}&isNew=true";
											});
							$("#searchForm").submit(function() {
								$("#organTable").reloadGrid({data : $("#searchForm").formobj()});
								return false;
							});
							$("#organTable")
									.grid(
											{
												url : _ctxPath
														+ "/organ/query.do",
                                                height: '94%',
												colNames : [ "名称", "是否有效", "简称",
														"联系人", "手机号", "负责人","加入时间",
														"操作" ],
												postData : $("#searchForm").formobj(),
												shrinkToFit : false,
												colModel : [
														{
															name : "name",
															index : "name"
														},
														{
															name : "valid",
															index : "valid",
															align : "center",
															width : 60,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																return cellvalue == "1" ? "是"
																		: "<font color=\"red\">否</font>";
															}
														},
														{
															name : "abname",
															index : "abname",
															align : "center",
															width : 100
														},
														{
															name : "contactMan",
															index : "contactMan",
															align : "center",
															width : 80
														},
														{
															name : "phone",
															index : "phone",
															align : "center",
															width : 100
														},
														{
															name : "master",
															index : "master",
															align : "center",
															width : 80
														},
														{
															name : "createTime",
															index : "createTime",
															align : "center",
															width : 100,
															formatter:function(cellvalue,options,rowObject) {
															    return cellvalue;
														/*		if (cellvalue) {
																	return cellvalue
																			.substring(
																					0,
																					10);
																} else {
																	return "";
																}*/
															}
														},
														{
															name : "_id",
															index : "_id",
															align : "center",
															width : 300,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/organ/toUpsert.do?parentId=${parentId}&organId="
																		+ cellvalue
																		+ "'>详情</a>&nbsp;&nbsp;";
																if (rowObject.parentId == "0") {
																	v += "<a href='${ctxPath}/organ/toQuery.do?parentId="
																			+ cellvalue
																			+ "'>子公司管理</a>&nbsp;&nbsp;";
																}

																v += "<a href='${ctxPath}/staff/toQuery.do?parentId=${parentId}&organId="
																		+ cellvalue
																		+ "'>员工管理</a>&nbsp;&nbsp;";
																v += "<a href='${ctxPath}/role/toQuery.do?parentId=${parentId}&organId="
																		+ cellvalue
																		+ "'>角色管理</a>&nbsp;&nbsp;";

																<c:if test="${!sessionScope.isOrganAdmin}">
																if (rowObject.valid == 0) {
																	v += "<a href='javascript:void(0);' onclick='enable(\""
																			+ cellvalue
																			+ "\")'>启用</a>";
																} else {
																	v += "<a href='javascript:void(0);' onclick='disable(\""
																			+ cellvalue
																			+ "\")'>禁用</a>";
																}
																</c:if>

																return v;
															}
														} ]
											});

						});
		function enable(organId) {
			doGet(_ctxPath + "/organ/enable/" + organId + ".do");
		}
		function disable(organId) {
			doGet(_ctxPath + "/organ/disable/" + organId + ".do");
		}
		function doGet(url) {
			$.get(url, {}, function(data) {
				if (data) {
					if (data.success) {
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#organTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
	</script>
</body>
</html>
