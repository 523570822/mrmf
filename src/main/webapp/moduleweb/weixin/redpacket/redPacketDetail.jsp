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
								<label  class="col-sm-1 control-label">领取用户:</label>
								<div class="col-sm-2">
									<input id="regex:userNick" name="regex:userNick"
										type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label">时间:</label>
								<div class="col-sm-3">
									<input id="gte:createTime|datetime" name="gte:createTime|datetime"
										class="laydate-icon form-control" placeholder="开始日期">
								</div>
								<label  class="col-sm-1 control-label">至</label>
								<div   class="col-sm-3">
									<input id="lte:createTime|datetime" name="lte:createTime|datetime"
										class="laydate-icon form-control" placeholder="结束日期">
								</div>
								<div class="col-sm-1" >
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
							</div>
						 </form>
						 <input type="hidden" value="${ redId }" id="redId">
						 <div class="jqGrid_wrapper">
								<table id="redpacketTable"></table>
						 </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$().ready(
				function() {
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
								$("#redpacketTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#redpacketTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/redPacket/queryWeRedRecord.do?redId="+$("#redId").val(),
												shrinkToFit : false,
												colNames : ["用户昵称", "用户头像",
														"领取金额", "领取时间"],
												colModel : [
														{
															name : "userNick",
															index : "userNick",
															align : "center",
														},
														{
															name : "userAvatar",
															index : "userAvatar",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
															var v = "<image style='height:36px;width:36px;border-radius:18px' src='${ossImageHost}/"
															  + cellvalue + "@!avatar'></image>";
															   return v;
															 }
														},
														{
															name : "amount",
															index : "amount",
															align : "center"
														},
														{
															name : "createTime",
															index : "createTime",
															align : "center"
														},
													],
												gridComplete: function(){
							             	$(".ui-jqgrid-sortable").css("text-align", "center");  
							         	}
								});
						});
	</script>
</body>
</html>
