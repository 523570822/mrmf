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
								<label  class="col-sm-1 control-label">反馈者:</label>
								<div class="col-sm-2">
									<input id="regex:userName" name="regex:userName"
										type="text" class="form-control">
								</div>
								<label style="width:70px"class="col-sm-1 control-label">时间:</label>
								<div style="width:250px" class="col-sm-3">
									<input id="gte:createTime|datetime" name="gte:createTime|datetime"
										class="laydate-icon form-control" placeholder="开始日期">
								</div>
								<label style="width:10px" class="col-sm-1 control-label">至</label>
								<div  style="width:250px" class="col-sm-3">
									<input id="lte:createTime|datetime" name="lte:createTime|datetime"
										class="laydate-icon form-control" placeholder="结束日期">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
									<button class="btn btn-danger" id="export" name="export"
										type="button">
										<strong>导出</strong>
									</button>
								</div>
							</div>
						</form>
				 		<div class="jqGrid_wrapper">
							<table id="feedBackTable"></table>
						</div>
				 	</div>
				</div>
				<input id="type" value="staff" type="hidden">
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$().ready(   function() {
		$('#export')
		.click(
				function() {
					var param = $("#searchForm")
							.formobj();
					var o = {};
					o.staffName = param["regex:userName"];
					o.startTime = param["gte:createTime|datetime"];
					o.endTime = param["lte:createTime|datetime"];
					var parStr = jQuery.param(o);
					//console.log(param);
					
					window.location.href = _ctxPath
							+ "/weixin/sysConfig/exportStaffFeed.do?"
							+ parStr;
					//$("#searchForm").attr("action", _ctxPath + "/weixin/sys/user/exportUser.do").submit();
				});
							var start = {
								elem : '#gte:createTime|datetime',
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
								elem : '#lte:createTime|datetime',
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
								$("#feedBackTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#feedBackTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/sysConfig/queryFeedBacks.do?type=" + $("#type").val(),
												shrinkToFit : false,
												colNames : [ "反馈用户姓名", "反馈用户电话","反馈意见","反馈时间"
														],
														
												colModel : [
														{
															name : "userName",
															index : "userName",
															align : "center",
														},
														{
															name : "contact",
															index : "contact",
															align : "center"
														},
														{
															name : "desc",
															index : "desc",
															align : "center",
															width:"500px"
														}
														,
														{
															name : "createTime",
															index : "createTime",
															align : "center",
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
