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
				 		<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">员工姓名:</label>
								<div class="col-sm-2">
									<input id="regex:staffName" name="regex:staffName"
										placeholder="员工姓名" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label">考勤时间:</label>
								<div class="col-sm-3">
									<input id="gte:day|date" name="gte:day|date"
										class="laydate-icon form-control" placeholder="开始日期">
								</div>
								<label class="col-sm-1 control-label">至</label>
								<div class="col-sm-3">
									<input id="lte:day|date" name="lte:day|date"
										class="laydate-icon form-control" placeholder="结束日期">
								</div>
								<div class="col-sm-1">
									<button class="btn btn-primary" id="search" name="search"
										type="submit">
										<strong>搜索</strong>
									</button>
								</div>
							</div>
						</form>
				 		<div class="jqGrid_wrapper">
							<table id="chidaoTable"></table>
						</div>
				 	</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$().ready(   function() {
						var start = {
								elem : '#gte:day|date',
								format : 'YYYY-MM-DD hh:mm:ss',
							/* 	min : laydate.now(), //设定最小日期为当前日期 */
								max : '2099-06-16 23:59:59', //最大日期
								istime : true,
								istoday : false,
								choose : function(datas) {
									end.min = datas; //开始日选好后，重置结束日的最小日期
									end.start = datas; //将结束日的初始值设定为开始日
								}
							};
							var end = {
								elem : '#lte:day|date',
								format : 'YYYY-MM-DD hh:mm:ss',
							/* 	min : laydate.now(), */
								max : '2099-06-16 23:59:59',
								istime : true,
								istoday : false,
								choose : function(datas) {
									start.max = datas; //结束日选好后，重置开始日的最大日期
								}
							};
							laydate(start);
							laydate(end);
							$("#searchForm").submit(function() {
								$("#chidaoTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false; //阻止提交
							});
							$("#chidaoTable")
									.grid(
											{
												url : _ctxPath
														+ "/kaoqin/queryChiDao.do",
												shrinkToFit : false,
												colNames : [ "员工姓名", "考勤类别名称","考勤日期",
														"迟到分钟数","考勤状态", "罚款金额(元)"
														],
												colModel : [
														{
															name : "staffName",
															index : "staffName",
															align : "center",
														},
														{
															name : "leibieName",
															index : "leibieName",
															align : "center"
														},
														{
															name : "day",
															index : "day",
															align : "center",
														},
														{
															name : "card",
															index : "card",
															align : "center"
														},
														{
															name : "code",
															index : "code",
															align : "center",
															formatter : function(cellvalue,
																	options, rowObject) {
																if (cellvalue == 3) {
																	return "旷工";
																} else if(cellvalue == 2){
																	return "早退";
																} else if(cellvalue == 1){
																	return "迟到";
																}
															}
														},
														{
															name : "money_koufa",
															index : "money_koufa",
															align : "center"
														}
														],
												 		gridComplete: function(){
							             				$(".ui-jqgrid-sortable").css("text-align", "center");  
							         		    },
								});
								
						});
	</script>
</body>
</html>
