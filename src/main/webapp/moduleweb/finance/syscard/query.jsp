<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
	<div class="wrapper wrapper-content  animated fadeInRight" style="height:99% !important;">
		<div class="row" style="height:100% !important;">
			<div class="col-sm-12" style="height:100% !important;">
				<div class="ibox float-e-margins" style="height:100% !important;">
					<div class="ibox-content" style="height:100% !important;">
						<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">店铺名称:</label>
								<div class="col-sm-2">
									<input id="organName" name="organName"
										placeholder="店铺名称" type="text" class="form-control">
								</div> 
								<label class="col-sm-1 control-label">查询时间:</label>
								<div class="col-sm-3" style="width:20%;">
									<input id="gte:createTime|date" name="gte:createTime|date"
										class="laydate-icon form-control" placeholder="开始日期">
								</div>
								<label class="col-sm-1 control-label">至</label>
								<div class="col-sm-3" style="width:20%;">
									<input id="lte:createTime|date" name="lte:createTime|date"
										class="laydate-icon form-control" placeholder="结束日期">
								</div>
								<div class="col-sm-1">
									<button class="btn btn-primary" id="search" name="search"
										type="button">
										<strong>搜索</strong>
									</button>
								</div>
								<div class="col-sm-1">
									<button class="btn btn-danger" id="export" name="export"
										type="button">
										<strong>导出</strong>
									</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper" style="height:90% !important;">
							<table id="sysCardTable"></table>
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
							$('#export').click(function() {
								$("#searchForm").attr("action", _ctxPath + "/finance/syscard/exportSyscard.do").submit();
							});
							var start = {
								elem : '#gte:createTime|date',
								format : 'YYYY-MM-DD hh:mm:ss',
								max : '2099-06-16 23:59:59', //最大日期
								istime : true,
								istoday : false,
								choose : function(datas) {
									end.min = datas; //开始日选好后，重置结束日的最小日期
									end.start = datas; //将结束日的初始值设定为开始日
								}
							};
							var end = {
								elem : '#lte:createTime|date',
								format : 'YYYY-MM-DD hh:mm:ss',
								max : '2099-06-16 23:59:59',
								istime : true,
								istoday : false,
								choose : function(datas) {
									start.max = datas; //结束日选好后，重置开始日的最大日期
								}
							};
							laydate(start);
							laydate(end);
							$("#search").click(function() {
								$("#sysCardTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
							//	return false;
							});
							$("#sysCardTable")
									.grid(
											{
												url : _ctxPath
														+ "/finance/syscard/querySyscard.do",
                                                height:'94%',
												shrinkToFit : false,
												colNames : [ "店铺名称","充值金额","充值时间"],
												postData : $("#searchForm")
														.formobj(),
												colModel : [
														{
															name : "organName",
															index : "organName",
															align : "center"
														},
														{
															name : "amount",
															index : "amount",
															align : "center",
															formatter:function(cellvalue,options,rowObject) { 
																return cellvalue.toFixed(2);
															}
														},{
															name : "createTime",
															index : "createTime",
															align : "center"
														}],
													gridComplete : function() {
														$(".ui-jqgrid-sortable")
																.css("text-align",
																		"center");
													},
											});
						});
	</script>
</body>
</html>
