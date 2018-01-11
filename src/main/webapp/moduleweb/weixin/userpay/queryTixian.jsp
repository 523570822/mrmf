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
							<div class="col-sm-2">
								<select class="form-control" id="in:orderId|array"
									name="in:orderId|array">
									<option value="1,2">选择类型</option>
									<option value="1">用户提现</option>
									<option value="2">技师提现</option>
								</select>
							</div>
							<div class="col-sm-2">
								<input id="gte:createTime|date" name="gte:createTime|date"
									class="laydate-icon form-control layer-date" placeholder="起始日期"
									data-mask="9999-99-99"
									laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<div class="col-sm-2">
								<input id="lte:createTime|date+1" name="lte:createTime|date+1"
									class="laydate-icon form-control layer-date" placeholder="结束日期"
									data-mask="9999-99-99"
									laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<div class="form-group">
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-5">
									总金额汇总：<font color="red" id="total"></font>
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
		$().ready(
				function() {

					$("#searchForm").submit(function() {
						$("#myTable").reloadGrid({
							postData : $("#searchForm").formobj()
						});
						getSum();
						return false;
					});

					function getSum() {
						var o = $("#searchForm").formobj();
						o.startTime = o["gte:createTime|date"];
						o.endTime = o["lte:createTime|date+1"];
						o.type = o["in:orderId|array"];
						$.post("${ctxPath}/weixin/userpay/queryTixianSum.do",
								o, function(data, status) {
									$("#total").text(data.total);
								});
					}

					getSum();

					$("#myTable").grid(
							{
								url : _ctxPath
										+ "/weixin/userpay/queryTixian.do",
								postData : $("#searchForm").formobj(),
								shrinkToFit : false,
								colNames : [ "姓名", "电话", "提现金额", "描述", "类别",
										"发生时间" ],
								colModel : [
										{
											name : "userName",
											index : "userName",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue)
													return cellvalue;
												else
													return "无";
											}
										},
										{
											name : "userPhone",
											index : "userPhone",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue)
													return cellvalue;
												else
													return "无";
											}
										},
										{
											name : "amount",
											index : "amount"
										},
										{
											name : "desc",
											index : "desc"
										},
										{
											name : "orderId",
											index : "orderId",
											align : "center",
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == "1")
													return "用户提现";
												else if (cellvalue == "2")
													return "技师提现";
												else
													return "未知";
											}
										}, {
											name : "createTime",
											index : "createTime"
										} ]
							});
				});
	</script>
</body>
</html>
