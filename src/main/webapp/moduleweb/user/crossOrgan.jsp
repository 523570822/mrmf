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
					<div class="ibox-title">
						<h5>跨店消费对账和处理</h5>
					</div>
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<div class="row">
								<label class="col-sm-2 control-label">处理状态</label>
								<div class="col-sm-3">
									<select class="form-control" id="status" name="status">
										<option value="">请选择</option>
										<option value="0" selected>未处理</option>
										<option value="1">已处理</option>
									</select>
								</div>
								<div class="col-sm-3">
									<input id="gte:createTime|date" name="gte:createTime|date"
										class="laydate-icon form-control layer-date"
										placeholder="起始日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-3">
									<input id="lte:createTime|date+1" name="lte:createTime|date+1"
										class="laydate-icon form-control layer-date"
										placeholder="结束日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
							</div>
							<div class="row" style="margin-top: 10px">
								<label class="col-sm-2 control-label">是否本店发卡</label>
								<div class="col-sm-3">
									<select class="form-control" id="isOwner" name="isOwner">
										<option value="true">是</option>
										<option value="false">否</option>
									</select>
								</div>
								<label class="col-sm-1 control-label">对方机构</label>
								<div class="col-sm-3">
									<select class="form-control" id="organId" name="organId">
										<option value="">请选择</option>
										<c:forEach items="${organList}" var="organ">
											<option value="${organ._id}">${organ.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="row" style="margin-top: 10px">
								<label class="col-sm-2 control-label">消费类型</label>
								<div class="col-sm-3">
									<select class="form-control" id="memo" name="memo">
										<option value="">请选择</option>
										<option value="会员卡消费">会员卡消费</option>
										<option value="会员卡充值">会员卡充值</option>
									</select>
								</div>
								<div class="form-group">
									<div class="col-sm-2">
										<button id="search" class="btn btn-primary" type="submit">查询</button>
										<button id="downLoad" class="btn btn-outline btn-danger"
										type="button">导出</button>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div id="totalRegion" class="col-sm-12">
									总金额汇总：<font color="red" id="totalAmount"></font>&nbsp;&nbsp;&nbsp;&nbsp;
									<button id="btnHandle" class="btn btn-success" type="submit">批量处理</button>
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
	<script type="text/javascript">
		var manageType;
		$()
				.ready(
						function() {

							function getHandleData() {
								var o = $("#searchForm").formobj(), oo = {};

								if (o.isOwner == "true") {
									oo.ownerStatus = o.status;
									oo.organId = o.organId;
									oo.ownerOrganId = "${organId}";
								} else {
									oo.status = o.status;
									oo.organId = "${organId}";
									oo.ownerOrganId = o.organId;
								}
								oo.startTime = o["gte:createTime|date"];
								oo.endTime = o["lte:createTime|date+1"];
								oo.isOwner = o.isOwner
								return oo;
							}
							$("#downLoad").click(function(){
								var o = $("#searchForm").formobj(), oo = {};

								if (o.isOwner == "true") {
									oo.ownerStatus = o.status;
									oo.organId = o.organId;
									oo.ownerOrganId = "${organId}";
								} else {
									oo.status = o.status;
									oo.organId = "${organId}";
									oo.ownerOrganId = o.organId;
								}
								oo.startTime = o["gte:createTime|date"];
								oo.endTime = o["lte:createTime|date+1"];
								oo.isOwner = o.isOwner
								window.location.href = _ctxPath + "/user/userpartCrossOrgan/download.do?ownerStatus="+oo.ownerStatus+"&organId="+oo.organId+"&ownerOrganId="+oo.ownerOrganId+"&startTime="+oo.startTime+"&endTime="+oo.endTime;
							
							});
							function getSum() {
								$
										.post(
												"${ctxPath}/user/userpartCrossOrgan/totalHandle.do",
												getHandleData(), function(data,
														status) {
													$("#totalRegion").fillform(
															data);
												});
							}

							$("#btnHandle")
									.click(
											function() {
												$
														.post(
																"${ctxPath}/user/userpartCrossOrgan/handle.do",
																getHandleData(),
																function(data,
																		status) {
																	$(
																			"#searchForm")
																			.submit();
																});
											});

							getSum();

							function getPostData() {
								var o = $("#searchForm").formobj();

								if (o.isOwner == "true") {
									o["ownerStatus|integer"] = o.status;
									o.organId = o.organId;
									o.ownerOrganId = "${organId}";
								} else {
									o["status|integer"] = o.status;
									o.ownerOrganId = o.organId;
									o.organId = "${organId}";
								}
								o.status = undefined;
								o.isOwner = undefined;
								return o;
							}

							$("#searchForm").submit(function() {
								$("#myTable").reloadGrid({
									postData : getPostData()
								});
								getSum();
								return false;
							});

							$("#myTable")
									.grid(
											{
												url : _ctxPath
														+ "/user/userpartCrossOrgan/query.do",
												postData : getPostData(),
												shrinkToFit : false,
												colNames : [ "办卡公司", "消卡公司",
														"办卡公司处理状态", "消卡公司处理状态",
														"金额", "发生时间", "备注" ],
												colModel : [
														{
															name : "ownerOrganName",
															index : "ownerOrganName",
															width : 120
														},
														{
															name : "organName",
															index : "organName",
															width : 120
														},
														{
															name : "ownerStatus",
															index : "ownerStatus",
															align : "center",
															width : 120,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue == 0)
																	return "未处理";
																else if (cellvalue == 1)
																	return "已处理";
																else
																	return "未知";
															}

														},
														{
															name : "status",
															index : "status",
															align : "center",
															width : 120,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue == 0)
																	return "未处理";
																else if (cellvalue == 1)
																	return "已处理";
																else
																	return "未知";
															}

														},
														{
															name : "amount",
															index : "amount",
															formatter : function(
																	cellvalue) {
																return parseFloat(
																		cellvalue)
																		.toFixed(
																				2);
															}
														},
														{
															name : "createTime",
															index : "createTime"
														}, {
															name : "memo",
															index : "memo"
														} ]
											});
						});
	</script>
</body>
</html>
